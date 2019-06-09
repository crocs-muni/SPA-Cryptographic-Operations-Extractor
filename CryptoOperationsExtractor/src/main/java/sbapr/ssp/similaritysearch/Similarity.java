/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbapr.ssp.similaritysearch;

/**
 *
 * @author Martin
 */
public class Similarity {
    private final int firstIndex;
    private final int lastIndex;
    private final double distance;
    
    public Similarity(int firstIndex, int lastIndex, double distance) {
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.distance = distance;
    }
    
    public int getFirstIndex() {
        return firstIndex;
    }
    
    public int getLastIndex() {
        return lastIndex;
    }
    
    public double getDistance() {
        return distance;
    }
    
    @Override
    public String toString() {
        return "<" + firstIndex + ", "+ lastIndex +"> at distance: " + distance + "\n";
    }
}
