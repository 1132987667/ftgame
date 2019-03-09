package game.utils;

import java.awt.Color;
import java.awt.Font;

public class C {
	public static int Width  = 1024 ;
	public static int height  = 600 ;
	
	public static final int INSET = 4 ;
	
	//Field字段
	public static String[] attrAry = {"名号","等级","境界","经验","血量","法力","攻击","防御"} ;
	
	public static final Font Kai_S = new Font("楷体", Font.PLAIN, 12);
	public static final Font Kai_M = new Font("楷体", Font.PLAIN, 14);
	public static final Font Kai_L = new Font("楷体", Font.PLAIN, 16);
	public static final Font Kai_XL = new Font("楷体", Font.PLAIN, 18);
	
	public static final Font Y_S = new Font("幼圆", Font.PLAIN, 12);
	public static final Font Y_M = new Font("幼圆", Font.PLAIN, 14);
	public static final Font Y_L = new Font("幼圆", Font.PLAIN, 16);
	public static final Font Y_XL = new Font("幼圆", Font.PLAIN, 18);
	
	public static final Font LS_S = new Font("隶书", Font.PLAIN, 12);
	public static final Font LS_M = new Font("隶书", Font.PLAIN, 14);
	public static final Font LS_L = new Font("隶书", Font.PLAIN, 16);
	public static final Font LS_XL = new Font("隶书", Font.PLAIN, 18);
	
	public static final Font YH_S = new Font("微软雅黑", Font.PLAIN, 12);
	public static final Font YH_M = new Font("微软雅黑", Font.PLAIN, 14);
	public static final Font YH_L = new Font("微软雅黑", Font.PLAIN, 16);
	public static final Font YH_XL = new Font("微软雅黑", Font.PLAIN, 18);
	
	public static final Font Courier_New_S = new Font("隶书", Font.PLAIN, 12);
	public static final Font Courier_New_M = new Font("隶书", Font.PLAIN, 14);
	public static final Font Courier_New_L = new Font("隶书", Font.PLAIN, 16);
	public static final Font Courier_New_XL = new Font("隶书", Font.PLAIN, 18);
	
	public static final String SState = "状态[V]" ;
	public static final String SSkill = "技能" ;
	public static final String SBag = "背包[B]" ;
	public static final String STask = "任务[T]" ;
	public static final String SJiangHu = "红尘[J]" ;
	public static final String SFuben = "副本[F]" ;
	public static final String SMap = "地图[M]" ;
	public static final String SArchive = "存档" ;
	public static final String SConsole = "控制台" ;
	
	
	public static String[] funAry = {SState, SSkill, SBag, STask, SJiangHu, SFuben, SArchive, SMap, SConsole} ;
	/** {"状态","技能","背包","任务","红尘","副本","存档","地图"} */
	public static final int State = 0 ;
	public static final int Skill = 1 ;
	public static final int Bag = 2 ;
	public static final int Task = 3 ;
	public static final int JiangHu = 4 ;
	public static final int Fuben = 5 ;
	public static final int Archive =6 ;
	public static final int Map = 7 ;
	public static final int Fight = 9 ;
	
	
	public static String[] bagClassifyAry = {"兵器","防具","技能书","材料"} ;
	public static String[] bagClassify = {"equip","armor","skill","material"} ;
	public static String[] equipAttrAry = {"名字","品质","种类","数量"} ;
	
	public static final String[] partDes = { "兵器", "头盔", "项链", "上衣", "戒指", "腰带", "裤子", "鞋子" };
	
	public static final String[] STATE = {"气动","长息","练气","筑基","结丹","元婴","出窍","分神","大成","渡劫"} ;
	
	/** 一级属性名 */
	public static String[] oneAttr = {"体质","灵力","力量","敏捷","幸运"} ;
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
	
	
	public static final Color defBacColor = new Color(249, 249, 249);
	
	/** 用来控制装备的生成  */
	/** 一级属性向二级属性转换的比率  hp mp atk def baoji  */
	public final static double[] oneToTwo = { 16 , 2.5 , 4 , 1 , 0.25  } ; 
	
	/** 每升一级对 一级 属性值的增长 体质，精神，力量，敏捷，幸运*/
	public final static int[] lvUpValue = {5,5,5,5,1} ;
	
	/** 设定不同品质怪物对于玩家属性比值 */
	public final static double[] npcAttrLv = {0.6,0.8,1.0,1.2,1.4} ;
	
