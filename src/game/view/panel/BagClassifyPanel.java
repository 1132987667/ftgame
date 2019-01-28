package game.view.panel;

import game.control.GameControl;
import game.entity.Equip;
import game.entity.Gong;
import game.entity.Player;
import game.listener.BagActionListener;
import game.listener.BagMouseListener;
import game.utils.C;
import game.utils.SUtils;
import game.view.button.PicBu;
import game.view.ui.DemoScrollBarUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *index 0 武器背包
 *index 1 防具背包
 *index 2 功法背包
 *index 3 材料背包
 */
public class BagClassifyPanel extends JPanel {
	private static final long serialVersionUID = 2792025876381447305L;
	/** 长高间距 */
	private int gridWidth = 60;
	private int gridHeight = 20;
	private int spec = 6;
	// 22 滑轮宽度 父类容器
	private BagPanel superPanel;
	
	private JButton n1, n2, n3, n4;
	/** 名字 种类 品质 数量 可以排序 */
	JButton[] jbAry = { n1, n2, n3, n4 };
	/** 内容面板 */
	private JPanel goodsContent;
	private JScrollPane scrollPane ;
	private GameControl gameControl = GameControl.getInstance();
	private Font font1 = new Font("隶书",Font.PLAIN,13);
	private Font font2 = new Font("隶书",Font.PLAIN,16);
	private Player player ;
	private BagActionListener bgac ;
	
	private String[] gongSortCase = {"名字","境界","品质","数量"} ;
	
	/** 当前背包存放什么 */
	private int index; 
	
	/** 
	 * 背包显示面板的具体构造类
	 * @param cur
	 * @param superPanel
	 */
	public BagClassifyPanel(BagPanel superPanel,int index) {
		this.superPanel = superPanel ;
		this.index = index ;
		setLayout(null);
		setOpaque(false);
		
		/** 设置排序按钮 */
		for (int i = 0; i < jbAry.length; i++) {
			if(index!=2){
				jbAry[i] = new JButton(C.equipAttrAry[i]);
			}else if(index==2){
				jbAry[i] = new JButton(gongSortCase[i]);
			}
			if(i==0){
				jbAry[i].setBounds(spec , 2, 98, gridHeight);
			}else{
				jbAry[i].setBounds(spec + gridWidth* i + 38 , 2, gridWidth, gridHeight);
			}
			jbAry[i].setFocusable(false);
			jbAry[i].setBackground(Color.black);
			jbAry[i].setForeground(Color.white);
			jbAry[i].addActionListener(ac);
			jbAry[i].setFont(font1);
			add(jbAry[i]);
		}
		
		/** 背包放进滚动面板中 */
		scrollPane = new JScrollPane();
		/** 自定义滑轮样式 */
		scrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		scrollPane.setBounds(2, 19, 300, 250);
		scrollPane.setPreferredSize(new Dimension(300, 250));
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		SUtils.setEmptyBorder(scrollPane);
		init();
	}
	
	/**设置 */
	public void init(){
		/** 根据背包内物品数量决定背包高度 */
		player = gameControl.getPlayer();
		bagConShow();
	}
	
	public void bagConShow(){
		System.out.println("正在重新布置背包！");
		if(goodsContent!=null){
			scrollPane.remove(goodsContent);
		}
		/** 重新加载背包显示内容面板 */
		goodsContent = new JPanel();
		goodsContent.setOpaque(false);
		goodsContent.setLayout(null);
		scrollPane.setViewportView(goodsContent);
		SUtils.setEmptyBorder(goodsContent);
		if(index==0||index==1){
			reloadEquipBag();
		}else if(index==2){
			reloadGongBag();
		}else if(index==3){
			
		}
		/** 触发调用以重新绘制组件认为“脏区域”的内容 */
		scrollPane.revalidate();
		/** 重绘 */
		scrollPane.repaint();
	}
	
	/**
	 * 查询设置背包内容
	 */
	private void reloadGongBag() {
		List<Gong> curBag = player.getGongBag();
		if(curBag==null){
			return ;
		}
		System.out.println("加载功法背包，数量为:"+curBag.size());
		Collections.sort(curBag);
		int size = curBag==null?0:curBag.size();
		if(size<=7){
			System.out.println(goodsContent);
			goodsContent.setPreferredSize(new Dimension(246, 140));
		}else{
			int height = 200 + (size-7)*21 ;
			goodsContent.setPreferredSize(new Dimension(246, height));
		}
		BagMouseListener bagml = new BagMouseListener(player);
		/** 通过页数得到当前背包物品 */
		if(curBag.size()<=0){
			return ;
		}
		Gong gong = null ;
		PicBu tempBu;
		JLabel tempField;
		/** 先设置第一个，再设置其他 */
		for (int i = 0; i < curBag.size(); i++) {
			gong = curBag.get(i);
			tempBu = new PicBu(gong.getName(),16);
			/** ActionCommand为在当前功法的ID */
			tempBu.setActionCommand(gong.getId());
			/** 设置组件边距 */
			tempBu.setBorder(new EmptyBorder(0, 0, 0, 0));
			tempBu.setHorizontalAlignment(SwingConstants.LEFT);
			/** 设置按钮透明 */
			tempBu.setFont(font2);
			tempBu.setContentAreaFilled(false);
			tempBu.setOpaque(true);
			tempBu.setBackground(new Color(57, 47, 65));
			/** 设置字体颜色 */
			tempBu.setForeground(C.equipColor[gong.getType()]);
			//tempBu.addActionListener(bgac);
			tempBu.addMouseListener(bagml);
			tempBu.setFocusable(false);
			tempBu.setBounds(4, i*20+5, 98, 20);
			goodsContent.add(tempBu);
			for (int j = 1; j < 4; j++) {
				String tempStr = SUtils.getGongField(j, gong);
				tempField = new JLabel(tempStr);
				tempField.setFont(font2);
				tempField.setBorder(new EmptyBorder(0, 4, 0, 0));
				tempField.setForeground(new Color(250,255,240));
				tempField.setHorizontalTextPosition(SwingConstants.CENTER);
				tempField.setBounds(2+j*60+40, tempBu.getY(), 60, 20);
				goodsContent.add(tempField);
			}
		}
	}

