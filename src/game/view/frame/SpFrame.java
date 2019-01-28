package game.view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import game.control.FightControl;
import game.control.GameControl;
import game.control.IoCtrl;
import game.entity.NPC;
import game.entity.Tasks;
import game.utils.C;
import game.view.TLabel;
import game.view.button.PicBu;
import game.view.panel.BagPanel;
import game.view.panel.BigMapP;
import game.view.panel.FtPanel;
import game.view.panel.FubenPanel;
import game.view.panel.GongPanel;
import game.view.panel.PlayerPanel;
import game.view.panel.TaskP;
import game.view.ui.Field;

/**
 * 特殊的弹窗，半透明
 * 副本 个人属性 等等
 * 背包
 * @author yilong22315
 * 
 */
public class SpFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	/** 父窗口 */
	private MainFrame parent;

	/** 用来设定窗体不规则样式的图片 */
	private ImageIcon image1;
	/** 获得游戏控制器 */
	private GameControl gameControl = GameControl.getInstance();
	/** 战斗控制器 */
	private FightControl fightControl = FightControl.getInstance();
	/** 主容器 */
	private JPanel contentPane;
	/** 拖动按钮和关闭按钮 */
	private PicBu drugBu,closeBu ;
	
	private BagPanel bagPanel ;
	
	private NPC npc ;
	
	/** 相关的面板 */
	private PlayerPanel playerPanel ;
	private FubenPanel fubenPanel ;
	private FubenPanel juqingPanel ;
	private JLabel back ;
	private FtPanel ftPanel ;
	/** 任务窗口 */
	private TaskP taskP ;
	private BigMapP bigMap ;
	
	/** 判断窗口类型的参数 */
	public int type ;
	
	/**
	 * @param parent
	 * @param type 决定这个窗口作用的不同
	 */
	public SpFrame(MainFrame parent, int type) {
		super();
		this.type = type ;
		this.parent = parent ;
		
		setBackground(new Color(0, 0, 0, 0));
		
		
		contentPane = new JPanel();
		contentPane.setOpaque(false);// 可视化编辑下会自动创建一个JPanel,也要将这个JPanel设为透明，
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.addKeyListener(gameControl.getKeyMana());
		
		closeBu = new PicBu("", 11);
		closeBu.setToolTipText("关闭");
		contentPane.add(closeBu);
		closeBu.setBounds(270, 13, 26, 26);
		closeBu.addActionListener(closeListener);
		drugBu = new PicBu("", 1);
		drugBu.setToolTipText("拖动");
		contentPane.add(drugBu);
		
		/** 得到不同背景图片和设置 */
		String imgPath = null;
		switch (type) {
		case C.Fuben://副本
			imgPath = "fubenBorder.png";
			initFuben(contentPane);
			break ;
		case C.State:// 人物属性和装备图
			imgPath = "attrAndGong.png";
			initPlayer(contentPane);
			break;
		case C.Task:
			imgPath = "taskbac.png";
			initTask();
			break;
		case C.JiangHu:
			imgPath = "fubenBorder.png";
			JiangHu(contentPane);
			break ;
		case C.Bag:
			imgPath = "bagBac.png";
			initBag();
		case C.Fight:
			imgPath = "bagBac.png";
			//init3(contentPane);
			break;
		case C.Map:
			initMap();
			break;
		default:
			break;
		}
		
		/** 设置显示图片 */
		if(imgPath!=null) {
			back = new JLabel("");
			image1 = IoCtrl.loadImageIcon("/game/img/back/"+imgPath);
			back.setIcon(image1);
			contentPane.add(back, BorderLayout.CENTER);
			back.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());
			setBounds(200, 100, image1.getIconWidth(), image1.getIconHeight());
		}else {
			if(type==C.Map) {
				setBounds(200, 100, bigMap.getWidth(), bigMap.getHeight());
			}
		}
		/** 背景图片标签发给人物属性面板 可以在人物面板中设置 */
		if(playerPanel!=null){
			playerPanel.setBack(back);
		}
		
		/** 添加父窗口控制监听器 */
		
		//setBounds(200, 100, 680, 517);
		
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
		drugBu.load(24);
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

	private void initTask() {
		closeBu.setBounds(268, 0, 26, 26);
		JLabel title = new JLabel("背包");
		title.setBounds(170, 12, 80, 20);
		title.setForeground(Color.white);
		title.setFont(new Font("隶书",0,18));
		drugBu.load(26);
		drugBu.setBounds(242, 0, 26, 26);
		contentPane.add(title);
		
		if(taskP==null){
			taskP = new TaskP(contentPane, gameControl.getPlayer());
			taskP.setLocation(0, 0);
			taskP.setSize(294, 440);
			contentPane.add(taskP);
		}
	}
	
	private void initMap() {
		closeBu.setBounds(574, 0, 26, 26);
		drugBu.load(26);
		drugBu.setBounds(548, 0, 26, 26);
		if(bigMap==null){
			bigMap = new BigMapP();
			bigMap.setLocation(0, 0);
			contentPane.add(bigMap);
		}
	}
	
	private void JiangHu(JPanel contentPane2) {
		drugBu.load(13);
		drugBu.setFont(new Font("幼圆",1,14));
		//t.setLocation(80, 50);
		//t.setFocusable(false);
		drugBu.setBounds(750, 40, 60, 42);
		closeBu.setBounds(726, 40, 26, 26);
		TLabel title = new TLabel("红  尘", 2);
		contentPane.add(title);
		title.setBounds(140, 48, 128, 30);//360
		if(juqingPanel==null){
			juqingPanel =new FubenPanel(C.JiangHu);
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
		drugBu.load(13);
		drugBu.setBounds(750, 40, 60, 42);
		closeBu.setBounds(726, 40, 26, 26);
		
		Field title = Field.defField("副  本");
		/** new TField("", 2) */
		contentPane.add(title);
		title.setBounds(140, 48, 128, 30);//360

		if(fubenPanel==null){
			fubenPanel =new FubenPanel(C.Fuben);
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
		drugBu.load(19);
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
		drugBu.load(23);
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
					SpFrame frame = new SpFrame(null, C.Task);
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
	
	/**
	 * 出现加载窗口
	 * @param type
	 */
	public void reload(int type) {
		if(parent!=null){
			parent.setEnabled(false);
		}
		if(type==C.Fuben){
			fubenPanel.initData();
		}else if(type==C.Task){
			//fubenPanel.initData();
		}else if(type==C.State){
			playerPanel.initData();
		}else if(type==C.Fight){
			npc = fightControl.getNpc();
			ftPanel.reload(gameControl.getPlayer(), npc);
		}else if(type==C.Bag){
			bagPanel.openBag();
		}else if(type==C.Map) {
			Point curPoint = new Point(gameControl.getScene().x, gameControl.getScene().y);
			bigMap.setTitle("大地图: "+gameControl.getScene().x+","+gameControl.getScene().y);
			bigMap.initMap(curPoint ,gameControl.getCurDituID());
		}
	}
	
	public void taskStart(Tasks task, NPC npc) {
		taskP.taskStart(task, npc);
	}
	
	public void taskComplete(Tasks task, NPC npc) {
		taskP.taskComplete(task, npc);
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}
	
}
