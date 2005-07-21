package feature.util.common;

import bot.listener.impl.MessageListenerImpl;

/**
 * implements a basic message feature which
 * @author y2uk
 *
 */
public abstract class MessageFeature extends MessageListenerImpl {

	protected enum MessageType {
		publicMessage,
		privateMessage,
		all
	}
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(getType() == MessageType.publicMessage || getType() == MessageType.all) {
			if(message.startsWith(getTrigger())) {
				execute(channel, message.replaceAll(getTrigger() + " ", ""));
			}
		}
	}

	@Override
	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		if(getType() == MessageType.privateMessage|| getType() == MessageType.all) {
			if(message.startsWith(getTrigger())) {
				execute(sender, message.replaceAll(getTrigger() + " ", ""));
			}
		}
	}

	protected abstract MessageType getType();
	
	public abstract String getTrigger();
	
	public abstract void execute(String sender, String messageArgs);
}
