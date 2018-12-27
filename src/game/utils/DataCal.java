package game.utils;

import java.util.Random;

import game.control.EquipControl;
import game.control.GameControl;
import game.entity.AddAttrs;
import game.entity.Equip;
import game.entity.Gong;
import game.entity.NPC;
import game.entity.Player;

/**
 * 计算游戏中数据的控制类
 * @author yilong22315
 *
 */
public class DataCal {
	public static final int TILI   = 0 ;
	public static final int JINGLi = 1 ;
	public static final int LI     = 2 ;
	public static final int MIN    = 3 ;
	public static final int LUCKY  = 4 ;
	public static final int HP     = 5 ;
	public static final int MP     = 6 ;
	public static final int ATK    = 7 ;
	public static final int DEF    = 8 ;
	public static final int BAOJI  = 9 ;
	
	private GameControl gameControl = null ;
	private static int initValue = 15 ; 
	/**
	 * @param rank
	 * @return 
	 */
	public static int getBaseTili(int rank){
		int value = initValue + rank * C.lvUpValue[0] ;
		return value ;
	}
	
	/**
	 * @param rank
	 * @return 
	 */
	public static int getBaseJingli(int rank){
		int value = initValue + rank * C.lvUpValue[1] ;
		return value ;
	}
	
	/**
	 * @param rank
	 * @return 
	 */
	public static int getBaseLi(int rank){
		int value = initValue + rank * C.lvUpValue[2] ;
		return value ;
	}
	
	/**
	 * @param rank
	 * @return 
	 */
	public static int getBaseMin(int rank){
		int value = initValue + rank * C.lvUpValue[3] ;
		return value ;
	}
	
