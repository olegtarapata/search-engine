package com.search.service.token;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TokenServiceConcurrentImplTest {

    @Test
    public void addSeveralDocumentsAndSearch() {
        final TokenService tokenService = new TokenServiceConcurrentImpl();
        tokenService.addDocument("1", asList("a", "b", "c"));
        tokenService.addDocument("2", asList("a", "b"));
        tokenService.addDocument("3", singletonList("a"));
        tokenService.addDocument("4", asList("b", "c"));

        assertThat(tokenService.search(asList("a", "b")), is(containsInAnyOrder("1", "2")));
        assertThat(tokenService.search(asList("a")), is(containsInAnyOrder("1", "2", "3")));
        assertThat(tokenService.search(asList("b")), is(containsInAnyOrder("1", "2", "4")));
        assertThat(tokenService.search(asList("c")), is(containsInAnyOrder("1", "4")));
        assertThat(tokenService.search(asList("b", "c")), is(containsInAnyOrder("1", "4")));
        assertThat(tokenService.search(asList("a", "c")), is(containsInAnyOrder("1")));
        assertThat(tokenService.search(asList("a", "b", "c")), is(containsInAnyOrder("1")));
        assertThat(tokenService.search(asList("a", "b", "c", "d")), is(empty()));
        assertThat(tokenService.search(asList("d")), is(empty()));
    }

    @Test
    public void addOneDocumentAndSearch() {
        final TokenService tokenService = new TokenServiceConcurrentImpl();
        tokenService.addDocument("1", asList("a", "b", "c"));

        assertThat(tokenService.search(asList("a")), is(containsInAnyOrder("1")));
        assertThat(tokenService.search(asList("b")), is(containsInAnyOrder("1")));
        assertThat(tokenService.search(asList("c")), is(containsInAnyOrder("1")));
        assertThat(tokenService.search(asList("a", "b")), is(containsInAnyOrder("1")));
        assertThat(tokenService.search(asList("a", "c")), is(containsInAnyOrder("1")));
        assertThat(tokenService.search(asList("a", "b", "c")), is(containsInAnyOrder("1")));
    }
}