package VanMiniTwitter;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PositiveSysEntryVisitor implements SysEntryVisitor{
	// Instance Variable
	private ArrayList<String> positiveWords;
	
	public PositiveSysEntryVisitor() {
		List<String> words = Arrays.asList(new String[] {
				"admire", "adore", "amazing", "amusing", "applaud", "awesome", "best", 
				"bliss", "blessed", "beautiful", "celebrate", "congrats", "congratulations",
				"cute", "cheerful", "confident", "delighted", "dope", "excited", "exciting",
				"excellent", "enjoy", "elated", "flourishing", "great", "good", "happy", 
				"heartwarming", "incredible", "inspiring", "joy", "kind", "like", "love",
				"marvelous", "motivating", "motivational", "nice", "optomistic", "pretty",
				"perfection", "paradise", "remarkable", "stunning", "sweet", "positivity",
				"thankful", "thriving", "thrive", "touched", "uplifting", "wonderful",
				"yay"});
		
		positiveWords = new ArrayList<String>(words);
		
	}

	// Calculates total number of positive messages >> messages containing any of the
	// words in positiveWords
	@Override
	public double visit(Group group) {
		double count = 0;
		
		for(SystemEntry element : group.getGroupsAndUsers()) {
			if ( element instanceof User) {
				count = count + visit((User)element);
			}
			else {
				count = count + visit((Group)element);
			}
		}
		
		return count;
	}

	@Override
	public double visit(User user) {
		double count = 0;
		
		for (String message : user.getMessages()) {
			for(String word : positiveWords) {
				if (message.contains(word)) {
					count = count + 1;
				}
			}
		}
		
		return count;
	}

}
