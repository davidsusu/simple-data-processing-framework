package hu.webarticum.sdpf.framework;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import hu.webarticum.sdpf.framework.InputItemParser;

public class InputItemParserTest {

    @Test
    public void verySimpleTest() {
        InputItemParser parser = new InputItemParser("%w, %d");
        Iterator<Object> valueIterator = parser.parse("test, 12").iterator();
        String string = (String)valueIterator.next();
        int integer = (Integer)valueIterator.next();
        assertEquals("test", string);
        assertEquals(12, integer);
    }

    @Test
    public void verySimpleFailTest() {
        InputItemParser parser = new InputItemParser("%w, %d");
        try {
            parser.parse("###test, 12").iterator();
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void littleMoreComplexTest() {
        InputItemParser parser = new InputItemParser("%w, %d, %f, %f, %s");
        Iterator<Object> valueIterator = parser.parse("word, -4, .3, 4E-2, apple, banana, orange").iterator();
        
        String word = (String)valueIterator.next();
        int integer = (Integer)valueIterator.next();
        double double1 = (Double)valueIterator.next();
        double double2 = (Double)valueIterator.next();
        String tail = (String)valueIterator.next();
        
        assertEquals("word", word);
        assertEquals(-4, integer);
        assertEquals(0.3, double1, 1E-10);
        assertEquals(0.04, double2, 1E-10);
        assertEquals("apple, banana, orange", tail);
    }

    @Test
    public void regexTest() {
        InputItemParser parser = new InputItemParser("\\s*%d; ([xy]z)+; %w; \\d+; %f", true);
        
        {
            Iterator<Object> _valueIterator = parser.parse("  \t 72; xzyz; green; 123; +4.3").iterator();
            
            int _int = (Integer)_valueIterator.next();
            String _word = (String)_valueIterator.next();
            double _double = (Double)_valueIterator.next();
            
            assertEquals(72, _int);
            assertEquals("green", _word);
            assertEquals(4.3, _double, 1E-10);
        }
        
        {
            Iterator<Object> _valueIterator = parser.parse("  \t 123; xz; red; 0; 0").iterator();
            
            int _int = (Integer)_valueIterator.next();
            String _word = (String)_valueIterator.next();
            double _double = (Double)_valueIterator.next();
            
            assertEquals(123, _int);
            assertEquals("red", _word);
            assertEquals(0, _double, 1E-10);
        }

        try {
            parser.parse("  \t 99; yz; hello; world; 4").iterator();
            fail();
        } catch (IllegalArgumentException e) {
        }
    }
    
}
