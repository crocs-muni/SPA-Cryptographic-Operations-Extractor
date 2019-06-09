/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.models;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Martin
 */
public class MainFrameModel {
    private Trace currentTrace;
    private Integer firstIndexOnChartTrace;
    private Integer lastIndexOnChartTrace;
    private Trace calibrationTrace;
    private CreateNewCardModel createNewCardModel;
    private SortedSet<OperationRecord> operationRecords;
    private OperationRecord currentOperationRecord;
    
    public MainFrameModel() {
        this.operationRecords = new TreeSet<>((OperationRecord or1, OperationRecord or2) -> {
            int eq = or1.getInstructionName().compareTo(or2.getInstructionName());
            if (eq == 0) {
                return or1.getPlace().compareTo(or2.getPlace());
            }
            return eq;
        });
    }
    
    /**
     * @return the currentTrace
     */
    public Trace getCurrentTrace() {
        return currentTrace;
    }

    /**
     * @param currentTrace the currentTrace to set
     */
    public void setCurrentTrace(Trace currentTrace) {
        this.currentTrace = currentTrace;
    }

    /**
     * @return the calibrationTrace
     */
    public Trace getCalibrationTrace() {
        return calibrationTrace;
    }

    /**
     * @param calibrationTrace the calibrationTrace to set
     */
    public void setCalibrationTrace(Trace calibrationTrace) {
        this.calibrationTrace = calibrationTrace;
    }

    /**
     * @return the createNewCardModel
     */
    public CreateNewCardModel getCreateNewCardModel() {
        return createNewCardModel;
    }

    /**
     * @param createNewCardModel the createNewCardModel to set
     */
    public void setCreateNewCardModel(CreateNewCardModel createNewCardModel) {
        this.createNewCardModel = createNewCardModel;
    }
    
    public SortedSet<OperationRecord> getOperationRecords() {
        return operationRecords;
    }

    public void setOperationRecords(SortedSet<OperationRecord> operationRecords) {
        this.operationRecords = operationRecords;
    }
    
    public OperationRecord getCurrentOperationRecord() {
        return currentOperationRecord;
    }

    public void setCurrentOperationRecord(OperationRecord currentOperationRecord) {
        this.currentOperationRecord = currentOperationRecord;
    }
    
    public Integer getFirstIndexOnChartTrace() {
        return firstIndexOnChartTrace;
    }

    public void setFirstIndexOnChartTrace(Integer firstIndexOnChartTrace) {
        this.firstIndexOnChartTrace = firstIndexOnChartTrace;
    }

    public Integer getLastIndexOnChartTrace() {
        return lastIndexOnChartTrace;
    }

    public void setLastIndexOnChartTrace(Integer lastIndexOnChartTrace) {
        this.lastIndexOnChartTrace = lastIndexOnChartTrace;
    }
    
    public void resetMainFrameModel() {
        this.currentTrace = null;
        this.firstIndexOnChartTrace = null;
        this.lastIndexOnChartTrace = null;
        this.calibrationTrace = null;
        this.createNewCardModel = null;
        this.currentOperationRecord = null;
        this.operationRecords.clear();
    }
    
    public boolean isModelEmpty() {
        return this.currentTrace == null &&
            this.firstIndexOnChartTrace == null &&
            this.lastIndexOnChartTrace == null &&
            this.calibrationTrace == null &&
            this.createNewCardModel == null &&
            this.operationRecords.isEmpty() &&
            this.currentOperationRecord == null;
    }
}
