package main;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;

public class MarketUMModelTest implements FsmModel{
	
	User user = new User("5b403bf6-4f10-4bb3-ba93-54a4513864e2");
	UserEnum userState = UserEnum.LOGGED_OUT;
	int noAlerts = 0;
	
	public boolean logoutGuard() throws IOException
	{
		return userState.equals(UserEnum.LOGGED_IN) && user.isLoggedIn();
	}
	
	public @Action void logout() throws IOException
	{
		System.out.println("Hello from Logout");
		this.user.loggout();
		this.userState = UserEnum.LOGGED_OUT;
		
		Assert.assertEquals(false, this.user.isLoggedIn());
	}
	public boolean loginGuard() throws IOException
	{
		return userState.equals(UserEnum.LOGGED_OUT) && !user.isLoggedIn();
	}
	
	public @Action void login() throws IOException
	{
		System.out.println("Hello from Login");
		this.user.login();
		this.userState = UserEnum.LOGGED_IN;
		
		Assert.assertEquals(true, this.user.isLoggedIn());
	}
	
	
	public @Action void createAlert() throws IOException
	{
		System.out.println("Hello from CREATE");
		this.user.createAlert();
		this.noAlerts ++;
		Assert.assertEquals(noAlerts, this.user.checkAlerts());
	}
	
	public @Action void deleteAlert() throws IOException
	{
		System.out.println("Hello from DELETE");
		user.deleteAlerts();
		this.noAlerts = 0;
		Assert.assertEquals(noAlerts, this.user.checkAlerts());
	}
	
	public boolean viewAlertsGuard() throws IOException
	{
		return userState.equals(UserEnum.LOGGED_IN) && user.isLoggedIn();
	}
	public @Action void viewAlerts() throws InterruptedException
	{
		System.out.println("Hello from View");
		List<WebElement> displayedAlerts = user.viewAlerts();
		
		Assert.assertEquals(user.validateAlerts(displayedAlerts),true);
		
	}
	
	@Override
	public UserEnum getState() 
	{
		return userState;
	}

	@Override
	public void reset(boolean b) 
	{
		if(b)
		{
			System.out.println("Full Reset");
			this.user.driver.close();
			this.user = new User("5b403bf6-4f10-4bb3-ba93-54a4513864e2");
		}else{
			System.out.println("Partial Reset");
		}
		this.noAlerts=0;
		this.userState = UserEnum.LOGGED_OUT;
	}

	@Test
	public void TestRunner()
	{
		final RandomTester tester = new RandomTester( new MarketUMModelTest());
		tester.setRandom(new Random());
		tester.buildGraph();
		tester.addListener("verbose");
		tester.addCoverageMetric(new TransitionPairCoverage());
		tester.addCoverageMetric(new StateCoverage());
		tester.addCoverageMetric(new ActionCoverage());
		tester.generate(100);
		tester.printCoverage();
	}

}
