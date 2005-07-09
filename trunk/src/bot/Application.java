package bot;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class Application {

	public static void main(String[] args) {
		BotConfiguration cfg = new BotConfiguration();
		EventNotificationBot bot = new EventNotificationBot(cfg);
		try {
			bot.connect(cfg.getIrcServer());
		} catch (NickAlreadyInUseException e) {
			bot.changeNick(cfg.getIrcNick() + "_");
			System.out.println("Changed nick to: " + bot.getNick());
		} catch (IOException e) {
			System.out.println("Unable to connect to Server");
		} catch (IrcException e) {
			System.out.println("Connection refused");
		}
	}// main
}
