package assignment4.part1;

/**
 *
 * @author ayishwarya
 */
public class SequenceWildCharacterMatcher implements PatternMatchChain {
    
    private PatternMatchChain chainObject;
    private Character stopLiteralValue;

    SequenceWildCharacterMatcher(Character newCharacter) {
        stopLiteralValue = newCharacter;
    }

    @Override
    public void setNextChain(PatternMatchChain nextChain) {
        chainObject = nextChain;
    }

    @Override
    public boolean validateCharacter(String targetString, int index) {
        if (chainObject == null)
            return true;
        else
            return chainObject.validateCharacter(targetString, targetString.indexOf(stopLiteralValue, index));
    }
    
}
