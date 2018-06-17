package com.search.client;

public class SearchServiceClientException extends RuntimeException {

    private final int code;

    public SearchServiceClientException(final String message, final int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
