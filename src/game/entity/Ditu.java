package game.entity;

import java.util.ArrayList;
import java.util.List;

/*****
 * 是剧情和副本地图的载体
 * 
 *	id
 *  name 
 *  msg 描述
 *  rank 适合等级 
 */
public class Ditu{
	private String id = "" ;
	private String name = "" ;
	private String des = "" ;
	private int rankL = 0 ;
	private int rankR = 0;
	private List<NPC> list = null ;
	private List<Scene> scene = null ;
	
	public Ditu(String id,String name, String des, int rankL, int rankR) {
		super();
		this.id = id ;
		this.name = name;
		this.des = des;
		this.rankL = rankL;
		this.rankR = rankR;
		list = new ArrayList<>();
		scene = new ArrayList<>();
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

	public int getRankL() {
		return rankL;
	}

	public void setRankL(int rankL) {
		this.rankL = rankL;
	}

	public int getRankR() {
		return rankR;
	}

	public void setRankR(int rankR) {
		this.rankR = rankR;
	}

	public List<NPC> getList() {
		return list;
	}

	public void setList(List<NPC> list) {
		this.list = list;
	}

	public List<Scene> getScene() {
		return scene;
	}

	public void setScene(List<Scene> scene) {
		this.scene = scene;
	}
}
