package game.control;

import game.entity.Citiao;
import game.entity.CitiaoSD;
import game.entity.Equip;
import game.entity.NPC;
import game.entity.Player;
import game.utils.ArchiveUtils;
import game.utils.Constant;
import game.utils.ReflectUtils;
import game.utils.SUtils;
import game.view.frame.DataCheckFrame;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * 装备控制类 控制装备的生成过程
 * 
 */
public class EquipControl {
	public static final String[] partAry = { "weapon", "helmet", "necklace",
			"coat", "ring", "waistband", "trousers", "shoes" };
	public static final String[] partDes = { "兵器", "头盔", "项链", "上衣", "戒指",
			"腰带", "裤子", "鞋子" };
	public static final String[] typeAry = { "普通", "精致", "勇者", "史诗", "传说" };

	public Random rd = new Random(System.currentTimeMillis());
	/** 词条类型|一级属性,二级属性,特殊属性,宠物属性,技能属性 */
	private String[] attrType = { "oneAdd", "twoAdd", "special", "petAdd",
			"skillAdd" };

	/** 一级属性详细 */
	private String[] oneAttrAry = { "tili", "jingli", "li", "min", "lucky" };
	/** 二级属性详细 */
	private String[] twoAttrAry = { "hp", "mp", "attack", "defense", "baoji" };
	/** 特殊属性详细 */
	private String[] specialAttrAry = { "hp", "mp", "attack", "defense",
			"suck", "exp", "money", "baolv" };
	/** 宠物属性详细 */
	private String[] petAttAry = { "hp", "attack", "defense", "all" };

	/** 一级属性名 */
	public static String[] oneAttrNameAry = { "体力", "精力", "力量", "敏捷", "幸运" };
	/** 二级属性名 */
	public static String[] twoAttrNameAry = { "血量", "法力", "攻击力", "防御力", "暴击几率" };
	/** 特殊属性名 */
	public static String[] specialAttrNameAry = { "血量加成", "法力加成", "攻击力加成",
			"防御力加成", "吸血比例", "经验加成", "金钱加成", "爆率加成" };
	/** 宠物属性详细 */
	public static String[] petAttNameAry = { "血量加成", "攻击力加成", "防御力加成", "全部加成" };

	/** 武器资料库所有武器信息 */
	public Map<String,Equip> equipMap = null;

	protected EquipControl() {
		/** 加载武器信息 */
		equipMap = SUtils.loadEquip();
		dataCheck = new DataCheckFrame();
		dataCheck.setVisible(false);
	}
	
	private DataCheckFrame dataCheck = null ;

	/***
	 * 输入装备部位和等级得到所有符合条件的装备
	 * @param part -1表示武器和装备都有可能   1-9表示对应的装备 10代表所有的防具
	 * @param rank
	 * @return
	 */
	public List<Equip> getSuitList(int part, int rank) {
		List<Equip> list = new ArrayList<>();
		String key = null ;
		Equip value = null ;
		/** 得到迭代器 */
		Iterator<String> iter = equipMap.keySet().iterator();
		if (part == -1) {/** 符合条件的武器和装备都加入 */
			    while (iter.hasNext()){
			          key = iter.next();
			          value = equipMap.get(key);
			          if(value.getRank()==rank && value.getPart()==part){
			        	  list.add(value);
			          }
			    }
		} else if ( part>=0&&part<=10) {/** 只掉落单种类型的装备 */
			while (iter.hasNext()){
		          key = iter.next();
		          value = equipMap.get(key);
		          if(value.getRank()==rank && value.getPart()==part){
		        	  list.add(value);
		          }
		    }
		}else if(part == 10){/** 只掉落防具 */
			while (iter.hasNext()){
		          key = iter.next();
		          value = equipMap.get(key);
		          if(value.getRank()==rank && value.getPart()!=0){
		        	  list.add(value);
		          }
		    }
		}
		return list;
	}

	// "weapon","helmet","necklace","coat","ring","waistband","trousers",
	// "shoes"
	// 2.23
	/**
	 * 默认生成 装备类型和品质都随机，等级固定的装备 根据要生成装备的等级来生成装备
	 * @param rank
	 * @return
	 */
	public Equip equipGenerate(int rank) {
		int part = rd.nextInt(8);
		int type = rd.nextInt(5);
		return equipGenerate(rank, part, type);
	}

	/**
	 * 默认生成 装备品质随机，等级和部位固定的装备 根据要生成装备的等级来生成装备
	 * 
	 * @param rank
	 * @return
	 */
	public Equip equipGenerate(int rank, int part) {
		int type = rd.nextInt(5);
		return equipGenerate(rank, part, type);
	}

	/**
	 * 根据要生成装备的等级，部位，品质来生成来生成装备
	 * 
	 * @param rank
	 * @return
	 */
	public Equip equipGenerate(int rank, int part, int type) {
		Equip equip = null;
		if (part == 2 || part == 4 ) {
			rank = rank % 3 == 0 ? rank : rank - rank % 3;
		} else {
			rank = rank % 2 == 0 ? rank + 1 : rank;
		}
		List<Equip> list = getSuitList(part, rank);
		if (list.size() == 0) {
			System.out.println();
			return null;
		}
		/** 在符合条件的装备中随机选中一样 */
		int num = rd.nextInt(list.size());
		equip = list.get(num);
		/** 还要设置部位，品质词条等信息,数量默认为1 */
		equip.setType(type);
		/** 设置词条数组的长度 信息 */
		equip.setAttrDes(new String[equip.getType() + 1]);
		/** 设置词条数组的长度 */
		equip.setAttrList(new Citiao[equip.getType() + 1]);
		for (int i = 0; i < equip.getType() + 1; i++) {
			equip.getAttrList()[i] = rdAddAttr(rank, part, i,type );
		}
		return (Equip) ArchiveUtils.depthClone(equip);
	}

