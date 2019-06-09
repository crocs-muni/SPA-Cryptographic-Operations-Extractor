package cz.muni.fi.sbapr.chartprocessing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.util.Collection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import cz.muni.fi.sbapr.dataprocessing.DataManager;
import cz.muni.fi.sbapr.models.Trace;

/**
 * This class contains static methods used for plotting and highlighting charts.
 * 
 * @author Martin Podhora
 */
public class ChartManager {
    public static final int SKIPPING_CONSTANT = 1;
    public static final float CHART_THICKNESS = 1.2f;
    private static final Color RED = new Color(255,85,85);;
    private static final Color GREEN = new Color(50, 195, 50);;
    private static final Color BLUE = new Color(0, 150, 255);;
    
    
    /**
     * Returns true when to skip, and false otherwise.
     * 
     * @param indexInOriginalArray
     * @return 
     */
    public static boolean skipFunction(int indexInOriginalArray) {
        return indexInOriginalArray % SKIPPING_CONSTANT != 0;
    }
    
    public static int modifiedToOriginalIndex(int indexInModifiedArray) {
        return indexInModifiedArray * SKIPPING_CONSTANT;
    }
    
    /**
     * This method creates chart panel from data in an instance of the trace.
     * It creates simple XYLineChart with legend.
     * It returns instance of an object ChartPanel - considering to change it to JPanel to remove dependency on jfreechart library in the SSPGui class.
     * 
     * @param trace
     * @param name
     * @return chart panel filled with data from trace
     */
    public static ChartPanel plotChart(Trace trace, String name) {
        int dataCount = trace.getDataCount();
        double[] timeArray = trace.getTime();
        double[] voltageArray = trace.getVoltage();
        
        XYSeries xySeries = new XYSeries(name);
        for (int i = 0; i < dataCount; i++) {
            if (!ChartManager.skipFunction(i)) {
                xySeries.add(timeArray[i], voltageArray[i]);
            }
        }
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection(xySeries);
        JFreeChart chart = ChartFactory.createXYLineChart(name, "Time" + trace.getTimeUnit(), "Voltage" + trace.getVoltageUnit(), xySeriesCollection, PlotOrientation.VERTICAL, true, false, false);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.BLACK);
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(CHART_THICKNESS));
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }
    
    /**
     * This method should be used only when @param chartPanel already filled by method @link this.highlightChart(Trace trace).
     * It changes the rendering of the chart @param chartPanel, it highlights the area between @param firstIndex and @param lastIndex.
     * 
     * @param chartPanel
     * @param delimitingOperations
     * @param newOperations
     * @param newOperationText
     * @return chart panel with highlighted area
     */
    public static ChartPanel highlightChart(ChartPanel chartPanel, Collection<Boundaries> delimitingOperations, Collection<Boundaries> newOperations, String newOperationText) {
        XYPlot plot = chartPanel.getChart().getXYPlot();
        
        plot.getRenderer().removeAnnotations();
        
        delimitingOperations.forEach((boundary) -> {
            XYTextAnnotation annotation = new XYTextAnnotation(
                    DataManager.getOperationInfoForInstruction(DataManager.DELIMITING_OPERATION_INSTRUCTION).getCodeName()
                    , (boundary.getLowerBound() + boundary.getUpperBound()) / 2
                    , (plot.getRangeAxis().getLowerBound() + plot.getRangeAxis().getUpperBound()) / 2);
            annotation.setFont(new Font("F1", 1, 13));
            plot.addAnnotation(annotation);
        });
        
        newOperations.forEach((boundary) -> {
            XYTextAnnotation annotation = new XYTextAnnotation(newOperationText
                    , (boundary.getLowerBound() + boundary.getUpperBound()) / 2
                    , (plot.getRangeAxis().getLowerBound() + plot.getRangeAxis().getUpperBound()) / 2);
            annotation.setFont(new Font("F1", 1, 13));
            plot.addAnnotation(annotation);
        });
        
        XYDataset dataset = plot.getDataset();
        
        XYItemRenderer renderer = new StandardXYItemRenderer() {
            @Override
            public Paint getItemPaint(int series, int item) {
                double value = dataset.getXValue(series, item);
                if (delimitingOperations
                        .stream()
                        .anyMatch((Boundaries boundaries) -> value >= boundaries.getLowerBound() && value <= boundaries.getUpperBound())) {
                    return GREEN;
                } else if (newOperations
                        .stream()
                        .anyMatch((Boundaries boundaries) -> value >= boundaries.getLowerBound() && value <= boundaries.getUpperBound())) {
                    return BLUE;
                } else {
                    return RED;
                }
            }
        };
        
        renderer.setSeriesStroke(0, new BasicStroke(CHART_THICKNESS));
        
        plot.setRenderer(renderer);
        
        return chartPanel;
    }
    
    public static ChartPanel highlightChart(ChartPanel chartPanel, Collection<Boundaries> operations) {
        XYPlot plot = chartPanel.getChart().getXYPlot();

        XYDataset dataset = plot.getDataset();

        XYItemRenderer renderer = new StandardXYItemRenderer() {
            @Override
            public Paint getItemPaint(int series, int item) {
                double value = dataset.getXValue(series, item);
                if (operations
                        .stream()
                        .anyMatch((Boundaries boundaries) -> value > boundaries.getLowerBound() && value < boundaries.getUpperBound())) {
                    return GREEN;
                } else {
                    return RED;
                }
            }
        };
        
        renderer.setSeriesStroke(0, new BasicStroke(CHART_THICKNESS));
        
        plot.setRenderer(renderer);
        
        return chartPanel;
    }
}
