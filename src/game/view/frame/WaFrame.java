package game.view.frame;

import game.control.GameControl;
import game.view.button.TButton;
import game.view.panel.PlayerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Area;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.awt.AWTUtilities;

/**
 * 不规则窗体示例（PNG图片实现）
 * 
 * @author yilong
 */
public class WaFrame extends JFrame implements WindowListener {
	private JFrame parent;
	private static final long serialVersionUID = 1L;
	private ImageIcon img; // 用来设定窗体不规则样式的图片
	private GameControl gameControl = null;

	/** 主容器 */
	private JPanel contentPane;

	public WaFrame(final JFrame parent, int type) {
		super();
		gameControl = GameControl.getInstance();

		/** 设置透明显示 */
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		contentPane = new JPanel();
		contentPane.setOpaque(false);// 可视化编辑下会自动创建一个JPanel,也要将这个JPanel设为透明，
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		/** 设置显示图片 */
		JLabel label = new JLabel("");
		String imgPath = null;
		switch (type) {
		case 1:// 人物属性和装备图
			imgPath = "src/game/img/back/水墨边框1.png";
			break;
		default:
			break;
		}
		img = new ImageIcon(imgPath);
		label.setIcon(img);
		contentPane.add(label, BorderLayout.CENTER);
		label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

		/** 与父窗口交互 */
		this.parent = parent;
		parent.setEnabled(false);
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		setDragable(); // 用来移动窗体
		
		/** 新增关闭按钮 */
		TButton close = new TButton("", 11);
		contentPane.add(close);
		close.addMouseListener(close);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//gameControl.close();
				parent.setEnabled(true);
				parent.requestFocus();
			}
		});
		close.setLocation(490, 2);
		close.setSize(23, 23);
		AWTUtilities.setWindowOpacity(this, 1f);
	}


	/** 控制主窗口无法被选取 */
	@Override
	public void windowClosed(WindowEvent e) {
		parent.setEnabled(true);
		parent.requestFocus();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		this.requestFocus();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	Point loc = null;
	Point tmp = null;
	boolean isDragged = false;

	/** 用来移动窗体的方法 */
	private void setDragable() {
		contentPane.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isDragged = false;
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(java.awt.event.MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if (isDragged) {
					loc = new Point(getLocation().x + e.getX() - tmp.x,
							getLocation().y + e.getY() - tmp.y);
					setLocation(loc);
				}
			}
		});
	}
}