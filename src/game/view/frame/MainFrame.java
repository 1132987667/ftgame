package game.view.frame;

import game.control.GameControl;
import game.control.SoundControl;
import game.entity.Archive;
import game.entity.Player;
import game.entity.scene.Tudimiao;
import game.listener.GreatListener;
import game.utils.Constant;
import game.utils.SUtils;
import game.utils.UIs;
import game.view.TTextPane;
import game.view.button.MButton;
import game.view.button.TButton;
import game.view.panel.BagClassifyPanel;
import game.view.panel.BagPanel;
import game.view.panel.EquipInfoPanel;
import game.view.panel.TestPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class MainFrame extends JFrame{
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
	private JPanel panelA ,panelC  ,panelE ,panelF ,panelG;
	private TTextPane panelB,panelD ;
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
	TButton status , skillsBu , bagBu , task , map ,fuben ,save ; 
	TButton[] funAry = { status , skillsBu , bagBu , task ,map ,fuben,save  }; 
	
	/** 战斗面板 */
	JPanel fightJpanel  ;
	/** 背包面板 */
	BagPanel bag ;
	
	
	TestPanel testPanel ;
	
	/** 四种物品 */
	JPanel weapons,armor,skillsLabel,materials;
	
	/** 弹窗监听器 */
	private GreatListener greatListener = null ;
	/** 控制游戏进行 */
	private GameControl gameControl = null ;
	
	/** 组件的x,y坐标 */
	private int x , y ;
	private int inset = 8 ;
	
	public int fontSize = 14 ;
	Font font1 = new Font("楷体", Font.PLAIN, 14);
	Font font2 = new Font("隶书", Font.BOLD, 14);
	Font font3 = new Font("幼圆", Font.PLAIN, 14);
	
	/** 当前角色和存档 */
	private Player player ;
	private Archive archive ;
	
	/** 再使用背包功能时 */
	EquipInfoPanel selectEp ;
	
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
		panelA = new JPanel();
		add(panelA);
		panelA.setOpaque(false);
		panelA.setBounds(0, 0, attrWidth, attrHeight);
		panelA.setLayout(null);
		
		tempL = new JLabel("啊啊啊啊") ;
		panelA.add(tempL);
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
			tempL.setForeground(Color.white);
			attrAry[i].setForeground(Color.white);
			panelA.add(tempL);
			panelA.add(attrAry[i]);
			tempL.setBounds(x*2, y, fontSize*3, fontSize+inset);
			x = tempL.getX()+tempL.getWidth() ;
			attrAry[i].setBounds(x,y,attrWidth-x-inset, fontSize+inset);
			y = tempL.getY()+tempL.getHeight() ;
		}
		/** 设置面板人物属性 */
		gameControl.setAttrValue(player);
		
		/** 设置人物属性的背景面板 */
		JLabel back = new JLabel();
		back.setOpaque(false);
		ImageIcon img = new ImageIcon("src/game/img/back/backC.png") ;
		back.setIcon(img);
		panelA.add(back);
		back.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		
		
		/** 右侧大小 545 */
		int rightWidth = 600 ;
		attrWidth = attrWidth - 8 ;
		/** 房间系统 */
		panelB = new TTextPane(rightWidth, 100) ;
		add(panelB);
		panelB.setLocation(attrWidth, 0);
		Tudimiao tudimiao = new Tudimiao();
		panelB.setBorder(BorderFactory.createTitledBorder(tudimiao.name));
		panelB.append(tudimiao.des, 0);
		
		/** 战斗面板 */
		fightJpanel = new JPanel() ;
		fightJpanel.setLayout(null);
		add(fightJpanel);
		fightJpanel.setBounds(attrWidth, panelB.getY()+panelB.getHeight(), rightWidth+200, 460);
		fightJpanel.setFont(font2);
		fightJpanel.setBackground(Constant.colorAry[2]);
		//fightJpanel.setBorder(BorderFactory.createEtchedBorder());
		gameControl.setFightJpanel(fightJpanel);
		
		/** 游戏信息 */
		panelD = new TTextPane( rightWidth, 180);
		JScrollPane jsc = panelD.getInstance();
		fightJpanel.add(jsc);
		jsc.setLocation(0, 0);
		
		/** 小地图 */
		panelE = new JPanel() ;
		panelE.setOpaque(false);
		panelE.setBounds(0, jsc.getHeight(), 302, 198);
		panelE.setLayout(null);
		fightJpanel.add(panelE);
		for (int j = 0; j < mapButton.length; j++) {
			 int x = j%3 ; 
			 int y = j/3 ;
			 mapButton[j] = new MButton("1", 15);
			 mapButton[j].setVisible(false);
			 mapButton[j].addMouseListener(mapButton[j]);
			 panelE.add(mapButton[j]);
			 mapButton[j].setBounds(19+(12+80)*x, 19+(12+45)*y, 80, 45);
		}
		
		/** 显示场景存在人物 */
		panelF = new JPanel() ;
		panelF.setLayout(null);
		panelF.setBounds(302, jsc.getHeight(), rightWidth-302, 99);
		fightJpanel.add(panelF);
		/** 显示可以对人物进行的操作 */
		panelG = new JPanel() ;
		panelG.setLayout(null);
		panelG.setBounds(302, panelF.getY()+panelF.getHeight(), rightWidth-302, 99);
		fightJpanel.add(panelG);
		panelG.setBackground(Color.RED);
		
		/** 初始化弹窗监听器 */
		greatListener = new GreatListener(this,archive,player);
		
		/** 功能 装备技能。。。  */
		panelC = new JPanel() ;
		add(panelC);
		panelC.setLayout(null);
		panelC.setBounds(0, attrHeight, attrWidth-8, 200);
		panelC.setBackground(Constant.colorAry[4]);
		//panelC.setBorder(BorderFactory.createEtchedBorder(1));
		for (int i = 0; i < funAry.length; i++) {
			funAry[i] = new TButton(Constant.funAry[i],1);
			funAry[i].addActionListener(greatListener);
			panelC.add(funAry[i]);
			if(i%2==0){
				funAry[i].setLocation(6, 26*(i/2)+6);
			}else{
				funAry[i].setLocation(82, 26*(i/2)+6);
			}
			funAry[i].setSize(72, 24);
			funAry[i].addMouseListener(funAry[i]);
		}
		

		/** 功能 --结束  */
		
		/** 背包 --开始  */
		bag = new BagPanel(fightJpanel,player) ;
		bag.setLocation(0, panelD.getHeight());
		fightJpanel.add(bag);
		/** 背包 --结束  */
		
		/** 传入重要组件 */
		gameControl.sendPanel(panelA, panelB, panelC, panelD, panelE, panelF, panelG);
		
		/** 测试面板 testPanel */
		testPanel = new TestPanel();
		add(testPanel);
		
		/** 主界面一些面板值的初始化 */
		gameControl.mainFrameInit();
		//SoundControl.ftMuc(21);
	}
	
	public void close(int type){
		greatListener.close(type);
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
		
		UIs.setUI();
		
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
			mapButton[i].setFlag();
			mapButton[i].mouseExited();
		}
	}
	
	public void removeNpc(){
		panelF.removeAll();
	}
	
	public void showNpc(MButton bu){
		panelF.add(bu);
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
	
	/** 隐藏战斗页面，显示及背包页面  */
	public void openBag(){
		if(panelE.isVisible()){
			panelE.setVisible(false);
		}
		if(panelF.isVisible()){
			panelF.setVisible(false);
		}
		if(panelG.isVisible()){
			panelG.setVisible(false);
		}
		if(!bag.isVisible()){
			bag.setVisible(true);
		}
		bag.openBag();
	}
	
	
	public void beganExplore(){
		if(bag.isVisible()){
			bag.setVisible(false);
		}
		if(!panelE.isVisible()){
			panelE.setVisible(true);
		}
		if(!panelF.isVisible()){
			panelF.setVisible(true);
		}
		if(!panelG.isVisible()){
			panelG.setVisible(true);
		}
	}
	
	/**
	 * 初始化人物主界面的属性面板
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

	public void closeBag() {
		/*if(panelE.isVisible()){
			panelE.setVisible(false);
		}
		if(panelF.isVisible()){
			panelF.setVisible(false);
		}
		if(panelG.isVisible()){
			panelG.setVisible(false);
		}*/
		if(bag.isVisible()){
			bag.setVisible(false);
		}
		if(!panelE.isVisible()){
			panelE.setVisible(true);
		}
		if(!panelF.isVisible()){
			panelF.setVisible(true);
		}
		if(!panelG.isVisible()){
			panelG.setVisible(true);
		}
	}
	
}
