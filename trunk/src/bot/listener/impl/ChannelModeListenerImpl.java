package bot.listener.impl;

import bot.listener.ChannelModeListener;

/**
 * default implementation fo the ChannelModeListener interface.
 * provides empty method stubs for each method which may be overridden by subclasses
 * if necessary
 * @author Ulrich Krömer
 *
 */
public class ChannelModeListenerImpl extends BotListenerImpl implements ChannelModeListener {

	/**
	 * default implementation does nothing.
	 */
	public void onRemoveModerated(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onRemoveNoExternalMessages(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onRemovePrivate(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onRemoveSecret(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessar
	}

	/**
	 * default implementation does nothing.
	 */
	public void onRemoveTopicProtection(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetChannelKey(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String key) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetChannelLimit(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, int limit) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetInviteOnly(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetModerated(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetNoExternalMessages(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetPrivate(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetSecret(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetTopicProtection(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onRemoveChannelKey(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String key) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onRemoveChannelLimit(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onRemoveInviteOnly(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		/// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onRemoveChannelBan(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String hostmask) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onSetChannelBan(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String hostmask) {
		// nothing to be done, override in derived classes if necessary
	}

}
