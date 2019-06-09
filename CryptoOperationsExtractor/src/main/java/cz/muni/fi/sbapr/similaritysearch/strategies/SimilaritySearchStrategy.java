/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.similaritysearch.strategies;

import java.util.SortedSet;
import cz.muni.fi.sbapr.similaritysearch.Similarity;

/**
 *
 * @author Martin
 */
public interface SimilaritySearchStrategy {    
    void addSimilarity(Similarity similarity);

    SortedSet<Similarity> getSimilarities();
}
