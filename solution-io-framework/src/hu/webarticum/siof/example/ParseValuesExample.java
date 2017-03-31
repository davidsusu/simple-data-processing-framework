package hu.webarticum.siof.example;

import java.util.Iterator;
import java.util.Locale;

import hu.webarticum.siof.framework.AbstractLineByLineSolution;
import hu.webarticum.siof.framework.InputItemParser;

public class ParseValuesExample extends AbstractLineByLineSolution implements TextExample {
    
    public static final String PATTERN = "%w, %d, %f";
    
    private final InputItemParser parser;
    
    public ParseValuesExample() {
        this.parser = new InputItemParser(PATTERN);
    }
    
    @Override
    protected String solveLine(int lineIndex, String inputLine) {
        Iterator<Object> dataIterator = parser.parse(inputLine).iterator();
        
        String label = (String)dataIterator.next();
        int number1 = (Integer)dataIterator.next();
        double number2 = (Double)dataIterator.next();
        
        double sum = number1 + number2;
        
        return String.format(Locale.US, "#%d '%s', sum: %.1f", lineIndex, label, sum);
    }

    @Override
    public String getSampleInputContent() {
        return "Hello, 4, 12.4\nWorld, -12, 3.4E2\nXXXXX, 0, -.4";
    }

}
