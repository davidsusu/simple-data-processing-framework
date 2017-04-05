package hu.webarticum.siof.example;

import java.io.InputStream;

import hu.webarticum.siof.framework.Solution;

/**
 * Interface for examples with an associated sample input
 */
public interface Example extends Solution {
    
    public InputStream getSampleInputStream();
    
}
