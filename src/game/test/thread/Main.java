package game.test.thread;

public class Main
{
	public static void main( String [] args ) {
		
		Channel c = new Channel(5);
		new Client("�ν�", c).start();
		new Client("١����", c).start();
		new Client("�������", c).start();
		c.startWork();
	}
}
