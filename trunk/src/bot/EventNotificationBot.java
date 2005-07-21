package bot;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;

import bot.listener.ChannelEventListener;
import bot.listener.ChannelModeListener;
import bot.listener.ConnectionListener;
import bot.listener.DccListener;
import bot.listener.MessageListener;
import bot.listener.UserAgentRequestListener;
import bot.listener.UserEventListener;
import bot.listener.UserModeListener;
import bot.util.IrcUtil;

/**
 * 
 * @author Ulrich Krömer
 *
 * This class extends the PircBot irc bot, which represents a fully functional irc bot.
 * This extending class overloads all event methods and enables features to register themselves
 * with the bot. The bot then notifies all registered features when an event occurs.
 * The features are loaded and registered dynamically by an object of type FeatureLoader, which
 * also resolves dependencies between features. To being able to register themselves the various
 * features have to implement one of the supported event interfaces (located in bot.listener), which
 * extend the BotListener base interface and implement event handling functions for some of the
 * provided functions.
 * if no listeners are registered for some common behaviour which every irc bot has to 
 * support (like responding to a server ping) the bot takes the necessary measures to
 * work properly. this works only if no listeners are registered for the specific request,
 * at the moment a listener registers its his responsibility to respond properly. the default
 * implementations of the interfaces fullfill that demand by responding in the same way as
 * the bot would do.
 * @author Ulrich Krömer
 * 
 */
public class EventNotificationBot extends PircBot {
	
	/**
	 * Constructor.
	 * Initializes the bot with the configuration data.
	 * Initializes the feature loader which then loads
	 * all features from the directory
	 * @param cfg
	 */
	public EventNotificationBot(BotConfiguration cfg) {
		
		this.setVerbose(cfg.isVerbose());
		this.setName(cfg.getIrcName());
		this.setVersion(cfg.getVersion());
		this.setAutoNickChange(true);
		this.configuration = cfg;
		
		featureLoader = new FeatureLoader(this, cfg.getFeatureDirectory());
		featureLoader.load();
	}
	
	private FeatureLoader featureLoader;
	
	/**
	 * returns the feature loader of the bot.
	 * maybe used to control the feature loader by registered features
	 * @return the feature loader of the bot
	 */
	public FeatureLoader getFeatureLoader() {
		return featureLoader;
	}
	
	private BotConfiguration configuration;
	
	/**
	 * returns the configuration of the bot.
	 * maybe used by registered listeners.
	 * @return the configuration of the bot
	 */
	public BotConfiguration getConfiguration() {
		return configuration;
	}
	
