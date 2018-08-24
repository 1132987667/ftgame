package game.test.thread;

import java.util.Random;

/**
 *	�������͹���������� 
 */
public class Client	extends Thread
{
	private final Channel channel; 
	private static final Random rd = new Random();
	public Client(String name,Channel channel) {
		super(name);
		this.channel=channel;
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				Request request = new Request(getName(),i);
				channel.putReqeust(request);
				Thread.sleep(rd.nextInt(1000));
			}
		} catch (Exception e) {
		}
	}
	
}
