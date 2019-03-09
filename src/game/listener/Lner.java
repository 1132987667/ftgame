package game.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Lner implements ActionListener{
	
	private Component c = null ;
	
	public Lner(Component c) {
		this.c = c ;
	}
	
	public void show(){
		if(c!=null){
			c.setVisible(true);
		}
	}
	
	public void hide(){
		if(c!=null){
			c.setVisible(false);
		}
	}

}
