package hu.webarticum.sdpf.example;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import hu.webarticum.sdpf.framework.TextDataProcessor;

/**
 * Interface for character based examples with an associated sample input
 */
public interface TextExample extends Example, TextDataProcessor {

    @Override
    public default InputStream getSampleInputStream() {
        return new ByteArrayInputStream(getSampleInputContent().getBytes());
    }
    
    public String getSampleInputContent();
    
}
