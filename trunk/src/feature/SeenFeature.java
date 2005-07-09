package feature;

import java.util.HashMap;
import java.util.Map;

import feature.util.seen.SeenMessage;

import bot.listener.ChannelEventListener;
import bot.listener.MessageListener;
import bot.listener.UserModeListener;
import bot.listener.impl.MessageListenerImpl;

public class SeenFeature extends MessageListenerImpl implements
		MessageListener, UserModeListener, ChannelEventListener {

	private static final String DEOPEVENT = " setting mode -o on ";
	private static final String DEVOICEEVENT = " setting mode -v on ";
	private static final String OPEVENT = " setting mode +o on ";
	private static final String VOICEEVENT = " setting mode +v on ";
	private static final String JOINEVENT = " joining ";
	private static final String KICKEVENT = " kicking ";
	private static final String PARTEVENT = " parting with reason ";
	private static final String NICKCHANGEEVENT = " changing nick to ";
	private static final String QUITEVENT = " quitting with reason ";
	private static final String ACTIONEVENT = " doing the following ";
	private static final String MESSAGEEVENT = " saying ";
	
	private Map<String, SeenMessage> issuedEvents = new HashMap<String, SeenMessage>();
	
	
	public void addEvent(String sender, String message) {
		issuedEvents.put(sender, new SeenMessage(message));
	}
	
	public void onDeop(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		
		addEvent(sourceNick, DEOPEVENT + recipient);
	}

	public void onDeVoice(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		
		addEvent(sourceNick, DEVOICEEVENT + recipient);
	}

	public void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		
		addEvent(sourceNick, OPEVENT + recipient);
	}

	public void onVoice(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		
		addEvent(sourceNick, VOICEEVENT + recipient);
	}

	public void onJoin(String channel, String sender, String login,
			String hostname) {
		
		addEvent(sender, JOINEVENT + channel);
	}

	public void onKick(String channel, String kickerNick, String kickerLogin,
			String kickerHostname, String recipientNick, String reason) {
		
		addEvent(kickerNick, KICKEVENT + recipientNick);
	}

	public void onPart(String channel, String sender, String login,
			String hostname) {
		
		addEvent(sender, PARTEVENT + channel);
	}

	public void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		
		addEvent(oldNick, NICKCHANGEEVENT + newNick);
	}

	public void onQuit(String sourceNick, String sourceLogin,
			String sourceHostname, String reason) {
		
		addEvent(sourceNick, QUITEVENT + reason);
	}

	@Override
	public void onAction(String sender, String login, String hostname, String target, String action) {

		addEvent(sender, ACTIONEVENT + action);
	}

	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		addEvent(sender, MESSAGEEVENT + message);
		
		if(message.startsWith("!seen ")) {
			String seenNick = message.replaceAll("!seen ", "");
			if(seenNick.equals(sender)) {
				bot.sendMessage(channel, "looking for yourself, eh?");
			}
			else if(issuedEvents.containsKey(seenNick)) {
				bot.sendMessage(channel, "i last saw " + seenNick + issuedEvents.get(seenNick).toString());
			}
			else {
				bot.sendMessage(channel, "i have no record of " + seenNick);
			}
		}
	}

}
