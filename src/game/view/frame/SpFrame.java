package game.view.frame;

import game.control.FightControl;
import game.control.GameControl;
import game.control.SoundControl;
import game.entity.NPC;
import game.utils.Constant;
import game.view.TLabel;
import game.view.button.TButton;
import game.view.panel.BagPanel;
import game.view.panel.FtPanel;
import game.view.panel.FubenPanel;
import game.view.panel.GongPanel;
import game.view.panel.PlayerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * 特殊的弹窗，半透明
 * 副本 个人属性 等等
 * 背包
 * @author yilong22315
 * 
 */
public class SpFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	/** 父窗口 */
	private MainFrame parent;

	/** 用来设定窗体不规则样式的图片 */
	private ImageIcon image1;
	/** 获得游戏控制器 */
	private GameControl gameControl = null;
	/** 战斗控制器 */
	private FightControl fightControl = FightControl.getInstance();
	/** 主容器 */
	private JPanel contentPane;
	/** 拖动按钮和关闭按钮 */
	private TButton drugBu,closeBu ;
	
	private BagPanel bagPanel ;
	
	private NPC npc ;
	
	/** 相关的面板 */
	private PlayerPanel playerPanel ;
	private FubenPanel fubenPanel ;
	private FubenPanel juqingPanel ;
	private JLabel back ;
	private FtPanel ftPanel ;
	public int type ;
	
	/**
	 * 
	 * @param parent
	 * @param type type不同决定这个窗口作用的不同
	 */
	public SpFrame(MainFrame parent, int type) {
		super();
		/** 得到游戏控制器 */
		gameControl = GameControl.getInstance();
		/** 取消容器装饰 */
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setOpaque(false);// 可视化编辑下会自动创建一个JPanel,也要将这个JPanel设为透明，
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		this.type = type ;
		contentPane.addKeyListener(gameControl.getKeyMana());
		
		closeBu = new TButton("", 11);
		contentPane.add(closeBu);
		closeBu.setBounds(270, 13, 26, 26);
		closeBu.addActionListener(closeListener);
		
		/** 得到不同背景图片和设置 */
		String imgPath = null;
		switch (type) {
		case Constant.Fuben://副本
			imgPath = "src/game/img/back/fubenBorder.png";
			initFuben(contentPane);
			break ;
		case Constant.State:// 人物属性和装备图
			imgPath = "src/game/img/back/attrAndGong.png";
			initPlayer(contentPane);
			break;
		case 3:
			imgPath = "";
			init3(contentPane);
			break;
		case Constant.JiangHu:
			imgPath = "src/game/img/back/fubenBorder.png";
			JiangHu(contentPane);
			break ;
		case Constant.Bag:
			imgPath = "src/game/img/back/bagBac.png";
			initBag();
		default:
			break;
		}
		
		/** 设置显示图片 */
		back = new JLabel("");
		image1 = new ImageIcon(imgPath);
		if(type!=3){
			back.setIcon(image1);
			contentPane.add(back, BorderLayout.CENTER);
			back.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());
			/** 背景图片标签发给人物属性面板 可以在人物面板中设置 */
			if(playerPanel!=null){
				playerPanel.setBack(back);
			}
		}
		
		this.parent = parent ;
		/** 添加父窗口控制监听器 */
		this.addWindowListener(this);
		if(type!=3){
			setBounds(200, 100, image1.getIconWidth(), image1.getIconHeight());
		}else{
			setBounds(200, 100, 680, 517);
		}
		
		/** 添加拖动功能 */
		setDragable();
		this.setVisible(false);
	}
	
	ActionListener closeListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			gameControl.funCloseInfo(type);
			gameControl.restore();
			gameControl.windowFlag="无";
		}
	};

	private void initBag() {
		JLayeredPane layeredPanel = getLayeredPane() ;
		layeredPanel.setLayout(null);
		gameControl.setLayeredPanel(layeredPanel);
		
		closeBu.setBounds(300, 11, 26, 26);
		JLabel title = new JLabel("背包");
		title.setBounds(170, 12, 80, 20);
		title.setForeground(Color.white);
		title.setFont(new Font("隶书",0,18));
		drugBu = new TButton("", 24);
		drugBu.setToolTipText("拖动");
		contentPane.add(drugBu);
		drugBu.setBounds(357, 32, 40, 64);
		contentPane.add(title);
		if(bagPanel==null){
			System.out.println("初始化背包面板");
			bagPanel = new BagPanel(contentPane, gameControl.getPlayer());
			bagPanel.setLocation(52, 42);
			bagPanel.setSize(800, 300);
			contentPane.add(bagPanel);
		}
	}

	private void JiangHu(JPanel contentPane2) {
		drugBu = new TButton("", 13);
		drugBu.setFont(new Font("幼圆",1,14));
		contentPane.add(drugBu);
		//t.setLocation(80, 50);
		//t.setFocusable(false);
		drugBu.setBounds(200, 40, 60, 42);
		closeBu.setBounds(726, 40, 26, 26);
		TLabel title = new TLabel("红尘", 2);
		contentPane.add(title);
		title.setBounds(140, 40, 128, 30);//360
		if(juqingPanel==null){
			juqingPanel =new FubenPanel(2);
			contentPane.add(juqingPanel);
		}else{
			juqingPanel.setVisible(true);
			juqingPanel.initData();
		}
		
	}

	/**********************************************************
	 * 初始化副本面板
	 * @param contentPane
	 */
	private void initFuben(JPanel contentPane) {
		drugBu = new TButton("", 13);
		drugBu.setFont(new Font("幼圆",1,14));
		contentPane.add(drugBu);
		//t.setLocation(80, 50);
		//t.setFocusable(false);
		drugBu.setBounds(200, 40, 60, 42);
		closeBu.setBounds(726, 40, 26, 26);
		TLabel title = new TLabel("副 本", 2);
		contentPane.add(title);
		title.setBounds(140, 40, 128, 30);//360
		if(fubenPanel==null){
			fubenPanel =new FubenPanel(1);
			contentPane.add(fubenPanel);
		}/*else{
			fubenPanel.setVisible(true);
			fubenPanel.initData();
		}*/
		
	}
	
	/**********************************************************
	 * 人物的属性和装备面板的初始化
	 * 人物功法面板初始化
	 * @param contentPane
	 */
	private void initPlayer(JPanel contentPane) {
		/** 添加特殊的拖动按钮 */
		drugBu = new TButton("", 19);
		drugBu.setToolTipText("拖动");
		contentPane.add(drugBu);
		drugBu.setBounds(8, 378, 64, 40);
		
		closeBu.setBounds(276, 1, 26, 26);
		TLabel title = new TLabel("人物属性", 0);
		contentPane.add(title);
		title.setBounds(10, 0, 128, 30);//360
		if(playerPanel==null){
			playerPanel= new PlayerPanel(drugBu,contentPane);
			contentPane.add(playerPanel);	
			playerPanel.initData();
			GongPanel gongPanel = new GongPanel(this) ;
			gongPanel.setBounds(playerPanel.getWidth(), 0, 300, 410);
			contentPane.add(gongPanel);	
		}else{
			playerPanel.setVisible(true);
			playerPanel.initData();
		}
	}
	
	
	/**********************************************************
	 * 战斗面板的初始化
	 * @param contentPane
	 */
	private void init3(JPanel contentPane) {
		npc = fightControl.getNpc();
		/** 添加特殊的拖动按钮 */
		drugBu = new TButton("", 23);
		drugBu.setToolTipText("拖动");
		contentPane.add(drugBu);
		drugBu.setBounds(580, 456, 104, 46);
		
		
		closeBu.setBounds(242, 84, 26, 26);
		/*TLabel title = new TLabel("战斗面板", 0);
		title.setForeground(Color.black);
		contentPane.add(title);
		title.setBounds(10, 0, 128, 30);//360*/		
		if(ftPanel==null){
			System.out.println(npc.toString());
			ftPanel= new FtPanel(drugBu,contentPane,npc);
			contentPane.add(ftPanel);	
		}
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpFrame frame = new SpFrame(null, 5);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Point loc = null;
	Point tmp = null;
	boolean isDragged = false;

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

	public void reload(int type) {
		if(parent!=null){
			parent.setEnabled(false);
		}
		if(type==Constant.Fuben){
			fubenPanel.initData();
		}else if(type==Constant.State){
			playerPanel.initData();
		}else if(type==Constant.Fight){
			npc = fightControl.getNpc();
			ftPanel.reload(gameControl.getPlayer(), npc);
		}else if(type==Constant.Bag){
			bagPanel.openBag();
		}
		
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}
	
}
