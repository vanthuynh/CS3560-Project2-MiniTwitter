package VanMiniTwitter;

public class CountUserSysEntryVisitor implements SysEntryVisitor {
	// Calculate total number of users
	@Override
	public double visit(Group group) {
		double count = 0;
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof Group) {
				count = count + visit((Group)element);
			}
			else {		
				count =  count + visit((User)element);
			}
		}
		
		return count;
		
	}

	@Override
	public double visit(User user) {
		return (double)1;
	}

}
