package com.search.client;

import com.search.service.SearchServiceException;

public class SearchServiceClientException extends SearchServiceException {

    private final int code;

    public SearchServiceClientException(final String message, final int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
