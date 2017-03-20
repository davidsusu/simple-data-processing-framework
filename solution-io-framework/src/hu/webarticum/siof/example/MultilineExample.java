package hu.webarticum.siof.example;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import hu.webarticum.siof.framework.AbstractLinePatternSolution;
import hu.webarticum.siof.framework.Solution;

public class MultilineExample implements Runnable {
    
    @Override
    public void run() {
        Solution solution = new InnerSolution();
        InputStream inputStream = getInputStream();
        
        try {
            solution.solve(inputStream, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private InputStream getInputStream() {
        String input = "HEADER1\nHEADER2\nHEADER3\ninput1-1\ninput1-2\ninput2-1\ninput2-2";
        return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }
    
    private class InnerSolution extends AbstractLinePatternSolution {
        
        InnerSolution() {
            super(3, 2);
        }

        @Override
        protected void parseHeader(String header, OutputStreamWriter outputWriter) throws IOException {
            outputWriter.write("<header>\n " + header.replace("\n", "\n ") + "\n</header>\n\n");
        }

        @Override
        protected String solveItem(int itemIndex, String item) {
            return "ITEM #" + itemIndex + ": " + item.replace("\n", "; ");
        }
        
    }
    
}
