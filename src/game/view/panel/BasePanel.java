package game.view.panel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import game.control.GameControl;
import game.view.TLabel;
import game.view.ui.Field;

public class BasePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	protected GameControl ctrl = GameControl.getInstance();
	
	public Field backImg = null ;
	
	public JScrollPane jsc = null ;
	
	/** 长 */
	private int w ;
	/** 高 */
	private int h ;
	
	public void initSet() {
		setOpaque(false);
		setLayout(null);
		
		jsc = new JScrollPane(this);
		jsc.setDoubleBuffered(true);
		
		backImg = new Field("");
	}
	
	public JScrollPane openScroll() {
		jsc = new JScrollPane(this);
		jsc.setDoubleBuffered(true);
		return jsc; 
	}
	
	public JScrollPane getScrollPanel(){
		return jsc ;
	}

	public  void reloadUI(){} ;
		
	public  void reloadData(){} ;
	
	public  void setBacImg(){} ;
	
}
