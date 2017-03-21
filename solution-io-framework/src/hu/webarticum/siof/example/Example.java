package hu.webarticum.siof.example;

import java.io.InputStream;

import hu.webarticum.siof.framework.Solution;

public interface Example extends Solution {
	
	public InputStream getSampleInputStream();
	
}
