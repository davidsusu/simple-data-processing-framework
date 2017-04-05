package hu.webarticum.sdpf.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * Base class for data processors based on tuples of lines, optinally with header
 */
public abstract class AbstractLinePatternDataProcessor extends AbstractTextDataProcessor {

    private final int headerHeight;

    private final int itemHeight;

    private final boolean parseTrailing;

    /** See the major constructor */
    protected AbstractLinePatternDataProcessor() {
        this(0, 1);
    }

    /** See the major constructor */
    protected AbstractLinePatternDataProcessor(int headerHeight, int itemHeight) {
        this(headerHeight, itemHeight, false);
    }

    /** See the major constructor */
    protected AbstractLinePatternDataProcessor(int headerHeight, int itemHeight, boolean parseTrailing) {
        super();
        this.headerHeight = headerHeight;
        this.itemHeight = itemHeight;
        this.parseTrailing = parseTrailing;
    }

    /**
     * See the major constructor
     */
    protected AbstractLinePatternDataProcessor(int headerHeight, int itemHeight, boolean parseTrailing, Charset charset) {
        super(charset);
        this.headerHeight = headerHeight;
        this.itemHeight = itemHeight;
        this.parseTrailing = parseTrailing;
    }

    /**
     * @param headerHeight  number of expected header lines
     * @param itemHeight    number of lines per input item
     * @param parseTrailing if true then an incomplete header or last item will be interpreted too
     * @param inputCharset  expected character set of the input stream, not used with reader
     * @param outputCharset character set for the output stream, not used with writer
     */
    protected AbstractLinePatternDataProcessor(int headerHeight, int itemHeight, boolean parseTrailing, Charset inputCharset, Charset outputCharset) {
        super(inputCharset, inputCharset);
        this.headerHeight = headerHeight;
        this.itemHeight = itemHeight;
        this.parseTrailing = parseTrailing;
    }

    @Override
    public void process(Reader inputReader, Writer outputWriter) throws IOException {
        BufferedReader bufferedInputReader = new BufferedReader(inputReader);
        
        String LINE_SEPARATOR = System.lineSeparator();
        
        LineBuffer buffer = new LineBuffer();
        String line;
        boolean isHeaderMode = (headerHeight > 0);
        int itemIndex = 0;
        
        while ((line = bufferedInputReader.readLine()) != null) {
            buffer.appendLine(line);
            
            if (isHeaderMode) {
                if (buffer.getHeight() == headerHeight) {
                    parseHeader(buffer.toString(), outputWriter);
                    buffer.clear();
                    isHeaderMode = false;
                }
            } else {
                if (buffer.getHeight() == itemHeight) {
                    String itemOutput = processItem(itemIndex++, buffer.toString());
                    outputWriter.write(itemOutput + LINE_SEPARATOR);
                    buffer.clear();
                }
            }
        }
        
        if (parseTrailing && buffer.getLength() > 0) {
            if (isHeaderMode) {
                parseHeader(buffer.toString(), outputWriter);
            } else {
                String itemOutput = processItem(itemIndex, buffer.toString());
                outputWriter.write(itemOutput + LINE_SEPARATOR);
            }
        }
        
    }
    
    /**
     * Write code here to process the header part
     * 
     * @param header        content of the header part
     * @param outputWriter  the Writer of the output
     * @throws IOException
     */
    protected abstract void parseHeader(String header, Writer outputWriter) throws IOException;

    /**
     * Write code here to process a single item, possibly with multiple lines
     * 
     * @param itemIndex index of the current input item (0-based, excluding header part)
     * @param item      content of the current input item
     * @return
     */
    protected abstract String processItem(int itemIndex, String item);

}
