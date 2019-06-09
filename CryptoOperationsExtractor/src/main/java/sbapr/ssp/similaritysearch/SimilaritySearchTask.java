/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbapr.ssp.similaritysearch;

import java.util.List;
import java.util.SortedSet;
import javax.swing.BoundedRangeModel;
import javax.swing.SwingWorker;
import sbapr.ssp.chartprocessing.Boundaries;
import sbapr.ssp.gui.ProgressBarJDialog;
import sbapr.ssp.models.MainFrameModel;
import sbapr.ssp.similaritysearch.strategies.TopNStrategy;

/**
 *
 * @author Martin
 */
public class SimilaritySearchTask extends SwingWorker<SortedSet<Similarity>, Void> {
    private final MainFrameModel mainFrameModel;
    private final int topNStrategyCount;
    private List<Boundaries> resultSimilaritiesSet;
    private final ProgressBarJDialog progressBarJDialog;
    
    public SimilaritySearchTask(MainFrameModel mainFrameModel, int topNStrategyCount, List<Boundaries> resultSimilaritiesSet, ProgressBarJDialog progressBarJDialog) {
        this.mainFrameModel = mainFrameModel;
        this.topNStrategyCount = topNStrategyCount;
        this.progressBarJDialog = progressBarJDialog;
        this.resultSimilaritiesSet = resultSimilaritiesSet;
    }

    @Override
    public SortedSet<Similarity> doInBackground() {
        return SimilaritySearchController.searchTraceForOperation(
            mainFrameModel.getCurrentTrace()
            , mainFrameModel.getCalibrationTrace()
            , new TopNStrategy(topNStrategyCount)
            , SimilaritySearchController.MANHATTAN_DISTANCE_ALGORITHM
            , this);
    }
    
    public BoundedRangeModel getProgressBarModel() {
        return this.progressBarJDialog.getProgressBarModel();
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
        } catch (Exception ex) {
            resultSimilaritiesSet = null;
        }
        progressBarJDialog.setVisible(false);
    }
}
