package game.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * 场景实体类
 * 代表着每一个地方
 * 格式:
 * <point>
 *		<name>土路</name>
 *		<des></des>
 *		<pos>0,1</pos>
 *		<npclist></npclist>
 *	</point>
 * @author yilong22315
 *
 */
public class Scene {
	public Scene() {
	}
	public Scene(String name, String des, int x, int y) {
		super();
		this.name = name;
		this.des = des;
		this.x = x;
		this.y = y;
		npcList = new ArrayList<>();
	}
	
	/** 是否为特殊按钮 */
	public boolean isSpScene = false ;

	
	/** 当前按钮是否代表出口,场景按钮中使用 */
	public boolean isExit = false ;
	/** 如果是出口，那么出口场景的地点是哪里 */
	public Point exitPoint ;
	
	public boolean isEntry = false ;
	public Point entryPoint ;
	
	/** 不能到达的地点 */
	public List<Point> points = new ArrayList<>() ;
	
	public String name ;
	
	public String des ;
	
	public String npcStr ;
	
	public int x ;

	public int y ;

	public List<NPC> npcList = new ArrayList<>() ;
	
	public List<Item> itemList = new ArrayList<>() ;
	
	public List<String> enters = new ArrayList<>() ;
	
	/** 场地特殊的功能 */
	public boolean canFishing = false ;//钓鱼
	public boolean canWushu  = false ;//练武
	public boolean canXiu = false ;//修炼
	public boolean canDig = false ;//挖矿
	public boolean canCutdown = false ;//挖矿
	public boolean canHunting = false ;//狩猎
	public boolean canRest = false ;//休息
	
	
	
	
	@Override
	public String toString() {
		return "Scene [name=" + name + ", des=" + des + ", npcStr=" + npcStr
				+ ", x=" + x + ", y=" + y + "]";
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
	public String getNpcStr() {
		return npcStr;
	}
	public void setNpcStr(String npcStr) {
		this.npcStr = npcStr;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public List<NPC> getNpcList() {
		return npcList;
	}
	public void setNpcList(List<NPC> npcList) {
		this.npcList = npcList;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
}
