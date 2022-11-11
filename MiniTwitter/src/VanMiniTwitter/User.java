package VanMiniTwitter;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

// Represents a user
public class User extends Subject implements SystemEntry, Observer {
	
	// Instance variables
	private String userID;
	private ArrayList<User> currentlyFollowing;
	private ArrayList<String> messages;
	DefaultListModel newsFeedModel;
	
	// Constructor
	public User(String userID) {
		this.userID = userID;
		this.currentlyFollowing = new ArrayList<User>();
		this.messages = new ArrayList<String>();
		this.newsFeedModel = new DefaultListModel();
	}

	@Override
	public String getId() {
		return this.userID;
	}

	public ArrayList<String> getMessages() {
		return this.messages;
	}


	public ArrayList<User> getCurrentlyFollowing() {
		return this.currentlyFollowing;
	}


	public DefaultListModel getFeedModel() {
		return this.newsFeedModel;
	}
	
	/*
	 *  Adds given user to this user's following list and adds this user as an observer 
	 *  of the given
	 */
	public void follow(User user) {
		this.currentlyFollowing.add(user);
		user.attach(this);
	}
	
	/*
	 * Adds message to user's message list and adds to feed model
	 * also notifies followers of new post 
	 */
	public void postMessage(String message) {
		this.messages.add(message);
		this.newsFeedModel.insertElementAt(userID + " : " + message, 0);
		notifyObservers();				
	}
	
	@Override
	public double accept(SysEntryVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public void update(Subject updatedUser) {
		int index = ((User)updatedUser).getMessages().size() - 1;
		
		String message = ((User)updatedUser).getId() + " : " + 
							((User)updatedUser).getMessages().get(index);
		
		// Adds updatedUser's message post to this user's feed
		this.newsFeedModel.insertElementAt(message , 0);
		
	}
}
