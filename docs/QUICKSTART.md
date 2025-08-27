Quickstart

1. Clone and enter the repo

```
git clone <your_repo_url>
cd ESG\ Booster
```

2. Prepare environment

```
cp .env.example .env
make setup
```

3. Seed demo data

```
make seed
```

4. Run services (choose one)

- Docker (recommended):

```
make up
```

- Dev mode (two terminals):

```
make dev-backend
make dev-frontend
```

5. Open

- Frontend: http://localhost:8501
- API docs: http://localhost:8000/docs

6. Test

```
make test
```


