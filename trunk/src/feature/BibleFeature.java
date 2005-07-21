package feature;

import java.io.IOException;

import feature.util.bible.WordReader;
import feature.util.common.MessageFeature;

import bot.listener.MessageListener;

public class BibleFeature extends MessageFeature implements MessageListener {

	private String fileName;

	public BibleFeature() {
		this("res/nt.txt");
	}

	public BibleFeature(String fileName) {
		this.fileName = fileName;
	}

	@Override
	protected MessageType getType() {
		return MessageType.publicMessage;
	}

	@Override
	public String getTrigger() {
		return "!bible";
	}

	@Override
	public void execute(String sender, String messageArgs) {
		String w = "";
		try {
			WordReader wr = new WordReader(fileName);
			while (w != null && !w.startsWith(messageArgs))
				w = wr.readWord();
			wr.close();
			wr = null;
			if (w != null)
				bot.sendMessage(sender, w.replaceAll(messageArgs, ""));
		}// try
		catch (IOException e) {
			// do error handling
		} // try/catch
	}
}
