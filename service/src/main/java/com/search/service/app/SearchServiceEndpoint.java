package com.search.service.app;

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

    @PostMapping("documents/{key}")
    @Override
    public void addDocument(@PathVariable("key") final String key, @RequestBody final String content) {
        searchService.addDocument(key, content);
    }

    @GetMapping("documents/{key}")
    @Override
    public String getDocument(@PathVariable("key") final String key) {
        return searchService.getDocument(key);
    }

    @GetMapping("search")
    @Override
    public List<String> search(@RequestParam String query) {
        return searchService.search(query);
    }
}
