package VanMiniTwitter;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
	
	// List of Observers
	private List<Observer> observers = new ArrayList<Observer>();
	
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	/**
	 * Observer Pattern: the Subject iterate through the list of 
	 * observers and call update() method
	 */
	public void notifyObservers() {
		for(Observer observer : observers) {
			
			observer.update(this);

		}
	}
	

}

