package game.utils;

import java.awt.Component;

import javax.swing.JPanel;

import game.entity.compontent.Point;
import game.view.ui.ItemMd;

public class PosUtil {
	
	private static Component pre = null ;
	
	public static void add(JPanel par, Component cur) {
		
	}
	
	public static void add(JPanel par, ItemMd cur) {
		Point p = null ;
		if(pre==null) {
			p = new Point(0, 0);
			cur.leftMd.setLocation(p.x, p.y);
			p.translate(cur.leftMd.getWidth(), 0);
			cur.rightMd.setLocation(p.x, p.y);
		}else {
			p = new Point(pre.getX(), pre.getY());
		}
	}
	
	
	
	public static void arrangement(JPanel one, JPanel two) {
		
	}
	
}
