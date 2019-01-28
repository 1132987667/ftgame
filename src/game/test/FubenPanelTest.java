package game.test;

import game.utils.C;
import game.view.frame.QuickFrame;
import game.view.panel.FubenPanel;

public class FubenPanelTest {
	public static void main(String[] args) {
		FubenPanel fp = new FubenPanel(C.Fuben);
		fp.setLocation(0, 0);
		QuickFrame qf = new QuickFrame(fp.getWidth(), fp.getHeight());
		qf.getMainPanel().add(fp);
		qf.start();
		
	}
}
