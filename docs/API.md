API Reference

Base URL: http://localhost:8000

Health

- GET /health — returns status and config

Stats

- GET /stats — request counters and averages

Upload Portfolio Holdings

- POST /api/portfolio/upload (multipart/form-data)
  - form fields:
    - portfolio_id: string
    - asof: YYYY-MM-DD (optional)
  - file:
    - file: CSV with columns ticker, weight (and optional asof)
  - responses:
    - 200: {"status": "ok", "rows": n}
    - 400/422: validation errors

Get Company Scores

- GET /api/scores/company/{ticker}
  - path params: ticker (e.g., AAPL)
  - response: {ticker, E, S, G, ESG, confidence}

Get Portfolio Scores (Sprint 2)

- GET /api/portfolio/{portfolio_id}/scores
  - query params:
    - by_company: bool (default true)
    - outliers: bool (default false)
  - response model:
    - {E, S, G, ESG, confidence, by_company[], outliers[]}

Backward-Compatible Portfolio Score (Sprint 1)

- GET /api/portfolio/{portfolio_id}/score
  - response model same as above (uses deterministic stub by_company)

Examples

Upload

```
curl -F "portfolio_id=demo" -F "asof=2024-06-30" -F "file=@holdings.csv" http://localhost:8000/api/portfolio/upload
```

Portfolio Scores

```
curl "http://localhost:8000/api/portfolio/demo/scores?by_company=true&outliers=true"
```

Company Score

```
curl http://localhost:8000/api/scores/company/AAPL
```

Rate Limits

- Default: 100 requests/minute (configurable via env)

Errors

- All domain errors return JSON with an error.code, error.message, and request_id header.


