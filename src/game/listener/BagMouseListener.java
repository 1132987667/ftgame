package game.listener;

import game.control.GameControl;
import game.entity.Player;
import game.view.button.PicBu;
import game.view.panel.BagGongPanel;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 与BagActionListener配套使用显示装备信息的监听
 * @author yilong22315
 *
 */
public class BagMouseListener implements MouseListener{
	
	private Player player ;
	private BagGongPanel gongPanel ;
	private GameControl gameControl = GameControl.getInstance() ;
	
	public BagMouseListener(Player player) {
		this.player = player ;
		gongPanel = new BagGongPanel(BagGongPanel.SIMPLE_GONG);
		gongPanel.setVisible(false);
		gameControl.getLayeredPanel().add(gongPanel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		PicBu bu = (PicBu) e.getSource() ;
		String id  = bu.getActionCommand();
		Point p = gameControl.getMainFrame().getLocation();
		Point p1 = e.getLocationOnScreen();
		System.out.println((p1.getX()-p.getX())+","+(p1.getY()-p.getY()));
		gongPanel.setLocation(358,90);
		gongPanel.reloadGongPanel(gameControl.getGongByID(id));
		gongPanel.setVisible(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		gongPanel.setVisible(false);
	}
	
}
