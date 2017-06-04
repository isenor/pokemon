package ca.isenor.pokemontcg.networking.server.lock;

public class PokeLock {
	private Object lock = new Object();
	private boolean wasSignalled = false;
	private int passes = 0;

	// issue: notifying thread can get in here and pass
	// through (wasSignalled will be false) before the waiting
	// thread takes ownership of the synchronized block.
	// Without knowledge of which player's turn it is, I don't think
	// I can use this class for interleaving turns.
	public void doWait() {
		synchronized(lock) {
			while(!wasSignalled) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			wasSignalled = false;
		}
	}

	public void doNotify() {
		synchronized(lock) {
			wasSignalled = true;
			lock.notifyAll();
		}
	}

	/**
	 * Control passing past a certain part in the execution.
	 *
	 * @return true if no one has passed by yet; false if someone has
	 */
	public boolean checkAndIncrement() {
		synchronized(lock) {
			if (passes == 0) {
				passes++;
				return true;
			}
			passes = 0;
			return false;
		}
	}
}
