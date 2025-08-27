import os
import json
from fastapi.testclient import TestClient

from backend.api.main import app, HOLDINGS_PARQUET

client = TestClient(app)


def seed_holdings(tmp_path):
    os.makedirs(os.path.dirname(HOLDINGS_PARQUET), exist_ok=True)
    with open(HOLDINGS_PARQUET, "wb") as f:
        pass  # ensure file path exists for polars to overwrite


def test_scores_flow(tmp_path):
    # Upload minimal CSV
    files = {"file": ("h.csv", b"ticker,weight\nAAPL,0.5\nMSFT,0.5\n", "text/csv")}
    data = {"portfolio_id": "t1", "asof": "2025-08-19"}
    r = client.post("/api/portfolio/upload", files=files, data=data)
    assert r.status_code == 200, r.text

    # Sprint 1 endpoint
    r1 = client.get("/api/portfolio/t1/score")
    assert r1.status_code == 200, r1.text
    body1 = r1.json()
    assert set(body1.keys()) >= {"E", "S", "G", "ESG", "confidence", "by_company", "outliers"}
    assert isinstance(body1["by_company"], list)

    # Sprint 2 endpoint with outliers
    r2 = client.get("/api/portfolio/t1/scores", params={"by_company": "true", "outliers": "true"})
    assert r2.status_code == 200, r2.text
    body2 = r2.json()
    assert isinstance(body2["by_company"], list)
    assert isinstance(body2["outliers"], list)

    # Company score endpoint
    r3 = client.get("/api/scores/company/AAPL")
    assert r3.status_code == 200
    company = r3.json()
    assert set(company.keys()) >= {"ticker", "E", "S", "G", "ESG", "confidence"}
