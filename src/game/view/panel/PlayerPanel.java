package game.view.panel;

import game.control.EquipControl.AddAttrs;
import game.control.GameControl;
import game.entity.Equip;
import game.entity.Player;
import game.utils.Constant;
import game.utils.SUtils;
import game.view.button.TButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public JPanel oneP, twoP, SPP, equipP, equipAdd;
	
	private JLabel tempLabel ;
	private AttrLabel attrLabel ;
	/** 人物的一级属性 */
	private JLabel tiliV, jingliV, liV, minV, luckyV;
	private JLabel[] oneAttrV = { tiliV, jingliV, liV, minV, luckyV };
	
	/** 人物的二级属性 */
	private JLabel hpV, mpV, atkV, defV;
	private JLabel[] twoAttrV = { hpV, mpV, atkV, defV };
	
	/** 人物的特殊属性 */
	private JLabel suckV, baojiV, baolvV, expV, moneyV, petV;
	private JLabel[] spAttrV = { suckV, baojiV, baolvV, expV, moneyV, petV };

	/** 名字，等级，境界 ， 经验 */
	private String[] myName = { "名号", "等级", "境界", "经验", };
	private AttrLabel nameV, rankV, stateV, theExpV;
	private AttrLabel[] myV = { nameV, rankV, stateV, theExpV };
	
	/** 装备部位 */
	private EqLabel weaponV, helmetV, necklaceV, coatV, ringV, waistbandV, trousersV, shoesV;
	private EqLabel[] equipAryV = { weaponV, helmetV, necklaceV, coatV, ringV, waistbandV, trousersV, shoesV };
	
	/** 装备带来的属性加成 */
	private JLabel add0V,add1V,add2V,add3V,add4V,add5V,add6V,add7V,add8V,add9V,add10V,
				   add11V,add12V,add13V,add14V,add15V,add16V,add17V,add18V,add19V ;
	private JLabel[] equipAddV = {add0V,add1V,add2V,add3V,add4V,add5V,add6V,add7V,add8V,add9V,add10V,
								  add11V,add12V,add13V,add14V,add15V,add16V,add17V,add18V,add19V} ; 
	
	private Equip[] equipAry = new Equip[8] ;
	
	/** 装备面板 */
	public JPanel equipPanel = null;
	/** 属性面板 */
	public JPanel attrPanel = null;
	
	/** 装备面板去往属性面板 */
	/** 属性面板去往装备面板 */
	private TButton jumpBu = null ;
	
	/** 当前所有装备加成 */
	private AddAttrs curAddAttrs ;
	
	private JLabel back ;
	/** 拖拽Bu */
	private TButton drugBu = null ;
	private JPanel parent ;
	
	/** 组件的x,y坐标 */
	int x = 0;
	int y = 0;
	/** 组件的间距 */
	int inset = 4;
	private int fontSize = 14;
	/** 边框上下的高度 */
	private int borderHeight = 25;
	/** 属性名字的大小 */
	private int attrName = fontSize * 5 / 2;// 35
	private int attrValue = fontSize * 6;// 86 121
	public Font font = new Font("幼圆", Font.PLAIN, fontSize);

	private GameControl gameControl = null ;
	private Player player = null ;
	
	public void setBack(JLabel back){
		this.back = back ;
	}
	
	public PlayerPanel(TButton drugB,JPanel parent) {
		this.parent = parent ;
		this.drugBu = drugB ;
		this.setBounds(0, 24, 300, 410);
		setLayout(null);
		setOpaque(false);
		//setBackground(Color.white);
		gameControl = GameControl.getInstance();
		player = gameControl.getPlayer();
		
		/** 初始化人物属性面板 */
		attrPanel = new JPanel() ;
		attrPanel.setVisible(true);
		add(attrPanel);
		attrPanel.setBounds(0,0,611,310);
		attrPanel.setLayout(null);
		attrPanel.setOpaque(false);
		initAttrPanel(attrPanel);
		
		/** 初始化人物装备面板 不同品质颜色不同 */
		equipPanel = new JPanel() ;
		add(equipPanel);
		equipPanel.setVisible(true);
		//equipPanel.setBounds(0,0,300,410);
		equipPanel.setLayout(null);
		equipPanel.setOpaque(false);
		initEquipPanel(equipPanel);
		
		/** jump完成全部跳转任务 */
		jumpBu = new TButton("",20) ;
		jumpBu.setActionCommand("attr");
		jumpBu.addActionListener(jump);
		add(jumpBu);
		jumpBu.setBounds(176, 322, 76, 30);
		
		/** 增加两个跳转按钮 */
		/*attrBu = new TButton("",20) ;
		attrBu.setActionCommand("attr");
		attrBu.addActionListener(jump);
		add(attrBu);
		attrBu.setBounds(176, 322, 76, 30);
		
		
		equipBu = new TButton("", 22);
		equipBu.setSize(new Dimension(40,26));
		equipBu.setActionCommand("equip");
		equipPanel.add(equipBu);*/
	}
	
	/** 初始化人物面板 */
	public void initAttrPanel(JPanel attrPanel) {
		x = inset * 2;
		y = inset * 2;
		attrLabel = new AttrLabel(myName[0]+":",Color.black);
		myV[0] = new AttrLabel("老白");
		attrLabel.setBounds(x, y, myName[0].length()*fontSize+fontSize/2,fontSize);
		myV[0].setBounds(x+attrLabel.getWidth()+inset, y,(fontSize+inset)*8, fontSize);
		attrPanel.add(attrLabel);
		attrPanel.add(myV[0]);
		for (int i = 1; i < myV.length; i++) {
			attrLabel = new AttrLabel(myName[i]+":",Color.black);
			myV[i] = new AttrLabel("");
			attrPanel.add(attrLabel);
			attrPanel.add(myV[i]);
			/** 属性名字占的大小 */
			attrName = myName[i].length()*fontSize+fontSize/2;
			attrLabel.setBounds(x,inset*3+fontSize+inset,attrName,fontSize);
			if(i==1||i==2){
				myV[i].setBounds(x+attrName+inset,inset*3+fontSize+inset,fontSize*2,fontSize);
			}else{
				myV[i].setBounds(x+attrName+inset,inset*3+fontSize+inset,150,fontSize);
			}
			x = myV[i].getX()+myV[i].getWidth()+inset;
		}
		
		x = inset;
		y = myV[3].getY()+myV[3].getHeight()+inset;
		/** 一级属性 */
		oneP = new JPanel();
		oneP.setOpaque(false);
		oneP.setLayout(null);
		attrPanel.add(oneP);
		oneP.setBounds(x, y,294,inset*4+(fontSize+inset)*3);
		SUtils.setBorder(oneP, "一级属性", Color.blue, new Font("隶书",Font.PLAIN,16));
		int length = Constant.oneAttr.length;
		x = inset*2 ;
		y = fontSize+inset+4 ;
		for (int i = 0; i < length; i++) {
			if(i==3){
				x = inset*2 ;
			}
			attrLabel = new AttrLabel(Constant.oneAttr[i]+":",Color.black);
			oneAttrV[i] = new AttrLabel("100000");
			oneP.add(attrLabel);
			oneP.add(oneAttrV[i]);
			attrName = Constant.oneAttr[i].length()*fontSize+fontSize/2;
			if(3==i){
				y = y + fontSize+inset;
			}
			attrLabel.setBounds(x, y, attrName, fontSize);
			oneAttrV[i].setBounds(attrLabel.getX()+attrName+inset,y,4*fontSize,fontSize);
			x = oneAttrV[i].getX()+oneAttrV[i].getWidth()+inset;
		}
		
		/** 二级属性 */
		x = inset ;
		y = oneP.getY()+oneP.getHeight()+inset;
		twoP = new JPanel();
		twoP.setOpaque(false);
		twoP.setLayout(null);
		attrPanel.add(twoP);
		twoP.setBounds(x, y,294,inset*4+(fontSize+inset)*3);
		SUtils.setBorder(twoP, "二级属性", Color.blue, new Font("隶书",Font.PLAIN,16));
		length = Constant.twoAttr.length;
		x = inset*2 ;
		y = fontSize+inset+4 ;
		for (int i = 0; i < length; i++) {
			if(i==3){
				x = inset*2 ;
			}
			attrLabel = new AttrLabel(Constant.twoAttr[i]+":",Color.black);
			twoAttrV[i] = new AttrLabel("100000");
			twoP.add(attrLabel);
			twoP.add(twoAttrV[i]);
			attrName = Constant.twoAttr[i].length()*fontSize+fontSize/2;
			if(3==i){
				y = y + fontSize+inset;
			}
			attrLabel.setBounds(x, y, attrName, fontSize);
			twoAttrV[i].setBounds(attrLabel.getX()+attrName+inset,y,4*fontSize,fontSize);
			x = twoAttrV[i].getX()+twoAttrV[i].getWidth()+inset;
		}
		
		/** 特殊属性 */
		x = inset ;
		y = twoP.getY()+twoP.getHeight()+inset;
		SPP = new JPanel();
		SPP.setOpaque(false);
		SPP.setLayout(null);
		attrPanel.add(SPP);
		SPP.setBounds(x, y,294,inset*4+(fontSize+inset)*4);
		SUtils.setBorder(SPP, "特殊属性", Color.blue, new Font("隶书",Font.PLAIN,16));
		length = Constant.spAttr.length;
		x = inset*2 ;
		y = fontSize+inset+4 ;
		for (int i = 0; i < length; i++) {
			attrLabel = new AttrLabel(Constant.spAttr[i]+":",Color.black);
			spAttrV[i] = new AttrLabel("100000");
			SPP.add(attrLabel);
			SPP.add(spAttrV[i]);
			attrName = Constant.spAttr[i].length()*fontSize+fontSize/2;
			if(i==2){
				x = inset*2 ;
				y = y + fontSize+inset;
			}else if(i==4){
				x = inset*2 ;
				y = y + fontSize+inset;
			}
			attrLabel.setBounds(x, y, attrName, fontSize);
			spAttrV[i].setBounds(attrLabel.getX()+attrName+inset,y,4*fontSize,fontSize);
			if(i==0){
				spAttrV[i].setBounds(attrLabel.getX()+attrName+inset,y,5*fontSize,fontSize);
			}
			x = spAttrV[i].getX()+spAttrV[i].getWidth()+inset;
		}
	}
	
	/** 初始化装备面板 */
	public void initEquipPanel(JPanel attrPanel){
		/** 显示装备的小面板 */
		equipP = new JPanel();
		equipP.setOpaque(false);
		equipP.setLayout(null);
		equipPanel.add(equipP);
		x = inset ;
		y = inset ;
		attrName = 2*fontSize+fontSize/2 ;
		equipP.setBounds(x, y, inset*2+(attrName+fontSize*8), inset*2+(fontSize+2*inset)*8);// 10 + (30+58)*3 + 10 = 284 
		/** 设置边框 */
		SUtils.setBorder(equipP, "",Color.blue, font);
		int length = equipAryV.length ;
		x = inset*2 ;
		y = inset*2;
		attrName = fontSize*5/2;
		/** 逐个的设置 每件装备的显示位置 */
		for (int i = 0; i < length; i++) {
			tempLabel = new JLabel(Constant.partDes[i]+":");
			equipAryV[i] = new EqLabel("万丈祥云高布衣");//equipAryV
			tempLabel.setFont(font);
			equipP.add(tempLabel);
			equipP.add(equipAryV[i]);
			tempLabel.setBounds(x, y, attrName, fontSize);
			equipAryV[i].setBounds(x+attrName+inset, y, 7*fontSize+inset, fontSize);
			x = tempLabel.getX();
			y = tempLabel.getY()+fontSize+2*inset;
		}
		
		/** 显示装备加成的小面板 8+4  4+8 */
		equipAdd = new JPanel() ;
		equipAdd.setOpaque(false);
		equipAdd.setLayout(null);
		SUtils.setBorder(equipAdd,"装备加成",Color.black, new Font("隶书",Font.PLAIN,16));
		attrPanel.add(equipAdd);
		x = inset ;
		y = inset+equipP.getHeight() ;
		equipAdd.setBounds(x, y, 294, 190);// 10 + (30+58)*3 + 10 = 284 
		length = Constant.equipAdd.length ;
		x = inset*2 ;
		y = fontSize+inset*3;
		attrName = 4*fontSize+fontSize/2 ;
		/** 逐步设置装备属性的位置 */
		for (int i = 0; i < length; i++) {
			tempLabel = new JLabel(Constant.equipAdd[i]+":");
			equipAddV[i] = new JLabel("10000");//equipAryV
			equipAddV[i].setForeground(Color.blue);
			tempLabel.setFont(font);
			equipAddV[i].setFont(font);
			/** 加入面板 */
			equipAdd.add(tempLabel);
			equipAdd.add(equipAddV[i]);
			attrName = Constant.equipAdd[i].length()*fontSize+fontSize/2 ;
			if(i<3){
				tempLabel.setBounds(x, y, attrName, fontSize);
				equipAddV[i].setBounds(x+attrName, y, fontSize*7/2, fontSize);
				y = 2*inset+fontSize+inset;
			}else if(3<=i && i<6){
				if(i==3){
					x = inset*2 ;
				}
				y = 2*inset+(fontSize+inset)*2;
				tempLabel.setBounds(x, y, attrName, fontSize);
				equipAddV[i].setBounds(x+attrName, y, fontSize*7/2, fontSize);
			}else if(6<=i&&i<9){
				if(i==6){
					x = inset*2 ;
				}
				y = 2*inset+(fontSize+inset)*3;
				tempLabel.setBounds(x, y, attrName, fontSize);
				equipAddV[i].setBounds(x+attrName, y, fontSize*7/2, fontSize);
			}else if(9<=i&&i<11){
				if(i==9){
					x = inset*2 ;
				}
				y = 2*inset+(fontSize+inset)*4;
				tempLabel.setBounds(x, y, attrName, fontSize);
				equipAddV[i].setBounds(x+attrName, y, fontSize*7/2, fontSize);
			}else if(11<=i&&i<13){
				if(i==11){
					x = inset*2 ;
				}
				y = 2*inset+(fontSize+inset)*5;
				tempLabel.setBounds(x, y, attrName, fontSize);
				equipAddV[i].setBounds(x+attrName, y, fontSize*7/2, fontSize);
			}else if(13<=i && i<15){
				if(i==13){
					x = inset*2 ;
				}
				y = 2*inset+(fontSize+inset)*6;
				tempLabel.setBounds(x, y, attrName, fontSize);
				equipAddV[i].setBounds(x+attrName, y, fontSize*7/2, fontSize);
			}else if(15<=i && i<17){
				if(i==15){
					x = inset*2 ;
				}
				y = 2*inset+(fontSize+inset)*7;
				tempLabel.setBounds(x, y, attrName, fontSize);
				equipAddV[i].setBounds(x+attrName, y, fontSize*7/2, fontSize);
			}else if(17<=i&&i<=18){
				if(i==17){
					x = inset*2 ;
				}
				y = 2*inset+(fontSize+inset)*8;
				tempLabel.setBounds(x, y, attrName, fontSize);
				equipAddV[i].setBounds(x+attrName, y, fontSize*7/2, fontSize);
			}
			x=x+attrName + fontSize*7/2 ;
		}
		
	}

	/**
	 * 设置/更新 面板上人物属性的显示
	 * 1:需要更新人物的装备
	 * 2:需要更新热门人物的装备加成
	 * 3:需要更新人物属性
	 */
	public void initData() {
		/** 重新加载人物属性 */
		AddAttrs addAttrs = gameControl.reloadAttr();
		
		/** 1 */
		Equip[] equipAry = player.getEquipAry() ;
		System.out.println("玩家信息:"+player);
		System.out.println("玩家现在装备的武器:"+player.getEquip(0));
		for (int i = 0; i < equipAryV.length; i++) {
			//System.out.println("玩家装备:"+equipAry[i]);
			if(equipAry[i]!=null){
				equipAryV[i].setText(equipAry[i].getName());
				equipAryV[i].setForeground(Constant.equipColor[equipAry[i].getType()]);
				if(equipAry[i].getType()==0){
					equipAryV[i].setForeground(Color.black);
				}
			}else{
			}
		}
		/** 2 */
		reloadAttrAdd(addAttrs);
		
		/** 3 */
		/** 体精力敏辛 */
		oneAttrV[0].setText(player.getTili()+""); 
		oneAttrV[1].setText(player.getJingli()+""); 
		oneAttrV[2].setText(player.getLi()+""); 
		oneAttrV[3].setText(player.getMin()+""); 
		oneAttrV[4].setText(player.getLucky()+""); 
		/** 血法攻防 */
		twoAttrV[0].setText(player.getHp()+"");
		twoAttrV[1].setText(player.getMp()+"");
		twoAttrV[2].setText(player.getAttack()+"");
		twoAttrV[3].setText(player.getDefense()+"");
		/** suckV, baojiV, baolvV, expV, moneyV, petV */
		spAttrV[0].setText(player.getSuck()+"");
		spAttrV[1].setText(player.getBaoji()+"");
		spAttrV[2].setText(player.getBaolv()+"");
		spAttrV[3].setText(player.getExpAdd()+"");
		spAttrV[4].setText(player.getMoneyAdd()+"");
		spAttrV[5].setText(player.getPetAdd()+"");
		
		/** nameV, rankV, stateV, theExpV */
		myV[0].setText(player.getName());
		myV[1].setText(player.getRank()+"");
		myV[2].setText(player.getState());
		myV[3].setText(player.getCurExp()+"/"+player.getExp());
	}
	
	/** 为装备字段而创建的 */
	class EqLabel extends JLabel implements MouseListener{
		private static final long serialVersionUID = 1L;
		public EqLabel(String text) {
			super(text);
			setForeground(Color.black);
			setFont(font);
			addMouseListener(this);
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			for (int i = 0; i < equipAryV.length; i++) {
				System.out.println(equipAryV[i].getText());
				equipAryV[i].setFont(font);
				equipAryV[i].setBorder(BorderFactory.createEmptyBorder());
			}
			this.setFont(new Font("幼圆", Font.PLAIN, 13));
			this.setBorder(BorderFactory.createEtchedBorder());
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
	}
	
	/** 为属性字段而创建的 */
	class AttrLabel extends JLabel{
		private static final long serialVersionUID = 1L;
		public AttrLabel(String text) {
			super(text);
			setForeground(Color.blue);
			setFont(font);
		}
		public AttrLabel(String text,Color color) {
			super(text);
			setForeground(color);
			setFont(font);
		}
	}
	
	ActionListener jump = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(e.getActionCommand().equals("attr")){
						jumpBu.setActionCommand("equip");
						jumpBu.reload(22);
						jumpBu.setSize(40, 26);
						jumpBu.setLocation(0, 380);
						
						attrPanel.setVisible(false);
						equipPanel.setVisible(true);
						equipPanel.setBounds(0, 0, 303, 410);
						/** 重置拖动按钮  */
						drugBu.reload(17);
						drugBu.setBounds(222, 394, 64, 40);
						/** 重新设置背景图 */
						ImageIcon img = new ImageIcon("src/game/img/back/墨A.png");
						back.setIcon(img);
						parent.repaint();
					}else{
						jumpBu.setActionCommand("attr");
						jumpBu.reload(20);
						jumpBu.setBounds(176, 322, 76, 30);

						attrPanel.setVisible(true);
						equipPanel.setVisible(false);
						attrPanel.setBounds(0, 0, 303, 410);
						/** 重置拖动按钮  */
						drugBu.reload(19);
						drugBu.setBounds(8, 378, 64, 40);
						/** 重新设置背景图 */
						ImageIcon img = new ImageIcon("src/game/img/back/diao.png");
						back.setIcon(img);
						parent.repaint();
					}
				}
			});
			
		}
	};
	
	/** 重新布置装备加成 */
	public void reloadAttrAdd(AddAttrs addAttrs){
		equipAddV[0].setText(addAttrs.tili+"");
		equipAddV[1].setText(addAttrs.jingli+"");
		equipAddV[2].setText(addAttrs.li+"");
		equipAddV[3].setText(addAttrs.min+"");
		equipAddV[4].setText(addAttrs.lucky+"");
		equipAddV[5].setText(addAttrs.hp+"");
		equipAddV[6].setText(addAttrs.mp+"");
		equipAddV[7].setText(addAttrs.attack+"");
		equipAddV[8].setText(addAttrs.defense+"");
		equipAddV[9].setText(addAttrs.baoji+"");
		equipAddV[10].setText(addAttrs.suck+"");
		equipAddV[11].setText(addAttrs.expEd+"");
		equipAddV[12].setText(addAttrs.moneyEd+"");
		equipAddV[13].setText(addAttrs.baolvEd+"");
		equipAddV[14].setText(addAttrs.petAll+"");
		equipAddV[15].setText(addAttrs.hpEd+"");
		equipAddV[16].setText(addAttrs.mpEd+"");
		equipAddV[17].setText(addAttrs.atkEd+"");
		equipAddV[18].setText(addAttrs.defEd+"");
	}
	
}