package hu.webarticum.siof.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    
    private JPanel innerPanel;
    
    
    public SettingsPanel() {
        setLayout(new BorderLayout());
        
        innerPanel = new JPanel();
        add(innerPanel, BorderLayout.PAGE_START);
        
        innerPanel.add(new JLabel("SettingsPanel {solutions, settings, run}"));
    }
    
}
