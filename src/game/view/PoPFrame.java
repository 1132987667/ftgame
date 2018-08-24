package game.view;

import javax.swing.JPanel;

public class PoPFrame extends JPanel{
	
	public int type = 0;
	
	public PoPFrame(int type) {
		this.type = type ;
	}
	
	public PoPFrame init(){
		PoPFrame poPFrame = null ;
		switch (type) {
		case 1:
			poPFrame = init1();
			break;
		case 2:
			poPFrame = init2();
			break;
		default:
			break;
		}
		return poPFrame;
	}

	private PoPFrame init2() {
		
		
		return null;
	}

	private PoPFrame init1() {
		
		
		return null;
	}	
		
}
