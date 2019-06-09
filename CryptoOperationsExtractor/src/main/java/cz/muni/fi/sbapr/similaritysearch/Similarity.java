/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.similaritysearch;

/**
 *
 * @author Martin
 */
public class Similarity implements Comparable<Similarity> {
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

    @Override
    public int compareTo(Similarity s) {
        if (this.getDistance() == s.getDistance()) // Be aware of the fact that 2 can have same distance and they are not the same.
            return 0;
        if (this.getDistance() > s.getDistance()) 
            return 1;
        return -1;    
    }
}
