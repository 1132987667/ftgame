package game.utils;

import java.util.Random;

import game.control.GameControl;
import game.entity.NPC;

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
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseTili(int rank){
		int value = 15 + rank*5 ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseJingli(int rank){
		int value = 15 + rank*5 ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseLi(int rank){
		int value = 15 + rank*5 ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseMin(int rank){
		int value = 15 + rank*5 ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseLucky(int rank){
		int value = 9 + rank ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseHp(int rank){
		int baseValue = getBaseTili(rank) ;
		int value = SUtils.reDouPoint(Constant.oneToTwo[0]*baseValue) ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseMp(int rank){
		int baseValue = getBaseJingli(rank) ;
		int value = SUtils.reDouPoint(Constant.oneToTwo[1]*baseValue) ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseAtk(int rank){
		int baseValue = getBaseLi(rank) ;
		int value = SUtils.reDouPoint(Constant.oneToTwo[2]*baseValue) ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseDef(int rank){
		int baseValue = getBaseMin(rank) ;
		int value = SUtils.reDouPoint(Constant.oneToTwo[3]*baseValue) ;
		return value ;
	}
	
	/**
	 * 
	 * @param rank
	 * @return 
	 */
	public static int getBaseBaoji(int rank){
		int baseValue = getBaseLi(rank) ;
		int value = SUtils.reDouPoint(Constant.oneToTwo[4]*baseValue) ;
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
		double radio = Constant.npcAttrLv[type] ;
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

}
