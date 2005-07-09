package feature.util.tell;

import java.util.Date;

public class TellMessage {

	private Date time;
	private String sender;
	private String message;
	
	public TellMessage(String sender, String message) {
		this.time = new Date();
		this.sender = sender;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getSender() {
		return sender;
	}

	public Date getTime() {
		return time;
	}
}
