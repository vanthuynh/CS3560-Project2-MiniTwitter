package VanMiniTwitter;

public class CountGroupSysEntryVisitor implements SysEntryVisitor {

	// Calculate total number of groups (Root included)
	@Override
	public double visit(Group group) {
		double count = 1;
		
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof Group) {
				count = count + visit((Group)element);
			}
			else {
				count = count + visit((User)element);
			}
		}
		
		return count;	
	}

	@Override
	public double visit(User user) {
		return 0;
	}

}
