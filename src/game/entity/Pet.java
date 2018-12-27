package game.entity;

import java.io.Serializable;

/**
 * 宠物实体类
 * @author yilong22315
 *
 */
public class Pet extends Item implements Serializable{
	private static final long serialVersionUID = 3315998605798097175L;
	/** 是否变异 */
	public boolean isVariation = false ;
	public int rank ;
	/** 是否出战 */
	public boolean isPlay ;
	
	/** 野生宠/宝宝宠  */
	public int spec = 0 ;
	
	
}
