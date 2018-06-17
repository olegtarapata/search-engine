package com.search.service;

import com.search.service.app.SearchServiceConfiguration;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SearchServiceImplTest {

    @Test
    public void addOneDocumentAndSearch() {
        final SearchService searchService = new SearchServiceConfiguration().searchService();
        searchService.addDocument(new SearchDocument("1", "a b c"));

        assertThat(searchService.search("a"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("b"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("c"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("a b"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("a c"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("a b c"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("d"), is(empty()));
    }
}
