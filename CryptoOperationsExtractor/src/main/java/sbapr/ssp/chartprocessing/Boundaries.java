/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbapr.ssp.chartprocessing;

/**
 * Used for highlighting areas of chart.
 * 
 * @author Martin
 */
public class Boundaries {
    private final double lowerBound;
    private final double upperBound;
    private final int firstIndex;
    private final int lastIndex;
    
    public Boundaries(double lowerBound, double upperBound, int beginingIndex, int endingIndex) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.firstIndex = beginingIndex;
        this.lastIndex = endingIndex;
    }
    
    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }
    
    public int getFirstIndex() {
        return firstIndex;
    }
    
    public int getLastIndex() {
        return lastIndex;
    }
}
