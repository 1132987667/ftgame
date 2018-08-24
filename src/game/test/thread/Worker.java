package game.test.thread;
/**
 *	�����߳���
 */
public class Worker extends Thread
{
	private final Channel channel ;
	public Worker(String name,Channel channel) {
		super(name) ;
		System.out.println(name+"����");
		this.channel = channel ;
	}
	
	@Override
	public void run() {
		while(true){
			Request request = channel.takeReqeust();
			request.execute();
		}
	}
	
}
