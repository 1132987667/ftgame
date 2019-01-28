package game.view.button;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import game.control.IoCtrl;

/**
 * 使用了背景图片的按钮
 * 
 * @author yilong22315
 *
 */
public class PicBu extends Bu implements MouseListener {
	public PicBu(String title, int type) {
		super(title, type);
		// 设置透明
		// setOpaque(false);// 是否透明 false透明
		// 图片填充所在区域
		setContentAreaFilled(false);// 设置图片填充所在区域
		// 设置与四周的间距
		setMargin(new Insets(0, 0, 0, 0));
		// 设置是否绘制边框
		setBorderPainted(false);
		// 设置边框
		setBorder(null);
		// 居中显示
		load(type);
		setRolloverEnabled(true);
		setHorizontalTextPosition(SwingConstants.CENTER);

		if (initImg != null) {
			this.setIcon(initImg);
			x = initImg.getIconHeight();
			y = initImg.getIconWidth();
			this.setSize(x, y);
			this.setPreferredSize(new Dimension(x, y));
		}

		addMouseListener(this);
		setSize();

	}

	Color[] c = { new Color(67, 110, 238), new Color(28, 134, 238), new Color(16, 78, 139) };
	private static final long serialVersionUID = 1L;
	private String imageName = null;
	protected ImageIcon initImg = null;
	protected ImageIcon clickImg = null;
	protected ImageIcon inImg = null;
	public int type;
	private Color color = new Color(255, 0, 0, 100);

	private boolean disable = false;
	/** 在x上画线的距离 */
	public int xPos = 0;

	/** 移入移出点击都有效果 */
	public static final int Well = 1;
	/** 移入移出有效果，点击无效果 */
	public static final int Base = 2;
	/** 移入移出都无效果 */
	public static final int Enabled = 3;
	/** 鼠标效果启用标志 */
	private int flag = Well;

	public void well() {
		flag = Well;
	}

	public void base() {
		flag = Base;
	}

	public void Enabled() {
		flag = Enabled;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (disable) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(color);
		}
	}

	public void load(int type) {
		getImageName(type);
		if (type == 5) {
			initImg = IoCtrl.loadImageIcon("/game/img/archive/" + imageName + "1.png");
			clickImg = IoCtrl.loadImageIcon("/game/img/archive/" + imageName + "2.png");
			inImg = IoCtrl.loadImageIcon("/game/img/archive/" + imageName + "3.png");
		} else {
			
			try {
				initImg = IoCtrl.loadImageIcon("/game/img/button/" + imageName + "1.png");
				clickImg = IoCtrl.loadImageIcon("/game/img/button/" + imageName + "2.png");
				inImg = IoCtrl.loadImageIcon("/game/img/button/" + imageName + "3.png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setIcon(initImg);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (flag != Enabled) {
			this.setIcon(clickImg);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (flag != Enabled) {
			this.setIcon(inImg);
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (flag == Well) {
			this.setIcon(inImg);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (flag == Well) {
			this.setIcon(initImg);
		}
	}

	private void getImageName(int type) {
		switch (type) {
		case -1:
			break;
		case 1:
			imageName = "type";
			break;
		case 2:
			imageName = "huihei";
			break;
		case 3:
			imageName = "lanhui";
			break;
		case 4:
			imageName = "zong";
			break;
		case 5:
			imageName = "archive";
			break;
		case 6:
			imageName = "type";
			break;
		case 7:
			imageName = "type2";
			break;
		case 8:
			imageName = "纯色";
			break;
		case 9:
			imageName = "精致";
			break;
		case 10:
			imageName = "蓝色";
			break;
		case 11:
			imageName = "close";
			break;
		case 12:// 晕倒
			imageName = "yd";
			break;
		case 13:
			imageName = "dragA";
			break;
		case 14:
			imageName = "PicA";
			break;
		case 15:
			imageName = "BuF";
			break;
		case 16:
			imageName = "BuK";
			break;
		case 17:
			imageName = "macheA";
			break;
		case 18:
			imageName = "baiheA";
			break;
		case 19:
			imageName = "chuanA";
			break;
		case 20:
			imageName = "chuanB";
			break;
		case 21:
			imageName = "jiuqi";
			break;
		case 22:
			imageName = "jiuqi";
			break;
		case 23:
			imageName = "makeB";
			break;
		case 24:
			imageName = "longTou";
			break;
		case 25:
			imageName = "capacity";
			break;
		case 26:
			imageName = "taskdrug";
			break;
		case 27:
			imageName = "point";
			break;
		case 28:
			imageName = "dituBac";
			break;
		case 29:
			imageName = "Fuben";
			break;
		case 30:
			imageName = "blackBu";
			break;
		case 31:
			imageName = "actionBu";
			break;
		case 32:
			imageName = "xu";
			break;
		case 33:
			imageName = "acBu";
			break;
		default:
			imageName = "type";
			break;
		}
	}

	public void setSize() {
		if (initImg != null)
			setSize(initImg.getIconWidth(), initImg.getIconHeight());
	}

	public void click() {
		setIcon(clickImg);
	}

	public void exit() {
		setIcon(initImg);
	}
}
