from __future__ import annotations

import os
from datetime import date, datetime

import polars as pl

DATA_CURATED_DIR = os.path.join("data", "curated")
COMPANIES_PARQUET = os.path.join(DATA_CURATED_DIR, "companies.parquet")
SIGNALS_PARQUET = os.path.join(DATA_CURATED_DIR, "signals.parquet")
SCORES_PARQUET = os.path.join(DATA_CURATED_DIR, "scores.parquet")

os.makedirs(DATA_CURATED_DIR, exist_ok=True)

if not os.path.exists(SIGNALS_PARQUET):
    raise SystemExit("No curated signals found. Run seed_curated_demo.py first.")

signals = pl.read_parquet(SIGNALS_PARQUET)

# Simple weighted sum per ticker-pillar with recency decay and quality weighting
# value_effective = value * weight * quality * recency_factor
# recency_factor = exp(-lambda * age_days/365), here approximate with 1 / (1 + age_days/180)

def recency_factor(d: str) -> float:
    today = date.today()
    dt = datetime.fromisoformat(d).date()
    age = (today - dt).days
    return 1.0 / (1.0 + (age / 180.0))

signals = signals.with_columns(
    pl.struct(["date"]).map_elements(lambda s: recency_factor(s["date"]), return_dtype=pl.Float64).alias("recency"),
    (pl.col("value") * pl.col("weight") * pl.col("quality")).alias("weighted"),
)

pillar_agg = (
    signals.with_columns((pl.col("weighted") * pl.col("recency")).alias("score_raw"))
    .group_by(["ticker", "pillar"])  # type: ignore[list-item]
    .agg(pl.col("score_raw").sum().alias("score_raw"), pl.len().alias("n"))
)

# Normalize per pillar to 0..100 using min-max across tickers (demo-friendly)
result_rows = []
for pillar in ["E", "S", "G"]:
    pdf = pillar_agg.filter(pl.col("pillar") == pillar)
    if pdf.height == 0:
        continue
    min_v = float(pdf["score_raw"].min())
    max_v = float(pdf["score_raw"].max())
    span = (max_v - min_v) or 1.0
    norm = pdf.with_columns(((pl.col("score_raw") - min_v) / span * 100.0).round(1).alias("score"))
    result_rows += [
        {"ticker": r["ticker"], "pillar": pillar, "score": float(r["score"]), "n": int(r["n"]) }
        for r in norm.to_dicts()
    ]

agg = pl.from_records(result_rows)
# Pivot to columns E,S,G then compute ESG and confidence
wide = agg.pivot(index="ticker", columns="pillar", values="score").rename({"E": "E", "S": "S", "G": "G"})

# Merge counts per pillar for a simple coverage-based confidence (0..1)
counts = pillar_agg.pivot(index="ticker", columns="pillar", values="n").rename({"E": "nE", "S": "nS", "G": "nG"})
merged = wide.join(counts, on="ticker", how="left")
merged = merged.with_columns(
    pl.mean_horizontal([pl.col("E"), pl.col("S"), pl.col("G")]).round(1).alias("ESG"),
    (pl.mean_horizontal([pl.col("nE"), pl.col("nS"), pl.col("nG")]) / 3.0).fill_null(0.0).clip(0.0, 1.0).round(2).alias("confidence"),
)

# Persist scores
merged.write_parquet(SCORES_PARQUET)
print({"status": "ok", "tickers": merged.height, "outfile": SCORES_PARQUET})
