package hu.webarticum.siof.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Solution {
    
    public void solve(InputStream inputStream, OutputStream outputStream) throws IOException;
    
}
