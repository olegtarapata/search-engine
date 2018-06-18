package com.search.service;

/**
 * Document already exists exception.
 */
public class DocumentAlreadyExistsException extends SearchServiceException {

    public DocumentAlreadyExistsException(final String documentKey) {
        super("Document already exists with key: " + documentKey);
    }
}
