package game.view.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class TTextArea extends JTextArea{
	
	private static final long serialVersionUID = 1L;

	public TTextArea() {
		setOpaque(false);
		setEditable(false);
		setBackground(new Color(1,1,1, (float) 0.01));
		setBorder(BorderFactory.createEmptyBorder(2,0,2,0));
		setLineWrap(true);
		setWrapStyleWord(true);
		setFont(new Font("微软雅黑", Font.PLAIN, 12));
		setForeground(Color.white);
	}
}
