package cz.muni.fi.sbapr.dataprocessing;

import com.opencsv.CSVReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import cz.muni.fi.sbapr.models.Trace;

/**
 * Class contains static helper methods for data loading from .csv files.
 * 
 * @author Martin Podhora
 */
class DataLoader {
    /**
     * Last header line of .csv file.
     */
    private static final int DATA_STARTING_LINE = 3;   
    
    /**
     * Helper static method used for counting all lines in .csv file.
     * 
     * @param filePath
     * @return
     * @throws IOException 
     */
    private static int countLinesInCsv(String filePath) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        }
    }
    
    /**
     * Imports data from csv file to class trace
     * 
     * @param filePath
     * @param timeColumn
     * @param voltageColumn
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static Trace importFromCsv(String filePath, int timeColumn, int voltageColumn, boolean notSkipping) throws FileNotFoundException, IOException {
        String[] csvRow;
        int linesInCsv = countLinesInCsv(filePath) - DATA_STARTING_LINE;                                // need to substract non-data lines
        int sizeNeededForArray = notSkipping ? linesInCsv : linesInCsv / DataManager.SKIPPING_CONSTANT; // decide whether skipping or not and if yes then divide number of lines by skipping constant
        int addDataPositionIndex = 0;
        int allCyclesCounter = 0;
        String timeUnit;
        String voltageUnit;
        double timeValue;
        double voltageValue;
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            try (CSVReader csvReader = new CSVReader(bufferedReader)) {
                csvRow = csvReader.readNext();
                if (csvRow.length < timeColumn && csvRow.length < voltageColumn) 
                    throw new IOException("Invalid format of CSV file.");
                csvRow = csvReader.readNext();
                timeUnit = csvRow[timeColumn];
                voltageUnit = csvRow[voltageColumn];
                csvReader.skip(1);

                Trace trace = new Trace(sizeNeededForArray, voltageUnit, timeUnit);

                while ((csvRow = csvReader.readNext()) != null && addDataPositionIndex < sizeNeededForArray) {
                    if (notSkipping || !DataManager.skipFunction(allCyclesCounter)) {
                        timeValue = Double.parseDouble(csvRow[timeColumn]);
                        voltageValue = Double.parseDouble(csvRow[voltageColumn]);
                        trace.addData(voltageValue, timeValue, addDataPositionIndex);
                        addDataPositionIndex++;
                    }
                    allCyclesCounter++;
                }
                return trace;    
            }
        }
    }
}
