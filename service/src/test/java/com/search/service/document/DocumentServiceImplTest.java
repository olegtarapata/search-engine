package com.search.service.document;

import org.junit.Test;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class DocumentServiceImplTest {

    @Test
    public void addDocumentSeveralTimes() {
        final DocumentService documentService = new DocumentServiceImpl();
        final String key = randomUUID().toString();
        assertThat(documentService.add(key, randomUUID().toString()), is(true));
        assertThat(documentService.add(key, randomUUID().toString()), is(false));
    }

    @Test
    public void getNotExistedDocument() {
        final DocumentService documentService = new DocumentServiceImpl();
        assertThat(documentService.get(randomUUID().toString()), is(nullValue()));
    }
}