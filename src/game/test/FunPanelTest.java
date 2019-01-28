package game.test;

import game.view.frame.QuickFrame;
import game.view.mainFrame.FunPanel;

public class FunPanelTest {
	public static void main(String[] args) {
		FunPanel fp = new FunPanel() ;
		QuickFrame qf = new QuickFrame(fp.getWidth(), fp.getHeight()) ;
		qf.getMainPanel().add(fp);
		qf.start();
		
	}
}
