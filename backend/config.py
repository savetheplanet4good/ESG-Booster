"""
Configuration management for ESG Portfolio Analytics.

Centralized configuration with environment variable validation and defaults.
"""
import os
from typing import List
from pydantic_settings import BaseSettings
from pydantic import validator, Field


class Settings(BaseSettings):
    """Application settings with environment variable support."""
    
    # API Configuration
    app_name: str = "ESG Portfolio Analytics API"
    app_version: str = "0.2.0"
    debug: bool = Field(default=False, description="Enable debug mode")
    
    # Server Configuration
    host: str = Field(default="0.0.0.0", description="Server host")
    port: int = Field(default=8000, description="Server port")
    reload: bool = Field(default=False, description="Enable auto-reload")
    
    # CORS Configuration
    cors_origins: List[str] = Field(
        default=["http://localhost:8501", "http://127.0.0.1:8501"],
        description="Allowed CORS origins"
    )
    cors_credentials: bool = Field(default=True, description="Allow credentials")
    cors_methods: List[str] = Field(default=["*"], description="Allowed methods")
    cors_headers: List[str] = Field(default=["*"], description="Allowed headers")
    
    # Data Directories
    data_curated_dir: str = Field(default="data/curated", description="Curated data directory")
    data_duckdb_dir: str = Field(default="data/duckdb", description="DuckDB data directory")
    
    # File Paths
    holdings_parquet: str = Field(default="data/curated/holdings.parquet", description="Holdings parquet file")
    scores_parquet: str = Field(default="data/curated/scores.parquet", description="Scores parquet file")
    companies_parquet: str = Field(default="data/curated/companies.parquet", description="Companies parquet file")
    signals_parquet: str = Field(default="data/curated/signals.parquet", description="Signals parquet file")
    duckdb_path: str = Field(default="data/duckdb/esg.duckdb", description="DuckDB database file")
    
    # Upload Limits
    max_upload_size_mb: int = Field(default=10, description="Maximum upload size in MB")
    max_portfolio_holdings: int = Field(default=1000, description="Maximum holdings per portfolio")
    
    # Scoring Parameters
    confidence_threshold: float = Field(default=0.1, description="Minimum confidence threshold")
    outlier_discordance_threshold: float = Field(default=30.0, description="Outlier discordance threshold")
    outlier_low_confidence_threshold: float = Field(default=0.5, description="Low confidence outlier threshold")
    
    # Rate Limiting
    enable_rate_limiting: bool = Field(default=True, description="Enable rate limiting")
    rate_limit_requests: int = Field(default=100, description="Requests per minute")
    rate_limit_window: int = Field(default=60, description="Rate limit window in seconds")
    
    @validator('cors_origins', pre=True)
    def parse_cors_origins(cls, v):
        """Parse CORS origins from environment variable."""
        if isinstance(v, str):
            return [origin.strip() for origin in v.split(',')]
        return v
    
    @validator('max_upload_size_mb')
    def validate_upload_size(cls, v):
        """Validate upload size is reasonable."""
        if v < 1 or v > 100:
            raise ValueError("Upload size must be between 1 and 100 MB")
        return v
    
    @validator('max_portfolio_holdings')
    def validate_max_holdings(cls, v):
        """Validate maximum holdings is reasonable."""
        if v < 1 or v > 10000:
            raise ValueError("Maximum holdings must be between 1 and 10,000")
        return v
    
    @validator('confidence_threshold')
    def validate_confidence_threshold(cls, v):
        """Validate confidence threshold is between 0 and 1."""
        if v < 0 or v > 1:
            raise ValueError("Confidence threshold must be between 0 and 1")
        return v
    
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        # Create directories if they don't exist
        os.makedirs(self.data_curated_dir, exist_ok=True)
        os.makedirs(self.data_duckdb_dir, exist_ok=True)
    
    class Config:
        env_file = ".env"
        env_prefix = "ESG_"
        case_sensitive = False


# Global settings instance
settings = Settings()

# Validate configuration on startup
def validate_configuration():
    """Validate configuration and environment."""
    errors = []
    
    # Check data directories are writable
    try:
        test_file = os.path.join(settings.data_curated_dir, ".test")
        with open(test_file, "w") as f:
            f.write("test")
        os.remove(test_file)
    except Exception as e:
        errors.append(f"Data curated directory not writable: {e}")
    
    try:
        test_file = os.path.join(settings.data_duckdb_dir, ".test")
        with open(test_file, "w") as f:
            f.write("test")
        os.remove(test_file)
    except Exception as e:
        errors.append(f"Data DuckDB directory not writable: {e}")
    
    if errors:
        raise RuntimeError(f"Configuration validation failed: {'; '.join(errors)}")