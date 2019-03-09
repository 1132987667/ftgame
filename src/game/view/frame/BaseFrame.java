package game.view.frame;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import game.control.GameControl;
import game.view.TLabel;
import game.view.button.PicBu;
import game.view.ui.Field;

public class BaseFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Container mainContainer ;
	
	protected GameControl ctrl = GameControl.getInstance();
	
	/** 背景图片 */
	public Field backImg = null ;
	/** 拖动按钮 */
	public PicBu drugBu = null ;
	
	
	public void initSet(){
		/** 取消容器装饰 */
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainContainer = getContentPane();
	}
	
	/** 打开拖动功能,设置默认样式 */
	public void openDrug(){
		drugBu = new PicBu("", 1);
		drugBu.setToolTipText("拖动");
		setDragable();
	}
	
	public PicBu getDrugBu(){
		return drugBu;
	}
	
	
	private Point loc = null;
	private Point tmp = null;
	private boolean isDragged = false;
	/** 用来移动窗体的方法 */
	private void setDragable() {
		drugBu.addMouseListener(new MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isDragged = false;
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(java.awt.event.MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		drugBu.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
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
