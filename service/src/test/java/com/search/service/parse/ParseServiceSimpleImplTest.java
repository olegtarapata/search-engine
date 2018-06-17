package com.search.service.parse;

import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ParseServiceSimpleImplTest {

    @Test
    public void parseEmptyLine() {
        final ParseServiceSimpleImpl parserService = new ParseServiceSimpleImpl();
        assertThat(parserService.parse(""), is(empty()));
    }

    @Test
    public void parseBlankLine() {
        final ParseServiceSimpleImpl parserService = new ParseServiceSimpleImpl();
        assertThat(parserService.parse("   "), is(empty()));
    }

    @Test
    public void parseUsualDocument() {
        final ParseServiceSimpleImpl parserService = new ParseServiceSimpleImpl();
        assertThat(parserService.parse("a b c f\ng"), is(contains("a", "b", "c", "f", "g")));
    }
}