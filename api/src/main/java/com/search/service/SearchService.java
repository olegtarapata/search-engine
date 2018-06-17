package com.search.service;

import java.util.List;

public interface SearchService {

    void addDocument(SearchDocument document);

    SearchDocument getDocument(String key);

    List<String> search(String query);
}
