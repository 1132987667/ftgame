package game.control;

import game.entity.Equip;
import game.entity.NPC;
import game.entity.Player;
import game.listener.NpcListener;
import game.utils.Constant;
import game.view.frame.SpFrame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	private NpcListener npcListener = null ;
	
	private Player player = gameControl.getPlayer() ;
	
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









	public NpcListener getNpcListener() {
		return npcListener;
	}









	public void setNpcListener(NpcListener npcListener) {
		this.npcListener = npcListener;
	}
	
	
}
