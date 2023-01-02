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
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
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
	
	private List<Status> eventsLog() throws IOException
	{
		Type listType = new TypeToken<ArrayList<Status>>(){}.getType();
		String baseUrl= "https://api.marketalertum.com/EventsLog/" + userId;
		URL url = new URL(baseUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();

		reader.lines().forEach(a->stringBuilder.append(a));
		List<Status> events = new Gson().fromJson(stringBuilder.toString(), listType);

		return events;
	}
	public User(String userId)
	{
		this.userId = userId;
		try {
			this.deleteAlerts();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.eventsLog();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		PostAlert newAlert = new PostAlert(alertType,"Test","TestDescription","https://olimpusmusic.com/product/adam-audio-t7v/","https://olimpusmusic.com/wp-content/uploads/2022/10/IT12575.jpg",21900, userId);
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
	public List<WebElement> viewAlerts() throws InterruptedException
	{
		driver.findElement(By.xpath("//a[@href='/Alerts/List']")).click();
		Thread.sleep(1000);
		return driver.findElements(By.xpath("//table[@border='1']"));
	}
	public void alertsValidated() {}
	public boolean isLoggedIn() throws IOException {
		List<WebElement> elts = driver.findElements(By.xpath("//a[@href='/Home/Logout']"));
		if (elts.size()>0)
		{
			return true;
		}else
		{
			return false;
		}
	}
	public int checkAlerts() throws IOException
	{
		List<Status> status = eventsLog();
		try{
			Status lastEvent = status.get(status.size()-1);
			this.alerts = lastEvent.systemState.alerts;
			return lastEvent.systemState.alerts.size();
		}catch(Exception e)
		{
			return -1;
		}
	}
	public boolean validateAlerts(List<WebElement> displayedAlerts ) 
	{
		boolean isValid = true;
			if(displayedAlerts.size() > 5)
			{
				System.out.println(" ERROR: More than 5 alerts displayed to user.");
				return false;
			}
			
			if(alerts.size() > 5 && displayedAlerts.size() !=5)
			{
				System.out.println(" ERROR: More than 5 alerts exist but less than 5 have been displayed to user.");
				return false;
			}
			
			if (alerts.size() <=5 && displayedAlerts.size() != alerts.size())
			{
				System.out.println(" ERROR: Less than 5 alerts exist but there exists a mismatch between the number of actual alerts and displayed alerts.");
				return false;
			}
			
			for(int i=0;i<displayedAlerts.size();i++)
			{
				ViewAlert displayedAlert = new ViewAlert(displayedAlerts.get(i)) ;
				isValid = isValid && displayedAlert.isValidAlert(alerts.get(i));
			}
			
			return isValid;
	}

}
