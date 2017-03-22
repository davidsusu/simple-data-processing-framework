package hu.webarticum.siof.example;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hu.webarticum.siof.framework.AbstractLineByLineSolution;

public class VerySimpleExample extends AbstractLineByLineSolution implements Example {
    
    public VerySimpleExample() {
        super();
    }
    
    @Override
    protected String solveLine(int lineIndex, String inputLine) {
        return "Content of line " + lineIndex + ": '" + inputLine + "'; length: " + inputLine.length();
    }

    @Override
    public InputStream getSampleInputStream() {
        String input = "AAA\nBBB\nCCC";
        return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }
    
}
