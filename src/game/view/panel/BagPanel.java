package game.view.panel;

import game.control.GameControl;
import game.entity.Equip;
import game.entity.Player;
import game.utils.Constant;
import game.view.button.TButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 背包显示面板
 * 
 * @author yilong22315
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
	private TButton putOn , resolve ; 
	
	private JPanel equipShow = null ;
	
	/** 当前选择的装备 */
	private Equip clickEq = null ;
	private int index = 0 ;
	
	private Player player = null ;
	private GameControl gameControl ;
	public BagPanel(JPanel superPanel,Player player) {
		this.superPanel = superPanel;
		this.player = player ;
		gameControl = GameControl.getInstance();
		/** 初始化 */
		setLayout(null);
		setOpaque(false);
		setSize(800, 300);
		setVisible(false);
		
		/** 查看当前装备信息的两个面板 */
		equipShow = new JPanel() ;
		equipShow.setLayout(null);
		equipShow.setBounds(310, 0, 600, 300);
		equipShow.setOpaque(false);
		//equipShow.setBackground(Color.red);
		add(equipShow);
		
		/** 当前穿戴的 */
		selectEp = new EquipInfoPanel();
		//selectEp.setVisible(false);
		equipShow.add(selectEp);
		selectEp.setBounds(0, 4, 170, 220);
		/** 当前点击的 */
		wearEp = new EquipInfoPanel();
		//wearEp.setVisible(false);
		equipShow.add(wearEp);
		wearEp.setBounds(176,4,170,220);
		
		/** 对装备进行操作的按钮 */
		putOn = new TButton("装备", 2);
		resolve = new TButton("分解", 2);
		putOn.addActionListener(eqAc);
		resolve.addActionListener(eqAc);
		putOn.setBounds(2, 226, 56, 22);
		resolve.setBounds(62, 226, 56, 22);
		/** 先隐藏 */
		//putOn.setVisible(false);
		//resolve.setVisible(false);
		equipShow.add(putOn);
		equipShow.add(resolve);
		
		
		/** 增加标签面板 */
		bagPanel = new JTabbedPane();
		bagPanel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("页签面板被改变!");
				clickEq = null ;
			}
		});
		bagPanel.setFont(new Font("楷体",Font.PLAIN,14));
		add(bagPanel);
		bagPanel.setBackground(Constant.colorAry[2]);
		bagPanel.setBounds(0, 0, 300, 199);
		/** 增加 */
		for (int i = 0; i < bagJPanelAry.length; i++) {
			/** 为背包分类制定具体面板 */
			bagJPanelAry[i] = new BagClassifyPanel(this,i);
			bagJPanelAry[i].setBackground(Color.white);
			bagJPanelAry[i].setLayout(null);
			bagPanel.addTab(Constant.bagClassifyAry[i], bagJPanelAry[i]);
		}
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


	public TButton getPutOn() {
		return putOn;
	}


	public void setPutOn(TButton putOn) {
		this.putOn = putOn;
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
						bagJPanelAry[0].equipBagShow();
					}else{
						bagJPanelAry[1].equipBagShow();
					}
					/** 刷新人物属性面板 */
					gameControl.reloadAttr();
					gameControl.reloadPlayerAttr();
				}else if(e.getActionCommand().equals("分解")){
					
				}
			}
			
		}
	};

	public Equip getClickEq() {
		return clickEq;
	}


	public void setClickEq(Equip clickEq) {
		this.clickEq = clickEq;
	}


	public void setIndex(int index) {
		this.index = index;
	}
	
	public void openBag(){
		bagJPanelAry[0].equipBagShow();
		bagJPanelAry[1].equipBagShow();
	}
	
}
