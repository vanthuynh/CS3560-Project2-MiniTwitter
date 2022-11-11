package VanMiniTwitter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;



public class UserView extends JDialog {

	private final JPanel contentPanel;
	private JTextField txtUserId;


	/**
	 * Constructor: Create the User Window.
	 */
	public UserView(User user, Group system) {
		// Creates Panel which holds all the ui elements
		contentPanel = new JPanel();
		setBounds(100, 100, 394, 326);
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
		
		
		JLabel lblNewsFeed = new JLabel("News Feed:");
		lblNewsFeed.setFont(new Font("Calibri", Font.BOLD, 16));
		JScrollPane newsFeedScrollPane = new JScrollPane();

		/*
		 * List of messages posted by who user is following including those they posted
		 * themselves
		 */
		JList listNewsFeed = new JList();
		newsFeedScrollPane.setViewportView(listNewsFeed);
		listNewsFeed.setFont(new Font("Calibri", Font.PLAIN, 15));
		listNewsFeed.setModel(user.getFeedModel());
		


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
		btnPostTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnPostTweet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String message = txtrEnterTweet.getText();
				user.postMessage(message);
			}
		});
		
		
		/*
		 * GUI LAYOUT
		 */
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtrEnterTweet, GroupLayout.DEFAULT_SIZE, 350, 
								Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(
									Alignment.LEADING)
								.addComponent(txtUserId, 0, 0, Short.MAX_VALUE)
								.addGroup(gl_contentPanel.createParallelGroup(
										Alignment.LEADING, false)
									.addComponent(listFollowing, Alignment.TRAILING, 
											GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblCurrentlyFollowing, 
											Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(
									Alignment.LEADING)
								.addComponent(lblNewsFeed)
								.addGroup(gl_contentPanel.createParallelGroup(
										Alignment.TRAILING, false)
									.addGroup(Alignment.LEADING, 
											gl_contentPanel.createSequentialGroup()
										.addComponent(btnFollowUser)
										.addPreferredGap(ComponentPlacement.RELATED, 
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnPostTweet))
									.addComponent(newsFeedScrollPane, Alignment.LEADING,
											GroupLayout.PREFERRED_SIZE, 215, 
											GroupLayout.PREFERRED_SIZE)))))
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
						.addComponent(listFollowing, GroupLayout.PREFERRED_SIZE, 131,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(newsFeedScrollPane, GroupLayout.PREFERRED_SIZE, 131, 
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, 26, 
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFollowUser, GroupLayout.PREFERRED_SIZE, 26, 
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPostTweet))
					.addGap(1)
					.addComponent(txtrEnterTweet, GroupLayout.PREFERRED_SIZE, 65,
							GroupLayout.PREFERRED_SIZE)
					.addGap(73))
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
}
