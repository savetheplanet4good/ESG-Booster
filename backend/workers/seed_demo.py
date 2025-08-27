from __future__ import annotations

import os
from datetime import date

import duckdb
import polars as pl

DATA_CURATED_DIR = os.path.join("data", "curated")
DATA_DUCKDB_DIR = os.path.join("data", "duckdb")
HOLDINGS_PARQUET = os.path.join(DATA_CURATED_DIR, "holdings.parquet")
DUCKDB_PATH = os.path.join(DATA_DUCKDB_DIR, "esg.duckdb")

os.makedirs(DATA_CURATED_DIR, exist_ok=True)
os.makedirs(DATA_DUCKDB_DIR, exist_ok=True)

portfolio_id = "demo"
asof = date.today().isoformat()

rows = [
    {"ticker": "AAPL", "weight": 0.20},
    {"ticker": "MSFT", "weight": 0.20},
    {"ticker": "GOOGL", "weight": 0.20},
    {"ticker": "AMZN", "weight": 0.20},
    {"ticker": "NVDA", "weight": 0.20},
]

seed_df = pl.from_records(rows).with_columns(
    pl.lit(portfolio_id).alias("portfolio_id"),
    pl.lit(asof).alias("asof"),
    pl.lit("seed").alias("source"),
).select(["portfolio_id", "asof", "ticker", "weight", "source"])  # type: ignore[list-item]

if os.path.exists(HOLDINGS_PARQUET):
    existing = pl.read_parquet(HOLDINGS_PARQUET)
    # Drop previous demo rows for cleanliness
    existing_no_demo = existing.filter(pl.col("portfolio_id") != portfolio_id)
    combined = pl.concat([existing_no_demo, seed_df], how="vertical_relaxed")
else:
    combined = seed_df

combined.write_parquet(HOLDINGS_PARQUET)

con = duckdb.connect(DUCKDB_PATH)
try:
    path_sql = HOLDINGS_PARQUET.replace("'", "''")
    con.execute(f"CREATE OR REPLACE VIEW v_holdings AS SELECT * FROM parquet_scan('{path_sql}')")
finally:
    con.close()

print({"status": "ok", "portfolio_id": portfolio_id, "rows": seed_df.height})
