package game.test;

import game.view.frame.QuickFrame;
import game.view.mainFrame.CtrlPanel;

public class CtrlPanelTest {
	public static void main(String[] args) {
		CtrlPanel cp = new CtrlPanel();
		QuickFrame qf = new QuickFrame(cp.getWidth(), cp.getHeight());
		qf.getMainPanel().add(cp);
		qf.start();
		
	}
}
