package game.entity;

import java.io.Serializable;

/**
 * 游戏材料实体类
 * @author yilong22315
 *
 */
public class Material implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id = 0 ;
	private String name = "" ;
	private String des = "" ;
	private int num = 0 ;
	private int price ;
	/** 珍贵程度 */
	private int type = 0 ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
