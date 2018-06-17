package com.search.service.token;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public final class IntersectionUtil {

    private IntersectionUtil() {
        throw new IllegalAccessError("Should not be instantiated");
    }

    public static SortedSet<String> intersection(final SortedSet<String> set1, final SortedSet<String> set2) {
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
        if (iterator1.hasNext() && element2 != null) {
            while (iterator1.hasNext()) {
                element1 = iterator1.next();
                final int compare = element1.compareTo(element2);
                if (compare == 0) {
                    result.add(element1);
                    break;
                }
                if (compare > 0) {
                    break;
                }
            }
        }
        if (iterator2.hasNext() && element1 != null) {
            while (iterator2.hasNext()) {
                element2 = iterator2.next();
                final int compare = element1.compareTo(element2);
                if (compare == 0) {
                    result.add(element2);
                    break;
                }
                if (compare < 0) {
                    break;
                }
            }
        }
        return result;
    }

}
