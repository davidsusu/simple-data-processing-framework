package hu.webarticum.siof.framework;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractLineByLineSolutionTest {

	@Test
	public void test() {
		AbstractLineByLineSolution solution = new AbstractLineByLineSolution() {
			
			@Override
			protected String solveLine(int lineIndex, String inputLine) {
				return "#" + lineIndex + ": '" + inputLine + "'";
			}
			
		};
		StringSolutionWrapper wrapper = new StringSolutionWrapper(solution);

		assertEquals("", wrapper.solve(""));
		assertEquals("#0: 'line1'\n", wrapper.solve("line1"));
		assertEquals("#0: 'line1'\n#1: 'line2'\n", wrapper.solve("line1\nline2"));
	}

}
