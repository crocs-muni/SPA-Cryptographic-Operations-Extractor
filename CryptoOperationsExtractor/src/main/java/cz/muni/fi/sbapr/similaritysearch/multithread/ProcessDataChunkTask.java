/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.similaritysearch.multithread;

import cz.muni.fi.sbapr.disatancemeasure.DistanceMeasure;
import cz.muni.fi.sbapr.models.Trace;
import cz.muni.fi.sbapr.similaritysearch.Similarity;
import cz.muni.fi.sbapr.similaritysearch.SimilaritySearchController;
import cz.muni.fi.sbapr.gui.SimilaritySearchSwingWorker;
import cz.muni.fi.sbapr.similaritysearch.strategies.SimilaritySearchStrategy;

/**
 *
 * @author Martin
 */
public class ProcessDataChunkTask implements Runnable {
    private final int firstIndex;
    private final int lastIndex;
    private final Trace preprocessedTrace;
    private final Trace preprocessedOperation;
    private final DistanceMeasure distanceAlgorithm;
    private final SimilaritySearchStrategy similaritySearchStrategy;
    private final SimilaritySearchSwingWorker similaritySearchTask;
    
    public ProcessDataChunkTask(int firstIndex
            , int lastIndex
            , Trace preprocessedTrace
            , Trace preprocessedOperation
            , DistanceMeasure distanceAlgorithm
            , SimilaritySearchStrategy similaritySearchStrategy
            , SimilaritySearchSwingWorker similaritySearchTask) {
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.preprocessedTrace = preprocessedTrace;
        this.preprocessedOperation = preprocessedOperation;
        this.distanceAlgorithm = distanceAlgorithm;
        this.similaritySearchStrategy = similaritySearchStrategy;
        this.similaritySearchTask = similaritySearchTask;
    }
    
    @Override
    public void run() {
        int firstIndexCounter = firstIndex;
        int lastIndexCounter = firstIndex + preprocessedOperation.getDataCount();
        int stoppingIndex = lastIndex + preprocessedOperation.getDataCount();
        if (stoppingIndex > preprocessedTrace.getDataCount()) stoppingIndex = preprocessedTrace.getDataCount();
        while (!similaritySearchTask.isCancelled() && lastIndexCounter < stoppingIndex) {
            Similarity similarity = new Similarity(firstIndexCounter
                    , lastIndexCounter
                    , distanceAlgorithm.compute(preprocessedOperation.getVoltage(), preprocessedTrace.getVoltage(), firstIndexCounter));
            similaritySearchStrategy.addSimilarity(similarity);
            firstIndexCounter += SimilaritySearchController.JUMPING_DISTANCE;
            lastIndexCounter += SimilaritySearchController.JUMPING_DISTANCE;
            similaritySearchTask.incrementProgress();
        }
    }
    
}
