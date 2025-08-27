from __future__ import annotations

from fastapi.testclient import TestClient

from backend.api.main import app

client = TestClient(app)


def test_company_score_endpoint():
	r = client.get("/api/scores/company/AAPL")
	assert r.status_code == 200
	body = r.json()
	for k in ["ticker", "E", "S", "G", "ESG", "confidence"]:
		assert k in body
	assert body["ticker"] == "AAPL"


def test_portfolio_scores_flags(tmp_path):
	# Upload minimal holdings for a temp portfolio
	pid = "t_flags"
	csv = b"ticker,weight\nAAPL,1.0\n"
	r = client.post("/api/portfolio/upload", files={"file": ("h.csv", csv, "text/csv")}, data={"portfolio_id": pid, "asof": "2025-08-01"})
	assert r.status_code == 200

	# With by_company True
	r2 = client.get(f"/api/portfolio/{pid}/scores", params={"by_company": "true", "outliers": "false"})
	assert r2.status_code == 200
	b2 = r2.json()
	assert isinstance(b2.get("by_company"), list) and len(b2["by_company"]) == 1

	# With by_company False
	r3 = client.get(f"/api/portfolio/{pid}/scores", params={"by_company": "false", "outliers": "false"})
	assert r3.status_code == 200
	b3 = r3.json()
	assert b3.get("by_company") == []
