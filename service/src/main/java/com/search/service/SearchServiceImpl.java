package com.search.service;

import com.search.service.document.DocumentService;
import com.search.service.parse.ParseService;
import com.search.service.token.TokenService;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private final DocumentService documentService;

    private final ParseService parseService;

    private final TokenService tokenService;

    public SearchServiceImpl(final DocumentService documentService, final ParseService parseService,
                             final TokenService tokenService) {
        this.documentService = documentService;
        this.parseService = parseService;
        this.tokenService = tokenService;
    }

    @Override
    public void addDocument(final SearchDocument document) {
        System.out.println("Doc key = " + document.getKey());
        System.out.println("Doc content = " + document.getContent());
        this.documentService.add(document.getKey(), document.getContent());
        final List<String> tokens = this.parseService.parse(document.getContent());
        System.out.println("SearchDocument tokens " + tokens);
        this.tokenService.addDocument(document.getKey(), tokens);
    }

    @Override
    public SearchDocument getDocument(final String key) {
        return new SearchDocument(key, this.documentService.get(key));
    }

    @Override
    public List<String> search(final String query) {
        final List<String> queryTokens = parseService.parse(query);
        System.out.println("Query tokens " + queryTokens);
        final List<String> searchResult = this.tokenService.search(queryTokens);
        System.out.println("Search result " + searchResult);
        return searchResult;
    }
}
