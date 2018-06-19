package com.search.service;

import com.search.service.app.SearchServiceConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SearchServiceImplTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addAndGetDocument() {
        final SearchService searchService = new SearchServiceConfiguration().searchService();
        final String key = randomUUID().toString();
        final String content = randomUUID().toString();
        searchService.addDocument(new SearchDocument(key, content));
        final SearchDocument gotDocument = searchService.getDocument(key);
        assertThat(gotDocument.getKey(), is(key));
        assertThat(gotDocument.getContent(), is(content));
    }

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

    @Test
    public void addSeveralDocumentsAndSearch() {
        final SearchService searchService = new SearchServiceConfiguration().searchService();
        searchService.addDocument(new SearchDocument("1", "a b c"));
        searchService.addDocument(new SearchDocument("2", "a b"));
        searchService.addDocument(new SearchDocument("3", "a b d"));

        assertThat(searchService.search("a"), is(containsInAnyOrder("1", "2", "3")));
        assertThat(searchService.search("b"), is(containsInAnyOrder("1", "2", "3")));
        assertThat(searchService.search("c"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("a b"), is(containsInAnyOrder("1", "2", "3")));
        assertThat(searchService.search("a c"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("a b c"), is(containsInAnyOrder("1")));
        assertThat(searchService.search("d"), is(containsInAnyOrder("3")));
        assertThat(searchService.search("a d"), is(containsInAnyOrder("3")));
        assertThat(searchService.search("a c d"), is(empty()));
    }

    @Test
    public void addDocumentWithEmptyKey() {
        final SearchService searchService = new SearchServiceConfiguration().searchService();
        expectedException.expect(SearchServiceIllegalArgumentException.class);
        expectedException.expectMessage(SearchServiceImpl.DOCUMENT_KEY_IS_EMPTY);
        searchService.addDocument(new SearchDocument(" ", randomUUID().toString()));
    }

    @Test
    public void addDocumentWithEmptyContent() {
        final SearchService searchService = new SearchServiceConfiguration().searchService();
        expectedException.expect(SearchServiceIllegalArgumentException.class);
        expectedException.expectMessage(SearchServiceImpl.DOCUMENT_CONTENT_IS_EMPTY);
        searchService.addDocument(new SearchDocument(randomUUID().toString(), null));
    }

    @Test
    public void searchWithEmptyQuery() {
        final SearchService searchService = new SearchServiceConfiguration().searchService();
        expectedException.expect(SearchServiceIllegalArgumentException.class);
        expectedException.expectMessage(SearchServiceImpl.QUERY_IS_EMPTY);
        searchService.search(null);
    }

    @Test
    public void getNotExistedDocument() {
        final SearchService searchService = new SearchServiceConfiguration().searchService();
        final String key = randomUUID().toString();
        expectedException.expect(DocumentNotExistException.class);
        expectedException.expectMessage(DocumentNotExistException.DOCUMENT_DOES_NOT_EXIST_WITH_KEY + key);
        searchService.getDocument(key);
    }

    @Test
    public void addDocumentWithKeyThatAlreadyExists() {
        final SearchService searchService = new SearchServiceConfiguration().searchService();
        final String key = randomUUID().toString();
        searchService.addDocument(new SearchDocument(key, randomUUID().toString()));
        expectedException.expect(DocumentAlreadyExistsException.class);
        expectedException.expectMessage(DocumentAlreadyExistsException.DOCUMENT_ALREADY_EXISTS_WITH_KEY + key);
        searchService.addDocument(new SearchDocument(key, randomUUID().toString()));
    }
}
