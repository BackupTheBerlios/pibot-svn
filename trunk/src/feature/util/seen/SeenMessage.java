package feature.util.seen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeenMessage {

	private Date time;
	private String message;
	
	public SeenMessage(String message) {
		time = new Date();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Date getTime() {
		return time;
	}
	
	public String toString() {
		StringBuffer response = new StringBuffer(" on ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		response.append(sdf.format(time));
		response.append(message);
		return response.toString();
	}
}
