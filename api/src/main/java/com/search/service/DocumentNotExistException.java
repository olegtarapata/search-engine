package com.search.service;

/**
 * {@link SearchService} document not exist exception.
 */
public class DocumentNotExistException extends SearchServiceException {

    public static final String DOCUMENT_DOES_NOT_EXIST_WITH_KEY = "Document does not exist with key: ";

    public DocumentNotExistException(final String documentKey) {
        super(DOCUMENT_DOES_NOT_EXIST_WITH_KEY + documentKey);
    }
}
