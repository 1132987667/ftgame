package game.entity;

import java.io.Serializable;

/**
 * 物品实体类
 * 为所有物品的父类
 * 如 武器，防具，材料，技能书
 * @author yilong22315
 *
 */
public class Item implements Serializable{
	private static final long serialVersionUID = 4318635595339998045L;
	public String id ;
	public String name ;
	public String des ;
	public int qua ;
	public int num ;
	public String effectStr ;
	/**
	 * 是否可以捡起
	 */
	//Costa = 0, //消耗品
	//Weapon = 1, //武器 
	//Armor = 2, //防具 
	//Accessories = 3, //饰品 
	//Book = 4, //功法书 
	//SkillBook = 5, //技能书
	//SpeicalSkillBook = 6, //特殊技书 
	//Mission = 7, //任务物品 
	//Upgrade = 8, //材料
	//Special = 9, //特殊物品 
	//Design = 10, //设计图
	//Pet 宠物
	public int mark ;
	
	public static void main(String[] args) {
	}

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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getQua() {
		return qua;
	}

	public void setQua(int qua) {
		this.qua = qua;
	}
	
	
}

