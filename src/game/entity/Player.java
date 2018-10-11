package game.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import game.control.SoundControl;
import game.utils.DataCal;
import game.utils.SUtils;

/**
 * 游戏角色实体类
 * 存储着游戏角色的信息
 * @author yilong22315
 */
public class Player {
	private String name = "" ;
	private String state = "" ;
	private int rank = 0 ;
	private int Exp ;
	private int curExp ;
	
	
	/** 怪物一级属性 力,敏,体力,精力,幸运值  */
	private int li ;
	private int min ;
	private int tili ;
	private int jingli ;
	private int lucky ;
	
	/** 怪物二级属性 当前血量,蓝量,当前攻击,当前防御力,当前暴击几率 */
	private int curHp ;
	private int hp ;
	private int curMp ;
	private int mp ;
	private int curAttack ;
	private int attack ;
	private int curDefense ;
	private int defense ;
	
	private int suck = 0 ;
	private int baoji = 0;
	/** 任务出手速度 */
	private int speed = 0 ;/** 毫秒  */
	//爆率 百分比
	private int baolv = 0;
	private int expAdd = 0;
	private int moneyAdd = 0;
	private int petAdd = 0;
	
	
	public Equip weapon,helmet,necklace,coat,ring,waistband,trousers,shoes;
	private Equip[] equipAry = {weapon,helmet,necklace,coat,ring,waistband,trousers,shoes} ;
			
	/**
	 * 游戏背包 
	 */
	private List<Equip> equipBag ;
	private List<Gong> gongBag ;
	private List<Material> materialBag ;
	/** 背包的大小,默认大小为36 */
	private int bagSize = 36 ;
	
	
	/***    功法系统     **/
	/** 当前装备的技能 1个被动技能 三个主动技能  */
	private Gong[]  curUseSkill ;
	/** 当前装备的内功 */
	private Gong curUseNeiGong ;
	/** 当前装备的外功 */
	private Gong curUseWaiGong ;
	/** 已经学会的内功列表 */
	private List<Gong> hasLeaNeiGongs = new ArrayList<>();
	/** 已经学会的外功 */
	private List<Gong> hasLeaWaiGongs = new ArrayList<>();
	/** 已经学会的技能 */
	private List<Gong> hasLeaSkills ;
	
	private Map<String,Tasks> curTasksList = new HashMap<>() ;
	
	/** 五个程度, 誓不两立，令人切齿，素昧平生，莫逆之交，生死之交 */
	private Map<String,String> liking = new HashMap<>();
	
	
	/** 当前所在点 */
	private Map<String,String> cur ;
	
