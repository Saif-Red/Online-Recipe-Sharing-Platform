package com.recipehub.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Small asynchronous counter used for contributor statistics.
 * The real database write can be plugged in later; the class is kept
 * separate so view tracking does not slow down page rendering.
 */
public class StatsService {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final Map<Integer, Integer> inMemoryViews = new ConcurrentHashMap<>();

    public void registerView(int recipeId) {
        executor.submit(() ->
                inMemoryViews.merge(recipeId, 1, Integer::sum)
        );
    }

    public int getLiveViewCount(int recipeId) {
        return inMemoryViews.getOrDefault(recipeId, 0);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
