package hu.webarticum.siof.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import hu.webarticum.siof.framework.TextSolution;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    
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

        JPanel settingsPanel = new JPanel();
        settingsPanel.add(new JLabel("TODO: solutions, settings, run"));
        topSplitPane.add(settingsPanel);

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
        
        settingsPanel.setPreferredSize(new Dimension(390, 180));
        checkOutputContentPanel.setPreferredSize(new Dimension(390, 180));
        inputContentPanel.setPreferredSize(new Dimension(390, 300));
        outputContentPanel.setPreferredSize(new Dimension(390, 300));
        
        pack();
    }

}
