package com.search.service.app;

import com.search.service.SearchDocument;
import com.search.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "engine", consumes = "application/json", produces = "application/json")
public class SearchServiceEndpoint implements SearchService {

    private final SearchService searchService;

    public SearchServiceEndpoint(final SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("documents")
    @Override
    public void addDocument(@RequestBody final SearchDocument document) {
        searchService.addDocument(document);
    }

    @GetMapping("documents/{key}")
    @Override
    public SearchDocument getDocument(@PathVariable("key") final String key) {
        return searchService.getDocument(key);
    }

    @GetMapping("search")
    @Override
    public List<String> search(@RequestParam String query) {
        return searchService.search(query);
    }
}
