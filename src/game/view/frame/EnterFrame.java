package game.view.frame;

import game.control.GameControl;
import game.control.IoCtrl;
import game.entity.Archive;
import game.entity.Player;
import game.utils.ArchiveUtils;
import game.utils.C;
import game.utils.SUtils;
import game.view.TLabel;
import game.view.button.PicBu;
import game.view.panel.GuidePanel;
import game.view.ui.ImageIconCopy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 进入 
 * 选择存档的面板
 * @author yilong22315
 *
 */
public class EnterFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	/** 四个存档按钮 */
	private PicBu cd1,cd2,cd3,cd4 ;
	private PicBu[] buAry = {cd1,cd2,cd3,cd4} ;
	/** 存档按钮的大小 */
	private int width = 200 , height = 140 ;
	
	/** 游戏控制器 */
	private GameControl gameControl ;
	/** 当前选择的存档的名字 */
	private String archiveName ;
	
	public GuidePanel guidePanel ;
	private PicBu bu3 ;
	
	private ImageIcon img = null ;
	/** 背景面板 */
	public BackPanel backPanel = null ;
	private Archive theArchive = null ;
	
	public JPanel showInfo ;
	private TLabel name,rank,money,time;
	
	
	
	public EnterFrame(){
		setUndecorated(true);
		setLayout(null);
		setSize(new Dimension(C.Width,C.height));
		SUtils.setFrameCenter(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backPanel = new BackPanel();
		backPanel.setBounds(0, 0, C.Width, C.height);
		this.getContentPane().add(backPanel);
		setResizable(false);
		
		/** 创建游戏控制器 */
		gameControl = GameControl.getInstance();
		gameControl.setEnterFrame(this);
		
		/** 设置图标 */
		img = IoCtrl.loadImageIcon("/game/img/button/laugh.png");
		this.setIconImage(img.getImage());
		/** 设置背景 */
		//this.getContentPane().setVisible(true);
		//this.getContentPane().setBackground(Color.red);
		img = IoCtrl.loadImageIcon("/game/img/button/back.png");
		
		/** 新增关闭按钮 */
		PicBu close = new PicBu("",11);
		backPanel.add(close);
		close.addActionListener(exit);
		close.setLocation(1000, 2);
		close.setSize(23, 23);
		
		/** 存档按钮 */
		for (int i = 0; i < buAry.length; i++) {
			buAry[i] = new PicBu("存档"+(i+1),5);
			buAry[i].setActionCommand("archive"+i);
			if(i==0){
				buAry[i].setText("自动存档");
				buAry[i].setActionCommand("autoArchive");
			}
			buAry[i].addActionListener(selectArchive);
			buAry[i].setFocusable(false);
			buAry[i].setLocation(12, 100+i*80);
			buAry[i].setOpaque(false);
			backPanel.add(buAry[i]);
		}
		
		PicBu out = new PicBu("退出",7);
		out.setSize(100, 33);
		out.setLocation(12,430);
		out.addActionListener(exit);
		backPanel.add(out);
		PicBu in = new PicBu("确认",7);
		in.setSize(100, 33);
		in.setLocation(12,473);
		in.addActionListener(sureArchive);
		backPanel.add(in);
		bu3 = new PicBu("进入主页面",7);
		bu3.setSize(100, 33);
		bu3.setLocation(12,516);
		bu3.addActionListener(sureArchive);
		backPanel.add(bu3);
		
		showInfo =new JPanel() ;
		//showInfo.setVisible(false);
		showInfo.setOpaque(false);
		showInfo.setSize(340,490);
		showInfo.setLocation(360, 50);
		showInfo.setLayout(null);
		backPanel.add(showInfo);
		
		TLabel temp = null ;
		temp = new TLabel("名号:", 0);
		temp.setForeground(Color.white);
		temp.setBounds(80,120,50,24);
		showInfo.add(temp);
		name = new TLabel("", 0);
		name.setBounds(136,120,120,24);
		temp = new TLabel("等级:", 0);
		temp.setForeground(Color.white);
		temp.setBounds(80,150,50,24);
		showInfo.add(temp);
		rank = new TLabel("", 0);
		rank.setBounds(136,150,120,24);
		temp = new TLabel("金钱:", 0);
		temp.setForeground(Color.white);
		temp.setBounds(80,180,50,24);
		showInfo.add(temp);
		money = new TLabel("", 0);
		money.setBounds(136,180,120,24);
		temp = new TLabel("时间:", 0);
		temp.setForeground(Color.white);
		temp.setBounds(80,210,50,24);
		showInfo.add(temp);
		time = new TLabel("", 0);
		time.setBounds(136,210,120,24);
		showInfo.add(temp);
		
		showInfo.add(name);
		showInfo.add(rank);
		showInfo.add(money);
		showInfo.add(time);
		
		TLabel showBack = new TLabel("", 13);
		showBack.setBounds(0,0,340,490);
		showInfo.add(showBack);
		//add(jb);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	/** 选择存档,并点击了确定 */
	ActionListener sureArchive = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int status = gameControl.getSelectArchive(archiveName) ;
			/**跟新当前选择的存档的名字*/			
			gameControl.setArchiveName(archiveName);
			if (e.getSource()==bu3) {
				status = 3 ;
				gameControl.createNewPlayer("老哥");
			}
			/** 存档不存在，进行创建和新手指引 */
			if(status==0){
				//gameControl.setArchiveName(archiveName);
				JOptionPane.showConfirmDialog(null, "现在为您创建一个新的存档，并开始新的游戏！","提示", JOptionPane.PLAIN_MESSAGE);
				getContentPane().remove(backPanel);
				guidePanel = new GuidePanel() ;
				getContentPane().add(guidePanel);
				guidePanel.setBackground(Color.black);
				repaint();
			}else if(status==-1){/** 没有选择存档 */
				/*setVisible(false);
				MainFrame frame = new MainFrame() ;
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(200, 100, 1028, 512);
				frame.setResizable(false);
				frame.setVisible(true);*/
				JOptionPane.showConfirmDialog(null, "请选择一个存档","提示", JOptionPane.PLAIN_MESSAGE);
			}else{
				/** 传输存档 和 游戏角色 */
																																																Archive archive = ArchiveUtils.loadArchive(archiveName);
																																																gameControl.setArchive(archive);
																																																Player player = SUtils.conArcToPlayer(theArchive);
																																																gameControl.setPlayer(player);
																																																gameControl.setArchiveName(archiveName);
				//System.out.println("玩家:"+player.getArmorBag().size());
				//System.out.println("存档:"+archive.getEquipBag().size());
				//存档存在
				setVisible(false);
				MainFrame frame = new MainFrame() ;
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(200, 100, C.Width, C.height);
				frame.setResizable(false);
				frame.setVisible(true);
			}
		}
	}; 
	
	
	
	/** 离开游戏 */
	ActionListener exit = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameControl.exitGame();
		}
	};
	
	/** 选择对应的存档 */
	ActionListener selectArchive = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			archiveName = e.getActionCommand();
			theArchive =  ArchiveUtils.loadArchive(archiveName);
			showInfo.setVisible(true);
			if(theArchive==null){
				System.out.println("存档不存在");
				name.setText("无");
				rank.setText("无");
				money.setText("无");
				time.setText("无");
				showInfo.repaint();
			}else{
				System.out.println(theArchive.toString());
				name.setText(theArchive.getName());
				rank.setText(theArchive.getRank()+"");
				money.setText("0");
				time.setText(SUtils.transferLongToDate(theArchive.getTime()));
			}
			
			System.out.println("点击了存档:"+archiveName);
		}
	};
	
	class BackPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		public ImageIcon img = null ;
		public BackPanel() {
			img = IoCtrl.loadImageIcon("/game/img/button/back.png");
			this.setOpaque(true);
			setLayout(null);
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponents(g);
			g.drawImage(img.getImage(),0,0,this.getWidth(),this.getHeight(),this);
		}
	}
	
	public void start() {
		setVisible(true);
	}
	
}