	/** 不同品质敌人给玩家提供经验的比率 */
	public final static double[] npcExpLv = {1,2,3,4,5} ;
	
	/** 功法品质 */
	public static String[] gongQu = {"黄品","玄品","地品","天品"} ;
	
	/** 技能品质对技能属性加成比例 */
	public static int[] skillQuaAdd = {2,3,4,5} ;
	
	
	
	public static final Font LiShu = new Font("隶书", 0, 16);
	
	/** 任务状态 */
	/** 任务还未开启 */
	public static final int Task_0 = 0 ;
	/** 满足任务条件 */
	public static final int Task_FullCase = 1 ;
	/** 接受任务 */
	public static final int Task_Accept = 2 ;
	/** 拒绝任务 */
	public static final int Task_Refused = 3 ;
	/** 任务完成 */
	public static final int Task_Complete = 4 ;
	
	
	
	
	
	/***
	 * 属性常量
	 */
	public static final int tiLi    = 101 ;
	public static final int jingLi  = 102 ;
	public static final int li      = 103 ;
	public static final int min     = 104 ;
	public static final int lucky   = 105 ;
	public static final int hp      = 106 ;
	public static final int mp      = 107 ;
	public static final int atk     = 108 ;
	public static final int def     = 109 ;
	public static final int baoJi   = 110 ;
	/** 金钱掉落加成 */
	public static final int monAdd = 111 ;
	/** 装备掉落加成 */
	public static final int eqDrop  = 112 ;
	/** 经验加成 */
	public static final int expAdd  = 113 ;
	/** 物理免伤 */
	public static final int wms      = 114 ;
	/** 法术免伤 */
	public static final int fms      = 119 ;
	/** 二连击 */
	public static final int lj2     = 115 ;
	/** 三连击 */
	public static final int lj3     = 116 ;
	public static final int suck    = 117 ;
	public static final int speed    = 118 ;
	
	public static final int reHp      = 120 ;/** 生命回复 */
	public static final int reMp      = 121 ;/** 法力回复 */
	public static final int atkUp     = 122 ;/** 攻击力增加 */
	public static final int atkDown   = 123 ;/** 攻击力下降 */
	public static final int defUp     = 124 ;/** 防御力上升 */
	public static final int defDown   = 125 ;/** 防御力下降 */
	public static final int speedUp   = 126 ;/** 速度下降 */
	public static final int speedDown = 127 ;/** 速度上升 */
	public static final int hitUp     = 128 ;/** 命中率上升 */
	public static final int hitDown   = 129 ;/** 命中率下降 */
	public static final int dodgeUp   = 130 ;/** 闪避率上升 */
	public static final int dodgeDown = 131 ;/** 闪避率下降 */
	public static final int wuRebound = 132 ;/** 物理伤害反弹 */
	public static final int bufRe     = 133 ;/** 移除所有增益状态 */
	public static final int debufRe   = 134 ;/** 移除所有减益状态 */
	public static final int daze      = 135 ;/** 眩晕 */
	
	
	public static final String TiLi    = "tiLi" ;
	public static final String JingLi  = "jingLi" ;
	public static final String Li      = "li" ;
	public static final String Min     = "min" ;
	public static final String Lucky   = "lucky" ;
	public static final String Hp      = "hp" ;
	public static final String Mp      = "mp" ;
	public static final String Atk     = "atk" ;
	public static final String Def     = "def" ;
	public static final String BaoJi   = "baoJi" ;
	/** 金钱掉落加成 */
	public static final String MonAdd = "monAdd" ;
	/** 装备掉落加成 */
	public static final String EqDrop  = "eqDrop" ;
	/** 经验加成 */
	public static final String ExpAdd  = "expAdd" ;
	/** 物理免伤 */
	public static final String WMs      = "wms" ;
	/** 法术免伤 */
	public static final String FMs      = "fms" ;
	/** 物理免伤百分比 */
	public static final String WMsR     = "wmsr" ;
	/** 法术免伤百分比 */
	public static final String FMsR     = "fmsr" ;
	/** 二连击 */
	public static final String Lj2     = "lj2" ;
	/** 三连击 */
	public static final String Lj3     = "lj3" ;
	public static final String Suck    = "suck" ;
	public static final String Speed    = "speed" ;
	
