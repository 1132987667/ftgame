package game.view.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class QuickFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	int w = 0 ;
	int h = 0 ;
	
	public JPanel main = new JPanel() ;
	private JButton close = new JButton("");
	
	public QuickFrame(int w, int h){
		this();
		this.w = w ;
		this.h = h ;
		setSize(w, h);
		setClosePos();
	}
	
	public QuickFrame() {
		setSize(400, 400);
		setUndecorated(true);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(main);
		main.setLayout(null);
		main.setBackground(new Color(220, 220, 220));
		main.add(close);
		close.setSize(26,26);
		close.setBackground(Color.red);
		close.setLocation(getWidth()-close.getWidth(), 0);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
					
		}
	}
	
	public void setClosePos() {
		close.setLocation(getWidth()-close.getWidth(), 0);
	}
	
	public void start() {
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new QuickFrame().start();
	}
	
	
}
