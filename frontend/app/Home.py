import io
import os
import requests
import streamlit as st

st.set_page_config(page_title="ESG Portfolio Analytics", layout="wide")

# Robust default: env var or localhost; avoid failing when secrets are missing
backend_url_default = os.environ.get("BACKEND_URL", "http://localhost:8000")
try:
	backend_url = st.secrets.get("BACKEND_URL", backend_url_default)  # type: ignore[attr-defined]
except Exception:
	backend_url = backend_url_default

st.title("ESG Portfolio Analytics — MVP")

with st.sidebar:
	st.markdown("**Settings**")
	backend_url = st.text_input("Backend URL", backend_url)

upload_tab, score_tab, company_tab, scores_tab = st.tabs(["Upload Portfolio", "Score (Sprint 1)", "Company Scores", "Portfolio Scores"]) 

with upload_tab:
	st.header("Upload Holdings CSV")
	portfolio_id = st.text_input("Portfolio ID", value="demo")
	asof = st.date_input("As of date")
	csv_file = st.file_uploader("CSV with columns: ticker, weight", type=["csv"]) 
	if st.button("Upload", disabled=csv_file is None):
		if csv_file is None:
			st.warning("Please select a CSV file.")
		else:
			files = {"file": (csv_file.name, csv_file.getvalue(), "text/csv")}
			data = {"portfolio_id": portfolio_id, "asof": asof.isoformat()}
			resp = requests.post(f"{backend_url}/api/portfolio/upload", files=files, data=data, timeout=30)
			if resp.ok:
				st.success(resp.json())
			else:
				st.error(f"Upload failed: {resp.status_code} {resp.text}")

with score_tab:
	st.header("Portfolio Score (Sprint 1 - Backward Compatible)")
	portfolio_id_q = st.text_input("Portfolio ID", value="demo", key="score_pid")
	if st.button("Fetch Score"):
		resp = requests.get(f"{backend_url}/api/portfolio/{portfolio_id_q}/score", timeout=30)
		if resp.ok:
			data = resp.json()
			st.metric("ESG", data["ESG"]) 
			c1, c2, c3 = st.columns(3)
			c1.metric("E", data["E"]) 
			c2.metric("S", data["S"]) 
			c3.metric("G", data["G"]) 
			st.caption(f"Confidence: {data['confidence']}")
			st.subheader("By Company")
			st.dataframe(data["by_company"]) 
		else:
			st.error(f"Failed: {resp.status_code} {resp.text}")

with company_tab:
	st.header("Company Scores")
	ticker = st.text_input("Ticker", value="AAPL")
	if st.button("Get Company Score"):
		resp = requests.get(f"{backend_url}/api/scores/company/{ticker}", timeout=30)
		if resp.ok:
			st.json(resp.json())
		else:
			st.error(f"Failed: {resp.status_code} {resp.text}")

with scores_tab:
	st.header("Portfolio Scores (Sprint 2)")
	portfolio_id_q2 = st.text_input("Portfolio ID", value="demo", key="scores_pid")
	colA, colB = st.columns(2)
	with colA:
		flag_by_company = st.checkbox("Include by_company", value=True)
	with colB:
		flag_outliers = st.checkbox("Include outliers", value=False)
	if st.button("Fetch Scores"):
		params = {"by_company": str(flag_by_company).lower(), "outliers": str(flag_outliers).lower()}
		resp = requests.get(f"{backend_url}/api/portfolio/{portfolio_id_q2}/scores", params=params, timeout=30)
		if resp.ok:
			data = resp.json()
			st.metric("ESG", data["ESG"]) 
			c1, c2, c3 = st.columns(3)
			c1.metric("E", data["E"]) 
			c2.metric("S", data["S"]) 
			c3.metric("G", data["G"]) 
			st.caption(f"Confidence: {data['confidence']}")
			if len(data.get("by_company", [])):
				st.subheader("By Company")
				st.dataframe(data["by_company"]) 
			if len(data.get("outliers", [])):
				st.subheader("Outliers")
				st.json(data["outliers"]) 
		else:
			st.error(f"Failed: {resp.status_code} {resp.text}")
