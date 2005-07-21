package bot.listener.impl;

import bot.listener.UserModeListener;

/**
 * default implementation for the UserModeListener interface.
 * provides empty method stubs for each methods that may be overridden by subclasses
 * if necessary
 * @author Ulrich Krömer
 *
 */
public class UserModeListenerImpl extends BotListenerImpl implements UserModeListener {

	/**
	 * default implementation does nothing.
	 */
	public void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recpient) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onOp(String channel, String sourceNick, 
			String sourceLogin, String sourceHostname, String recipient) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onVoice(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		// nothing to be done, override in derived classes if necessary
	}

}
