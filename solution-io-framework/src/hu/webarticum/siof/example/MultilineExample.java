package hu.webarticum.siof.example;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import hu.webarticum.siof.framework.AbstractLinePatternSolution;

public class MultilineExample extends AbstractLinePatternSolution implements Example {
    
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
    public InputStream getSampleInputStream() {
        String input = "HEADER1\nHEADER2\nHEADER3\ninput1-1\ninput1-2\ninput2-1\ninput2-2";
        return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }
    
}
