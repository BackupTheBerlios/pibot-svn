package bot.listener.impl;

import bot.listener.MessageListener;

/**
 * the default implementation for the MessageListener interface.
 * consists only of empty method stubs, which may be overridden if necessary.
 * @author Ulrich Krömer
 *
 */
public class MessageListenerImpl extends BotListenerImpl implements MessageListener {

	/**
	 * default implementation does nothing.
	 */
	public void onAction(String sender, String login, String hostname,
			String target, String action) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onNotice(String sourceNick, String sourceLogin,
			String sourceHostname, String notice) {
		// nothing to be done, override in derived classes if necessary
	}

}
