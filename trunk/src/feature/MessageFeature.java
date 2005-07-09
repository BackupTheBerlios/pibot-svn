package feature;

import bot.listener.impl.MessageListenerImpl;

public abstract class MessageFeature extends MessageListenerImpl {

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

	public abstract MessageType getType();
	
	public abstract String getTrigger();
	
	public abstract void execute(String sender, String messageArgs);
}
