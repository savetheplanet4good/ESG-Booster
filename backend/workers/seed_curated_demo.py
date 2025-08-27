from __future__ import annotations

import os
import hashlib
from datetime import date, timedelta

import polars as pl

DATA_CURATED_DIR = os.path.join("data", "curated")
COMPANIES_PARQUET = os.path.join(DATA_CURATED_DIR, "companies.parquet")
SIGNALS_PARQUET = os.path.join(DATA_CURATED_DIR, "signals.parquet")

os.makedirs(DATA_CURATED_DIR, exist_ok=True)

# Demo companies aligned with demo holdings
companies = [
    {"ticker": "AAPL", "cik": "0000320193", "name": "Apple Inc.", "sector": "Technology", "country": "US"},
    {"ticker": "MSFT", "cik": "0000789019", "name": "Microsoft Corp.", "sector": "Technology", "country": "US"},
    {"ticker": "GOOGL", "cik": "0001652044", "name": "Alphabet Inc.", "sector": "Communication Services", "country": "US"},
    {"ticker": "AMZN", "cik": "0001018724", "name": "Amazon.com, Inc.", "sector": "Consumer Discretionary", "country": "US"},
    {"ticker": "NVDA", "cik": "0001045810", "name": "NVIDIA Corporation", "sector": "Technology", "country": "US"},
]
companies_df = pl.from_records(companies)
companies_df.write_parquet(COMPANIES_PARQUET)

# Helper: deterministic jitter per ticker in approx [-0.10, 0.10]

def jitter_for_ticker(ticker: str) -> float:
    b = hashlib.sha1(ticker.encode()).digest()[0]
    return ((b % 21) - 10) / 100.0


def clamp01(x: float) -> float:
    return max(0.0, min(1.0, x))

# Demo signals with per-ticker variation
base_date = date.today()
rows = []
for c in companies:
    t = c["ticker"]
    j = jitter_for_ticker(t)
    # Environmental features
    rows += [
        {"ticker": t, "date": base_date.isoformat(), "pillar": "E", "feature": "energy_intensity", "value": clamp01(0.6 + j), "source": "prior", "weight": 1.0, "quality": 0.8},
        {"ticker": t, "date": (base_date - timedelta(days=30)).isoformat(), "pillar": "E", "feature": "emissions_policy", "value": clamp01(0.7 - j/2), "source": "policy", "weight": 1.2, "quality": 0.9},
    ]
    # Social features
    rows += [
        {"ticker": t, "date": base_date.isoformat(), "pillar": "S", "feature": "labor_controversies", "value": clamp01(0.4 - j), "source": "news", "weight": 1.0, "quality": 0.7},
        {"ticker": t, "date": (base_date - timedelta(days=60)).isoformat(), "pillar": "S", "feature": "diversity_policy", "value": clamp01(0.8 + j/3), "source": "policy", "weight": 1.1, "quality": 0.9},
    ]
    # Governance features
    rows += [
        {"ticker": t, "date": base_date.isoformat(), "pillar": "G", "feature": "board_independence", "value": clamp01(0.7 + j/2), "source": "proxy", "weight": 1.0, "quality": 0.9},
        {"ticker": t, "date": (base_date - timedelta(days=10)).isoformat(), "pillar": "G", "feature": "dual_class_shares", "value": clamp01(0.2 - j/4), "source": "proxy", "weight": 0.8, "quality": 0.8},
    ]

signals_df = pl.from_records(rows)
signals_df.write_parquet(SIGNALS_PARQUET)

print({"status": "ok", "companies": companies_df.height, "signals": signals_df.height})
