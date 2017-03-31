package hu.webarticum.siof.framework;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface TextSolution extends Solution {

    public void solve(Reader inputReader, Writer outputWriter) throws IOException;
    
}
