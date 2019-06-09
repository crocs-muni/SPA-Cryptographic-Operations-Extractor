package sbapr.ssp.dataprocessing;

import com.opencsv.CSVReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import sbapr.ssp.models.Trace;

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
        int linesInCsv = notSkipping ? countLinesInCsv(filePath) : countLinesInCsv(filePath) / 2;
        int counter = 0;
        int index = 0;
        String timeUnit;
        String voltageUnit;
        double time;
        double voltage;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            CSVReader csvReader = new CSVReader(br);
            
            csvRow = csvReader.readNext();
            if (csvRow.length < timeColumn && csvRow.length < voltageColumn) throw new IllegalArgumentException();
            csvRow = csvReader.readNext();
            timeUnit = csvRow[timeColumn];
            voltageUnit = csvRow[voltageColumn];
            csvReader.skip(1);

            Trace trace = new Trace(linesInCsv - DATA_STARTING_LINE + 2, voltageUnit, timeUnit);

            while ((csvRow = csvReader.readNext()) != null) {
                if (notSkipping || !DataManager.skipFunction(index)) {
                    time = Double.parseDouble(csvRow[timeColumn]);
                    voltage = Double.parseDouble(csvRow[voltageColumn]);
                    trace.addData(voltage, time, counter);
                    counter++;
                }
                index++;
            }
            return trace;
        }
    }
}
