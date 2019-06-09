package cz.muni.fi.sbapr.dataprocessing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import cz.muni.fi.sbapr.models.OperationRecord;
import cz.muni.fi.sbapr.models.Trace;

/**
 * This class contains many utility methods used for data loading and saving.
 * HashMap will in future be replaced with resource file.
 * 
 * @author Martin Podhora
 */
public class DataManager {
    /**
     * Column of time values in .csv file.
     */
    public static final int DEFAULT_TIME_COLUMN = 0;
    
    /**
     * Column of voltage in .csv file.
     */
    public static final int DEFAULT_VOLTAGE_COLUMN = 1;
    public static final int SKIPPING_CONSTANT = 2;
    public static final String DELIMITING_OPERATION_INSTRUCTION = "B0";
    private static final HashMap<String, OperationInfo> INSTRUCTION_TO_OPERATION_MAPPING;
    static {
        INSTRUCTION_TO_OPERATION_MAPPING = new HashMap<>();
        INSTRUCTION_TO_OPERATION_MAPPING.put("B0"
                , new OperationInfo("B0", "secureRandom.generateData", "RandomData.ALG_SECURE_RANDOM, 128B", "TRNG"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B1"
                , new OperationInfo("B1", "aesKey.setKey", "KeyBuilder.LENGTH_AES_256", "setKey"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B2"
                , new OperationInfo("B2", "desKey.setKey", "KeyBuilder.LENGTH_DES3_3KEY", "SetKey"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B3"
                , new OperationInfo("B3", "aesCipher.doFinal", "Cipher.ALG_AES_BLOCK_128_CBC_NOPAD", "Enc"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B4"
                , new OperationInfo("B4", "desCipher.doFinal", "Cipher.ALG_DES_CBC_NOPAD", "Enc"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B5"
                , new OperationInfo("B5", "SHA1Hash.doFinal", "MessageDigest.ALG_SHA", "SHA1"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B6"
                , new OperationInfo("B6", "SHA256Hash.doFinal", "MessageDigest.ALG_SHA_256", "SHA256"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B7"
                , new OperationInfo("B7", "RSAKeyPair.genKeyPair", "KeyPair.ALG_RSA_CRT, KeyBuilder.LENGTH_RSA_512", "KeyGen"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B8"
                , new OperationInfo("B8", "RSASign.sign", "Signature.ALG_RSA_SHA_PKCS1", "Sign"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("B9"
                , new OperationInfo("B9", "EC192FPKeyPair.genKeyPair", "KeyPair.ALG_EC_FP-192", "KeyGen"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("BA"
                , new OperationInfo("BA", "EC192FPSign.sign", "Signature.ALG_ECDSA_SHA", "Sign"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("BB"
                , new OperationInfo("BB", "EC256FPKeyPair.genKeyPair", "KeyPair.ALG_EC_FP-256", "KeyGen"));
        INSTRUCTION_TO_OPERATION_MAPPING.put("BC"
                , new OperationInfo("BC", "EC256FPSign.sign", "Signature.ALG_ECDSA_SHA", "Sign"));
    }
    
    public static OperationInfo getOperationInfoForInstruction(String instruction) {
        return INSTRUCTION_TO_OPERATION_MAPPING.get(instruction);
    }
    
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
    /**
     * Scans directory. If it contains CSV files, then it will look for particular names defined in the constant.
     * 
     * @param directory
     * @return CSV files with appropriate name from source directory.
     */
    public static List<OperationRecord> scanDirectory(String directory) {
        File file = new File(directory);
        File[] files = file.listFiles();
        ArrayList<OperationRecord> operationRecordsList = new ArrayList<>();
        for (File fileToAdd : files) {
            if (!getFileExtension(fileToAdd).equals("csv")) continue;
            Integer duplicatesCount = 0;
            String instructionName = null;
            String operationName = null;
            for (Entry<String, OperationInfo> instructionToOperation : INSTRUCTION_TO_OPERATION_MAPPING.entrySet()) {
                if (!fileToAdd.getName().contains(instructionToOperation.getKey())) continue;
                instructionName = instructionToOperation.getKey();
                operationName = instructionToOperation.getValue().getFullName();
                for (OperationRecord operationRecord : operationRecordsList) {
                    if (operationRecord.getInstructionName().equals(instructionToOperation.getKey()))
                        duplicatesCount++;
                }
            }
            if (instructionName != null) 
                operationRecordsList.add(new OperationRecord(fileToAdd, getOperationInfoForInstruction(instructionName), duplicatesCount));
        }
        return operationRecordsList;
    }
    
    public static Trace loadTrace(String filePath, boolean notSkipping) throws IOException {
        return DataLoader.importFromCsv(filePath, DEFAULT_TIME_COLUMN, DEFAULT_VOLTAGE_COLUMN, notSkipping);
    }
    /**
     * Returns true when to skip, and false otherwise
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
     * Method used to save data to file specified in @param dataPath.
     * This method saves whole trace.
     * 
     * @param filePath
     * @param trace
     * @throws IOException 
     */
    public static void saveTrace(String filePath, Trace trace) throws IOException {
        DataSaver.exportToCsv(trace, filePath);
    }
    
    /**
     * Method used to save data to file specified in @param dataPath between two indices.
     * 
     * @param filePath
     * @param trace
     * @param firstIndex
     * @param lastIndex
     * @throws IOException 
     */
    public static void saveTrace(String filePath, Trace trace, int firstIndex, int lastIndex) throws IOException {
        DataSaver.exportToCsv(trace, filePath, firstIndex, lastIndex);
    }
}
