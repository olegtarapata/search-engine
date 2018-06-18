package com.search.service;

/**
 * Base {@link SearchService} exception.
 */
public class SearchServiceException extends RuntimeException {

    public SearchServiceException() {
    }

    public SearchServiceException(final String message) {
        super(message);
    }

    public SearchServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SearchServiceException(final Throwable cause) {
        super(cause);
    }
}
