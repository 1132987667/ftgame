package game.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

public class UICtrl {
	
	
	
	
	/**
	 * 为容器设置带 标题 和 指定 字体的边框
	 * @param c
	 * @param str
	 * @param f
	 */
	public static void setBorder(JComponent c, String str, Font f) {
		/** 样式 标题 位置 字体 边框颜色 */
		c.setBorder(new TitledBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(128, 29, 174)), str,
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
	
	
	
	
	
}