	/**
	 * 随机生成装备的部位
	 * 
	 * @return
	 */
	public int rdPart() {
		int part = 0;
		int num = rd.nextInt(1000) + 1;
		if (num <= 223)
			part = 0;
		else
			part = (num - 223) / 111 + 1;
		return part;
	}

	/**
	 * 随机生成装备的品质
	 * 
	 * @param lucky
	 *            幸运点
	 * @param baolvEd
	 *            加成 小数
	 * @return 装备品质
	 */
	public int rdType(int lucky, double baolvEd) {
		double mcl = (1 + lucky / 50) * (1 + baolvEd);
		if (mcl > 5) {
			mcl = 5;
		}
		int t5 = (int) (0.01 * mcl * 1000);
		int t4 = (int) (0.02 * mcl * 0.9 * 1000);
		int t3 = (int) (0.06 * mcl * 0.7 * 1000);
		int t2 = (int) (0.15 * mcl * 0.5 * 1000);
		int t1 = 1000 - t5 - t4 - t3 - t2;
		int type = 0;
		int num = rd.nextInt(1000) + 1;
		if (num + t5 >= 1000) {
			type = 4;
		} else if (num + t4 + t5 >= 1000) {
			type = 3;
		} else if (num + t3 + t5 + t4 >= 1000) {
			type = 2;
		} else if (num + t2 + t5 + t4 + t3 >= 1000) {
			type = 1;
		}
		return type;
	}
	
	/** 得到每级正常属性加成 一定幅度之间的 数值 
	 *  但是有些值的比率很低，比如 暴击 和 幸运值
	 * @param attrValue
	 * @param rank
	 * @return
	 */
	public double rdAttrValue(double attrValue,int rank){
		double value = attrValue*0.6*rank + rd.nextInt((int)(attrValue*0.8*rank)+1) ; 
		return value ;
	}

	/**
	 * 随机生成词条的具体属性
	 * 
	 * @param rank
	 *            等级
	 * @param part
	 *            部位
	 * @param num
	 *            装备的第几条词条
	 * @param type
	 *            品质
	 * @return
	 */
	public Citiao rdAddAttr(int rank, int part, int num, int type) {
		/**
		 * 先根据部位得到该装备类型的设定 再根据num得到第几条词条数
		 */
		List<CitiaoSD> list = SUtils.loadEquipSetting(part);
		/** 得到装备第num条词条设定 */
		CitiaoSD citiaoSD = list.get(num);

		/** 装备部位 */
		String equipPart = partAry[part];
		/** 得到该词条可能随机出现的属性 */
		String[] bias = citiaoSD.attrName.split("\\|");
		
		/** 随机生成选取数字 
		 *  控制 在x级之前不生成对应属性
		 */
		int rdNum = rd.nextInt(bias.length);
		if(rank<10&&citiaoSD.type==2){
			while(bias[rdNum].equals("suck")){
				rdNum = rd.nextInt(bias.length);
			}
		}
		/** 得到要随机生成的属性名字 */
		String attrName = bias[rdNum];
		/** 属性所在种类中位置 */

		//System.out.print("属性类型是:" + citiaoSD.typeName + ",属性名:" + attrName+ ",type:" + citiaoSD.type + " , ");
		int attr = getAttrByAttrName(citiaoSD.type, attrName);
		/** 该词条的内容 */
		Citiao citiao = new Citiao();
		citiao.type = citiaoSD.type;
		citiao.typeName = citiaoSD.typeName;
		citiao.attrName = attrName;
		citiao.attr = attr;

		/** 词条属性的值 */
		String attrValueStr = null;
		/** 判断词条中种类，根据不同规则设定属性 */

		/** oneAdd 词条中属性为生成一级属性 */
		if (attrType[0].equals(citiaoSD.typeName)) {
			switch (attrName) {
			case "tili":
			case "jingli":
			case "li":
			case "min":
				citiao.attrValue = rdAttrValue(Constant.lvUpValue[0], rank);
				break;
			case "lucky":
				citiao.attrValue = rdAttrValue(Constant.lvUpValue[4], rank);
				break;
			default:
				break;
			}
		} else if (attrType[1].equals(citiaoSD.typeName)) {
			/** 二级属性 */
			switch (attrName) {// "hp","mp","attack","defense","baoji","baolv"
			case "hp":// min 30 max 2600
				citiao.attrValue = rdAttrValue(Constant.oneToTwo[0]*Constant.lvUpValue[0], rank);
				break;
			case "mp": // min 9 max 845
				citiao.attrValue = rdAttrValue(Constant.oneToTwo[1]*Constant.lvUpValue[1], rank);
				break;
			case "attack":// min 14 max 1300
				citiao.attrValue = rdAttrValue(Constant.oneToTwo[2]*Constant.lvUpValue[2], rank);
				break;
			case "defense":// min 3.5 max 250
				citiao.attrValue = rdAttrValue(Constant.oneToTwo[3]*Constant.lvUpValue[3], rank);
				break;
			case "baoji":
				citiao.attrValue = rdAttrValue(Constant.oneToTwo[4]*Constant.lvUpValue[4], rank);
				break;
			default:
				break;
			}
		} else if (attrType[2].equals(citiaoSD.typeName)) {
			/** 特殊属性 attack|defense|hp|mp|suck|exp|money|baolv */
			switch (attrName) {
			case "suck": // max 18.75*2*1.2
				citiao.attrValue =   (rank*0.5 + rd.nextInt(rank*1+1))/4 ;
				break;
			case "exp": // 20-100
			case "money":
			case "baolv": //1-75
				citiao.attrValue = rd.nextInt(rank)+rank/2+1 ;
				break;
			case "attack": // 5 - 105 
				citiao.attrValue = rd.nextInt(rank*2) + 5;
				break;
			case "defense": // 0.2 - 1.0
				citiao.attrValue = rd.nextInt(rank*2) + 5;
				break;
			case "hp": // 0.4 - 1.2
				citiao.attrValue = rd.nextInt((int)(rank*2.5)) + 10;
				break;
			case "mp": // 0.4 - 1.2
				citiao.attrValue = rd.nextInt((int)(rank*2.5)) + 10;
				break;
			default:
				break;
			}
		} else if (attrType[3].equals(citiaoSD.typeName)) {
			/** 宠物属性 attack|defense|hp|all */
			switch (attrName) {
			case "attack":// 0.3 - 1.1
				citiao.attrValue = (rd.nextInt(rank + 40) + 30) * 0.01;
				break;
			case "defense":// 0.3 = 1.1
				citiao.attrValue = (rd.nextInt(rank + 40) + 30) * 0.01;
				break;
			case "hp":// 0.4-140
				citiao.attrValue = (rd.nextInt(rank + 40) + 60) * 0.01;
				break;
			case "all":// 0.2-0.7
				citiao.attrValue = (rd.nextInt(rank + 30) + 20) * 0.01;
				break;
			default:
				break;
			}
		} else if (attrType[4].equals(citiaoSD.typeName)) {
			/** 技能属性 */
			citiao.des = "● ";
		}
		// System.out.println("词条值是："+citiao.attrValue+" , 词条类型是:"+citiaoSD.type);
		/** 乘以设定的倍数 */
		citiao.attrValue = citiao.attrValue * citiaoSD.ratio;
		// System.out.println("特殊属性值:"+attrName+addAttr.attrValue);
		/** 根据装备的品质进行加强 */
		citiao.attrValue = attrAddByType(citiao.attrValue, type);
		// System.out.println("加强特殊属性值:"+attrName+addAttr.attrValue);
		/** 去除小数点 */
		citiao.attrValue = attrFloat(citiao.attrValue);
		// System.out.println("格式化特殊属性值:"+attrName+addAttr.attrValue);

		/** 这是第几类词条 */
		String[] tempAry = null;
		String[] tempNameAry = null;
		/** attr.type词条类型 */
		switch (citiaoSD.typeName) {
		case "oneAdd":
			tempAry = oneAttrAry;
			tempNameAry = oneAttrNameAry;
			break;
		case "twoAdd":
			tempAry = twoAttrAry;
			tempNameAry = twoAttrNameAry;
			break;
		case "special":
			tempAry = specialAttrAry;
			tempNameAry = specialAttrNameAry;
			break;
		case "petAdd":
			tempAry = petAttAry;
			tempNameAry = petAttNameAry;
			break;
		case "skillAdd":
			tempAry = oneAttrAry;
			tempNameAry = oneAttrNameAry;
			citiao.type = 0;
			break;
		default:
			break;
		}
		attrValueStr = citiao.attrValue + "";
		citiao.des = "● " + tempNameAry[citiao.attr] + "+"
				+ attrValueStr.substring(0, attrValueStr.length() - 2);
		if (citiaoSD.typeName.equals("special")) {
			citiao.des += "%";
		}
		return citiao;
	}

