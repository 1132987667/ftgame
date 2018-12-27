package game.listener.actionLner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.control.GameControl;

public class FixBuLner implements ActionListener{
	GameControl gameControl = GameControl.getInstance();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command  = e.getActionCommand();
		
	}

	
	
}
