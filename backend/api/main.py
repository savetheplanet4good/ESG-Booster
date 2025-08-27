from __future__ import annotations

import io
import os
from datetime import date
from typing import Any, Dict, List, Optional

import duckdb
import polars as pl
from fastapi import FastAPI, File, Form, HTTPException, UploadFile, Query, Request, status
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field, validator
from slowapi import Limiter
from slowapi.util import get_remote_address

from backend.config import settings
from backend.exceptions import (
    ValidationError,
    NotFoundError,
    DataError,
    FileProcessingError,
    ESGException,
    ComputeError,
)
from backend.middleware import setup_middleware, lifespan, get_request_stats, limiter
from backend.esg.scoring import (
    compute_placeholder_scores,
    compute_stub_scores,
    score_company,
    aggregate_portfolio_scores,
    compute_outliers,
)


class PortfolioUpload(BaseModel):
    """Portfolio upload request model."""
    portfolio_id: str = Field(..., min_length=1, max_length=50, description="Portfolio identifier")
    asof: Optional[str] = Field(None, description="As-of date (YYYY-MM-DD)")
    
    @validator('portfolio_id')
    def validate_portfolio_id(cls, v):
        if not v.replace('_', '').replace('-', '').isalnum():
            raise ValueError('Portfolio ID must contain only alphanumeric characters, hyphens, and underscores')
        return v


class PortfolioScore(BaseModel):
    """Portfolio score response model."""
    E: float = Field(..., ge=0, le=100, description="Environmental score (0-100)")
    S: float = Field(..., ge=0, le=100, description="Social score (0-100)")
    G: float = Field(..., ge=0, le=100, description="Governance score (0-100)")
    ESG: float = Field(..., ge=0, le=100, description="Overall ESG score (0-100)")
    confidence: float = Field(..., ge=0, le=1, description="Confidence score (0-1)")
    by_company: List[Dict[str, Any]] = Field(default_factory=list)
    outliers: List[Dict[str, Any]] = Field(default_factory=list)


# Initialize FastAPI app with lifespan
app = FastAPI(
    title=settings.app_name,
    version=settings.app_version,
    lifespan=lifespan,
    docs_url="/docs" if settings.debug else None,
    redoc_url="/redoc" if settings.debug else None,
)

# Setup CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.cors_origins,
    allow_credentials=settings.cors_credentials,
    allow_methods=settings.cors_methods,
    allow_headers=settings.cors_headers,
)

# Setup all middleware
setup_middleware(app)

# Global exception handler
@app.exception_handler(ESGException)
async def esg_exception_handler(request: Request, exc: ESGException) -> JSONResponse:
    """Handle custom ESG exceptions."""
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "error": {
                "code": exc.error_code,
                "message": exc.detail,
                "request_id": getattr(request.state, 'request_id', 'unknown'),
            }
        },
    )

@app.exception_handler(ValueError)
async def value_error_handler(request: Request, exc: ValueError) -> JSONResponse:
    """Handle ValueError exceptions."""
    return JSONResponse(
        status_code=status.HTTP_400_BAD_REQUEST,
        content={
            "error": {
                "code": "VALUE_ERROR",
                "message": str(exc),
                "request_id": getattr(request.state, 'request_id', 'unknown'),
            }
        },
    )


# Health check endpoint
@app.get("/health")
async def health_check():
    """Basic health check endpoint."""
    return {
        "status": "healthy",
        "version": settings.app_version,
        "config": {
            "max_upload_size_mb": settings.max_upload_size_mb,
            "max_portfolio_holdings": settings.max_portfolio_holdings,
        }
    }


# Request statistics endpoint (for monitoring)
@app.get("/stats")
async def request_statistics():
    """Get request statistics."""
    return get_request_stats()


