package com.search.service.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public final class TokenServiceConcurrentImpl implements TokenService {

    private final Map<String, SortedSet<String>> tokenToKeys = new ConcurrentHashMap<>();

    static SortedSet<String> intersection(final SortedSet<String> set1, final SortedSet<String> set2) {
        final SortedSet<String> result = new TreeSet<>();
        final Iterator<String> iterator1 = set1.iterator();
        final Iterator<String> iterator2 = set2.iterator();
        String element1 = null;
        String element2 = null;
        while (iterator1.hasNext() && iterator2.hasNext()) {
            if (element1 == null) {
                element1 = iterator1.next();
            }
            if (element2 == null) {
                element2 = iterator2.next();
            }
            final int compare = element1.compareTo(element2);
            if (compare == 0) {
                result.add(element1);
                element1 = null;
                element2 = null;
            } else {
                if (compare < 0) {
                    element1 = null;
                } else {
                    element2 = null;
                }
            }
        }
        return result;
    }

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
