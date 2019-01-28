package game.view.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import game.control.GameControl;
import game.control.UICtrl;
import game.entity.Archive;
import game.entity.Player;
import game.listener.FunListener;
import game.listener.KeyMana;
import game.utils.C;
import game.view.TTextPane;
import game.view.button.MButton;
import game.view.button.PicBu;
import game.view.mainFrame.CtrlPanel;
import game.view.mainFrame.FunPanel;
import game.view.mainFrame.LocalAttrPanel;
import game.view.panel.BagPanel;
import game.view.panel.EquipInfoPanel;
import game.view.panel.GameView;
import game.view.panel.TestPanel;
/**
 * 进行游戏的主界面
 * @author yilong22315
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	/**	
	 *	1.人物面板
	 *  2.场景描述
	 *  3.功能
	 *  4.游戏信息
	 *  5.小地图
	 *  6.npc与物品
	 *  7.与npc或物品交互
	 */
	private FunPanel funPanel ;
	private LocalAttrPanel localAttrP ;
	private TTextPane sceneP,infoP ;
	
	/** 移动时可能会用到的全部按钮 */
	private MButton[] mapButton = null ;
	
	/** 角色面板字段的JLabel */
	private JLabel tempL = null ;
	/** 角色面板值的JLabel */
	JLabel nameValue,stateValue,rankValue,ExpValue,hpValue,mpValue,atkValue,defValue ;
	public JLabel[] attrAry = {nameValue,rankValue,stateValue,ExpValue,hpValue,mpValue,atkValue,defValue} ;
	/** 地方面板 */
	JPanel foeAttr   ;
	/** 功能按钮 */
	PicBu status, skillsBu, bagBu, task, map, fuben, save, juqing, console;
	/** 状态,技能,背包,任务,剧情,副本,保存,大地图,宠物,境界 */
	PicBu[] funAry = { status, skillsBu, bagBu, task, juqing, fuben, save, map, console };

	/** 战斗面板 */
	CtrlPanel ctrlPanel  ;
	/**
	 * 玩家与游戏进行交互的面板
	 * 包括了 
	 * 小地图面板
	 * 人物显示面板
	 * 操作人物面板
	 */
	JPanel tianPanel ;
	/** 背包面板 */
	BagPanel bag ;
	
	private GameView gameView ;
	
	TestPanel testPanel ;
	
	/** 四种物品 */
	JPanel weapons,armor,skillsLabel,materials;
	
	/** 弹窗监听器 */
	private FunListener funListener = null ;
	/** 快捷键接听 */
	private KeyMana keyMana  ;
	/** 控制游戏进行 */
	private GameControl gameControl = null ;
	
	/** 组件的x,y坐标 */
	private int x , y ;
	private int inset = 8 ;
	
	public int fontSize = 14 ;
	Font font1 = new Font("楷体", Font.PLAIN, 14);
	Font font2 = new Font("华文楷体", Font.BOLD, 14);
	Font font3 = new Font("幼圆", Font.PLAIN, 14);
	/** 当前角色和存档 */
	private Player player ;
	private Archive archive ;
	/** 再使用背包功能时 */
	EquipInfoPanel selectEp ;
	private  JLayeredPane layeredPanel ;
	
	/** 构造方法 */
	public MainFrame() {
		init();
	}
	
	/** 初始化主界面 */
	public void init(){
		setUndecorated(true);
		setLayout(null);
		this.getContentPane().setBackground(Color.black);
		
		/** 初始化游戏控制器 */
		gameControl = GameControl.getInstance();
		/** 游戏控制器组件传入 */
		gameControl.setMainFrame(this);
		
		/** 得到游戏角色 */
		archive = gameControl.getArchive();
		player = gameControl.getPlayer();
				//SUtils.conArcToPlayer(archive);
		if(gameControl.getPlayer()==null){
			gameControl.setPlayer(player);
		}
		
		
		int attrWidth = 160 ;
		int attrHeight = 225 ;
		x = fontSize*19/2 + 2*inset + 20 ;
		attrWidth = x ;
		
		/** 我的属性面板 */
		localAttrP = new LocalAttrPanel(160, 225);
		/** 设置面板人物属性 */
		localAttrP.reloadUI();
		add(localAttrP);
		
		
		/** 右侧大小 545 */
		int rightWidth = 600 ;
		attrWidth = attrWidth - 8 ;
		/** 房间系统 */
		sceneP = new TTextPane(rightWidth, 100) ;
		sceneP.setBackground(Color.WHITE);
		add(sceneP);
		sceneP.setLocation(attrWidth, 0);
		UICtrl.setBorder(sceneP, "当前位置", C.YH_M);
		sceneP.append("这儿一间破落的土地庙,因年久失修，土地庙四周都已经长满了杂草，土地神像也是锈迹斑斑，甚至两条胳膊上都已经生出了一条条裂痕，好似随时都有可能掉落下来", 0);
		
		
		/** 战斗面板 */
		ctrlPanel = new CtrlPanel() ;
		add(ctrlPanel);
		ctrlPanel.setLocation(attrWidth, sceneP.getY()+sceneP.getHeight());
		gameView = ctrlPanel.getGameView();
		
		
		gameControl.setFightJpanel(ctrlPanel);
		
		mapButton = ctrlPanel.getMapButton() ;
		
		PicBu close = new PicBu("",11);
		add(close);
		close.addActionListener(exit);
		close.setLocation(1000, 2);
		
		
		/** 初始化弹窗监听器 */
		funListener = new FunListener(this,archive,player);
		
		
		gameControl.initFunFrame(funListener);
		keyMana = new KeyMana(funListener);
		gameControl.setKeyMana(keyMana);
		addKeyListener(keyMana);
		ctrlPanel.getInfoPanel().addKeyListener(keyMana);
		setFocusable(true);
		
		/** 功能 装备技能。。。  */
		funPanel = new FunPanel() ;
		funPanel.setFunListener(funListener);
		add(funPanel);
		funPanel.setLocation(0, attrHeight);
		
		MyDialog tan = new MyDialog(this, 2);
		tan.setVisible(false);
		gameControl.setTan(tan);

		/** 功能 --结束  */
		
		/** 传入重要组件 */
		gameControl.sendPanel(localAttrP, sceneP, funPanel, infoP, gameView);
		
		/** 测试面板 testPanel */
		/*testPanel = new TestPanel();
		add(testPanel);*/
		
		/** 主界面一些面板值的初始化 */
		//gameControl.mainFrameInit();
		//SoundControl.ftMuc(21);
	}
	
	
	public void close(int type){
		funListener.close(type);
	}
	
	
	public MButton getMapBu(int num){
		return mapButton[num-1];
	}
	public MButton[] getMapBuAry(){
		return mapButton ;
	}

	
	/***
	 * 隐藏显示地图地点的每个按钮
	 */
	public void hideMapBu() {
		gameView.hideMapBu();
	}

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}


	/**
	 * 重新设置人物主界面的属性面板
	 */
	public void reloadPlayerAttr(){
		localAttrP.reloadData();
	}
	
	ActionListener exit = new  ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameControl.exitGame();
		}
	};
	
	

	
}
