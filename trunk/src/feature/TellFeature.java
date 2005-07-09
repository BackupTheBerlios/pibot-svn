package feature;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.listener.MessageListener;
import bot.listener.impl.MessageListenerImpl;
import feature.util.tell.TellMessage;

public class TellFeature extends MessageListenerImpl implements MessageListener {

	private Map<String, List<TellMessage>> messages = new HashMap<String, List<TellMessage>>();

	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(messages.containsKey(sender)) {
			for (TellMessage tellMessage : messages.get(sender)) {
				bot.sendNotice(sender, sendMessage(tellMessage));
			}
		}
		if(message.startsWith("!tell ")) {
			saveMessage(sender, message.replaceAll("!tell ", ""));
			bot.sendNotice(sender, "message saved");
		}		
	}

	private String sendMessage(TellMessage message) {
		StringBuffer response = new StringBuffer(message.getSender());
		response.append(" has left you the following message on ");	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		response.append(sdf.format(message.getTime()));
		response.append(" : ");
		response.append(message.getMessage());
		
		return response.toString();
	}

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
