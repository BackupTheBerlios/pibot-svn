package bot.listener.impl;

import java.util.Date;

import bot.listener.UserAgentRequestListener;

/**
 * this is the default implementation of the UserAgentRequestListener interface which
 * implments default behaviour for the request that one can call on a user. it returns
 * the value of the version member in the OnVersion method and the value of the finger
 * member in the OnFinger method (to the requesting user).
 * it also responds to a ping request with a ping response and to a time request with the current time.
 * @author Ulrich Krömer
 *
 */
public class UserAgentRequestListenerImpl extends BotListenerImpl implements UserAgentRequestListener {

	/**
	 * the version response of the bot.
	 * this version does not have to be equal to the program version.
	 */
	protected String version;
	
	/**
	 * the finger response of the bot.
	 */
	protected String finger;
	
	/**
	 * Default Constructor.
	 * calls the other Constructor with default values.
	 */
	public UserAgentRequestListenerImpl() {
		this("0.0", "");
	}
	
	/**
	 * Constructor. initializes the member variables with the given variables.
	 * @param version the version that should be returned on a version request
	 * @param finger the finger response that should be returned on a finger request
	 */
	public UserAgentRequestListenerImpl(String version, String finger) {
		this.version = version;
		this.finger = finger;
	}
	
	/**
	 * default implementation sends back the finger response.
	 */
	public void onFinger(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		bot.sendRawLine("NOTICE " + sourceNick + " :\u0001FINGER " + finger + "\u0001");

	}

	/**
	 * default implementation responds to the ping accordingly.
	 */
	public void onPing(String sourceNick, String sourceLogin,
			String sourceHostname, String target, String pingValue) {
		bot.sendRawLine("NOTICE " + sourceNick + " :\u0001PING " + pingValue + "\u0001");

	}

	/**
	 * default implementation sends back the currently set version.
	 */
	public void onVersion(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		  bot.sendRawLine("NOTICE " + sourceNick + " :\u0001VERSION " + version + "\u0001");

	}

	/**
	 * default implementation sends back the current time.
	 */
	public void onTime(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		bot.sendRawLine("NOTICE " + sourceNick + " :\u0001TIME " + new Date().toString() + "\u0001");
	}

}
