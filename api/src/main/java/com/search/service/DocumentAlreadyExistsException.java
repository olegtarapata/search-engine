package com.search.service;

/**
 * Document already exists exception.
 */
public class DocumentAlreadyExistsException extends SearchServiceException {

    public
    static final String DOCUMENT_ALREADY_EXISTS_WITH_KEY = "Document already exists with key: ";

    public DocumentAlreadyExistsException(final String documentKey) {
        super(DOCUMENT_ALREADY_EXISTS_WITH_KEY + documentKey);
    }
}
