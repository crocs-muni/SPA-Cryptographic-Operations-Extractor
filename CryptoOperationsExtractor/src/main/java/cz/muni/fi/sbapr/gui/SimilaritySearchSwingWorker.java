/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.gui;

import java.util.List;
import java.util.SortedSet;
import javax.swing.SwingWorker;
import cz.muni.fi.sbapr.chartprocessing.Boundaries;
import cz.muni.fi.sbapr.models.MainFrameModel;
import cz.muni.fi.sbapr.similaritysearch.Similarity;
import cz.muni.fi.sbapr.similaritysearch.SimilaritySearchController;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Martin
 */
public class SimilaritySearchSwingWorker extends SwingWorker<SortedSet<Similarity>, Void> {
    private final MainFrameModel mainFrameModel;
    private List<Boundaries> resultSimilaritiesSet;
    private final ProgressBarJDialog progressBarJDialog;
    private int progress;
    private final int toDo;
    
    public SimilaritySearchSwingWorker(MainFrameModel mainFrameModel, List<Boundaries> resultSimilaritiesSet, ProgressBarJDialog progressBarJDialog) {
        this.mainFrameModel = mainFrameModel;
        this.progressBarJDialog = progressBarJDialog;
        this.resultSimilaritiesSet = resultSimilaritiesSet;
        this.progress = 0;
        this.toDo = mainFrameModel.getCurrentTrace().getDataCount() / SimilaritySearchController.JUMPING_DISTANCE;
    }

    @Override
    public SortedSet<Similarity> doInBackground() throws InterruptedException {
        return SimilaritySearchController.searchTraceForOperation(
            mainFrameModel.getCurrentTrace()
            , mainFrameModel.getCalibrationTrace()
            , SimilaritySearchController.MANHATTAN_DISTANCE_ALGORITHM
            , this);
    }
    
    public synchronized void incrementProgress() {
        progress++;
        this.progressBarJDialog.getProgressBarModel().setValue((int)(1f * progress / toDo * 100));
    }
    
    @Override
    public void done() {
        try {
            get().forEach((similarity) ->
                    resultSimilaritiesSet.add(
                            new Boundaries(mainFrameModel.getCurrentTrace().getTimeOnPosition(similarity.getFirstIndex())
                                    , mainFrameModel.getCurrentTrace().getTimeOnPosition(similarity.getLastIndex())
                                    , similarity.getFirstIndex()
                                    , similarity.getLastIndex())));
        } catch (InterruptedException | ExecutionException ex) {
            resultSimilaritiesSet = null;
        }
        progressBarJDialog.setVisible(false);
    }
}