	/**
	 * 重新加载装备背包
	 * 包括 武器和装备
	 */
	public void reloadEquipBag(){
		/** 再次进入将上次选择的装备设置为空 */
		superPanel.setClickEq(null);
		
		/** 得到当前背包内物品并设置背包的大小 */
		List<Equip> curBag = null ;
		if(index==0){
			 curBag = player.getWeaponBag();
			 //System.out.println("得到背包内武器,数量是:"+curBag.size());
		}else{
			 curBag = player.getArmorBag();
			 //System.out.println("得到背包内防具,数量是:"+curBag.size());
		}
		Collections.sort(curBag);
		int size = curBag==null?0:curBag.size();
		if(size<=7){
			goodsContent.setPreferredSize(new Dimension(246, 140));
		}else{
			int height = 200 + (size-7)*21 ;
			goodsContent.setPreferredSize(new Dimension(246, height));
		}
		bgac = new BagActionListener(superPanel,index,player);
	
		/** 通过页数得到当前背包物品 */
		List<Equip> equipList = player.getEquipBag(index);
		if(equipList==null){
			return ;
		}
		Equip equip = null ;
		PicBu tempBu;
		JLabel tempField;
		System.out.println("背包里物品的数量:"+equipList.size());
		/** 先设置第一个，再设置其他 */
		for (int i = 0; i < equipList.size(); i++) {
			equip = equipList.get(i);
			tempBu = new PicBu(equip.getName(),16);
			/** ActionCommand为在当前list中的序号 */
			tempBu.setActionCommand(i+"");
			/** 设置组件边距 */
			tempBu.setBorder(new EmptyBorder(0, 0, 0, 0));
			tempBu.setHorizontalAlignment(SwingConstants.LEFT);
			/** 设置按钮透明 */
			tempBu.setFont(font2);
			tempBu.setContentAreaFilled(false);
			tempBu.setOpaque(true);
			tempBu.setBackground(new Color(57, 47, 65));
			/** 设置字体颜色 */
			tempBu.setForeground(C.equipColor[equip.getType()]);
			tempBu.addActionListener(bgac);
			tempBu.addMouseListener(equipMl);
			tempBu.setFocusable(false);
			tempBu.setBounds(4, i*20+5, 98, 20);
			goodsContent.add(tempBu);
			for (int j = 1; j < 4; j++) {
				String tempStr = SUtils.getEquipField(j, equip);
				tempField = new JLabel(tempStr);
				tempField.setFont(font2);
				tempField.setBorder(new EmptyBorder(0, 4, 0, 0));
				tempField.setForeground(new Color(250,255,240));
				tempField.setHorizontalTextPosition(SwingConstants.CENTER);
				tempField.setBounds(2+j*60+40, tempBu.getY(), 60, 20);
				goodsContent.add(tempField);
			}
		}
	}
	
	MouseAdapter equipMl = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) { 
			PicBu bu = (PicBu) e.getSource() ;
			String curIndex  = bu.getActionCommand();
			
			List<Equip> eqList = player.getEquipBag(index);
			Equip selectEquip = eqList.get(SUtils.conStrtoInt(curIndex));
			superPanel.setEnterEq(selectEquip);
			
			int part = selectEquip.getPart();
			Equip wearEquip = player.getEquip(part);
			
			superPanel.getSelectEp().setEpInfo(selectEquip,1);
			superPanel.getSelectEp().setVisible(true);
			superPanel.getWearEp().setVisible(true);
			superPanel.getWearEp().setEpInfo(wearEquip,2);
			
		};
		public void mouseExited(MouseEvent e) {
			superPanel.getSelectEp().setVisible(false);
			superPanel.getWearEp().setVisible(false);
		};
	};
	
	/** 点击重新排序 */
	ActionListener ac = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*goodsContent.setPreferredSize(new Dimension(246, 400));
			goodsContent.repaint();
			scrollPane.repaint();
			System.out.println("点击");*/
		}
	};
	
}
