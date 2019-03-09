package game.main;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.UIManager;

import game.utils.C;
import game.view.frame.EnterFrame;
/**
 * 游戏启动的文件
 * @author yilong22315
 *
 */
public class Starter {
	public static void main(String[] args) {
		UIManager.put("Label.foreground", Color.blue);
		UIManager.put("Label.font",C.Y_S); 
	//	System.setProperty("awt.useSystemAAFontSettings", "on"); 
	//	System.setProperty("swing.aatext", "true");
		EnterFrame frame = new EnterFrame();
		//frame.setBounds(200, 100, 1028, 512);
		
		frame.start();
	}
}
