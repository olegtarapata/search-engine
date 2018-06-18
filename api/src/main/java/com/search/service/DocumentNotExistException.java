package com.search.service;

public class DocumentNotExistException extends SearchServiceException {

    public DocumentNotExistException(final String documentKey) {
        super("Document does not exist with key: " + documentKey);
    }
}
