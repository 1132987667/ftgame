package game.test;

import java.awt.Color;

import javax.swing.JPanel;

import game.view.frame.QuickFrame;
import game.view.mainFrame.LocalAttrPanel;

public class LocalAttrPanelTest {
	public static void main(String[] args) {
		QuickFrame qf = new QuickFrame();
		JPanel panel = qf.getMainPanel();
		LocalAttrPanel attrPanel = new LocalAttrPanel(0,  0);
		attrPanel.setOpaque(true);
		attrPanel.setBackground(Color.white);
		panel.add(attrPanel);
		qf.start();
	}
}		
