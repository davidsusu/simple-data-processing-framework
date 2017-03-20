package hu.webarticum.siof.example;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hu.webarticum.siof.framework.AbstractLineByLineSolution;
import hu.webarticum.siof.framework.Solution;

public class VerySimpleExample implements Runnable {
	
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
		String input = "AAA\nBBB\nCCC";
		return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
	}
	
	private class InnerSolution extends AbstractLineByLineSolution {
		
		@Override
		protected String solveLine(int lineIndex, String inputLine) {
			return "Content of line " + lineIndex + ": '" + inputLine + "'; length: " + inputLine.length();
		}
		
	}
	
}
