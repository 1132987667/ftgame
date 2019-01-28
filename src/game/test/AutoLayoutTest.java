package game.test;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.control.UICtrl;
import game.view.frame.QuickFrame;
import game.view.ui.Field;
import game.view.ui.UniteModule;

public class AutoLayoutTest {
	public void fun1() {
		QuickFrame qf = new QuickFrame(600, 400);
		JPanel panel = qf.getMainPanel() ;
		panel.setSize(600, 400);
		
		List<UniteModule> list = new ArrayList<>();
		String s = "劫" ;
		for (int i = 0; i < 8; i++) {
			list.add(new UniteModule(Field.defField(s), new Fields("我在这")));
			s += "劫" ;
		}
		
		UICtrl.autoLayout(panel, null, list, 0);
		qf.start();
		
	}
	
	public static void main(String[] args) {
		new AutoLayoutTest().fun1();
	}
	
	class Fields extends JLabel{
		private static final long serialVersionUID = 1L;

		public Fields(String s) {
			super(s);
			setOpaque(true);
			setSize(160, 20);
			setPreferredSize(new Dimension(160, 20));
			setBackground(Color.black);
			setForeground(Color.WHITE);
		}
	}
	
	
}
