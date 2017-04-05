package hu.webarticum.siof.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for parsing numeric or other data tokens
 */
public class InputItemParser {
    
    private final Pattern pattern;
    
    private final List<Character> typeCharacters;
    
    /**
     * @param patternString simplified pattern, see useRegex in the other constructor
     */
    public InputItemParser(String patternString) {
        this(patternString, false);
    }

    /**
     * If useRegex is false, then patternString will be interpreted as a simplified expression.
     * You can use only the following place-holders in this expression:
     * 
     * <ul>
     *  <li><code>%d</code> integral value (int)</li>
     *  <li><code>%f</code> floating point value (double)</li>
     *  <li><code>%w</code> word (sequence of word characters, same as <code>\w+</code> in a regular expression)</li>
     *  <li><code>%s</code> any character sequence (except line break)</li>
     * </ul>
     * 
     * However, you can use these place-holders in regular expression mode too.
     * Place-holders will be converted to capturing groups, and all other explicitly written
     * capturing group will be converted to non capturing.
     * 
     * @param patternString simplified pattern or regular expression
     * @param useRegex      if true then patternString will be interpreted as a regular expression
     */
    public InputItemParser(String patternString, boolean useRegex) {
        String normalizedPattern;
        if (useRegex) {
            normalizedPattern = patternString.replaceAll("(\\A|[^\\\\])(\\\\\\\\)*\\(", "$0?:");
        } else {
            normalizedPattern = patternString.replaceAll("[.*?+(){}\\[\\]\\-\\\\]", "\\\\$0");
        }
        normalizedPattern = "\\A" + normalizedPattern + "\\Z";
        
        Pattern placeholderPattern = Pattern.compile("%.");
        Matcher placeholderMatcher = placeholderPattern.matcher(normalizedPattern);
        
        typeCharacters = new ArrayList<Character>();
        StringBuffer patternBuffer = new StringBuffer();
        
        while (placeholderMatcher.find()) {
            String placeholder = placeholderMatcher.group();
            char placeholderCharacter = placeholder.charAt(1);
            String replacementString;
            switch (placeholderCharacter) {
                case '%':
                    replacementString = "%";
                    break;
                case 'd':
                    replacementString = "([+\\-]?\\d+)";
                    typeCharacters.add('d');
                    break;
                case 'f':
                    replacementString = "([+\\-]?\\d*\\.?\\d+(?:[eE][+\\-]?\\d+)?)";
                    typeCharacters.add('f');
                    break;
                case 'w':
                    replacementString = "(\\w+)";
                    typeCharacters.add('s');
                    break;
                case 's':
                default:
                    replacementString = "(.+)";
                    typeCharacters.add('s');
                    break;
            }
            placeholderMatcher.appendReplacement(patternBuffer, Matcher.quoteReplacement(replacementString));
        }
        placeholderMatcher.appendTail(patternBuffer);
        
        pattern = Pattern.compile(patternBuffer.toString());
    }
    
    public List<Object> parse(String itemInput) {
        int length = typeCharacters.size();
        
        List<Object> result = new ArrayList<Object>(length);
        
        Matcher matcher = pattern.matcher(itemInput);
        
        if (!matcher.find()) {
            throw new IllegalArgumentException("Unmatched input: '" + itemInput + "'");
        }
        
        for (int i = 0;  i < length; i++) {
            String valueString = matcher.group(i + 1);
            char typeCharacter = typeCharacters.get(i);

            Object value;
            switch (typeCharacter) {
                case 'd':
                    value = Integer.parseInt(valueString);
                    break;
                case 'f':
                    value = Double.parseDouble(valueString);
                    break;
                case 's':
                default:
                    value = valueString;
                    break;
            }
            
            result.add(value);
        }
        
        return result;
    }
    
}
