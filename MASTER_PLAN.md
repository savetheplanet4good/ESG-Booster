## ESG Portfolio Analytics MVP — Master Plan (Baseline)

This document is the single source of truth for scope, architecture, sprints, APIs, runbook, testing, and acceptance criteria. Keep it simple, reproducible, and CLI-first.

### Objectives
- Deliver a reliable MVP for ESG portfolio analytics suitable for live demos and pilot users
- Use only public/permissive data; keep architecture minimal and offline-capable
- Prioritize determinism, debuggability, and documentation over “fancy” features

### Non-goals (for MVP)
- No news/sentiment, no RAG/Pinecone, no streaming UIs
- No heavy ETL/MLOps or multi-service mesh; single backend and single UI only
- No exhaustive regulatory automation; basic methods appendix only

## Product Scope (MVP)
- Portfolio upload (CSV) → validate, persist, compute company/pillar/portfolio ESG with confidence
- Benchmark comparison against public ETF baskets
- Alternatives suggestions with constraints (turnover, per-name cap, sector drift, tracking error proxy)
- Report export (HTML/PDF) including methodology appendix and versioning
- Entirely local: file-based analytics, optional internet only for initial data seeding

## Public Data Sources (seed set)
- Companies: SEC CIK↔ticker map; sector via SEC SIC; country via Wikipedia/DBpedia snapshot
- Benchmarks: ETF holdings CSVs from issuer sites (S&P/NDX proxies)
- Governance cues (light): selected DEF 14A features (heuristic extraction from pre-curated samples)
- Environmental/Social proxies (light): sector intensity priors from OWID/OECD; small, curated event proxies

## Architecture (minimal, CLI-first)
- Backend: FastAPI (REST)
- UI: Streamlit (tabs-based)
- Analytics: DuckDB over Parquet + Polars transforms (no DB servers)
- Packaging: Docker Compose + Makefile
- Seeds: deterministic sample datasets checked into `data/curated`

## Repo Layout
- `backend/api` (FastAPI app)
- `backend/esg` (scoring, normalization, optimizer, utils)
- `backend/workers` (seed/ingest/recompute scripts)
- `frontend/app` (Streamlit)
- `data/raw`, `data/curated`, `data/duckdb`
- `docker-compose.yml`, `Makefile`, `.env.example`, `scripts/*.py`
- `docs/` (runbook, API reference, data dictionary, ADRs)

## Environments
- Local demo (default): DuckDB + Parquet only
- Offline mode: no external calls; use pre-seeded curated datasets

## Sprint Plan (5 sprints, each shippable)

### Sprint 1 — Foundation + Portfolio Upload + Scoring Stub
- Deliverables:
  - Compose, Makefile, `.env.example`
  - `POST /api/portfolio/upload` (CSV) → write to `data/curated/holdings.parquet` and DuckDB view
  - `GET /api/portfolio/{id}/score` → placeholder ESG from CSV columns
  - Streamlit: Upload tab + ESG tile/table
- Tests: CSV schema validation; deterministic score for fixture
- Docs: README (setup/run), API docs (FastAPI), data dictionary

### Sprint 2 — ESG Scoring v1 (Proxies + Normalization + Confidence)
- Signals:
  - E: sector-intensity prior × size proxy; light environmental proxy counts
  - S: simple labor/legal proxy counts (from curated fixtures)
  - G: DEF 14A heuristic features (board independence keywords, tenure proxy) from curated samples
- Normalization:
  - Per-sector z-score; winsorize 5/95; map to 0–100 with 50 as sector mean
  - Confidence = coverage × recency × source-diversity (0–1) and propagated to portfolio
- Endpoints:
  - `GET /api/scores/company/{ticker}`
  - `GET /api/portfolio/{id}/scores` (with `by_company` and outlier flags)
- Evaluation:
  - Coverage ≥ 80% of fixture tickers; rebuild drift ≤ 5%; confidence present for all rows
