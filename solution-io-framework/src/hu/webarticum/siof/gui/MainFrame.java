package hu.webarticum.siof.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import hu.webarticum.siof.example.TextExample;
import hu.webarticum.siof.framework.TextSolution;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;


    private JSplitPane outerSplitPane;
    
    private JSplitPane topSplitPane;
    
    private JSplitPane bottomSplitPane;
    
    private JComboBox<TextSolution> solutionComboBox;
    
    private JCheckBox reloadContentsCheckBox;
    
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
        setTitle("Solution tester window");
        
        outerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        makeSplitPaneResettable(outerSplitPane, 0.4);
        setContentPane(outerSplitPane);
        
        topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        makeSplitPaneResettable(topSplitPane, 0.5);
        outerSplitPane.add(topSplitPane);
        
        bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        makeSplitPaneResettable(bottomSplitPane, 0.5);
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
        solutionComboBox.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent ev) {
                loadSolution();
            }
            
        });
        solutionSelectPanel.add(solutionComboBox);

        JPanel reloadContentsPanel = new JPanel();
        reloadContentsPanel.setLayout(new BorderLayout());
        settingsPanel.add(reloadContentsPanel);
        
        reloadContentsCheckBox = new JCheckBox("Reload contents automatically");
        reloadContentsCheckBox.setSelected(true);
        reloadContentsCheckBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (reloadContentsCheckBox.isSelected()) {
                    loadSolution();
                }
            }
            
        });
        reloadContentsPanel.add(reloadContentsCheckBox, BorderLayout.LINE_START);
        
        JPanel useCheckOutputPanel = new JPanel();
        useCheckOutputPanel.setLayout(new BorderLayout());
        settingsPanel.add(useCheckOutputPanel);
        
        useCheckOutputContentPanelCheckBox = new JCheckBox("Enable expected output");
        useCheckOutputContentPanelCheckBox.setSelected(false);
        useCheckOutputContentPanelCheckBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ev) {
                checkOutputContentPanel.setEnabled(useCheckOutputContentPanelCheckBox.isSelected());
            }
            
        });
        useCheckOutputPanel.add(useCheckOutputContentPanelCheckBox, BorderLayout.LINE_START);
        
        
        controlPanel.setPreferredSize(new Dimension(390, 180));
        checkOutputContentPanel.setPreferredSize(new Dimension(390, 180));
        inputContentPanel.setPreferredSize(new Dimension(390, 300));
        outputContentPanel.setPreferredSize(new Dimension(390, 300));
        
        
        pack();
        
        
        loadSolution();
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
    
    private void loadSolution() {
        TextSolution solution = (TextSolution)solutionComboBox.getSelectedItem();
        if (reloadContentsCheckBox.isSelected()) {
            if (solution instanceof TextExample) {
                inputContentPanel.setContent(((TextExample)solution).getSampleInputContent());
                outputContentPanel.setContent("");
                checkOutputContentPanel.setContent("");
                runSolution();
            } else {
                inputContentPanel.setContent("");
                outputContentPanel.setContent("");
                checkOutputContentPanel.setContent("");
            }
        }
    }
    
    private void makeSplitPaneResettable(final JSplitPane splitPane, final double value) {
        splitPane.setDividerLocation(-1);
        splitPane.setResizeWeight(value);
        SplitPaneUI splitPaneUi = splitPane.getUI();
        if (splitPaneUi instanceof BasicSplitPaneUI) {
            BasicSplitPaneDivider divider = ((BasicSplitPaneUI)splitPaneUi).getDivider();
            divider.addMouseListener(new MouseListener() {
                
                @Override
                public void mouseReleased(MouseEvent ev) {
                }
                
                @Override
                public void mousePressed(MouseEvent ev) {
                }
                
                @Override
                public void mouseExited(MouseEvent ev) {
                }
                
                @Override
                public void mouseEntered(MouseEvent ev) {
                }
                
                @Override
                public void mouseClicked(MouseEvent ev) {
                    if (ev.getButton() == 1 && ev.getClickCount() == 2) {
                        splitPane.setDividerLocation(-1);
                        splitPane.setResizeWeight(value);
                    }
                }
            });
        }
    }
    
}
