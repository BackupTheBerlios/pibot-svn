package feature.util.event;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Event implements Serializable {

	private static final long serialVersionUID = 50002;

	private Set<String> yesUsers = new HashSet<String>();

	private Set<String> maybeUsers = new HashSet<String>();

	private Set<String> noUsers = new HashSet<String>();

	private String name;

	private Date date;

	public Event(String name, Date date) {
		this.date = date;
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public void addUserYes(String name) {
		synchronized (this) {
			yesUsers.add(name);
			if (maybeUsers.contains(name))
				maybeUsers.remove(name);
			if (noUsers.contains(name))
				noUsers.remove(name);
		}
	}

	public void addUserMaybe(String name) {
		synchronized (this) {
			maybeUsers.add(name);
			if (yesUsers.contains(name))
				yesUsers.remove(name);
			if (noUsers.contains(name))
				noUsers.remove(name);
		}
	}

	public void addUserNo(String name) {
		synchronized (this) {
			noUsers.add(name);
			if (yesUsers.contains(name))
				yesUsers.remove(name);
			if (maybeUsers.contains(name))
				maybeUsers.remove(name);
		}
	}

	public String toString() {
		StringBuffer retVal = new StringBuffer(name);
		retVal.append(" scheduled for: ");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		retVal.append(sdf.format(date));
		retVal.append(" ");
		retVal.append("[yes]: ");
		printUsers(yesUsers);
		retVal.append("[maybe]: ");
		printUsers(maybeUsers);
		retVal.append("[no]: ");
		printUsers(noUsers);

		return retVal.toString();
	}

	private static String printUsers(Set<String> users) {
		StringBuffer names = new StringBuffer();
		for (String name : users) {
			names.append(name);
			names.append(", ");
		}
		return names.toString();
	}
}
