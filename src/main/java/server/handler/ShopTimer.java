package server.handler;

/**this class takes care of launching a countdown to end the purchase within a time*/

public class ShopTimer extends Thread{
	private int timeWait; //in minute
	server.handler.ShopCart parent;

	public ShopTimer(int minuteWait,server.handler.ShopCart parent) {
		this.timeWait = minuteWait;
		this.parent = parent;
	}

	@Override
	public void run() {
		//thread wait
		try {
			Thread.sleep(timeWait * 60 * 5000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

		//if the thread is not stopped by the shopCard class, it does this
		
		this.parent.timeBreak();
		interrupt();
	}
}
