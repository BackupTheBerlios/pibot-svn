package feature.util.common;

import bot.listener.impl.MessageListenerImpl;

/**
 * implements a basic message feature which reacts on a trigger.
 * such a basic feature exists because many features take actions on a single
 * triggering message. for those features this basic implementation is meant
 * to provide an easy and straightforward implementation as the derived class
 * only has to specify on which messages it wants to react, on which trigger
 * it wants to react and what action should be taken if the trigger occurs.
 * this trigger can either be sent by public or private message depending
 * on the type of the MessageFeature, if the trigger is found the execute
 * method is called with the sender and the rest of the sent message as
 * argument. the MessageFeature uses the template pattern to define the
 * behaviour of implementing subclasses which have to override the execute
 * and the getTrigger method.
 * @author Ulrich Krömer
 *
 */
public abstract class MessageFeature extends MessageListenerImpl {

	protected enum MessageType {
		publicMessage,
		privateMessage,
		all
	}
	
	@Override
	public final void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(getType() == MessageType.publicMessage || getType() == MessageType.all) {
			if(message.startsWith(getTrigger())) {
				execute(channel, message.replaceAll(getTrigger() + " ", ""));
			}
		}
	}

	@Override
	public final void onPrivateMessage(String sender, String login, String hostname, String message) {
		if(getType() == MessageType.privateMessage|| getType() == MessageType.all) {
			if(message.startsWith(getTrigger())) {
				execute(sender, message.replaceAll(getTrigger() + " ", ""));
			}
		}
	}

	/**
	 * returns the type of the message feature. this can either be public private
	 * or all. has to be overridden by implementing methods.
	 * @return the type of messages the feature responds to
	 */
	protected abstract MessageType getType();
	
	/**
	 * returns the trigger on which the feature reacts. typically such a trigger
	 * starts witch !. has to be overridden by implementing methods
	 * @return the trigger for the implementing feature
	 */
	public abstract String getTrigger();
	
	/**
	 * defines the action that should be taken when the trigger occurs. in this 
	 * method the actual action of the feature is done. 
	 * @param sender the sender of the triggering message
	 * @param messageArgs the rest of the trigger message (the trigger has been stripped)
	 */
	public abstract void execute(String sender, String messageArgs);
}
