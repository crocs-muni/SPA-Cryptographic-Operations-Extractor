package cz.muni.fi.sbapr.filters;

import java.util.Arrays;
import cz.muni.fi.sbapr.models.Trace;
import uk.me.berndporr.iirj.Butterworth;

/**
 * Class that represents low-pass filter.
 * It is used to filter voltage array of the trace.
 * 
 * @author Martin Podhora
 */
public class LowPassFilter {
    private int samplingFrequency;
    private int cutOffFrequency;
    public static final int ORDER = 1;
    
    /**
     * Constructor
     * 
     * @param samplingFreq
     * @param cutOffFreq
     */
    public LowPassFilter(int samplingFreq, int cutOffFreq) {
        this.samplingFrequency = samplingFreq;
        this.cutOffFrequency = cutOffFreq;
    }
    
    /**
     * Method used to filter voltage values of trace
     * 
     * @param trace 
     */
    public void applyLowPassFilterInPlace(Trace trace) {
        double filteredValue;
        Butterworth butterworth = new Butterworth();
        butterworth.lowPass(ORDER, samplingFrequency, cutOffFrequency);
        for (int i = 0; i < trace.getDataCount(); i++) {
            filteredValue = butterworth.filter(trace.getVoltageOnPosition(i));
            trace.setVoltageOnPosition(filteredValue, i);
        }
    }
    
    public Trace applyLowPassFilterMakeCopy(Trace trace) {
        Trace traceCopy = new Trace(trace.getVoltageUnit(), trace.getTimeUnit(), trace.getDataCount(), Arrays.copyOf(trace.getVoltage(), trace.getDataCount()), null, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        applyLowPassFilterInPlace(traceCopy);
        return traceCopy;
    }
    
    public void setSamplingFrequency(int samplingFrequency) {
        this.samplingFrequency = samplingFrequency;
    }
    
    public void setCutOffFrequency(int cutOffFrequency) {
        this.cutOffFrequency = cutOffFrequency;
    }
}
