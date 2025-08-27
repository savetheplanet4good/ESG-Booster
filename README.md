ESG Booster — Developer Guide

Overview

This repository contains an MVP for ESG portfolio analytics with a FastAPI backend, a Streamlit frontend, and local data storage using Parquet and DuckDB. Use the Makefile for a smooth local/dev workflow, or Docker Compose to run both services together.

Quickstart (TL;DR)

1. Install prerequisites (macOS):
   - Homebrew: /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   - Python 3.11: brew install python@3.11
   - Docker Desktop: brew install --cask docker (then launch Docker)
   - Make: preinstalled on macOS; otherwise brew install make
2. Clone repo and enter directory:
   - git clone <your_repo_url>
   - cd ESG\ Booster
3. Create env and install deps:
   - cp .env.example .env
   - make setup
4. Seed demo data and run:
   - make seed
   - make up
5. Open UI and API:
   - Frontend: http://localhost:8501
   - API docs: http://localhost:8000/docs

Repository Structure

- backend/ — FastAPI app, middleware, scoring, workers
- frontend/app/Home.py — Streamlit UI
- data/curated/ — Parquet data files (holdings, scores, companies, signals)
- data/duckdb/esg.duckdb — DuckDB database file
- tests/ — Pytest-based API tests
- Makefile — common dev commands
- docker-compose.yml — backend and frontend containers

Key Docs

- docs/INSTALL.md — detailed setup on a fresh laptop
- docs/QUICKSTART.md — minimal commands to run locally
- docs/API.md — endpoints, parameters, and curl examples
- docs/DATA.md — data contracts and schemas
- docs/ARCHITECTURE.md — components and flows
- docs/BUSINESS_CONTEXT.md — purpose, scope, KPIs, risks, roadmap
- docs/TROUBLESHOOTING.md — common issues and fixes
- docs/GITHUB_MIGRATION.md — push to GitHub and set up a new laptop

Common Commands

- make setup — install Python dependencies
- make seed — populate demo curated data and recompute scores
- make up — start backend and frontend via Docker Compose
- make down — stop and remove containers and volumes
- make dev-backend — run backend with auto-reload (port 8000)
- make dev-frontend — run Streamlit UI (port 8501)
- make test — run tests

Environment

See .env.example for configurable settings. Frontend can optionally use frontend/.streamlit/secrets.toml with BACKEND_URL.

License

Private project. All rights reserved.


