package bot.listener;

public interface MessageListener extends BotListener{

	public void onAction(String sender, String login, String hostname, String target, String action);

	public void onMessage(String channel, String sender, String login, String hostname, String message);

	public void onPrivateMessage(String sender, String login, String hostname, String message);

	public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String notice);

}
