package bot.listener;

public interface UserAgentRequestListener extends BotListener {

	public void onFinger(String sourceNick, String sourceLogin, String sourceHostname, String target);

	public void onPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue);

	public void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target);

	public void onTime(String sourceNick, String sourceLogin, String sourceHostname, String target);

}