	/*
	 * The following methods provide functionality to add and remove a listener
	 * for a specific interface. When the FeatureLoader is instantiated it determines
	 * which interfaces are supported by the bot class by querying for this add methods
	 * the interface of those mehods has to look like "add" + MyListener(MyListener listener)
	 * and "remove" + ... 
	 */
	
	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}
	
	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}
	
	public void addConnectionListener(ConnectionListener listener) {
		connectionListeners.add(listener);
	}
	
	public void removeConnectionListener(ConnectionListener listener) {
		connectionListeners.remove(listener);
	}
	
	public void addUserModeListener(UserModeListener listener) {
		userModeListeners.add(listener);
	}
	
	public void removeUserModeListener(UserModeListener listener) {
		userModeListeners.remove(listener);
	}
	
	public void addChannelModeListener(ChannelModeListener listener) {
		channelModeListeners.add(listener);
	}
	
	public void removeChannelModeListener(ChannelModeListener listener) {
		channelModeListeners.remove(listener);
	}
	
	public void addUserEventListener(UserEventListener listener) {
		userEventListeners.add(listener);
	}
	
	public void removeUserEventListener(UserEventListener listener) {
		userEventListeners.remove(listener);
	}
	
	public void addChannelEventListener(ChannelEventListener listener) {
		channelEventListeners.add(listener);
	}
	
	public void removeChannelEventListener(ChannelEventListener listener) {
		channelEventListeners.remove(listener);
	}
	
	public void addDccListener(DccListener listener) {
		dccListeners.add(listener);
	}
	
	public void removeDccListener(DccListener listener) {
		dccListeners.remove(listener);
	}
	
	public void addUserAgentRequestListener(UserAgentRequestListener listener) {
		userAgentRequestListeners.add(listener);
	}
	
	public void removeUserAgentRequestListener(UserAgentRequestListener listener) {
		userAgentRequestListeners.remove(listener);
	}
	
	/*
	 * following are the sets of listeners the are holding the currently registered listeners
	 */
	
	private Set<MessageListener> messageListeners = new HashSet<MessageListener>();
	private Set<ConnectionListener> connectionListeners = new HashSet<ConnectionListener>();
	
	private Set<UserModeListener> userModeListeners = new HashSet<UserModeListener>();
	private Set<ChannelModeListener> channelModeListeners = new HashSet<ChannelModeListener>();
	
	private Set<UserEventListener> userEventListeners = new HashSet<UserEventListener>();
	private Set<ChannelEventListener> channelEventListeners = new HashSet<ChannelEventListener>();

	private Set<DccListener> dccListeners = new HashSet<DccListener>();
	private Set<UserAgentRequestListener> userAgentRequestListeners = new HashSet<UserAgentRequestListener>();
	
	/*
	 * the following methods override methods from the bot base class. they forward each event for which an
	 * on methode exists to all registered clients which may then act accordingly
	 * 
	 */
	
	@Override
	protected void onAction(String sender, String login, String hostname, String target, String action) {
		for (MessageListener messageListener : messageListeners) {
			messageListener.onAction(sender, login, hostname, target, action);
		}
	}

	@Override
	protected void onChannelInfo(String channel, int userCount, String topic) {
		for (UserEventListener userEventListener : userEventListeners) {
			userEventListener.onChannelInfo(channel, userCount, topic);
		}
	}

	@Override
	protected void onConnect() {
		for (ConnectionListener connectionListener : connectionListeners) {
			connectionListener.onConnect();
		}
	}

	@Override
	protected void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		for (UserModeListener userModeListener : userModeListeners) {
			userModeListener.onDeop(channel, sourceNick, sourceLogin, sourceHostname, recipient);
		}
	}

	@Override
	protected void onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		for (UserModeListener userModeListener : userModeListeners) {
			userModeListener.onDeVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
		}
	}

	@Override
	protected void onDisconnect() {
		for (ConnectionListener connectionListener : connectionListeners) {
			connectionListener.onDisconnect();
		}
	}

	@Override
	protected void onFileTransferFinished(DccFileTransfer transfer, Exception e) {
		for(Iterator it = dccListeners.iterator(); it.hasNext();) {
			((DccListener)it.next()).onFileTransferFinsihed(transfer, e);
		}
	}

	@Override
	protected void onFinger(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		if(userAgentRequestListeners.size() == 0)
			super.onFinger(sourceNick, sourceLogin, sourceHostname, target);
		for (UserAgentRequestListener userAgentRequestListener : userAgentRequestListeners) {
			userAgentRequestListener.onFinger(sourceNick, sourceLogin, sourceHostname, target);
		}
	}

	@Override
	protected void onIncomingChatRequest(DccChat chat) {
		for (DccListener dccListener : dccListeners) {
			dccListener.onIncomingChatRequest(chat);
		}
	}

	@Override
	protected void onIncomingFileTransfer(DccFileTransfer transfer) {
		for (DccListener dccListener : dccListeners) {
			dccListener.onIncomingFileTransfer(transfer);
		}
	}

	@Override
	protected void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) {
		for (UserEventListener userEventListener : userEventListeners) {
			userEventListener.onInvite(targetNick, sourceNick, sourceLogin, sourceHostname, channel);
		}
	}

	@Override
	protected void onJoin(String channel, String sender, String login, String hostname) {
		for (ChannelEventListener channelEventListener : channelEventListeners) {
			channelEventListener.onJoin(channel, sender, login, hostname);
		}
	}

	@Override
	protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		for (ChannelEventListener channelEventListener : channelEventListeners) {
			channelEventListener.onKick(channel, kickerNick, kickerLogin, kickerHostname, recipientNick, reason);
		}
	}

	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		for (MessageListener messageListener : messageListeners) {
			messageListener.onMessage(channel, sender, login, hostname, message);
		}
	}

	@Override
	protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
		for (ChannelEventListener channelEventListener : channelEventListeners) {
			channelEventListener.onNickChange(oldNick, login, hostname, newNick);
		}
	}

	@Override
	protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
		for (MessageListener messageListener : messageListeners) {
			messageListener.onNotice(sourceNick, sourceLogin, sourceHostname, notice);
		}
	}

	@Override
	protected void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		for (UserModeListener userModeListener : userModeListeners) {
			userModeListener.onOp(channel, sourceNick, sourceLogin, sourceHostname, recipient);
		}
	}

	@Override
	protected void onPart(String channel, String sender, String login, String hostname) {
		for (ChannelEventListener channelEventListener : channelEventListeners) {
			channelEventListener.onPart(channel, sender, login, hostname);
		}
	}

	@Override
	protected void onPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue) {
		if(userAgentRequestListeners.size() == 0)
			super.onPing(sourceNick, sourceLogin, sourceHostname, target, pingValue);
		for (UserAgentRequestListener userAgentRequestListener : userAgentRequestListeners) {
			userAgentRequestListener.onPing(sourceNick, sourceLogin, sourceHostname, target, pingValue);
		}
	}

	@Override
	protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		for (MessageListener messageListener : messageListeners) {
			messageListener.onPrivateMessage(sender, login, hostname, message);
		}
	}

	@Override
	protected void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {
		for (ChannelEventListener channelEventListener : channelEventListeners) {
			channelEventListener.onQuit(sourceNick, sourceLogin, sourceHostname, reason);
		}
	}

	@Override
	protected void onRemoveChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask) {
		for(Iterator it = channelModeListeners.iterator(); it.hasNext();) {
			((ChannelModeListener)it.next()).onRemoveChannelBan(channel, sourceNick, sourceLogin, sourceHostname, hostmask);
		}
	}

	@Override
	protected void onRemoveChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onRemoveChannelKey(channel, sourceNick, sourceLogin, sourceHostname, key);
		}
	}

	@Override
	protected void onRemoveChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onRemoveChannelLimit(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onRemoveInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onRemoveInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onRemoveModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onRemoveModerated(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onRemoveNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onRemoveNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
		};
	}

	@Override
	protected void onRemovePrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onRemovePrivate(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onRemoveSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onRemoveSecret(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onRemoveTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onRemoveTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onServerPing(String response) {
		if(connectionListeners.size() == 0)
			super.onServerPing(response);
		for (ConnectionListener connectionListener : connectionListeners) {
			connectionListener.onServerPing(response);
		}
	}

	@Override
	protected void onServerResponse(int code, String response) {
		for (ConnectionListener connectionListener : connectionListeners) {
			connectionListener.onServerResponse(code, response);
		}
	}

	@Override
	protected void onSetChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetChannelBan(channel, sourceNick, sourceLogin, sourceHostname, hostmask);
		}
	}

	@Override
	protected void onSetChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetChannelKey(channel, sourceNick, sourceLogin, sourceHostname, key);
		}
	}

	@Override
	protected void onSetChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname, int limit) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetChannelLimit(channel, sourceNick, sourceLogin, sourceHostname, limit);
		}
	}

	@Override
	protected void onSetInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onSetModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetModerated(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onSetNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onSetPrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetPrivate(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onSetSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetSecret(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onSetTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname) {
		for (ChannelModeListener channelModeListener : channelModeListeners) {
			channelModeListener.onSetTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
		}
	}

	@Override
	protected void onTime(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		if(userAgentRequestListeners.size() == 0)
			super.onTime(sourceNick, sourceLogin, sourceHostname, target);
		for (UserAgentRequestListener userAgentRequestListener : userAgentRequestListeners) {
			userAgentRequestListener.onTime(sourceNick, sourceLogin, sourceHostname, target);
		}
	}

	@Override
	protected void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
		for (UserEventListener userEventListener : userEventListeners) {
			userEventListener.onTopic(channel, topic, setBy, date, changed);
		}
	}

	@Override
	protected void onUserList(String channel, org.jibble.pircbot.User[] users) {
		for (UserEventListener userEventListener : userEventListeners) {
			userEventListener.onUserList(channel, IrcUtil.TransformUsers(users));
		}
	}

	@Override
	protected void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		if(userAgentRequestListeners.size() == 0)
			super.onVersion(sourceNick, sourceLogin, sourceHostname, target);
		for (UserAgentRequestListener userAgentRequestListener : userAgentRequestListeners) {
			userAgentRequestListener.onVersion(sourceNick, sourceLogin, sourceHostname, target);
		}
	}

	@Override
	protected void onVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		for (UserModeListener userModeListener : userModeListeners) {
			userModeListener.onVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
		}
	}
}
