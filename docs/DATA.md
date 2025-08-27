Data Contracts and Locations

Curated Parquet Files (data/curated)

- holdings.parquet
  - portfolio_id: str
  - asof: str (YYYY-MM-DD)
  - ticker: str
  - weight: float
  - source: str (e.g., upload)

- scores.parquet (optional; overrides placeholder scoring when present)
  - ticker: str
  - E: float (0-100)
  - S: float (0-100)
  - G: float (0-100)
  - ESG: float (0-100)
  - confidence: float (0-1)

- companies.parquet (optional; for metadata)
  - ticker: str
  - company_id: str (optional)
  - name: str (optional)
  - sector: str (optional)

- signals.parquet (optional; future scoring)
  - ticker: str
  - signal_id: str
  - pillar: str (E|S|G)
  - value: float
  - source: str
  - asof: str (YYYY-MM-DD)

DuckDB

- data/duckdb/esg.duckdb — local analytical DB
- The backend creates/refreshes a view v_holdings pointing to holdings.parquet

Notes

- Makefile seeds demo data to curated files and DuckDB
- Use make reset to remove all local curated and DuckDB artifacts


