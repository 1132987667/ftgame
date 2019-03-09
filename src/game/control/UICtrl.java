package game.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import game.entity.compontent.Point;
import game.utils.C;
import game.view.ui.UniteModule;

public class UICtrl {
	
	
	public static void panelBaseSet(JPanel panel) {
		panel.setOpaque(false);
		panel.setLayout(null);
	}
	
	
	/**
	 * 为容器设置带 标题 和 指定 字体的边框
	 * @param c
	 * @param str
	 * @param f
	 */
	public static void setBorder(JComponent c, String str, Font f) {
		/** 样式 标题 位置 字体 边框颜色 */
		c.setBorder(new TitledBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK), str,
				TitledBorder.LEFT, TitledBorder.TOP, new Font("楷体", 0, 12), Color.BLUE));
	}
	
	
	/**
	 * 为容器设置带 标题 和 指定字体 和 颜色 的边框
	 * @param c
	 * @param str
	 * @param color
	 * @param f
	 */
	public static void setBorder(JComponent c, String str, Color color, Font f) {
		TitledBorder border = BorderFactory.createTitledBorder(str);
		border.setTitleFont(f);
		border.setTitleColor(color);
		c.setBorder(border);
	}
	
	public static void setEmptyBorder(JComponent c) {
		c.setBorder(new EmptyBorder(0, 0, 0, 0));
	}
	
	
	public static void setUi(JTabbedPane jtp) {
		jtp.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI() {
			@Override
			protected void paintTopTabBorder(int tabIndex, Graphics g, int x, int y, int w, int h, int btm, int rght,
					boolean isSelected) {
				g.drawLine(x, y, x + w - 2, y);
				// g.setColor(MetalLookAndFeel.getWhite());
				// g.drawLine(x, y + 2, x+w-2, y + 2);
			};

			@Override
			protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
					boolean isSelected) {
				int bottom = y + (h - 1);
				int right = x + (w - 1);
				g.setColor(Color.orange);
				paintTopTabBorder(tabIndex, g, x, y, w, h, bottom, right, isSelected);
			}

			@Override
			protected void paintLeftTabBorder(int tabIndex, Graphics g, int x, int y, int w, int h, int btm, int rght,
					boolean isSelected) {
			};

			@Override
			protected void paintRightTabBorder(int tabIndex, Graphics g, int x, int y, int w, int h, int btm, int rght,
					boolean isSelected) {
			};

			@Override
			protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y,
					int w, int h) {
			}

			@Override
			protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y,
					int w, int h) {
			}

		});
	}
	
	public void showFeel() {
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < info.length; i++)
		{
		    System.out.println(info[i].toString());
		}
	}
	
	/***
	 * 设置窗体样式
	 */
	public void setFeel() {
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
							
				} 
			}
		}
	}
	
	/**
	 * 自动设置布局, 或模块
	 * @param parentModule
	 * @param initPoint
	 * @param list
	 * @param model
	 * @return
	 */
	public static Point autoLayout(JPanel parentModule, Point initPoint, List<UniteModule> list, int model) {
		Point p = null ;
		if(initPoint==null) {
			p = new Point(C.INSET, C.INSET);
		}else {
			p =new Point(initPoint);
		}
		int w = parentModule.getWidth() ;
		int h = parentModule.getHeight() ;
		int textLen = 0 ;
		int fontSize = list.get(0).leftModule.getFont().getSize();
		for (int i = 0; i < list.size(); i++) {
			int temp = list.get(i).leftModule.getText().length();
			if(temp>textLen)
				textLen = temp ;
		}
		int leftW = textLen *fontSize + C.INSET ;
		
		for (int i = 0; i < list.size(); i++) {
			JLabel leftModule = list.get(i).leftModule;
			JLabel rightModule = list.get(i).rightModule;
			if(p.x + leftW + rightModule.getWidth() > w ) {
				p.move(C.INSET, p.y + 20 + C.INSET);
			}
			
			leftModule.setSize(leftW, 20);
			//leftModule.setPreferredSize(new Dimension(leftW, 20));
			leftModule.setLocation(p.x, p.y);
			System.out.println(p.x + "," + p.y);
			p.translate(leftW+C.INSET, 0);
			rightModule.setLocation(p.x, p.y);
			parentModule.add(leftModule);
			parentModule.add(rightModule);
			p.translate(rightModule.getWidth()+C.INSET, 0);
		}
		
		return p ;
	}
	
	/**
	 * 
	 * @param ref 参照物
	 * @param curC 当前物
	 * @param model 模式
	 */
	public Point autoLocation(Component ref, Component curC, int model) {
		int x = ref.getX()+ref.getWidth() ;
		int y = ref.getY()+ref.getHeight() ;
		Point p = new Point(x, y);
		/** 1下 2右 */
		if(model==1) 
			p.translate(0, C.INSET);
		else
			p.translate(C.INSET, 0);
		curC.setLocation(p.x, p.y);
		p.translate(curC.getWidth(), 0);
		return p ;
	}
	
	/**
	 * 
	 * @param ref 参照物
	 * @param curC 当前物
	 * @param model 模式
	 */
	public Point autoLocation(Point ref, Component curC, int model) {
		if(model==1) 
			ref.translate(0, C.INSET);
		else
			ref.translate(C.INSET, 0);
		curC.setLocation(ref.x, ref.y);
		ref.translate(curC.getWidth(), 0);
		return ref ;
	}
	
	
}
