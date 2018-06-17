package com.search.service.app;

import com.search.service.SearchService;
import com.search.service.SearchServiceImpl;
import com.search.service.document.DocumentService;
import com.search.service.document.DocumentServiceImpl;
import com.search.service.parse.ParseService;
import com.search.service.parse.ParseServiceSimpleImpl;
import com.search.service.token.TokenService;
import com.search.service.token.TokenServiceConcurrentImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchServiceConfiguration {

    @Bean
    public DocumentService documentService() {
        return new DocumentServiceImpl();
    }

    @Bean
    public ParseService parseService() {
        return new ParseServiceSimpleImpl();
    }

    @Bean
    public TokenService tokenService() {
        return new TokenServiceConcurrentImpl();
    }

    @Bean
    public SearchService searchService() {
        return new SearchServiceImpl(documentService(), parseService(), tokenService());
    }
}
