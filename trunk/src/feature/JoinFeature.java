package feature;

import feature.util.common.MessageFeature;
import bot.listener.MessageListener;


public class JoinFeature extends MessageFeature implements MessageListener {

	public void execute(String sender, String messageArgs) {
		bot.joinChannel(messageArgs);
	}

	public String getTrigger() {
		return "!join";
	}
	
	protected MessageType getType() {
		return MessageType.privateMessage;
	}
}
