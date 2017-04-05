package hu.webarticum.sdpf.framework;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Writer;

import org.junit.Test;

import hu.webarticum.sdpf.framework.AbstractLinePatternDataProcessor;
import hu.webarticum.sdpf.framework.StringDataProcessorWrapper;

public class AbstractLinePatternDataProcessorTest {

    @Test
    public void singleLineItemsTest() {
        AbstractLinePatternDataProcessor dataProcessor = new AbstractLinePatternDataProcessor(0, 1, false) {
            
            @Override
            protected String solveItem(int itemIndex, String item) {
                return "#" + itemIndex + ": '" + item + "'";
            }
            
            @Override
            protected void parseHeader(String header, Writer outputWriter) throws IOException {
            }
            
        };
        StringDataProcessorWrapper wrapper = new StringDataProcessorWrapper(dataProcessor);

        assertEquals("", wrapper.solve(""));
        assertEquals("#0: 'line1'\n", wrapper.solve("line1"));
        assertEquals("#0: 'line1'\n#1: 'line2'\n", wrapper.solve("line1\nline2"));
    }

    @Test
    public void multiLineItemsTest() {
        AbstractLinePatternDataProcessor dataProcessor = new AbstractLinePatternDataProcessor(0, 3, false) {
            
            @Override
            protected String solveItem(int itemIndex, String item) {
                return item.replace("\n", ";");
            }
            
            @Override
            protected void parseHeader(String header, Writer outputWriter) throws IOException {
            }
            
        };
        StringDataProcessorWrapper wrapper = new StringDataProcessorWrapper(dataProcessor);

        assertEquals("", wrapper.solve(""));
        assertEquals("1;2;3\n", wrapper.solve("1\n2\n3"));
        assertEquals("1;2;3\n4;5;6\n", wrapper.solve("1\n2\n3\n4\n5\n6"));
        assertEquals("1;2;3\n4;5;6\n", wrapper.solve("1\n2\n3\n4\n5\n6\n7\n8"));
    }

    @Test
    public void trailingTest() {
        AbstractLinePatternDataProcessor dataProcessor = new AbstractLinePatternDataProcessor(0, 3, true) {
            
            @Override
            protected String solveItem(int itemIndex, String item) {
                return item.replace("\n", ";");
            }
            
            @Override
            protected void parseHeader(String header, Writer outputWriter) throws IOException {
            }
            
        };
        StringDataProcessorWrapper wrapper = new StringDataProcessorWrapper(dataProcessor);

        assertEquals("", wrapper.solve(""));
        assertEquals("1;2;3\n", wrapper.solve("1\n2\n3"));
        assertEquals("1;2;3\n4;5;6\n", wrapper.solve("1\n2\n3\n4\n5\n6"));
        assertEquals("1;2;3\n4;5;6\n7;8\n", wrapper.solve("1\n2\n3\n4\n5\n6\n7\n8"));
    }

    @Test
    public void headerTest() {
        AbstractLinePatternDataProcessor dataProcessor = new AbstractLinePatternDataProcessor(2, 3, false) {
            
            @Override
            protected String solveItem(int itemIndex, String item) {
                return item.replace("\n", ";");
            }
            
            @Override
            protected void parseHeader(String header, Writer outputWriter) throws IOException {
                outputWriter.write("HEAD FOUND\n");
            }
            
        };
        StringDataProcessorWrapper wrapper = new StringDataProcessorWrapper(dataProcessor);

        assertEquals("", wrapper.solve(""));
        assertEquals("", wrapper.solve("h"));
        assertEquals("HEAD FOUND\n", wrapper.solve("h\nh"));
        assertEquals("HEAD FOUND\n1;2;3\n", wrapper.solve("h\nh\n1\n2\n3"));
        assertEquals("HEAD FOUND\n1;2;3\n4;5;6\n", wrapper.solve("h\nh\n1\n2\n3\n4\n5\n6"));
        assertEquals("HEAD FOUND\n1;2;3\n4;5;6\n", wrapper.solve("h\nh\n1\n2\n3\n4\n5\n6\n7\n8"));
    }

    @Test
    public void headerTrailingTest() {
        AbstractLinePatternDataProcessor dataProcessor = new AbstractLinePatternDataProcessor(2, 3, true) {
            
            @Override
            protected String solveItem(int itemIndex, String item) {
                return item.replace("\n", ";");
            }
            
            @Override
            protected void parseHeader(String header, Writer outputWriter) throws IOException {
                outputWriter.write("HEAD FOUND\n");
            }
            
        };
        StringDataProcessorWrapper wrapper = new StringDataProcessorWrapper(dataProcessor);

        assertEquals("", wrapper.solve(""));
        assertEquals("HEAD FOUND\n", wrapper.solve("h"));
        assertEquals("HEAD FOUND\n", wrapper.solve("h\nh"));
        assertEquals("HEAD FOUND\n1;2;3\n", wrapper.solve("h\nh\n1\n2\n3"));
        assertEquals("HEAD FOUND\n1;2;3\n4;5;6\n", wrapper.solve("h\nh\n1\n2\n3\n4\n5\n6"));
        assertEquals("HEAD FOUND\n1;2;3\n4;5;6\n7;8\n", wrapper.solve("h\nh\n1\n2\n3\n4\n5\n6\n7\n8"));
        
    }
    
}
