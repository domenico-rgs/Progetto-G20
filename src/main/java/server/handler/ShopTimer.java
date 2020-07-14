package server.handler;

public class ShopTimer extends Thread{
	private int timeWait; //in minute
	server.handler.ShopCart parent;

	public ShopTimer(int minuteWait,server.handler.ShopCart parent) {
		this.timeWait = minuteWait;
		this.parent = parent;
	}

	@Override
	public void run() {
		//attesa del thread
		try {
			Thread.sleep(timeWait * 60 * 5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//se il thread non viene interrotto dalla classe shopCard, esegue questo
		this.parent.timeBreak();
		interrupt();
	}
}
