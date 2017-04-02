package hu.webarticum.siof.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
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
import java.util.List;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ContentPanel extends JLayeredPane {

    
    private static final long serialVersionUID = 1L;

    
    private JPanel mainPanel;
    
    private JLabel titleLabel;

    private JTextArea contentTextArea;
    
    private JScrollPane contentScrollPane;

    private JLabel fileLabel;
    
    private JTextField fileTextField;

    private JButton chooseFileButton;

    private JButton loadFileButton;

    private JButton saveFileButton;

    private JPanel overlay;
    
    
    private boolean enabled = true;
    
    private Color backgroundColor = Color.WHITE;;


    private final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
    
    private final Font OVERLAY_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 32);
    
    private final String OVERLAY_TEXT = "Drop file here!";
    
    private final Color DISABLED_BACKGROUND_COLOR = new Color(0xCCCCCC);

    
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
        this.backgroundColor = backgroundColor;
        if (enabled) {
            mainPanel.setBackground(backgroundColor);
        }
    }

    public void setContent(String content) {
        contentTextArea.setText(content);
    }

    public void setAssociatedFilePath(String associatedFilePath) {
        fileTextField.setText(associatedFilePath);
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        mainPanel.setBackground(enabled ? backgroundColor : DISABLED_BACKGROUND_COLOR);
        titleLabel.setEnabled(enabled);
        contentTextArea.setEnabled(enabled);
        contentScrollPane.setEnabled(enabled);
        fileLabel.setEnabled(enabled);
        fileTextField.setEnabled(enabled);
        chooseFileButton.setEnabled(enabled);
        loadFileButton.setEnabled(enabled);
        saveFileButton.setEnabled(enabled);
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
            if (!confirm("Non-empty content", "Overwrite existing content?")) {
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
            JOptionPane.showMessageDialog(this, "Please choose a file!", "No file", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        File file = new File(fileTextField.getText());
        if (askToConfirmReplace && file.exists()) {
            if (!confirm("File exists", "Overwrite the existing file?")) {
                return;
            }
        }
        
        try {
            SiofGui.writeFile(file, contentTextArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "File could not be written!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean confirm(String title, String message) {
        return (
            JOptionPane.showConfirmDialog(
                this,
                "Overwrite the existing file?",
                "File exists",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            ) == JOptionPane.YES_OPTION
        );
    }
    
    private void build() {
        mainPanel = new JPanel();
        mainPanel.setBounds(new Rectangle(0, 0, 200, 200));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel, new Integer(0));
        
        mainPanel.setLayout(new BorderLayout());
        
        titleLabel = new JLabel();
        titleLabel.setBorder(new EmptyBorder(0, 0, 4, 0));
        titleLabel.setFont(TITLE_FONT);
        mainPanel.add(titleLabel, BorderLayout.PAGE_START);
        
        contentTextArea = new JTextArea();
        contentScrollPane = new JScrollPane(contentTextArea);
        mainPanel.add(contentScrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        fileLabel = new JLabel("File:");
        bottomPanel.add(fileLabel, BorderLayout.LINE_START);
        
        fileTextField = new JTextField();
        bottomPanel.add(fileTextField, BorderLayout.CENTER);
        
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(bottomRightPanel, BorderLayout.LINE_END);
        
        chooseFileButton = new JButton("...");
        chooseFileButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ev) {
                JFileChooser fileChooser = new JFileChooser();
                if (!fileTextField.getText().isEmpty()) {
                    fileChooser.setSelectedFile(new File(fileTextField.getText()));
                }
                if (fileChooser.showDialog(ContentPanel.this, "OK") == JFileChooser.APPROVE_OPTION) {
                    fileTextField.setText(fileChooser.getSelectedFile().getPath());
                    tryToLoadFromAssociatedFile(false);
                }
            }
            
        });
        bottomRightPanel.add(chooseFileButton);
        
        loadFileButton = new JButton("Load");
        loadFileButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ev) {
                tryToLoadFromAssociatedFile(true);
            }
            
        });
        bottomRightPanel.add(loadFileButton);

        saveFileButton = new JButton("Save");
        saveFileButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ev) {
                tryToSaveToAssociatedFile(true);
            }
            
        });
        bottomRightPanel.add(saveFileButton);
        
        overlay = new JPanel() {
            
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                int width = getWidth();
                int height = getHeight();
                g.setColor(getBackground());
                g.fillRect(0, 0, width, height);
                g.setColor(new Color(0x993300));
                g.setFont(OVERLAY_FONT);
                FontMetrics fontMetrics = g.getFontMetrics();
                int textWidth = fontMetrics.stringWidth(OVERLAY_TEXT);
                int textHeight = fontMetrics.getHeight();
                g.drawString(OVERLAY_TEXT, (width - textWidth) / 2, (height - textHeight) / 2);
                super.paintComponent(g);
            }
            
        };
        overlay.setOpaque(false);
        overlay.setBackground(new Color(255, 200, 50, 100));
        overlay.setVisible(false);
        add(overlay, new Integer(1));
        
        addComponentListener(new ComponentListener() {
            
            @Override
            public void componentShown(ComponentEvent ev) {
                componentResized(ev);
            }
            
            @Override
            public void componentResized(ComponentEvent ev) {
                Dimension size = ContentPanel.this.getSize();
                mainPanel.setSize(size);
                overlay.setSize(size);
                ContentPanel.this.revalidate();
            }
            
            @Override
            public void componentMoved(ComponentEvent ev) {
            }
            
            @Override
            public void componentHidden(ComponentEvent ev) {
            }
        });
        
        Function<JComponent, DropTarget> addDropStart = (JComponent component) -> new DropTarget(component, new DropTargetListener() {
            
            @Override
            public void dropActionChanged(DropTargetDragEvent ev) {
            }
            
            @Override
            public void drop(DropTargetDropEvent ev) {
            }
            
            @Override
            public void dragOver(DropTargetDragEvent ev) {
                if (!enabled) {
                    ev.rejectDrag();
                }
            }
            
            @Override
            public void dragExit(DropTargetEvent ev) {
            }
            
            @Override
            public void dragEnter(DropTargetDragEvent ev) {
                if (enabled && ev.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    overlay.setVisible(true);
                }
            }
            
        });

        addDropStart.apply(this);
        addDropStart.apply(contentTextArea);
        addDropStart.apply(fileTextField);
        
        new DropTarget(overlay, new DropTargetListener() {
            
            private File file = null;
            
            @Override
            public void dropActionChanged(DropTargetDragEvent ev) {
            }
            
            @Override
            public void drop(DropTargetDropEvent ev) {
                if (file != null) {
                    fileTextField.setText(file.getPath());
                    tryToLoadFromAssociatedFile(false);
                }
                overlay.setVisible(false);
            }
            
            @Override
            public void dragOver(DropTargetDragEvent ev) {
            }
            
            @Override
            public void dragExit(DropTargetEvent ev) {
                overlay.setVisible(false);
                file = null;
            }
            
            @Override
            public void dragEnter(DropTargetDragEvent ev) {
                if (ev.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    try {
                        @SuppressWarnings("unchecked")
                        List<File> files = (List<File>)ev.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                        if (!files.isEmpty()) {
                            file = files.get(0);
                        }
                    } catch (UnsupportedFlavorException | IOException e) {
                    }
                }
            }
        });
        
        overlay.addComponentListener(new ComponentListener() {
            
            @Override
            public void componentShown(ComponentEvent ev) {
                Point point = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(point, overlay);
                if (!new Rectangle(new Point(0, 0), overlay.getSize()).contains(point)) {
                    overlay.setVisible(false);
                }
            }
            
            @Override
            public void componentResized(ComponentEvent ev) {
            }
            
            @Override
            public void componentMoved(ComponentEvent ev) {
            }
            
            @Override
            public void componentHidden(ComponentEvent ev) {
            }
        });
    }
    
}
