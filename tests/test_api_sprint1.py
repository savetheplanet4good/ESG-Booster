from __future__ import annotations

import io
from fastapi.testclient import TestClient

from backend.api.main import app

client = TestClient(app)


def test_upload_and_score_stub():
	csv_bytes = b"ticker,weight\nAAPL,0.5\nMSFT,0.5\n"
	files = {"file": ("t.csv", io.BytesIO(csv_bytes), "text/csv")}
	data = {"portfolio_id": "t_sprint1", "asof": "2025-08-01"}
	r = client.post("/api/portfolio/upload", files=files, data=data)
	assert r.status_code == 200, r.text

	r2 = client.get("/api/portfolio/t_sprint1/score")
	assert r2.status_code == 200, r2.text
	body = r2.json()
	assert set(body.keys()) >= {"E", "S", "G", "ESG", "confidence", "by_company", "outliers"}
	assert isinstance(body["by_company"], list)
	assert len(body["by_company"]) == 2
	assert body["ESG"] == 50.0
