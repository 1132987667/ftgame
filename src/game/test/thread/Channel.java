package game.test.thread;

/**
 *	���𴫵ݹ�������ͱ��湤���̵߳��� 
 *
 */
public class Channel
{
	private static final int MAX_REQUEST = 100 ;
	private final Request[] requestQueue ;
	private int tail ;
	private int head ;
	private int count ;//��������
	
	private final Worker[] threadPool ;
	public Channel(int threads) {
		this.requestQueue = new Request[MAX_REQUEST];
		this.tail = 0 ;
		this.head = 0 ;
		this.count = 0 ;
		threadPool = new Worker[threads];
		for (int i = 0; i < threadPool.length; i++) {
			threadPool[i] = new Worker("����"+i,this);
		}
	}
	
	public void startWork(){
		for (int i = 0; i < threadPool.length; i++) {
			threadPool[i].start();
		}
	}

	public synchronized void putReqeust(Request request){
		while(count>=requestQueue.length){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		requestQueue[tail]=request;
		tail = (tail+1)%requestQueue.length ;
		count++;
		notifyAll();
	}
	
	public synchronized Request takeReqeust(){
		while(count<=0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Request request = requestQueue[head];
		System.out.println("����"+count+"������");
		head = (head+1)%requestQueue.length ;
		count--;
		notifyAll();
		return request ;
	}
	
}
