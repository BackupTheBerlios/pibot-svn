package bot.listener.impl;

import java.util.Date;

import bot.listener.UserAgentRequestListener;

public class UserAgentRequestListenerImpl extends BotListenerImpl implements UserAgentRequestListener {

	protected String version;
	protected String finger;
	
	public UserAgentRequestListenerImpl() {
		this("0.0", "");
	}
	
	public UserAgentRequestListenerImpl(String version, String finger) {
		this.version = version;
		this.finger = finger;
	}
	
	public void onFinger(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		bot.sendRawLine("NOTICE " + sourceNick + " :\u0001FINGER " + finger + "\u0001");

	}

	public void onPing(String sourceNick, String sourceLogin,
			String sourceHostname, String target, String pingValue) {
		bot.sendRawLine("NOTICE " + sourceNick + " :\u0001PING " + pingValue + "\u0001");

	}

	public void onVersion(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		  bot.sendRawLine("NOTICE " + sourceNick + " :\u0001VERSION " + version + "\u0001");

	}

	public void onTime(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		bot.sendRawLine("NOTICE " + sourceNick + " :\u0001TIME " + new Date().toString() + "\u0001");

	}

}
