package game.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 *	装备的词条类
 *		储存词条的具体信息
 *	例子: 
 *	des:攻击力+55
 *	type:二级属性
 *	attrName:攻击力
 *  attrValue:55
 */
public class Citiao implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 词条的类型  一级属性词条 二级属性词条 技能词条 宠物词条  特殊加成词条
	 */
	public String[] attrType = { " oneAdd", "twoAdd", "skillAdd", "petAdd", "special" };

	/** 关于词条的描述 */
	public String des = "";

	/** 词条的类型 
	 *  oneAdd:0, twoAdd:1, skillAdd:2, petAdd:3, special:4
	 */
	public int type = 0;
	
	/** 词条类型的名字 */
	public String typeName = "" ;
	
	/** 词条具体属性位置 */
	public int attr = 0 ;
	
	/** 词条具体属性的名字 */
	public String attrName = "";

	/** 属性的值 小数 */
	public double attrValue = 0;

	
	
	@Override
	public String toString() {
		return "Citiao [attrType=" + Arrays.toString(attrType) + ", des=" + des
				+ ", type=" + type + ", typeName=" + typeName + ", attr="
				+ attr + ", attrName=" + attrName + ", attrValue=" + attrValue
				+ "]";
	}
	
	public String des(){
		return des ;
	}
	
	

}