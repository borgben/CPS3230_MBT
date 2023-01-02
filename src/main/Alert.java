package main;

public class Alert  {
		
		// Actual Alert Variables
	    public int alertType;
	    public String heading;
	    public String description;
	    public String url;
	    public String imageUrl;
	    public String postedBy;
	    public int priceInCents;
	    public String postDate;
	    
		
	    public Alert(int alertType, String heading, String description, String url, String imageUrl, int priceInCents, String postedBy, String postDate) {
	        this.alertType = alertType;
	        this.heading = heading;
	        this.description = description;
	        this.url = url;
	        this.imageUrl = imageUrl;
	        this.postedBy = postedBy;
	        this.priceInCents = priceInCents;
	        this.postDate = postDate;
	    }
	    
	    @Override
	    public String toString() {
	        return "Alert{" +
	                "alertType=" + alertType +
	                ", heading='" + heading + '\'' +
	                ", description='" + description + '\'' +
	                ", url='" + url + '\'' +
	                ", imageUrl='" + imageUrl + '\'' +
	                ", postedBy='" + postedBy + '\'' +
	                ", priceInCents=" + priceInCents +
	                '}';
	    }


}