	/** 
	 * 初始化一个人物角色 
	 */
	public Player() {
		/**
		 * 初始化背包
		 */
		equipBag = new ArrayList<>();
		gongBag = new ArrayList<>();
		materialBag = new ArrayList<>();
		
		/** 初始化装备栏 */
		for (int i = 0; i < equipAry.length; i++) {
			equipAry[i] = new Equip() ;
		}
		 
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
	
	/** 重新设置基本属性 */
	public void reloadBaseAttr(){
		tili = 15+rank*5 ;
		jingli = 15+rank*5 ;
		li = 15+rank*5 ;
		min = 15+rank*5 ;
		lucky = 10+rank ;
		
		hp = tili*10 ;
		curHp = hp ;
		mp = jingli*5/2 ;
		curMp = mp ;
		attack = 4*li-35 ;
		curAttack = attack ;
		defense = min ;
		curDefense = defense ;

		speed = getAtkSpeed(jingli, min);
		baoji = lucky/40*100 ;
		baolv = lucky*2;
		suck = 0 ;
		expAdd = 0;
		moneyAdd = 0;
		petAdd = 0;
	}
	
	/**
	 * 得到人物的出手速度
	 * @param li
	 * @param min
	 * @return 返回出手所需要等待的时间 单位:ms
	 */
	public int getAtkSpeed(int li,int min){
		int speed = SUtils.reDouPoint(2500*(1/(1+(0.5*li+min)*0.0067))) ;
		if(speed<500){
			speed = 500 ;
		}
		return speed ;
	}
	
	/** 得到背包内武器 */
	public List<Equip> getWeaponBag(){
		List<Equip> weaponBag = new ArrayList<>() ;
		for (int i = 0; i < equipBag.size(); i++) {
			if(equipBag.get(i).getPart()==0 && equipBag.get(i).getName().trim().length()>0 ){
				weaponBag.add(equipBag.get(i));
			}
		}
		return weaponBag ;
	}
	
	/** 获得背包内的防具 */
	public List<Equip> getArmorBag(){
		List<Equip> armorBag = new ArrayList<>() ;
		int length = equipBag.size() ;
		for (int i = 0; i < length; i++) {
			if(equipBag.get(i).getPart()!=0 && equipBag.get(i).getName().trim().length()>0 ){
				armorBag.add(equipBag.get(i));
			}
		}
		return armorBag ;
	}
	
	public static void main(String[] args) {
		Random rd = new Random(System.currentTimeMillis());
		for (int i = 0; i < 10; i++) {
			rd = new Random();
			System.out.println(rd.nextInt(30));
		}
	}
	
	/**
	 * 得到玩家在rank等级下默认的攻击力
	 * @param rank
	 * @return
	 */
	public static int getBaseAck(int rank){
		return (rank*5+15)*4-35 ;
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
		sort();
		return flag ;
	}
	
	
	
	public List<Equip> getEquipBag(int index){
		if(index==0){
			return getWeaponBag();
		}else if(index==1){
			return getArmorBag();
		}
		return null; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getExp() {
		return Exp;
	}

	public void setExp(int exp) {
		Exp = exp;
	}

	public int getCurExp() {
		return curExp;
	}

	public void setCurExp(int curExp) {
		this.curExp = curExp;
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

	public int getSuck() {
		return suck;
	}

	public void setSuck(int suck) {
		this.suck = suck;
	}

	public int getBaoji() {
		return baoji;
	}

	public void setBaoji(int baoji) {
		this.baoji = baoji;
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

	public Equip[] getEquipAry() {
		return equipAry;
	}

	public void setEquipAry(Equip[] equipAry) {
		this.equipAry = equipAry;
	}

	public List<Equip> getEquipBag() {
		return equipBag;
	}

	public void setEquipBag(List<Equip> equipBag) {
		this.equipBag = equipBag;
	}
	
	
	public void gainExp(int exp){
		curExp+=exp;
		if(curExp>=Exp){
			rank+=1;
			Exp = DataCal.getUpgradeExp(rank);
			SoundControl.jiemianMuc("lvUp");
			JOptionPane.showConfirmDialog(null, "恭喜你，升级了~~","提示", JOptionPane.PLAIN_MESSAGE);
		}
		
	}
	
	public void Upgrade(){
		this.tili+=5;
		this.jingli+=5;
		this.li+=5;
		this.min+=5;
		this.lucky+=1;
		
		this.hp+=50;
		this.mp+=13;
		this.attack+=4*5;
		this.defense+=1*5;
	}
	
	public void gainMoney(int money){
		
	}
	
	public void sort(){
		Collections.sort(equipBag);
	}

	public Map<String, String> getLiking() {
		return liking;
	}

	public void setLiking(Map<String, String> liking) {
		this.liking = liking;
	}

	public boolean isBagFull() {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, Tasks> getCurTasksList() {
		return curTasksList;
	}

	public void setCurTasksList(Map<String, Tasks> curTasksList) {
		this.curTasksList = curTasksList;
	}

	public List<Material> getMaterialBag() {
		return materialBag;
	}

	public void setMaterialBag(List<Material> materialBag) {
		this.materialBag = materialBag;
	}

	public List<Gong> getGongBag() {
		return gongBag;
	}

	public void setGongBag(List<Gong> gongBag) {
		this.gongBag = gongBag;
	}

	public Map<String,String> getCur() {
		return cur;
	}

	public void setCur(Map<String,String> cur) {
		this.cur = cur;
	}

	public int getBagSize() {
		return bagSize;
	}

	public void setBagSize(int bagSize) {
		this.bagSize = bagSize;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Gong getCurUseNeiGong() {
		return curUseNeiGong;
	}

	public void setCurUseNeiGong(Gong curUseNeiGong) {
		this.curUseNeiGong = curUseNeiGong;
	}

	public Gong getCurUseWaiGong() {
		return curUseWaiGong;
	}

	public void setCurUseWaiGong(Gong curUseWaiGong) {
		this.curUseWaiGong = curUseWaiGong;
	}

	public List<Gong> getHasLeaNeiGongs() {
		return hasLeaNeiGongs;
	}

	public void setHasLeaNeiGongs(List<Gong> hasLeaNeiGongs) {
		this.hasLeaNeiGongs = hasLeaNeiGongs;
	}

	public List<Gong> getHasLeaWaiGongs() {
		return hasLeaWaiGongs;
	}

	public void setHasLeaWaiGongs(List<Gong> hasLeaWaiGongs) {
		this.hasLeaWaiGongs = hasLeaWaiGongs;
	}

	public List<Gong> getHasLeaSkills() {
		return hasLeaSkills;
	}

	public void setHasLeaSkills(List<Gong> hasLeaSkills) {
		this.hasLeaSkills = hasLeaSkills;
	}

	public Gong[] getCurUseSkill() {
		return curUseSkill;
	}

	public void setCurUseSkill(Gong[] curUseSkill) {
		this.curUseSkill = curUseSkill;
	}

}
