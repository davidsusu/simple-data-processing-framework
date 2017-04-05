package hu.webarticum.sdpf.example;

import java.io.InputStream;

import hu.webarticum.sdpf.framework.DataProcessor;

/**
 * Interface for examples with an associated sample input
 */
public interface Example extends DataProcessor {
    
    public InputStream getSampleInputStream();
    
}
