package game.test;

import game.entity.npc.Liehudashu;
import game.utils.Constant;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class TalkTest extends JPanel{
	
	public TalkTest() {
		setLayout(null);
		this.setBackground(Constant.colorAry[4]);
		this.setBorder(BorderFactory.createTitledBorder("test"));
		final Liehudashu liehudashu = new Liehudashu() ;
		
		final JTextArea jt = new JTextArea();
		jt.setPreferredSize(new Dimension(260, 200));
		jt.setRows(20);
		add(jt);
		jt.setBounds(10,80,260,200);
		
		JButton jb = new JButton(liehudashu.name);
		add(jb);
		jb.setBounds(20,20,80,25);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] tempAry = liehudashu.action ;
				JButton[] jbAry = new JButton[tempAry.length]; 
				for (int i = 0; i < tempAry.length; i++) {
					jbAry[i] = new JButton(tempAry[i]);
					add(jbAry[i]);
					jbAry[i].setBounds(20+i*80,50,80,25);
					jbAry[i].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(e.getActionCommand().equals("交谈")){
								liehudashu.talk(jt);
							}else if(e.getActionCommand().equals("给予")){
								liehudashu.give(jt);
							}else if(e.getActionCommand().equals("任务")){
								liehudashu.task(jt);
							}
						}
					});
				}
			}
		});
	
		
		
	}
	
	
	public static void main(String[] args) {
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
							
				}
			}
		}
		JFrame f = new JFrame();
		f.add(new TalkTest());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setSize(300, 300);
		f.setVisible(true);
	}
	
}
