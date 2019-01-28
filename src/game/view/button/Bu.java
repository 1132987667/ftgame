package game.view.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import game.control.UICtrl;
import game.utils.C;

public class Bu extends JButton {
	private static final long serialVersionUID = 1L;
	private int type = 0;
	protected final int defX = 80;
	protected final int defY = 24;
	protected int x = defX;
	protected int y = defY;
	public Bu(String title, int type) {
		this.type = type;

		setText(title);
		// 设置字体颜色
		setForeground(Color.white);
		setBackground(Color.BLACK);
		// 设置字体大小 和 格式
		setFont(C.Kai_M);
		// 设置透明
		// setOpaque(false);// 是否透明 false透明
		// 图片填充所在区域
		// setContentAreaFilled(false);// 设置图片填充所在区域
		// 设置与四周的间距
		setMargin(new Insets(0, 0, 0, 0));
		// 设置是否绘制边框
		setBorderPainted(false);
		// 设置边框
		UICtrl.setEmptyBorder(this);

		// setRolloverEnabled(true);
		/** 文字居中 */
		setHorizontalTextPosition(SwingConstants.CENTER);
		setSize();
	}

	public void setSize() {
		setSize(x, y);
		setPreferredSize(new Dimension(x, y));
	}

}
