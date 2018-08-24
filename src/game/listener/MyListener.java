package game.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import game.control.TimeController;


public class MyListener implements ActionListener{
	
	private JTextArea jta = null ;
	private JTextField jtf = null ;
	private TimeController t = TimeController.getInstance();
	//private GameControl gameControl = new GameControl();
	
	public MyListener( JTextArea jta, JTextField jtf ) {
		this.jta = jta ;
		this.jtf = jtf ;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//gameControl.gameRunning(jtf);
	}
	
}
