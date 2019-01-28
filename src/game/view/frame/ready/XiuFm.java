package game.view.frame.ready;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.view.button.PicBu;

public class XiuFm extends JFrame{
	private static final long serialVersionUID = 1L;
	public XiuFm() {
		setContentPane(main);
		main.setLayout(null);
		h = 400 ;
		initFunBu();
		
		w = funBu[0].getWidth()*5 - 4*8 ;
		
		
		close.setSize(26,26);
		close.setBackground(Color.red);
		close.setLocation(w-close.getWidth(), 0);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		add(close);
		
		setSize(w, h);
		setUndecorated(true);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private String[] funStr = {"修炼","灵根","功法","洞府","入世"} ;
	
	private PicBu[] funBu = new PicBu[5];
	
	private JPanel main = new JPanel() ;
	
	private JButton close = new JButton("");
	
	private int w, h ;
	
	private Font font = new Font("楷体",Font.PLAIN,19);
	
	void initFunBu() {
		for (int i = 0; i < funBu.length; i++) {
			funBu[i] = new PicBu(funStr[i], 32);
			funBu[i].setFont(font);
			funBu[i].setLocation((funBu[i].getWidth()-8)*i, h-funBu[i].getHeight());
			main.add(funBu[i]);
		}
	}
	
	public static void main(String[] args) {
		new XiuFm().setVisible(true);
	}
	
	
}
