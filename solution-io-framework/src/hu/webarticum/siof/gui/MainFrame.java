package hu.webarticum.siof.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import hu.webarticum.siof.framework.TextSolution;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    
    private JComboBox<TextSolution> solutionComboBox;
    
    private JCheckBox useCheckOutputContentPanelCheckBox;
    
    private ContentPanel inputContentPanel;
    
    private ContentPanel outputContentPanel;
    
    private ContentPanel checkOutputContentPanel;
    
    
    public MainFrame(
        List<TextSolution> solutions,
        File inputFile,
        String inputContent,
        File outputFile,
        String outputContent,
        File checkOutputFile,
        String checkOutputContent
    ) {
        JSplitPane outerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        outerSplitPane.setResizeWeight(0.3);
        setContentPane(outerSplitPane);
        
        JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        topSplitPane.setResizeWeight(0.5);
        outerSplitPane.add(topSplitPane);
        
        JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bottomSplitPane.setResizeWeight(0.5);
        outerSplitPane.add(bottomSplitPane);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topSplitPane.add(controlPanel);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));
        controlPanel.add(settingsPanel, BorderLayout.PAGE_START);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        controlPanel.add(buttonPanel, BorderLayout.PAGE_END);
        
        JButton runButton = new JButton("Run");
        runButton.setPreferredSize(new Dimension(70, 35));
        runButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                runSolution();
            }
            
        });
        buttonPanel.add(runButton, BorderLayout.LINE_END);
        
        checkOutputContentPanel = new ContentPanel(
            "Expected", new Color(0x596E9B), checkOutputContent,
            checkOutputFile == null ? "" : checkOutputFile.getPath()
        );
        checkOutputContentPanel.setEnabled(false);
        topSplitPane.add(checkOutputContentPanel);

        inputContentPanel = new ContentPanel(
            "Input", new Color(0x6F9B59), inputContent, inputFile == null ? "" : inputFile.getPath()
        );
        bottomSplitPane.add(inputContentPanel);

        outputContentPanel = new ContentPanel(
            "Output", new Color(0x9B5F59), outputContent, outputFile == null ? "" : outputFile.getPath()
        );
        bottomSplitPane.add(outputContentPanel);
        
        
        JPanel solutionSelectPanel = new JPanel();
        solutionSelectPanel.setLayout(new BorderLayout());
        settingsPanel.add(solutionSelectPanel, BorderLayout.CENTER);

        solutionSelectPanel.add(new JLabel("Solution:"), BorderLayout.LINE_START);
        
        solutionComboBox = new JComboBox<>();
        TextSolution[] solutionArray = solutions.toArray(new TextSolution[solutions.size()]);
        solutionComboBox.setModel(new DefaultComboBoxModel<>(solutionArray));
        solutionSelectPanel.add(solutionComboBox);
        
        
        useCheckOutputContentPanelCheckBox = new JCheckBox("Use expected");
        useCheckOutputContentPanelCheckBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ev) {
                checkOutputContentPanel.setEnabled(useCheckOutputContentPanelCheckBox.isSelected());
            }
            
        });
        settingsPanel.add(useCheckOutputContentPanelCheckBox);
        
        
        controlPanel.setPreferredSize(new Dimension(390, 180));
        checkOutputContentPanel.setPreferredSize(new Dimension(390, 180));
        inputContentPanel.setPreferredSize(new Dimension(390, 300));
        outputContentPanel.setPreferredSize(new Dimension(390, 300));
        
        
        pack();
    }
    
    public void runSolution() {
        TextSolution solution = (TextSolution)solutionComboBox.getSelectedItem();
        
        String input = inputContentPanel.getContent();
        
        StringReader inputReader = new StringReader(input);
        StringWriter outputWriter = new StringWriter();
        
        try {
            solution.solve(inputReader, outputWriter);
        } catch (Throwable e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "An error occured, have a look at the console", "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        String output = outputWriter.toString();
        
        outputContentPanel.setContent(output);
    }
    
}
