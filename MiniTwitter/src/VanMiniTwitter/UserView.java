package VanMiniTwitter;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;



public class UserView extends JDialog {

	private final JPanel contentPanel;
	private JTextField txtUserId;


	/**
	 * Constructor: Create the User Window.
	 */
	public UserView(User user, Group system) {
		// Creates Panel which holds all the ui elements
		contentPanel = new JPanel();
		setBounds(100, 100, 394, 369);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		
		JLabel lblCurrentlyFollowing = new JLabel("Currently Following:");
		lblCurrentlyFollowing.setFont(new Font("Calibri", Font.BOLD, 15));
		
		/*
		 * List of who user is currently following
		 */
		JList listFollowing = new JList();
		listFollowing.setFont(new Font("Calibri", Font.PLAIN, 15));
		listFollowing.setModel(setFollowListModel(user)); 
		
		
		/*
		 * Text field where user enter user id of who they want to follow
		 */
		txtUserId = new JTextField();
		txtUserId.setText("USER ID");
		txtUserId.setColumns(10);
		
		JButton btnFollowUser = new JButton("Follow User");
		
		
		/*
		 * Follows user with user id when button is clicked and updates list model
		 */
		btnFollowUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = txtUserId.getText();
				
				if (id.equals(user.getId())) {
					JOptionPane.showMessageDialog(null, "CANNOT FOLLOW YOURSELF");
				}
				else {
					User userToFollow = system.findUser(id);

					if(userToFollow == null) {
						JOptionPane.showMessageDialog(null, "\"" + id 
								+ "\" DOES NOT EXIST");
					}
					else if (user.getCurrentlyFollowing().contains(userToFollow)){
						JOptionPane.showMessageDialog(null, "You are already following " 
								+ id);
					}
					else {
						user.follow(userToFollow);
						listFollowing.setModel(setFollowListModel(user)); 
					}
				}
			}
		});
		
		
		String lastUpdateTime = getTimeAndDate(user.getLastUpdateTime());
		
		JTextPane txtpnLastUpdated = new JTextPane();
		txtpnLastUpdated.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtpnLastUpdated.setText("Last Updated: " + lastUpdateTime);
		txtpnLastUpdated.setEditable(false);
		txtpnLastUpdated.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		
		
		
		JLabel lblNewsFeed = new JLabel("News Feed:");
		lblNewsFeed.setFont(new Font("Calibri", Font.BOLD, 16));
		JScrollPane newsFeedScrollPane = new JScrollPane();

		/*
		 * List of messages posted by who user is following including those they posted
		 * themselves
		 * 
		 * Listens for changes to list to updated the last update time on UI
		 */
		JList listNewsFeed = new JList();
		newsFeedScrollPane.setViewportView(listNewsFeed);
		listNewsFeed.setFont(new Font("Calibri", Font.PLAIN, 15));
		listNewsFeed.setModel(user.getFeedModel());
		user.getFeedModel().addListDataListener(new ListDataListener() {
			@Override
			public void contentsChanged(ListDataEvent arg0) {
				String lastUpdateTime = getTimeAndDate(user.getLastUpdateTime());
				txtpnLastUpdated.setText("Last Updated: " + lastUpdateTime);
				
			}

			@Override
			public void intervalAdded(ListDataEvent arg0) {
				String lastUpdateTime = getTimeAndDate(user.getLastUpdateTime());
				txtpnLastUpdated.setText("Last Updated: " + lastUpdateTime);
			}

			@Override
			public void intervalRemoved(ListDataEvent arg0) {
				String lastUpdateTime = getTimeAndDate(user.getLastUpdateTime());
				txtpnLastUpdated.setText("Last Updated: " + lastUpdateTime);
				
			}
		});
		

	

		/*
		 * Message user wants to post
		 */
		JTextArea txtrEnterTweet = new JTextArea();
		txtrEnterTweet.setFont(new Font("Calibri", Font.PLAIN, 16));
		txtrEnterTweet.setText("Share your thoughts...");
		
		/*
		 *  OBSERVER PATTERN: Posts message when user clicks button and updates followers
		 */
		JButton btnPostTweet = new JButton("Post");
		btnPostTweet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String message = txtrEnterTweet.getText();
				user.postMessage(message);
			}
		});
		
		
		// Time object was created
		String creationTime = getTimeAndDate(user.getCreationTime());
		
		JTextPane txtpnCreationTime = new JTextPane();
		txtpnCreationTime.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtpnCreationTime.setText("Creation Time: " + creationTime);
		txtpnCreationTime.setEditable(false);
		txtpnCreationTime.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		
		
		
		/*
		 * GUI LAYOUT
		 */
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnCreationTime, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
						.addComponent(txtrEnterTweet, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
						.addComponent(txtpnLastUpdated, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtUserId, 0, 0, Short.MAX_VALUE)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(listFollowing, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblCurrentlyFollowing, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(btnFollowUser)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnPostTweet))
								.addComponent(newsFeedScrollPane, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
								.addComponent(lblNewsFeed))))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCurrentlyFollowing)
						.addComponent(lblNewsFeed))
					.addGap(1)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(listFollowing, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
						.addComponent(newsFeedScrollPane, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFollowUser, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPostTweet))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtrEnterTweet, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(txtpnLastUpdated, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnCreationTime, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(42))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
	
	// Converts list of who user is following into a DefaultListModel
	private DefaultListModel setFollowListModel(User user) {
		DefaultListModel model = new DefaultListModel();
		
		for (User id : user.getCurrentlyFollowing()) {
			model.addElement(id.getId());
		}
		
		return model;
	}
	
	private String getTimeAndDate(long timestamp) {
		Calendar dateAndTime = Calendar.getInstance();
		dateAndTime.setTimeInMillis(timestamp);
		
		String time = (dateAndTime.get(Calendar.DAY_OF_MONTH) + "." + 
				(dateAndTime.get(Calendar.MONTH) + 1) + "." +
				dateAndTime.get(Calendar.YEAR) + ", " + 
				dateAndTime.get(Calendar.HOUR_OF_DAY) + ":" +
				dateAndTime.get(Calendar.MINUTE) + ":" + 
				dateAndTime.get(Calendar.SECOND) + ":" +
				dateAndTime.get(Calendar.MILLISECOND));
		
		return time;
	}
	
}
