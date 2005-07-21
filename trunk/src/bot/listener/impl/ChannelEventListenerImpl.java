/**
 * 
 */
package bot.listener.impl;

import bot.listener.ChannelEventListener;

/**
 * the default implementation for the ChannelEventListenerInterface
 * consists only of empty method stubs, which may be overridden if necessary
 * @author Ulrich Krömer
 * 
 */
public class ChannelEventListenerImpl extends BotListenerImpl implements ChannelEventListener {

	/**
	 * default implementation does nothing.
	 */
	public void onJoin(String channel, String sender, String login,
			String hostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onKick(String channel, String kickerNick, String kickerLogin,
			String kickerHostname, String recipientNick, String reason) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onPart(String channel, String sender, String login,
			String hostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onQuit(String sourceNick, String sourceLogin,
			String sourceHostname, String reason) {
		// nothing to be done, override in derived classes if necessary
	}
}
