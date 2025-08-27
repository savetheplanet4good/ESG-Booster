"""
Custom exceptions for ESG Portfolio Analytics.

Provides structured error handling with proper HTTP status codes and error details.
"""
from typing import Any, Dict, Optional
from fastapi import HTTPException, status


class ESGException(HTTPException):
    """Base exception class for ESG application errors."""
    
    def __init__(
        self,
        detail: str,
        status_code: int = status.HTTP_500_INTERNAL_SERVER_ERROR,
        headers: Optional[Dict[str, Any]] = None,
        error_code: Optional[str] = None,
    ):
        super().__init__(status_code=status_code, detail=detail, headers=headers)
        self.error_code = error_code or self.__class__.__name__


class ValidationError(ESGException):
    """Raised when input validation fails."""
    
    def __init__(self, detail: str, field: Optional[str] = None):
        super().__init__(
            detail=detail,
            status_code=status.HTTP_400_BAD_REQUEST,
            error_code="VALIDATION_ERROR"
        )
        self.field = field


class DataError(ESGException):
    """Raised when there are issues with data processing or availability."""
    
    def __init__(self, detail: str, data_type: Optional[str] = None):
        super().__init__(
            detail=detail,
            status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
            error_code="DATA_ERROR"
        )
        self.data_type = data_type


class NotFoundError(ESGException):
    """Raised when requested resource is not found."""
    
    def __init__(self, detail: str, resource_type: Optional[str] = None):
        super().__init__(
            detail=detail,
            status_code=status.HTTP_404_NOT_FOUND,
            error_code="NOT_FOUND_ERROR"
        )
        self.resource_type = resource_type


class ComputeError(ESGException):
    """Raised when computation or optimization fails."""
    
    def __init__(self, detail: str, computation_type: Optional[str] = None):
        super().__init__(
            detail=detail,
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            error_code="COMPUTE_ERROR"
        )
        self.computation_type = computation_type


class ConfigurationError(ESGException):
    """Raised when there are configuration issues."""
    
    def __init__(self, detail: str):
        super().__init__(
            detail=detail,
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            error_code="CONFIGURATION_ERROR"
        )


class RateLimitError(ESGException):
    """Raised when rate limit is exceeded."""
    
    def __init__(self, detail: str = "Rate limit exceeded"):
        super().__init__(
            detail=detail,
            status_code=status.HTTP_429_TOO_MANY_REQUESTS,
            error_code="RATE_LIMIT_ERROR"
        )


class FileProcessingError(ESGException):
    """Raised when file processing fails."""
    
    def __init__(self, detail: str, file_type: Optional[str] = None):
        super().__init__(
            detail=detail,
            status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
            error_code="FILE_PROCESSING_ERROR"
        )
        self.file_type = file_type