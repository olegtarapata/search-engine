package com.search.service.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ParseServiceSpaceImpl implements ParseService {

    private static final String DELIMITER = " ";

    private static final Pattern PATTERN = Pattern.compile(DELIMITER);

    @Override
    public List<String> parse(final String document) {
        if (document == null || document.isEmpty()) {
            Collections.emptyList();
        }
        final String[] tokens = PATTERN.split(document);
        if (tokens.length == 0) {
            return Collections.emptyList();
        }
        final List<String> result = new ArrayList<>(tokens.length);
        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].trim().isEmpty()) {
                result.add(tokens[i]);
            }
        }
        return result;
    }
}
