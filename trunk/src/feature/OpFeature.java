package feature;

import java.util.ArrayList;
import java.util.List;

import bot.listener.ChannelEventListener;
import bot.listener.impl.ChannelEventListenerImpl;
import bot.util.User;

public class OpFeature extends ChannelEventListenerImpl implements ChannelEventListener {

	private List<User> ops = new ArrayList<User>(); 
	private List<User> masters = new ArrayList<User>();
	private List<User> knownUser = new ArrayList<User>();
	
	
	public void onJoin(String channel, String sender, String login, String hostname) {
		User curUser = new User(sender, hostname, login);
		if(ops.contains(curUser)) {
			bot.op(channel, sender);
		}
	}


	public List<User> getKnownUser() {
		return knownUser;
	}

	public List<User> getMasters() {
		return masters;
	}


	public List<User> getOps() {
		return ops;
	}
	
	public void addOp(User user) {
		ops.add(user);
	}
	
	public void addMaster(User user) {
		masters.add(user);
	}
	
}
