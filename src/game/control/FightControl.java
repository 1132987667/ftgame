package game.control;

import game.entity.Equip;
import game.entity.NPC;
import game.entity.Player;
import game.listener.ObjLner;
import game.utils.SUtils;
import game.view.frame.SpFrame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 战斗控制类
 * 主要作用:
 * 1: 返回战斗结果
 * 2: 得到战斗过程
 * 3: 控制战斗面板
 * 4: 
 * @author yilong22315
 */
public class FightControl {
	/***************************************************************
	 *    变量的声明
	 ***************************************************************/
	/** 当前敌人实体类 */
	private NPC npc = null ;
	/** 判断战斗是否胜利的标志,默认为false */
	private boolean victorySign = false ;
	/** 战斗信息 */
	private String combatProcess ;
	/** 战斗面板 */
	private SpFrame ftFrame ;
	/** 战斗奖励 */
	private List<Equip> equipList = new ArrayList<>() ;
	/** 战斗金钱 */
	private int money = 0 ;
	/** 战斗经验 */
	private int exp = 0 ;
	
	private static FightControl fightControl = new FightControl();
	
	private GameControl gameControl = GameControl.getInstance();
	
	private EquipControl equipControl = gameControl.equipControl;
	
	private ObjLner npcListener = null ;
	
	private Player player = gameControl.getPlayer() ;
	
	private static Random rd = new Random(System.currentTimeMillis());
	
	/*****************************************************
	 *  方法开始!
	 ******************************************************/
	/**
	 * 战斗结束
	 * 任务:
	 * 	隐藏战斗面板
	 * 	恢复父面板的控制
	 * 	移除被击杀的npc
	 *  添加和显示敌人掉落的装备
	 *  增加敌人给予的经验和金钱
	 * 			
	 */
	public void battleOver(){
		ftFrame.setVisible(false);
		gameControl.restore();
		if(victorySign){
			SoundControl.ftMuc(14);//拔刀动作 
			if(npcListener!=null){
				npcListener.remove(); 
			}
			int exp = npc.getCombatExp();
			int money= npc.getCombatMoney();
			/** 玩家获得经验 */
			player.gainExp(exp);
			/** 玩家获得金钱 */
			gameControl.reloadPlayerAttr();
			player.gainMoney(money);
			gameControl.append("你得到了"+exp+"点经验,得到了"+money+"金!\n", 0);
			List<Equip> dropList = fightControl.KillRewards(player);
			for (int i = 0; i < dropList.size(); i++) {
				if(dropList.get(i)!=null){
					gameControl.append("你获得了"+"【", 0);
					int type = dropList.get(i).getType() ;
					type = type==0?5:type;
					gameControl.append(Equip.typeDesAry[dropList.get(i).getType()], type+10);
					gameControl.append("】"+dropList.get(i).getName()+"\n", 0);
					player.getEquipBag().add(dropList.get(i));
					Collections.sort(player.getEquipBag());
				}
			}
			SoundControl.ftMuc(12);//胜利
		}else{
			SoundControl.ftMuc(13);//拔刀动作 
		}
	}
	
	/** 得到击杀奖励 */
	public List<Equip> KillRewards(Player player){
		int type = npc.getType() ;
		int rank = npc.getRank() ;
		List<Equip> list =equipControl.dropPredict(type, rank, player);
		
		List<Equip> spList = npc.getSpEquipList();
		list.addAll(equipControl.spDropPredict(spList, type));
		System.out.println("共计掉落装备"+list.size());
		return list ;
	}
	
