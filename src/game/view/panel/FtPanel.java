package game.view.panel;

import game.control.GameControl;
import game.entity.NPC;
import game.entity.Player;
import game.thread.AwakenThread;
import game.utils.Constant;
import game.view.TLabel;
import game.view.TTextPane;
import game.view.button.TButton;
import game.view.ui.DemoScrollBarUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 * 负责显示战斗过程的战斗面板
 * @author yilong22315
 *
 */
public class FtPanel extends JPanel{
	/*********************************************
	 * 	变量的声明
	 *********************************************/
	private GameControl gameControl = GameControl.getInstance();
	private static final long serialVersionUID = 1L;
	private JPanel info = null ;
	private Font font = new Font("隶书",Font.PLAIN,16);
	private Font font2 = new Font("隶书",Font.PLAIN,14) ;
	private Font font3 = new Font("华文新魏",Font.PLAIN,16) ;
	private Player player = null;
	private NPC npc = null ;
	private static Object obj = new Object();
	/** 显示敌我姓名的标签 */
	private TLabel myName , foeName ;
	private int inset = 49 ;
	/** 我的等级，敌人等级，敌人品质，我的攻击图标，敌人攻击图标 */
	private TLabel myRank , foeRank , foeType ;
	/** 我和敌人的血条法力条 */
	private static TLabel myXue,myFa,foeXue,foeFa,mySu,foeSu;
	/** 用来显示敌我血量的jLabel */
	private JLabel myTextHp , foeTextHp , myTextMp , foeTextMp  ;
	/** 显示攻击动作的两把剑 */
	private JLabel myJian , foeJian ;
	/** 我造成的伤害数值 */
	private JLabel myDamage , foeDamage ;
	
