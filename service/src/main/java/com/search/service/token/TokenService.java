package com.search.service.token;

import java.util.List;

public interface TokenService {

    void addDocument(String key, List<String> tokens);

    List<String> search(List<String> tokens);
}
