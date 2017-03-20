package hu.webarticum.siof.example;

import hu.webarticum.siof.framework.AbstractLineByLineSolution;

public class VerySimpleSolution extends AbstractLineByLineSolution {

	@Override
	protected String solveLine(int lineIndex, String inputLine) {
		return "Content of line " + lineIndex + ": '" + inputLine + "'; length: " + inputLine.length();
	}

}
