package hu.webarticum.siof.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;

import hu.webarticum.siof.example.TextExample;
import hu.webarticum.siof.framework.TextSolution;

public class SiofGui implements Runnable {
    
    private final List<TextSolution> solutions;

    private final File inputFile;
    
    private final String inputContent;

    private final File outputFile;

    private final String outputContent;

    private final File checkOutputFile;

    private final String checkOutputContent;

    public SiofGui(TextSolution... solutions) {
        this(Arrays.asList(solutions));
    }

    public SiofGui(TextExample example) {
        this(Arrays.asList(example), example.getSampleInputContent());
    }

    public SiofGui(TextSolution solution, File inputFile, File outputFile) {
        this(Arrays.asList(solution), inputFile, outputFile);
    }

    public SiofGui(TextSolution solution, String inputContent) {
        this(Arrays.asList(solution), inputContent);
    }

    public SiofGui(Collection<TextSolution> solutions) {
        this(solutions, null, "", null, "", null, "");
    }

    public SiofGui(Collection<TextSolution> solutions, File inputFile, File outputFile) {
        this(
            solutions, inputFile,
            readFileSilently(inputFile), outputFile,
            readFileSilently(outputFile), null, ""
        );
    }

    public SiofGui(Collection<TextSolution> solutions, String inputContent) {
        this(solutions, null, inputContent, null, "", null, "");
    }

    public SiofGui(
        Collection<TextSolution> solutions,
        File inputFile,
        String inputContent,
        File outputFile,
        String outputContent,
        File checkOutputFile,
        String checkOutputContent
    ) {
        this.solutions = new ArrayList<>(solutions);
        this.inputFile = inputFile;
        this.inputContent = inputContent;
        this.outputFile = outputFile;
        this.outputContent = outputContent;
        this.checkOutputFile = checkOutputFile;
        this.checkOutputContent = checkOutputContent;
    }

    private static String readFileSilently(File file) {
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);) {
            char[] buffer = new char[1024];
            int size = 0;
            StringBuilder resultBuilder = new StringBuilder();
            while ((size = reader.read(buffer)) != -1) {
                resultBuilder.append(new String(buffer, 0, size));
            }
            return resultBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void run() {
        MainFrame mainFrame = new MainFrame(
            solutions,
            inputFile, inputContent,
            outputFile, outputContent,
            checkOutputFile, checkOutputContent
        );
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
    
}
