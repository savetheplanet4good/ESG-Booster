Install Guide (macOS, Windows, Linux)

Prerequisites

- Git
- Python 3.11
- pip
- Make (optional but recommended)
- Docker Desktop (for containerized run)

macOS (Homebrew)

- Install Homebrew:
  - /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
- Install dependencies:
  - brew install python@3.11 git
  - brew install --cask docker
  - Optional: brew install make

Windows

- Install Git for Windows
- Install Python 3.11 (add to PATH)
- Install Docker Desktop for Windows
- Optional: Install Make via Chocolatey (choco install make)

Linux (Debian/Ubuntu)

- sudo apt update
- sudo apt install -y git python3.11 python3.11-venv python3-pip make
- Install Docker Engine + Compose plugin per official docs

Clone the Repository

- git clone <your_repo_url>
- cd ESG\ Booster

Environment

- cp .env.example .env
- Edit values as needed. Defaults are safe for local.

Optional: Streamlit secrets (for overriding BACKEND_URL):

- mkdir -p frontend/.streamlit
- Create frontend/.streamlit/secrets.toml with:

```
BACKEND_URL = "http://localhost:8000"
```

Local (native) Run

- make setup
- make seed
- make dev-backend  # Terminal 1
- make dev-frontend # Terminal 2

Open:

- Frontend: http://localhost:8501
- API: http://localhost:8000/docs

Docker Run

- make up

Open:

- Frontend: http://localhost:8501
- API: http://localhost:8000/docs

To stop:

- make down

Testing

- make test

Data Locations

- data/curated/*.parquet
- data/duckdb/esg.duckdb

Use make reset to clear local data.

Troubleshooting

- See docs/TROUBLESHOOTING.md


