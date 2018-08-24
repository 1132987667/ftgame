package game.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;

public class DrawThread implements Runnable {
	private Graphics g = null;
	private String str = null;
	private int type;
	ImageIcon image1 = new ImageIcon("src/game/img/one/minSwordH.png");

	public DrawThread(Graphics g,  String str, int type) {
		super();
		this.g = g;
		this.str = str;
		this.type = type;
	}

	@Override
	public void run() {
		Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		if(type==1){
			g2d.drawString(str, 60, 24);
			g2d.drawImage(image1.getImage(),0, 0, image1.getIconWidth(), image1.getIconHeight(), null);
		}else{
			g2d.drawString(str, 0, 24);
			g2d.drawImage(image1.getImage(),56, 0, image1.getIconWidth(), image1.getIconHeight(), null);
		}
	}

}
