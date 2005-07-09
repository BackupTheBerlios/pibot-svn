package feature;

import bot.listener.MessageListener;


public class JoinFeature extends MessageFeature implements MessageListener {

	public void execute(String sender, String messageArgs) {
		bot.joinChannel(messageArgs);
	}

	public String getTrigger() {
		return "!join";
	}
	
	public MessageType getType() {
		return MessageType.privateMessage;
	}
}
