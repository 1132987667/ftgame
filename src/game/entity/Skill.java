package game.entity;

import java.io.Serializable;

/***
 * 技能实体类
 * @author yilong22315
 *
 */
public class Skill extends Item implements Serializable{
	private static final long serialVersionUID = 1L;
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
	/** xml中必填 */
	public String id      = "" ;
	public String name    = "" ;
	public String des     = "" ;
	public String qua     = "" ;
	/** 技能类型 */
	public String type    ;
	/** 范围:单双 */
	public String scope   = "" ;
	/** 消耗 */
	public String consume = "" ;
	public String effectStr ;
	/** 技能最大等级 */
	public int skillMaxRank ;
	/** 伤害系数 */
	public double baseDamageRatio ;
	
	
	/** 来源的功法id */
	public String gongId ;
	/** 来源的功法的名字 */
	public String gongName ;
	/** 使用条件|如武器必须为剑  null为无*/
	/** 技能当前等级 */
	public int skillCurRank ;
	public String useCase ;
	/** 学习条件 */
	public String studyCase ;
	/** 需要功法等级 */
	public int needTier ;
	/** 技能冷却 */
	public int cd ;
	/** 已冷却时间 */
	public int curCd ;
	
	
	
	
	
	
	
	
	
}
