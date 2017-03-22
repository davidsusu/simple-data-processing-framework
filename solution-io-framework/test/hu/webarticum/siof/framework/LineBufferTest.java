package hu.webarticum.siof.framework;

import static org.junit.Assert.*;

import org.junit.Test;

public class LineBufferTest {

	@Test
	public void test() {
		LineBuffer lineBuffer = new LineBuffer();
		assertEquals(0, lineBuffer.getHeight());
		assertEquals(0, lineBuffer.getLength());
		
		lineBuffer.appendLine("");
		assertEquals(1, lineBuffer.getHeight());
		assertEquals(0, lineBuffer.getLength());

		lineBuffer.appendLine("line1");
		assertEquals(2, lineBuffer.getHeight());
		assertEquals(6, lineBuffer.getLength());

		lineBuffer.appendLine("line2");
		lineBuffer.appendLine("line3");
		assertEquals(4, lineBuffer.getHeight());
		assertEquals(18, lineBuffer.getLength());
		
		lineBuffer.clear();
		assertEquals(0, lineBuffer.getHeight());
		assertEquals(0, lineBuffer.getLength());

		lineBuffer.appendLine("a line");
		assertEquals(1, lineBuffer.getHeight());
		assertEquals(6, lineBuffer.getLength());
	}

}
