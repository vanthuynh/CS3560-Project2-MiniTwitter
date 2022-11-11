/**
 * AdminControlPanel
 */
package VanMiniTwitter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class AdminControlPanel extends JFrame {
	// SINGLETON pattern: static field to store the one and only copy of object
	private static AdminControlPanel frame;
	
	private JPanel contentPane;
	private JTextField txtUserId;
	private JTextField txtGroupId;
	
	private Group system;
    

	
	// SINGLETON pattern: getter method to access the single instance of this class.
	public static AdminControlPanel getFrame() {
		if(frame == null) {
			synchronized(AdminControlPanel.class) {
				if(frame == null) {
					frame = new AdminControlPanel();
				}
			}
		}
		return frame;
	}
	
	
	/**
	 * SINGLETON pattern: private constructor
	 */
	private AdminControlPanel() {
		setTitle("Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 518, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		

		
		/*
		 * TREE DISPLAY OF GROUPS AND USERS
		 */
		system = new Group("Root");
		JTree treeGroupsUsers = new JTree();
		treeGroupsUsers.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode(system.getId())));
		
		// FORMATS TREE
		treeGroupsUsers.setCellRenderer(new DefaultTreeCellRenderer() {

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, 
					boolean selected, boolean expanded, boolean isLeaf, int row, 
					boolean focused) {
				super.getTreeCellRendererComponent(treeGroupsUsers, value, selected, 
						expanded, isLeaf, row, focused);
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				
				// No icon next to node name
				setIcon(null);
				
				// BOLD if group, PLAIN if user.
				if(node.getAllowsChildren()) {
					setFont(new Font("Calibri", Font.BOLD, 14));
				}
				else
				{
					setFont(new Font("Calibri", Font.PLAIN, 14));
				}

				return this;
			}
		});

		expandAllNodes(treeGroupsUsers, 0, treeGroupsUsers.getRowCount());
		
		
		/*
		 * TEXT FIELD FOR USER ID - FOR ID OF USER BEING ADDED
		 */
		txtUserId = new JTextField();
		txtUserId.setFont(new Font("Calibri", Font.PLAIN, 16));
		txtUserId.setForeground(Color.BLACK);
		txtUserId.setText("USER ID");
		txtUserId.setColumns(10);
		
		/*
		 * BUTTON TO ADD USER
		 */
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		/*
		 * When button is clicked, adds user.
		 */
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String userID = txtUserId.getText();
				
				// if nothing on tree has been selected
				if (treeGroupsUsers.getSelectionPath() == null) {
					JOptionPane.showMessageDialog(null, "Please select a group to add the "
							+ "user to.");
				}
				else {
					// Gets group that was selected to add user into
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
							treeGroupsUsers.getSelectionPath().getLastPathComponent();
					
	
					// If a user was selected instead of a group
					if (!selectedNode.getAllowsChildren()) {
						JOptionPane.showMessageDialog(null, "Cannot add a user to a user! "
								+ "Please select a group.");
					}
					else if (system.isExistingID(userID)) {
							JOptionPane.showMessageDialog(null, "ID ALREADY EXISTS!");	
					}
					else {
						User newUser = new User(userID);
						Group group = system.findGroup(selectedNode.toString());
						
						if (group == null) {
							JOptionPane.showMessageDialog(null, "GROUP NOT FOUND");
						}
						else {
							group.addGroupOrUser(newUser);
							
							/*
							 *  Creates the user and adds under selected group. 
							 *  allowsChildren = false
							 */
							DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
									userID, false);
							selectedNode.add(newNode);
							
							// Reloads tree display to show updated version with new user
							((DefaultTreeModel)treeGroupsUsers.getModel()).reload();
							expandAllNodes(treeGroupsUsers, 0, 
									treeGroupsUsers.getRowCount());
						}
					}
				}
			}
		});
		
		
		/*
		 * TEXT FIELD FOR USER ID - FOR ID OF GROUP BEING ADDED
		 */
		txtGroupId = new JTextField();
		txtGroupId.setFont(new Font("Calibri", Font.PLAIN, 16));
		txtGroupId.setForeground(Color.BLACK);
		txtGroupId.setText("GROUP ID");
		txtGroupId.setColumns(10);
		
		/*
		 * BUTTON TO ADD USER
		 */
		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		/*
		 *  When button is clicked, adds group
		 */
		btnAddGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String groupID = txtGroupId.getText();
				
				// If nothing from tree was selected
				if (treeGroupsUsers.getSelectionPath() == null) {
					JOptionPane.showMessageDialog(null, "Please select a group to add the "
							+ "group to.");
				}
				else {
					// Gets group that was selected to add user into
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
							treeGroupsUsers.getSelectionPath().getLastPathComponent();
					
					// If a group was not selected.
					if (!selectedNode.getAllowsChildren()) {
						JOptionPane.showMessageDialog(null, "Cannot add a group to a user! "
								+ "Please select a group.");
					}
					else if(system.isExistingID(groupID)){
						    JOptionPane.showMessageDialog(null, "ID ALREADY EXISTS!");
					}
					else {
						Group newGroup = new Group(groupID);
						Group group = system.findGroup(selectedNode.toString());
						
						if (group == null) {
							JOptionPane.showMessageDialog(null, "PARENT NODE NOT FOUND");
						}
						else {
							group.addGroupOrUser(newGroup);
							
							// Creates the group and adds group under selected group
							DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
									groupID);
							selectedNode.add(newNode);
							
							// Reloads tree display to show updated version with new user
							((DefaultTreeModel)treeGroupsUsers.getModel()).reload();
							expandAllNodes(treeGroupsUsers, 0, 
									treeGroupsUsers.getRowCount());
						}
					}
				}
			}
		});
		
		

		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		// OPENS USER VIEW WINDOW WHEN CLICKED
		btnOpenUserView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode id = (DefaultMutableTreeNode)
						treeGroupsUsers.getLastSelectedPathComponent();
				
				// If item from tree was selected and it is not a group
				if ( id != null && !id.getAllowsChildren()) {
					String userId = id.toString();
					User user = system.findUser(userId);
					
					// Opens window
					UserView temp = new UserView(user, system);
					temp.setTitle(userId);
					temp.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Please select a user to view.");
				}
			}
		});

		
		
		
		// TextPane to display results from buttons below
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		
		JButton btnUserTotal = new JButton("Show User Total");
		btnUserTotal.setFont(new Font("Calibri", Font.PLAIN, 16)); 
		
		/*
		 * VISITOR : shows total users in system
		 */
		btnUserTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CountUserSysEntryVisitor countUser = new CountUserSysEntryVisitor();
				int userTotal = (int)(system.accept(countUser));
				String message = String.valueOf(userTotal) + " TOTAL USERS";
				textPane.setText(message);
			}
		});
		
		
		
		
		
		JButton btnShowGroupTotal = new JButton("Show Group Total");
		/*
		 * VISITOR : shows total groups in system
		 */
		btnShowGroupTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {	
				CountGroupSysEntryVisitor groupCalc = new CountGroupSysEntryVisitor();
				int groupTotal = (int)(system.accept(groupCalc));
				String message = String.valueOf(groupTotal) + " TOTAL GROUPS";
				textPane.setText(message);
			}
		});
		btnShowGroupTotal.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		
		
		
		JButton btnShowMessageTotal = new JButton("Show Message Total");
		/*
		 * VISITOR : shows total messages in system
		 */
		btnShowMessageTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				CountMsgSysEntryVisitor msgTotal = new CountMsgSysEntryVisitor();
				int messageTotal = (int)(system.accept(msgTotal));
				String message = String.valueOf(messageTotal) + " TOTAL MESSAGES";
				textPane.setText(message);
			}
		});
		btnShowMessageTotal.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		
		
		JButton btnShowPositivePercentage = new JButton("Show Positive Percentage");
		btnShowPositivePercentage.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		/*
		 * VISITOR : shows percentage of positive messages in system
		 */
		btnShowPositivePercentage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				
				String message;
				CountMsgSysEntryVisitor calcTotal = new CountMsgSysEntryVisitor();
				double totalMsgs = system.accept(calcTotal);
				
				if(totalMsgs != 0) {
					PositiveSysEntryVisitor positiveCalc = new PositiveSysEntryVisitor();
					double positive = system.accept(positiveCalc);
					double positivePerc = (100 * (positive / totalMsgs));
	
					message = String.format("%.2f", positivePerc) + "% POSITIVE";
				}
				else {
					message = "0% POSITIVE";
				}
				
				textPane.setText(message);
			}
		});
		

		
		
		/*
		 * LAYOUT OF GUI
		 */
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(treeGroupsUsers, GroupLayout.PREFERRED_SIZE, 217, 
							GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 259, 
								Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(
									Alignment.TRAILING, false)
								.addComponent(txtGroupId, Alignment.LEADING, 149, 149, 
										Short.MAX_VALUE)
								.addComponent(btnOpenUserView, Alignment.LEADING,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 
										Short.MAX_VALUE)
								.addComponent(txtUserId))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, 
									false)
								.addComponent(btnAddUser, GroupLayout.DEFAULT_SIZE, 
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAddGroup, GroupLayout.DEFAULT_SIZE, 
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(btnShowMessageTotal, GroupLayout.DEFAULT_SIZE, 259, 
								Short.MAX_VALUE)
						.addComponent(btnShowGroupTotal, GroupLayout.DEFAULT_SIZE, 259, 
								Short.MAX_VALUE)
						.addComponent(btnShowPositivePercentage, GroupLayout.DEFAULT_SIZE, 
								259, Short.MAX_VALUE)
						.addComponent(btnUserTotal, GroupLayout.DEFAULT_SIZE, 259, 
								Short.MAX_VALUE))
					.addGap(152))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(treeGroupsUsers, GroupLayout.DEFAULT_SIZE, 
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(
									Alignment.BASELINE)
								.addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, 35, 
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAddUser))
							.addGap(1)
							.addGroup(gl_contentPane.createParallelGroup(
									Alignment.BASELINE)
								.addComponent(txtGroupId, GroupLayout.PREFERRED_SIZE, 35, 
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAddGroup))
							.addGap(1)
							.addComponent(btnOpenUserView)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 54, 
									GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnUserTotal)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnShowMessageTotal)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnShowGroupTotal)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnShowPositivePercentage)))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	// Expands all groups in the tree
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
		for(int i = startingIndex; i < rowCount; ++i){
			tree.expandRow(i);
		}

		if(tree.getRowCount() != rowCount){
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}
}
