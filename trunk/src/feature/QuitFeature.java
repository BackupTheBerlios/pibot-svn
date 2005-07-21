package feature;

import feature.util.common.MessageFeature;
import bot.listener.MessageListener;


public class QuitFeature extends MessageFeature implements MessageListener {

	public void execute(String sender, String messageArgs) {
		bot.quitServer(messageArgs);
		System.exit(0);
	}

	public String getTrigger() {
		return "!quit";
	}

	protected MessageType getType() {
		return MessageType.privateMessage;
	}

}
