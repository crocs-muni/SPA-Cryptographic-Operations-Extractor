/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.similaritysearch.strategies;

import java.util.SortedSet;
import java.util.TreeSet;
import cz.muni.fi.sbapr.similaritysearch.Similarity;

/**
 *
 * @author Martin
 */
public class WithinThresholdStrategy implements SimilaritySearchStrategy {
    private final SortedSet<Similarity> similarities;
    private final double threshold;
    private final int stopAfter;
    
    public WithinThresholdStrategy(double threshold, int stopAfter) {
        this.threshold = threshold;
        this.stopAfter = stopAfter;
        this.similarities = new TreeSet<>();
    }
    
    @Override
    public void addSimilarity(Similarity similarity) {
        if (isWithinDistance(similarity) && !isFull()) 
            similarities.add(similarity);
    }
    
    private boolean isFull() {
        return similarities.size() >= stopAfter;
    }
    
    private boolean isWithinDistance(Similarity similarity) {
        return similarity.getDistance() <= threshold;
    }
    
    @Override
    public SortedSet<Similarity> getSimilarities() {
        return similarities;
    }
}
