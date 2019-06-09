/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.models;

import java.util.List;

/**
 *
 * @author Martin
 */
public class CreateNewCardModel {
    private String sourceDirectory;
    private String destinationDirectory;
    private List<OperationRecord> operationRecords;
    private boolean finishedProcess;

    public List<OperationRecord> getOperationRecords() {
        return operationRecords;
    }

    public void setOperationRecords(List<OperationRecord> operationRecords) {
        this.operationRecords = operationRecords;
    }
    
    public CreateNewCardModel() {
        this.finishedProcess = false;
    }
    
    /**
     * @return the sourceDirectory
     */
    public String getSourceDirectory() {
        return sourceDirectory;
    }

    /**
     * @param sourceDirectory the sourceDirectory to set
     */
    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    /**
     * @return the destinationDirectory
     */
    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    /**
     * @param destinationDirectory the destinationDirectory to set
     */
    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    /**
     * @return the finishedProcess
     */
    public boolean isFinishedProcess() {
        return finishedProcess;
    }

    /**
     * @param finishedProcess the finishedProcess to set
     */
    public void setFinishedProcess(boolean finishedProcess) {
        this.finishedProcess = finishedProcess;
    }
    
}
