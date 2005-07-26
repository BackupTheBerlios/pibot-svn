package feature;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import bot.listener.MessageListener;
import bot.listener.impl.MessageListenerImpl;
import bot.logging.Logging;
import feature.util.common.ByteStreamSerializer;
import feature.util.common.Serializer;
import feature.util.common.timer.Timer;
import feature.util.common.timer.TimerEvent;
import feature.util.common.timer.TimerListener;
import feature.util.event.Event;

public class EventFeature extends MessageListenerImpl implements MessageListener, TimerListener {

	protected enum Participation { YES, NO, MAYBE };
	
	private static final String FILE_NAME = "events.sav";
	private static final int INVALID = Integer.MIN_VALUE;
	private final Logger logger = Logging.getFeatureLogger();
	
	private Timer timer = new Timer();
	private List<Event> events = new ArrayList<Event>(); 
	private Serializer serializer = new ByteStreamSerializer();
	
	public void onUnload() {
		if(!serializer.serialize(events, FILE_NAME)) {
			logger.warning("events could not be saved");	
		}
	}
	
	public void onLoad() {
		Object obj = serializer.deserialize(FILE_NAME);
		if(obj == null) {
			logger.warning("events could not be loaded");
		}
		else {
			events= (List<Event>)obj;
		}
		
		// start thread to wait for events to come up
		timer.setNoTicks(Integer.MAX_VALUE);
		timer.setInterval(1000 * 60);
		timer.start();
	}
	
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(message.equals("!le") || message.equals("!listevents")) {	
			int i = 0;
			if(events.size() == 0) 
				bot.sendNotice(sender, "no events availible");
			for(Event event : events) {
				StringBuffer retMsg = new StringBuffer();
				retMsg.append("[");
				retMsg.append(i);
				retMsg.append("] ");
				retMsg.append(event);
				retMsg.append("\n");
				++i;
				bot.sendNotice(sender, retMsg.toString());
			}
		}
		else if(message.startsWith("!addme")) {
			int eventId = getEventNumber(message.replaceAll("!addme ", ""));
			if(eventId == INVALID || events.size() <= eventId)
				bot.sendNotice(sender, "invalid id specified");
			else
				events.get(eventId).addUserYes(sender);
		}
		else if(message.startsWith("!addmaybe")) {
			int eventId = getEventNumber(message.replaceAll("!addmaybe ", ""));
			if(eventId == INVALID || events.size() <= eventId)
				bot.sendNotice(sender, "invalid id specified");
			else
				events.get(eventId).addUserMaybe(sender);
		}
		else if(message.startsWith("!addnot")) {
			int eventId = getEventNumber(message.replaceAll("!addnot ", ""));
			if(eventId == INVALID || events.size() <= eventId)
				bot.sendNotice(sender, "invalid id specified");
			else {
				events.get(eventId).addUserNo(sender);
			}
		}
	}
	
	
	private int getEventNumber(String number) {
		try {
			return Integer.parseInt(number);
		}
		catch(NumberFormatException ex) {
			return INVALID;
		}
	}

	public void expired(TimerEvent e) {
		for(Event event : events) {
			if(event.getDate().before(new Date())) {
				// TODO: notify of upcoming event
			}
		}
		
	}
}
