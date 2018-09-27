package game.entity;

import java.io.Serializable;
	/**
	 * 属性加成
	 * 装备，功法，技能...
	 * @author yilong22315
	 * 一共22条属性
	 */
	public class AddAttrs implements Serializable{
		private static final long serialVersionUID = 1L;
		/** 一级属性 力,敏,体力,精力,幸运值 */
		public int li = 0;
		public int min = 0;
		public int tili = 0;
		public int jingli = 0;
		public int lucky = 0;

		/** 怪物二级属性 当前血量,蓝量,当前攻击,当前防御力,当前暴击几率 */
		public int hp = 0;
		public int mp = 0;
		public int attack = 0;
		public int defense = 0;
		public int baoji = 0;
		public int suck = 0;
		
		/** 属性值带来的 加成 */
		public int hpAdd = 0 ;
		public int mpAdd = 0;
		public int atkAdd = 0;
		public int defAdd = 0;
		public int baojiAdd = 0;
		public int baolvAdd = 0;
		
		/** 装备直接带来的 血量，蓝量，攻击，防御加成 */
		public int hpEd = 0;
		public int mpEd = 0;
		public int atkEd = 0;
		public int defEd = 0;
		public int expEd = 0;
		public int moneyEd = 0;
		public int baolvEd = 0;

		/** petEd */
		public int petHp = 0;
		public int petAtk = 0;
		public int petDef = 0;
		public int petAll = 0;
		
		/** 其他属性 */
		public int reHp = 0 ;/** 生命回复 */
		public int reMp = 0 ;/** 法力回复 */
		public int atkUp = 0 ;/** 攻击力增加 */
		public int atkDown = 0 ;/** 攻击力下降 */
		public int defUp = 0 ;/** 防御力上升 */
		public int defDown = 0 ;/** 防御力下降 */
		public int speedUp = 0 ;/** 速度下降 */
		public int speedDown = 0 ;/** 速度上升 */
		public int hitUp = 0 ;/** 命中率上升 */
		public int hitDown = 0 ;/** 命中率下降 */
		public int dodgeUp = 0 ;/** 闪避率上升 */
		public int dodgeDown = 0 ;/** 闪避率下降 */
		public int wuRebound = 0 ;/** 物理伤害反弹 */
		public int bufRe = 0 ;/** 移除所有增益状态 */
		public int debufRe = 0 ;/** 移除所有减益状态 */
		public int daze = 0 ;/** 眩晕 */
		
		@Override
		public String toString() {
			return "AddAttrs [li=" + li + ", min=" + min + ", tili=" + tili
					+ ", jingli=" + jingli + ", lucky=" + lucky + ", hp=" + hp
					+ ", mp=" + mp + ", attack=" + attack + ", defense="
					+ defense + ", baoji=" + baoji + ", hpEd=" + hpEd
					+ ", mpEd=" + mpEd + ", atkEd=" + atkEd + ", defEd="
					+ defEd + ", expEd=" + expEd + ", moneyEd=" + moneyEd
					+ ", baolvEd=" + baolvEd + ", suck=" + suck + ", petHp="
					+ petHp + ", petAtk=" + petAtk + ", petDef=" + petDef
					+ ", petAll=" + petAll + "]";
		}

	}