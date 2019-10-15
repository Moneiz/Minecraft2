package net.minecraft2.client.multiplayer;

public class ThreadLanServerPing extends Thread {
	private boolean isStopping = true;
	public void interrupt() {
		super.interrupt();
		this.isStopping = false;
	}
}
