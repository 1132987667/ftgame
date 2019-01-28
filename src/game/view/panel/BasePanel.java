package game.view.panel;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	
	
	
	protected void initSet() {
		setOpaque(false);
		setLayout(null);
	}

	public abstract void reloadUI() ;
		
	public abstract void reloadData() ;
	
	public abstract void setBacImg() ;
	
}
