package game.entity;

import game.control.GameControl;
import game.utils.ArchiveUtils;
import game.utils.ReflectUtils;
import game.utils.SUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Node;
/***
 * 表示人物和小怪的实体类
 * 都具有id
 * 
 *
 */
public class NPC implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private GameControl gameControl = GameControl.getInstance();
	
	public NPC() {
		
	}
	
	private int id = 0 ;

	private String name = null  ;
	/** 描述 */
	private String des = null ;
	/** 单位 */
	private String unit = null ;
	/** 对话 */
	private String msg = null ;
	
	/** 等级(通过等级来确认经济)  */
	private int rank = -1 ;
	/** 品质 [普通,精英,稀有,霸主,王者] */
	private int type = 0 ;
	/** 是否为强化怪 */
	private boolean isIntensify = false ;
	
	/** 怪物一级属性 力,敏,体力,精力,幸运值  */
	private int li = 0 ;
	private int min = 0 ;
	private int tili = 0 ;
	private int jingli = 0 ;
	private int lucky = 0 ;
	private int hit = 0 ;
	
	/** 怪物二级属性 当前血量,蓝量,当前攻击,当前防御力,当前暴击几率 */
	private int curHp = 0 ;
	private int hp = 0 ;
	private int curMp = 0 ;
	private int mp = 0 ;
	private int curAttack = 0 ;
	private int attack = 0 ;
	private int curDefense = 0 ;
	private int defense = 0 ;
	private int baoji = 0 ;
	
	private int suck = 0 ;
	
	private String[] action = null ;
	
	/*** 角色身上装备设置  头盔 项链 上衣 腰带 裤子 鞋子*/
	private Equip weapon,helmet,necklace,coat,ring,waistband,trousers,shoes;
	private Equip[] equipAry = {weapon,helmet,necklace,coat,ring,waistband,trousers,shoes} ;
	private List<Equip>  spEquipList = null ;
	
	/**===================判断NPC功能=======================*/
	/*** 判断是否能击杀 默认为false */
	private boolean cankill = true ;
	/*** 是否为商人 默认为false */
	private boolean isSell = false ;
	/*** 能否向他学习技能 默认为false */
	private boolean canStudy = false ;
	/*** 给与 */
	private boolean canGive = false ;
	
	
	/*** ===交易数据=== */
	/** 交易模式 bestTrader[极品商人] petTrader[宠物商人] skillTrader[技能商人] commonTrader[普通商人] */
	private String sellMode = "" ;
	/** 商人是否一直出现 */
	private String appearMode = "" ;
	/** 如果商人出现是概率出现，那么，设置它出现的概率 */
	private int appearLv = 0 ;
	/** 商人货物的数量 |当为极品商人或其他商人时需要 */
	private int goodsNum = 0 ;
	/** 商人货物列表|[装备][材料][技能书][图纸][宠物蛋] */
	private List<Object> sellList = null ;
	
	/*** ===掉落设置==== */
	/** 掉落技能书 */
	private List<Skill> skillAry = null  ;
	/** 掉落装备 */
	private List<Equip> dropEquipAry = null ;
	/** 掉落金钱数 */
	private int combatMoney = 0 ;
	/** 获得经验数 */
	private int combatExp = 0 ;
	/*** 掉落宠物设置 */
	private List<Pet> petAry = null ;
	
	public void talk(){
		
	}
	
	public void give(){
		
	}
	/**
	 * 逻辑:
	 * 得到npc take动作的内容
	 * 得到npc take动作具体事件的数量
	 * 	逐个分析take具体事件
	 * 	得到take具体事件需要给与npc的物品
	 * 		加入到单次事件npc收取集合
	 *  得到take具体事件npc需要给玩家的物品
	 * 		加入具体事件npc赠送集合
	 * 	迭代npc收取集合，查看玩家是否满足条件
	 *  	满足则查看是否存在对应give事件
	 *  	存在则执行give事件
	 */
	public void task(){
		Document document = SUtils.load("src/game/xml/npc.xml");
		String xPath = "/root/npc[id='"+id+"']/action/ac[@type='take']/take" ;
		//Node node = document.selectSingleNode(xPath);
		List<Node> nodeList = document.selectNodes(xPath);
		/** npc全部的收取物品信息 */
		List<List<Item>> takeList = new ArrayList<>();
		List<List<Item>> giveList = new ArrayList<>();
		boolean giveFlag = true ;
		boolean takeFlag = true ;
		for (int i = 0; i < nodeList.size(); i++) {
			if(nodeList.get(i)!=null){
				/**
				 * need:[1001,,1];
				 * give:[1002,name,1]
				 */
				String str = nodeList.get(i).getText();
				String[] strAry = str.split(";");
				/**
				 * 
				 */
				for (int j = 0; j < strAry.length; j++) {
					/** 单个任务需要给予的物品集合 */
					List<Item> singleTakeList = new ArrayList<>();
					List<Item> singleGiveList = new ArrayList<>();
					String tempStr = strAry[j] ;
					int start = tempStr.indexOf("[");
					/** 1001,name,1 */
					String temp = tempStr.substring(start+1,tempStr.length()-1);
					/**[1001],[name],[1]*/
					String[] info = temp.split(",");
					Item item = null ;
					/*** id<8000 装备 id>8000 其他   */
					if(SUtils.conStrtoInt(info[0])>=8000){
						item = gameControl.getEquipMap().get(info[0]);
					}else{
						/** 还未进行处理 */
					}
					/** 设置数量 */
					item.setNum(SUtils.conStrtoInt(info[2]));
					item = (Item) ArchiveUtils.depthClone(item);
					if(tempStr.startsWith("need")){//玩家需要给npc的
						singleTakeList.add(item);
					}else if(strAry[j].startsWith("give")){//npc需要给玩家的
						singleGiveList.add(item);
					}
					if(singleTakeList.size()>0){
						takeList.add(singleTakeList);
					}
					if(singleGiveList.size()>0){
						giveList.add(singleGiveList);
					}
				}
			}
		}
		/** 判断玩家是否满足give条件  */
		Player player = gameControl.getPlayer();
		for (int i = 0; i < takeList.size(); i++) {
			List<Item> singleTakeList = takeList.get(i);
			/** 判断单次任务物品清单是否满足 */
			for (int j = 0; j < singleTakeList.size(); j++) {
				Item tempItem= singleTakeList.get(i);
				if(tempItem instanceof Equip){
					/** 如果不存在，不满足条件 */
					if(!player.isExistEquip(((Equip) tempItem).getId())){
						takeFlag = false ;
					}
				}else{/** 是除了装备外的其他东西，还未进行处理 */
					
				}
			}
			/** 满足则进行操作 */
			if(takeFlag){
				/** 先判断玩家背包容量是否充足 */
				if(player.isBagFull()){
					gameControl.append("你的背包容量不足，请先进行清理！\n", 2);
					return ;
				}
				for (int j = 0; j < singleTakeList.size(); j++) {
					Item tempItem= singleTakeList.get(i);
					if(tempItem instanceof Equip){
						/** 移除 */
						player.removeEquip((Equip)tempItem);
						gameControl.append("你失去了"+tempItem.getName()+"!\n", 2);
					}else{
						/** 还未进行处理 */
					}
				}
				List<Item> singleGiveList = takeList.get(i);
				for (int j = 0; j < singleGiveList.size(); j++) {
					Item tempItem = singleGiveList.get(j);
					if(tempItem instanceof Equip){
						/** 加入背包,得到了装备 */
						player.obtainEquip((Equip)tempItem);
						gameControl.append("你得到了"+tempItem.getName()+",数量为:"+tempItem.getNum()+"!\n", 3);
					}else{
						/** 还未进行处理 */
					}
				}
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	/** npc接受玩家东西 */
	public void take(){
		
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isIntensify() {
		return isIntensify;
	}

	public void setIntensify(boolean isIntensify) {
		this.isIntensify = isIntensify;
	}

	public int getLi() {
		return li;
	}

	public void setLi(int li) {
		this.li = li;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getTili() {
		return tili;
	}

	public void setTili(int tili) {
		this.tili = tili;
	}

	public int getJingli() {
		return jingli;
	}

	public void setJingli(int jingli) {
		this.jingli = jingli;
	}

	public int getLucky() {
		return lucky;
	}

	public void setLucky(int lucky) {
		this.lucky = lucky;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getCurHp() {
		return curHp;
	}

	public void setCurHp(int curHp) {
		this.curHp = curHp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getCurMp() {
		return curMp;
	}

	public void setCurMp(int curMp) {
		this.curMp = curMp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public int getCurAttack() {
		return curAttack;
	}

	public void setCurAttack(int curAttack) {
		this.curAttack = curAttack;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getCurDefense() {
		return curDefense;
	}

	public void setCurDefense(int curDefense) {
		this.curDefense = curDefense;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getBaoji() {
		return baoji;
	}

	public void setBaoji(int baoji) {
		this.baoji = baoji;
	}

	public String[] getAction() {
		return action;
	}

	public void setAction(String[] action) {
		this.action = action;
	}

	public Equip getWeapon() {
		return weapon;
	}

	public void setWeapon(Equip weapon) {
		this.weapon = weapon;
	}

	public Equip getHelmet() {
		return helmet;
	}

	public void setHelmet(Equip helmet) {
		this.helmet = helmet;
	}

	public Equip getNecklace() {
		return necklace;
	}

	public void setNecklace(Equip necklace) {
		this.necklace = necklace;
	}

	public Equip getCoat() {
		return coat;
	}

	public void setCoat(Equip coat) {
		this.coat = coat;
	}

	public Equip getWaistband() {
		return waistband;
	}

	public void setWaistband(Equip waistband) {
		this.waistband = waistband;
	}

	public Equip getTrousers() {
		return trousers;
	}

	public void setTrousers(Equip trousers) {
		this.trousers = trousers;
	}

	public Equip getShoes() {
		return shoes;
	}

	public void setShoes(Equip shoes) {
		this.shoes = shoes;
	}

	public boolean isCankill() {
		return cankill;
	}

	public void setCankill(boolean cankill) {
		this.cankill = cankill;
	}

	public boolean isSell() {
		return isSell;
	}

	public void setSell(boolean isSell) {
		this.isSell = isSell;
	}

	public boolean isCanStudy() {
		return canStudy;
	}

	public void setCanStudy(boolean canStudy) {
		this.canStudy = canStudy;
	}

	public List<Skill> getSkillAry() {
		return skillAry;
	}

	public void setSkillAry(List<Skill> skillAry) {
		this.skillAry = skillAry;
	}

	
	public Equip[] getEquipAry() {
		return equipAry;
	}

	public void setEquipAry(Equip[] equipAry) {
		this.equipAry = equipAry;
	}

	public List<Equip> getSpEquipList() {
		return spEquipList;
	}

	public void setSpEquipList(List<Equip> spEquipList) {
		this.spEquipList = spEquipList;
	}

	public List<Equip> getDropEquipAry() {
		return dropEquipAry;
	}

	public void setDropEquipAry(List<Equip> dropEquipAry) {
		this.dropEquipAry = dropEquipAry;
	}

	public int getCombatMoney() {
		return combatMoney;
	}

	public void setCombatMoney(int combatMoney) {
		this.combatMoney = combatMoney;
	}

	public int getCombatExp() {
		return combatExp;
	}

	public void setCombatExp(int combatExp) {
		this.combatExp = combatExp;
	}

	public List<Pet> getPetAry() {
		return petAry;
	}

	public void setPetAry(List<Pet> petAry) {
		this.petAry = petAry;
	}
	
	public NPC getFubenNpc(NPC npc){
		npc.cankill = true ;
		return npc ;
	}

	public int getSuck() {
		return suck;
	}

	public void setSuck(int suck) {
		this.suck = suck;
	}

	//	public Equip weapon,helmet,necklace,coat,ring,waistband,trousers,shoes;
	@Override
	public String toString() {
		return "NPC [name=" + name + ", rank=" + rank + ", type=" + type
				+ ", li=" + li + ", min=" + min + ", tili=" + tili
				+ ", jingli=" + jingli + ", lucky=" + lucky + ", hp=" + hp
				+ ", mp=" + mp + ", attack=" + attack + ", defense=" + defense
				+ ", baoji=" + baoji + ", suck=" + suck + ", weapon=" + equipAry[0]
				+ ", helmet=" + equipAry[1] + ", necklace=" + equipAry[2] + ", coat="
				+ equipAry[3] + ", ring=" + equipAry[4]+ ", waistband=" + equipAry[5] 
				+ ", trousers=" + equipAry[6] + ", shoes=" + equipAry[7]  + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		NPC npc = (NPC) obj ;
		if(npc.getName().equals(name)&&npc.getRank()==rank&&npc.getType()==type){
			if(npc.getLi()==li&&npc.getMin()==min&&npc.getTili()==tili&&npc.getJingli()==jingli&&npc.getLucky()==lucky){
				if(npc.getAttack()==attack&&npc.getDefense()==defense&&npc.getHp()==hp&&npc.getMp()==mp){
					return true;
				}
			}
		}
		return false;
	}
	
	public String npcInfo(){
		StringBuffer str = new StringBuffer("敌人信息:\n") ;
		str.append(name+"	rank:"+rank+"	type:"+Equip.typeDesAry[type]+"\n");
		str.append("hp:"+hp+"  mp:"+mp+"  atk:"+attack+"  def:"+defense+"\n");
		for (int i = 0; i < equipAry.length; i++) {
			if(equipAry[i]!=null){
				str.append(equipAry[i]);
			}
		}
		return str.toString();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Equip getRing() {
		return ring;
	}

	public void setRing(Equip ring) {
		this.ring = ring;
	}

	public boolean isCanGive() {
		return canGive;
	}

	public void setCanGive(boolean canGive) {
		this.canGive = canGive;
	}

	public String getSellMode() {
		return sellMode;
	}

	public void setSellMode(String sellMode) {
		this.sellMode = sellMode;
	}

	public String getAppearMode() {
		return appearMode;
	}

	public void setAppearMode(String appearMode) {
		this.appearMode = appearMode;
	}

	public int getAppearLv() {
		return appearLv;
	}

	public void setAppearLv(int appearLv) {
		this.appearLv = appearLv;
	}

	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	public List<Object> getSellList() {
		return sellList;
	}

	public void setSellList(List<Object> sellList) {
		this.sellList = sellList;
	}
	

}
