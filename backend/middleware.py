"""
Middleware for ESG Portfolio Analytics.

Provides rate limiting, request tracking, and security middleware.
"""
import time
import uuid
from typing import Callable, Dict, Any
from contextlib import asynccontextmanager

from fastapi import FastAPI, Request, Response, status
from fastapi.middleware.base import BaseHTTPMiddleware
from slowapi import Limiter, _rate_limit_exceeded_handler
from slowapi.util import get_remote_address
from slowapi.errors import RateLimitExceeded
from slowapi.middleware import SlowAPIMiddleware
import logging

from backend.config import settings
from backend.exceptions import RateLimitError


# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Rate limiter setup
limiter = Limiter(key_func=get_remote_address)

# Request tracking
request_stats: Dict[str, Any] = {
    "total_requests": 0,
    "active_requests": 0,
    "endpoint_stats": {},
}


class RequestTrackingMiddleware(BaseHTTPMiddleware):
    """Middleware to track request statistics and add request IDs."""
    
    async def dispatch(self, request: Request, call_next: Callable) -> Response:
        # Generate request ID
        request_id = str(uuid.uuid4())
        request.state.request_id = request_id
        
        # Track request start
        start_time = time.time()
        request_stats["total_requests"] += 1
        request_stats["active_requests"] += 1
        
        endpoint = f"{request.method} {request.url.path}"
        if endpoint not in request_stats["endpoint_stats"]:
            request_stats["endpoint_stats"][endpoint] = {
                "count": 0,
                "total_time": 0,
                "avg_time": 0,
            }
        
        request_stats["endpoint_stats"][endpoint]["count"] += 1
        
        # Log request start
        logger.info(
            f"Request started",
            extra={
                "request_id": request_id,
                "method": request.method,
                "path": request.url.path,
                "client_ip": get_remote_address(request),
            }
        )
        
        try:
            # Process request
            response = await call_next(request)
            
            # Calculate response time
            duration = time.time() - start_time
            request_stats["endpoint_stats"][endpoint]["total_time"] += duration
            request_stats["endpoint_stats"][endpoint]["avg_time"] = (
                request_stats["endpoint_stats"][endpoint]["total_time"] /
                request_stats["endpoint_stats"][endpoint]["count"]
            )
            
            # Add headers
            response.headers["X-Request-ID"] = request_id
            response.headers["X-Response-Time"] = f"{duration:.4f}s"
            
            # Log successful response
            logger.info(
                f"Request completed",
                extra={
                    "request_id": request_id,
                    "status_code": response.status_code,
                    "duration": duration,
                }
            )
            
            return response
            
        except Exception as e:
            # Log error
            logger.error(
                f"Request failed",
                extra={
                    "request_id": request_id,
                    "error": str(e),
                    "duration": time.time() - start_time,
                },
                exc_info=True
            )
            raise
        finally:
            # Always decrement active request count
            request_stats["active_requests"] -= 1


class SecurityHeadersMiddleware(BaseHTTPMiddleware):
    """Middleware to add security headers."""
    
    async def dispatch(self, request: Request, call_next: Callable) -> Response:
        response = await call_next(request)
        
        # Add security headers
        response.headers["X-Content-Type-Options"] = "nosniff"
        response.headers["X-Frame-Options"] = "DENY"
        response.headers["X-XSS-Protection"] = "1; mode=block"
        response.headers["Referrer-Policy"] = "strict-origin-when-cross-origin"
        
        # Content Security Policy for API endpoints
        if request.url.path.startswith("/api/"):
            response.headers["Content-Security-Policy"] = "default-src 'none'"
        
        return response


def setup_middleware(app: FastAPI) -> None:
    """Configure all middleware for the application."""
    
    # Security headers middleware
    app.add_middleware(SecurityHeadersMiddleware)
    
    # Request tracking middleware
    app.add_middleware(RequestTrackingMiddleware)
    
    # Rate limiting middleware (if enabled)
    if settings.enable_rate_limiting:
        app.state.limiter = limiter
        app.add_middleware(SlowAPIMiddleware)
        
        # Custom rate limit exceeded handler
        @app.exception_handler(RateLimitExceeded)
        async def rate_limit_handler(request: Request, exc: RateLimitExceeded):
            request_id = getattr(request.state, 'request_id', 'unknown')
            logger.warning(
                f"Rate limit exceeded",
                extra={
                    "request_id": request_id,
                    "client_ip": get_remote_address(request),
                    "path": request.url.path,
                }
            )
            raise RateLimitError("Rate limit exceeded. Please try again later.")


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan context manager."""
    # Startup
    logger.info("ESG Portfolio Analytics API starting up...")
    
    # Validate configuration
    from backend.config import validate_configuration
    try:
        validate_configuration()
        logger.info("Configuration validation passed")
    except Exception as e:
        logger.error(f"Configuration validation failed: {e}")
        raise
    
    yield
    
    # Shutdown
    logger.info("ESG Portfolio Analytics API shutting down...")
    logger.info(f"Total requests processed: {request_stats['total_requests']}")


def get_request_stats() -> Dict[str, Any]:
    """Get current request statistics."""
    return request_stats.copy()