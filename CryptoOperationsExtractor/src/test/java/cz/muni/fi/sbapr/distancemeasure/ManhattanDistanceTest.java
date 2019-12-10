/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.distancemeasure;

import cz.muni.fi.sbapr.disatancemeasure.ManhattanDistance;
import cz.muni.fi.sbapr.dataprocessing.DataManager;
import cz.muni.fi.sbapr.models.Trace;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class ManhattanDistanceTest {
    private final String currentWorkingDirectory = System.getProperty("user.dir");
    private final String pathToTrace = currentWorkingDirectory 
            + "\\src\\test\\java\\cz\\muni\\fi\\sbapr\\TestData\\Data\\TestCard\\B0_10msdiv_2MS(1).csv";
    private final String pathToOperation = currentWorkingDirectory 
            + "\\src\\test\\java\\cz\\muni\\fi\\sbapr\\TestData\\Results\\TestCard\\0. B0-secureRandom.generateData-RandomData.ALG_SECURE_RANDOM, 128B.csv";
    private final Trace trace;
    private final Trace operation;
    private final double[] biggerArray;
    private final double[] smallerArray;
    private final int subzeroIndex = -3;
    private final int zeroIndex = 0;
    private final int inBoundsIndex = 3;
    private final int canGoOutOfBoundsIndex = 10;
    private final int outOfBoundsIndex = 3301;
    private final int mesh = 1000;
    
    public ManhattanDistanceTest() throws IOException {
        trace = DataManager.loadTrace(pathToTrace, true);
        operation = DataManager.loadTrace(pathToOperation, true);
        biggerArray = new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        smallerArray = new double[]{0, 1, 2};
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void realDataTest_compareWithPrecomputedDistance() {
        ManhattanDistance distanceAlgorithm = new ManhattanDistance();
        double expResult = 70077;
        double result = distanceAlgorithm.compute(operation.getVoltage(), trace.getVoltage(), 0);
        assertTrue(Math.abs(expResult - result) < mesh);
    }
    
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void createdDataTest_compareWithSubzeroIndex() {
        ManhattanDistance distanceAlgorithm = new ManhattanDistance();
        distanceAlgorithm.compute(smallerArray, biggerArray, subzeroIndex);
    }
    
    @Test
    public void createdDataTest_compareWithZeroIndex() {
        ManhattanDistance distanceAlgorithm = new ManhattanDistance();
        double expResult = 0;
        double result = distanceAlgorithm.compute(smallerArray, biggerArray, zeroIndex);
        assertTrue(Math.abs(expResult - result) < mesh);
    }
    
    @Test
    public void createdDataTest_compareWithInBoundsIndex() {
        ManhattanDistance distanceAlgorithm = new ManhattanDistance();
        double expResult = 9;
        double result = distanceAlgorithm.compute(smallerArray, biggerArray, inBoundsIndex);
        assertTrue(Math.abs(expResult - result) < mesh);
    }
    
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void createdDataTest_compareWithCanGoOutOfBoundsIndex() {
        ManhattanDistance distanceAlgorithm = new ManhattanDistance();
        distanceAlgorithm.compute(smallerArray, biggerArray, canGoOutOfBoundsIndex);
    }
    
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void createdDataTest_compareWithOutOfBoundsIndex() {
        ManhattanDistance distanceAlgorithm = new ManhattanDistance();
        distanceAlgorithm.compute(smallerArray, biggerArray, outOfBoundsIndex);
    }
    
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void createdDataTest_swappedArrays() {
        ManhattanDistance distanceAlgorithm = new ManhattanDistance();
        distanceAlgorithm.compute(biggerArray, smallerArray, zeroIndex);
    }
    
}
