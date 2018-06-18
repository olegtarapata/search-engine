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
        final boolean result = this.documentService.add(document.getKey(), document.getContent());
        if (!result) {
            throw new DocumentAlreadyExistsException(document.getKey());
        }
        final List<String> tokens = this.parseService.parse(document.getContent());
        this.tokenService.addDocument(document.getKey(), tokens);
    }

    @Override
    public SearchDocument getDocument(final String key) {
        final String content = this.documentService.get(key);
        if (content == null) {
            throw new DocumentNotExistException(key);
        }
        return new SearchDocument(key, content);
    }

    @Override
    public List<String> search(final String query) {
        final List<String> queryTokens = parseService.parse(query);
        return this.tokenService.search(queryTokens);
    }
}
