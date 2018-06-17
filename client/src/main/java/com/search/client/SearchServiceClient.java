package com.search.client;

import com.search.service.SearchDocument;
import com.search.service.SearchService;

import java.util.List;

public class SearchServiceClient implements SearchService {

    @Override
    public void addDocument(final SearchDocument document) {

    }

    @Override
    public SearchDocument getDocument(final String key) {
        return null;
    }

    @Override
    public List<String> search(final String query) {
        return null;
    }
}
