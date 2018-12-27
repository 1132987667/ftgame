package game.view.button;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import game.utils.SUtils;

/**
 * 放置了图片的按钮
 * 
 * @author yilong22315
 *
 */
public class TButton extends JButton implements MouseListener {
	Color[] c = { new Color(67, 110, 238), new Color(28, 134, 238), new Color(16, 78, 139) };
	private static final long serialVersionUID = 1L;
	private String imageName = null;
	protected ImageIcon image1 = null;
	protected ImageIcon image2 = null;
	protected ImageIcon image3 = null;
	public int type;
	private Color color = new Color(255, 0, 0,100);
	
	public boolean disable = false ;
	/** 在x上画线的距离 */
	public int xPos = 0 ;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(disable) {
			Graphics2D g2d = (Graphics2D) g ;
			g2d.setColor(color);
		}
	}
	
	
	public TButton(String str, int type) {
		super(str);
		this.type = type;
		// 设置字体颜色
		setForeground(Color.white);
		// 设置字体大小 和 格式
		setFont(new Font("楷体", Font.PLAIN, 14));
		// 设置透明
		//setOpaque(false);// 是否透明 false透明
		// 图片填充所在区域
		setContentAreaFilled(false);// 设置图片填充所在区域
		// 设置与四周的间距
		setMargin(new Insets(0, 0, 0, 0));
		// 设置是否绘制边框
		setBorderPainted(false);
		// 设置边框
		setBorder(null);
		// 居中显示
		iconControl(type);
		setRolloverEnabled(true);
		if (type != -1) {
			setHorizontalTextPosition(SwingConstants.CENTER);
			this.setIcon(image1);
			int x = image1.getIconHeight();
			int y = image1.getIconWidth();
			this.setSize(x, y);
			this.setPreferredSize(new Dimension(x, y));
			addMouseListener(this);
		} else {
			setBackground(Color.BLACK);
			setForeground(Color.orange);
			setFont(new Font("幼圆", Font.PLAIN, 12));
			setOpaque(true);
			SUtils.setEmptyBorder(this);
			setHorizontalTextPosition(SwingConstants.LEFT);
		}
		setSize();
	}

	private void iconControl(int type) {
		getImageName(type);
		if (type == 5) {
			image1 = SUtils.loadImageIcon("/game/img/archive/" + imageName + "1.png");
			image2 = SUtils.loadImageIcon("/game/img/archive/" + imageName + "2.png");
			image3 = SUtils.loadImageIcon("/game/img/archive/" + imageName + "3.png");
		} else if (type == 14) {
			image1 = SUtils.loadImageIcon("/game/img/one/" + imageName + ".png");
		} else if (type == -1) {

		} else {
			try {
				image1 = SUtils.loadImageIcon("/game/img/button/" + imageName + "1.png");
				image2 = SUtils.loadImageIcon("/game/img/button/" + imageName + "2.png");
				image3 = SUtils.loadImageIcon("/game/img/button/" + imageName + "3.png");

			} catch (NullPointerException e) {
			}
			// image4 = new ImageIcon("/game/img/button/" + imageName + "4.png");
		}
		setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (type == -1) {
			setBackground(c[2]);
		} else if (type == 14) {

		} else {
			this.setIcon(image2);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (type == -1) {
			setBackground(c[1]);
		} else if (type == 14) {

		} else {
			this.setIcon(image3);
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		if (type == -1) {
			setBackground(c[1]);
		} else if (type == 14) {

		} else {
			this.setIcon(image3);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (type == -1) {
			setBackground(c[0]);
		} else if (type == 14) {

		} else {
			this.setIcon(image1);
		}
	}

	public void reload(int theType) {
		getImageName(theType);
		if (type == 5) {
			image1 = SUtils.loadImageIcon("/game/img/archive/" + imageName + "1.png");
			image2 = SUtils.loadImageIcon("/game/img/archive/" + imageName + "2.png");
			image3 = SUtils.loadImageIcon("/game/img/archive/" + imageName + "3.png");
		} else if (type == 14) {
			image1 = SUtils.loadImageIcon("/game/img/one/" + imageName + ".png");
		} else if (type == -1) {

		} else {
			try {
				image1 = SUtils.loadImageIcon("/game/img/button/" + imageName + "1.png");
				image2 = SUtils.loadImageIcon("/game/img/button/" + imageName + "2.png");
				image3 = SUtils.loadImageIcon("/game/img/button/" + imageName + "3.png");

			} catch (NullPointerException e) {
			}
			// image4 = new ImageIcon("/game/img/button/" + imageName + "4.png");
		}

		setIcon(image1);
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
		case 12://晕倒
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
			break ;
		case 29:
			imageName = "Fuben";
			break ;	
		case 30:
			imageName = "blackBu";
			break ;	
		case 31:
			imageName = "actionBu";
			break ;
		case 32:
			imageName = "xu";
			break ;
		case 33:
			imageName = "acBu";
			break ;
		default:
			imageName = "type";
			break;
		}
	}
	
	public void setSize() {
		if(image1!=null) 
			setSize(image1.getIconWidth(), image1.getIconHeight());
	}
	
	public void click() {
		setIcon(image2);
	}
	public void exit() {
		setIcon(image1);
	}
}
