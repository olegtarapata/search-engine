package com.search.service;

import com.search.service.document.DocumentService;
import com.search.service.parse.ParseService;
import com.search.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private final DocumentService documentService;

    private final ParseService parseService;

    private final TokenService tokenService;

    @Autowired
    public SearchServiceImpl(final DocumentService documentService, final ParseService parseService,
                             final TokenService tokenService) {
        this.documentService = documentService;
        this.parseService = parseService;
        this.tokenService = tokenService;
    }

    @Override
    public void addDocument(final String key, final String content) {
        this.documentService.add(key, content);
        final List<String> tokens = this.parseService.parse(content);
        this.tokenService.addDocument(key, tokens);
    }

    @Override
    public String getDocument(final String key) {
        return this.documentService.get(key);
    }

    @Override
    public List<String> search(final String query) {
        return this.tokenService.search(parseService.parse(query));
    }
}
