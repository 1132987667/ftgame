package game.view.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import game.utils.SUtils;

public class TTextArea extends JTextArea{
	
	private static final long serialVersionUID = 1L;

	public TTextArea(int type) {
		setOpaque(false);//透明
		setEditable(false);//不可编辑
		setBackground(new Color(1,1,1, (float) 0.01));//透明
		setBorder(BorderFactory.createEmptyBorder(2,0,2,0));//无边框
		setLineWrap(true);//自动换行
		setWrapStyleWord(true);
		setFont(new Font("微软雅黑", Font.PLAIN, 12));
		setForeground(Color.white);
		if(type==1) {
			setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
			setFont(new Font("幼圆", Font.PLAIN, 14));//华文中魏
			setBackground(SUtils.BlueGrey);
		}
		
		
	}
}
