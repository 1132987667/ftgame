package game.view.panel;

import game.control.GameControl;
import game.entity.Equip;
import game.entity.Gong;
import game.entity.Player;
import game.utils.Constant;
import game.utils.SUtils;
import game.view.button.TButton;
import game.view.ui.TextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 背包显示面板
 * 
 */
public class BagPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTabbedPane bagPanel ;
	/** fightPanel */
	private JPanel superPanel;
	/** 兵器 防具 技能书 材料 具体显示面板 */
	private BagClassifyPanel weapons, armor, skillsLabel, materials;
	private BagClassifyPanel[] bagJPanelAry = {weapons,armor,skillsLabel,materials} ;
	/** 显示装备具体信息的面板 */
	private EquipInfoPanel selectEp , wearEp;
	/** 操作装备的按钮 */
	/** 装备 分解  出售  强化 锻造    */
	private TButton wear , resolve , crafting, salv ; 
	/** 操作功法的按钮 */
	private TButton use ;
	/** 公用按钮 */
	private TButton sell ;
	
	private TextField capacity, money, lingshi ;
	public TButton capacityAdd ;
	
	/** 当前点击和穿戴的装备 */
	private Equip clickEq, enterEq, wearEq ;
	/** 当前点击的功法 */
	private Gong clickGong ;
	
	private Player player = null ;
	private GameControl gameControl ;
	public BagPanel(JPanel superPanel,final Player player) {
		this.superPanel = superPanel;
		this.player = player ;
		gameControl = GameControl.getInstance();
		/** 初始化 */
		setLayout(null);
		setOpaque(false);
		setSize(800, 300);
		setVisible(true);
		
		/** 当前穿戴的装备显示面板 */
		selectEp = new EquipInfoPanel();
		selectEp.setVisible(false);
		selectEp.setBounds(310, 64, 170, 240);
		/** 当前点击的的装备面板 */
		wearEp = new EquipInfoPanel();
		wearEp.setVisible(false);
		wearEp.setBounds(310+174,64,170,240);
		add(wearEp);
		add(selectEp);
		
		
		/** 增加标签面板 */
		bagPanel = new JTabbedPane();
		/** 设置tab的样式 */
		SUtils.setUi(bagPanel);
		bagPanel.setOpaque(false);
		bagPanel.setFont(new Font("隶书",Font.PLAIN,14));
		add(bagPanel);
		bagPanel.setBounds(-5, 0, 304, 350);
		/** 增加4个具体背包面板 武器，防具，功法，材料 */
		for (int i = 0; i < bagJPanelAry.length; i++) {
			/** 为背包分类制定具体面板 */
			bagJPanelAry[i] = new BagClassifyPanel(this,i);
			bagPanel.addTab(Constant.bagClassifyAry[i], bagJPanelAry[i]);
			bagPanel.setForegroundAt(i, Color.white); 
		}
		bagPanel.addChangeListener(tabLin);
		
		/** 对装备进行操作的按钮 */
		wear = new TButton("装备", 2);
		resolve = new TButton("分解", 2);
		wear.addActionListener(eqAc);
		resolve.addActionListener(eqAc);
		wear.setBounds(54, 338, 56, 22);
		resolve.setBounds(114, 338, 56, 22);
		
		use = new TButton("使用", 2);
		
		capacity = new TextField("容量:10/40", 1);
		capacity.setBounds(234, 362, 80, 20);
		capacityAdd = new TButton("", 25);
		capacityAdd.setLocation(324, 360);
		
		money = new TextField("1000", 3);
		money.setBounds(80, 364, 60, 20);
		lingshi = new TextField("1000", 3);
		lingshi.setBounds(172, 364, 60, 20);
		
		superPanel.add(wear);
		superPanel.add(resolve);
		superPanel.add(use);
		superPanel.add(capacity);
		superPanel.add(capacityAdd);
		superPanel.add(money);
		superPanel.add(lingshi);
		
		/*JLabel back = new JLabel();
		ImageIcon image1 = new ImageIcon("src/game/img/back/bookA.png") ;
		back.setIcon(image1);
		back.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());
		equipShow.add(back);*/
	}


	public EquipInfoPanel getWearEp() {
		return wearEp;
	}
	public EquipInfoPanel getSelectEp() {
		return selectEp;
	}
	public TButton getWear() {
		return wear;
	}
	public void setWear(TButton wear) {
		this.wear = wear;
	}
	public TButton getResolve() {
		return resolve;
	}
	public void setResolve(TButton resolve) {
		this.resolve = resolve;
	}
	
	/**
	 * 对 穿上装备按钮 进行监听和实现
	 */
	ActionListener eqAc = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(clickEq!=null){
				if(e.getActionCommand().equals("装备")){
					System.out.println("正要穿上装备:"+clickEq.toString());
					int part = clickEq.getPart();
					Equip cur = player.getEquip(part);
					/** 重新设置装备 */
					wearEp.setEpInfo(clickEq, 2);
					selectEp.setEpInfo(player.getEquip(part), 1);
					System.out.println("当前装备:"+cur.toString());
					/** 先穿，再移，再加 */
					/** 当前点击的装备穿上 ,把它从背包内移走,把脱下来的装备放入背包 */
					player.wearEquip(clickEq, part);
					player.removeEquip(clickEq);
					player.obtainEquip(cur);
					if(part==0){
						bagJPanelAry[0].bagConShow();
					}else{
						bagJPanelAry[1].bagConShow();
					}
					/** 刷新人物属性面板 */
					gameControl.reloadAttr();
					gameControl.reloadPlayerAttr();
				}else if(e.getActionCommand().equals("分解")){
					
				}
			}
			
		}
	};

	ChangeListener tabLin = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			clickEq = null ;
			selectEp.setVisible(false);
			wearEp.setVisible(false);
			JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
			int index = tabbedPane.getSelectedIndex();
			if(index==0||index==1){
				/** 装备 */
			}else if(index==2){
				/** 功法 */
			}else if(index==3){
				/** 材料 */
			}
		}
	};
	
	
	public Equip getClickEq() {
		return clickEq;
	}


	public void setClickEq(Equip clickEq) {
		this.clickEq = clickEq;
	}


	public void openBag(){
		bagJPanelAry[0].bagConShow();
		bagJPanelAry[1].bagConShow();
	}


	public Equip getEnterEq() {
		return enterEq;
	}


	public void setEnterEq(Equip enterEq) {
		this.enterEq = enterEq;
	}
	
}
