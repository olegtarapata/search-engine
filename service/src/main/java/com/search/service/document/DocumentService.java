package com.search.service.document;

public interface DocumentService {

    boolean add(String key, String content);

    String get(String key);
}
