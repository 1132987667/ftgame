package game.view.ui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import game.view.button.PicBu;

public class ImageIconCopy extends ImageIcon {
	private static final long serialVersionUID = 1L;

	public ImageIconCopy(String fileName) throws Exception {
		super(ImageIO.read(PicBu.class.getResourceAsStream(fileName)));
		System.out.println(fileName);
	}
	
	public ImageIcon ImageIconLoad(String fileName) {
		ImageIcon imageIcon = null ;
		try {
			imageIcon = new ImageIcon(ImageIO.read(PicBu.class.getResourceAsStream(fileName))) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageIcon ;
	} 
	
}