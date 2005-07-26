package feature;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import bot.listener.MessageListener;
import bot.listener.impl.MessageListenerImpl;
import bot.logging.Logging;
import feature.util.common.ByteStreamSerializer;
import feature.util.common.Serializer;
import feature.util.tell.TellMessage;

/**
 * implements a simle TellFeature which enables users to tell other users which are
 * currently not availible messages throw messages which are saved by the bot. the bot
 * saves those messages and if the user issues some action again or sends a message
 * to the channel all saved messages are sent to him per notice. if a user wants to tell
 * another user a message he has to send a message in the format:
 * "!tell userName this is the message" 
 * @author Ulrich Krömer
 *
 */
public class TellFeature extends MessageListenerImpl implements MessageListener {

	private final Logger logger = Logging.getFeatureLogger();
	private static final String FILE_NAME = "tell.sav";
	
	private Serializer serializer = new ByteStreamSerializer();
	private Map<String, List<TellMessage>> messages = new HashMap<String, List<TellMessage>>();

	/**
	 * looks up if one or more messages have been saved for the currently sender and sends him
	 * those messages per notice if they exist.
	 * afterwards it tests if the message starts with "!tell" which would mean that the
	 * message should be saved for the specified user, a tell message looks like
	 * "!tell userName this is the message"
	 * for saving the message the saveMessage method is called.
	 */
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(messages.containsKey(sender)) {
			for (TellMessage tellMessage : messages.get(sender)) {
				bot.sendNotice(sender, buildMessage(tellMessage));
			}
		}
		if(message.startsWith("!tell ")) {
			saveMessage(sender, message.replaceAll("!tell ", ""));
			bot.sendNotice(sender, "message saved");
		}		
	}
	
	@Override
	public void onAction(String sender, String login, String hostname, String target, String action) {
		if(messages.containsKey(sender)) {
			for(TellMessage tellMessage : messages.get(sender)) {
				bot.sendNotice(sender, buildMessage(tellMessage));
			}
		}
	}
	
	
	/**
	 * saves the currently saved messages to a file.
	 */
	public void onUnload() {
		if(!serializer.serialize(messages, FILE_NAME)) {
			logger.warning("messages could not be saved");
		}
	}
	
	/**
	 * reads the saved messages from a file.
	 */
	public void onLoad() {
		Object obj = serializer.deserialize(FILE_NAME);
		if(obj == null) {
			logger.warning("messages could not be loaded");
		}
		else {
			messages = (Map<String, List<TellMessage>>)obj;
		}
	}

	/**
	 * builds the message that should be sent to a user for
	 * whom the message was saved.
	 * @param message the message to send to the user
	 * @return the String that is ready to be sent to the user
	 */
	private String buildMessage(TellMessage message) {
		StringBuffer response = new StringBuffer(message.getSender());
		response.append(" has left you the following message on ");	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		response.append(sdf.format(message.getTime()));
		response.append(" : ");
		response.append(message.getMessage());
		
		return response.toString();
	}

	/**
	 * saves the message to be looked up later.
	 * @param sender the sender of the message
	 * @param message the string that represents the message
	 */
	private void saveMessage(String sender, String message) {
		int endName = message.indexOf(' ');
		String name = message.substring(0, endName);
		
		TellMessage tellMessage = new TellMessage(sender, message.replaceAll(name, ""));
		if(!messages.containsKey(name)) {
			List<TellMessage> messageList = new ArrayList<TellMessage>();
			messageList.add(tellMessage);
			
			messages.put(name, messageList);	
		}
		else {
			messages.get(name).add(tellMessage);
		}
	}





	

	
}
