package game.utils;

import java.awt.Color;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

public class Constant {
	//Field字段
	public static String[] attrAry = {"名号","等级","境界","经验","血量","法力","攻击","防御"} ;
	public static String[] funAry = {"状态","技能","背包","人物","地图","副本","存档"} ;
	public static String[] bagClassifyAry = {"兵器","防具","技能书","材料"} ;
	public static String[] bagClassify = {"equip","armor","skill","material"} ;
	public static String[] equipAttrAry = {"名字","品质","种类","数量"} ;
	
	public static final String[] partDes = { "兵器", "头盔", "项链", "上衣", "戒指", "腰带", "裤子", "鞋子" };

	
	/** 一级属性名 */
	public static String[] oneAttr = {"体质","精力","力量","敏捷","幸运"} ;
	/** 二级属性名 */
	public static String[] twoAttr = {"血量","法力","攻击","防御"} ;
	/** 特殊属性名 */
	public static String[] spAttr = {"暴击率","吸血比例","经验加成","金钱加成","爆率加成","宠物加成"} ;

	public static String[] equipAdd = {
		"体力","精力","力量","敏捷","幸运","血量","法力","攻击","防御","暴击率","吸血率","经验加成","金钱加成","爆率加成","宠物加成",
		"血量加成","法力加成","攻击加成","防御加成"
	};
	
	/** npc品质描述,5为普通是颜色显示5为黑色 */
	public static final String[] npcTypeDes = { "普通","精英","稀有","霸主","王者","普通" } ;
	
	/**
	 *	白色 蓝色 紫色 橙色 红色
	 *  装备品质对应的颜色 
	 */
	public static Color[] equipColor = {Color.white,new Color(0,192,255),Color.magenta,Color.orange,new Color(242,12,0)} ;
	
	
	//红色 255,51,0
	
	public static Color[] colorAry = {new Color(233, 221, 183),new Color(255, 251, 240),new Color(240, 252, 255),new Color(238, 222, 176),new Color(243, 249, 241),new Color(255, 198, 75)} ;
	
	
	/** 用来控制装备的生成  */
	/** 一级属性向二级属性转换的比率  hp mp atk def baoji  */
	public final static double[] oneToTwo = { 10 , 2.5 , 4 , 1 , 0.25  } ; 
	
	/** 每升一级对 一级 属性值的增长 体质，精神，力量，敏捷，幸运*/
	public final static int[] lvUpValue = {5,5,5,5,1} ;
	
	/** 设定不同品质怪物对于玩家属性比值 */
	public final static double[] npcAttrLv = {0.6,0.8,1.0,1.2,1.4} ;
	
	/** 不同品质敌人给玩家提供经验的比率 */
	public final static double[] npcExpLv = {1,2,3,4,5} ;
	
	public static Properties getUserInfo(){
		Properties pps = new Properties();
		try {
			pps.load(new FileInputStream("src/com/test/fight/base/entity/info.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return pps ;
	}

}
