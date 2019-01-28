package game.test;

import game.view.frame.QuickFrame;
import game.view.panel.GameView;

public class GameViewTest {
	public static void main(String[] args) {
		GameView gv = new GameView();
		QuickFrame qf = new QuickFrame(gv.getWidth(), gv.getHeight()) ;
		qf.getMainPanel().add(gv);
		qf.start();
	}
}
