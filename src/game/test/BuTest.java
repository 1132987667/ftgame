package game.test;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.view.frame.QuickFrame;

public class BuTest {
	public static void main(String[] args) {
		QuickFrame qf = new QuickFrame(600, 400);
		JPanel panel = qf.getMainPanel() ;
		panel.setSize(600, 400);
		
		JButton jb = new JButton("大侠") ;
		jb.setContentAreaFilled(true);// 设置图片填充所在区域
		// 设置与四周的间距
		jb.setMargin(new Insets(0, 0, 0, 0));
		// 设置是否绘制边框
		jb.setBorderPainted(false);
		// 设置边框
		//jb.setBorder(null);
		// 居中显示
		jb.setRolloverEnabled(true);
		jb.setHorizontalTextPosition(SwingConstants.CENTER);
		jb.setBounds(50, 50, 80, 24);
		
		panel.add(jb);
		qf.start();
	}
}