@app.post("/api/portfolio/upload")
@limiter.limit(f"{settings.rate_limit_requests}/minute")
async def upload_portfolio(
    request: Request,
    portfolio_id: str = Form(...),
    file: UploadFile = File(...),
    asof: Optional[str] = Form(None),
):
    # Validate file
    if not file.filename or not file.filename.endswith('.csv'):
        raise ValidationError("File must be a CSV file", "file")
    
    # Check file size
    if file.size and file.size > settings.max_upload_size_mb * 1024 * 1024:
        raise ValidationError(f"File size must be less than {settings.max_upload_size_mb}MB", "file")
    
    # Validate portfolio_id
    if not portfolio_id or len(portfolio_id.strip()) == 0:
        raise ValidationError("Portfolio ID is required", "portfolio_id")
    
    try:
        raw_bytes = await file.read()
        if len(raw_bytes) == 0:
            raise ValidationError("File is empty", "file")
        
        buffer = io.BytesIO(raw_bytes)
        df = pl.read_csv(buffer)
    except Exception as exc:
        raise FileProcessingError(f"Invalid CSV file: {str(exc)}", "csv")

    # Validate CSV structure
    required_cols = {"ticker", "weight"}
    if not required_cols.issubset(set(df.columns)):
        raise ValidationError(
            f"CSV must contain columns: {sorted(required_cols)}. Found: {sorted(df.columns)}",
            "csv_columns"
        )
    
    # Check for reasonable number of holdings
    if df.height == 0:
        raise ValidationError("CSV file contains no data rows", "csv_data")
    
    if df.height > settings.max_portfolio_holdings:
        raise ValidationError(
            f"Portfolio cannot have more than {settings.max_portfolio_holdings} holdings. Found: {df.height}",
            "csv_data"
        )

    # Normalize schema and validate weights
    today = date.today().isoformat()
    asof_value = asof or today
    df = df.rename({c: c.lower() for c in df.columns})
    if "asof" not in df.columns:
        df = df.with_columns(pl.lit(asof_value).alias("asof"))

    # Validate and normalize weights
    try:
        df = df.with_columns(pl.col("weight").cast(pl.Float64))
    except Exception:
        raise ValidationError("Column 'weight' must contain numeric values", "weight")
    
    if df["weight"].is_null().any():
        raise ValidationError("Weights cannot be null or missing", "weight")
    
    if (df["weight"] < 0).any():
        raise ValidationError("All weights must be non-negative", "weight")
    
    # Check if weights sum to approximately 1.0 (allow for small rounding errors)
    weight_sum = float(df["weight"].sum())
    if abs(weight_sum - 1.0) > 0.01:
        raise ValidationError(
            f"Portfolio weights should sum to approximately 1.0. Current sum: {weight_sum:.4f}",
            "weight"
        )

    df = df.with_columns(
        pl.lit(portfolio_id).alias("portfolio_id"),
        pl.lit("upload").alias("source"),
    )
    df = df.select(["portfolio_id", "asof", "ticker", "weight", "source"])  # type: ignore[list-item]

    # Append to curated parquet
    try:
        if os.path.exists(settings.holdings_parquet):
            existing = pl.read_parquet(settings.holdings_parquet)
            # Replace any previous uploads for this portfolio_id for idempotency
            existing_no_pid = existing.filter(pl.col("portfolio_id") != portfolio_id)
            combined = pl.concat([existing_no_pid, df], how="vertical_relaxed")
        else:
            combined = df
        combined.write_parquet(settings.holdings_parquet)

        # Maintain a simple DuckDB view for convenience
        con = duckdb.connect(settings.duckdb_path)
        try:
            path_sql = settings.holdings_parquet.replace("'", "''")
            con.execute(f"CREATE OR REPLACE VIEW v_holdings AS SELECT * FROM parquet_scan('{path_sql}')")
        finally:
            con.close()
    except Exception as e:
        raise DataError(f"Failed to save portfolio data: {str(e)}", "holdings")

    return {"status": "ok", "rows": df.height}


