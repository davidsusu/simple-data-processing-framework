package hu.webarticum.siof.example;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import hu.webarticum.siof.framework.TextSolution;

public interface TextExample extends Example, TextSolution {

    @Override
    public default InputStream getSampleInputStream() {
        return new ByteArrayInputStream(getSampleInputContent().getBytes());
    }
    
    public String getSampleInputContent();
    
}
