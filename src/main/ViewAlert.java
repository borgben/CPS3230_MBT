package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ViewAlert {
	
    //Display Variables
    public String heading;
    public String description;
    public String price;
    public String url;
    public String imgUrl;
    public int iconType;
    
    public ViewAlert (WebElement displayedAlert)
    {
    	
    	this.heading = displayedAlert.findElement(By.tagName("h4")).getText();
    	this.description = displayedAlert.findElements(By.tagName("td")).get(2).getText();
    	this.price = displayedAlert.findElements(By.tagName("td")).get(3).getText();
    	this.url = displayedAlert.findElements(By.tagName("td")).get(4).findElement(By.tagName("a")).getAttribute("href");
    	this.imgUrl = displayedAlert.findElement(By.xpath("//td[@rowspan='4']")).findElement(By.tagName("img")).getAttribute("src");
    	this.iconType = mapIconToAlertType(extractIconFile(displayedAlert.getAttribute("innerHTML").toString()));
    }
    
    
    public boolean isValidAlert(Alert alert)
    {
    	boolean isValid = true;
    	
    	if (this.headingIsNull())
    	{
    		System.out.println("ERROR: Alert has no heading! ");
    		isValid = false;
    	}
    	
    	if(this.descriptionIsNull())
    	{
    		System.out.println("ERROR: Alert has no description! ");
    		isValid = false;
    	}
    	
    	if(this.priceIsNull())
    	{
    		System.out.println("ERROR: Alert has no price! ");
    		isValid = false;
    	}
    	
    	if(this.urlIsNull())
    	{
    		System.out.println("ERROR: Alert has no link to original website! ");
    		isValid = false;
    	}
    	
    	if(this.imgUrlIsNull())
    	{
    		System.out.println("ERROR: Alert has no product image! ");
    		isValid = false;
    	}
    	
    
    	return isValid && (alert.alertType==this.iconType);
    }
    
    public boolean descriptionIsNull()
    {
    	if(this.description == null || this.description == "")
    	{
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean headingIsNull()
    {
    	if(this.heading == null || this.heading == "")
    	{
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean priceIsNull()
    {
    	if(this.price == null || this.price == "")
    	{
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean urlIsNull()
    {
    	if(this.url == null || this.url == "")
    	{
    		return true;
    	}else{
    		return false;
    	}
    }
    
    
    public boolean imgUrlIsNull()
    {
    	if(this.imgUrl == null || this.imgUrl == "")
    	{
    		return true;
    	}else{
    		return false;
    	}
    }
    
    
	int mapIconToAlertType(String iconName)
	{
		switch (iconName) {
			case "icon-car.png":
				return 1;
			case "icon-boat.png":
				return 2;
			case "icon-property-rent.png": 
				return 3;
			case "icon-property-sale.png": 
				return 4;
			case "icon-toys.png": 
				return 5;
			case "icon-electronics.png":
				return 6;
			default:
				return 0;
		
		}
			
	}
	
	public String extractIconFile(String htmlBlock)
	{
		String pattern = "(?<=src=\").*(?=\" width)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(htmlBlock);
		m.find();
		return m.group().replaceAll("/images/", "");
		
	}
}
