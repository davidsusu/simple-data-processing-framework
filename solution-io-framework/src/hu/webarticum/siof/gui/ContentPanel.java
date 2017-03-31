package hu.webarticum.siof.gui;

import java.io.File;

import javax.swing.JPanel;

public class ContentPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public ContentPanel(String content) {
        this(content, "");
    }

    public ContentPanel(String content, File associatedFile) {
        this(content, associatedFile == null ? "" : associatedFile.getPath());
    }

    public ContentPanel(String content, String associatedFilePath) {
        // TODO
    }
    
}
