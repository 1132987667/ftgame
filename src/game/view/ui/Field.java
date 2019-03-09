package game.view.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import game.control.IoCtrl;
import game.utils.C;
import game.utils.SUtils;

/**
 * 基础标签类
 * 
 * 
 * @author admin
 *
 */
public class Field extends JLabel{
	private static final long serialVersionUID = 1L;
	private int type ; 
	
	
	public Field(String str, int type) {
		this(str, type, null);
	}
	
	public Field(String str){
		super(str);
	}
	
	/** 有默认字体:幼圆14，默认字体颜色:黑色，默认背景颜色:*/
	public static Field defField(String text) {
		Field field = new Field(text);
		field.setOpaque(true);
		field.setFont(C.YH_M);
		field.setForeground(Color.black);
		field.setBackground(C.defBacColor);
		field.setBorder(new EmptyBorder(0,2,0,2));
		return field ;
	}
	
	public Field(String text, Font f) {
		super(text);
		setFont(f);
	}
	
	public Field(String str, int type, Color color) {
		super(str);
		this.type = type ;
		
		if(color==null)
			setForeground(Color.white);
		else
			setForeground(color);
		
		if(type==1)
			setFont(C.Kai_M);
		else if(type==2)
			setFont(C.Y_M);
		else if(type==3)
			type3Field();
		else if(type==3)
			setFont(C.LS_M);
		else if(type==4) {
			setBackground(SUtils.LightBlue);
			setForeground(SUtils.LightBlue);
		}
	}
	
	public Field(int type, int w, int h) {
		this("",type,SUtils.Red);
		setPreferredSize(new Dimension(w, h));
		setSize(w, h);
	}
	
	public void type3Field(){
		setFont(C.Y_M);
		setOpaque(true);
		setBackground(Color.GRAY);
		setVerticalAlignment(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
	}
	
	/** 用来设定窗体背景图片 */
	private ImageIcon image1;
	
	/**
	 * 作为背景图片出现
	 * @param imgPath
	 */
	public Dimension asImgLabel(String imgPath){
		/** 不透明 */
		setOpaque(false);
		setText("");
		
		image1 = IoCtrl.loadImageIcon("/game/img/back/"+imgPath);
		System.out.println(image1);
		setIcon(image1);
		Dimension d = new Dimension(image1.getIconWidth(), image1.getIconHeight());
		setBounds(0, 0, d.width, d.height);
		return d ;
	}
	
	public Field color(Color c) {
		this.setForeground(c);
		return this ;
	}
	
	
}
