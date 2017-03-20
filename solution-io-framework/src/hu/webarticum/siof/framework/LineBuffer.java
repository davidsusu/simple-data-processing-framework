package hu.webarticum.siof.framework;

public class LineBuffer {
	

	private final String LINE_SEPARATOR = System.lineSeparator();
	
	private final StringBuilder contentBuilder = new StringBuilder();
	
	private int height = 0;
	
	
	public void appendLine(String line) {
		if (height > 0) {
			contentBuilder.append(LINE_SEPARATOR);
		}
		contentBuilder.append(line);
		height++;
	}

	public boolean isEmpty() {
		return (contentBuilder.length() == 0);
	}
	
	public void clear() {
		contentBuilder.setLength(0);
		height = 0;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return contentBuilder.toString();
	}
	
}
