package game.view.frame;


import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MyDialog extends Dialog{

	public MyDialog(Frame owner) {
		super(owner);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JButton jb = new JButton() ;
		frame.add(jb);
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
		frame.setSize(500,300);   
		frame.setVisible(true);     
		
	}
	
}
