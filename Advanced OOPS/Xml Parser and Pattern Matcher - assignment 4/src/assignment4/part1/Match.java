package assignment4.part1;

/**
 *
 * @author ayishwarya
 */
public class Match {

	private PatternMatchChain headMatcher;

	public Match(String pattern) {
		this.headMatcher = buildMatcherChain(pattern);
	}

	private PatternMatchChain buildMatcherChain(String pattern) {
		//PatternMatchChain head = null;
		//PatternMatchChain prev = null;
		PatternMatchChain current = null;
		char[] patternArray = pattern.toCharArray();

		for (int index = 0; index < patternArray.length; index++) {

			char token = patternArray[index];

			switch (token) {
			case '.':
				current = new SingleWildCharacterMatcher();
			case '*':
				if (index == patternArray.length - 1)
                                    current = new SequenceWildCharacterMatcher (null);
                                else
                                    current = new SequenceWildCharacterMatcher (pattern.charAt(index + 1));
			default:
				current = new NormalCharacterMatcher(token);
			}
                }
                return current;
		
	}

        
        protected int findFirstIn(String text) {
        for(int i = 0; i < text.length(); i++) {
            boolean result = headMatcher.validateCharacter(text, i);
            if (result){
                return i;
            }
        }
        return -1;
        }
        
        public static void main(String[] args) throws Exception {
            Match match = new Match("u*t");
            System.out.print(match.findFirstIn("cacacauat"));
    }
}

