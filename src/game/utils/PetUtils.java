package game.utils;

import game.entity.Pet;

public class PetUtils {

	
	/**
	 * 判断宠物能否融合
	 * @param pet
	 * @return
	 */
	public boolean canFusion(Pet pet) {
		if(!pet.isVariation && pet.rank>30 && !pet.isPlay) {
			return true ;
		}
		return false;
	}
	
	/**
	 * 合成宠的成长率和根骨在两只参与合成宠物的成长率和根骨的平均值的90％～105％范围内随机生成
	 * 合成宠的等级为两只宠物等级的平均值（取整）
	 * 变异宠无法参与合成，合成也无法产生变异宠
	 * 宠物类型	总属性点	分配点	剩余潜能点
	 * 野生	50+（lv-1）*8	50+（lv-1）*8	0
	 * 宝宝	50+（lv-1）*10	50	（lv-1）*10

	 */
	public void fusionRuler() {
		double pOdds = 0.5 ;
		
	}
	
	
}
