package cz.muni.fi.sbapr.chartprocessing;

/**
 * Value class used for highlighting areas of chart.
 * Contains indices and real values on those indices.
 * 
 * @author Martin
 */
public class Boundaries implements Comparable<Boundaries> {
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

    @Override
    public int compareTo(Boundaries b) {
        if (this.getLowerBound() > b.getLowerBound())
            return 1;
        if (this.getLowerBound() < b.getLowerBound())
            return -1;
        return 0;    }
}
