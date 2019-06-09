/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbapr.ssp.similaritysearch.strategies;

import java.util.SortedSet;
import java.util.TreeSet;
import javafx.util.Pair;
import sbapr.ssp.similaritysearch.Similarity;

/**
 *
 * @author Martin
 */
public class TopNStrategy implements SimilaritySearchStrategy {
    private final SortedSet<Similarity> similarities;
    private final int topNOccurences;
    
    public TopNStrategy(int topNOccurences) {
        this.topNOccurences = topNOccurences;
        this.similarities = new TreeSet<>((Similarity s1, Similarity s2) -> {
            if (s1.getDistance() == s2.getDistance()) return 0;
            if (s1.getDistance() > s2.getDistance()) return 1;
            else return -1;            
        });
    }
    
    @Override
    public void addSimilarity(Similarity similarity) {
        if (similarities.isEmpty()) {
            similarities.add(similarity);
        }
        
        if (similarity.getDistance() < similarities.last().getDistance() || !isFull())
        {
            Pair<Boolean, Similarity> overlapping = isOverlapping(similarity);
            if (overlapping.getKey() && overlapping.getValue().getDistance() > similarity.getDistance()) {
                similarities.remove(overlapping.getValue());
                similarities.add(similarity);
            } else if (!overlapping.getKey() && isFull()) {
                similarities.remove(similarities.last());                
                similarities.add(similarity);
            } else if (!overlapping.getKey() && !isFull()) {
                similarities.add(similarity);
            }
        }
    }
    
    private boolean isFull() {
        return similarities.size() >= topNOccurences;
    }
    
    private Pair<Boolean, Similarity> isOverlapping(Similarity similarityToAdd) {
        for (Similarity similarity : similarities) {
            if (similarity.getLastIndex() > similarityToAdd.getFirstIndex()) return new Pair(true, similarity);
        }
        return new Pair(false, null);
    }
    
    @Override
    public SortedSet<Similarity> getSimilarities() {
        return similarities;
    } 
}
