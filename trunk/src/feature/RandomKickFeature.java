package feature;

import java.util.List;
import java.util.Random;

import feature.util.common.MessageFeature;

import bot.listener.MessageListener;
import bot.util.IrcUtil;
import bot.util.User;
import bot.util.UserManager;

public class RandomKickFeature extends MessageFeature implements MessageListener {

	public void execute(String sender, String messagArgs) {
		boolean finished = false;
		Random r = new Random();
		int pos = 0;
		while (!finished) {
			List<User> users = IrcUtil.TransformUsers(bot.getUsers(bot.getConfiguration().getChannel())); 
			User user = users.get(r.nextInt(users.size()));
			if (!UserManager.getInstance().isMaster(user)) {
				bot.kick(user.getNick(), "requested by " + sender);
				finished = true;
			}// if
			if(pos == users.size()) 
				finished = true;	
			++pos;
		}// while
	}
	
	public String getTrigger() {
		return "!randomkick";
	}
	

	@Override
	protected MessageType getType() {
		return MessageType.publicMessage;
	}

}
