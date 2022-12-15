package VanMiniTwitter;

public class LastUpdatedSysEntryVisitor implements SysEntryVisitorFind {

	@Override
	public User visit(Group group) {
		User lastUpdated = null;
		User temp;
		
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof Group) {
				temp = visit((Group)element);
			}
			else {
				temp = visit((User)element);
			}
			
			if((lastUpdated != null && 
			lastUpdated.getLastUpdateTime() < temp.getLastUpdateTime()) ||
			lastUpdated == null) {
				lastUpdated = temp;
			}
		}
		
		return lastUpdated;
	}

	@Override
	public User visit(User user) {
		return user;
	}

}
