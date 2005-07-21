package feature;

import feature.util.common.MessageFeature;
import bot.listener.MessageListener;


public class PartFeature extends MessageFeature implements MessageListener {

	public void execute(String sender, String messageArgs) {
		String reason;
		String[] args = messageArgs.split(" ");
		String channel = args[0];
		if(args.length > 1)
			reason = args[1];
		else
			reason = "leaving";
		bot.partChannel(channel, reason);
	}

	public String getTrigger() {
		return "!part";
	}

	@Override
	protected MessageType getType() {
		return MessageType.privateMessage;
	}

}
