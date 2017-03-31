package hu.webarticum.siof.example;

import hu.webarticum.siof.framework.AbstractLineByLineSolution;

public class VerySimpleExample extends AbstractLineByLineSolution implements TextExample {
    
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
