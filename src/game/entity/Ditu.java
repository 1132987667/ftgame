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
	public int id = 0 ;
	public String name = "" ;
	public String des = "" ;
	public int rankL = 0 ;
	public int rankR = 0;
	public List<NPC> list = null ;
	public List<Scene> scene = null ;
	
	public Ditu(int id,String name, String des, int rankL, int rankR) {
		super();
		this.id = id ;
		this.name = name;
		this.des = des;
		this.rankL = rankL;
		this.rankR = rankR;
		list = new ArrayList<>();
		scene = new ArrayList<>();
	}
}
