package game.listener.actionLner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcLner implements ActionListener{

	protected String[] msgs = null ;
	protected int index = 0 ;
	
	
	public AcLner() {
	}
	
	public AcLner(String[] msgs) {
		this.msgs = msgs ;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	

}
