package VanMiniTwitter;

public class AreValidIDSysEntryVisitor implements SysEntryVisitorCheck {

	@Override
	public boolean visit(Group group) {
		boolean isValid = true;
		
		if(group.getId().contains(" ")) {
			return false;
		}
		
		
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof Group) {
				isValid = visit((Group)element);
			}
			else {
				isValid = visit((User)element);
			}
		}
		
		return isValid;
	}

	@Override
	public boolean visit(User user) {
		if(user.getId().contains(" ")) {
			return false;
		}
		
		return true;
	}

}