- Docs: scoring math, examples, limitations

### Sprint 3 — Benchmarking
- Deliverables:
  - Ingest ETF holdings; compute benchmark ESG; relative performance and contributions
  - `POST /api/benchmark/compare` → `{ relPerf, contributions }`
  - UI: Benchmark tab (selection + chart)
- Tests: deterministic comparison for fixture; contribution sums sanity checks
- Docs: benchmarking method and assumptions

### Sprint 4 — Alternatives Optimizer v1 (with safe fallback)
- Optimizer (cvxpy): maximize ESG uplift − λ·tracking_error − μ·turnover
- Constraints: Σw=1, 0 ≤ wi ≤ cap, sector drift ≤ δ, turnover ≤ τ; TE via simple covariance proxy
- Fallback heuristic: replace k lowest-ESG with same-sector better names bounded by turnover
- Endpoint: `POST /api/alternatives/suggest` → trades, uplift, TE, constraints_report
- UI: Alternatives tab with sliders; show trades, uplift, TE, and bound explanations
- Tests: no constraint violations; uplift ≥ 10% with TE ≤ 2% and τ ≤ 5% on fixture; fallback activates on solver failure
- Docs: optimization math, constraints, troubleshooting

### Sprint 5 — Report Export + Hardening
- Deliverables:
  - `POST /api/report/export` → HTML/PDF (portfolio ESG, benchmark compare, alternatives) with methods appendix
  - Performance and reliability: sub-1s responses on fixtures; error taxonomy; recovery paths
  - CLI scripts: `make demo`, `make ingest`, `make recompute`
- Tests: golden-master report snapshot; latency assertions
- Docs: runbook (ops), debugging guide, ADRs

## Data Model (Parquet + DuckDB views)
- `companies.parquet`: `ticker, cik, name, sector, country`
- `signals.parquet`: `ticker, date, pillar {E|S|G}, feature, value, source, weight, quality`
- `scores.parquet`: `ticker, date, E, S, G, ESG, confidence, method_version`
- `benchmarks.parquet`: `benchmark_id, asof, ticker, weight`
- `holdings.parquet`: `portfolio_id, asof, ticker, weight, source`
- `prices.parquet` (optional): `ticker, date, close, ret`

DuckDB views
- `v_portfolio_scores` (holdings → scores, weighted aggregate)
- `v_benchmark_scores` (benchmark holdings → scores)
- `v_relperf` ((portfolio − benchmark)/benchmark)

## Scoring Methodology (transparent)
- Signals:
  - E: sector intensity prior × size proxy; light environmental event counts (curated)
  - S: labor/legal proxy counts (curated)
  - G: DEF 14A heuristic cues (keywords/structure) on curated samples
- Normalization:
  - Per-sector z-score; winsorize 5/95; map to 0–100 (50 = sector mean)
- Confidence: 0–1 from coverage, recency, diversity; down-weights portfolio score when low
- Aggregation: ESG = wE*E + wS*S + wG*G (default 0.33 each; overridable)
- Outliers: cross-pillar discordance + temporal instability flags

## Benchmarking Methodology
- Benchmark ESG = weighted holdings ESG (latest common date)
- Relative performance = ((Portfolio − Benchmark) / Benchmark) × 100 per pillar + ESG
- Contribution = Σ weight × (score − benchmark_avg)

## Alternatives Optimizer — Details
- Objective: maximize ESG uplift − λ·tracking_error − μ·turnover
- Constraints: Σw=1; bounds per name; sector drift ≤ δ; turnover ≤ τ
- Tracking error proxy: √(Δwᵀ Σ Δw), Σ from returns (diagonal if sparse)
- Inputs: current weights, investable universe (min coverage), scores, sector, covariance
- Fallback: sector-matched replacements for k worst names under turnover cap
- Explainability: list active constraints, marginal ESG gains, turnover impact

