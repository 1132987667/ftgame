package game.entity;

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

	public String name ;
	
	public String des ;
	
	public String npcStr ;
	
	public int x ;

	public int y ;

	public List<NPC> npcList ;

	@Override
	public String toString() {
		return "Scene [name=" + name + ", des=" + des + ", npcStr=" + npcStr
				+ ", x=" + x + ", y=" + y + "]";
	}
	
}
