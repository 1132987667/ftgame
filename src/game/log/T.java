package game.log;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import game.view.button.PicBu;
import game.view.frame.QuickFrame;


public class T {
	public static void main(String[] args) {
		
		QuickFrame f = new QuickFrame();
		JPanel p = f.main;
		PicBu mb = new PicBu("????", 33);
		mb.setSize();
		System.out.println(mb.getX());
		mb.setSize(mb.getWidth()+20, mb.getHeight()+20);
		mb.setLocation(20, 20);
		mb.setBorder(BorderFactory.createEtchedBorder(1, Color.ORANGE, Color.ORANGE));
		
		JButton b = new JButton("学习") ;
		b.setSize(80,20);
		b.setLocation(104,  20);
		BevelBorder bor = new BevelBorder(1, Color.ORANGE, Color.GREEN);

		b.setBorder(BorderFactory.createEtchedBorder(1, Color.ORANGE, Color.ORANGE));
		
		f.add(mb);
		f.add(b);
		
		f.start();
	}
}
