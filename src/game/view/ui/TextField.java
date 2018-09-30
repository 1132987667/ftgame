package game.view.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

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
