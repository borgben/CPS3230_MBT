package main;

public class Status{
	String id;
	String timestamp;
	int eventLogType;
	String userId;
	SystemState systemState;
	
	public Status(String id, String timestamp, int eventLogType, String userId, SystemState systemState)
	{
		this.id = id;
		this.timestamp = timestamp;
		this.eventLogType = eventLogType;
		this.userId = userId;
		this.systemState = systemState;
	}
	
    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", eventLogType='" + eventLogType + '\'' +
                ", userId='" + userId + '\'' +
                ", systemState=" +systemState.toString() + '\''+
                '}';
    }
}
