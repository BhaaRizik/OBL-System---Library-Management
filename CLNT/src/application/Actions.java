

/**
 * 
 * In this class we update the data base 
 * with all the actions that the subscriber do 
 * like :edit info., change password, reset password ...
 * 
 * @author Mahmoud Atarieh 
 * 
 * 
 * */



package application;

import java.io.Serializable;

public class Actions implements Serializable {
	private String SubscriberId;
	private String date;
	private String Action;
	public String getSubscriberId() {
		return SubscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		SubscriberId = subscriberId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public Actions(String subscriberId, String date, String action) {
	
		SubscriberId = subscriberId;
		this.date = date;
		Action = action;
	}
	
	
	public String toString()
	{
		return SubscriberId+","+date+","+Action+"\n";	
	}

}
