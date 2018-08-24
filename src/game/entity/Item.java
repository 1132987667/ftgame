package game.entity;

/**
 * 物品实体类
 * 为所有物品的父类
 * 如 武器，防具，材料，技能书
 * @author yilong22315
 *
 */
public class Item {
	/**
	 * 名字 数量 品质
	 */
	public String id = "" ;
	public String name = "" ;
	public int num ;
	public int type ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}

