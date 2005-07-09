package bot.listener;

import java.util.List;

import bot.util.User;

public interface UserEventListener extends BotListener {

	public void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel);

	public void onUserList(String channel, List<User> name);

	public void onChannelInfo(String channel, int userCount, String topic);
	
	public void onTopic(String channel, String topic, String setBy, long date, boolean changed);
}
