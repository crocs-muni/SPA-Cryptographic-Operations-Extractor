package sbapr.ssp.chartprocessing;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JSpinner;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import sbapr.ssp.dataprocessing.DataManager;
import sbapr.ssp.models.MainFrameModel;

/**
 * This class is a simple mouse listener used in chart panel.
 * If one of JRadioButtons is marked, then clicked index of value is send to particular JSpinner.
 * 
 * @author Martin Podhora
 */
public class CustomChartMouseListener implements ChartMouseListener {
    private final ChartPanel chartPanel;
    private final JSpinner firstTimeJSpinner;
    private final JSpinner lastTimeJSpinner;
    private final MainFrameModel mainFrameModel;
    
    public CustomChartMouseListener(ChartPanel chartPanel, JSpinner firstTimeJSpinner, JSpinner lastTimeJSpinner, MainFrameModel mainFrameModel) {
        this.chartPanel = chartPanel;
        this.firstTimeJSpinner = firstTimeJSpinner;
        this.lastTimeJSpinner = lastTimeJSpinner;
        this.mainFrameModel = mainFrameModel;
    }
    
    /**
     * Helper function that finds index of clicked value.
     * 
     * @param x
     * @param y
     * @return 
     */
    private int getIndexOfClickedValue(double realx) {
        XYPlot plot = chartPanel.getChart().getXYPlot();

        double oldDifference = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < plot.getDataset().getItemCount(0); i++) {
            double difference = Math.max(realx, plot.getDataset().getXValue(0, i)) - Math.min(realx, plot.getDataset().getXValue(0, i));
            if (difference > oldDifference) {
                index = i - 1;
                break;
            }
            oldDifference = difference;
        }
        
        return index;
    }
    
    @Override
    public void chartMouseClicked(ChartMouseEvent cme) {
        int x = cme.getTrigger().getX(); 
        int y = cme.getTrigger().getY();
        XYItemEntity xyitem = (XYItemEntity)cme.getEntity();
        XYDataset ds = xyitem.getDataset();
        double realx = ds.getXValue(xyitem.getSeriesIndex(), xyitem.getItem());
        int clickedIndexOnModifiedTrace = getIndexOfClickedValue(realx);
        if (mainFrameModel.getFirstIndexOnChartTrace() == null) {
            mainFrameModel.setFirstIndexOnChartTrace(clickedIndexOnModifiedTrace);
            firstTimeJSpinner.setValue(mainFrameModel.getCurrentTrace().getTimeOnPosition(ChartManager.modifiedToOriginalIndex(mainFrameModel.getFirstIndexOnChartTrace())));
        } else {
            if (clickedIndexOnModifiedTrace < mainFrameModel.getFirstIndexOnChartTrace()) {
                mainFrameModel.setLastIndexOnChartTrace(mainFrameModel.getFirstIndexOnChartTrace());
                mainFrameModel.setFirstIndexOnChartTrace(clickedIndexOnModifiedTrace);
            } else {
                mainFrameModel.setLastIndexOnChartTrace(clickedIndexOnModifiedTrace);
            }
            firstTimeJSpinner.setValue(mainFrameModel.getCurrentTrace().getTimeOnPosition(ChartManager.modifiedToOriginalIndex(mainFrameModel.getFirstIndexOnChartTrace())));
            lastTimeJSpinner.setValue(mainFrameModel.getCurrentTrace().getTimeOnPosition(ChartManager.modifiedToOriginalIndex(mainFrameModel.getLastIndexOnChartTrace())));
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent cme) {
    }
}
