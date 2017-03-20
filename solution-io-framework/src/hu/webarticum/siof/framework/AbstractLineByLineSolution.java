package hu.webarticum.siof.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public abstract class AbstractLineByLineSolution extends AbstractTextSolution {
    
    protected AbstractLineByLineSolution() {
        super();
    }

    protected AbstractLineByLineSolution(Charset charset) {
        super(charset);
    }

    protected AbstractLineByLineSolution(Charset inputCharset, Charset outputCharset) {
        super(inputCharset, inputCharset);
    }
    
    @Override
    public void solve(Reader inputReader, Writer outputWriter) throws IOException {
        BufferedReader bufferedInputReader = new BufferedReader(inputReader);
        String LINE_SEPARATOR = System.lineSeparator();
        int lineIndex = 0;
        String inputLine;
        while ((inputLine = bufferedInputReader.readLine()) != null) {
            String outputLine = solveLine(lineIndex++, inputLine);
            outputWriter.write(outputLine + LINE_SEPARATOR);
        }
    }
    
    protected abstract String solveLine(int lineIndex, String inputLine);
    
}
