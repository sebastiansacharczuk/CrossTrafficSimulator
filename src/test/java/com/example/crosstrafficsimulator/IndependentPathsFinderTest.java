package com.example.crosstrafficsimulator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IndependentPathsFinderTest {

    @Test
    void testIntersects() {
        assertTrue(IndependentPathsFinder.intersects(List.of(1, 2, 3), List.of(1, 4, 2)));
        assertTrue(IndependentPathsFinder.intersects(List.of(1, 2, 3), List.of(1, 3, 5)));

        assertFalse(IndependentPathsFinder.intersects(List.of(1, 2, 3), List.of(1, 4, 5)));
        assertFalse(IndependentPathsFinder.intersects(List.of(1, 2, 3), List.of(2, 4, 6)));
    }

    @Test
    void testIsIndependent() {
        List<List<Integer>> allPaths = List.of(
                List.of(1, 2, 3),
                List.of(3, 2, 5),
                List.of(6, 7, 8)
        );

        assertFalse(IndependentPathsFinder.isIndependent(List.of(0, 1), allPaths)); // Ścieżki się przecinają

        assertTrue(IndependentPathsFinder.isIndependent(List.of(0, 2), allPaths));
    }

    @Test
    void testFindMaximalIndependentSets() {
        List<List<Integer>> allPaths = List.of(
                List.of(3, 5, 6, 10),
                List.of(1, 5, 7, 11),
                List.of(2, 6, 8, 12),
                List.of(4, 6, 9, 13),
                List.of(14, 9, 15, 16)
        );

        List<List<Integer>> independentSets = IndependentPathsFinder.findMaximalIndependentSets(allPaths);
        assertNotNull(independentSets);
        assertFalse(independentSets.isEmpty());
        for(List<Integer> independentSet : independentSets) {
            assertTrue(independentSet.size() < allPaths.size());
            assertFalse(independentSet.isEmpty());
        }
    }
}