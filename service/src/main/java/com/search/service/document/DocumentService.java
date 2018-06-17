package com.search.service.document;

public interface DocumentService {

    boolean add(String key, String document);

    String get(String key);
}
