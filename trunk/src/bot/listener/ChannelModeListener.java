package bot.listener;

public interface ChannelModeListener extends BotListener {

	public void onRemoveModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onRemoveNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onRemovePrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onRemoveSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onRemoveTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onSetChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key);

	public void onSetChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname, int limit);

	public void onSetInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onSetModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onSetNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onSetPrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onSetSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onSetTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onRemoveChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key);

	public void onRemoveChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onRemoveInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname);

	public void onRemoveChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask);

	public void onSetChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask);

}
