package main;

import java.io.IOException;


public class Runner {
	
	public static void main(String[] args) throws IOException
	{
		User user = new User("5b403bf6-4f10-4bb3-ba93-54a4513864e2");
		user.login();
		user.viewAlerts();
		user.loggout();
	}

}
