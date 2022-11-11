package VanMiniTwitter;

import java.util.ArrayList;

// Visitor & Composite
public class Group implements SystemEntry {
	// Instance variables
	private String groupId;
	private ArrayList<SystemEntry> groupsAndUsers;
	
	// Constructor
	public Group(String groupId) {
		this.groupId = groupId;
		groupsAndUsers  = new ArrayList<SystemEntry>();
	}
	
	@Override
	public String getId() {
		return this.groupId;
	}
	
	public ArrayList<SystemEntry> getGroupsAndUsers() {
		return this.groupsAndUsers;
	}

	public void addGroupOrUser(SystemEntry id) {
		this.groupsAndUsers.add(id);
	}

	@Override
	public double accept(SysEntryVisitor visitor) {
		for (SystemEntry element : groupsAndUsers) {
			element.accept(visitor);
		}
		
		return visitor.visit(this);
	}
	
	// Checks if ID already exists / is stored
	public boolean isExistingID(String id){
		boolean isExisting = false;
		
		if(this.groupId.equals(id)){
			isExisting = true;
		}
		
		for (SystemEntry element : groupsAndUsers) {

			if (element.getId().equals(id)) {
				isExisting = true;
			}
			
			if (element instanceof Group){
				// If it has existed anywhere
				// Implemented this way in case the previous element matches the id
				// and the next one does not -> so it stays true if it exists
				isExisting = isExisting || ((Group) element).isExistingID(id);
			}	
		}
		return isExisting;
	}
	
	// Finds group with given id
	public Group findGroup(String id) {
		Group group = null;
		
		if (id.equals(groupId)) {
			return this;
		}
		else {
			for(SystemEntry element : groupsAndUsers) {
				
				if(element instanceof Group) {
					// If element is found
					if(((Group)element).findGroup(id) != null){
						group = ((Group)element).findGroup(id);
					}
				}	
			}
		}
		
		return group;
	}

	// Finds user with given id
	public User findUser(String id) {
		User user = null;
		
		for(SystemEntry element : groupsAndUsers) {
			
			if(element instanceof Group) {	
				// If user is found among group
				if(((Group)element).findUser(id) != null){
					user = ((Group)element).findUser(id);
				}
			}	
			// If element is a User and its id matches
			else if (element.getId().equals(id)) {
				return (User)element;
			}
		}
		
		return user;
	}

}
