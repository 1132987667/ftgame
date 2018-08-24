package game.view.panel;

import game.control.GameControl;
import game.entity.Equip;
import game.entity.Player;
import game.listener.BagActionListener;
import game.utils.Constant;
import game.utils.SUtils;
import game.view.button.TButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * 为武器,防具,技能书,防具等具体物品显示面板
 * @author yilong22315
 *
 */
public class BagClassifyPanel extends JPanel {
	private static final long serialVersionUID = 2792025876381447305L;
	/** 长高间距 */
	private int gridWidth = 60;
	private int gridHeight = 20;
	private int spec = 6;
	// 22 滑轮宽度
	private BagPanel superPanel;
	
	private JButton n1, n2, n3, n4;
	/** 名字 种类 品质 数量 可以排序 */
	JButton[] jbAry = { n1, n2, n3, n4 };
	/** 内容面板 */
	private JPanel goodsContent;
	private JScrollPane scrollPane ;
	private GameControl gameControl = GameControl.getInstance();
	private Font font = new Font("宋体",Font.PLAIN,13);
	private Font f = new Font("隶书",Font.PLAIN,16);
	private Player player ;
	private BagActionListener bgac ;
	
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
		/** 设置排序按钮 */
		for (int i = 0; i < jbAry.length; i++) {
			jbAry[i] = new JButton(Constant.equipAttrAry[i]);
			if(i==0){
				jbAry[i].setBounds(spec , 2, 98, gridHeight);
			}else{
				jbAry[i].setBounds(spec + gridWidth* i + 38 , 2, gridWidth, gridHeight);
			}
			jbAry[i].setFocusable(false);
			jbAry[i].setBackground(new Color(76, 141, 176));
			jbAry[i].setForeground(Color.white);
			jbAry[i].addActionListener(ac);
			jbAry[i].setFont(font);
			add(jbAry[i]);
		}
		
		/** 背包放进滚动面板中 */
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(   
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		scrollPane.setBounds(2, 24, 300, 148);
		
		init();
		
	}
	
	/**设置 */
	public void init(){
		/** 根据背包内物品数量决定背包高度 */
		player = gameControl.getPlayer();
		if(index==0||index==1){
			equipBagShow();
		}
		
		
		
		
		
		
		/** 增加监听 index 0-3 */
		/*if(index==0||index==1){//  weapon armor
			bgac = new BagActionListener(superPanel,index,player);
			equipBagShow();
		}else{
			SUtils.otherBagShow(goodsContent,curBag, bgac);
		}*/
	}
	
	/** 布置当前背包物品 */
	public void equipBagShow() {
		System.out.println("正在重新布置背包！");
		if(goodsContent!=null){
			scrollPane.remove(goodsContent);
		}
		/** 显示每件装备的信息 */
		goodsContent = new JPanel();
		scrollPane.setViewportView(goodsContent);
		goodsContent.setLayout(null);
		goodsContent.setBackground(new Color(57, 47, 65));
		
		if(player.getWeaponBag()!=null&&player.getWeaponBag().size()>0){
			System.out.println("index:"+index+",现在来查看背包内第一件武器:"+player.getWeaponBag().get(0));
		}
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
		//排序
		Collections.sort(curBag);
		
		int size = curBag==null?0:curBag.size();
		if(size<=7){
			goodsContent.setPreferredSize(new Dimension(246, 140));
		}else{
			int height = 200 + (size-7)*21 ;
			goodsContent.setPreferredSize(new Dimension(246, height));
		}
		bgac = new BagActionListener(superPanel,index,player);
		
		Equip equip = null ;
		TButton tempBu;
		JLabel tempField;
		
		/** 通过页数得到当前背包物品 */
		List<Equip> equipList = player.getEquipBag(index);
		if(equipList==null){
			return ;
		}
		System.out.println("背包里物品的数量:"+equipList.size());
		/** 先设置第一个，再设置其他 */
		for (int i = 0; i < equipList.size(); i++) {
			equip = equipList.get(i);
			tempBu = new TButton(equip.getName(),16);
			/** ActionCommand为在当前list中的序号 */
			tempBu.setActionCommand(i+"");
			/** 设置组件边距 */
			tempBu.setBorder(new EmptyBorder(0, 1, 0, 0));
			tempBu.setHorizontalAlignment(SwingConstants.LEFT);
			/** 设置按钮透明 */
			tempBu.setFont(f);
			tempBu.setContentAreaFilled(false);
			tempBu.setOpaque(true);
			tempBu.setBackground(new Color(57, 47, 65));
			/** 设置字体颜色 */
			tempBu.setForeground(Constant.equipColor[equip.getType()]);
			tempBu.addActionListener(bgac);
			tempBu.setFocusable(false);
			tempBu.setBounds(2, i * 20 + 1, 98, 20);
			goodsContent.add(tempBu);
			for (int j = 1; j < 4; j++) {
				String tempStr = SUtils.getShowField(j, equip);
				tempField = new JLabel(tempStr);
				tempField.setFont(f);
				tempField.setBorder(new EmptyBorder(0, 4, 0, 0));
				tempField.setForeground(new Color(250,255,240));
				tempField.setHorizontalTextPosition(SwingConstants.CENTER);
				tempField.setBounds(2 + j * 60+40, i * 20 + 1, 60, 20);
				goodsContent.add(tempField);
			}
		}
		/** 触发调用以重新绘制组件认为“脏区域”的内容 */
		scrollPane.revalidate();
		/** 重绘 */
		scrollPane.repaint();
	}
	
	
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
