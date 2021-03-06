package game.view.panel;

import game.entity.Equip;
import game.utils.C;
import game.view.frame.QuickFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
/**
 * 负责显示人物所所穿戴的装备的信息
 * @author yilong22315
 *
 */
public class EquipInfoPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	Font font = new Font("宋体", Font.PLAIN, 12);
	public static String[] ary = { "品质", "lv", "位置", "售价", "数量", "血", "攻", "防", "敏", "描述", "特效" };// ,
	private TempLabel name,part,price,type,rank,attr1,attr2,attr3,attr4,attr5;	
	private TempLabel[] attrAry = {attr1,attr2,attr3,attr4,attr5} ;
	
	private JTextArea jta ;
	
	Font font2 = new Font("隶书", Font.PLAIN, 18);// "id"
	Font font1 = new Font("微软雅黑", Font.PLAIN, 12);
	
	private int inset = 12;
	private int height = 17 ;
	private int width = 80;
	private JScrollPane jsc = null ;
	
	public EquipInfoPanel() {
		/** 170,240 */
		/** 宽300 高度 210 */
		this.setLayout(null);
		this.setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		jsc = new JScrollPane(this);
		//setBorder(BorderFactory.createEtchedBorder());
		//setBackground(new Color(204,204,255));
		name = new TempLabel("醉银剑",2);
		name.setHorizontalAlignment(SwingConstants.LEFT);
		name.setBounds(inset, 8, 170, 20);
		
		part = new TempLabel("武器:剑",1);
		part.setBounds(inset, name.getY()+name.getHeight(), width, height);
		price = new TempLabel("售价:10000",1);
		price.setBounds(part.getX()+width,  name.getY()+name.getHeight(), width, height);
		
		type = new TempLabel("品质:普通",1);
		type.setBounds(inset, price.getY()+height, width, height);
		rank = new TempLabel("等级:",1);
		rank.setBounds(type.getX()+width, price.getY()+height, width, height);
		/** 装备介绍/描述 */
		jta = new JTextArea();
		jta.setOpaque(false);
		jta.setEditable(false);
		jta.setBackground(new Color(1,1,1, (float) 0.01));
		jta.setBorder(BorderFactory.createEmptyBorder(2,0,2,0));
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setFont(new Font("宋体", Font.PLAIN, 12));
		jta.setForeground(Color.white);
		jta.setBounds(inset, rank.getY()+height, 146, 78);
		jta.setPreferredSize(new Dimension( 146, 78));
		jta.setText("描述:巧匠打造的一种头盔，镶嵌着一种蓝色巨鸟的羽毛。据说这种鸟的羽毛能够为佩带者带来意想不到的好运。啊实打实大苏打似的");
		for (int i = 0; i < attrAry.length; i++) {
			attrAry[i] = new TempLabel("属性"+i+":", 1);
			//attrAry[i].setBounds(inset, (i+2)*height+jta.getHeight()+name.getHeight(), 140, height);
			add(attrAry[i]);
		}
		
		
		add(name);
		add(part);
		add(price);
		add(type);
		add(rank);
		add(jta);
		
		JLabel back = new JLabel();
		back.setOpaque(false);
		ImageIcon image1 = new ImageIcon("src/game/img/back/equipBack2.png") ;
		back.setIcon(image1);
		back.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());
		add(back);
	}
	
	public JScrollPane getScrollPanel(){
		return jsc; 
	}
	
	class TempLabel extends JLabel{
		private static final long serialVersionUID = 1L;

		public TempLabel(String str,int i) {
			super(str);
			setForeground(Color.white);//new Color(255, 70, 31)
			if(i==1){
				setFont(font1);
			}else if(i==2){
				setFont(font2);
			}
			
		}
	}
	
	/** part,price,type,rank,attr1,attr2,attr3,attr4,attr5, tempLabel;	 */ 
	/**
	 * 把装备信息显示在面板上
	 * i=1当前【选择装备】  i=2【穿戴】 14
	 * @param equip
	 * @param i
	 */
	public void setEpInfo(Equip equip,int theI){
		if(equip==null){
			emptyEquip();
			return ;
		}
		String last = "" ;
		if(theI==2){
			last = "已装备" ;
		}else{
			last = "已选择" ;
		}
		name.setText(equip.getName());
		name.setForeground(C.equipColor[equip.getType()]);
		//name.setText(equip.getName());
		part.setText(C.partDes[equip.getPart()]+"|"+last);
		price.setText("售价:"+equip.getPrice());
		type.setText(Equip.typeDesAry[equip.getType()]);
		type.setForeground(C.equipColor[equip.getType()]);
		rank.setText("等级:"+equip.getRank()+"");
		int column = equip.getDes().length()%13>0?equip.getDes().length()/13+1:equip.getDes().length()/13;
		jta.setText(equip.getDes());
		jta.setSize(new Dimension(146,15*column));
		jta.setFont(new Font("微软雅黑", Font.PLAIN, 11));
		if(equip.getAttrDes()!=null){
			for (int i = 0; i < attrAry.length; i++) {
				if(i<equip.getAttrDes().length){
					attrAry[i].setText(equip.getAttrList()[i].des);
					attrAry[i].setBounds(inset, i*height+jta.getHeight()+jta.getY(), 140, height);
					attrAry[i].setForeground(new Color(135,206,250));
				}else{
					attrAry[i].setText("");
				}
			}
		}
	}
	
	/**
	 * 某个部位没有穿戴装备
	 */
	public void emptyEquip(){
		name.setText("");
		part.setText("|无");
		price.setText("");
		type.setText("");
		rank.setText("");
		jta.setText("");
		for (int i = 0; i < attrAry.length; i++) 
			attrAry[i].setText("");
	}
	
	public static void main(String[] args) {
		QuickFrame qf = new QuickFrame();
		JPanel panel = qf.getMainPanel();
		EquipInfoPanel eqinfo = new EquipInfoPanel();
		panel.add(eqinfo);
		eqinfo.setLocation(0, 0);
		qf.start();
		
	}
	
}
