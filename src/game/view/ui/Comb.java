package game.view.ui;

import java.awt.Dimension;

import javax.swing.JComboBox;

public class Comb extends JComboBox<String>{
	private static final long serialVersionUID = -1219620940117015517L;

	public Comb(String[] args) {
	}
	
	public void reload(String[] args){
		
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setMaximumSize(new Dimension(width ,height));
	}
	
	public int curSelect(){
		return getSelectedIndex();
	}
	
}
