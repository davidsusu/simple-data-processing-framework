package hu.webarticum.siof.gui;

import java.io.IOException;

import hu.webarticum.siof.example.MultilineExample;

public class Main {

	public static void main(String[] args) throws IOException {
		Runnable example = new MultilineExample();
		example.run();
	}

}
