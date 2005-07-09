/**
 * 
 */
package bot.listener.impl;

import bot.listener.ChannelEventListener;

/**
 * @author y2uk
 *
 */
public class ChannelEventListenerImpl extends BotListenerImpl implements ChannelEventListener {

	/* (non-Javadoc)
	 * @see bot.listener.ChannelEventListener#onJoin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void onJoin(String channel, String sender, String login,
			String hostname) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bot.listener.ChannelEventListener#onKick(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void onKick(String channel, String kickerNick, String kickerLogin,
			String kickerHostname, String recipientNick, String reason) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bot.listener.ChannelEventListener#onPart(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void onPart(String channel, String sender, String login,
			String hostname) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bot.listener.ChannelEventListener#onNickChange(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bot.listener.ChannelEventListener#onQuit(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void onQuit(String sourceNick, String sourceLogin,
			String sourceHostname, String reason) {
		// TODO Auto-generated method stub

	}
}