	/** 根据属性名得到属性所在的列 */
	public int getAttrByAttrName(int type, String attrName) {
		String[] temp = null;
		int attr = 0;
		if (type == 0) {
			temp = oneAttrAry;
		} else if (type == 1) {
			temp = twoAttrAry;
		} else if (type == 2) {
			temp = specialAttrAry;
		} else if (type == 3) {
			temp = petAttAry;
		} else {
			temp = oneAttrAry;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].equals(attrName)) {
				attr = i;
				//System.out.println("根据属性名得到属性所在的列，属性名是:" + attrName + ",列是:"+ attr);
			}
		}
		return attr;
	}

	/**
	 * 固定生成词条的具体属性
	 * 
	 * @param rank
	 *            等级
	 * @param part
	 *            部位
	 * @param num
	 *            第几条词条
	 * @param type
	 *            品质
	 * @return
	 */
	public Citiao fixAddAttr(int rank, int part, int num, int type, int power) {
		Citiao addAttr = new Citiao();
		/** 装备部位 */
		String equipPart = Equip.partAry[part];
		/** 得到对应装备部位的所有词条设定 */
		CitiaoSD[] ary = (CitiaoSD[]) ReflectUtils.getArray(this.getClass(),
				equipPart + "G");
		/** 得到装备第num条词条设定 */
		CitiaoSD attr = ary[num];
		// System.out.println(attr.toString());

		// "oneAdd","twoAdd","special","petAdd","skillAdd"
		/** 得到该词条可能随机出现的属性 */
		String[] bias = attr.attrName.split("\\|");
		/** 得到 */
		int biasAttr = rd.nextInt(bias.length);
		/** 得到要随机生成的属性名字 */
		String attrName = bias[biasAttr];

		String attrValueStr = null;
		/** 判断词条中种类，根据不同规则设定属性 */
		/** oneAdd 词条中属性为生成一级属性 */
		if (attrType[0].equals(attr.type)) {
			switch (attrName) {
			case "tili":// 10 - 234
			case "jingli": // 10 - 234
			case "li":// 20 - 220
			case "min":// 20 - 220
				addAttr.attrValue = (10 + (rank + 6) * 4) * power;
				break;
			case "lucky":// 10 - 50
				addAttr.attrValue = (5 + rank) * power;
				break;
			default:
				break;
			}
			/** 乘以设定的倍数 */
			addAttr.attrValue = addAttr.attrValue * attr.ratio;
			// System.out.println("特殊属性值:"+attrName+addAttr.attrValue);
			/** 根据装备的品质进行加强 */
			addAttr.attrValue = attrAddByType(addAttr.attrValue, type);
			// System.out.println("加强特殊属性值:"+attrName+addAttr.attrValue);
			/** 去除小数点 */
			addAttr.attrValue = attrFloat(addAttr.attrValue);
			// System.out.println("格式化特殊属性值:"+attrName+addAttr.attrValue);
			for (int i = 0; i < oneAttrAry.length; i++) {
				if (oneAttrAry[i].equals(attrName)) {
					/** 属性类型 */
					addAttr.type = i;
					break;
				}
			}
			attrValueStr = addAttr.attrValue + "";
			addAttr.des = "● " + oneAttrNameAry[addAttr.type] + "+"
					+ attrValueStr.substring(0, attrValueStr.length() - 2);
		} else if (attrType[1].equals(attr.type)) {
			/** 二级属性 */
			switch (attrName) {// "hp","mp","attack","defense","baoji","baolv"
			case "hp":// 10 + rank + rd.nextInt(){} 200 - 2360
				addAttr.attrValue = (200 + (rank + 5) * 48) * power;
				break;
			case "mp": // 50 - 760
				addAttr.attrValue = (50 + (rank + 5) * 16) * power;
				break;
			case "attack":// 45 - 855
				addAttr.attrValue = (45 + (rank + 5) * 18) * power;
				break;
			case "defense":// 30 - 485
				addAttr.attrValue = (30 + (rank + 5) * 10) * power;
				break;
			case "baoji":// 5 - 16
				addAttr.attrValue = (5 + (rank + 5) * 0.25) * power;
				break;
			default:
				break;
			}
			/* if ("baoji".equals(attrName)) { *//** 暴击率 */
			/*
			 * addAttr.attrValue = Math.floor((9 +
			 * rank)*attr.ratio*(rd.nextInt(20)+14)/100 + 0.5); } else
			 * if("baolv".equals(attrName)){//爆率小数 0.2-0.9 // addAttr.attrValue
			 * = attr.ratio*(rd.nextInt(40+rank)+20)/100;//爆率加成0.2-1.0 0.1-0.5
			 * }else { addAttr.attrValue = Math.round(((rank*5+15)*4-35) *
			 * attr.ratio);//40-450 }
			 */
			/** 乘以设定的倍数 */
			addAttr.attrValue = addAttr.attrValue * attr.ratio;

			/** 根据装备的品质进行加强 */
			addAttr.attrValue = attrAddByType(addAttr.attrValue, type);

			/** 去除小数点 */
			addAttr.attrValue = attrFloat(addAttr.attrValue);

			for (int i = 0; i < twoAttrAry.length; i++) {
				if (twoAttrAry[i].equals(attrName)) {
					/** 属性类型 */
					addAttr.type = i;
					break;
				}
			}
			attrValueStr = attrFloat(addAttr.attrValue) + "";
			addAttr.des = "● " + twoAttrNameAry[addAttr.type] + "+"
					+ attrValueStr.substring(0, attrValueStr.length() - 2);
			/*
			 * if("baolv".equals(attrName)){ attrValueStr =
			 * addAttr.attrValue*1+""; addAttr.des =
			 * "● "+twoAttrAry[addAttr.type]+"+"+attrValueStr.substring(0,
			 * attrValueStr.length()-2) ; }
			 */
		} else if (attrType[2].equals(attr.type)) {
			/** 特殊属性 attack|defense|hp|mp|suck|exp|money */
			switch (attrName) {
			case "suck": // 10 - 35
				addAttr.attrValue = (rd.nextInt(rank / 2 + 1) + 10) * 0.01
						* power;
				break;
			case "exp": // 20-100
			case "money":
				addAttr.attrValue = ((rank * 2) + 20) * 0.01 * power;
				break;
			case "attack": // 0.2 - 1.0
				addAttr.attrValue = ((rank * 2) + 20) * 0.01 * power;
				break;
			case "defense": // 0.2 - 1.0
				addAttr.attrValue = ((rank * 2) + 20) * 0.01 * power;
				break;
			case "hp": // 0.4 - 1.2
				addAttr.attrValue = (rank + 50 + 30) * 0.02 * power;
				break;
			case "mp": // 0.4 - 1.2
				addAttr.attrValue = ((rank + 70) + 50) * 0.02 * power;
				break;
			default:
				break;
			}
			/** 乘以设定的倍数 */
			addAttr.attrValue = addAttr.attrValue * attr.ratio;

			/** 根据装备的品质进行加强 */
			addAttr.attrValue = attrAddByType(addAttr.attrValue, type);

			/** 去除小数点 */
			addAttr.attrValue = attrFloat(addAttr.attrValue);

			for (int i = 0; i < specialAttrAry.length; i++) {
				if (specialAttrAry[i].equals(attrName)) {
					/** 属性类型 */
					addAttr.type = i;
					break;
				}
			}

			attrValueStr = attrFloat(addAttr.attrValue * 100) + "";
			addAttr.des = "● " + specialAttrNameAry[addAttr.type]
					+ attrValueStr.substring(0, attrValueStr.length() - 2)
					+ "%";
		} else if (attrType[3].equals(attr.type)) {
			/** 宠物属性 attack|defense|hp|all */
			switch (attrName) {
			case "attack":// 0.3 - 1.1
				addAttr.attrValue = ((rank + 40) + 30) * 0.01 * power;
				break;
			case "defense":// 0.3 = 1.1
				addAttr.attrValue = ((rank + 40) + 30) * 0.01 * power;
				break;
			case "hp":// 0.4-140
				addAttr.attrValue = ((rank + 40) + 60) * 0.01 * power;
				break;
			case "all":// 0.2-0.7
				addAttr.attrValue = ((rank + 30) + 20) * 0.01 * power;
				break;
			default:
				break;
			}
			/** 乘以设定的倍数 */
			addAttr.attrValue = addAttr.attrValue * attr.ratio;

			/** 根据装备的品质进行加强 */
			addAttr.attrValue = attrAddByType(addAttr.attrValue, type);

			/** 去除小数点 */
			addAttr.attrValue = attrFloat(addAttr.attrValue);

			for (int i = 0; i < petAttAry.length; i++) {
				if (petAttAry[i].equals(attrName)) {
					/** 属性类型 */
					addAttr.type = i;
					break;
				}
			}
			attrValueStr = attrFloat(addAttr.attrValue * 100) + "";
			addAttr.des = "● 宠物" + petAttNameAry[addAttr.type]
					+ attrValueStr.substring(0, attrValueStr.length() - 2)
					+ "%";
		} else if (attrType[4].equals(attr.type)) {
			/** 技能属性 */
			addAttr.des = "● ";
		}
		return addAttr;
	}


	/** 根据装备品质对词条进行强化 */
	public double attrAddByType(double value, int type) {
		for (int i = 0; i < type; i++) {
			value = value * 1.2;
		}
		return value;
	}

	/** 属性浮动 >2 则取整,不然取两位小数 */
	public double attrFloat(double value) {
		return Math.ceil(rd.nextInt((int) value / 2 + 1) + value * 0.7);
	}

	/** 计算攻击力 */
	public int getAttack(int baseAtk,int atk, int li, int weaponEd) {
		int finValue = SUtils.reDouPoint((baseAtk+atk) * (1 + weaponEd*0.01) * (1 + li / 200));
		dataCheck.setLabelText("("+baseAtk+"+"+atk+")*(1+"+weaponEd+"*0.01)*(1+"+li+"/200)="+finValue, 2);
		return finValue;
	}

	/** 计算防御力 */
	public int getDefense(int baseDef,int def, int min, int armorEd) {
		int finValue = SUtils.reDouPoint((baseDef + def) * (1 + armorEd*0.01) * (1 + min / 200));
		dataCheck.setLabelText("("+baseDef+"+"+def+")*(1+"+armorEd+"*0.01)*(1+"+min+"/200)="+finValue, 3);
		return finValue;
	}

	/** 计算血量 */
	public int getHp(int baseHp, int hp, int tili, int armorEd) {
		int finValue = SUtils.reDouPoint((baseHp + hp) * (1 + armorEd*0.01) * (1 + tili / 200));
		dataCheck.setLabelText("("+baseHp+"+"+hp+")*(1+"+armorEd+"*0.01)*(1+"+tili+"/200)="+finValue, 0);
		return finValue;
	}

	/** 计算蓝量 */
	public int getMp(int baseMp,int mp, int jingli, int armorEd) {
		int finValue = SUtils.reDouPoint((baseMp + mp) * (1 + armorEd*0.01) * (1 + jingli / 200));
		dataCheck.setLabelText("("+baseMp+"+"+mp+")*(1+"+armorEd+"*0.01)*(1+"+jingli+"/200)="+finValue, 1);
		return finValue;
	}

	/** 计算暴击率 */
	public int getBaoji( int baseBaoji, int baoji, int lucky) {
		int finValue = (baseBaoji + baoji) * (lucky / 100);
		return finValue;
	}

	public AddAttrs newADdAttrs() {
		return new AddAttrs();
	}
	
	/**
	 * 计算得到人物穿戴装备后的最后属性
	 * 人物的基本属性甲装备属性
	 */
	public void calReallyAttr(Player player,AddAttrs addAttrs){
		//设置一级属性 力,敏,体力,精力,幸运值
		player.setTili(player.getTili()+addAttrs.tili);
		player.setJingli(player.getJingli()+addAttrs.jingli);
		player.setLi(player.getLi()+addAttrs.li);
		player.setMin(player.getMin()+addAttrs.min);
		player.setLucky(player.getLucky()+addAttrs.lucky);
		
		//设置二级属性   血量,蓝量,攻击,防御力,暴击几率
		player.setHp(getHp(player.getHp(),addAttrs.hp, player.getTili(), addAttrs.hpEd));
		player.setCurHp(player.getHp());
		player.setMp(getMp(player.getMp(),addAttrs.mp, player.getJingli(), addAttrs.mpEd));
		player.setCurMp(player.getMp());
		player.setAttack(getAttack(player.getAttack(),addAttrs.attack, player.getLi(), addAttrs.atkEd));
		player.setDefense(getDefense(player.getDefense(),addAttrs.defense, player.getMin(), addAttrs.defEd));
		player.setBaoji(getBaoji(player.getBaoji(),addAttrs.baoji, player.getLucky()));
		
		// suck baolv 
		player.setSuck(player.getSuck()+addAttrs.suck);
		player.setBaolv(player.getBaolv()+addAttrs.baolvEd);
		player.setExpAdd(player.getExpAdd()+addAttrs.expEd);
		player.setMoneyAdd(player.getMoneyAdd()+addAttrs.moneyEd);
		player.setPetAdd(player.getPetAdd()+addAttrs.petAll);
	}
	
	/**
	 * 计算得到人物穿戴装备后的最后属性
	 * 人物的基本属性甲装备属性
	 */
	public void calNpcReallyAttr(NPC npc,AddAttrs addAttrs){
		//设置一级属性 力,敏,体力,精力,幸运值
		npc.setTili(npc.getTili()+addAttrs.tili);
		npc.setJingli(npc.getJingli()+addAttrs.jingli);
		npc.setLi(npc.getLi()+addAttrs.li);
		npc.setMin(npc.getMin()+addAttrs.min);
		npc.setLucky(npc.getLucky()+addAttrs.lucky);
		
		//设置二级属性   血量,蓝量,攻击,防御力,暴击几率
		npc.setHp(getHp(npc.getHp(),addAttrs.hp, npc.getTili(), addAttrs.hpEd));
		npc.setCurHp(npc.getHp());
		npc.setMp(getMp(npc.getMp(),addAttrs.mp, npc.getJingli(), addAttrs.mpEd));
		npc.setCurMp(npc.getMp());
		npc.setAttack(getAttack(npc.getAttack(),addAttrs.attack, npc.getLi(), addAttrs.atkEd));
		npc.setDefense(getDefense(npc.getDefense(),addAttrs.defense, npc.getMin(), addAttrs.defEd));
		npc.setBaoji(getBaoji(npc.getBaoji(),addAttrs.baoji, npc.getLucky()));
		
		// suck baolv 
		npc.setSuck(addAttrs.suck);
	
	}
	
	/**
	 * 内部类
	 * 装备带来的属性加成
	 * @author yilong22315
	 * 一共22条属性
	 */
	public class AddAttrs {
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

	/**--------------------------------------------------------------------------------
	 * ------------------------------------AddAttrs控制 begin----------------------------
	   -------------------------------------------------------------------------------- */
	
	/**
	 * 在此游戏中，人物的属性值会给玩家带来二级属性的百分比加成
	 */
	public AddAttrs calAttrAdd(AddAttrs attrs){
		// 数值*100
		attrs.hpAdd = attrs.tili/200*100 ;
		attrs.mpAdd = attrs.jingli/200*100 ;
		attrs.atkAdd = attrs.li/200*100 ;
		attrs.defAdd = attrs.min/200*100 ;
		attrs.baojiAdd = attrs.lucky/100*100 ;
		attrs.baolvAdd = attrs.lucky/50*100;
		return attrs;
	}
	
	
	/**
	 * 传入上一件装备的属性加成 和 新装备的词条集合 进行累加
	 * @param attrs
	 * @param list
	 * @return 返回这件装备能带来的属性加成
	 */
	public AddAttrs countAttr(AddAttrs attrs, Citiao[] list) {
		Citiao temp = null;
		for (int i = 0; i < list.length; i++) {
			temp = list[i];
			attrs = countAttr(attrs, temp);
		}
		return attrs;
	}
	
	/**
	 * 累加装备属性效果
	 * @param list 传入一件装备的词条集合
	 * @return 返回这件装备能带来的属性加成
	 */
	public AddAttrs countAttr(Citiao[] list) {
		AddAttrs attrs = new AddAttrs();
		Citiao temp = null;
		for (int i = 0; i < list.length; i++) {
			temp = list[i];
			attrs = countAttr(attrs, temp);
		}
		return attrs;
	}
	
	/**
	 * 把新装备的属性值 加入到  现在人物属性值中
	 * @param cur
	 * @param add
	 * @return
	 */
	public AddAttrs addEquipAttr(AddAttrs cur,AddAttrs add){
		cur.tili += add.tili;
		cur.jingli += add.jingli;
		cur.li += add.li;
		cur.min += add.min;
		cur.lucky += add.lucky;
		cur.hp += add.hp;
		cur.mp += add.mp;
		cur.attack += add.attack;
		cur.defense += add.defense;
		cur.baoji += add.baoji;
		cur.hpEd += add.hpEd;
		cur.mpEd += add.mpEd;
		cur.atkEd += add.atkEd;
		cur.defEd += add.defEd;
		cur.suck += add.suck;
		cur.expEd += add.expEd;
		cur.moneyEd += add.moneyEd;
		cur.baolvEd += add.baolvEd;
		cur.petHp += add.petHp;
		cur.petAtk += add.petAtk;
		cur.petDef += add.petDef;
		cur.petAll += add.petAll;
		return cur ;
	}
	
	/**
	 * 现在人物属性值中 加入 去下的装备的全部属性值
	 * @param cur
	 * @param remove
	 * @return 
	 */
	public AddAttrs removeEquipAttr(AddAttrs cur,AddAttrs remove){
		cur.tili -= remove.tili;
		cur.jingli -= remove.jingli;
		cur.li -= remove.li;
		cur.min -= remove.min;
		cur.lucky -= remove.lucky;
		cur.hp -= remove.hp;
		cur.mp -= remove.mp;
		cur.attack -= remove.attack;
		cur.defense -= remove.defense;
		cur.baoji -= remove.baoji;
		cur.hpEd -= remove.hpEd;
		cur.mpEd -= remove.mpEd;
		cur.atkEd -= remove.atkEd;
		cur.defEd -= remove.defEd;
		cur.suck -= remove.suck;
		cur.expEd -= remove.expEd;
		cur.moneyEd -= remove.moneyEd;
		cur.baolvEd -= remove.baolvEd;
		cur.petHp -= remove.petHp;
		cur.petAtk -= remove.petAtk;
		cur.petDef -= remove.petDef;
		cur.petAll -= remove.petAll;
		return cur;
	}

	/***                  
	 * 总属性加成addAttrs 和 单词条属性加成addAttr
	 * @param addAttrs
	 * @param citiao 储存词条的具体信息
	 * @return
	 */
	public AddAttrs countAttr(AddAttrs addAttrs, Citiao citiao) {
		if (citiao.type == 0) {//一级属性
			switch (oneAttrAry[citiao.attr]) {
			case "tili":
				addAttrs.tili += citiao.attrValue;
				break;
			case "jingli":
				addAttrs.jingli += citiao.attrValue;
				break;
			case "li":
				addAttrs.li += citiao.attrValue;
				break;
			case "min":
				addAttrs.min += citiao.attrValue;
				break;
			case "lucky":
				addAttrs.lucky += citiao.attrValue;
				break;
			default:
				break;
			}
		} else if (citiao.type == 1) {//二级属性
			switch (twoAttrAry[citiao.attr]) {
			case "hp":
				addAttrs.hp += citiao.attrValue;
				break;
			case "mp":
				addAttrs.mp += citiao.attrValue;
				break;
			case "attack":
				addAttrs.attack += citiao.attrValue;
				break;
			case "defense":
				addAttrs.defense += citiao.attrValue;
				break;
			case "baoji":
				addAttrs.baoji += citiao.attrValue;
				break;
			default:
				break;
			}
		} else if (citiao.type == 2) {//特殊属性
			switch (specialAttrAry[citiao.attr]) {
			case "hp":
				addAttrs.hpEd += citiao.attrValue;
				break;
			case "mp":
				addAttrs.mpEd += citiao.attrValue;
				break;
			case "attack":
				addAttrs.atkEd += citiao.attrValue;
				break;
			case "defense":
				addAttrs.defEd += citiao.attrValue;
				break;
			case "suck":
				addAttrs.suck += citiao.attrValue;
				break;
			case "exp":
				addAttrs.expEd += citiao.attrValue;
				break;
			case "money":
				addAttrs.moneyEd += citiao.attrValue;
				break;
			case "baolv":
				addAttrs.baolvEd += citiao.attrValue;
				break;
			default:
				break;
			}
		} else if (citiao.type == 3) {
			switch (petAttAry[citiao.attr]) {
			case "hp":
				addAttrs.petHp += citiao.attrValue;
				break;
			case "attack":
				addAttrs.petAtk += citiao.attrValue;
				break;
			case "defense":
				addAttrs.petDef += citiao.attrValue;
				break;
			case "all":
				addAttrs.petAll += citiao.attrValue;
				break;
			default:
				break;
			}
		}
		return addAttrs;
	}
	
	/** 装备加成 */
	private String[] equipAddAry = { "li", "min", "tili", "jingli", "lucky", "hp",
			"mp", "attack", "defense", "baoji", "suck", "hpEd", "mpEd",
			"atkEd", "defEd", "expEd", "moneyEd", "baolvEd", "petHp", "petAtk",
			"petDef", "petAll" };
	
	/**
	 * 特殊装备掉落计算方式
	 * @param eqList
	 * @param type
	 * @return 掉落列表
	 */
	public List<Equip> spDropPredict(List<Equip> eqList,int type){
		List<Equip> eqDrop = new ArrayList<>() ;
		Equip equip = null ; 
		int eqType = 0 ;
		int lv = 0 ;
		int num = 0 ; 
	 	for (int i = 0; i < eqList.size(); i++) {
			equip = eqList.get(i);
			if(equip!=null){
				eqType = equip.getType();
				lv = (eqType - type)*20 ;
				num = rd.nextInt(100)+1;
				System.out.println("当前特殊装备为:"+equip.getName()+",掉落几率为:"+lv+",当前随机数为:"+num);
				if(num<lv){
					eqDrop.add(equip);
				}
			}
		}
	 	System.out.println("特殊掉落装备:"+eqDrop.size());
		return eqDrop;
	}
	
	/** 掉落预测 
	 * 判断这次战斗掉落物品
	 * @param type
	 * @param rank
	 * @param p
	 * @return
	 */
	public List<Equip> dropPredict(int type,int rank,Player player){
		double odds = (1 + player.getLucky()/50) * (1 + player.getBaolv());
		System.out.println("爆率加成："+odds+",该NPC品质为:"+type);
		int baolv = 7+type*4;
		baolv = (int) (baolv*(1+odds*0.01)) ;
		baolv = baolv > 100 ? 10000 : baolv*100 ;
		System.out.println(baolv);
		int d1 = 0,d2 = 0,d3 = 0,d4 = 0,d5 = 0 ;
		List<Equip> eqList = new ArrayList<>();
		if(type==0){
			d1 = baolv ;
			d2 =  d1/2 ;
			d3 =  d2/2 ;
			d4 =  d3/2 ;
			d5 =  d4/2 ;
		}else if(type==1){
			d2 =  baolv ;
			d1 =  d2*2 ;
			d3 =  d2/2 ;
			d4 =  d3/2 ;
			d5 =  d4/2 ;
		}else if(type==2){
			d3 =  baolv ;
			d2 =  d3*2 ;
			d1 =  d2*2 ;
			d4 =  d3/2 ;
			d5 =  d4/2 ;
		}else if(type==3){
			d4 =  baolv ;
			d3 =  d4*2 ;
			d2 =  d3*2 ;
			d1 =  d2*2>10000?10000:d2*2 ;
			d5 =  d4/2 ;
		}else if(type==4){
			d5 =  baolv ;
			d4 =  d5*2 ;
			d3 =  d4*2>10000?10000:d4*2 ;
			d2 =  d3*2>10000?10000:d3*2 ;
			d1 =  d2*2>10000?10000:d2*2 ;
		}
		System.out.println("d1:"+d1+",d2:"+d2+",d3:"+d3+",d4:"+d4+",d5:"+d5);
		int lv = 100 ;
		int num = 0;
		int curT = 4 ;
		while(curT>-1){
			num = rd.nextInt(100)+1;
			if(num<lv){
				num = rd.nextInt(10000)+1;
				System.out.println("curT:"+curT+","+"rdNum:"+num);
				switch (curT) {
				case 0:
					if(num<d1){
						eqList.add(equipGenerate(rank, rdPart(), 0));
						lv/=2;
					}else{
						curT--;
						lv=100;
					}
					break;
				case 1:
					if(num<d2){
						eqList.add(equipGenerate(rank, rdPart(), 1));
						lv/=2;
					}else{
						curT--;
						lv=100;
					}
					break;
				case 2:
					if(num<d3){
						eqList.add(equipGenerate(rank, rdPart(), 2));
						lv/=2;
					}else{
						curT--;
						lv=100;
					}
					break;
				case 3:
					if(num<d4){
						eqList.add(equipGenerate(rank, rdPart(), 3));
						lv/=2;
					}else{
						curT--;
						lv=100;
					}
					break;
				case 4:
					if(num<d5){
						eqList.add(equipGenerate(rank, rdPart(), 4));
						lv/=2;
					}else{
						curT--;
						lv=100;
					}
					break;
				default:
					break;
				}
			}else{
				curT--;
				lv=100;
			}
		}
		for (int i = 0; i < eqList.size(); i++) {
			if(eqList.get(i)!=null){
				System.out.println(typeAry[eqList.get(i).getType()]);
			}
		}
		System.out.println("普通掉落装备的数量:"+eqList.size());
		
		/*Equip tempEq = null ;
		for (int i = 0; i < baolvAry.length; i++) {
			baolvAry[i] = SUtils.getTwoDecimal(baolvAry[i]);
			int rdNum = rd.nextInt(10000);
			if(rdNum<baolvAry[i]*100){
				tempEq = new Equip() ;
				tempEq.setType(i); 
				tempEq.setRank(rank); 
				eqList.add(tempEq);
			}
		}*/
		return eqList ;
	}
	
	/**
	 * 为npc随机生成全套装备
	 * @param npc 
	 * @param rank 等级 
	 * @param type 品质
	 * @return 返回npc全身装备
	 */
	public Equip[] getWholeBodyEq(NPC npc,int rank,int type){
		/** 基本几率,npc身上的装备品质可能会比他的品质高 */
		int lv = 8 ;
		int typeLV1 = 0 ;
		int typeLV2 = 0 ;
		int typeLV3 = 0 ;
		int typeLV4 = 0 ;
		if(type==0){
			typeLV1 = lv ;
			typeLV2 = lv/2+typeLV1 ;
			typeLV3 = lv/4+typeLV2 ;
			typeLV4 = lv/8+typeLV3 ;
		}else if(type==1){
			typeLV2 = lv ;
			typeLV3 = lv/2 + typeLV2 ;
			typeLV4 = lv/4 + typeLV3 ;
		}else if(type==2){
			typeLV3 = lv ;
			typeLV4 = lv/2+typeLV3 ;
		}else if(type==3){
			typeLV4 = lv ;
		}
		int realType = -1 ;
		Equip[] eqAry = new Equip[8] ;
		/** 储存npc身上突破装备的集合 */
		List<Equip> spEqList = new ArrayList<>() ;
		Equip temp = null ;
		for (int i = 0; i < 8; i++) {
			int num = rd.nextInt(100)+1;
			if(num<typeLV1){
				realType = 1 ;
			}else if(typeLV1<num&&num<=typeLV2){
				realType = 2 ;
			}else if(typeLV2<num&&num<=typeLV3){
				realType = 3 ;
			}else if(typeLV3<num&&num<=typeLV4){
				realType = 4 ;
			}else{
				realType = type ;
			}
			temp = equipGenerate(rank,i,realType);
			if(temp==null){
				temp = new Equip();
			}
			/** 装备品质比人物品质高则加入 */
			if(realType>type){
				spEqList.add(temp);
			}
			eqAry[i]=temp;
		}
		/** npc身上品质比它本身品质高的装备 */
		npc.setSpEquipList(spEqList);
		/** 设置全身装备 */
		npc.setEquipAry(eqAry);
		return eqAry;
	}
	
	public static void main(String[] args) {
	/*
		*//** 部位测试 *//*
		
		 * int[] ary = new int[100]; for (int i = 0; i < 100; i++) { ary[i] =
		 * e.rdPart() ; System.out.print(Equip.partDes[ary[i]]+"	"); }
		 * System.out.println("---------------------------------");
		 * Arrays.sort(ary); for (int i = 0; i < 100; i++) {
		 * System.out.print(Equip.partDes[ary[i]]+"	"); }
		 
		*//** 品质测试 *//*
		
		 * int[] ary = new int[1000]; for (int i = 0; i < 1000; i++) { ary[i] =
		 * e.rdType(49, 2); System.out.println(Equip.typeAry[ary[i]]); }
		 * HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>(); for
		 * (int i = 0; i < ary.length; i++) { if (!hm.containsKey(ary[i])) {
		 * hm.put(ary[i], 1); } else { hm.put(ary[i], (hm.get(ary[i])) + 1); } }
		 * for (Map.Entry<Integer, Integer> entry : hm.entrySet()) {
		 * System.out.println(Equip.typeAry[entry.getKey()] + "出现了" +
		 * entry.getValue() + "次"); }
		 
		int rank = 5;
		int type = 0;
		for (int i = 0; i < 10; i++) {
			*//** 1级人物攻击45 一级 武器 *//*
			System.out.println(rank + "级基础攻击力为:" + Player.getBaseAck(rank)
					+ ", 一级白装攻击力为:" + e.rdAddAttr(rank, 0, 0, type).attrValue);

		}*/
		EquipControl e = new EquipControl();
		for (int i = 0; i < 6; i++) {
			e.getWholeBodyEq(null, 20, 1);
		}
	}
	
}
