package game.entity;

public class ExpConstant {
	/** 玩家升级所需要的经验 */
	public static final int[] ExpAry = {
			0,210,850,1950,3500,5600,8200,11400,15200,19500,
			24500,30000,36000,43000,51000,60000,69000,79000,90000,101000,
			114000,128000,142000,157000,173000,190000,210000,228000,248000,270000,
			300000,316000,340000,366000,400000,421000,452000,482000,514000,550000
			
	};
	
	/**  击杀怪物所需要的经验*/
	public static final int[] killExpAry = {
			0,20,58,91,121,148,173,197,219,240,
			261,280,299,317,334,351,368,384,400,416,
			431,446,461,476,491,505,520,534,548,562,
			576,590,603,617,631,644,657,571,684,735
			} ; 
	
	/** 怪物品质对应经验倍数 普通,精英,稀有,霸主,王者 */
	public static final double[] killMclAry = {0.9,1.2,1.6,2.0,2.5} ;
}
