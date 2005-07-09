package bot.listener.impl;

import bot.listener.ConnectionListener;

public class ConnectionListenerImpl extends BotListenerImpl implements ConnectionListener {

	public void onConnect() {
		// TODO Auto-generated method stub

	}

	public void onDisconnect() {
		// TODO Auto-generated method stub

	}

	public void onServerPing(String response) {
		bot.sendRawLine("PONG " + response);
	}

	public void onServerResponse(int code, String response) {
		// TODO Auto-generated method stub

	}

}
