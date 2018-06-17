package com.search.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.search.service.SearchDocument;
import com.search.service.SearchService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class SearchServiceClient implements SearchService {

    public static final String APPLICATION_JSON = "application/json";

    public static final String DOCUMENTS_PATH = "documents";

    public static final String SEARCH_PATH = "search";

    private static final String URL_FORMAT = "http://%s:%s/engine/";

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse(APPLICATION_JSON);

    private final OkHttpClient client = new OkHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CollectionType listOfStrings = objectMapper.getTypeFactory()
            .constructCollectionType(List.class, String.class);

    private final String baseUrl;

    public SearchServiceClient(final String host, final int port) {
        this.baseUrl = String.format(URL_FORMAT, host, port);
    }

    @Override
    public void addDocument(final SearchDocument document) {
        final String documentString;
        try {
            documentString = objectMapper.writeValueAsString(document);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        final Request request = new Request.Builder()
                .url(baseUrl + DOCUMENTS_PATH)
                .post(RequestBody.create(JSON_MEDIA_TYPE, documentString))
                .build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new SearchServiceClientException(response.body().string(), response.code());
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SearchDocument getDocument(final String key) {
        final Request request = new Request.Builder()
                .url(baseUrl + DOCUMENTS_PATH + "/" + key)
                .header("Content-Type", APPLICATION_JSON)
                .get()
                .build();
        try (final Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().bytes(), SearchDocument.class);
            } else {
                throw new SearchServiceClientException(response.body().string(), response.code());
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> search(final String query) {
        final Request request = new Request.Builder()
                .url(baseUrl + SEARCH_PATH + "/query=" + query)
                .header("Content-Type", APPLICATION_JSON)
                .get()
                .build();
        try (final Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().bytes(), listOfStrings);
            } else {
                throw new SearchServiceClientException(response.body().string(), response.code());
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
