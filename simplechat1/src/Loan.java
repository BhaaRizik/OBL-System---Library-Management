/**
 * This interface used for 
 * Observer 
 * 
 * @author Bhaa Rizik
 * 
 * */

import java.util.Observer;

public interface Loan {

	 void addObserver(Observer observer);
	  void removeObserver(Observer observer);
	  void notifyAllObservers();
	  
}
