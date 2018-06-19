package com.search.service.app;

import com.search.client.SearchServiceClient;
import com.search.service.SearchDocument;
import com.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {SearchServiceBootApp.class})
public class SearchServiceBootAppTest {

    @LocalServerPort
    int port;

    @Test
    public void addSeveralDocumentsAndSearch() {
        final SearchService searchService = new SearchServiceClient("localhost", port);
        searchService.addDocument(new SearchDocument("1", "a b c"));
        searchService.addDocument(new SearchDocument("2", "a b"));
        searchService.addDocument(new SearchDocument("3", "a b d"));

        assertThat(searchService.search("a"), is(containsInAnyOrder("1", "2", "3")));
        assertThat(searchService.search("b"), is(containsInAnyOrder("1", "2", "3")));
        assertThat(searchService.search("a b"), is(containsInAnyOrder("1", "2", "3")));
        assertThat(searchService.search("a c"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("a c d"), is(empty()));

        assertThat(searchService.getDocument("1").getContent(), is("a b c"));
    }
}