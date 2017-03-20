package hu.webarticum.siof.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public abstract class AbstractLinePatternSolution extends AbstractTextSolution {

    private final int headerHeight;

    private final int itemHeight;

    private final boolean parseTrailing;

    protected AbstractLinePatternSolution() {
        this(0, 1);
    }

    protected AbstractLinePatternSolution(int headerHeight, int itemHeight) {
        this(headerHeight, itemHeight, false);
    }

    protected AbstractLinePatternSolution(int headerHeight, int itemHeight, boolean parseTrailing) {
        super();
        this.headerHeight = headerHeight;
        this.itemHeight = itemHeight;
        this.parseTrailing = parseTrailing;
    }

    protected AbstractLinePatternSolution(int headerHeight, int itemHeight, boolean parseTrailing, Charset charset) {
        super(charset);
        this.headerHeight = headerHeight;
        this.itemHeight = itemHeight;
        this.parseTrailing = parseTrailing;
    }

    protected AbstractLinePatternSolution(int headerHeight, int itemHeight, boolean parseTrailing, Charset inputCharset, Charset outputCharset) {
        super(inputCharset, inputCharset);
        this.headerHeight = headerHeight;
        this.itemHeight = itemHeight;
        this.parseTrailing = parseTrailing;
    }

    @Override
    public void solve(InputStreamReader inputReader, OutputStreamWriter outputWriter) throws IOException {
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
                    String itemSolution = solveItem(itemIndex++, buffer.toString());
                    outputWriter.write(itemSolution + LINE_SEPARATOR);
                    buffer.clear();
                }
            }
        }
        
        if (parseTrailing && !buffer.isEmpty()) {
            if (isHeaderMode) {
                parseHeader(buffer.toString(), outputWriter);
            } else {
                String itemSolution = solveItem(itemIndex, buffer.toString());
                outputWriter.write(itemSolution + LINE_SEPARATOR);
            }
        }
        
    }
    
    protected abstract void parseHeader(String header, OutputStreamWriter outputWriter) throws IOException;

    protected abstract String solveItem(int itemIndex, String item);

}
