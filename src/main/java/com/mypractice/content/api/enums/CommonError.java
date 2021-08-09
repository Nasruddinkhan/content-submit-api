package com.mypractice.content.api.enums;

public enum CommonError {
    VALIDATION_ERROR("Invalid request - see details", 400),
    UNAUTHORIZED("The requested resource requires authentication", 401),
    FORBIDDEN("No permission for the requested operation", 403),
    INVALID_RESOURCE_ID("The requested resource ID was not found", 404),
    METHOD_NOT_SUPPORTED("The server does not implement the requested HTTP method.", 405),
    NOT_ACCEPTABLE("The requested media type is not supported", 406),
    CONFLICT("The server has detected a conflict while processing this request.", 409),
    PRECONDITION_FAILED("Update failed. Resource has changed on the server.", 412),
    UNSUPPORTED_MEDIA_TYPE("The request payload is not supported", 415),
    MISSING_PRECONDITION_HEADER("This request must be conditional and requires a If-Match header.", 428),
    RATE_LIMIT_REACHED("Too many requests. Blocked due to rate limiting", 429),
    UNPROCESSABLE_ENTITY("The requested action cannot be performed, and may require interaction with APIs or processes outside of the current request", 422),
    INTERNAL_SERVICE_ERROR("An internal service error has occurred", 500),
    SERVICE_UNAVAILABLE("The server is not able to handle your request at this time", 503);
    private String message;
    private int statusCode;

    CommonError(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static class CommonErrorBuilder {
        public static CommonError fromStatus(int status) {
            for (CommonError commonError : values()) {
                if (commonError.statusCode == status) {
                    return commonError;
                }
            }
            CommonError error = null;
            if (status >= 400 && status < 500) {
                error = VALIDATION_ERROR;
            } else if (status >= 500 && status < 600) {
                error = INTERNAL_SERVICE_ERROR;
            }
            return error;
        }
    }
}