	/**
	 * @param rank
	 * @return 
	 */
	public static int getBaseLucky(int rank){
		int value = 9 + rank * C.lvUpValue[4];
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseHp(int rank){
		int baseValue = getBaseTili(rank) ;
		int value = SUtils.reDouPoint(C.oneToTwo[0] * baseValue) ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseMp(int rank){
		int baseValue = getBaseJingli(rank) ;
		int value = SUtils.reDouPoint(C.oneToTwo[1] * baseValue) ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseAtk(int rank){
		int baseValue = getBaseLi(rank) ;
		int value = SUtils.reDouPoint(C.oneToTwo[2] * baseValue) ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseDef(int rank){
		int baseValue = getBaseMin(rank) ;
		int value = SUtils.reDouPoint(C.oneToTwo[3] * baseValue) ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseBaoji(int rank){
		int baseValue = getBaseLi(rank) ;
		int value = SUtils.reDouPoint(C.oneToTwo[4] * baseValue) ;
		return value ;
	}
	
	public static int getHP(int tiLi) {
		int value = SUtils.reDouPoint(tiLi * C.oneToTwo[0]);
		return value ;
	}
	
	public static int getMp(int jingLi) {
		int value = SUtils.reDouPoint(jingLi * C.oneToTwo[1]);
		return value ;
	}
	
	public static int getAtk(int li) {
		int value = SUtils.reDouPoint(li * C.oneToTwo[2]);
		return value ;
	}
	
	public static int getDef(int min) {
		int value = SUtils.reDouPoint(min * C.oneToTwo[3]);
		return value ;
	}
	
	public static int getBaoji(int lucky) {
		int value = SUtils.reDouPoint(lucky * C.oneToTwo[4]);
		return value ;
	}
	
	/**
	 * 设置npc的基本属性
	 * @param npc
	 */
	public static void setNpcBaseAttr(NPC npc){
		if(npc==null){
			throw new IllegalAccessError("npc为null");
		}
		int rank = npc.getRank() ;
		int type = npc.getType() ;
		double radio = C.npcAttrLv[type] ;
		int tili = SUtils.reDouPoint(getBaseTili(rank)*radio);
		int jingli = SUtils.reDouPoint(getBaseJingli(rank)*radio);
		int li = SUtils.reDouPoint(getBaseLi(rank)*radio);
		int min = SUtils.reDouPoint(getBaseMin(rank)*radio);
		int lucky = SUtils.reDouPoint(getBaseLucky(rank)*radio);
		int hp = SUtils.reDouPoint(getBaseHp(rank)*radio);
		int mp = SUtils.reDouPoint(getBaseMp(rank)*radio);
		int atk = SUtils.reDouPoint(getBaseAtk(rank)*radio);
		int def = SUtils.reDouPoint(getBaseDef(rank)*radio);
		int baoji = SUtils.reDouPoint(getBaseBaoji(rank)*radio);
		npc.setTili(tili);
		npc.setJingli(jingli);
		npc.setLi(li);
		npc.setMin(min);
		npc.setLucky(lucky);
		npc.setHp(hp);
		npc.setMp(mp);
		npc.setAttack(atk);
		npc.setDefense(def);
		npc.setBaoji(baoji);
	}

	/**
	 * 得到每个等级升级所需要的经验值
	 * @param rank
	 * @return expValue 经验值
	 */
	public static int getUpgradeExp(int rank){
		int expValue = (int) Math.floor(((rank-1)*(rank-1)*20)/5*(rank+50));
		return expValue; 
	}

	/**
	 * 得到不同品质npc掉落金钱
	 * @param rank
	 * @param type
	 * @return
	 */
	public static int getNpcMoney(int myRank,int foeRank,int type){
		int money = foeRank*100 ;
		money*=type+1 ;
		money/=5;
		if(myRank-foeRank>10){
			money*=0.1;
		}else{
			money = SUtils.reDouPoint(money*(1-(myRank-foeRank)*0.1));
		}
		return money ;
	}
	
	/**
	 * 根据玩家幸运值得到遇见怪物品质
	 * @param lucky
	 * @return
	 */
	public static int rdNpcType(int lucky){
		int npcType = 0 ;
		int type0 = 40 ;
		int type1 = 32 ;
		int type2 = 16 ;
		int type3 = 8 ;
		int type4 = 4 ;
		int lv = lucky/25 ;
		if(lv<=4){
			type4 = type4 + 2*lv ;
			type3 = type3 + 2*lv ;
			type2 = type2 + 2*lv ;
			type1 = type1 + 2*lv ;
			type0 = 100 - type1 - type2 - type3 - type4 ;
		}else{
			type0 = 8 ;
			type4 = type4 + 2*lv ;
			type3 = type3 + 2*lv ;
			type2 = type2 + 2*lv ;
			type1 = 100 - type0 - type2 - type3 - type4 ;
		}
		int rdNum = new Random().nextInt(100)+1;
		if (rdNum<type4) {
			npcType = 4;
		} else if (type4<rdNum&&rdNum<type3+type4) {
			npcType = 3;
		} else if (type4+type3<rdNum&&rdNum<type3+type4+type2) {
			npcType = 2;
		} else if (type4+type3+type2<rdNum&&rdNum<type3+type4+type2+type1) {
			npcType = 1;
		}else{
			npcType = 0;
		}
		return npcType;
	}

	/**
	 * 升级功法需要的经验值
	 * @param rank
	 * @return
	 */
	public int getUpgradeGongExp(int rank) {
		int neenKill = rank+1 ;
		int singleExp = SUtils.reDouPoint(Math.sqrt(rank*rank*rank/2)*rank);
		int expValue = neenKill*singleExp ;
		return expValue ;  
	}
	
	/** 得到请教所需要的经验 */
	public int getLearnGongExp(int rank) {
		return getUpgradeGongExp(rank)/3 ;
	}
	
	/**
	 * 输入功法境界等级
	 * 每个境界对属性的加成不一样，越来越多
	 * 每个境界对属性的提升			
	 * 	1	0.02		
	 *	2	0.06		
	 *	3	0.09		
	 *	4	0.13		
	 *	5	0.16		
	 *	6	0.2		
	 *	7	0.23		
	 *	8	0.27		
	 *	9	0.3		
	 *	10	0.34		
	 *	11	0.37		
	 *	12	0.41		
	 *	13	0.44		
	 *	14	0.48		
	 *	15	0.51		
	 * 返回 权值为10,品质为3-天时的境界加成
	 * @return
	 */
	public double getSingleAdd(int statusTier) {
		if(statusTier<=0) {
			return 0;
		}
		double base = 0.02 ;
		double baseAdd = 0.025*(statusTier-1) ;
		double deepAdd = Math.sqrt((statusTier-1)*statusTier)*0.01;
		double value = SUtils.formatDou((base+baseAdd+deepAdd),2) ;
		return value ;
	}
	
	/**
	 * 得到功法对某个10权值属性的加成
	 * 
	 * @param statusRank 境界等级
	 * @param weight 权值
	 * @return
	 */
	public double getGongPartAdd(int statusRank, int weight, int qua) {
		double base = 0.2 ;
		for (int i = 0; i < 3-qua; i++) {
			base *= 0.8 ;
		}
		double result = base;
		/** 上一层功法境界 */
		int statusTier = (statusRank-1) / 4 ;
		for (int i = 1; i < statusTier+1; i++) {
			/** 当前权值 */
			double add = getSingleAdd(i) *0.1 * weight ;
			for (int j = 0; j < 3-qua; j++) {
				add *= 0.8 ;
			}
			result += 4 * add;
		}
		double add = getSingleAdd(statusTier+1) *0.1 * weight ;
		for (int i = 0; i < 3-qua; i++) {
			add *= 0.8 ;
		}
		double little = (statusRank-1)%4+1 ; 
		result +=  add * little ;
		result = SUtils.formatDou(result, 2) ;
		return result;
	}
	
	/**
	 * 得到不同等级之下人物的属性
	 * @param attrName 属性名
	 * @param rank 人物等级
	 * @return
	 */
	public static int getBaseAttrByRank(String attrName, int rank) {
		int value = 0;
		Player player = new Player();
		player.setRank(rank);
		player.reloadBaseAttr();
		switch (attrName) {
		case C.TiLi:
			value = player.getTili();
			break;
		case C.JingLi:
			value = player.getJingli();
			break;
		case C.Li:
			value = player.getLi();
			break;
		case C.Min:
			value = player.getMin();
			break;
		case C.Hp:
			value = player.getHp();
			break;
		case C.Mp:
			value = player.getMp();
			break;
		case C.Atk:
			value = player.getAttack();
			break;
		case C.Def:
			value = player.getDefense();
			break;
		case C.BaoJi:
			value = player.getBaoji();
			break;
		case C.Speed:
			value = player.getSpeed();
			break;
		default:
			break;
		}
		return value;
	}
	
	/** 计算属性 */
	public int calAddValue(String name, double add, int rank) {
		int value = getBaseAttrByRank(name, rank);
		System.out.println("name:"+name+",add:"+add+",rank:"+rank+",value:"+value);
		value = SUtils.reDouPoint(add*value);
		return value ;
	}
	
	/** 计算属性 */
	public int calAddValue(String name, Gong gong, int weight) {
		double add = getGongPartAdd(gong.getCurTier(), weight, gong.getQua());
		int value = getBaseAttrByRank(name, gong.getCurTier());
		value = SUtils.reDouPoint(add*value);
		return value ;
	}
	
	/**
	 * 当前为权值为4，品质为天的物理免伤
	 * @param statusRank 境界等级
	 * @param qua 品质
	 * @return
	 */
	public int getDefWuAvoid(int statusRank, int qua, int quanzhi) {
		statusRank = statusRank/4 ;
		int value = C.avoidWu[statusRank];
		for (int i = 3-qua; i >0; i--) {
			value *= 0.8 ;
		}
		value = value / 4 * quanzhi ;
		return value ;
	}
	
	/**
	 * 得到不同权值和品质的功法的物理免疫率加成
	 * @return
	 */
	public int getWuAvoidRatio(int statusRank, int qua, int quanzhi) {
		int baseValue = 2 ;
		double baseAdd = 5 ;
		if(quanzhi==8||quanzhi==7) baseValue = 5 ;
		if(quanzhi==6||quanzhi==5) baseValue = 4 ;
		if(quanzhi==4||quanzhi==3) baseValue = 3 ;
		for (int i = 0; i < 8-quanzhi; i++) {
			baseAdd*=0.8;
		}
		for (int i = 0; i < 3-qua; i++) {
			baseAdd*=0.8;
		}
		baseValue=SUtils.reDouPoint(baseValue+(statusRank/4)*baseAdd);
		return baseValue ;
	}
	
	/**
	 * qua
	 * tier
	 * weight
	 * @return
	 */
	public AddAttrs accGongAddAttrs(AddAttrs addAttrs, String effName, Gong gong, int weight) {
		int value = 0 ;
		switch (effName) {
		case C.TiLi:
			value = calAddValue(effName, gong, weight);
			addAttrs.tili += value ;
			break;
		case C.JingLi:
			value = calAddValue(effName, gong, weight);
			addAttrs.jingli += value ;
			break;
		case C.Li:
			value = calAddValue(effName, gong, weight);
			addAttrs.li += value ;
			break;
		case C.Min:
			value = calAddValue(effName, gong, weight);
			addAttrs.min += value ;
			break;
		case C.Lucky:
			value = calAddValue(effName, gong, weight);
			addAttrs.lucky += value ;
			break;
		case C.Hp:
			value = calAddValue(effName, gong, weight);
			addAttrs.hp += value ;
			break;
		case C.Mp:
			value = calAddValue(effName, gong, weight);
			addAttrs.mp += value ;
			break;
		case C.Atk:
			value = calAddValue(effName, gong, weight);
			addAttrs.attack += value ;
			break;
		case C.Def:
			value = calAddValue(effName, gong, weight);
			addAttrs.defense += value ;
			break;
		case C.BaoJi:
			value = calAddValue(effName, gong, weight);
			addAttrs.baoji += value ;
			break;
		case C.WMs:
			value = getDefWuAvoid(gong.getCurTier(), gong.getQua(), weight) ;
			break;
		case C.WMsR:
			value = getWuAvoidRatio(gong.getCurTier(), gong.getQua(), weight) ;
			break;
		case C.FMs:
			
		case C.ReHp:
			
		case C.ReMp:
			
		default:
			break;
		}
		return addAttrs ;
	}
	
	/**
	 * 累加属性加成
	 * @param addAttrs
	 * @param name 属性名
	 * @param attrValue 属性的值
	 * @return
	 */
	public static AddAttrs calAddAttrsValue(AddAttrs addAttrs, String name, int attrValue) {
		switch (name) {
		case "ti":
			// dataCal.calAddValue("ti", add, rank)
			break;
		case "jing":

			break;
		case "li":
			break;

		case "min":

			break;
		case "hp":
			addAttrs.hp += attrValue;
			break;
		case "mp":
			addAttrs.mp += attrValue;
			break;
		case "atk":
			addAttrs.attack += attrValue;
			break;
		case "def":
			addAttrs.defense += attrValue;
			break;
		case "baoji":
			addAttrs.baoji += attrValue;
			break;
		case "suck":
			break;
		case "reHp":/** 生命回复 */
			addAttrs.reHp += attrValue;
			break;
		case "reMp":/** 法力回复 */
			addAttrs.reMp += attrValue;
			break;
		case "atkUp":
			addAttrs.atkUp += attrValue;
			break;
		case "atkDown":
			addAttrs.atkDown += attrValue;
			break;
		case "defUp":
			addAttrs.defUp += attrValue;
			break;
		case "defDown":
			addAttrs.defDown += attrValue;
			break;
		case "speedUp":
			addAttrs.speedUp += attrValue;
			break;
		case "speedDown":
			addAttrs.speedDown += attrValue;
			break;
		case "hitUp":/** 命中上升 */
			addAttrs.hitUp += attrValue;
			break;
		case "hitDown":/** 命中下降 */
			addAttrs.hitDown += attrValue;
			break;
		case "dodgeUp":/** 闪避上升 */
			addAttrs.dodgeUp += attrValue;
			break;
		case "dodgeDown":/** 闪避下降 */
			addAttrs.dodgeDown += attrValue;
			break;
		case "wuRebound":/** 物理伤害反弹 */
			addAttrs.wuRebound += attrValue;
			break;
		case "bufRe":/** 增益移除 */
			addAttrs.bufRe += attrValue;
			break;
		case "debufRe":/** 减益移除 */
			addAttrs.debufRe += attrValue;
			break;
		case "daze":/** 眩晕 */
			addAttrs.daze += attrValue;
			break;
		default:
			break;
		}
		return addAttrs;
	}
	
	/***
	 * 重新计算人物属性
	 * @return
	 */
	public AddAttrs reloadAttr(){
		if(gameControl == null) {
			gameControl = GameControl.getInstance();
		}
		Player player = gameControl.getPlayer();
		/** 根据当前人物装备重新计算  */
		Equip[] ary = player.getEquipAry();
		
		player.reloadBaseAttr();
		EquipControl equipControl = gameControl.equipControl;
		
		AddAttrs addAttrs = null ;
		/** 累加玩家装备带来的属性加成 */
		if(ary[0].getName().trim().length()>0){
			addAttrs = equipControl.countAttr(ary[0].getAttrList());
		}else{
			addAttrs = equipControl.newADdAttrs();
		}
		
		for (int i = 1; i < ary.length; i++) {
			if(ary[i].getName().trim().length()>0){
				addAttrs = equipControl.countAttr(addAttrs, ary[i].getAttrList());
			}
		}
		/** 玩家属性和装备加成属性相加 */
		equipControl.calReallyAttr(player, addAttrs);
		return addAttrs ;
	}
	
	public static void main(String[] args) {
		/*double base = 0.02 ;
		double baseAdd = 0.015 ;
		
		for (int i = 1; i < 16; i++) {
			double deePAdd = Math.sqrt((i-1)*i)*0.01;
			double value  = base + baseAdd*(i-1) + deePAdd ;
			System.out.println(SUtils.getTwoDecimal(value));
		}*/
		
		
		DataCal dataCal = new DataCal();
		new SUtils();
		/**
		 * getSingleAdd()方法测试
		 * 成功
		 */
		/*for (int i = 1; i < 16; i++) {
			System.out.println(dataCal.getSingleAdd(i));
		}*/
		
		/**
		 * 测试方法getGongPartAdd()
		 * 成功
		 */
		/*for (int i = 0; i < 61; i++) {
			dataCal.getGongPartAdd(i, 10, 3);
		}*/
		
		/**
		 * 测试方法getWuAvoidRatio();
		 * 可以
		 */
		/*for (int i = 0; i < 15; i++) {
			System.out.println(dataCal.getWuAvoidRatio(i*4, 3, 6));
		}*/
		/**
		 * 测试方法getDefWuAvoid();
		 * 
		 */
		/*for (int i = 0; i < 15; i++) {
			System.out.println(dataCal.getDefWuAvoid(i*4, 3, 4));
		}*/
		
	}
	
	
	
	
	
	
	
	
	
}
