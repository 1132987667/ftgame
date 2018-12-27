package game.view.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import game.control.GameControl;
import game.control.UICtrl;
import game.entity.Gong;
import game.entity.Player;
import game.view.frame.SpFrame;
/**
 * 用来显示人物功法信息的面板
 * 当前装备功法，技能
 * 当前已学会功法，技能
 * @author yilong22315
 *
 */
public class GongPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GameControl gameControl = GameControl.getInstance();
	private Player player;

	private TempLabel temp;
	/** 间距 */
	private int inset = 4;
	/** 当前拥有经验值 */
	private TempLabel curExp;
	/** 当前装备的功法 */
	private JPanel curWear;
	/** 当前装备内外功名标签 */
	private TempLabel neiGong, waiGong;
	/** 当前装备的四种标签 */
	private TempLabel skill1, skill2, skill3, skill4;
	/** 标签面板 */
	private JTabbedPane skillPanel;
	/** 四种已习得功法 */
	private JPanel neiGongPanel = null, waiGongPanel = null, passiveSkill = null, activeSkill = null ;
	private JPanel[] ary = { neiGongPanel, waiGongPanel, passiveSkill,
			activeSkill };
	private String[] nameAry = { "内", "外", "被动", "主动" };
	/** layered层 */
	private static JLayeredPane layeredPanel;
	/** 用来显示要查看信息面板 */
	private static BagGongPanel gongInfo = null;

	private final int NeiGong = 1;
	private final int WaiGong = 2;
	private final int Skill = 3;
	
	private final int FieldWidth = 90 ;
	private final int FieldHeight = 20 ;

	public GongPanel(SpFrame superFrame) {
		System.out.println("初始化功法面板");
		setLayout(null);
		setOpaque(false);
		setBackground(Color.white);
		
		layeredPanel = superFrame.getLayeredPane();
		layeredPanel.setLayout(null);
		gongInfo = new BagGongPanel(BagGongPanel.DETAIL_GONG);
		layeredPanel.add(gongInfo);

		temp = new TempLabel("拥有经验:", 1, 0);
		temp.addMouseListener(ml);
		temp.setBounds(4, 40, 64, 20);
		curExp = new TempLabel("10000/100000", 1, 0);
		curExp.setBounds(temp.getWidth() + inset * 2, 40, 100, 20);
		add(temp);
		add(curExp);

		/** 当前装备的功法和技能 */
		curWear = new JPanel();
		curWear.setLayout(null);
		curWear.setOpaque(false);

		temp = new TempLabel("内功:", 2, 0);
		temp.setBounds(inset * 2, 22, 30, 20);
		curWear.add(temp);
		neiGong = new TempLabel("内功心法", 2, NeiGong);
		neiGong.addMouseListener(ml);
		neiGong.setBounds(temp.getX() + temp.getWidth(), temp.getY(), FieldWidth, FieldHeight);
		curWear.add(neiGong);

		temp = new TempLabel("外功:", 2, 0);
		temp.setBounds(inset * 2, neiGong.getHeight() + neiGong.getY(), 30, FieldHeight);
		curWear.add(temp);
		waiGong = new TempLabel("外功心法", 2, WaiGong);
		waiGong.addMouseListener(ml);
		waiGong.setBounds(temp.getX() + temp.getWidth(), neiGong.getHeight()
				+ neiGong.getY(), FieldWidth, 20);
		curWear.add(waiGong);

		/** 被动技能和主动技能1 */
		temp = new TempLabel("被动技:", 2, 0);
		temp.setBounds(inset * 2, waiGong.getY() + waiGong.getHeight(), 48, FieldHeight);
		curWear.add(temp);
		skill1 = new TempLabel("铜皮铁骨", 2, Skill);
		skill1.setBounds(temp.getX() + temp.getWidth(), waiGong.getY()
				+ waiGong.getHeight(), FieldWidth, 20);
		curWear.add(skill1);
		temp = new TempLabel("主动技1:", 2, 0);
		temp.setBounds(skill1.getX() + skill1.getWidth(), skill1.getY(), 48, FieldHeight);
		curWear.add(temp);
		skill2 = new TempLabel("铜皮铁骨", 2, Skill);
		skill2.setBounds(temp.getX() + temp.getWidth(), skill1.getY(), FieldWidth, FieldHeight);
		curWear.add(skill2);
		/** 主动技能2和主动技能3 */
		temp = new TempLabel("主动技2:", 2, 0);
		temp.setBounds(inset * 2, skill1.getY() + skill1.getHeight(), 48, FieldHeight);
		curWear.add(temp);
		skill3 = new TempLabel("铜皮铁骨", 2, Skill);
		skill3.setBounds(temp.getX() + temp.getWidth(),
				skill1.getY() + skill1.getHeight(), FieldWidth, FieldHeight);
		curWear.add(skill3);
		temp = new TempLabel("主动技3:", 2, 0);
		temp.setBounds(skill3.getX() + skill3.getWidth(), skill3.getY(), 48, FieldHeight);
		curWear.add(temp);
		skill4 = new TempLabel("铜皮铁骨", 2, Skill);
		skill4.setBounds(temp.getX() + temp.getWidth(), skill3.getY(), FieldWidth, FieldHeight);
		curWear.add(skill4);
		UICtrl.setBorder(curWear, "当前功法", Color.blue, new Font("隶书",
				Font.PLAIN, 16));
		curWear.setBounds(inset, curExp.getHeight() + curExp.getY() + inset,
				294, 120);
		add(curWear);

		/** 增加标签面板 */
		skillPanel = new JTabbedPane();
		skillPanel.setUI(new BasicTabbedPaneUI());
		//SUtils.setUi(skillPanel);
		skillPanel.setFont(new Font("楷体", Font.PLAIN, 12));
		add(skillPanel);
		skillPanel.setOpaque(false);
		//skillPanel.setBackground(Constant.colorAry[2]);
		skillPanel.setBounds(inset, curWear.getY() + curWear.getHeight(), 150,
				180);
		/** 增加 */
		for (int i = 0; i < ary.length; i++) {
			/** 为背包分类制定具体面板 */
			ary[i] = new JPanel();
			//ary[i].setBackground(Color.white);
			ary[i].setLayout(null);
			ary[i].setOpaque(false);
			skillPanel.addTab(nameAry[i], ary[i]);
		}
	}

	MouseListener ml = new MouseListener() {
		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {
			gongInfo.setVisible(false);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			TempLabel tl = (TempLabel) e.getSource();
			System.out.println(tl.effectType);
			gongInfo.setVisible(true);
			gongInfo.setLocation(tl.getX() + tl.getWidth()+300, tl.getY()
					+ tl.getHeight() / 2);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}
	};

	class TempLabel extends JLabel {
		int effectType = -1;
		private static final long serialVersionUID = 1L;
		private Font f1 = new Font("幼圆", Font.PLAIN, 14);
		private Font f2 = new Font("幼圆", Font.PLAIN, 12);
		
		public TempLabel(String title, int type, int effectType) {
			super(title);
			setForeground(Color.black);
			this.effectType = effectType;
			if (type == 1) {
				setFont(f1);
			} else {
				setFont(f2);
			}

		}
	}

	public void dataInit() {
		player = gameControl.getPlayer();
		Gong nGong = player.getCurUseNeiGong();
		Gong wGong = player.getCurUseWaiGong();
		if (nGong != null) {
			neiGong.setText(nGong.getName());
		}
		if (wGong != null) {
			waiGong.setText(wGong.getName());
		}
		Gong[] curUseSkill = player.getCurUseSkill();
		if (curUseSkill != null) {
			skill1.setText(curUseSkill[0].getName());
			skill2.setText(curUseSkill[1].getName());
			skill3.setText(curUseSkill[2].getName());
			skill4.setText(curUseSkill[3].getName());
		}
	}

	public static void main(String[] args) {
		JFrame j = new JFrame();
		layeredPanel = j.getLayeredPane();
		layeredPanel.setLayout(null);
		layeredPanel.add(gongInfo, 80);
		gongInfo.setVisible(false);
		gongInfo.setBackground(Color.red);
		gongInfo.setBounds(0, 0, 200, 100);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setBounds(100, 100, 304, 434);
		j.setResizable(false);
		j.setUndecorated(true);
		j.setBackground(new Color(0, 0, 0, 0));
		j.setVisible(true);
	}

}
