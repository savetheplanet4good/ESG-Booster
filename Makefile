PYTHON ?= python3
PIP ?= pip3

.PHONY: setup up down dev-backend dev-frontend seed seed-curated recompute demo reset test

setup:
	$(PIP) install -r requirements.txt

up:
	docker compose up --build

down:
	docker compose down -v

dev-backend:
	uvicorn backend.api.main:app --host 0.0.0.0 --port 8000 --reload

dev-frontend:
	streamlit run frontend/app/Home.py --server.port 8501 --server.headless true

seed:
	$(PYTHON) backend/workers/seed_curated_demo.py
	$(PYTHON) backend/workers/seed_demo.py
	$(PYTHON) backend/workers/recompute_scores.py

seed-curated:
	$(PYTHON) backend/workers/seed_curated_demo.py

recompute:
	$(PYTHON) backend/workers/recompute_scores.py

demo: setup seed
	@echo "Open http://localhost:8501 and http://localhost:8000/docs"
	@echo "Use 'make dev-backend' and 'make dev-frontend' in two shells."

reset:
	rm -rf data/curated/*.parquet data/duckdb/*.duckdb || true

test:
	PYTHONPATH=. pytest -q
