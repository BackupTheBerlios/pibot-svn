package bot.listener.impl;

import bot.listener.ConnectionListener;

/**
 * Provides the default implementation for the connection listener implementation.
 * this default implementation consists only of empty method stubs except only by
 * the onServerPing method which by default correctly responds to the server ping
 * with the according pong response.
 * @author Ulrich Krömer
 *
 */
public class ConnectionListenerImpl extends BotListenerImpl implements ConnectionListener {

	/**
	 * default implementation does nothing.
	 */
	public void onConnect() {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onDisconnect() {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation responds to the server ping correctly so that the bot
	 * does not time out.
	 */
	public void onServerPing(String response) {
		bot.sendRawLine("PONG " + response);
	}

	/**
	 * default implementation does nothing.
	 */
	public void onServerResponse(int code, String response) {
		// nothing to be done, override in derived classes if necessary
	}

}
