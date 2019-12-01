/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.similaritysearch.multithread;

import cz.muni.fi.sbapr.disatancemeasure.DistanceMeasure;
import cz.muni.fi.sbapr.models.Trace;
import cz.muni.fi.sbapr.similaritysearch.Similarity;
import cz.muni.fi.sbapr.gui.SimilaritySearchSwingWorker;
import cz.muni.fi.sbapr.similaritysearch.strategies.SimilaritySearchStrategy;
import cz.muni.fi.sbapr.similaritysearch.strategies.TopNStrategy;
import java.util.SortedSet;

/**
 * In future ThreadPoolExecutor should be used.
 * 
 * @author Martin
 */
public class MultiThreadController {
    public static SortedSet<Similarity> searchForSimilarities(Trace trace, Trace operation, DistanceMeasure distanceAlgorithm, SimilaritySearchSwingWorker similaritySearchSwingWorker) throws InterruptedException {
        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        int delimitingNumber = trace.getDataCount() / numberOfProcessors;
        Thread[] threads = new Thread[numberOfProcessors];
        SimilaritySearchStrategy[] strategies = new SimilaritySearchStrategy[numberOfProcessors];
        SimilaritySearchStrategy resultStrategy = new TopNStrategy(TopNStrategy.TOP_N_STRATEGY_COUNT);
        createAndStartThreads(numberOfProcessors, strategies, threads, delimitingNumber, trace, operation, distanceAlgorithm, similaritySearchSwingWorker);
        stopThreadsAndMergeResults(numberOfProcessors, threads, strategies, resultStrategy);
        return resultStrategy.getSimilarities();
    }

    private static void stopThreadsAndMergeResults(int numberOfProcessors, Thread[] threads, SimilaritySearchStrategy[] strategies, SimilaritySearchStrategy resultStrategy) throws InterruptedException {
        for (int i = 0; i < numberOfProcessors; i++) {
            threads[i].join();
        }
        for (int i = 0; i < numberOfProcessors; i++) {
            strategies[i].getSimilarities().forEach((similarity) -> resultStrategy.addSimilarity(similarity));
        }
    }

    private static void createAndStartThreads(int numberOfProcessors
            , SimilaritySearchStrategy[] strategies
            , Thread[] threads
            , int delimitingNumber
            , Trace trace
            , Trace operation
            , DistanceMeasure distanceAlgorithm
            , SimilaritySearchSwingWorker similaritySearchSwingWorker) {
        for (int i = 0; i < numberOfProcessors; i++) {
            strategies[i] = new TopNStrategy(TopNStrategy.TOP_N_STRATEGY_COUNT);
            threads[i] = new Thread(new ProcessDataChunkTask(i * delimitingNumber
                    , (i + 1) * delimitingNumber
                    , trace
                    , operation
                    , distanceAlgorithm
                    , strategies[i]
                    , similaritySearchSwingWorker));
            threads[i].start();
        }
    }
}
