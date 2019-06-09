/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbapr.ssp.similaritysearch;

import java.util.Arrays;
import java.util.SortedSet;
import javafx.util.Pair;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;
import sbapr.ssp.filters.LowPassFilter;
import sbapr.ssp.models.Trace;
import sbapr.ssp.similaritysearch.strategies.SimilaritySearchStrategy;

/**
 *
 * @author Martin
 */
public class SimilaritySearchController {
    public static final int CUTOFF_FREQUENCY = 10000;
    public static final int JUMPING_DISTANCE = 10;
    private static final float TOLERATED_SAMPLING_RATIO_UPPER_BOUND = 1.05f;
    private static final float TOLERATED_SAMPLING_RATIO_LOWER_BOUND = 0.95f;
    private static final double TOLERATED_Y_AXIS_DIFFERENCE_UPPER_BOUND = 5;
    private static final double TOLERATED_Y_AXIS_DIFFERENCE_LOWER_BOUND = -5;
    public static final EuclideanDistance EUCLIDEAN_DISTANCE_ALGORITHM = new EuclideanDistance();
    public static final ManhattanDistance MANHATTAN_DISTANCE_ALGORITHM = new ManhattanDistance();
    
    private static Trace removeFromOperation(float toRetainRatio, Trace operation) {
        int retained = 0;
        float retainedRatio = 0;
        int toRetain = Math.round(operation.getDataCount() * toRetainRatio);
        double[] operationVoltage = new double[toRetain];
        double voltageMaximum = Double.NEGATIVE_INFINITY;
        double voltageMinimum = Double.POSITIVE_INFINITY;

        for (int i = 0; i < operation.getDataCount(); i++) {
            if (retainedRatio < toRetainRatio) {
                operationVoltage[retained] = operation.getVoltageOnPosition(i);
                if (operationVoltage[retained] > voltageMaximum) voltageMaximum = operationVoltage[retained];
                if (operationVoltage[retained] < voltageMinimum) voltageMaximum = operationVoltage[retained];
                retained++;
            }
            retainedRatio = retained / (i + 1f);

        }
        
        return new Trace(operation.getVoltageUnit(), operation.getTimeUnit(), retained, operationVoltage, null, voltageMaximum, voltageMinimum);
    }
    
    private static Trace addToOperation(float toAddRatio, Trace operation) {
        int toAdd = Math.round(operation.getDataCount() * toAddRatio);
        double[] operationVoltage = new double[toAdd];
        int previousDataCounter = 0;
        //Adding first because assignement in for cycle needs first element to be added
        operationVoltage[0] = operation.getVoltageOnPosition(0);
        int added = 1;
        float addedRatio = 1f;
        
        for (int i = 1; i < toAdd; i++) {
            if (addedRatio < toAddRatio) {
                operationVoltage[i] = (operationVoltage[i - 1] + operation.getVoltageOnPosition(previousDataCounter)) / 2;
                added++;
            } else {
                operationVoltage[i] = operation.getVoltageOnPosition(previousDataCounter);
                previousDataCounter++;
            }
            addedRatio = added / (previousDataCounter + 1f);
        }

        return new Trace(operation.getVoltageUnit(), operation.getTimeUnit(), toAdd, operationVoltage, null, operation.getMaximalVoltage(), operation.getMinimalVoltage());

    }
    
    /**
     * TO ADJUST SIZE OF ARRAY WHILE REMOVING
     * 
     * @param trace
     * @param traceSamplingFrequency
     * @param operation
     * @param operationSamplingFrequency
     * @return 
     */
    private static Trace adjustSamplingFrequency(Trace trace, int traceSamplingFrequency, Trace operation, int operationSamplingFrequency) {
        float traceToOperationRatio = 1f * traceSamplingFrequency / operationSamplingFrequency;
        if (traceToOperationRatio < TOLERATED_SAMPLING_RATIO_LOWER_BOUND) {
            return removeFromOperation(traceToOperationRatio, operation);
        }
        else if (traceToOperationRatio > TOLERATED_SAMPLING_RATIO_UPPER_BOUND) {
            return addToOperation(traceToOperationRatio, operation);
        } else {
            return operation;
        }
    }
    
    private static Pair<Trace, Trace> applyFilterMakeCopy(Trace trace, Trace operation) {
        LowPassFilter lowPassFilter = new LowPassFilter(operation.getSamplingFrequency(), CUTOFF_FREQUENCY);
        Trace operationCopy = lowPassFilter.applyLowPassFilterMakeCopy(operation);
        lowPassFilter.setSamplingFrequency(trace.getSamplingFrequency());
        Trace traceCopy = lowPassFilter.applyLowPassFilterMakeCopy(trace);
        return new Pair(traceCopy, operationCopy);
    }
    
    private static void moveAlongYAxis(Trace trace, Trace operation) {
        double distance = trace.getMaximalVoltage() - operation.getMaximalVoltage();
        if (distance < TOLERATED_Y_AXIS_DIFFERENCE_UPPER_BOUND && distance > TOLERATED_Y_AXIS_DIFFERENCE_LOWER_BOUND) return;
        for (int i = 0; i < operation.getDataCount(); i++) {
            operation.setVoltageOnPosition(operation.getVoltageOnPosition(i) + distance, i);
        }
    }
    
    public static SortedSet<Similarity> searchTraceForOperation(Trace trace, Trace operation, SimilaritySearchStrategy similaritySearchStrategy, DistanceMeasure distanceAlgorithm, SimilaritySearchTask similaritySearchTask) {
            Pair<Trace, Trace> traceOperationCopies = applyFilterMakeCopy(trace, operation);

            Trace modifiedOperation = adjustSamplingFrequency(traceOperationCopies.getKey(), trace.getSamplingFrequency(), traceOperationCopies.getValue(), operation.getSamplingFrequency());
            
            //moveAlongYAxis(traceOperationCopies.getKey(), traceOperationCopies.getValue());

            double[] slidingWindow;
            int firstIndex = 0;
            int lastIndex = modifiedOperation.getDataCount();
            while (!similaritySearchTask.isCancelled() && lastIndex < trace.getDataCount()) {
                slidingWindow = Arrays.copyOfRange(traceOperationCopies.getKey().getVoltage(), firstIndex, lastIndex);
                Similarity similarity = new Similarity(firstIndex, lastIndex, distanceAlgorithm.compute(slidingWindow, modifiedOperation.getVoltage()));
                similaritySearchStrategy.addSimilarity(similarity);
                firstIndex += JUMPING_DISTANCE;
                lastIndex += JUMPING_DISTANCE;
                similaritySearchTask.getProgressBarModel().setValue((int) ((double) lastIndex / trace.getDataCount() * 100));
            }
            return similaritySearchStrategy.getSimilarities();
        }
}
