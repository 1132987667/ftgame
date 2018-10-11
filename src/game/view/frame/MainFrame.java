package game.view.frame;

import game.control.GameControl;
import game.entity.Archive;
import game.entity.Player;
import game.listener.FunListener;
import game.listener.KeyMana;
import game.utils.Constant;
import game.utils.SUtils;
import game.view.TTextPane;
import game.view.button.MButton;
import game.view.button.TButton;
import game.view.panel.BagPanel;
import game.view.panel.EquipInfoPanel;
import game.view.panel.TestPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
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
	private JPanel playerP ,functionP ,mapP ,npcP ,controlP;
	private TTextPane sceneP,infoP ;
	/** 移动时可能会用到的全部按钮 */
	private MButton t1,t2,t3,t4,t5,t6,t7,t8,t9 ;
	private MButton[] mapButton = {t1,t2,t3,t4,t5,t6,t7,t8,t9} ;
	
	/** 角色面板字段的JLabel */
	private JLabel tempL = null ;
	/** 角色面板值的JLabel */
	JLabel nameValue,stateValue,rankValue,ExpValue,hpValue,mpValue,atkValue,defValue ;
	public JLabel[] attrAry = {nameValue,rankValue,stateValue,ExpValue,hpValue,mpValue,atkValue,defValue} ;
	/** 地方面板 */
	JPanel foeAttr   ;
	/** 功能按钮 */
	TButton status, skillsBu, bagBu, task, map, fuben, save, juqing;
	TButton[] funAry = { status, skillsBu, bagBu, task, juqing, fuben, save, map };

	/** 战斗面板 */
	JPanel fightJpanel  ;
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
		setLayout(null);
		this.getContentPane().setBackground(Color.white);
		
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
		playerP = new JPanel();
		add(playerP);
		playerP.setOpaque(false);
		playerP.setBounds(0, 0, attrWidth, attrHeight);
		playerP.setLayout(null);
		
		tempL = new JLabel("啊啊啊啊") ;
		playerP.add(tempL);
		tempL.setBounds(inset, 0, 180, inset+18);
		tempL.setFont(new Font("隶书" ,Font.BOLD , 18));
		
		x = inset ;
		y = tempL.getHeight() ;
		
		/** 设置属性的标签和值 */
		for (int i = 0; i < attrAry.length; i++) {
			x = inset ;
			tempL = new JLabel(Constant.attrAry[i]+":");
			attrAry[i] = new JLabel("");
			tempL.setFont(font2);
			attrAry[i].setFont(font2);
			tempL.setForeground(Color.blue);
			attrAry[i].setForeground(Color.blue);
			playerP.add(tempL);
			playerP.add(attrAry[i]);
			tempL.setBounds(x, y, fontSize*3, fontSize+inset);
			x = tempL.getX()+tempL.getWidth() ;
			attrAry[i].setBounds(x,y,attrWidth-x-inset, fontSize+inset);
			y = tempL.getY()+tempL.getHeight() ;
		}
		/** 设置面板人物属性 */
		gameControl.setAttrValue(player);
		
		/** 设置人物属性的背景面板 */
		JLabel back = new JLabel();
		back.setOpaque(false);
		ImageIcon img = SUtils.loadImageIcon("/game/img/back/backC.png") ;
		back.setIcon(img);
		playerP.add(back);
		back.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		
		
		/** 右侧大小 545 */
		int rightWidth = 600 ;
		attrWidth = attrWidth - 8 ;
		/** 房间系统 */
		sceneP = new TTextPane(rightWidth, 100) ;
		add(sceneP);
		sceneP.setLocation(attrWidth, 0);
		sceneP.setBorder(BorderFactory.createTitledBorder("土地庙"));
		sceneP.append("这儿一间破落的土地庙,因年久失修，土地庙四周都已经长满了杂草，土地神像也是锈迹斑斑，甚至两条胳膊上都已经生出了一条条裂痕，好似随时都有可能掉落下来", 0);
		
		/** 战斗面板 */
		fightJpanel = new JPanel() ;
		fightJpanel.setLayout(null);
		add(fightJpanel);
		fightJpanel.setOpaque(false);
		fightJpanel.setBounds(attrWidth, sceneP.getY()+sceneP.getHeight(), rightWidth+200, 460);
		fightJpanel.setFont(font2);
		//fightJpanel.setBackground(Color.red);
		//fightJpanel.setBorder(BorderFactory.createEtchedBorder());
		gameControl.setFightJpanel(fightJpanel);
		
		/** 游戏信息 */
		infoP = new TTextPane( rightWidth, 180);
		JScrollPane jsc = infoP.getInstance();
		fightJpanel.add(jsc);
		jsc.setLocation(0, 0);
		
		/** 初始化交互面板，为了放置交互背景图片 */
		tianPanel = new JPanel() ;
		tianPanel.setLayout(null);
		fightJpanel.add(tianPanel);
		tianPanel.setBounds(0, jsc.getHeight(), 600, 198);
		
		/** 小地图 */
		mapP = new JPanel() ;
		mapP.setOpaque(false);
		mapP.setBounds(0, 0, 302, 198);
		mapP.setLayout(null);
		tianPanel.add(mapP);
		for (int j = 0; j < mapButton.length; j++) {
			 int x = j%3 ; 
			 int y = j/3 ;
			 mapButton[j] = new MButton("1", 15);
			 mapButton[j].setVisible(false);
			 mapButton[j].addMouseListener(mapButton[j]);
			 mapP.add(mapButton[j]);
			 mapButton[j].setBounds(19+(12+80)*x, 19+(12+45)*y, 80, 45);
		}
		
		/** 显示场景存在人物 */
		npcP = new JPanel() ;
		npcP.setOpaque(false);
		npcP.setLayout(null);
		npcP.setBounds(302 ,0, rightWidth-302, 99);
		tianPanel.add(npcP);
		/** 显示可以对人物进行的操作 */
		controlP = new JPanel() ;
		controlP.setOpaque(false);
		controlP.setLayout(null);
		controlP.setBounds(302, npcP.getY()+npcP.getHeight(), rightWidth-302, 99);
		tianPanel.add(controlP);
		//panelG.setBackground(Color.RED);
		
		JLabel tianBack = new JLabel();
		tianBack.setIcon(SUtils.loadImageIcon("/game/img/back/select2.png"));
		tianBack.setBounds(0, 0, tianPanel.getWidth(), tianPanel.getHeight());
		tianPanel.add(tianBack);
		
		/** 初始化弹窗监听器 */
		funListener = new FunListener(this,archive,player);
		gameControl.initFunFrame(funListener);
		keyMana = new KeyMana(funListener);
		gameControl.setKeyMana(keyMana);
		addKeyListener(keyMana);
		infoP.addKeyListener(keyMana);
		setFocusable(true);
		
		/** 功能 装备技能。。。  */
		functionP = new JPanel() ;
		add(functionP);
		functionP.setLayout(null);
		functionP.setBounds(0, attrHeight, attrWidth-8, 200);
		functionP.setBackground(Constant.colorAry[4]);
		//panelC.setBorder(BorderFactory.createEtchedBorder(1));
		for (int i = 0; i < funAry.length; i++) {
			funAry[i] = new TButton(Constant.funAry[i],1);
			funAry[i].addActionListener(funListener);
			functionP.add(funAry[i]);
			if(i%2==0){
				funAry[i].setLocation(6, 26*(i/2)+6);
			}else{
				funAry[i].setLocation(82, 26*(i/2)+6);
			}
			funAry[i].setSize(72, 24);
			funAry[i].addMouseListener(funAry[i]);
		}
		

		/** 功能 --结束  */
		
		/** 传入重要组件 */
		gameControl.sendPanel(playerP, sceneP, functionP, infoP, mapP, npcP, controlP);
		
		/** 测试面板 testPanel */
		testPanel = new TestPanel();
		add(testPanel);
		
		/** 主界面一些面板值的初始化 */
		gameControl.mainFrameInit();
		//SoundControl.ftMuc(21);
	}
	
	
	public void close(int type){
		funListener.close(type);
	}
	
	public static void main(String[] args) {
		Font font1 = new Font("楷体", Font.PLAIN, 10);
		UIManager.put("Label.foreground", Color.blue);
		UIManager.put("Label.font",font1); 
		System.setProperty("awt.useSystemAAFontSettings", "on"); 
		System.setProperty("swing.aatext", "true");
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
							
				}
			}
		}
		MainFrame frame = new MainFrame() ;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200, 100, 1028, 512);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public MButton getMapBu(int num){
		return mapButton[num-1];
	}
	public MButton[] getMapBuAry(){
		return mapButton ;
	}


	public void setMapBuHide() {
		for (int i = 0; i < mapButton.length; i++) {
			mapButton[i].setVisible(false);
			//mapButton[i].setFlag();
			mapButton[i].mouseExited();
		}
	}
	
	public void removeNpc(){
		npcP.removeAll();
	}
	
	public void showNpc(MButton bu){
		npcP.add(bu);
	}
	
	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public Archive getArchive() {
		return archive;
	}


	public void setArchive(Archive archive) {
		this.archive = archive;
	}


	public JLabel[] getAttrAry() {
		return attrAry;
	}


	public void setAttrAry(JLabel[] attrAry) {
		this.attrAry = attrAry;
	}
	
	
	/**
	 * 重新设置人物主界面的属性面板
	 */
	public void reloadPlayerAttr(){
		//nameValue,rankValue,stateValue,ExpValue,hpValue,mpValue,atkValue,defValue
		attrAry[0].setText(player.getName()); 
		attrAry[1].setText(player.getRank()+"");
		attrAry[2].setText(player.getState()+"");
		attrAry[3].setText(player.getCurExp()+"/"+player.getExp());
		attrAry[4].setText(player.getHp()+"");
		attrAry[5].setText(player.getMp()+"");
		attrAry[6].setText(player.getAttack()+"");
		attrAry[7].setText(player.getDefense()+"");
	}

	
}
