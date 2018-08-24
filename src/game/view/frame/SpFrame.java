package game.view.frame;

import game.control.FightControl;
import game.control.GameControl;
import game.control.SoundControl;
import game.entity.NPC;
import game.view.TLabel;
import game.view.button.TButton;
import game.view.panel.FtPanel;
import game.view.panel.FubenPanel;
import game.view.panel.PlayerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * 特殊的弹窗，半透明
 * 
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
	private TButton drugBu,close ;
	
	private NPC npc ;
	
	/** 相关的面板 */
	private PlayerPanel playerPanel ;
	private FubenPanel fubenPanel ;
	private JLabel back ;
	private FtPanel ftPanel ;
	
	/**
	 * 
	 * @param parent
	 * @param type type不同决定这个窗口作用的不同
	 */
	public SpFrame(final MainFrame parent, final int type) {
		super();
		/** 得到游戏控制器 */
		gameControl = GameControl.getInstance();
		/** 取消容器装饰 */
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		contentPane = new JPanel();
		contentPane.setOpaque(false);// 可视化编辑下会自动创建一个JPanel,也要将这个JPanel设为透明，
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		close = new TButton("", 11);
		contentPane.add(close);
		close.addMouseListener(close);
		close.setBounds(270, 13, 26, 26);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (type == 1) {
					SoundControl.jiemianMuc("closeMap"); 
					gameControl.append("你犹豫了会，还是决定先不去【",0);
					gameControl.append("副本", 1);
					gameControl.append("】！\n", 0);
				}else if(type == 2){
					gameControl.append("你不再查看自己的【",0);
					gameControl.append("状态", 1);
					gameControl.append("】！\n", 0);
				}else if(type == 3){
					gameControl.append("战斗结束！\n", 1);
				}
				gameControl.restore();
			}
		});
		
		/** 得到不同背景图片和设置 */
		String imgPath = null;
		switch (type) {
		case 1://副本
			imgPath = "src/game/img/back/水墨边框B.png";
			init1(contentPane);
			break ;
		case 2:// 人物属性和装备图
			imgPath = "src/game/img/back/diao.png";
			init2(contentPane);
			break;
		case 3:
			imgPath = "";
			init3(contentPane);
			break;
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
		if(parent!=null){
			parent.setEnabled(false);
		}
		/** 添加父窗口控制监听器 */
		this.addWindowListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		if(type!=3){
			setBounds(200, 100, image1.getIconWidth(), image1.getIconHeight());
		}else{
			setBounds(200, 100, 680, 517);
		}
		
		/** 添加拖动功能 */
		setDragable();
	}

	/**********************************************************
	 * 初始化副本面板
	 * @param contentPane
	 */
	private void init1(JPanel contentPane) {
		drugBu = new TButton("", 13);
		drugBu.setFont(new Font("幼圆",1,14));
		contentPane.add(drugBu);
		//t.setLocation(80, 50);
		//t.setFocusable(false);
		drugBu.setBounds(200, 40, 60, 42);
		close.setBounds(726, 40, 26, 26);
		TLabel title = new TLabel("副 本", 2);
		contentPane.add(title);
		title.setBounds(140, 40, 128, 30);//360
		if(fubenPanel==null){
			fubenPanel =new FubenPanel();
			contentPane.add(fubenPanel);
		}else{
			fubenPanel.setVisible(true);
			fubenPanel.initData();
		}
		
	}
	
	/**********************************************************
	 * 人物的属性和装备面板的初始化
	 * @param contentPane
	 */
	private void init2(JPanel contentPane) {
		/** 添加特殊的拖动按钮 */
		drugBu = new TButton("", 19);
		drugBu.setToolTipText("拖动");
		contentPane.add(drugBu);
		drugBu.setBounds(8, 378, 64, 40);
		
		close.setBounds(276, 1, 26, 26);
		TLabel title = new TLabel("人物属性", 0);
		contentPane.add(title);
		title.setBounds(10, 0, 128, 30);//360
		if(playerPanel==null){
			playerPanel= new PlayerPanel(drugBu,contentPane);
			contentPane.add(playerPanel);	
			playerPanel.initData();
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
		
		
		close.setBounds(242, 84, 26, 26);
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
					SpFrame frame = new SpFrame(null, 1);
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
		drugBu.addMouseListener(new java.awt.event.MouseAdapter() {
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
		if(type==1){
			fubenPanel.initData();
		}else if(type==2){
			playerPanel.initData();
		}else if(type==3){
			npc = fightControl.getNpc();
			ftPanel.reload(gameControl.getPlayer(), npc);
		}
		
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}
	
}
