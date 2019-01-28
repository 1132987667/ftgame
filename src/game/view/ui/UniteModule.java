package game.view.ui;

import javax.swing.JLabel;

public class UniteModule {
	
	public JLabel leftModule ;
	public JLabel rightModule ;
	
	public UniteModule(JLabel leftModule, JLabel rightModule) {
		if(leftModule == null) {
			leftModule = new JLabel("");
		}
		this.leftModule = leftModule ;
		if(rightModule==null) {
			rightModule = new JLabel("");
		}
		this.rightModule = rightModule ;
	}
	
}
