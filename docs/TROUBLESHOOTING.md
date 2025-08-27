Troubleshooting

Docker issues

- Docker Desktop not running: start the app, ensure virtualization is enabled
- Port conflicts: change ports 8000/8501 or stop other services

Python deps

- pip install failures on M1/M2 Macs: ensure Xcode CLT (xcode-select --install)
- Version mismatch: use Python 3.11 per requirements.txt

Data not found

- Missing Parquet files: run make seed
- Permission errors: verify data/ directories exist and are writable

Frontend cannot reach backend

- Set BACKEND_URL in frontend/.streamlit/secrets.toml or sidebar
- CORS errors: access UI via http://localhost:8501

Rate limit errors

- Default 100 rpm; slow down or change ESG_RATE_LIMIT_REQUESTS in .env

Reset local state

- make down && make reset

Logs

- Backend logs show X-Request-ID and X-Response-Time for each call


