Architecture Overview

Components

- Backend (FastAPI)
  - Entry: backend/api/main.py
  - Config: backend/config.py (pydantic-settings; ENV prefix ESG_)
  - Middleware: backend/middleware.py (rate limiting, request tracking, security headers)
  - Scoring: backend/esg/scoring.py (placeholder and stub scoring, aggregation, outliers)
  - Workers: backend/workers/* (seed/recompute stubs)

- Frontend (Streamlit)
  - UI: frontend/app/Home.py (upload CSV, fetch scores, view company scores)
  - Config: BACKEND_URL via env or Streamlit secrets

- Data
  - Parquet under data/curated
  - DuckDB file under data/duckdb

Local Development

- make dev-backend (8000) and make dev-frontend (8501)
- Or docker-compose (make up)

Security & Observability

- CORS configured for local Streamlit
- Rate limiting via slowapi (default 100 rpm)
- Request IDs and timing headers added to every response

Testing

- pytest tests/ with make test


