package bot;

/**
 * implements a configuration for a bot.
 * supplies a default configuration
 * @author Ulrich Krömer
 *
 */
public class BotConfiguration {

	private String configFile;
	
	private String channel;
	private String ircServer;
	private String ircNick;
	private String version;
	private String ircName;
	private boolean verbose;
	private String featureDirectory;
	
	public BotConfiguration() {
		channel = "#hgb.test";
		ircServer = "linz.irc.at";
		ircNick = "Pi-Bot";
		version = "3.1415";
		ircName = "PiBot";
		featureDirectory = "feature";
		verbose = true;
	}
	
	public void SetConfigFile(String path) {
		
	}
	
	public String GetConfigFile() {
		return configFile;
	}
	
	public void Read() {
	}
	
	public void Write() {
		
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIrcName() {
		return ircName;
	}

	public void setIrcName(String ircName) {
		this.ircName = ircName;
	}

	public String getIrcNick() {
		return ircNick;
	}

	public void setIrcNick(String ircNick) {
		this.ircNick = ircNick;
	}

	public String getIrcServer() {
		return ircServer;
	}

	public void setIrcServer(String ircServer) {
		this.ircServer = ircServer;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public String getFeatureDirectory() {
		return featureDirectory;
	}	
}
