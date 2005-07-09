package bot.listener.impl;

import java.util.ArrayList;
import java.util.List;

import bot.EventNotificationBot;
import bot.listener.BotListener;

public class BotListenerImpl implements BotListener {

	protected EventNotificationBot bot;
	protected List<Class<? extends BotListener>> dependencies = new ArrayList<Class<? extends BotListener>>();
	
	public void setBot(EventNotificationBot bot) {
		this.bot = bot;
	}

	public List<Class<? extends BotListener>> getDependencies() {
		return dependencies;
	}

}
