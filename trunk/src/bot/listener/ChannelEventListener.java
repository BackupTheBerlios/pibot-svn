package bot.listener;

public interface ChannelEventListener extends BotListener {

	public void onJoin(String channel, String sender, String login, String hostname);

	public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason);

	public void onPart(String channel, String sender, String login, String hostname);

	public void onNickChange(String oldNick, String login, String hostname, String newNick);

	public void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason);
}
