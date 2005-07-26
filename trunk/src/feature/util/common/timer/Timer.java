package feature.util.common.timer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

// Timer ist ein Bean, das in bestimmten Zeitabständen Events feuert.
// Der Zeitabstand und die Anzahl der generierten Events ist konfigurierbar.
public class Timer implements Runnable, Serializable {

	private static final long serialVersionUID = 20001;

	protected transient PropertyChangeSupport propChange = new PropertyChangeSupport(
			this);

	// Liste aller registrierten Listener
	protected Vector<TimerListener> listeners = new Vector<TimerListener>();

	protected int interval;

	protected int noTicks = 1;

	protected int tickCount = 0;

	protected boolean stopThread = false;

	protected transient Thread tickerThread = null;

	public void addPropertyChangeSupport(PropertyChangeListener pcl) {
		propChange.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeSupport(PropertyChangeListener pcl) {
		propChange.removePropertyChangeListener(pcl);
	}

	// Default-Konstruktor: Events werden in Sekundenabständen gefeuert.
	public Timer() {
		this(1000);
	}

	// Konstruktor, mit dem die Eventintervalle angegeben werden können.
	public Timer(int timeout) {
		this.interval = timeout;
	}

	// Property interval: Länge des Timeout-Intervalls
	public int getInterval() {
		return interval;
	}

	public void setInterval(int t) {
		int oldTimeout = interval;
		interval = t;
		propChange.firePropertyChange("interval", new Integer(oldTimeout),
				new Integer(interval));
	}

	// Bound Property noTicks: Anzahl der Tick-Events
	public void setNoTicks(int n) {
		if (n >= 1) {
			int old = this.noTicks;
			this.noTicks = n;
			this.tickCount = 0;
			propChange.firePropertyChange("noTimeouts", new Integer(old),
					new Integer(interval));
		} else {
			throw new IllegalArgumentException();
		}

	}

	public int getNoTicks() {
		return noTicks;
	}

	// Starten des Timers. Die Timer-Events werden in einem eigenen Thread
	// generiert.
	public void start() {
		if (this.tickerThread == null) {
			this.tickerThread = new Thread(this);
			this.stopThread = false;
			this.tickerThread.start();
		}
	}

	// Timer-Thread wird beim nächsten Aufwachen terminiert.
	public void stop() {
		if (tickerThread != null)
			stopThread = true;
	}

	// Zurücksetzten des Timers. Timer kann nach "reset" neu gestartet werden.
	// Timer muss allerdings vorher beendet sein.
	public boolean reset() {
		if (tickerThread != null) {
			tickCount = 0;
			return true;
		}

		return false;
	}

	// Thread-Methode: Thread wird "noTicks"-mal jeweils für "interval"
	// Millisekunden schlafen gelegt.
	public void run() {
		if (this.tickerThread != null) {
			while (!this.stopThread && this.tickCount < this.noTicks) {
				try {
					Thread.sleep(this.interval);
				} catch (InterruptedException ex) {
					System.out.println(ex);
				}
				if (!this.stopThread)
					fireEvent(++this.tickCount);
			}// while
		}// if
	}

	// Registrieren eines Listeners
	public void addTimerListener(TimerListener l) {
		listeners.add(l);
	}

	// Listener meldet sich ab
	public void removeTimerListener(TimerListener l) {
		listeners.remove(l);
	}

	// Feuere das Tick-Event
	protected void fireEvent(int tickCount) {
		TimerEvent te = new TimerEvent(this, this.tickCount, this.noTicks);
		Vector listeners = (Vector) this.listeners.clone();
		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			((TimerListener) it.next()).expired(te);
		}
	}
}
