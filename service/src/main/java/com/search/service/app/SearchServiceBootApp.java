package com.search.service.app;

import com.search.service.SearchService;
import com.search.service.SearchServiceImpl;
import com.search.service.document.DocumentService;
import com.search.service.document.DocumentServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SearchServiceBootApp {

    public static void main(String[] args) {
        SpringApplication.run(SearchServiceBootApp.class, args);
    }

    @Bean
    public DocumentService documentService() {
        return new DocumentServiceImpl();
    }

    @Bean
    public SearchService searchService() {
        return new SearchServiceImpl(documentService());
    }
}
