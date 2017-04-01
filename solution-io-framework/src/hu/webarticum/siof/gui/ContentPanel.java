package hu.webarticum.siof.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ContentPanel extends JLayeredPane {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;
    
    private JLabel titleLabel;

    private JTextArea contentTextArea;

    private JTextField fileTextField;
    
    private JPanel overlay;
    
    private DropTarget dropTarget;

    public ContentPanel(String title, Color backgroundColor, String content, String associatedFilePath) {
        build();
        setTitle(title);
        setBackgroundColor(backgroundColor);
        setContent(content);
        setAssociatedFilePath(associatedFilePath);
    }
    
    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setBackgroundColor(Color backgroundColor) {
        setBackground(backgroundColor);
    }

    public void setContent(String content) {
        contentTextArea.setText(content);
    }

    public void setAssociatedFilePath(String associatedFilePath) {
        fileTextField.setText(associatedFilePath);
    }
    
    public void tryToLoadFromAssociatedFile(boolean askToConfirmOverwrite) {
        if (fileTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please specify filename!", "No filename", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        File file = new File(fileTextField.getText());
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "The specified file does not exists!!", "File not found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (askToConfirmOverwrite && !contentTextArea.getText().isEmpty()) {
            if (JOptionPane.showConfirmDialog(this, "Overwrite existing content?") != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        try {
            contentTextArea.setText(SiofGui.readFile(file));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "File could not be loaded!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tryToSaveToAssociatedFile(boolean askToConfirmReplace) {
        if (fileTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please specify filename!", "No filename", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        File file = new File(fileTextField.getText());
        if (askToConfirmReplace && file.exists()) {
            JOptionPane.showMessageDialog(this, "The specified file does not exists!!", "File not found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            SiofGui.writeFile(file, contentTextArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "File could not be written!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void build() {
        mainPanel = new JPanel();
        mainPanel.setBounds(new Rectangle(0, 0, 200, 200));
        add(mainPanel, new Integer(0));
        
        mainPanel.setLayout(new BorderLayout());
        
        titleLabel = new JLabel();
        mainPanel.add(titleLabel, BorderLayout.PAGE_START);
        
        contentTextArea = new JTextArea();
        JScrollPane contentScrollPane = new JScrollPane(contentTextArea);
        mainPanel.add(contentScrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        bottomPanel.add(new JLabel("File:"), BorderLayout.LINE_START);
        
        fileTextField = new JTextField();
        bottomPanel.add(fileTextField, BorderLayout.CENTER);
        
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(bottomRightPanel, BorderLayout.LINE_END);
        
        JButton loadFileButton = new JButton("Load");
        loadFileButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ev) {
                tryToLoadFromAssociatedFile(true);
            }
            
        });
        bottomRightPanel.add(loadFileButton);

        JButton saveFileButton = new JButton("Save");
        saveFileButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ev) {
                tryToSaveToAssociatedFile(true);
            }
            
        });
        bottomRightPanel.add(saveFileButton);
        
        
        // XXX
        
        overlay = new JPanel();
        overlay.setBounds(new Rectangle(100, 100, 200, 200));
        overlay.setVisible(false);
        add(overlay, new Integer(1));
        
        addComponentListener(new ComponentListener() {
            
            @Override
            public void componentShown(ComponentEvent ev) {
                componentResized(ev);
            }
            
            @Override
            public void componentResized(ComponentEvent ev) {
                SwingUtilities.invokeLater(new Runnable() {
                    
                    @Override
                    public void run() {
                        mainPanel.setSize(ContentPanel.this.getSize());
                        overlay.setSize(ContentPanel.this.getSize());
                        ContentPanel.this.repaint();
                    }
                    
                });
            }
            
            @Override
            public void componentMoved(ComponentEvent ev) {
            }
            
            @Override
            public void componentHidden(ComponentEvent ev) {
            }
        });
        
        /*
        dropTarget = new DropTarget(this, new DropTargetListener() {
            
            @Override
            public void dropActionChanged(DropTargetDragEvent ev) {
                // TODO
            }
            
            @Override
            public void drop(DropTargetDropEvent ev) {
                // TODO
                System.out.println("DROP!");
                ev.rejectDrop();
            }
            
            @Override
            public void dragOver(DropTargetDragEvent ev) {
                // TODO
            }
            
            @Override
            public void dragExit(DropTargetEvent ev) {
                // TODO
            }
            
            @Override
            public void dragEnter(DropTargetDragEvent ev) {
                // TODO
            }
            
        });*/
    }
    
}
