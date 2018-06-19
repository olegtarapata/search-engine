package com.search.service;

import java.util.List;

/**
 * Search service api.
 */
public interface SearchService {

    /**
     * Add document to search service.
     * Document content is tokens separated by space.
     *
     * @param document new document
     * @throws SearchServiceIllegalArgumentException if key or content is empty
     * @throws DocumentAlreadyExistsException        if document with given key already exists
     * @throws SearchServiceException                if unexpected runtime exception occurred
     */
    void addDocument(SearchDocument document);

    /**
     * Get document by key.
     *
     * @param key document key
     * @return document
     * @throws SearchServiceIllegalArgumentException if key is empty
     * @throws DocumentAlreadyExistsException        if document with given key does not exist
     * @throws SearchServiceException                if unexpected runtime exception occurred
     */
    SearchDocument getDocument(String key);

    /**
     * Search document keys by query.
     * Query is tokens separated by space.
     * Return all documents that contain all tokens from query.
     *
     * @param query search query
     * @return document keys
     * @throws SearchServiceIllegalArgumentException if query is empty
     * @throws SearchServiceException                if unexpected runtime exception occurred
     */
    List<String> search(String query);
}
