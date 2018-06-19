package com.search.service;

/**
 * Search document.
 */
public class SearchDocument {

    private String key;

    private String content;

    public SearchDocument() {
        // used by frameworks.
    }

    public SearchDocument(final String key, final String content) {
        this.key = key;
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
