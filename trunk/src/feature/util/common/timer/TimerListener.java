
package feature.util.common.timer;

import java.util.EventListener;


public interface TimerListener extends EventListener {
	public void expired(TimerEvent e);
}
