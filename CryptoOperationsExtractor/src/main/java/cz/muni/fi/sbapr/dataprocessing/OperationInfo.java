/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.dataprocessing;

/**
 *
 * @author Martin
 */
public class OperationInfo {
    private final String instruction;
    private final String operationCalledOnInstance;
    private final String additionalInfo;
    private final String codeName;

    public OperationInfo(String instruction, String operationCalledOnInstance, String additionnalInfo, String codeName) {
        this.instruction = instruction;
        this.operationCalledOnInstance = operationCalledOnInstance;
        this.additionalInfo = additionnalInfo;
        this.codeName = codeName;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getOperationCalledOnInstance() {
        return operationCalledOnInstance;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getCodeName() {
        return codeName;
    }
    
    public String getFullName() {
        return instruction + "-" + operationCalledOnInstance + "-" + additionalInfo;
    }
}
