package game.view.button;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class TButton extends JButton implements MouseListener {
	Color[] c = { new Color(67, 110, 238), new Color(28, 134, 238),
			new Color(16, 78, 139) };
	private static final long serialVersionUID = 1L;
	private String imageName = null;
	protected ImageIcon image1 = null;
	protected ImageIcon image2 = null;
	protected ImageIcon image3 = null;
	protected ImageIcon image4 = null;
	public int type;

	public TButton(String str, int type) {
		super(str);
		this.type = type;
		// 设置字体颜色
		setForeground(Color.white);
		// 设置字体大小 和 格式
		setFont(new Font("楷体", Font.PLAIN, 16));
		// 设置透明
		setOpaque(false);// 是否透明 false透明
		// 图片填充所在区域
		setContentAreaFilled(false);// 设置图片填充所在区域
		// 设置与四周的间距
		setMargin(new Insets(0, 0, 0, 0));
		// 设置是否绘制边框
		setBorderPainted(false);
		// 设置边框
		setBorder(null);
		// 居中显示
		setHorizontalTextPosition(SwingConstants.CENTER);
		addMouseListener(this);
		iconControl(type);
		if (type != -1) {
			// System.out.println(image1);
			this.setIcon(image1);
			int x = image1.getIconHeight();
			int y = image1.getIconWidth();
			// System.out.println(x+" , "+y);
			this.setSize(x, y);
		} else {
			setBorderPainted(true);
			setOpaque(true);
			setBorder(BorderFactory.createEtchedBorder());
			setBackground(c[0]);
		}
	}

	private void iconControl(int type) {
		// System.out.println("type:"+type);
		switch (type) {
		case -1:
			break;
		case 1:
			imageName = "type";
			break;
		case 2:
			imageName = "灰黑";
			break;
		case 3:
			imageName = "蓝灰";
			break;
		case 4:
			imageName = "棕";
			break;
		case 5:
			imageName = "存档";
			break;
		case 6:
			imageName = "type1";
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
		case 12:
			imageName = "yd";
			break;
		case 13:
			imageName = "dragA";
			break;
		case 14:
			imageName = "picA";
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
			imageName = "shuzhi";
			break;
		case 22:
			imageName = "jiuqi";
			break;
		case 23:
			imageName = "makeB";
			break;
		default:
			imageName = "type";
			break;
		}
		if (type == 5) {
			image1 = new ImageIcon("src/game/img/archive/" + imageName
					+ "1.png");
			image2 = new ImageIcon("src/game/img/archive/" + imageName
					+ "2.png");
			image3 = new ImageIcon("src/game/img/archive/" + imageName
					+ "3.png");
		} else if (type == 14) {
			image1 = new ImageIcon("src/game/img/one/" + imageName + ".png");
		} else {
			image1 = new ImageIcon("src/game/img/button/" + imageName + "1.png");
			image2 = new ImageIcon("src/game/img/button/" + imageName + "2.png");
			image3 = new ImageIcon("src/game/img/button/" + imageName + "3.png");
			image4 = new ImageIcon("src/game/img/button/" + imageName + "4.png");
		}

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
		// System.out.println(type);
		if (type == -1) {
			setBackground(c[0]);
		} else if (type == 14) {

		} else {
			this.setIcon(image1);
		}
	}
	
	public void reload(int theType){
			// System.out.println("type:"+type);
			switch (theType) {
			case -1:
				break;
			case 1:
				imageName = "type";
				break;
			case 2:
				imageName = "灰黑";
				break;
			case 3:
				imageName = "蓝灰";
				break;
			case 4:
				imageName = "棕";
				break;
			case 5:
				imageName = "存档";
				break;
			case 6:
				imageName = "type1";
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
			case 12:
				imageName = "yd";
				break;
			case 13:
				imageName = "dragA";
				break;
			case 14:
				imageName = "picA";
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
				imageName = "shuzhi";
				break;
			case 22:
				imageName = "jiuqi";
				break;
			case 23:
				imageName = "makeB";
				break;
			default:
				imageName = "type";
				break;
			}
			if (type == 5) {
				image1 = new ImageIcon("src/game/img/archive/" + imageName
						+ "1.png");
				image2 = new ImageIcon("src/game/img/archive/" + imageName
						+ "2.png");
				image3 = new ImageIcon("src/game/img/archive/" + imageName
						+ "3.png");
			} else if (type == 14) {
				image1 = new ImageIcon("src/game/img/one/" + imageName + ".png");
			} else {
				image1 = new ImageIcon("src/game/img/button/" + imageName + "1.png");
				image2 = new ImageIcon("src/game/img/button/" + imageName + "2.png");
				image3 = new ImageIcon("src/game/img/button/" + imageName + "3.png");
				image4 = new ImageIcon("src/game/img/button/" + imageName + "4.png");
			}
			setIcon(image1);
	}
}
