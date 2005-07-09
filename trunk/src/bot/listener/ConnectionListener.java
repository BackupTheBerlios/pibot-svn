package bot.listener;

public interface ConnectionListener extends BotListener {

	public void onConnect();

	public void onDisconnect();

	public void onServerPing(String response);

	public void onServerResponse(int code, String response);

}