## API Contracts (stable)
- `POST /api/portfolio/upload` (multipart CSV)
```json
{ "portfolio_id": "p_123", "asof": "2025-08-01", "holdings": [{"ticker":"AAPL","weight":0.05}] }
```
- `GET /api/portfolio/{id}/scores`
```json
{ "E":61.2, "S":58.4, "G":64.1, "ESG":61.2, "confidence":0.83,
  "by_company": [{"ticker":"AAPL","E":0,"S":0,"G":0,"ESG":0,"confidence":0.8}],
  "outliers": [] }
```
- `POST /api/benchmark/compare`
```json
{ "portfolio_id":"p_123", "benchmark_id":"SPY" }
```
→
```json
{ "relPerf": {"E": 3.1, "S": -1.2, "G": 2.5, "ESG": 1.7},
  "contributions": [{"ticker":"AAPL","delta":0.6}] }
```
- `POST /api/alternatives/suggest`
```json
{ "portfolio_id":"p_123",
  "constraints": { "max_turnover":0.05, "sector_drift":0.02, "max_weight":0.1 } }
```
→
```json
{ "trades":[{"ticker":"X","from":0.02,"to":0.00},{"ticker":"Y","from":0.00,"to":0.02}],
  "esg_uplift":0.15, "te_estimate":0.018, "constraints_report":"turnover bound active" }
```
- `POST /api/report/export`
```json
{ "portfolio_id":"p_123", "benchmark_id":"SPY" }
```
→ file (HTML/PDF)

## Operational Runbook (CLI)
- Bring up stack:
```bash
make up
```
- Seed demo data (curated):
```bash
make seed
```
- Recompute scores:
```bash
make recompute
```
- Full demo:
```bash
make demo
```
- Reset state:
```bash
make reset
```

## Debugging & Logging
- Structured JSON logs with `request_id`, timings, `method_version`
- Error taxonomy: `UserError` (validation), `DataError` (missing signals), `ComputeError` (optimizer), `SystemError`
- Trace scripts:
  - `scripts/trace_scores.py` (input → signals → normalized → scores)
  - `scripts/trace_alt.py` (input → constraints → solution/fallback)
- Deterministic seeds: same inputs ⇒ same outputs

## CI/CD & Testing
- CI: ruff, mypy, unit + contract tests, image build
- Unit tests: scoring math, normalizers, optimizer constraints
- Contract tests: fixtures ↔ API schemas
- Regression: snapshot ESG scores/relPerf on fixtures; golden-master report snapshot

## Performance Targets (fixtures)
- Upload → portfolio scores: < 1s
- Benchmark compare: < 1s
- Alternatives suggest (n≈300 universe): < 2s (fallback < 500ms)
- Report export: < 20s

## Documentation
- `README.md`: quickstart (CLI/Compose)
- `docs/`: Runbook, API reference, data dictionary, scoring method, optimizer notes, troubleshooting
- ADRs: one page per key decision (file-based analytics, normalization, optimizer)

## Risks & Mitigations
- Data coverage/quality: start with large caps; surface confidence; allow manual overrides
- Optimizer fragility: robust defaults; fallback heuristic; clear explanations
- Demo fragility: pre-seeded datasets; offline mode; deterministic seeds

## Acceptance Criteria (MVP done)
- Upload sample portfolio → ESG + confidence in UI under 1s
- Benchmark compare rendered with deterministic values
- Alternatives suggest produces feasible trades, uplift, TE, and constraints report; fallback works
- Report export includes methods appendix and versions
- Full stack runs via `make demo`; logs are clean and errors are actionable

## Timeline & Roles (lean)
- 5 sprints over ~5–6 weeks (1.5–2 engineers)
  - Week 1: Sprint 1
  - Week 2: Sprint 2
  - Week 3: Sprint 3
  - Week 4: Sprint 4
  - Week 5–6: Sprint 5 + hardening

Keep this document updated as the source of truth. Any change to scope, APIs, or data contracts must be reflected here and in `docs/`.
