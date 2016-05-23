package assignment4.part1;

/**
 *
 * @author ayishwarya
 */
public class SingleWildCharacterMatcher implements PatternMatchChain {
    
    private PatternMatchChain chainObject;

    @Override
    public void setNextChain(PatternMatchChain nextChain) {
        chainObject = nextChain;
    }

    @Override
    public boolean validateCharacter(String targetString, int index) {
        int targetStringLength = targetString.length();
        boolean currentResponsibility = index < targetStringLength;
        if (chainObject == null) 
            return currentResponsibility;
        else
            return currentResponsibility && chainObject.validateCharacter(targetString, index + 1);
    }
    
}
