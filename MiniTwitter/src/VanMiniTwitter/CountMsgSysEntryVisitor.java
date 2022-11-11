package VanMiniTwitter;

public class CountMsgSysEntryVisitor implements SysEntryVisitor {

	// Calculate total number of messages
	@Override
	public double visit(Group group) {
		double count = 0;
		
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof User) {
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
		int count = user.getMessages().size();
		return (double)count;
	}

}
