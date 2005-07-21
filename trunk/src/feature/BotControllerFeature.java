package feature;

import feature.util.common.MessageFeature;
import bot.listener.MessageListener;

public class BotControllerFeature extends MessageFeature implements
		MessageListener {

	@Override
	protected MessageType getType() {
		return MessageType.privateMessage;
	}

	@Override
	public String getTrigger() {
		return "!features";
	}

	@Override
	public void execute(String sender, String messageArgs) {
		if(messageArgs.equals("reload")) {
			bot.getFeatureLoader().load();
			bot.sendMessage(sender, "features reloaded...");
		}
		else if(messageArgs.equals("list")) {
			StringBuffer features = new StringBuffer("currently loaded features: ");
			for (String featureName : bot.getFeatureLoader().getRegisteredFeatures()) {
				features.append(featureName);
				features.append(" ");
			}
			bot.sendMessage(sender, features.toString());
		}
	}
}