	public static final String ReHp      = "reHp" ;/** 生命回复 */
	public static final String ReMp      = "reMp" ;/** 法力回复 */
	public static final String AtkUp     = "atkUp" ;/** 攻击力增加 */
	public static final String AtkDown   = "atkDown" ;/** 攻击力下降 */
	public static final String DefUp     = "defUp" ;/** 防御力上升 */
	public static final String DefDown   = "defDown" ;/** 防御力下降 */
	public static final String SpeedUp   = "speedUp" ;/** 速度下降 */
	public static final String SpeedDown = "speedDown" ;/** 速度上升 */
	public static final String HitUp     = "hitUp" ;/** 命中率上升 */
	public static final String HitDown   = "hitDown" ;/** 命中率下降 */
	public static final String DodgeUp   = "dodgeUp" ;/** 闪避率上升 */
	public static final String DodgeDown = "dodgeDown" ;/** 闪避率下降 */
	public static final String WuRebound = "wuRebound" ;/** 物理伤害反弹 */
	public static final String BufRe     = "bufRe" ;/** 移除所有增益状态 */
	public static final String DebufRe   = "debufRe" ;/** 移除所有减益状态 */
	public static final String Daze      = "daze" ;/** 眩晕 */
	public static final String JianAdd   = "jianAdd" ;/** 剑系加成 */
	
	
	public static final int xml_Gong     = 1 ;
	public static final int xml_Skill    = 2 ;
	public static final int xml_Equip    = 3 ;
	public static final int xml_Npc      = 4 ;
	public static final int xml_Task     = 5 ;
	public static final int xml_Fuben    = 6 ;
	public static final int xml_EquipSet = 7 ;
	public static final int xml_World    = 8 ;
	
	
	
	
	public static final int Hurt_Skill = 0 ;
	/** 恢复技能 */
	public static final int Restore_Skill = 1 ;
	/** 控制技能 */
	public static final int Control_Skill = 2 ;
	/** 被动技能 */
	public static final int Passive_Skill = 3 ;
	
	
	/** 单双群 */
	public static final int Single = 0 ;
	public static final int Double = 1 ;
	public static final int Group  = 2 ;
	
	public static final String[] buffNames = {
			"恢复","泉涌","战勇","轻身","坚守"
	};
	
	/** 物品通用类型 */
	int Weapon      = 0 ; //武器 
	int Armor       = 1 ; //防具 
	int GongBook    = 3 ; //功法书 
	int SkillBook   = 4 ; //技能书
	int Mission     = 5 ; //任务物品 
	int Costa       = 6 ; //消耗品
	int Material    = 7 ; //材料
	int Special     = 8 ; //特殊物品 
	int Design      = 9 ; //设计图
	int Pet         = 10 ; //宠物 
	
	/** 背景图片设置 */
	public static String gameBack = "/game/img/back/GameBack.png" ;
	public static String mapBack = "" ;
	public static String npcBack = "" ;
	public static String actionBack = "" ;
	
	
	
	
	/** 物理免伤分为4级，这是第四级的效果，每级递减0.2 */
	public static int[] avoidWu = { 40,195,350,505,660,815,980,1135,1290,1445,1600,1755,1810,1965,2200 } ;
	
	
	
	
	
	public static String view = "查看" ;
	public static String talk = "交谈" ;
	public static String fight = "战斗" ;
	public static String give = "给予" ;
	public static String dazuo = "打坐" ;
	public static String lianwu = "练武" ;
	public static String ting = "停功" ;
	public static String cure = "治疗" ;
	public static String dig = "挖掘" ;
	public static String fishing = "钓鱼" ;
	public static String cutDown = "砍伐" ;
	public static String rest = "休息" ;
	public static String liandan = "炼丹" ;
	public static String cook = "烹饪" ;
	public static String ask = "询问" ;
	public static String eat = "食用" ;
	
	
	
	public static String[] actionStr = {"查看","交谈","战斗","给予","交易","任务","钓鱼","练武","修炼","挖矿","狩猎","砍伐","休息"} ;

	public static String[] fixAcionStr = {dazuo, lianwu, ting, cure, dig, fishing,cutDown, liandan, cook} ;
	public static String[] fixAcionTip = {"修炼内功","修炼外功和技能","停止修炼","为自己治疗","挖矿","钓鱼","砍伐","炼丹 ","烹饪"} ;
	
	
	
	
	
	
	
	
	
	
	
	
}
