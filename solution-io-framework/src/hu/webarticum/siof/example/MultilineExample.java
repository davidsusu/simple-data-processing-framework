package hu.webarticum.siof.example;

import java.io.IOException;
import java.io.Writer;

import hu.webarticum.siof.framework.AbstractLinePatternSolution;

public class MultilineExample extends AbstractLinePatternSolution implements TextExample {
    
    public MultilineExample() {
        super(3, 2);
    }

    @Override
    protected void parseHeader(String header, Writer outputWriter) throws IOException {
        outputWriter.write("<header>\n " + header.replace("\n", "\n ") + "\n</header>\n\n");
    }

    @Override
    protected String solveItem(int itemIndex, String item) {
        return "ITEM #" + itemIndex + ": " + item.replace("\n", "; ");
    }

    @Override
    public String getSampleInputContent() {
        return "HEADER1\nHEADER2\nHEADER3\ninput1-1\ninput1-2\ninput2-1\ninput2-2";
    }
    
}
