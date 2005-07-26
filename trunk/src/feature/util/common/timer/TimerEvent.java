
package feature.util.common.timer;

import java.util.EventObject;

public class TimerEvent extends EventObject {
	
	private static final long serialVersionUID = 20002;
	
	protected int noTicks = 0; //max number
	protected int tickCount = 0; //current number
	
	public TimerEvent(Object obj, int i, int n) {
		super(obj);
		this.noTicks = n;
		this.tickCount = i;
	}
	
	public int getNoTicks() {
		return this.noTicks;
	}
	
	public int getTickCount() {
		return this.tickCount;
	}
}

