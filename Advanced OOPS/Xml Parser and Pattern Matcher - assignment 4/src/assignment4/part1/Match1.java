package assignment4.part1;

/**
 *
 * @author ayishwarya
 */
public class Match1 {
    PatternMatchChain matchChain;
   // CreateObjects co = new CreateObjects();

    Match1(String pattern)
    {
        PatternMatchChain newChain = chainOfObjects(pattern,0);
        this.matchChain = newChain;
        this.matchChain = buildMatchChain(pattern);
        
    }
    
    private PatternMatchChain chainOfObjects(String pattern, int index) {
    

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
    
    private PatternMatchChain buildMatchChain(String pattern) {
        int patternLength = pattern.length();
        
        if(patternLength > 0)
        {
            for (int i = 1; i < pattern.length(); i++)
            {
            PatternMatchChain next = chainOfObjects(pattern, i);
            matchChain.setNextChain(next);
            matchChain = next;
            }
        }
        else
            matchChain = null;
        
        return matchChain;
    }

    int findFirstIn(String literal) {
        for(int i = 0; i < literal.length(); i++) {
            if (matchChain.validateCharacter(literal, i)){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        Match1 match = new Match1("u.t");
        System.out.print(match.findFirstIn("cauatcauat"));
    }
}
