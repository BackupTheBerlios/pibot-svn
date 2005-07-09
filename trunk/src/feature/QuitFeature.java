package feature;

import bot.listener.MessageListener;


public class QuitFeature extends MessageFeature implements MessageListener {

	public void execute(String sender, String messageArgs) {
		bot.quitServer(messageArgs);
		System.exit(0);
	}

	public String getTrigger() {
		return "!quit";
	}

	public MessageType getType() {
		return MessageType.privateMessage;
	}

}
