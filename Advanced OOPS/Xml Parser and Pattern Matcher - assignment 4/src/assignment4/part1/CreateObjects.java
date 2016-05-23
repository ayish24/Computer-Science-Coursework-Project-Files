package assignment4.part1;

/**
 *
 * @author ayishwarya
 */
public class CreateObjects {
    
   public PatternMatchChain chainOfObjects(String pattern, int index) {
    

        char charAtIndex = pattern.charAt(index);
        int patternLength = pattern.length();
        int nextIndex = index++;
        switch(charAtIndex) {
            case '*':
                if (index == patternLength-1)
                    return new SequenceWildCharacterMatcher (null);
                else
                    return new SequenceWildCharacterMatcher (pattern.charAt(nextIndex));
                
            case '.':
                return new SingleWildCharacterMatcher();
                
            default :
                return new NormalCharacterMatcher(charAtIndex);
        }
    }
}