@app.get("/api/scores/company/{ticker}")
@limiter.limit(f"{settings.rate_limit_requests}/minute")
def get_company_scores(request: Request, ticker: str):
    if not ticker or not ticker.strip():
        raise ValidationError("Ticker symbol is required", "ticker")
    
    ticker = ticker.strip().upper()
    if not ticker.isalpha() or len(ticker) > 10:
        raise ValidationError("Invalid ticker symbol format", "ticker")
    
    try:
        return score_company(ticker)
    except Exception as e:
        raise DataError(f"Failed to retrieve scores for {ticker}: {str(e)}", "scoring")


@app.get("/api/portfolio/{portfolio_id}/scores", response_model=PortfolioScore)
@limiter.limit(f"{settings.rate_limit_requests}/minute")
def get_portfolio_scores(
    request: Request,
    portfolio_id: str,
    by_company: bool = Query(True, description="Include per-company scores"),
    outliers: bool = Query(False, description="Include outlier analysis"),
):
    if not portfolio_id or not portfolio_id.strip():
        raise ValidationError("Portfolio ID is required", "portfolio_id")
    
    if not os.path.exists(settings.holdings_parquet):
        raise NotFoundError("No portfolio holdings have been uploaded yet", "holdings")

    try:
        all_holdings = pl.read_parquet(settings.holdings_parquet)
        holdings = all_holdings.filter(pl.col("portfolio_id") == portfolio_id)
        if holdings.height == 0:
            raise NotFoundError(f"Portfolio '{portfolio_id}' not found", "portfolio")
    except Exception as e:
        raise DataError(f"Failed to load portfolio data: {str(e)}", "holdings")

    try:
        per_company = compute_placeholder_scores(holdings) if by_company else []
        agg = aggregate_portfolio_scores(holdings, per_company if per_company else compute_placeholder_scores(holdings))
        outlier_rows = compute_outliers(
            per_company, 
            discordance_threshold=settings.outlier_discordance_threshold, 
            low_conf_threshold=settings.outlier_low_confidence_threshold
        ) if (per_company and outliers) else []
    except Exception as e:
        raise ComputeError(f"Failed to compute portfolio scores: {str(e)}", "scoring")

    return PortfolioScore(
        E=agg["E"],
        S=agg["S"],
        G=agg["G"],
        ESG=agg["ESG"],
        confidence=agg["confidence"],
        by_company=per_company,
        outliers=outlier_rows,
    )


# Backward-compatible Sprint 1 endpoint
@app.get("/api/portfolio/{portfolio_id}/score", response_model=PortfolioScore)
@limiter.limit(f"{settings.rate_limit_requests}/minute")
def get_portfolio_score(request: Request, portfolio_id: str):
    if not portfolio_id or not portfolio_id.strip():
        raise ValidationError("Portfolio ID is required", "portfolio_id")
    
    if not os.path.exists(settings.holdings_parquet):
        raise NotFoundError("No portfolio holdings have been uploaded yet", "holdings")

    try:
        all_holdings = pl.read_parquet(settings.holdings_parquet)
        holdings = all_holdings.filter(pl.col("portfolio_id") == portfolio_id)
        if holdings.height == 0:
            raise NotFoundError(f"Portfolio '{portfolio_id}' not found", "portfolio")
    except Exception as e:
        raise DataError(f"Failed to load portfolio data: {str(e)}", "holdings")

    try:
        # Sprint 1: use deterministic stub scores (all 50) for by_company
        per_company = compute_stub_scores(holdings)
        agg = aggregate_portfolio_scores(holdings, per_company)
    except Exception as e:
        raise ComputeError(f"Failed to compute portfolio scores: {str(e)}", "scoring")

    return PortfolioScore(
        E=agg["E"],
        S=agg["S"],
        G=agg["G"],
        ESG=agg["ESG"],
        confidence=agg["confidence"],
        by_company=per_company,
        outliers=[],
    )