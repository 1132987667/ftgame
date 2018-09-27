package game.entity;

/***
 * 技能实体类
 * @author yilong22315
 *
 */
public class Skill {
	/**
	 * 技能名
	 * 一共几层x
	 * 当前层数x
	 * 最高层数x
	 * 技能描述
	 * 技能效果
	 * 	回复 血量|法力
	 * 技能类型
	 * 	被动，主动
	 * 使用条件
	 * 学习条件
	 * 技能消耗
	 * 技能冷却
	 * 技能升级x
	 *  每级效果加成x
	 * 攻击判定：由技能类型来决定 , 物理
	 *  
	 */
	public String id ;
	public String name = "" ;
	public String des = "" ;
	public String type ;
	/** 消耗 */
	public String consume  ;
	/** 效果 */
	public String result ;
	/** 目标:敌友 */
	public String target ;
	/** 范围:单双 */
	public String scope ;
	/** 来源的功法id */
	public String gongId ;
	/** 来源的功法的名字 */
	public String gongName ;
	/** 使用条件|如武器必须为剑  null为无*/
	public String useCase ;
	/** 学习条件 */
	public String studyCase ;
	/** 需要功法等级 */
	public int needTier ;
	/** 技能冷却 */
	public int cd ;
	/** 已冷却时间 */
	public int curCd ;
	
	/** 暂未使用 */
	/** 技能当前等级 */
	public String skillCurTier ;
	/** 技能最大等级 */
	public String skillMaxTier ;
	
	
	
	
	
	
	
	
	
}
