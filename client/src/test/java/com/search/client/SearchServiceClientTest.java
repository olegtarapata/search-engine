package com.search.client;

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

import static java.util.UUID.randomUUID;

public class SearchServiceClientTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addDocument() throws IOException {
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
}