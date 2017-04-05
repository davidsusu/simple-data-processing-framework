package hu.webarticum.sdpf.framework;

/**
 * Base class for simple line based data processors
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public abstract class AbstractLineByLineDataProcessor extends AbstractTextDataProcessor {

    protected AbstractLineByLineDataProcessor() {
        super();
    }

    /**
     * Sets character set for input and output too
     * 
     * @param charset
     */
    protected AbstractLineByLineDataProcessor(Charset charset) {
        super(charset);
    }

    /**
     * Sets possibly different character sets for input and output
     * 
     * @param inputCharset  expected character set of the input stream
     * @param outputCharset character set for the output stream
     */
    protected AbstractLineByLineDataProcessor(Charset inputCharset, Charset outputCharset) {
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
    
    /**
     * Write code here to process a single input line
     * 
     * @param lineIndex index of the current input line (0-based)
     * @param inputLine content of the current input line
     * @return
     */
    protected abstract String solveLine(int lineIndex, String inputLine);
    
}
