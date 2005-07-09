package bot.listener;

public interface UserModeListener extends BotListener {

	public void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recpipient);

	public void onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient);

	public void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient);

	public void onVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient);

}
