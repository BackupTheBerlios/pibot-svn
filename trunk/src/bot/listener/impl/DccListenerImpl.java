package bot.listener.impl;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;

import bot.listener.DccListener;

/**
 * default implementation for the DccListener interface.
 * provides empty method subs for each method that may be overridden if necessary.
 * @author Ulrich Krömer
 *
 */
public class DccListenerImpl extends BotListenerImpl implements DccListener {

	/**
	 * default implementation does nothing.
	 */
	public void onFileTransferFinsihed(DccFileTransfer transfer, Exception e) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onIncomingChatRequest(DccChat chat) {
		// nothing to be done, override in derived classes if necessary
	}

	/**
	 * default implementation does nothing.
	 */
	public void onIncomingFileTransfer(DccFileTransfer transfer) {
		// nothing to be done, override in derived classes if necessary
	}

}