	/**
	 * 计算 一次攻击 发生的实际情况 0 友方 1敌方 1暴击 2普通攻击 3miss
	 * @param player
	 * @param npc
	 * @param type
	 * @return
	 */
	public static Map<String, Object> fightHelper(Player player, NPC npc, int type) {
		Map<String, Object> map = new HashMap<>();
		/** 此次攻击的攻击加成 两位小数 */
		double atkAdd = SUtils.getTwoDecimal((rd.nextInt(5) + 7) * 0.1);
		/** value造成的伤害显示 atkType伤害的类型1：普攻 2：暴击 3:miss */
		String value = "";
		int atkType = 0;
		int atk = 0, def = 0, baoji = 0, atkDemage = 0;
		/** atkAdd 这次攻击的攻击加成 atkDemage这次攻击造成伤害数值 lv命中率 rankLv等级比 */
		double lv = 0.0, rankLv = 0.0;
		if (type == 0) {
			atk = player.getAttack();
			baoji = player.getBaoji();
			if (player.getRank() < 6) {
				rankLv = player.getRank() * 3.0 / (player.getRank() + npc.getRank());
			} else {
				rankLv = player.getRank() * 2.0 / (player.getRank() + npc.getRank());
			}
			def = npc.getDefense();
		} else {
			atk = npc.getAttack();
			baoji = npc.getBaoji();
			if (player.getRank() < 6) {
				rankLv = npc.getRank() * 2.0 / (player.getRank() * 2 + npc.getRank());
			} else {
				rankLv = npc.getRank() * 2.0 / (player.getRank() + npc.getRank());
			}
			def = player.getDefense();
		}
		/**
		 * 先判断是否暴击 暴击双倍伤害
		 * 
		 */
		int rdValue = rd.nextInt(100) + 1;
		if (rdValue < baoji) {
			atkDemage = (int) ((atkAdd * 2) * atk);
			value = SUtils.conObjToStr(atkDemage);
			atkType = 2;
		} else {
			/** 判断是否普攻击 */
			lv = SUtils.getTwoDecimal(atk * 100 / (atk + def));
			lv = lv * rankLv;
			rdValue = rd.nextInt(100) + 1;
			// System.out.println("没有打出暴击,判断是否命中,随机数为:"+rdValue+",命中率为:"+lv);
			if (rdValue < lv) {
				atkAdd = (rd.nextInt(5) + 7) * 0.1;
				atkDemage = (int) (atkAdd * atk);
				value = SUtils.conObjToStr(atkDemage);
				// System.out.println("耶,打中了,基数为"+atkAdd+",伤害值是:"+value);
				atkType = 1;
			} else {
				atkType = 3;
			}
		}
		/** 将战斗信息写入日志 */
		if (type == 0) {
			SUtils.writeFtLog(
					"我方进行攻击,我攻击力为:" + atk + ",我的暴击率为:" + baoji + ",敌人防御力为:" + def + ",此次攻击加成为:" + atkAdd + "\n");
		} else {
			SUtils.writeFtLog(
					"敌方进行攻击,敌方攻击力为:" + atk + ",敌方的暴击率为:" + baoji + ",我的防御力为:" + def + ",此次攻击加成为:" + atkAdd + "\n");
		}
		SUtils.writeFtLog("此次攻击命中率为:" + lv + ",此次攻击判定数数值为:" + rdValue + "\n");

		map.put("atkType", atkType);
		map.put("value", value);
		map.put("atkDemage", atkDemage);
		return map;
	}
	
	
	
	
	public static FightControl getInstance(){
		return fightControl ;
	}
	
	private FightControl() {
	}

	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public boolean isVictorySign() {
		return victorySign;
	}

	public void setVictorySign(boolean victorySign) {
		this.victorySign = victorySign;
	}

	public String getCombatProcess() {
		return combatProcess;
	}

	public void setCombatProcess(String combatProcess) {
		this.combatProcess = combatProcess;
	}

	public SpFrame getFtFrame() {
		return ftFrame;
	}

	public void setFtFrame(SpFrame ftFrame) {
		this.ftFrame = ftFrame;
	}

	public List<Equip> getEquipList() {
		return equipList;
	}

	public void setEquipList(List<Equip> equipList) {
		this.equipList = equipList;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public static FightControl getFightControl() {
		return fightControl;
	}

	public static void setFightControl(FightControl fightControl) {
		FightControl.fightControl = fightControl;
	}









	public ObjLner getNpcListener() {
		return npcListener;
	}









	public void setNpcListener(ObjLner npcListener) {
		this.npcListener = npcListener;
	}
	
	
}
