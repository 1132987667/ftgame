package game.view.frame;

import java.awt.Container;

import javax.swing.JFrame;

public class BaseFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Container mainContainer ;
	
	
	public void initSet(){
		/** 取消容器装饰 */
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainContainer = getContentPane();
	}
	
}
