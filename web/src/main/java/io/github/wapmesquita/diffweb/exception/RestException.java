package io.github.wapmesquita.diffweb.exception;

public class RestException extends RuntimeException {

    private final int statusCode;

    public RestException(int statusCode, String message, Exception e) {
        super(message, e);
        this.statusCode = statusCode;
    }

    public RestException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
