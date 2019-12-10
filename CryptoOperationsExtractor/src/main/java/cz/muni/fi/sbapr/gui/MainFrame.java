/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import cz.muni.fi.sbapr.chartprocessing.Boundaries;
import cz.muni.fi.sbapr.chartprocessing.ChartManager;
import cz.muni.fi.sbapr.chartprocessing.CustomChartMouseListener;
import cz.muni.fi.sbapr.dataprocessing.DataManager;
import cz.muni.fi.sbapr.dataprocessing.ExtractionHelp;
import cz.muni.fi.sbapr.models.CreateNewCardModel;
import cz.muni.fi.sbapr.models.MainFrameModel;
import java.awt.Event;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * @author Martin
 */
public class MainFrame extends javax.swing.JFrame {
    /**
     * Creates new form NewJFrame
     */
    public MainFrame() {
        initComponents();
        this.mainFrameModel = new MainFrameModel();
        setup();
    }

    protected class ResetValuesAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mainFrameModel != null
                    && firstSelectedTimeJSpinner != null
                    && lastSelectedTimeJSpinner != null
                    && saveOperationJButton != null) {
                mainFrameModel.setFirstIndexOnChartTrace(null);
                mainFrameModel.setLastIndexOnChartTrace(null);
                firstSelectedTimeJSpinner.setValue(0);
                lastSelectedTimeJSpinner.setValue(0);
                saveOperationJButton.setEnabled(false);
            }
        }
    }
    
    protected class AutoRangeChartAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (traceJPanel != null && traceJPanel.getComponentCount() > 0) {
                ChartPanel chartPanel = (ChartPanel) traceJPanel.getComponent(0);
                if (chartPanel != null) {
                    chartPanel.restoreAutoBounds();
                }
            }
        }
    }

    protected class HighlightAreaChartAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!highlightAreaJButton.isEnabled()) {
                return;
            }
            if (mainFrameModel.getFirstIndexOnChartTrace() == null
                    || mainFrameModel.getFirstIndexOnChartTrace() <= 0
                    || mainFrameModel.getLastIndexOnChartTrace() == null
                    || mainFrameModel.getLastIndexOnChartTrace() <= 0
                    || mainFrameModel.getLastIndexOnChartTrace() <= mainFrameModel.getFirstIndexOnChartTrace()) {
                JOptionPane.showMessageDialog(MainFrame.this, "Could not highlight area.");
                return;
            }
            Boundaries boundaries = new Boundaries(
                    mainFrameModel.getCurrentTrace().getTimeOnPosition(ChartManager.modifiedToOriginalIndex(mainFrameModel.getFirstIndexOnChartTrace()))
                    , mainFrameModel.getCurrentTrace().getTimeOnPosition(ChartManager.modifiedToOriginalIndex(mainFrameModel.getLastIndexOnChartTrace()))
                    , mainFrameModel.getFirstIndexOnChartTrace()
                    , mainFrameModel.getLastIndexOnChartTrace());
            List<Boundaries> boundariesList = new ArrayList<>();
            boundariesList.add(boundaries);
            highlightArea(boundariesList, DataManager.getOperationInfoForInstruction(mainFrameModel.getCurrentOperationRecord().getInstructionName()).getCodeName());
            saveOperationJButton.setEnabled(true);
        }
    }
    
    protected class SaveOperationAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (saveOperationJButton.isEnabled()) {
                boolean saved = saveOperation(
                    ChartManager.modifiedToOriginalIndex(mainFrameModel.getFirstIndexOnChartTrace())
                    , ChartManager.modifiedToOriginalIndex(mainFrameModel.getLastIndexOnChartTrace()));
                if (saved) fillComboBoxIfCalibrationOperation();
            }
        }
    }
    
    private final HashMap<KeyStroke, Action> actionMap = new HashMap<>();

    private void setup() {
        KeyStroke keyResetValues = KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK);
        KeyStroke keyAutoRange = KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK);
        KeyStroke keySaveOperation = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
        KeyStroke keyHighlightArea = KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK);
        actionMap.put(keyResetValues, new ResetValuesAction());
        actionMap.put(keyAutoRange, new AutoRangeChartAction());
        actionMap.put(keySaveOperation, new SaveOperationAction());
        actionMap.put(keyHighlightArea, new HighlightAreaChartAction());

        KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kfm.addKeyEventDispatcher((KeyEvent e) -> {
            KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
            if ( actionMap.containsKey(keyStroke) ) {
                final Action a = actionMap.get(keyStroke);
                final ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), null );
                SwingUtilities.invokeLater(() -> {
                    a.actionPerformed(ae);
                }); 
            return true;
            }
            return false;
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator4 = new javax.swing.JSeparator();
        traceJPanel = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        firstSelectedTimeJLabel = new javax.swing.JLabel();
        lastSelectedTimeJLabel = new javax.swing.JLabel();
        firstSelectedTimeJSpinner = new javax.swing.JSpinner();
        lastSelectedTimeJSpinner = new javax.swing.JSpinner();
        highlightAreaJButton = new javax.swing.JButton();
        saveOperationJButton = new javax.swing.JButton();
        continueJButton = new javax.swing.JButton();
        enableSimilaritySearchJCheckBox = new javax.swing.JCheckBox();
        chooseCalibrationOperationJLabel = new javax.swing.JLabel();
        delimitingOperationJComboBox = new javax.swing.JComboBox<>();
        createNewCardJButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        helpTextArea = new java.awt.TextArea();
        jSeparator1 = new javax.swing.JSeparator();
        logTextArea = new java.awt.TextArea();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(964, 800));

        traceJPanel.setBackground(new java.awt.Color(255, 255, 255));
        traceJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        traceJPanel.setPreferredSize(new java.awt.Dimension(940, 400));
        traceJPanel.setLayout(new java.awt.BorderLayout());
        traceJPanel.add(jSeparator3, java.awt.BorderLayout.PAGE_END);

        firstSelectedTimeJLabel.setText("First selected time:");

        lastSelectedTimeJLabel.setText("Last selected time:");

        firstSelectedTimeJSpinner.setEnabled(false);

        lastSelectedTimeJSpinner.setEnabled(false);

        highlightAreaJButton.setText("Highlight area");
        highlightAreaJButton.setEnabled(false);
        highlightAreaJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightAreaJButtonActionPerformed(evt);
            }
        });

        saveOperationJButton.setText("Save operation");
        saveOperationJButton.setEnabled(false);
        saveOperationJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveOperationJButtonActionPerformed(evt);
            }
        });

        continueJButton.setText("Continue");
        continueJButton.setEnabled(false);
        continueJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continueJButtonActionPerformed(evt);
            }
        });

        enableSimilaritySearchJCheckBox.setText("Enable similarity search");
        enableSimilaritySearchJCheckBox.setEnabled(false);
        enableSimilaritySearchJCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        enableSimilaritySearchJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableSimilaritySearchJCheckBoxActionPerformed(evt);
            }
        });

        chooseCalibrationOperationJLabel.setText("Similarity search operation:");

        delimitingOperationJComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No operation" }));
        delimitingOperationJComboBox.setEnabled(false);
        delimitingOperationJComboBox.setMaximumSize(new java.awt.Dimension(315, 22));

        createNewCardJButton.setText("Create new card");
        createNewCardJButton.setPreferredSize(new java.awt.Dimension(130, 25));
        createNewCardJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewCardJButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("ms");

        jLabel2.setText("ms");

        helpTextArea.setBackground(new java.awt.Color(255, 255, 255));
        helpTextArea.setEditable(false);
        helpTextArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        helpTextArea.setPreferredSize(new java.awt.Dimension(513, 212));

        logTextArea.setBackground(new java.awt.Color(255, 255, 255));
        logTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        logTextArea.setEditable(false);
        logTextArea.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        logTextArea.setPreferredSize(new java.awt.Dimension(417, 364));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(traceJPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(helpTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(enableSimilaritySearchJCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lastSelectedTimeJLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lastSelectedTimeJSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel2)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(firstSelectedTimeJLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(firstSelectedTimeJSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(continueJButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(saveOperationJButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(highlightAreaJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chooseCalibrationOperationJLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delimitingOperationJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createNewCardJButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(createNewCardJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(helpTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(firstSelectedTimeJLabel)
                                    .addComponent(firstSelectedTimeJSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addComponent(highlightAreaJButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lastSelectedTimeJLabel)
                                    .addComponent(lastSelectedTimeJSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(saveOperationJButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(enableSimilaritySearchJCheckBox)
                                    .addComponent(continueJButton)))
                            .addComponent(jSeparator2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chooseCalibrationOperationJLabel)
                            .addComponent(delimitingOperationJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(logTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(traceJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void showCreateNewCardDialog() {
        CreateNewCardModel createNewCardModel = new CreateNewCardModel();
        CreateNewJCardDialog createNewJCardDialog = new CreateNewJCardDialog(this, true, createNewCardModel);
        mainFrameModel.setCreateNewCardModel(createNewCardModel);
        createNewJCardDialog.dispose();
    }
    
    private void logCreateNewCardAction() {
        logTextArea.append("Started process of creating new card operations database.\n");
        logTextArea.append("Source directory of traces: " + mainFrameModel.getCreateNewCardModel().getSourceDirectory() + ".\n");
        logTextArea.append("Destination directory of traces: " + mainFrameModel.getCreateNewCardModel().getDestinationDirectory() + ".\n");
    }
    
    private void fillMainFrameModelOperationRecords() {
        logTextArea.append("----------------------------------------------------------------\n");
        logTextArea.append("Creating log of present operations in the destination directory.\n");
        mainFrameModel.getCreateNewCardModel().getOperationRecords().forEach((operationRecord) -> {
            mainFrameModel.getOperationRecords().add(operationRecord);
            logTextArea.append(operationRecord.getInstructionName() + " - " + operationRecord.getOperationName() + " - " + operationRecord.getPlace() + "\n");
        });
        logTextArea.append("----------------------------------------------------------------\n");
    }
   
    private void highlightArea(Collection<Boundaries> delimitingOperations, Collection<Boundaries> newOperations, String operationText) {
        ChartPanel chartPanel = (ChartPanel) traceJPanel.getComponent(0);
        ChartManager.highlightChart(chartPanel, delimitingOperations, newOperations, operationText);
    }

    private void highlightArea(Collection<Boundaries> operations, String operationText) {
        ChartPanel chartPanel = (ChartPanel) traceJPanel.getComponent(0);
        ChartManager.highlightChart(chartPanel, operations);
    }
    
    private void highlightAreaJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightAreaJButtonActionPerformed
        if (mainFrameModel.getFirstIndexOnChartTrace() == null
                || mainFrameModel.getFirstIndexOnChartTrace() <= 0
                || mainFrameModel.getLastIndexOnChartTrace() == null
                || mainFrameModel.getLastIndexOnChartTrace() <= 0
                || mainFrameModel.getLastIndexOnChartTrace() <= mainFrameModel.getFirstIndexOnChartTrace()) {
            JOptionPane.showMessageDialog(this, "Could not highlight area.");
            return;
        }
        Boundaries boundaries = new Boundaries(
                mainFrameModel.getCurrentTrace().getTimeOnPosition(ChartManager.modifiedToOriginalIndex(mainFrameModel.getFirstIndexOnChartTrace()))
                , mainFrameModel.getCurrentTrace().getTimeOnPosition(ChartManager.modifiedToOriginalIndex(mainFrameModel.getLastIndexOnChartTrace()))
                , mainFrameModel.getFirstIndexOnChartTrace()
                , mainFrameModel.getLastIndexOnChartTrace());
        List<Boundaries> boundariesList = new ArrayList<>();
        boundariesList.add(boundaries);
        highlightArea(boundariesList, DataManager.getOperationInfoForInstruction(mainFrameModel.getCurrentOperationRecord().getInstructionName()).getCodeName());
        saveOperationJButton.setEnabled(true);
    }//GEN-LAST:event_highlightAreaJButtonActionPerformed

    private void fillComboBoxIfCalibrationOperation() {
        boolean alreadyIn = false;
        for (int i = 0; i < delimitingOperationJComboBox.getItemCount(); i++) {
            if (delimitingOperationJComboBox.getItemAt(i)
                    .equals(mainFrameModel.getCurrentOperationRecord().getPlace().toString() + ". " + mainFrameModel.getCurrentOperationRecord().getOperationName())) {
                alreadyIn = true;
            }
        }
        if (!alreadyIn && mainFrameModel.getCurrentOperationRecord().getInstructionName().equals(DataManager.DELIMITING_OPERATION_INSTRUCTION)) {
            delimitingOperationJComboBox.addItem(mainFrameModel.getCurrentOperationRecord().getPlace().toString() + ". " + mainFrameModel.getCurrentOperationRecord().getOperationName());
            delimitingOperationJComboBox.setSelectedIndex(delimitingOperationJComboBox.getItemCount() - 1);
        }
    }
    
    private boolean saveOperation(int firstIndex, int lastIndex) {
        String fullPath = mainFrameModel.getCreateNewCardModel().getDestinationDirectory() 
                    + "\\" + mainFrameModel.getCurrentOperationRecord().getPlace().toString() + ". "
                    + mainFrameModel.getCurrentOperationRecord().getOperationName() + ".csv";
            logTextArea.append("Saving highlighted area.");
        try {
            DataManager.saveTrace(
                    fullPath
                    , mainFrameModel.getCurrentTrace()
                    , firstIndex
                    , lastIndex);
            logTextArea.append(" - OK\n");
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save the trace.");
            logTextArea.append(" - NOK\n");
            return false;
        }
    }
    
    /**
     * @param evt 
     */
    private void saveOperationJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveOperationJButtonActionPerformed
        saveOperation(
                ChartManager.modifiedToOriginalIndex(mainFrameModel.getFirstIndexOnChartTrace())
                , ChartManager.modifiedToOriginalIndex(mainFrameModel.getLastIndexOnChartTrace()));
        fillComboBoxIfCalibrationOperation();
    }//GEN-LAST:event_saveOperationJButtonActionPerformed
    
    private void continueWithNewOperation() {
        try {
            handleOperationExtractionProcess();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error occurred while loading the trace.");
            handleProcessFinished();
        }
    }
    
    private void savePngBeforeFinish() throws FileNotFoundException, IOException {
        String fullPath = mainFrameModel.getCreateNewCardModel().getDestinationDirectory() 
            + "\\" + mainFrameModel.getCurrentOperationRecord().getOperationName()
            +"(" + mainFrameModel.getCurrentOperationRecord().getPlace().toString() + ")" + ".png";
        ChartPanel chartPanel = (ChartPanel) traceJPanel.getComponent(0);
        try (OutputStream out = new FileOutputStream(fullPath)) {
            ChartUtilities.writeChartAsPNG(out, chartPanel.getChart(), chartPanel.getWidth(), chartPanel.getHeight());
        }
    }
    
    private void continueJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continueJButtonActionPerformed
        continueWithNewOperation();
    }//GEN-LAST:event_continueJButtonActionPerformed

    private void enableSimilaritySearchJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableSimilaritySearchJCheckBoxActionPerformed
        if (enableSimilaritySearchJCheckBox.isSelected()) {
            delimitingOperationJComboBox.setEnabled(true);
        } else {
            delimitingOperationJComboBox.setEnabled(false);           
        }
    }//GEN-LAST:event_enableSimilaritySearchJCheckBoxActionPerformed

    private void createNewCardJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewCardJButtonActionPerformed
        handleProcessFinished();
        showCreateNewCardDialog();
        
        if (!mainFrameModel.getCreateNewCardModel().isFinishedProcess())  {
            mainFrameModel.setCreateNewCardModel(null);
            return;
        }
        
        logCreateNewCardAction();
        fillMainFrameModelOperationRecords();
        
        try {
            logTextArea.append("Strating operation extraction process.\n");
            handleOperationExtractionProcess();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error occurred while loading the trace.");
            handleProcessFinished();
        }
    }//GEN-LAST:event_createNewCardJButtonActionPerformed
    
    private void prepareHandleOperationExtractionProcess() {
        firstSelectedTimeJSpinner.setEnabled(true);
        lastSelectedTimeJSpinner.setEnabled(true);
        highlightAreaJButton.setEnabled(true);
        continueJButton.setEnabled(true);
        saveOperationJButton.setEnabled(false);
        enableSimilaritySearchJCheckBox.setEnabled(true);
        delimitingOperationJComboBox.setEnabled(true);
        firstSelectedTimeJSpinner.setValue(0);
        lastSelectedTimeJSpinner.setValue(0);
        traceJPanel.removeAll();
        traceJPanel.updateUI();
        mainFrameModel.setFirstIndexOnChartTrace(null);
        mainFrameModel.setLastIndexOnChartTrace(null);
        mainFrameModel.setCurrentTrace(null);
        System.gc();
    }
    
    private void setCurrentTraceAndLoad() throws IOException {
        mainFrameModel.setCurrentOperationRecord(mainFrameModel.getOperationRecords().first());
        mainFrameModel.getOperationRecords().remove(mainFrameModel.getCurrentOperationRecord());
        mainFrameModel.setCurrentTrace(
                DataManager.loadTrace(mainFrameModel
                .getCurrentOperationRecord()
                .getFilePath()
                .getAbsolutePath(), false));
        logTextArea.append("Trace for " + mainFrameModel.getCurrentOperationRecord().getOperationName() + " loaded.\n");
    }
    
    private void displayCodeForCurrentTrace() {
        helpTextArea.setText("Code executed while measuring this trace:\n\n"
                + ExtractionHelp.codeForInstruction(mainFrameModel.getCurrentOperationRecord().getInstructionName()));
    }
    
    private void handleOperationExtractionProcess() throws IOException {
        prepareHandleOperationExtractionProcess();

        if (mainFrameModel.getOperationRecords().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All operations were extracted.");
            handleProcessFinished();
            return;
        }
        
        setCurrentTraceAndLoad();
        
        displayCurrentTraceOnChartPanel();
        
        displayCodeForCurrentTrace();
        
        handleSimilaritySearch();
    }
    
    private void handleProcessFinished() {
        highlightAreaJButton.setEnabled(false);
        saveOperationJButton.setEnabled(false);
        continueJButton.setEnabled(false);
        firstSelectedTimeJSpinner.setEnabled(false);
        lastSelectedTimeJSpinner.setEnabled(false);
        enableSimilaritySearchJCheckBox.setEnabled(false);
        enableSimilaritySearchJCheckBox.setSelected(false);
        firstSelectedTimeJSpinner.setEnabled(false);
        lastSelectedTimeJSpinner.setEnabled(false);
        delimitingOperationJComboBox.setEnabled(false);
        logTextArea.setText("");
        helpTextArea.setText("");
        traceJPanel.removeAll();
        traceJPanel.revalidate();
        traceJPanel.repaint();
        mainFrameModel.resetMainFrameModel();
        firstSelectedTimeJSpinner.setValue(0);
        lastSelectedTimeJSpinner.setValue(0);
        for (int i = 1; i < delimitingOperationJComboBox.getItemCount(); i++) {
            delimitingOperationJComboBox.removeItemAt(i);
        }
        System.gc();
    }
    
    private void displayCurrentTraceOnChartPanel() {
        logTextArea.append("Started process of displaying");
        
        traceJPanel.removeAll();
        ChartPanel chartPanel = ChartManager.plotChart(mainFrameModel.getCurrentTrace(), mainFrameModel.getCurrentOperationRecord().getOperationName());
        chartPanel.setPreferredSize(new Dimension(traceJPanel.getWidth(), traceJPanel.getHeight()));
        chartPanel.addChartMouseListener(
                new CustomChartMouseListener(chartPanel, firstSelectedTimeJSpinner, lastSelectedTimeJSpinner, mainFrameModel));
        traceJPanel.add(chartPanel, BorderLayout.CENTER);
        traceJPanel.validate();
        logTextArea.append(" - OK\n");
        highlightAreaJButton.setEnabled(true);
    }
    
    private List<Boundaries> findSimilarities() {
        List<Boundaries> similaritiesBoundaries = new ArrayList<>();
        ProgressBarJDialog progressBarJDialog = new ProgressBarJDialog(this, true);
        SimilaritySearchSwingWorker similaritySearchTask = new SimilaritySearchSwingWorker(mainFrameModel, similaritiesBoundaries, progressBarJDialog);
        progressBarJDialog.setTask(similaritySearchTask);
        similaritySearchTask.execute();
        progressBarJDialog.setVisible(true);
        progressBarJDialog.dispose();
        return similaritiesBoundaries;
    }
    
    private void handleSimilaritySearch() throws IOException {
        if (mainFrameModel.getCurrentOperationRecord().getInstructionName().equals("B0")
                || mainFrameModel.getCurrentOperationRecord().getInstructionName().equals("B7")
                || mainFrameModel.getCurrentOperationRecord().getInstructionName().equals("B9")
                || mainFrameModel.getCurrentOperationRecord().getInstructionName().equals("BB")
                || !enableSimilaritySearchJCheckBox.isSelected()
                || delimitingOperationJComboBox.getSelectedItem().equals("No operation")) {
            logTextArea.append("Skipping similarity search.\n");
            return;
        }
        
        String fullPath = mainFrameModel.getCreateNewCardModel().getDestinationDirectory() + "\\" + delimitingOperationJComboBox.getSelectedItem() + ".csv";
        mainFrameModel.setCalibrationTrace(DataManager.loadTrace(fullPath, true));
        
        logTextArea.append("Started process of similarity searching");
        
        List<Boundaries> similaritiesBoundaries = findSimilarities();
        
        if (similaritiesBoundaries != null && similaritiesBoundaries.size() == 6) {
            logTextArea.append(" - OK\n");
            List<Boundaries> newOperationBoundaries = new ArrayList<>();
            Collections.sort(similaritiesBoundaries);
            newOperationBoundaries.add(
                    new Boundaries(similaritiesBoundaries.get(0).getUpperBound()
                    , similaritiesBoundaries.get(1).getLowerBound()
                    , similaritiesBoundaries.get(0).getLastIndex()
                    , similaritiesBoundaries.get(1).getFirstIndex()));
            newOperationBoundaries.add(
                    new Boundaries(similaritiesBoundaries.get(2).getUpperBound()
                    , similaritiesBoundaries.get(3).getLowerBound()
                    , similaritiesBoundaries.get(2).getLastIndex()
                    , similaritiesBoundaries.get(3).getFirstIndex()));
            highlightArea(similaritiesBoundaries
                    , newOperationBoundaries
                    , DataManager.getOperationInfoForInstruction(mainFrameModel.getCurrentOperationRecord().getInstructionName()).getCodeName());
            CheckSimilaritySearchJDialog similaritySearchResultCheck = new CheckSimilaritySearchJDialog(this, true);
            similaritySearchResultCheck.setVisible(true);
            if (similaritySearchResultCheck.isResultOk()) {
                saveOperation(newOperationBoundaries.get(0).getFirstIndex(), newOperationBoundaries.get(0).getLastIndex());
            }
            similaritySearchResultCheck.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Similarity search operation unsuccessful.");
            logTextArea.append(" - NOK\n");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>      

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });
    }

    private final MainFrameModel mainFrameModel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chooseCalibrationOperationJLabel;
    private javax.swing.JButton continueJButton;
    private javax.swing.JButton createNewCardJButton;
    private javax.swing.JComboBox<String> delimitingOperationJComboBox;
    private javax.swing.JCheckBox enableSimilaritySearchJCheckBox;
    private javax.swing.JLabel firstSelectedTimeJLabel;
    private javax.swing.JSpinner firstSelectedTimeJSpinner;
    private java.awt.TextArea helpTextArea;
    private javax.swing.JButton highlightAreaJButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lastSelectedTimeJLabel;
    private javax.swing.JSpinner lastSelectedTimeJSpinner;
    private java.awt.TextArea logTextArea;
    private javax.swing.JButton saveOperationJButton;
    private javax.swing.JPanel traceJPanel;
    // End of variables declaration//GEN-END:variables
}
