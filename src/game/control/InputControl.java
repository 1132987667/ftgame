package game.control;

import javax.swing.JTextArea;

import game.utils.SUtils;
import game.view.FightTextArea;


public class InputControl implements Runnable{
	private JTextArea jt = null ;
	private String str = null ;
	private int time = 10 ;
	
	public InputControl(JTextArea jt,String str) {
		this.jt = jt ;
		this.str = str ;
	}
	
	public InputControl(JTextArea jt,String str,int time) {
		this.jt = jt ;
		this.str = str ;
		this.time = time ;
	}
	
	@Override
	public void run() {
		str = SUtils.lineSeparatorSplit(str);
		System.out.println("要输出内容"+str);
		char[] ary  = str.toCharArray();
		for (int i = 0; i < ary.length; i++) {
			jt.append(ary[i]+"");
			//是textArea随内容移动
			//FightTextArea.autoFlow();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
