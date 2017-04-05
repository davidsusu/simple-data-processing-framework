package hu.webarticum.sdpf.framework;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Simplifier adapter for data processors
 */
public class StringDataProcessorWrapper {
    
    private final DataProcessor dataProcessor;
    
    public StringDataProcessorWrapper(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }
    
    /**
     * Processed the given input and returns with the result as a string
     */
    public String process(String input) {
        InputStream sampleInputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            dataProcessor.process(sampleInputStream, outputStream);
        } catch (IOException e) {
        }
        
        return outputStream.toString();
    }
    
}
