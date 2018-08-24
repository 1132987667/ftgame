package game.view;

import java.awt.Font;

import javax.swing.JTextArea;

public class FightTextArea extends JTextArea{
	private static JTextArea jt = null ;
	
	private FightTextArea(int x,int y) {
		super(x, y);
		this.setFont(new Font("楷体",Font.PLAIN,18));
		this.setEditable(false);
		this.setLineWrap(true);        //激活自动换行功能 
		this.setWrapStyleWord(true);            // 激活断行不断字功能
	}

	public static JTextArea getInstance(int x,int y){
		if(jt==null){
			jt = new FightTextArea(x,y);
		}
		return jt;
	}
	
	public static void autoFlow() {
		int len = jt.getText().length() ;
		/*System.out.println("len:"+len);
		System.out.println( len +" , "+jt.getLineCount());*/
		/*if(len>30){
			jt.replaceRange("", 0, jt.getRows()*10); 
		}
		len = jt.getText().length() ;*/
		jt.setCaretPosition(len);   
	}
	
}
