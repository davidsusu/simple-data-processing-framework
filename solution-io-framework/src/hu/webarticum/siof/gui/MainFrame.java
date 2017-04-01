package hu.webarticum.siof.gui;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.swing.JFrame;

import hu.webarticum.siof.framework.TextSolution;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainFrame(
        List<TextSolution> solutions,
        File inputFile,
        String inputContent,
        File outputFile,
        String outputContent,
        File checkOutputFile,
        String checkOutputContent
    ) {
        setSize(420, 300);
        setContentPane(new ContentPanel("Input", new Color(0x99CC44), inputContent, inputFile == null ? "" : inputFile.getPath()));
        
        // TODO
        
        TextSolution solution = solutions.get(0);
        Reader reader = new StringReader(inputContent);
        Writer writer = new StringWriter();
        
        try {
            solution.solve(reader, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Output:\n\n" + writer.toString());
    }

}
