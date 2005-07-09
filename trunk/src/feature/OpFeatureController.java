package feature;

import bot.listener.MessageListener;

public class OpFeatureController extends MessageFeature implements
		MessageListener {

	private OpFeature opFeature;
	
	public OpFeatureController() {
		dependencies.add(OpFeature.class);
	}
	
	public void setOpFeature(OpFeature opFeature) {
		this.opFeature = opFeature;
	}
	
	@Override
	public MessageType getType() {
		return MessageType.privateMessage;
	}

	@Override
	public String getTrigger() {
		return "!op";		
	}

	@Override
	public void execute(String sender, String messageArgs) {
		

	}

}
