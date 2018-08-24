package game.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.UIManager;

import game.view.frame.EnterFrame;

public class Demo {
	public static void main(String[] args) {
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < info.length; i++)
		{
		    System.out.println(info[i].toString());
		}
		Font font1 = new Font("幼圆", Font.PLAIN, 10);
		UIManager.put("Label.foreground", Color.blue);
		UIManager.put("Label.font",font1); 
	//	System.setProperty("awt.useSystemAAFontSettings", "on"); 
	//	System.setProperty("swing.aatext", "true");
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
							
				}
			}
		}
		//MainFrame frame = new MainFrame() ;
		EnterFrame frame = new EnterFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setBounds(200, 100, 1028, 512);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
