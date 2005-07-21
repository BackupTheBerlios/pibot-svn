package bot.listener.impl;

import java.util.List;

import bot.listener.UserEventListener;
import bot.util.User;

/**
 * default implementation for the UserEventListener interface.
 * by default all methods are empty. this methods may be overridden by subclasses 
 * if necessary.
 * @author Ulrich Krömer
 *
 */
public class UserEventListenerImpl extends BotListenerImpl implements UserEventListener {

	/**
	 * default implementation does nothing.
	 */
	public void onInvite(String targetNick, String sourceNick,
			String sourceLogin, String sourceHostname, String channel) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onUserList(String channel, List<User> name) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onChannelInfo(String channel, int userCount, String topic) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
		// nothing to be done, override in derived classes if necessary
	}	
}
