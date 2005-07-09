package bot.listener;

import java.util.List;

import bot.EventNotificationBot;

/**
 * 
 * @author y2uk
 *
 */
public interface BotListener {

	/**
	 * sets the bot which the listener needs to execute some actions.
	 * @param bot the bot on which the listener can execute actions
	 */
	public void setBot(EventNotificationBot bot);
	
	/**
	 * returns a list of classes on which the bot depends.
	 * those classes have to implement the bot listener interface because
	 * a feature can only depend on other features not on other classes.
	 * @return the list of dependencies for this bot listener.
	 */
	public List<Class<? extends BotListener>> getDependencies();
}
