package com.search.service;

import com.search.service.document.DocumentService;
import com.search.service.exceptions.MvcDocumentAlreadyExistsException;
import com.search.service.exceptions.MvcDocumentNotExistException;
import com.search.service.exceptions.MvcSearchServiceIllegalArgumentException;
import com.search.service.parse.ParseService;
import com.search.service.token.TokenService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * {@link SearchService} implementation.
 */
public class SearchServiceImpl implements SearchService {

    static final String DOCUMENT_KEY_IS_EMPTY = "document key is empty";

    static final String DOCUMENT_CONTENT_IS_EMPTY = "document content is empty";

    static final String QUERY_IS_EMPTY = "query is empty";

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
        if (!StringUtils.hasText(document.getKey())) {
            throw new MvcSearchServiceIllegalArgumentException(DOCUMENT_KEY_IS_EMPTY);
        }
        if (!StringUtils.hasText(document.getContent())) {
            throw new MvcSearchServiceIllegalArgumentException(DOCUMENT_CONTENT_IS_EMPTY);
        }
        final boolean result = this.documentService.add(document.getKey(), document.getContent());
        if (!result) {
            throw new MvcDocumentAlreadyExistsException(document.getKey());
        }
        final List<String> tokens = this.parseService.parse(document.getContent());
        this.tokenService.addDocument(document.getKey(), tokens);
    }

    @Override
    public SearchDocument getDocument(final String key) {
        final String content = this.documentService.get(key);
        if (content == null) {
            throw new MvcDocumentNotExistException(key);
        }
        return new SearchDocument(key, content);
    }

    @Override
    public List<String> search(final String query) {
        if (!StringUtils.hasText(query)) {
            throw new MvcSearchServiceIllegalArgumentException(QUERY_IS_EMPTY);
        }
        final List<String> queryTokens = parseService.parse(query);
        return this.tokenService.search(queryTokens);
    }
}
