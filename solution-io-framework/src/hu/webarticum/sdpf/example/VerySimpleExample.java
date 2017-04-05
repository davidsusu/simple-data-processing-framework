package hu.webarticum.sdpf.example;

import hu.webarticum.sdpf.framework.AbstractLineByLineDataProcessor;

/**
 * A simple example of how to process some simple lines
 */
public class VerySimpleExample extends AbstractLineByLineDataProcessor implements TextExample {
    
    public VerySimpleExample() {
        super();
    }
    
    @Override
    protected String solveLine(int lineIndex, String inputLine) {
        return "Content of line " + lineIndex + ": '" + inputLine + "'; length: " + inputLine.length();
    }

    @Override
    public String getSampleInputContent() {
        return "AAA\nBBB\nCCC";
    }
    
}
