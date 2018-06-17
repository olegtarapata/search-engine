package com.search.service.document;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DocumentServiceImpl implements DocumentService {

    private final Map<String, String> storage = new ConcurrentHashMap<>();

    @Override
    public boolean add(final String key, final String document) {
        final String currentDocument = storage.putIfAbsent(key, document);
        return currentDocument == null;
    }

    @Override
    public String get(final String key) {
        return storage.get(key);
    }
}
