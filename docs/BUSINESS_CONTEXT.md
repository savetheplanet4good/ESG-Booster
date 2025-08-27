Business Context — ESG Booster

Purpose

Provide a lightweight, transparent ESG portfolio analytics platform that enables investment teams to upload holdings, compute consistent ESG scores, benchmark against ETFs, and explore alternatives — all with clear data lineage and confidence.

Background

Many ESG tools are opaque and tied to proprietary ratings. This project starts from open, explainable methods and curated signals, exposing the math and data via simple APIs and a Streamlit UI. The repo already ships an MVP: upload, scoring endpoints, and a UI.

Problem Statement

- Analysts need fast, defensible ESG assessments at portfolio and company levels
- Data quality varies (coverage, recency, sources); confidence must be explicit
- Teams need comparable benchmarks and a way to trial alternative constructions

Who This Is For

- Portfolio managers and ESG analysts
- Product and distribution teams needing explainable metrics
- Data/quant teams integrating transparent ESG features into workflows

What We’re Building

- FastAPI backend (`backend/api/main.py`) with scoring routes
- Streamlit UI (`frontend/app/Home.py`) for upload and insights
- Curated Parquet + DuckDB storage (`data/`)
- Deterministic stubs today; designed to evolve to signal-based scores

MVP Scope (current repo)

1) Sprint 1 — Foundations
- Upload holdings CSV and compute deterministic stub scores
- Basic health, stats, and error handling

2) Sprint 2 — Scoring v1 (framework in place)
- Placeholder per-company scores and portfolio aggregation
- Outlier flags (pillar discordance, low confidence)
- Data contracts: `holdings.parquet`, `scores.parquet`, optional `companies.parquet`, `signals.parquet`

3) Sprint 3 — Benchmarking (planned)
- Compare portfolio vs ETF holdings and ESG diffs

4) Sprint 4 — Alternatives (planned)
- Suggest alternatives under constraints; heuristic and optimizer paths

5) Sprint 5 — Reporting (planned)
- Export HTML/PDF with methods appendix

How It Works (at a glance)

- Upload: `/api/portfolio/upload` stores normalized holdings to Parquet and refreshes a DuckDB view
- Scores:
  - Company: `/api/scores/company/{ticker}` returns ESG and confidence (stub/placeholder)
  - Portfolio: `/api/portfolio/{id}/scores` aggregates per-company results with optional outliers
- UI mirrors these calls and shows metrics and tables

Data and Governance

- Curated files in `data/curated/` with explicit schemas (see `docs/DATA.md`)
- Confidence combines coverage/recency/source-diversity in future iterations
- All methods documented and versioned; avoid hidden vendor-specific magic

Non‑Functional Requirements

- Transparency: methods and parameters are documented and versioned
- Performance: sub-second responses on typical portfolios (<1,000 holdings)
- Safety: input validation, CORS, basic rate limiting, audit headers
- Portability: Docker Compose and Make targets for dev/prod parity

Success Metrics (examples)

- Time-to-insight: <5 minutes from clone to first score
- Method clarity: 100% of math steps documented in `docs/`
- Determinism: same inputs → same outputs (seeded demo data)
- Coverage: company universe (tickers) mapped to sectors for normalization (future)

Key Risks and Mitigations

- Data sparsity/recency: start with clear confidence modeling; surface missingness
- Opaque vendor data: prioritize open signals, document mappings when used
- Overfitting/false precision: publish uncertainty/confidence alongside scores

90‑Day Roadmap (indicative)

- Weeks 1–2: finalize data contracts, add unit tests for normalization and confidence
- Weeks 3–4: implement signal ingestion and sector z‑score normalization
- Weeks 5–6: benchmarking endpoint + Streamlit visuals
- Weeks 7–8: alternatives (constraints + heuristic, then optimizer)
- Weeks 9–10: reporting + docs hardening; perf + observability polish

How To Use This Repo

- Start here: `README.md` → `docs/QUICKSTART.md`
- Explore APIs: `docs/API.md`
- Understand schemas: `docs/DATA.md`
- See architecture: `docs/ARCHITECTURE.md`
- Troubleshoot: `docs/TROUBLESHOOTING.md`

Glossary (brief)

- ESG: Environmental, Social, Governance pillars
- Confidence: scalar capturing coverage, recency, and source diversity
- Winsorization: limiting extreme values prior to z‑score normalization
- Z‑score: standardization within sectors to enable comparability