	private Graphics g = null ;
	private Map<String, Object> map = null ;
	//缓存的定时线程池
	private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8);
	/** 显示战斗信息文字的面板 */
	private TTextPane showInfo ;
	
	public FtPanel(TButton drugBu, JPanel contentPane,NPC npc) {
		this.setBounds(0,0,680, 517);
		this.setLayout(null);
		this.setOpaque(false);
		
		info = new JPanel();
		info.setOpaque(false);
		//info.setBackground(Color.red);
		info.setLayout(null);
		info.setBounds(0, 181, 545, 75);
		add(info);
		
		this.player = gameControl.getPlayer() ;
		this.npc = npc ;
		
		/** 设置敌我姓名 */
		myName = new TLabel(player.getName(),0);
		myName.setHorizontalAlignment(SwingConstants.CENTER);
		myName.setFont(font);
		info.add(myName);
		foeName = new TLabel(npc.getName(),0);
		foeName.setHorizontalAlignment(SwingConstants.CENTER);
		foeName.setFont(font);
		info.add(foeName);
		myName.setBounds(inset+28, 5, 98, 22);
		foeName.setBounds(myName.getX()+254, 5, 98, 22);
		
		/** 显示血条法力 */
		/** 玩家的血条 */
		myXue = new TLabel("", 4);
		//TLabel myXueK = new TLabel("", 5);
		//myXueK.setBounds(inset, foeName.getY()+foeName.getHeight(), 150, 13);
		//info.add(myXueK);
		//TLabel myFaK = new TLabel("", 5);
		//myFaK.setBounds(inset, myXueK.getY() + 13, 137, 13);
		//info.add(myFaK);
		myXue.setBounds(48, 47, 150, 11);
		info.add(myXue);
		/** 蓝条 */
		myFa = new TLabel("", 8);
		myFa.setBounds(myXue.getX()+1, myXue.getY() + 17, 127, 9);
		info.add(myFa);
		
		mySu = new TLabel("", 14);
		mySu.setBounds(myFa.getX(), info.getY()+info.getHeight()+3, 99, 7);
		add(mySu);
		
		/** 敌人血条 */
		foeXue = new TLabel("", 6);
		//TLabel foeXueK = new TLabel("", 7);
		//foeXueK.setBounds(info.getWidth() - inset - 152, myXueK.getY(), 150, 13);
		//info.add(foeXueK);
		//TLabel foeFaK = new TLabel("", 5);
		//info.add(foeFaK);
		//foeFaK.setBounds(info.getWidth() - inset - 139, foeXueK.getY() + 13, 137,13);
		foeXue.setBounds(myXue.getX()+myXue.getWidth()+118, myXue.getY(), 150, 11);
		info.add(foeXue);
		/** 敌人法力条 */
		foeFa = new TLabel("", 9);
		foeFa.setBounds(foeXue.getX()+21, foeXue.getY() + 17, 127,9);
		info.add(foeFa);
		
		foeSu = new TLabel("", 15);
		foeSu.setBounds(foeFa.getX()+foeFa.getWidth()-99, info.getY()+info.getHeight()+3, 99, 7);
		add(foeSu);
		
		/** 显示战斗动作的两把剑 */
		ImageIcon image1 = new ImageIcon("src/game/img/one/黑剑反.png");
		ImageIcon image2 = new ImageIcon("src/game/img/one/黑剑.png");
		myJian = new JLabel(image1) ;
		myJian.setSize(image1.getIconWidth(), image1.getIconHeight());
		foeJian = new JLabel(image2) ;
		foeJian.setSize(image2.getIconWidth(), image2.getIconHeight());
		myJian.setLocation(212, 30);
		foeJian.setLocation(212, 30);
		add(myJian);
		add(foeJian);
		
		/** 显示敌我伤害值的Label */
		myDamage = new JLabel() ;
		foeDamage = new JLabel() ;
		myDamage.setBounds(386, 80, 80, 20);
		foeDamage.setBounds(80, 80, 80, 20);
		add(myDamage);
		add(foeDamage);
		
		
		/** 设置等级显示 */
		myRank = new TLabel("lv:"+player.getRank()+"",0) ;
		myRank.setFont(font2);
		foeRank = new TLabel("lv:"+npc.getRank()+"",0) ;
		foeRank.setFont(font2);
		foeType = new TLabel("精英",0) ;
		myRank.setBounds(myName.getX(),myName.getY()+myName.getHeight(),60,20);
		foeRank.setBounds(foeName.getX(), foeName.getY()+foeName.getHeight(), 60, 20);
		foeType.setBounds(foeRank.getX()+foeRank.getWidth(), foeRank.getY(), 60, 20);
		foeType.setForeground(Constant.equipColor[npc.getType()]);
		
		info.add(myRank);
		info.add(foeRank);
		info.add(foeType);
		
		/** 显示敌我血量的JLabel */
		myTextHp = new JLabel(player.getCurHp()+"/"+player.getHp()) ;
		myTextHp.setFont(font3);
		myTextHp.setForeground(Color.white);
		myTextHp.setHorizontalAlignment(SwingConstants.CENTER);
		foeTextHp = new JLabel(npc.getCurHp()+"/"+npc.getHp()) ;
		foeTextHp.setForeground(Color.white);
		foeTextHp.setFont(font3);
		foeTextHp.setHorizontalAlignment(SwingConstants.CENTER);
		//foeShow.setOpaque(false);
		//myShow.setOpaque(false);
		myTextHp.setBounds(mySu.getX(), mySu.getY()+mySu.getHeight()+2, 80, 20);
		foeTextHp.setBounds(foeSu.getX()+foeSu.getWidth()-80, foeSu.getY()+foeSu.getHeight()+2, 80, 20);
		add(myTextHp);
		add(foeTextHp);
		
		/** 显示战斗信息的JTextPanel面板初始化 */
		showInfo = new TTextPane("我了个去",490, 180);
		showInfo.setOpaque(false);
		showInfo.setPreferredSize(new Dimension(490,600));
		JScrollPane jsc = showInfo.getInstance();
		jsc.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		jsc.setOpaque(false);
		jsc.getViewport().setOpaque(false);
		jsc.setBounds(34, 300, 490, 174);
		jsc.setBorder(BorderFactory.createEmptyBorder());
		add(jsc);
		jsc.setUI(new BasicScrollPaneUI());
		
		map = new HashMap<>();
		map.put("myXue", myXue);
		map.put("foeXue", foeXue);
		map.put("info", info);
		map.put("obj", obj);
		map.put("mtTextHp", myTextHp);
		map.put("foeTextHp", foeTextHp);
		map.put("myJian", myJian);
		map.put("foeJian", foeJian);
		map.put("myDamage", myDamage);
		map.put("foeDamage", foeDamage);
		map.put("mySu", mySu);
		map.put("foeSu", foeSu);
		
		/*JButton jb = new JButton("开始");
		jb.setBounds(200,30,100,24);
		info.add(jb);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startFt();
			}
		});*/
		
		JLabel back = new JLabel();
		image1 = new ImageIcon("src/game/img/back/FtBackA.png");
		back.setIcon(image1);
		add(back);
		back.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				startFt();
			}
		});
	}
	
	/** 战斗开始 */
	private void startFt() {
		font = new Font("隶书",Font.PLAIN,16);
		g = info.getGraphics();
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("游戏开始!", 210, 67);
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						info.repaint();
					}
				});
			}
		}, 2000, TimeUnit.MILLISECONDS);
		/** 攻击开始 
		 *  1:我方攻击敌方
		 *  2:敌方攻击我方
		 */
		AwakenThread awaken = new AwakenThread(null, map, showInfo, this);
		new Thread(awaken).start();	
	}
	
	/**
	 * 
	 * @param player
	 * @param npc
	 */
	public void reload(Player player,NPC npc){
		this.player = player ;
		this.npc = npc ;
		/** 重新设置敌我血量 */
		myName.setText(player.getName());
		myXue.setSize(150, 11);
		myFa.setSize(127,9);
		foeName.setText(npc.getName());
		foeXue.setSize(150, 11);
		foeFa.setSize(127,9);
		mySu.setSize(0, 9);
		foeSu.setSize(0, 9);
		
		myTextHp.setText(player.getCurHp()+"/"+player.getHp());
		foeTextHp.setText(npc.getCurHp()+"/"+npc.getHp());
		
		myRank.setText("lv:"+player.getRank());
		foeRank.setText("lv:"+npc.getRank());
		
		foeType.setText(Constant.npcTypeDes[npc.getType()]);
		
		/** 清空战斗信息 */
		showInfo.setText("");
		
		map = new HashMap<>();
		map.put("player", player);
		map.put("npc", npc);
		map.put("myXue", myXue);
		map.put("foeXue", foeXue);
		map.put("info", info);
		map.put("obj", obj);
		map.put("mtTextHp", myTextHp);
		map.put("foeTextHp", foeTextHp);
		map.put("myJian", myJian);
		map.put("foeJian", foeJian);
		map.put("myDamage", myDamage);
		map.put("foeDamage", foeDamage);
		map.put("mySu", mySu);
		map.put("foeSu", foeSu);
		
		startFt();
		
	}
	
	/** 测试方法 */
	public static void main(String[] args) {
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
							
				}
			}
		}
		JFrame j = new JFrame();
		JPanel p = new JPanel() ;
		p.setLayout(null);
		j.setContentPane(p);
		p.add(new FtPanel(null,null,null));
		j.setUndecorated(true);
		j.setBackground(new Color(0, 0, 0, 0));
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setBounds(200, 100, 680, 517);
		j.setVisible(true);
	}

	
}
