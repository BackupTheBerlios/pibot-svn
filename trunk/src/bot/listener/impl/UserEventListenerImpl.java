package bot.listener.impl;

import java.util.List;

import bot.listener.UserEventListener;
import bot.util.User;

public class UserEventListenerImpl extends BotListenerImpl implements UserEventListener {

	public void onInvite(String targetNick, String sourceNick,
			String sourceLogin, String sourceHostname, String channel) {
		// TODO Auto-generated method stub

	}

	public void onUserList(String channel, List<User> name) {
		// TODO Auto-generated method stub

	}

	public void onChannelInfo(String channel, int userCount, String topic) {
		// TODO Auto-generated method stub

	}

	public void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
		// TODO Auto-generated method stub
		
	}
	
}
