package game.entity;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * 心法，内功和外功
 * @author yilong22315
 */
public class Gong implements Serializable, Comparable<Gong>{
	private static final long serialVersionUID = 1L;
	
	private String id ;
	private String name ;
	private String des ;
	private int num ;
	/** 品质 */
	private int quality ;
	private int maxTier ;
	private int curTier ;
	private int type ;
	private int needRank ;
	private int price ;
	private String require ;
	
	
	/** 功法附带的技能 */
	private List<Skill> allSkills = new ArrayList<>();
	/** 已经从功法上学习的技能 */
	private List<Skill> hasLearn = new ArrayList<>();
	/** 功法带来的属性加成 */
	private AddAttrs addAttrs = new AddAttrs();
	/** 功法每一层信息 */
	private List<Tier> tiers = new ArrayList<>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getMaxTier() {
		return maxTier;
	}

	public void setMaxTier(int maxTier) {
		this.maxTier = maxTier;
	}

	public int getCurTier() {
		return curTier;
	}

	public void setCurTier(int curTier) {
		this.curTier = curTier;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Skill> getAllSkills() {
		return allSkills;
	}

	public void setAllSkills(List<Skill> allSkills) {
		this.allSkills = allSkills;
	}

	public List<Skill> getHasLearn() {
		return hasLearn;
	}

	public void setHasLearn(List<Skill> hasLearn) {
		this.hasLearn = hasLearn;
	}

	public AddAttrs getAddAttrs() {
		return addAttrs;
	}

	public void setAddAttrs(AddAttrs addAttrs) {
		this.addAttrs = addAttrs;
	}

	public List<Tier> getTiers() {
		return tiers;
	}

	public void setTiers(List<Tier> tiers) {
		this.tiers = tiers;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNeedRank() {
		return needRank;
	}

	public void setNeedRank(int needRank) {
		this.needRank = needRank;
	}

	@Override
	public int compareTo(Gong o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRequire() {
		return require;
	}

	public void setRequire(String require) {
		this.require = require;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
