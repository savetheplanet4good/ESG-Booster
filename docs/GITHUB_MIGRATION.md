GitHub Push and New Laptop Setup

Push to GitHub

1. Create a new empty GitHub repo (private recommended)
2. Initialize locally if needed:

```
git init
git add .
git commit -m "Initial import: ESG Booster"
git branch -M main
git remote add origin git@github.com:<your-org-or-user>/<repo>.git
git push -u origin main
```

3. If using HTTPS (instead of SSH):

```
git remote set-url origin https://github.com/<your-org-or-user>/<repo>.git
```

New Laptop Setup

1. Install prerequisites (see docs/INSTALL.md)
2. Clone repo:

```
git clone git@github.com:<your-org-or-user>/<repo>.git
cd ESG\ Booster
```

3. Environment and dependencies:

```
cp .env.example .env
make setup
```

4. Optional: Streamlit secrets

```
mkdir -p frontend/.streamlit
cat > frontend/.streamlit/secrets.toml <<EOF
BACKEND_URL = "http://localhost:8000"
EOF
```

5. Seed and run:

```
make seed
make up
```

6. Verify:

- UI: http://localhost:8501
- API: http://localhost:8000/docs

Branching Model (suggested)

- main: stable
- feature/*: short-lived feature branches; PRs require passing tests

CI (optional next step)

- Add GitHub Actions for lint/test on push


