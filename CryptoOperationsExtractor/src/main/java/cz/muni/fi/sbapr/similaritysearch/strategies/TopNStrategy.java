/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.similaritysearch.strategies;

import java.util.SortedSet;
import java.util.TreeSet;
import cz.muni.fi.sbapr.similaritysearch.Similarity;
import cz.muni.fi.sbapr.util.OverlappingPair;

/**
 *
 * @author Martin
 */
public class TopNStrategy implements SimilaritySearchStrategy {
    public static final int TOP_N_STRATEGY_COUNT = 6;
    
    private final SortedSet<Similarity> similarities;
    private final int topNOccurences;
    
    public TopNStrategy(int topNOccurences) {
        this.topNOccurences = topNOccurences;
        this.similarities = new TreeSet<>();
    }
    
    @Override
    public void addSimilarity(Similarity similarity) {
        if (similarities.isEmpty()) {
            similarities.add(similarity);
            return;
        }
        
        if (similarity.getDistance() < similarities.last().getDistance() || !isFull())
        {
            OverlappingPair<Boolean, Similarity> overlapping = isOverlapping(similarity);
            if (overlapping.getFirstValue()&& overlapping.getSecondValue().getDistance() > similarity.getDistance()) {
                similarities.remove(overlapping.getSecondValue());
                similarities.add(similarity);
                return;
            }
            if (!overlapping.getFirstValue() && isFull()) {
                similarities.remove(similarities.last());                
                similarities.add(similarity);
                return;
            }
            if (!overlapping.getFirstValue() && !isFull()) {
                similarities.add(similarity);
            }
        }
    }
    
    private boolean isFull() {
        return similarities.size() >= topNOccurences;
    }
    
    private OverlappingPair<Boolean, Similarity> isOverlapping(Similarity similarityToAdd) {
        for (Similarity similarity : similarities) {
            if (similarity.getLastIndex() > similarityToAdd.getFirstIndex() && similarity.getFirstIndex() < similarityToAdd.getLastIndex()) 
                return new OverlappingPair(true, similarity);
        }
        return new OverlappingPair(false, null);
    }
    
    @Override
    public SortedSet<Similarity> getSimilarities() {
        return similarities;
    } 
}
