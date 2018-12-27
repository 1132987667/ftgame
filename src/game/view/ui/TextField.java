package game.view.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import game.utils.SUtils;

public class TextField extends JLabel{
	private static final long serialVersionUID = 1L;

	private int type ; 
	private Font font1 = new Font("微软雅黑", Font.PLAIN, 12); 
	private Font font2 = new Font("隶书", Font.PLAIN, 16); ;
	private Font font3 = new Font("幼圆", Font.PLAIN, 14); 
	public TextField(String str, int type) {
		this(str, type, null);
	}
	
	public TextField(String str, int type, Color color) {
		super(str);
		this.type = type ;
		
		if(color==null)
			setForeground(Color.white);
		else
			setForeground(color);
		
		if(type==1)
			setFont(font2);
		else if(type==2)
			setFont(font1);
		else if(type==3)
			type3Field();
		else if(type==3)
			setFont(font3);
		else if(type==4) {
			setBackground(SUtils.LightBlue);
			setForeground(SUtils.LightBlue);
		}
	}
	
	public TextField(int type, int w, int h) {
		this("",type,SUtils.Red);
		setPreferredSize(new Dimension(w, h));
		setSize(w, h);
	}
	
	public void type3Field(){
		setFont(font2);
		setOpaque(true);
		setBackground(Color.GRAY);
		setVerticalAlignment(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
	}
	
}
