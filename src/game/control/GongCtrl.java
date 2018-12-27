package game.control;

import game.entity.AddAttrs;
import game.entity.Gong;
import game.utils.DataCal;
import game.utils.SUtils;

public class GongCtrl {
	
	public final double GradeAdd = 0.2 ;
	
	public final double GongBaseAdd = 1 ;
	
	/** 增长幅度 */
	public final double upRatio = 0.2 ;
	
	public final int rank = 0 ;
	
	
	/**
	 * 计算不同境界的功法属性加成比例
	 */
	public double cal(int needRank, int type) {
		/** 境界 */
		int rank = needRank%10>0?needRank/10+1:needRank/10 ;
		double ratioValue = rank*GradeAdd*GongBaseAdd+rank*(rank-1)*GradeAdd*upRatio ;
		return diffQuaValue(type, ratioValue);
	}
	
	
	/** 得到不同品质下加成结果 */
	public double diffQuaValue(int type, double value) {
		for (int i = type; i < 3; i++) {
			value = value*4/5 ;
		}
		return value ;
	}
	
	/** 得到某功法第n层属性加成 */
	public AddAttrs getAddAttrs(AddAttrs addAttrs, String attrName, int rank, int type, int weight) {
		int attrValue = DataCal.getBaseAttrByRank(attrName, rank);
		double ratio = cal(rank, type);
		attrValue = SUtils.reDouPoint(ratio*attrValue*weight) ;
		return DataCal.calAddAttrsValue(addAttrs, attrName, attrValue);
	}
	
	public AddAttrs getGongEffect(Gong gong) {
		int curTier = gong.getCurTier() ;
		int needRank = gong.getNeedRank() ;
		int type = gong.getType() ;
		needRank = needRank + curTier*2 - 2 ;
		//System.out.println("需要等级:"+needRank);
		String effectStr = gong.getEffectStr() ;
		return getGongEffect(effectStr, curTier, needRank, type);
	}
	
	public AddAttrs getGongEffect(String effectStr, int curTier, int needRank, int type) {
		needRank = needRank + curTier*2 - 2 ;
		//System.out.println(effectStr);
		String[] effects = effectStr.split(";");
		AddAttrs addAttrs = new AddAttrs();
		for (int i = 0; i < effects.length; i++) {
			String[] info = effects[i].split(":");
			String attrName = info[0] ;
			int weight = SUtils.conStrtoInt(info[1]) ;
			addAttrs = getAddAttrs(addAttrs, attrName, needRank, type, weight);
		}
		return addAttrs ;
	}
	
	public static void main(String[] args) {
		GameControl gameControl = GameControl.getInstance();
		Gong gong = gameControl.getGongByID("2001");
		gong.setCurTier(2);
		System.out.println(gong.getName());
		GongCtrl g = new GongCtrl() ;
		AddAttrs addAttrs = g.getGongEffect(gong);
		System.out.println(addAttrs);
	}
	
	
}
