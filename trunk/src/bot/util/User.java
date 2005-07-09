package bot.util;

public class User {
	private String nick;
	private String hostmask;
	private String login;
	
	public User(String nick, String hostmask, String login) {
		this.nick = nick;
		this.hostmask = hostmask;
		this.login = login;
	}
	
	public User(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	public String getHostmask() {
		return hostmask;
	}
	
	public String getLogin() {
		return login;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof User))
			return false;
		else
			return ((User)obj).nick.equals(this.nick);
	}
	
	public int hashCode() {
		return this.nick.hashCode();
	}

	public void setHostmask(String hostmask) {
		this.hostmask = hostmask;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
}
