package hu.webarticum.siof.gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hu.webarticum.siof.example.VerySimpleSolution;
import hu.webarticum.siof.framework.Solution;

public class Main {

	public static void main(String[] args) throws IOException {
		String input = "AAA\nBBB\nCCC";

		System.out.println(input);
		System.out.println();
		System.out.println(". . .");
		
		InputStream sampleInputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		Solution sampleSolution = new VerySimpleSolution();
		sampleSolution.solve(sampleInputStream, outputStream);
		
		String output = outputStream.toString();

		System.out.println();
		
		System.out.println(output);
	}

}
