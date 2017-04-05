package hu.webarticum.siof.framework;

/**
 * Helper class for buffering lines of text
 */
public class LineBuffer {
    

    private final String LINE_SEPARATOR = System.lineSeparator();
    
    private final StringBuilder contentBuilder = new StringBuilder();
    
    private int height = 0;
    
    
    /**
     * Appends a line to the buffer
     * 
     * @param line  the new line
     */
    public void appendLine(String line) {
        if (height > 0) {
            contentBuilder.append(LINE_SEPARATOR);
        }
        contentBuilder.append(line);
        height++;
    }

    /**
     * Gets the current length of this buffer
     */
    public int getLength() {
        return contentBuilder.length();
    }

    /**
     * Gets the height of the content
     * 
     * Returns zero on empty content, number of newlines + 1 otherwise
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Clears this buffer
     */
    public void clear() {
        contentBuilder.setLength(0);
        height = 0;
    }
    
    @Override
    public String toString() {
        return contentBuilder.toString();
    }
    
}
