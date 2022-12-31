package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class User {
	public String userId;
	WebDriver driver;
	public int noDisplayedAlerts;
	public List<Alert> alerts;
	
	
	public User(String userId)
	{
		this.userId = userId;
        this.driver = new ChromeDriver();
        driver.get("https://www.marketalertum.com/");
        this.alerts = new ArrayList<Alert>();
	}
	
	public void mismatchedAlertSizes() {}
	public void validateAlert() {}
	public void createAlert() throws IOException 
	{
		Random rand = new Random();
		int alertType = rand.nextInt(6)+1;
		Alert newAlert = new Alert(alertType,"Test","TestDescription","https://olimpusmusic.com/product/adam-audio-t7v/","https://olimpusmusic.com/wp-content/uploads/2022/10/IT12575.jpg",21900, userId);
		URL url = new URL("https://api.marketalertum.com/Alert");
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection)con;
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		String jsonString = new Gson().toJson(newAlert);
		byte[] out = jsonString.getBytes(StandardCharsets.UTF_8);
		int length = out.length;
		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		http.connect();
		try(OutputStream os = http.getOutputStream()) {
		    os.write(out);
		}
		this.alerts.add(newAlert);
	}
	public void deleteAlerts() throws IOException 
	{
		URL url = new URL(String.format("https://api.marketalertum.com/Alert?userId=%s",userId));
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection)con;
		http.setRequestMethod("DELETE");
		http.setDoOutput(true);
		http.setRequestProperty(
			    "Content-Type","application/json; charset=UTF-8" );
		http.connect();
		http.getResponseCode();
		this.alerts = new ArrayList<Alert>();
	}
	public void login() 
	{
		driver.findElement(By.xpath("//a[@href='/Alerts/Login']")).click();
		driver.findElement(By.xpath("//input[@id='UserId']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
	}
	public void loggout() 
	{
		driver.findElement(By.xpath("//a[@href='/Home/Logout']")).click();
	}
	
	public void viewAlerts()
	{
		driver.findElement(By.xpath("//a[@href='/Alerts/List']")).click();
	}
	
	public void alertsValidated() {}
	
//	public void userViewedAlerts(List<WebElement> webScrapeAlerts, List<Alert> alerts) 
//	{	
//		
//		if (alerts.size()<=5)
//		{
//			for (int i=0; i< alerts.size(); i++)
//			{
//				
//				alerts.get(i).setDisplayAttributes(webScrapeAlerts.get(i));
//				System.out.println("Alert Displayed Description " + alerts.get(i).displayDescription);
//				alerts.get(i).viewingAlert();
//			}
//		}else{
//			for (int i=0; i<5; i++)
//			{
//				alerts.get(i).setDisplayAttributes(webScrapeAlerts.get(i));
//				System.out.println("Alert Displayed Description " + alerts.get(i).displayDescription);
//				alerts.get(i).viewingAlert();
//			}
//		}
//
//	}
	
	
//	public void matchEventToFn(Status event) throws InterruptedException
//	{
//		System.out.println(event.eventLogType);
//		switch (event.eventLogType) {
//			case 0:
//				this.noAlerts = event.systemState.alerts.size();
//				alertCreated();return;
//			case 1:
//				this.noAlerts = event.systemState.alerts.size();
//				alertsDeleted();return;
//			case 5:
//				this.userValidLogin();return;
//			case 6:
//				userLoggedOut();return;
//			case 7:
//				Thread.sleep(2000);
//				List<WebElement> webScrapeAlerts = driver.findElements(By.xpath("//table[@border='1']"));
//				System.out.println(webScrapeAlerts.size());
//				this.noDisplayedAlerts = webScrapeAlerts.size();
//				this.noAlerts = event.systemState.alerts.size();
//				userViewedAlerts(webScrapeAlerts, event.systemState.alerts);
//				return;
//			default:
//				return;
//		}
//	}
}
