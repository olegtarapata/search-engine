package com.search.service;

import java.util.List;

public interface SearchService {

    void addDocument(String key, String content);

    String getDocument(String key);

    List<String> search(String query);
}
