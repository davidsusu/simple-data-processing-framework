package hu.webarticum.siof.framework;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Simplifier adapter for data processors
 */
public class StringSolutionWrapper {
    
    private final Solution solution;
    
    public StringSolutionWrapper(Solution solution) {
        this.solution = solution;
    }
    
    /**
     * Processed the given input and returns with the result as a string
     */
    public String solve(String input) {
        InputStream sampleInputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            solution.solve(sampleInputStream, outputStream);
        } catch (IOException e) {
        }
        
        return outputStream.toString();
    }
    
}
