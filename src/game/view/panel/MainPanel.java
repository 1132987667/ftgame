package game.view.panel;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import game.utils.Constant;


public class MainPanel extends JPanel{
	/** 我的属性面板 */
	private JPanel myAttr ;
	private JPanel foeAttr ;
	/** 房间描述面板 */
	private JPanel roomInfo ;
	/** 战斗所在面板 */
	private JPanel fightJpanel  ;
	/** 背包面板 */
	private JTabbedPane bagPanel ;
	/** 功能 */
	JPanel function ;
	
	private BagPanel bag ;
	
	/** 基本属性 */
	JLabel name,hp,attack,defense,nimble,rank,exp,epithet ;
	JLabel[] attrAryLabel = {name,hp,attack,defense,nimble,rank,exp,epithet} ;
	
	/** 功能的按钮  状态，技能，背包，人物，地图 */
	JButton status , skillsBu , bagBu , task , map ; 
	JButton[] funAry = { status , skillsBu , bagBu , task ,map }; 
	
	/** 背包内物品的四种分类物品 */
	JPanel weapons,armor,skillsLabel,materials;
	JPanel[] bagJPanelAry = {weapons,armor,skillsLabel,materials};
	
	public MainPanel() {
		setLayout(null);
		/** 我的属性面板 */
		int attrWidth = 136 ;
		int attrHeight = 216 ;
		myAttr = new JPanel();
		add(myAttr);
		myAttr.setBounds(0, 0, attrWidth, attrHeight);
		myAttr.setBackground(Constant.colorAry[4]);
		Font font1 = new Font("楷体", Font.PLAIN, 16);
		Font font2 = new Font("楷体", Font.BOLD, 20);
		myAttr.setLayout(null);
		myAttr.setBorder(BorderFactory.createTitledBorder("当前玩家"));
		for (int i = 0; i < attrAryLabel.length; i++) {
			attrAryLabel[i] = new JLabel(Constant.attrAry[i]+":");
			if(i==0){
				attrAryLabel[i] = new JLabel("	");
			}
			attrAryLabel[i].setFont(font1);
			myAttr.add(attrAryLabel[i]);
			attrAryLabel[i].setBounds(8, 24*i+16, 40, 24);
		}
		
		/** 敌人的属性面板 --开始 */
		foeAttr = new JPanel();
		add(foeAttr);
		foeAttr.setBounds(632, 0, 136, 216);
		foeAttr.setBackground(Constant.colorAry[4]);
		foeAttr.setLayout(null);
		foeAttr.setBorder(BorderFactory.createTitledBorder("敌方信息"));
		for (int i = 0; i < attrAryLabel.length; i++) {
			attrAryLabel[i] = new JLabel(Constant.attrAry[i]+":");
			if(i==0){
				attrAryLabel[i] = new JLabel("	");
			}
			attrAryLabel[i].setFont(font1);
			foeAttr.add(attrAryLabel[i]);
			attrAryLabel[i].setBounds(8, 24*i+16, 40, 24);
		}
		foeAttr.setVisible(false);
		/** 敌人的属性面板 --结束 */
		
		/** 房间系统 */
		roomInfo = new JPanel() ;
		add(roomInfo);
		roomInfo.setBounds(attrWidth, 0, 512, 120);
		roomInfo.setFont(font2);
		roomInfo.setBackground(Constant.colorAry[1]);
		roomInfo.setBorder(BorderFactory.createTitledBorder("场景"));
		
		/** 战斗场景初始化 */
		fightJpanel = new JPanel() ;
		fightJpanel.setLayout(null);
		add(fightJpanel);
		fightJpanel.setBounds(attrWidth, 120, 512, 298);
		fightJpanel.setFont(font2);
		fightJpanel.setBackground(Constant.colorAry[2]);
		fightJpanel.setBorder(BorderFactory.createEtchedBorder());
		
		/** 功能 --开始  */
		function = new JPanel() ;
		add(function);
		function.setBounds(0, attrHeight, 120, 200);
		function.setBackground(Constant.colorAry[3]);
		function.setBorder(BorderFactory.createEtchedBorder());
		for (int i = 0; i < funAry.length; i++) {
			funAry[i] = new JButton("【"+Constant.funAry[i]+"】");
			funAry[i].setFont(font1);
			funAry[i].setFocusable(false);
			function.add(funAry[i]);
			funAry[i].setBounds(4, 24*i, 80, 24);
		}
		/** 功能 --结束  */
		
		/** 背包 --开始  */
		bagPanel = new JTabbedPane();
		fightJpanel.add(bagPanel);
		bagPanel.setBackground(Constant.colorAry[2]);
		bagPanel.setBounds(6, 6, 278, 280);
		
		setLayout(null);
		for (int i = 0; i < bagJPanelAry.length; i++) {
			bagJPanelAry[i] = new JPanel();
			bagJPanelAry[i].setBackground(Constant.colorAry[2]);
			bagJPanelAry[i].setLayout(null);
			bagPanel.addTab(Constant.bagClassifyAry[i], bagJPanelAry[i]);
			//bagJPanelAry[i].add(new BagClassifyPanel(fightJpanel,i));
		}
		/** 背包 --结束  */
	}
}
