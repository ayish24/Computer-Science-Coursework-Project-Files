package assignment4.part1;

/**
 *
 * @author ayishwarya
 */
public class NormalCharacterMatcher implements PatternMatchChain {

    private PatternMatchChain chainObject;
    private char expectedCharacter;
    
    NormalCharacterMatcher(char charAtIndex) {
        expectedCharacter = charAtIndex;
    }

    @Override
    public void setNextChain(PatternMatchChain nextChain) {
        chainObject = nextChain;
    }

    @Override
    public boolean validateCharacter(String targetString, int index) {
        char actualCharacterAtIndex = targetString.charAt(index);
        boolean currentResponsibility = (actualCharacterAtIndex == expectedCharacter);
        
        if (chainObject == null) 
            return currentResponsibility;
        else
            return currentResponsibility && chainObject.validateCharacter(targetString, index+1);
    }
    
}
