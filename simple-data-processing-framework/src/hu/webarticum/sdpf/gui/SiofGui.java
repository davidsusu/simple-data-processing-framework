package hu.webarticum.sdpf.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;

import hu.webarticum.sdpf.example.TextExample;
import hu.webarticum.sdpf.framework.TextDataProcessor;

public class SiofGui implements Runnable {
    
    private final List<TextDataProcessor> dataProcessors;

    private final File inputFile;
    
    private final String inputContent;

    private final File outputFile;

    private final String outputContent;

    private final File checkOutputFile;

    private final String checkOutputContent;

    private final String diffCommand;

    public SiofGui(TextDataProcessor... dataProcessors) {
        this(Arrays.asList(dataProcessors));
    }

    public SiofGui(TextExample example) {
        this(Arrays.asList(example), example.getSampleInputContent());
    }

    public SiofGui(TextDataProcessor dataProcessor, File inputFile, File outputFile) {
        this(Arrays.asList(dataProcessor), inputFile, outputFile);
    }

    public SiofGui(TextDataProcessor dataProcessor, String inputContent) {
        this(Arrays.asList(dataProcessor), inputContent);
    }

    public SiofGui(Collection<TextDataProcessor> dataProcessors) {
        this(dataProcessors, null, "", null, "", null, "", "");
    }

    public SiofGui(Collection<TextDataProcessor> dataProcessors, File inputFile, File outputFile) {
        this(
            dataProcessors, inputFile,
            readFileSilently(inputFile), outputFile,
            readFileSilently(outputFile), null, "", ""
        );
    }

    public SiofGui(Collection<TextDataProcessor> dataProcessors, String inputContent) {
        this(dataProcessors, null, inputContent, null, "", null, "", "");
    }

    public SiofGui(
        TextDataProcessor[] dataProcessors,
        File inputFile,
        String inputContent,
        File outputFile,
        String outputContent,
        File checkOutputFile,
        String checkOutputContent,
        String diffCommand
    ) {
        this(
            Arrays.asList(dataProcessors),
            inputFile,
            inputContent,
            outputFile,
            outputContent,
            checkOutputFile,
            checkOutputContent,
            diffCommand
        );
    }

    public SiofGui(
        Collection<TextDataProcessor> dataProcessors,
        File inputFile,
        String inputContent,
        File outputFile,
        String outputContent,
        File checkOutputFile,
        String checkOutputContent,
        String diffCommand
    ) {
        this.dataProcessors = new ArrayList<>(dataProcessors);
        this.inputFile = inputFile;
        this.inputContent = inputContent;
        this.outputFile = outputFile;
        this.outputContent = outputContent;
        this.checkOutputFile = checkOutputFile;
        this.checkOutputContent = checkOutputContent;
        this.diffCommand = diffCommand;
    }

    public static String readFileSilently(File file) {
        try {
            return readFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static String readFile(File file) throws IOException {
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            char[] buffer = new char[1024];
            int size = 0;
            StringBuilder resultBuilder = new StringBuilder();
            while ((size = reader.read(buffer)) != -1) {
                resultBuilder.append(new String(buffer, 0, size));
            }
            return resultBuilder.toString();
        }
    }
    
    public static void writeFile(File file, String content) throws IOException {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
            writer.write(content);
        }
    }

    @Override
    public void run() {
        MainFrame mainFrame = new MainFrame(
            dataProcessors,
            inputFile, inputContent,
            outputFile, outputContent,
            checkOutputFile, checkOutputContent,
            diffCommand
        );
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
    
}
