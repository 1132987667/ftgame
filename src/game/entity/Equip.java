package game.entity;

import game.control.EquipControl;

import java.awt.Color;
import java.io.Serializable;

public class Equip extends Item implements Serializable,Comparable<Equip>{
	
	private static final long serialVersionUID = 1L;

	public static final String[] partAry = { "weapon", "helmet", "necklace", "coat", "ring", "waistband", "trousers",
			"shoes" };
	public static final String[] partDes = { "兵器", "头盔", "项链", "上衣", "戒指", "腰带", "裤子", "鞋子" };
	public static final String[] typeDesAry = { "普通", "精致", "勇者", "史诗", "传说" };
	public static final Color[] colorAry = { Color.white,Color.blue,new Color(141,75,187),new Color(240,0,86),new Color(255,182,30) } ;
	
	
	private String[] unitAry = { "把", "项", "条", "件", "支", "条", "条", "双" };
	/** 装备id */
	private String id ;
	/** 装备名字 */
	private String name;
	/** 装备单位 */
	private String unit;
	/** 装备的描述 */
	private String des;
	/** 装备位置 */
	private int part = -1 ;
	/** 装备品质 */
	private int type;
	/** 装备要求等级 */
	private int rank;
	/** 装备的数量 */
	private int num = 1 ;
	/** 装备的种类 */
	private String kind ;
	/** 价格 */
	private int price ;
	/** 生成种类 |【固定属性】【随机属性】*/
	private String mode = "" ;
	
	
	/** 词条列表 */
	private Citiao[] attrList = null ;
	private String[] attrDes = null ;
	/** 固定的词条类型 */
	private Citiao[] fixedList = null ;
	
	
	/** 初始化 */
	public Equip() {
		name = "" ;
		unit = "" ;
		des = "" ;
		part = 0 ;
		type = 0 ;
		rank = 0 ;
		num = 1 ;
		kind = "" ;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer("装备:"+name+"\n") ;
		str.append("要求等级:"+rank+"\n");
		str.append("部位:"+EquipControl.partDes[part]+"\n");
		str.append("品质:"+EquipControl.typeAry[type]+"\n");
		str.append("描述:"+des+"\n");
		if(attrList!=null){
			for (int i = 0; i < attrList.length; i++) {
				//attrList[i] = control.rdAddAttr(rank, part, i, type);
				str.append(attrList[i].des()+"\n");
			}
		}
		
		return str.toString();
	}
	public static void main(String[] args) {
		/*for (int i = 0; i < 100; i++) {
			int num = new Random().nextInt(40 + 50) ;
			System.out.println("num:"+num/50+" , "+num/50 * 0.4);
		}*/
		double i = 10/100 ;
		System.out.println( i );	
	}
	public String[] getUnitAry() {
		return unitAry;
	}
	public void setUnitAry(String[] unitAry) {
		this.unitAry = unitAry;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public int getPart() {
		return part;
	}
	public void setPart(int part) {
		this.part = part;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public Citiao[] getAttrList() {
		return attrList;
	}
	public void setAttrList(Citiao[] attrList) {
		this.attrList = attrList;
	}
	public String[] getAttrDes() {
		return attrDes;
	}
	public void setAttrDes(String[] attrDes) {
		this.attrDes = attrDes;
	}
	public static String[] getPartary() {
		return partAry;
	}
	public static String[] getPartdes() {
		return partDes;
	}
	public static String[] getTypeary() {
		return typeDesAry;
	}
	public static Color[] getColorary() {
		return colorAry;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int compareTo(Equip o) {
		int value = o.getType() - this.getType() ;//先按装备名排序，再按装备品质排序
		if(value == 0){
			return o.getName().compareTo(this.getName());
		}
		return value;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Citiao[] getFixedList() {
		return fixedList;
	}

	public void setFixedList(Citiao[] fixedList) {
		this.fixedList = fixedList;
	}

}