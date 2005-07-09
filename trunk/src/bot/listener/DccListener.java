package bot.listener;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;

public interface DccListener extends BotListener {

	public void onFileTransferFinsihed(DccFileTransfer transfer, Exception e);

	public void onIncomingChatRequest(DccChat chat);

	public void onIncomingFileTransfer(DccFileTransfer transfer);

}
