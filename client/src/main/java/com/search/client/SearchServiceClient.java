package com.search.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.search.service.DocumentAlreadyExistsException;
import com.search.service.DocumentNotExistException;
import com.search.service.SearchDocument;
import com.search.service.SearchService;
import com.search.service.SearchServiceException;
import com.search.service.SearchServiceIllegalArgumentException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class SearchServiceClient implements SearchService {

    public static final String APPLICATION_JSON = "application/json";

    static final String DOCUMENTS_PATH = "/engine/documents";

    static final String SEARCH_PATH = "/engine/search";

    private static final String URL_FORMAT = "http://%s:%s";

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
            throw new SearchServiceIllegalArgumentException(e);
        }
        final Request request = new Request.Builder()
                .url(baseUrl + DOCUMENTS_PATH)
                .post(RequestBody.create(JSON_MEDIA_TYPE, documentString))
                .build();
        try (final Response response = client.newCall(request).execute()) {
            handleBadRequestCode(response);
            if (response.code() == 200) {
                return;
            }
            if (response.code() == 409) {
                throw new DocumentAlreadyExistsException(document.getKey());
            }
            throw new SearchServiceClientException(response.body().string(), response.code());
        } catch (final IOException e) {
            throw new SearchServiceException(e);
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
            handleBadRequestCode(response);
            if (response.code() == 200) {
                return objectMapper.readValue(response.body().byteStream(), SearchDocument.class);
            }
            if (response.code() == 404) {
                throw new DocumentNotExistException(key);
            }
            throw new SearchServiceClientException(response.body().string(), response.code());
        } catch (final IOException e) {
            throw new SearchServiceException(e);
        }
    }

    @Override
    public List<String> search(final String query) {
        final Request request = new Request.Builder()
                .url(baseUrl + SEARCH_PATH + "?query=" + query)
                .header("Content-Type", APPLICATION_JSON)
                .get()
                .build();
        try (final Response response = client.newCall(request).execute()) {
            handleBadRequestCode(response);
            if (response.code() == 200) {
                return objectMapper.readValue(response.body().byteStream(), listOfStrings);
            }
            throw new SearchServiceClientException(response.body().string(), response.code());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleBadRequestCode(final Response response) throws IOException {
        if (response.code() == 400) {
            final JsonNode responseJson = objectMapper.readTree(response.body().byteStream());
            throw new SearchServiceIllegalArgumentException(responseJson.get("message").asText());
        }
    }
}
