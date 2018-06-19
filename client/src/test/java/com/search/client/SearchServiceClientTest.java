package com.search.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.service.DocumentAlreadyExistsException;
import com.search.service.SearchDocument;
import com.search.service.SearchService;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SearchServiceClientTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addDocumentAlreadyExist() throws IOException {
        try (final MockWebServer server = new MockWebServer()) {
            final String key = randomUUID().toString();
            server.setDispatcher(new Dispatcher() {
                @Override
                public MockResponse dispatch(RecordedRequest recordedRequest) {
                    final String method = recordedRequest.getMethod();
                    final String path = recordedRequest.getPath();
                    if (method.equals("POST") && path.equals(SearchServiceClient.DOCUMENTS_PATH)) {
                        return new MockResponse().setResponseCode(409).setBody("{}");
                    }
                    return new MockResponse().setResponseCode(500).setBody("{}");
                }
            });
            server.start();
            final SearchService client = new SearchServiceClient(server.getHostName(), server.getPort());
            expectedException.expect(DocumentAlreadyExistsException.class);
            expectedException.expectMessage(DocumentAlreadyExistsException.DOCUMENT_ALREADY_EXISTS_WITH_KEY + key);
            client.addDocument(new SearchDocument(key, randomUUID().toString()));
        }
    }

    @Test
    public void addAndGetDocument() throws IOException {
        try (final MockWebServer server = new MockWebServer()) {
            final String key = randomUUID().toString();
            final String content = randomUUID().toString();
            server.setDispatcher(new Dispatcher() {
                private String document;

                @Override
                public MockResponse dispatch(RecordedRequest recordedRequest) {
                    final String method = recordedRequest.getMethod();
                    final String path = recordedRequest.getPath();
                    if (method.equals("POST") && path.equals(SearchServiceClient.DOCUMENTS_PATH)) {
                        document = recordedRequest.getBody().readString(Charset.forName("UTF-8"));
                        return new MockResponse().setResponseCode(200).setBody("{}");
                    }
                    if (method.equals("GET") && path.equals(SearchServiceClient.DOCUMENTS_PATH + "/" + key)) {
                        return new MockResponse().setResponseCode(200).setBody(document);
                    }
                    return new MockResponse().setResponseCode(500).setBody("{}");
                }
            });
            server.start();
            final SearchService client = new SearchServiceClient(server.getHostName(), server.getPort());
            client.addDocument(new SearchDocument(key, content));
            final SearchDocument loadedDocument = client.getDocument(key);
            assertThat(loadedDocument.getKey(), is(key));
            assertThat(loadedDocument.getContent(), is(content));
        }
    }

    @Test
    public void search() throws IOException {
        try (final MockWebServer server = new MockWebServer()) {
            final String query = randomUUID().toString();
            final List<String> searchResult = Arrays.asList(randomUUID().toString(), randomUUID().toString());
            final String resultString = new ObjectMapper().writeValueAsString(searchResult);
            server.setDispatcher(new Dispatcher() {
                @Override
                public MockResponse dispatch(RecordedRequest recordedRequest) {
                    System.out.println(recordedRequest);
                    final String method = recordedRequest.getMethod();
                    final String path = recordedRequest.getPath();
                    if (method.equals("GET") && path.equals(SearchServiceClient.SEARCH_PATH + "?query=" + query)) {
                        return new MockResponse().setResponseCode(200).setBody(resultString);
                    }
                    return new MockResponse().setResponseCode(500).setBody("{}");
                }
            });
            server.start();
            final SearchService client = new SearchServiceClient(server.getHostName(), server.getPort());
            final List<String> result = client.search(query);
            assertThat(searchResult, is(result));
        }
    }
}