package game.view.panel;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.control.GameControl;
import game.entity.NPC;
import game.listener.actionLner.FixBuLner;
import game.utils.C;
import game.utils.SUtils;
import game.view.button.MButton;
import game.view.ui.TextField;
/**
 * 进行游戏的主要界面
 * @author yilong22315
 *
 */
public class GameView extends JPanel{
	private static final long serialVersionUID = -8790788550649369541L;
	public JPanel mapView  = new JPanel();
	public JPanel npcView  = new JPanel();
	public JPanel ctrlView = new JPanel();
	public JPanel fixedView = new JPanel() ;
	/** 用来显示位置的按钮 */
	private MButton t1,t2,t3,t4,t5,t6,t7,t8,t9 ;
	public MButton[] mapButton = {t1,t2,t3,t4,t5,t6,t7,t8,t9} ;
	public Point[] buPos = new Point[9] ;
	private GameControl gameControl = GameControl.getInstance();
	private int w = 612 ;
	private int h = 268 ;
	private int sceneBuX = 80, sceneBuY = 45 ;
	
	
	/** 连接线 */
	private TextField[] cables ;
	
	
	/** 挖矿 钓鱼  */
	private MButton view    = new MButton(C.actionStr[0], 31);
	private MButton talk    = new MButton(C.actionStr[1], 31);  
	private MButton kill    = new MButton(C.actionStr[2], 31);
	private MButton give    = new MButton(C.actionStr[3], 31);
	private MButton trading = new MButton(C.actionStr[4], 31);
	private MButton tasks   = new MButton(C.actionStr[5], 31);
	
	
	
	public MButton[] actionBu = {view, talk, kill, give, tasks} ;
	
	
	public MButton dazuo, lianwu, ting, ziliao, wakuang, diaoyu, kanfa, liandan, cook ;
	public MButton[] fixActionBu = {dazuo, lianwu, ting, ziliao, wakuang, diaoyu, kanfa, liandan, cook} ;
	
	
	
	/** 信息实体类 */
	public NPC npc ;
	
	
	
	public GameView() {
		setLayout(null);
		setOpaque(true);
		setSize(w, h);
		setBackground(Color.white);
		
		/*** map部分初始化 */
		mapView.setOpaque(false);
		mapView.setBackground(new Color(0, 0, 0, 0));
		mapView.setLayout(null);
		mapView.setBounds(0, 0, 280, 198);
		setDTBu();
		/** 初始化连接线 */
		initCables();
		
		/** npc部分初始化 */
		npcView = new JPanel() ;
		npcView.setOpaque(false);
		npcView.setLayout(null);
		npcView.setBounds(mapView.getWidth() ,0, w-mapView.getWidth(), 33*4);
		
		/**交互部分初始化 */
		ctrlView.setOpaque(false);
		ctrlView.setLayout(null);
		ctrlView.setBounds(npcView.getX(), npcView.getY()+npcView.getHeight(), npcView.getWidth(), mapView.getHeight() - npcView.getHeight());
		
		initFixBu();
		
		fixedView.setOpaque(true);
		fixedView.setBackground(Color.black);
		fixedView.setLayout(null);
		System.out.println("位子高度:"+ctrlView.getY()+ctrlView.getHeight());
		fixedView.setBounds(0, ctrlView.getY()+ctrlView.getHeight(), w, 70);
		
		
		
		/** 组件加入 */
		add(mapView);
		add(npcView);
		add(ctrlView);
		add(fixedView);
		
		/** 设置背景图片 */
		SUtils.setBackImg(this, C.gameBack);
	}
	
