package cz.muni.fi.sbapr.disatancemeasure;

/**
 * Class for Euclidean metric function.
 * 
 * @author Martin
 */
public class EuclideanDistance implements DistanceMeasure {
    @Override
    public double compute(double[] smallerVector, double[] biggerVector, int firstIndexOfBiggerVector) {
        double sum = 0;
        for (int i = 0; i < smallerVector.length; i++) {
            sum += (smallerVector[i] - biggerVector[firstIndexOfBiggerVector + i]) * (smallerVector[i] - biggerVector[firstIndexOfBiggerVector + i]);
        }
        return Math.sqrt(sum);
    }
}
