package cz.muni.fi.sbapr.dataprocessing;

import com.opencsv.CSVWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import cz.muni.fi.sbapr.models.Trace;

/**
 * Class contains static helper methods for data saving to .csv files.
 * 
 * @author Martin Podhora
 */
class DataSaver {
    /**
     * Last header line of .csv file.
     */
    private static final int DATA_STARTING_LINE = 3;
    
    /**
     * Exports all data from trace to .csv file.
     * 
     * @param trace
     * @param dataPath
     * @throws IOException 
     */
    public static void exportToCsv(Trace trace, String dataPath) throws IOException {
        privateExportToCsv(trace, dataPath, 0, trace.getDataCount() - DATA_STARTING_LINE);
    }
    
    /**
     * Exports specified interval of data from trace to .csv file.
     * 
     * @param trace
     * @param dataPath
     * @param firstIndex
     * @param lastIndex
     * @throws IOException 
     */
    public static void exportToCsv(Trace trace, String dataPath, int firstIndex, int lastIndex) throws IOException {
        privateExportToCsv(trace, dataPath, firstIndex, lastIndex);
    }
    
    /**
     * More general helper method.
     * 
     * @param trace
     * @param dataPath
     * @param firstIndex
     * @param lastIndex
     * @throws IOException 
     */
    private static void privateExportToCsv(Trace trace, String dataPath, int firstIndex, int lastIndex) throws IOException {
        String[] csvRow = new String[2];
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataPath))) {
            try (CSVWriter csvWriter = new CSVWriter(bw, ',', CSVWriter.NO_QUOTE_CHARACTER)) {
                csvRow[0] = "Time";
                csvRow[1] = "Voltage";
                csvWriter.writeNext(csvRow);
                csvRow[0] = trace.getTimeUnit();
                csvRow[1] = trace.getVoltageUnit();
                csvWriter.writeNext(csvRow);
                csvWriter.writeNext(new String[2]);
                for (int i = firstIndex; i < lastIndex; i++) {
                    csvRow[0] = String.valueOf(trace.getTimeOnPosition(i));
                    csvRow[1] = String.valueOf(trace.getVoltageOnPosition(i));
                    csvWriter.writeNext(csvRow);
                }
            }
        }
    }
}
