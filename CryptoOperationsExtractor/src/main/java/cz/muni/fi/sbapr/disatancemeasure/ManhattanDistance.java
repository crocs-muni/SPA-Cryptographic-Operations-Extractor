package cz.muni.fi.sbapr.disatancemeasure;

/**
 * Class for Manhattan metric function.
 * 
 * @author Martin
 */
public class ManhattanDistance implements DistanceMeasure {
    @Override
    public double compute(double[] smallerVector, double[] biggerVector, int firstIndexOfBiggerVector) {
        double sum = 0;
        for (int i = 0; i < smallerVector.length; i++) {
            sum += Math.abs(smallerVector[i] - biggerVector[firstIndexOfBiggerVector + i]);
        }
        return sum;
    }
}
