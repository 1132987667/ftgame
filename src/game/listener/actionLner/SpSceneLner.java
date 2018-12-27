package game.listener.actionLner;
/***
 * 特殊场景点击监听
 * @author yilong22315
 */

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.control.GameControl;
import game.entity.Scene;
import game.utils.SUtils;

public class SpSceneLner implements ActionListener
{
	GameControl gameControl  = GameControl.getInstance();
	
	String destn ;
	
	public SpSceneLner(String destn) {
		this.destn = destn ;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {  
		String[] info = destn.split(";");
		Point p = SUtils.psCoord(info[1]);
		Scene sc = gameControl.getScene(p.x, p.y);
		/** true 可以到达 */
		gameControl.playerMoved(sc, null, true);
	}

}
