package game.control;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import game.utils.SUtils;


public class IoCtrl {
	

	public static ImageIcon loadImageIcon(String fileName) {
		ImageIcon imageIcon = null;
		try {
			imageIcon = new ImageIcon(ImageIO.read(SUtils.class.getResourceAsStream(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageIcon;
	}
	
}
