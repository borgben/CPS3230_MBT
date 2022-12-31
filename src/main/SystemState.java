package main;

import java.util.List;

public class SystemState {
	String userId;
	Boolean loggedIn;
	List<Alert> alerts;
	
	public SystemState(String userId, Boolean loggedIn, List<Alert> alerts)
	{
		this.userId = userId;
		this.loggedIn = loggedIn;
		this.alerts = alerts;
	}
	
	
    @Override
    public String toString() {
        return "SystemState{" +
                "userId=" + userId +
                ", loggedIn='" + loggedIn + '\'' +
                ", alerts='" + alerts.toString() +
                '}';
    }
}
