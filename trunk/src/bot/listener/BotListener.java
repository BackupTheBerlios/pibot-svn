package bot.listener;

import java.util.List;

import bot.EventNotificationBot;

/**
 * defines the common behaviour of a listener that may register itself with the
 * bot. each listener has to provide all of the following methods in order to
 * work properly. moreover as each class that registers itself with the bot has
 * to implement this interface it is guaranteed that does methods exist.
 * the default implementation for this interface is the BotListenerImpl class 
 * (for each listener interface there exists an according default implemantation with
 * Impl suffixed to the interface name)
 * @author Ulrich Krömer
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
	
	/**
	 * is called after the feature is registered and initialized to provide 
	 * the feature with the possibility to add some user defined initialization.
	 * this should not be done in the constructor as not every information needed
	 * may be accessible at that time.
	 */
	public void onLoad();
	
	/**
	 * this method is called before a feature is unloaded.
	 * this provides a feature with the possibilty to add some user defined behaviour
	 * (like saving the current status) before the feature is unloaded.
	 */
	public void onUnload();
}
