package game.entity;

import game.control.GameControl;
import game.control.SoundControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

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
	private int li = -1 ;
	private int min = -1 ;
	private int tili = -1 ;
	private int jingli = -1 ;
	private int lucky = -1 ;
	
	/** 怪物二级属性 当前血量,蓝量,当前攻击,当前防御力,当前暴击几率 */
	private int curHp = -1 ;
	private int hp = -1 ;
	private int curMp = -1 ;
	private int mp = -1 ;
	private int curAttack = -1 ;
	private int attack = -1 ;
	private int curDefense = -1 ;
	private int defense = -1 ;
	
	private int suck = 0 ;
	private int baoji = 0;
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
	private List<Object> skillBag ;
	private List<Object> materialBag ;
	
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
		skillBag = new ArrayList<>();
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
	
	/** 把属性设为基本属性 */
	public void setBaseAttr(){
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
		defense = min ;
		
		baoji = lucky/40*100 ;
		baolv = lucky*2;
		suck = 0 ;
		expAdd = 0;
		moneyAdd = 0;
		petAdd = 0;
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
			Exp = GameControl.getUpgradeExp(rank);
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
	
	/**
	 * 查看某件装备是否存在玩家背包
	 * @param id
	 * @return
	 */
	public boolean isExistEquip(String id){
		for (int i = 0; i < equipBag.size(); i++) {
			if(equipBag.get(i).getId().equals(id)){
				return true ;
			}
			
		}
		return false ;
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
	
}
