package game.view.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class TextField extends JLabel{
	private static final long serialVersionUID = 1L;

	private int type ; 
	private Font font1 = new Font("微软雅黑", Font.PLAIN, 12); 
	private Font font2 = new Font("隶书", Font.PLAIN, 16); ;
	
	public TextField(String str, int type) {
		this(str, type, null);
	}
	
	public TextField(String str, int type, Color color) {
		super(str);
		this.type = type ;
		if(type==1)
			setFont(font2);
		else
			setFont(font1);
		if(color==null)
			setForeground(Color.white);
		else
			setForeground(color);
	}
	
}
