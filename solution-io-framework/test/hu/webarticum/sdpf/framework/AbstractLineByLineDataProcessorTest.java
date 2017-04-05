package hu.webarticum.sdpf.framework;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.webarticum.sdpf.framework.AbstractLineByLineDataProcessor;
import hu.webarticum.sdpf.framework.StringDataProcessorWrapper;

public class AbstractLineByLineDataProcessorTest {

    @Test
    public void test() {
        AbstractLineByLineDataProcessor processor = new AbstractLineByLineDataProcessor() {
            
            @Override
            protected String processLine(int lineIndex, String inputLine) {
                return "#" + lineIndex + ": '" + inputLine + "'";
            }
            
        };
        StringDataProcessorWrapper wrapper = new StringDataProcessorWrapper(processor);

        assertEquals("", wrapper.process(""));
        assertEquals("#0: 'line1'\n", wrapper.process("line1"));
        assertEquals("#0: 'line1'\n#1: 'line2'\n", wrapper.process("line1\nline2"));
    }

}
