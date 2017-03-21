package hu.webarticum.siof.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputItemParser {
    
    private final Pattern pattern;
    
    private final List<Character> typeCharacters;
    
    public InputItemParser(String patternString) {
        this(patternString, false);
    }
    
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
