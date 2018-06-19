package com.search.service.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.search.service.token.IntersectionUtil.intersection;

public final class TokenServiceConcurrentImpl implements TokenService {

    private final Map<String, SortedSet<String>> tokenToKeys = new ConcurrentHashMap<>();

    @Override
    public void addDocument(final String key, final List<String> tokens) {
        for (String token : tokens) {
            tokenToKeys.computeIfAbsent(token, mapKey -> new ConcurrentSkipListSet<>()).add(key);
        }
    }

    @Override
    public List<String> search(final List<String> tokens) {
        if (tokens.isEmpty()) {
            return Collections.emptyList();
        }
        SortedSet<String> result = null;
        for (String token : tokens) {
            final SortedSet<String> keysSet = tokenToKeys.getOrDefault(token, Collections.emptySortedSet());
            if (keysSet.isEmpty()) {
                return Collections.emptyList();
            }
            if (result == null) {
                result = keysSet;
            } else {
                result = intersection(result, keysSet);
                if (result.isEmpty()) {
                    return Collections.emptyList();
                }
            }
        }
        return new ArrayList<>(result);
    }
}
