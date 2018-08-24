package game.test.thread;
import java.util.Random;
/**
 *	��ʾ����������� 
 */
public class Request
{
	private final String name ;
	private final int num ;
	private static final Random rd = new Random();
	public Request( String name, int i ) {
		this.name = name ;
		this.num = i ;
	}
	
	public void execute(){
		System.out.println(Thread.currentThread().getName()+" ���ڴ��� " +this );
		try {
			Thread.sleep(rd.nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "[ ���� "+name+"No."+num+" ������ ]" ;
	}
	
}
