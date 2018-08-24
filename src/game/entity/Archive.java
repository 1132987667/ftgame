package game.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 存档类 */
public class Archive implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long time ;
	/** 角色属性 
	 * 	攻击 防御
	 *  敏捷 幸运值
	 *  属性点
	 */
	
	private String name = "" ;
	private int rank = 0 ;
	private int Exp ;
	private int curExp ;
	
	
	/** 怪物一级属性 力,敏,体力,精力,幸运值  */
	private int li = -1 ;
	private int min = -1 ;
	private int tili = -1 ;
	private int jingli = -1 ;
	private int lucky = -1 ;
	private int hit = -1 ;
	
	/** 怪物二级属性 当前血量,蓝量,当前攻击,当前防御力,当前暴击几率 */
	private int curHp = -1 ;
	private int hp = -1 ;
	private int curMp = -1 ;
	private int mp = -1 ;
	private int curAttack = -1 ;
	private int attack = -1 ;
	private int curDefense = -1 ;
	private int defense = -1 ;
	
	/** 特殊属性值 */
	private int suck = 0 ;
	private int baoji = 0;
	private int baolv = 0;
	private int expAdd = 0;
	private int moneyAdd = 0;
	private int petAdd = 0;
	
	/**
	 * 游戏背包 
	 */
	private List<Equip> equipBag ;
	private List<Object> skillBag ;
	private List<Object> materialBag ;
	
	/** 身上的装备 */
	public Equip weapon,helmet,necklace,coat,ring,waistband,trousers,shoes;
	private Equip[] equipAry = {weapon,helmet,necklace,coat,ring,waistband,trousers,shoes} ;
	
	
	/** 建筑 */
	
	/** 当前所在点 */
	private Map<String,String> cur ;

	
	public Archive() {
		/**
		 * 初始化背包
		 */
		equipBag = new ArrayList<>();
		skillBag = new ArrayList<>();
		materialBag = new ArrayList<>();
		
		/** 初始化装备栏 */
		for (int i = 0; i < equipAry.length; i++) {
			equipAry[i] = new Equip() ;
		}
		
		/** 设置基本属性 */
		rank = 1;
		curExp = 0 ;
		Exp = 150 ;
		
		tili = 20 ;
		jingli = 20 ;
		li = 20 ;
		min = 20 ;
		lucky = 10 ;
		
		hp = 200 ;
		curHp = 200 ;
		mp = 50 ;
		curMp = 20 ;
		attack = 45 ;
		curAttack = 45 ;
		attack = 20 ;
		curDefense = 20 ;
		defense = 20 ;
		
		baoji = 3 ;
		suck = 0 ;
		baolv = 0;
		expAdd = 0;
		moneyAdd = 0;
		petAdd = 0;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("【Archive:setName()】你决定将名字设置为:"+name);
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getCurExp() {
		return curExp;
	}

	public void setCurExp(int curExp) {
		this.curExp = curExp;
	}

	public int getExp() {
		return Exp;
	}

	public void setExp(int Exp) {
		this.Exp = Exp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}


	public int getLucky() {
		return lucky;
	}

	public void setLucky(int lucky) {
		this.lucky = lucky;
	}

	public Map<String, String> getCur() {
		return cur;
	}

	public void setCur(Map<String, String> cur) {
		this.cur = cur;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
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

	public int getCurDefense() {
		return curDefense;
	}

	public void setCurDefense(int curDefense) {
		this.curDefense = curDefense;
	}

	public int getBaoji() {
		return baoji;
	}

	public void setBaoji(int baoji) {
		this.baoji = baoji;
	}
	
	@Override
	public String toString() {
		return "Archive [time=" + time + ", name=" + name + ", rank=" + rank
				+ ", Exp=" + Exp + ", curExp=" + curExp + ", li=" + li
				+ ", min=" + min + ", tili=" + tili + ", jingli=" + jingli
				+ ", lucky=" + lucky + ", hp=" + hp + ", mp=" + mp
				+ ", attack=" + attack + ", defense=" + defense + ", baoji="
				+ baoji + "]";
	}

	public int getSuck() {
		return suck;
	}

	public void setSuck(int suck) {
		this.suck = suck;
	}

	public int getBaolv() {
		return baolv;
	}

	public void setBaolv(int baolv) {
		this.baolv = baolv;
	}

	public int getExpAdd() {
		return expAdd;
	}

	public void setExpAdd(int expAdd) {
		this.expAdd = expAdd;
	}

	public int getMoneyAdd() {
		return moneyAdd;
	}

	public void setMoneyAdd(int moneyAdd) {
		this.moneyAdd = moneyAdd;
	}

	public int getPetAdd() {
		return petAdd;
	}

	public void setPetAdd(int petAdd) {
		this.petAdd = petAdd;
	}


	public List<Equip> getEquipBag() {
		return equipBag;
	}


	public void setEquipBag(List<Equip> equipBag) {
		this.equipBag = equipBag;
	}


	public List<Object> getSkillBag() {
		return skillBag;
	}


	public void setSkillBag(List<Object> skillBag) {
		this.skillBag = skillBag;
	}


	public List<Object> getMaterialBag() {
		return materialBag;
	}


	public void setMaterialBag(List<Object> materialBag) {
		this.materialBag = materialBag;
	}


	public Equip[] getEquipAry() {
		return equipAry;
	}


	public void setEquipAry(Equip[] equipAry) {
		this.equipAry = equipAry;
	}
	
	/** 得到某个部位穿着的装备 */
	public Equip getEquip(int part){
		return equipAry[part];
	}
	
	/** 穿上装备 */
	public void wearEquip(Equip equip,int part){
		equipAry[part] = equip ;
	}
	
	/** 把装备从背包移除 */
	public boolean removeEquip(Equip equip){
		boolean flag = equipBag.remove(equip);
		return flag ;
	}
	
	/** 新增装备到背包中/得到装备 */
	public boolean obtainEquip(Equip equip){
		boolean flag = equipBag.add(equip);
		return flag ;
	}
	
	
}