	/**
	 * 初始化连接线
	 */
	public void initCables() {
		Point p1 = new Point(7, 4);
		Point p2 = new Point(4, 10);
		
		cables = new TextField[12];
		TextField tf = null ;
		for (int i = 0; i < cables.length; i++) {
			if(i>5) {
				tf = new TextField(4, p2.x, p2.y);
				tf.setSize(p2.x, p2.y);
			}else {
				tf = new TextField(4, p1.x, p1.y);
				tf.setSize(p1.x, p1.y);
			}
			tf.setOpaque(true);
			tf.setVisible(false);
			cables[i] = tf ;
			mapView.add(cables[i]);
			
		}
		cables[0].setLocation(buPos[0].x+sceneBuX, buPos[0].y+(sceneBuY-p1.y)/2);
		cables[1].setLocation(buPos[1].x+sceneBuX, buPos[1].y+(sceneBuY-p1.y)/2);
		cables[2].setLocation(buPos[3].x+sceneBuX, buPos[3].y+(sceneBuY-p1.y)/2);
		cables[3].setLocation(buPos[4].x+sceneBuX, buPos[4].y+(sceneBuY-p1.y)/2);
		cables[4].setLocation(buPos[6].x+sceneBuX, buPos[6].y+(sceneBuY-p1.y)/2);		
		cables[5].setLocation(buPos[7].x+sceneBuX, buPos[7].y+(sceneBuY-p1.y)/2);
		
		cables[6].setLocation(buPos[0].x+(sceneBuX-p2.x)/2, buPos[0].y+sceneBuY+1);
		cables[7].setLocation(buPos[1].x+(sceneBuX-p2.x)/2, buPos[1].y+sceneBuY+1);
		cables[8].setLocation(buPos[2].x+(sceneBuX-p2.x)/2, buPos[2].y+sceneBuY+1);
		cables[9].setLocation(buPos[3].x+(sceneBuX-p2.x)/2, buPos[3].y+sceneBuY+1);
		cables[10].setLocation(buPos[4].x+(sceneBuX-p2.x)/2, buPos[4].y+sceneBuY+1);
		cables[11].setLocation(buPos[5].x+(sceneBuX-p2.x)/2, buPos[5].y+sceneBuY+1);
	}
	
	
	public void setCablesLoc() {
		for (int i = 0; i < cables.length; i++) {
			cables[i].setVisible(false);
		}
		if( mapButton[0].isVisible() ) {
			if( mapButton[1].isVisible()) 
				if (!SUtils.canArrived(mapButton[0].getScene(), mapButton[1].getScene()))
					cables[0].setVisible(true);
			if( mapButton[3].isVisible()) 
				if (!SUtils.canArrived(mapButton[0].getScene(), mapButton[3].getScene()))
				cables[6].setVisible(true);
		}
		if( mapButton[2].isVisible() ) {
			if( mapButton[1].isVisible()) 
				if (!SUtils.canArrived(mapButton[2].getScene(), mapButton[1].getScene()))
				cables[1].setVisible(true);
			if( mapButton[5].isVisible()) 
				if (!SUtils.canArrived(mapButton[2].getScene(), mapButton[5].getScene()))
				cables[8].setVisible(true);
		}
		if( mapButton[6].isVisible() ) {
			if( mapButton[3].isVisible()) 
				if (!SUtils.canArrived(mapButton[6].getScene(), mapButton[3].getScene()))
				cables[9].setVisible(true);
			if( mapButton[7].isVisible()) 
				if (!SUtils.canArrived(mapButton[6].getScene(), mapButton[7].getScene()))
				cables[4].setVisible(true);
		}
		if( mapButton[8].isVisible() ) {
			if( mapButton[7].isVisible()) 
				if (!SUtils.canArrived(mapButton[8].getScene(), mapButton[7].getScene()))
				cables[5].setVisible(true);
			if( mapButton[5].isVisible()) 
				if (!SUtils.canArrived(mapButton[8].getScene(), mapButton[5].getScene()))
				cables[11].setVisible(true);
		}
		if( mapButton[4].isVisible() ) {
			if( mapButton[1].isVisible()) 
				if (!SUtils.canArrived(mapButton[4].getScene(), mapButton[1].getScene()))
				cables[7].setVisible(true);
			if( mapButton[3].isVisible()) 
				if (!SUtils.canArrived(mapButton[4].getScene(), mapButton[3].getScene()))
				cables[2].setVisible(true);
			if( mapButton[5].isVisible()) 
				if (!SUtils.canArrived(mapButton[4].getScene(), mapButton[5].getScene()))
				cables[3].setVisible(true);
			if( mapButton[7].isVisible()) 
				if (!SUtils.canArrived(mapButton[4].getScene(), mapButton[7].getScene()))
				cables[10].setVisible(true);
		}
	}
	
	/**
	 * 设置地图按钮的位置
	 */
	public void setDTBu() {
		for (int j = 0; j < mapButton.length; j++) {
			 int x = j%3 ; 
			 int y = j/3 ;
			 mapButton[j] = new MButton("scene"+j, 15);
			 mapButton[j].setVisible(false);
			 mapButton[j].addMouseListener(mapButton[j]);
			 mapButton[j].addActionListener(gameControl.mapBuAc);
			 mapView.add(mapButton[j]);
			 mapButton[j].setBounds(12+(8+80)*x, 19+(12+45)*y, sceneBuX, sceneBuY);
			 buPos[j] = new Point(mapButton[j].getX(), mapButton[j].getY());
			 System.out.println("按钮"+j+"位置:"+mapButton[j].getX()+","+mapButton[j].getY());			 
		}
	}

	
	
	/***
	 * 隐藏显示地图地点的每个按钮
	 */
	public void hideMapBu() {
		for (int i = 0; i < mapButton.length; i++) {
			mapButton[i].setVisible(false);
			mapButton[i].setFlag(1);
			mapButton[i].mouseExited();
		}
	}
	
	/**
	 * 初始化固定的按钮
	 */
	public void initFixBu() {
		FixBuLner fixBuLner = new FixBuLner();
		for (int i = 0; i < fixActionBu.length; i++) {
			fixActionBu[i] = new MButton(C.fixAcionStr[i], 31) ;
			fixActionBu[i].setToolTipText(C.fixAcionTip[i]);
			fixActionBu[i].setSize();
			fixActionBu[i].setLocation(i*(fixActionBu[i].getWidth()), 0);
			fixActionBu[i].addActionListener(fixBuLner);
			fixedView.add(fixActionBu[i]);
		}
	}
	
	
	/**
	 * 把所有交互按钮设置为不启用
	 */
	public void initAcBustatus() {
		for (int i = 0; i < actionBu.length; i++) {
			actionBu[i].used = false ;
		}
	}
	
	public void hideAcBu() {
		for (int i = 0; i < actionBu.length; i++) {
			actionBu[i].setVisible(false);
		}
	}
	
	/** npc显示区域重新刷新 */
	public void removeNpc() {
		npcView.removeAll();
		npcView.repaint();
		npcView.revalidate();
	}
	
	public void removeNpc(JButton npc) {
		npcView.remove(npc);
	}

	public int getSceneBuX() {
		return sceneBuX;
	}

	public int getSceneBuY() {
		return sceneBuY;
	}
	
	
	
	
	
}
