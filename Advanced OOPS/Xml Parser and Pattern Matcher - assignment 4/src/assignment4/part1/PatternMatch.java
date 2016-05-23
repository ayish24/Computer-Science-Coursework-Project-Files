package assignment4.part1;

/**
 *
 * @author ayishwarya
 */
public class PatternMatch {
    
    private PatternMatchChain chainObject;
    
    
    protected PatternMatch(String pattern) throws Exception{
        int patternLength = pattern.length();
        if (patternLength < 1) {
            throw new Exception();
        }
        
        this.chainObject = buildMatcherChain(pattern);
        //PatternMatchChain currentChainObject = patternMatchChainFactory(pattern, 0);
        //chainObject = currentChainObject;
    }

    private PatternMatchChain buildMatcherChain(String pattern) {
        int index = 0;
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
    
    // FIX it 
    private int findFirstInt(String targetString) {
        int targetStringLength = targetString.length();
        for(int i =0; i < targetStringLength; i++) {
            if (chainObject.validateCharacter(targetString, i)){
                return i;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) throws Exception {
        
        PatternMatch m = new PatternMatch("c*t");
        System.out.println(m.findFirstInt("lctlcat"));
        
        PatternMatch m1 = new PatternMatch("c.t");
        System.out.println(m1.findFirstInt("cacacat"));
        
        PatternMatch m2 = new PatternMatch("cat");
        System.out.println(m2.findFirstInt("lcutlcat"));
    }
    
}
