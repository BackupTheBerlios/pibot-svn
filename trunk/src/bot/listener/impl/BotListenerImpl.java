package bot.listener.impl;

import java.util.ArrayList;
import java.util.List;

import bot.EventNotificationBot;
import bot.listener.BotListener;

/**
 * provides the default implementation for the bot listener interface.
 * this implementation consists of the empty methods OnLoad and OnUnload, which
 * may be overridden if setup or teardown actions are necessary as well as the setBot
 * method that sets the bot associated with the listener and the getDependencies method
 * which by default returns an empty list with no dependencies
 * @author Ulrich Krömer
 *
 */
public class BotListenerImpl implements BotListener {

	/**
	 * the bot object on which the user can call actions.
	 */
	protected EventNotificationBot bot;
	
	/**
	 * the list of dependencies to which the user may add entries.
	 */
	protected List<Class<? extends BotListener>> dependencies = new ArrayList<Class<? extends BotListener>>();
	
	/**
	 * associates the provided bot with the protected bot member
	 */
	public void setBot(EventNotificationBot bot) {
		this.bot = bot;
	}

	/**
	 * by default returns an empty list (because not dependencies exist)
	 */
	public List<Class<? extends BotListener>> getDependencies() {
		return dependencies;
	}

	/**
	 * default implementation does nothing.
	 */
	public void onLoad() {
		// nothing to be done, override in derived classes if necessary
	}
	
	/**
	 * default implementation does nothing.
	 */
	public void onUnload() {
		// nothing to be done, override in derived classes if necessary
	}

}
