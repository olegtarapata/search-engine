package com.search.service.token;

import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySortedSet;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntersectionUtilTest {

    @Test
    public void intersectEmptySets() {
        assertThat(
                IntersectionUtil.intersection(emptySortedSet(), emptySortedSet()),
                is(empty())
        );
    }

    @Test
    public void intersectEmptyAndNotEmptySet() {
        assertThat(
                IntersectionUtil.intersection(emptySortedSet(), new TreeSet<>(singletonList("one"))),
                is(empty())
        );
    }

    @Test
    public void intersectTheSameSets() {
        final TreeSet<String> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(UUID.randomUUID().toString());
        }
        assertThat(IntersectionUtil.intersection(set, set), is(set));
    }

    @Test
    public void intersectDifferentSets() {
        final SortedSet<String> set1 = new TreeSet<>(asList("one", "two", "three", "five"));
        final SortedSet<String> set2 = new TreeSet<>(asList("two", "three", "four"));
        final SortedSet<String> result = new TreeSet<>(asList("two", "three"));
        assertThat(IntersectionUtil.intersection(set1, set2), is(result));
    }

    @Test
    public void intersectSetsWithAdditionalElementInTheMiddle() {
        final SortedSet<String> set1 = new TreeSet<>(asList("1", "2", "3", "4"));
        final SortedSet<String> set2 = new TreeSet<>(asList("1", "4"));
        assertThat(IntersectionUtil.intersection(set1, set2), is(set2));
        assertThat(IntersectionUtil.intersection(set2, set1), is(set2));
    }
}