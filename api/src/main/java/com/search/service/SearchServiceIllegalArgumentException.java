package com.search.service;

/**
 * {@link SearchService} illegal argument exception.
 */
public class SearchServiceIllegalArgumentException extends SearchServiceException {

    public SearchServiceIllegalArgumentException(final String message) {
        super(message);
    }

    public SearchServiceIllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
