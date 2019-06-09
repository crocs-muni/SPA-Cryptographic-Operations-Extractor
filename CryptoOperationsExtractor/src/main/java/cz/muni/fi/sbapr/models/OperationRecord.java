/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.models;

import java.io.File;
import cz.muni.fi.sbapr.dataprocessing.OperationInfo;

/**
 *
 * @author Martin
 */
public class OperationRecord {
    private final File filePath;
    private final Integer place;
    private final OperationInfo operationInfo;
    
    public OperationRecord(File filePath, OperationInfo operationInfo, Integer place) {
        this.filePath = filePath;
        this.operationInfo = operationInfo;
        this.place = place;
    }
    
    public File getFilePath() {
        return filePath;
    }

    public String getInstructionName() {
        return operationInfo.getInstruction();
    }

    public String getOperationName() {
        return operationInfo.getFullName();
    }
    
    public Integer getPlace() {
        return place;
    }
}
