package game.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.control.GameControl;
import game.entity.NPC;

public class ViewLner implements ActionListener{
	private NPC npc ;
	private GameControl gameControl = GameControl.getInstance() ;
	
	public ViewLner(NPC npc) {
		this.npc = npc ;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gameControl.append(npc.getName()+" : ", 4);
		gameControl.append(npc.getDes()+"\n", 0);
	}

}
