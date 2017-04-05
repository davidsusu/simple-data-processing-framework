package hu.webarticum.siof.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * General interface for data processors
 */
public interface Solution {
    
    /**
     * Processes data coming from an InputStream
     * 
     * @param inputStream   the stream of input data
     * @param outputStream  a stream for output
     * @throws IOException
     */
    public void solve(InputStream inputStream, OutputStream outputStream) throws IOException;
    
}
