package game.view.frame;


import java.awt.Color;
import java.awt.Dimension;

import game.view.ui.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.utils.SUtils;
import game.view.button.TButton;

public class MyDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	
	TButton close = new TButton("关闭", 32);
	TButton sure = new TButton("确定", 32);
	TButton hide = new TButton("", 11);	
	
	TButton perf = new TButton("执行", 31);
	JTextField input ;
	
	JPanel type1 = new JPanel();
	JPanel type2 = new JPanel();
	/**
	 * 弹出窗口的类型
	 * 1
	 * 2
	 * @param owner
	 * @param type
	 */
	public MyDialog(JFrame owner, int type) {
		super(owner);
		setUndecorated(true);
		setSize(600, 200);
		setLocation(200, 200);
		if(type==1) {
			initType1();
		}else if(type==2) {
			initType2();
		}
		JPanel panel = (JPanel) this.getContentPane();
		panel.add(hide);
		hide.setLocation(374,0);
		hide.addActionListener(hideLner);
	}
	
	public void initType1() {
		type1.setLayout(null);
		type1.setBackground(Color.white);
		type1.add(close);
		type1.add(sure);
		close.setLocation(40, 160);
		sure.setLocation(200, 160);
		setContentPane(type1);
	}
	
	public void initType2() {
		System.out.println("布置控制台");
		type2.setLayout(null);
		type2.setBackground(Color.white);
		input = new JTextField();
		input.setSize(300, 30);
		input.setPreferredSize(new Dimension(300, 30));
		input.setLocation(20, 40);
		perf.setLocation(input.getX()+input.getWidth()+4, input.getY()+2);
		perf.addActionListener(perfLner);
		type2.add(perf);
		type2.add(input);
		setContentPane(type2);
	}
	
	ActionListener perfLner = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = input.getText().trim();
			SUtils.psConsoleStr(text);
		}
	};
	
	ActionListener hideLner = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	};
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(1000,600);
		JButton jb = new JButton() ;
		frame.add(jb);
		final MyDialog myDialog = new MyDialog(frame, 2);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myDialog.setVisible(true);
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
		frame.setLocation(200, 200);
		frame.setVisible(true);     
		
	}
	
}
