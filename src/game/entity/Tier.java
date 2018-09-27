package game.entity;
/**
 * 功法每一重效果和条件
 * curTier 当前层数
 * needRank 需要的等级
 * addAttrs 带来的属性加成
 * @author yilong22315
 */
public class Tier{
	public int curTier = 1 ;
	public int needRank = 1 ;
	public String needExp  ; 
	public String addAttrStr ;
	public AddAttrs addAttrs ;
	
	public String info(){
		StringBuffer sb = new StringBuffer("当前层数:");
		sb.append(curTier+",需要等级:"+needRank+",需要经验:"+needExp+",属性加成:"+addAttrStr);
		return sb.toString() ;
	}
	
}
