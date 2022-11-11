/****************************************************************************************
 * Name  : Van Huynh
 * Date  : 11 November 2022
 * Class : CS3650.01
 * 
 * Assignment 2
 * Has an admin control panel where users and groups can be added to the system. The
 * analytics of the system can also be calculated and displayed.
 * 
 * User view can be opened.
 * A user can follow another user and make posts. A news feed is displayed and updated
 * whenever a post is made by someone they are following.
 * 
 * No requirement for unfollowing or deleting users/groups.
 * 
 * Assignment meant to practice use of Singleton, Composite, Visitor, and Observer pattern
 ****************************************************************************************/
package VanMiniTwitter;

import java.awt.EventQueue;
import java.util.ArrayList;

	public class Driver {
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						AdminControlPanel.getFrame().setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
