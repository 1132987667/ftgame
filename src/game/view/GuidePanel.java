package game.view;

import game.chapter.Chapter1;
import game.control.GameControl;
import game.control.SoundControl;
import game.entity.Archive;
import game.utils.ThreadUtils;
import game.view.button.TButton;
import game.view.frame.MainFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GuidePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private GameControl gameControl ;
	Chapter1 c = new Chapter1();
	int curMsgNum = 1 ;
	int width = 304 ;
	int height = 110 ;
	Font f = new Font("楷体",Font.PLAIN,14);
	JPanel jpp ;
	
	JTextArea text = null ;
	public boolean flag = false ;
	TButton sure , no ;
	JLabel name = null ;
	JTextField input = null ;
	
	private Archive theArchive ;
	
	public GuidePanel() {
		setLayout(null);
		gameControl = GameControl.getInstance();
		theArchive = gameControl.getArchive();
		
		//createNext();
		/** view设置 */
		this.setOpaque(true);
		this.setBounds(0, 0, 1024, 576);
		text = new JTextArea() ;
		text.setBackground(Color.black);
		text.setForeground(Color.white);
		text.setFont(new Font("楷体",Font.PLAIN,18));
		text.setAutoscrolls(true);
		text.setSize(600, 300);
		text.setBorder(null);
		/** 增加监听，点击则进行下一场景 */
		text.addMouseListener(next);
		text.setEditable(false);
		int x = (this.getWidth() - 600) / 2;
		int y = (this.getHeight() - 300) / 2 ;
		System.out.println("x:"+x+" , y:"+y);
		text.setLocation(x, y);
		add(text);
		createNext();
		
		/** 关闭游戏的按钮 */
		TButton close = new TButton("",11);
		add(close);
		close.addMouseListener(close);
		close.addActionListener(exit);
		close.setLocation(1000, 2);
		close.setSize(23, 23);
		
		/** 还需要一个返回首页的按钮 */
	}
	
	/** 选择是否进入游戏 */
	ActionListener choose = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("按下了"+e.getActionCommand()+"按钮 , "+input.getText());
			if(e.getActionCommand().equals("不接受")){
				curMsgNum++;
				/** 移出选择按钮 */
				text.remove(sure);
				text.remove(no);
				text.remove(name);
				text.remove(input);
				repaint();
				flag = true ;
				text.addMouseListener(next);
			}
			if (e.getActionCommand().equals("接受")) {
				if("".equals(input.getText())){
					return ;
				}
				/** 建立一个新的游戏角色 */
				gameControl.createNewPlayer(input.getText());
				
				getParent().setVisible(false);
				gameControl.hideEnterFrame();
				
				MainFrame frame = new MainFrame() ;
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(200, 100, 1028, 600);
				frame.setResizable(false);
				frame.setVisible(true);
			}
		}
	};
	
	/** 进行下一对白的展示 */
	public void createNext(){
		flag = false ;
		TextControl tc = new TextControl(c.get(curMsgNum)+"\n");
		ThreadUtils.execute(tc);
	}
	
	/** 离开游戏 */
	ActionListener exit = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameControl.exitGame();
		}
	};
	
	/** 用来控制文字逐渐输入的线程 */
	class TextControl implements Runnable{
		public String str ;
		public TextControl(String str) {
			this.str = str ;
		}
		@Override
		public void run() {
			flag = false ;
			text.setText("");
			char[] ary  = str.toCharArray();
			for (int i = 0; i < ary.length; i++) {
				if(i%4==0){
					SoundControl.jiemianMuc("keyDown");
				}
				text.append(ary[i]+"");
				//是textArea随内容移动
				//FightTextArea.autoFlow();
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(curMsgNum==6){
				//先移出监听
				text.removeMouseListener(next);
				sure = new TButton("接受", 7);
				sure.addMouseListener(sure);
				sure.setSize(100, 33);
				no = new TButton("不接受", 7);
				no.addMouseListener(no);
				no.setSize(100, 33);
				sure.setLocation(100,120);
				no.setLocation(240, 120);
				
				name = new JLabel("请输入你的名号:");
				name.setFont(new Font("幼圆", Font.PLAIN, 16));
				name.setSize(140,24);
				name.setForeground(Color.white);
				input = new JTextField();
				input.setSize(80,24);
				text.add(name);
				text.add(input);
				name.setLocation(100, 85);
				input.setLocation(240,85);
				text.add(sure);
				text.add(no);
				
				sure.addActionListener(choose);
				no.addActionListener(choose);
				validate();
				repaint();
			}else{
				curMsgNum++;
				flag = true ;
			}
			System.out.println(curMsgNum);
		}
		
	}
	
	MouseListener next = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(flag){
					createNext();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	};

	public Archive getTheArchive() {
		return theArchive;
	}

	public void setTheArchive(Archive theArchive) {
		this.theArchive = theArchive;
	}

	
}
