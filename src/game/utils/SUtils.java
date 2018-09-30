package game.utils;

import game.control.EquipControl;
import game.control.GameControl;
import game.entity.AddAttrs;
import game.entity.Archive;
import game.entity.CitiaoSD;
import game.entity.Ditu;
import game.entity.Equip;
import game.entity.Gong;
import game.entity.Item;
import game.entity.Material;
import game.entity.NPC;
import game.entity.Player;
import game.entity.Scene;
import game.entity.Skill;
import game.entity.Tasks;
import game.entity.Tier;
import game.listener.BagActionListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import sun.audio.AudioStream;

public class SUtils {
	public static final Color Red = new Color(0xF44336);
	public static final Color Pink = new Color(0xE91E63);
	public static final Color Purple = new Color(0x9C27B0);
	/** 靛蓝 */
	public static final Color Indigo = new Color(0x673AB7);
	public static final Color Blue = new Color(0x2196F3);
	public static final Color LightBlue = new Color(0x03A9F4);
	public static final Color Cyan = new Color(0x00BCD4);
	public static final Color Teal = new Color(0x009688);
	public static final Color Green = new Color(0x4CAF50);
	public static final Color LightGreen = new Color(0x8BC34A);
	public static final Color Lime = new Color(0xCDDC39);
	public static final Color Yellow = new Color(0xFFEB3B);
	public static final Color Amber = new Color(0xFFC107);
	public static final Color Orange = new Color(0xFF9800);
	public static final Color DeepOrange = new Color(0xFF5722);
	public static final Color Brown = new Color(0x795548);
	public static final Color Grey = new Color(0x9E9E9E);
	public static final Color BlueGrey = new Color(0x607D8B);
	public static final Color Black = new Color(0x000000);
	public static final Color White = new Color(0xFFFFFF);
	
	
	private static int count = 21;

	/**
	 * 随机数生成
	 */
	private static Random rd = new Random(System.currentTimeMillis());

	private static GameControl gameControl = GameControl.getInstance();

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 *            字符串
	 * @return true为空 false 不为空
	 */
	public static boolean isNullStr(String s) {
		if (s == null || s.trim().length() <= 0)
			return true;
		else
			return false;
	}

	public static String strTrim(String s){
		return s==null?"":s.trim();
	}
	
	public static String strTrim(Object obj){
		String s = conObjToStr(obj);
		return s==null?"":s.trim();
	}
	
	/**
	 * Object 转化成 int
	 * 
	 * @param obj
	 * @return
	 */
	public static int conObjToInt(Object obj) {
		return obj == null || "".equals(obj.toString()) ? 0 : Integer
				.parseInt(obj.toString().trim());
	}

	/**
	 * Object转化为str
	 * 
	 * @param str
	 * @return
	 */
	public static double conStrToDou(String str) {
		return isNullStr(str) ? 0.0 : Double.parseDouble(str);
	}
	
	/**
	 * String转化为boolean
	 * 
	 * @param str
	 * @return
	 */
	public static boolean conStrToBol(String str) {
		return isNullStr(str) ? false : Boolean.parseBoolean(str);
	}

	
	/**
	 * 加载xml文件
	 * 
	 * @param filename
	 * @return
	 */
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");
			document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * 加载对应装备的属性生成设定
	 * 
	 * @param part
	 * @return
	 */
	public static List<CitiaoSD> loadEquipSetting(int part) {
		/** 对应部位的设定列表 */
		List<CitiaoSD> list = new ArrayList<>();
		Document document = load("src/game/xml/equipSetting.xml");
		/** 获取根目录 */
		Element root = document.getRootElement();
		List<Element> temp = root.elements();
		/**  */
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i).attribute("name").getText()
					.equals(EquipControl.partAry[part])) {
				temp = temp.get(i).elements();
				break;
			}
		}
		// System.out.println("size:"+temp.size()+" , "+EquipControl.partAry[part]);
		/** 5条设定 每条设定六个属性 */
		for (int i = 0; i < temp.size(); i++) {
			String type = temp.get(i).element("type").getText();
			String attrType = temp.get(i).element("attrType").getText();
			String attrName = temp.get(i).element("attrName").getText();
			String ratio = temp.get(i).element("value").getText();
			CitiaoSD theAttr = new CitiaoSD(part, i, conStrtoInt(type),
					attrType, attrName, conStrtoDou(ratio));
			// System.out.println(theAttr.toString());
			list.add(theAttr);
		}
		return list;
	}

	/**
	 * 加载所有的装备信息
	 * 武器，防具
	 * @return
	 */
	public static Map<String,Equip> loadEquip() {
		Map<String,Equip> equipMap = new HashMap<>();
		Document document = load("src/game/xml/equip.xml");
		/** 迭代获得所有元素 */
		/** 获取根目录 */
		Element root = document.getRootElement();
		/** 获取根目录下所有元素 */
		List<Element> list = root.elements();
		int part = 0, rank = 0;
		String mode, name, kind, des, id ;
		Equip temp = null;
		for (Element e : list) {
			// 获取属性值 
			part = conStrtoInt(e.attributeValue("part"));
			mode = e.attributeValue("mode");
			id = e.element("id").getText(); ;
			name = e.element("name").getText();
			kind = e.element("kind").getText();
			des = e.element("des").getText();
			rank = conStrtoInt(e.element("rank").getText());
			temp = new Equip();
			temp.setId(id);
			temp.setPart(part);
			temp.setMode(mode);
			temp.setName(name);
			temp.setKind(kind);
			temp.setDes(des);
			temp.setRank(rank);
			equipMap.put(id, temp);
		}
		return equipMap;
	}
	
	public static Map<String,Skill> loadSkill(){
		Map<String,Gong> map = new HashMap<>() ;
		Document document = load("src/game/xml/skill.xml");
		Element root = document.getRootElement();
		List<Element> eleList = root.elements("skill");
		Skill skillTmp = null ;
		/**
		<name></name>
		<des></des>
		<!-- 消耗 -->
		<consume></consume>
		<!-- 结果 -->
		<result></result>
		<!-- 目标 -->
		<target></target>
		<scope></scope>
		<gongId></gongId>
		<gongName></gongName>
		<useCase></useCase>
		<studyCase></studyCase>
		<needTier></needTier>
		<cd></cd>
		<curCd></curCd>
		 */
		Map<String,Skill> skillMap  = new HashMap<>() ;
		//String id,name,des,consume,result,target,scope,gongId,gongName,useCase,studyCase;
		//int needTier,cd,curCd ; 
		for (int i = 0; i < eleList.size(); i++) {
			skillTmp = new Skill();
			skillTmp.id = strTrim(eleList.get(i).attributeValue("id"));
			skillTmp.name = strTrim(eleList.get(i).elementText("name"));
			skillTmp.des = strTrim(eleList.get(i).elementText("des"));
			skillTmp.type = strTrim(eleList.get(i).elementText("type"));
			skillTmp.consume = strTrim(eleList.get(i).elementText("consume"));
			skillTmp.result = strTrim(eleList.get(i).elementText("result"));
			skillTmp.target = strTrim(eleList.get(i).elementText("target"));
			skillTmp.scope = strTrim(eleList.get(i).elementText("scope"));
			skillTmp.gongId = strTrim(eleList.get(i).elementText("gongId"));
			skillTmp.gongName = strTrim(eleList.get(i).elementText("gongName"));
			skillTmp.useCase = strTrim(eleList.get(i).elementText("useCase"));
			skillTmp.studyCase = strTrim(eleList.get(i).elementText("studyCase"));
			skillTmp.needTier = conStrtoInt(eleList.get(i).elementText("needTier"));
			skillTmp.cd = conStrtoInt(eleList.get(i).elementText("cd"));
			skillTmp.curCd = conStrtoInt(eleList.get(i).elementText("curCd"));
			skillMap.put(skillTmp.id, skillTmp);
		}
		System.out.println("加载了"+skillMap.size()+"个技能");
		return skillMap;
	}
	
	/**
	 * 加载功法
	 * @return
	 */
	public static Map<String,Gong> loadGong(){
		Map<String,Gong> map = new HashMap<>() ;
		Document document = load("src/game/xml/gong.xml");
		Element root = document.getRootElement();
		List<Element> eleList = root.elements();
		List<Element> tierList = root.elements();
		Gong gong = null ;
		/** 功的属性 */
		String id, name, des, req ;
		int quality, maxTier, curTier, type, needRank ;
		List<Skill> allSkills , hasLearn ;
		AddAttrs addAttrs ;
		Tier tier ;
		for (int i = 0; i < eleList.size(); i++) {
			gong = new Gong() ;
			id = eleList.get(i).attributeValue("id");
			name = eleList.get(i).elementText("name");
			des = eleList.get(i).elementText("des");
			type = conStrtoInt(eleList.get(i).elementText("type"));
			maxTier = conStrtoInt(eleList.get(i).elementText("maxTier"));
			curTier = conStrtoInt(eleList.get(i).elementText("curTier"));
			quality = conStrtoInt(eleList.get(i).elementText("quality"));
			needRank = conStrtoInt(eleList.get(i).elementText("needRank"));
			req = eleList.get(i).elementText("req") ;
			gong.setId(id);
			gong.setName(name);
			gong.setDes(des);
			gong.setType(type);
			gong.setNeedRank(needRank);
			gong.setMaxTier(maxTier);
			gong.setCurTier(curTier);
			gong.setQuality(quality);
			gong.setRequire(req);
			gong.setPrice(getGongPrice(1, quality));
			/** 加载功法每一层的信息 */
			Element effect = eleList.get(i).element("effect");
			tierList = effect.elements();
			/**
			 * <tier value="4">
			 *		<needRank>7</needRank>
			 *		<AddAttrs>
			 *			hp:60%;
			 *			mp:60%
			 *		</AddAttrs>
			 *	</tier>
			 */
			for (int j = 0; j < tierList.size(); j++) {
				tier = new Tier() ;
				tier.curTier = conStrtoInt(tierList.get(j).attributeValue("value"));
				tier.needRank = conStrtoInt(tierList.get(j).elementText("needRank"));
				tier.needExp = conObjToStr(tierList.get(j).elementText("needExp"));
				addAttrs = new AddAttrs();
				String addAttrsValue =  tierList.get(j).elementText("AddAttrs");
				String[] ary = addAttrsValue.trim().split(";");
				String addAttrStr = "" ;
				/**
				 * hp:60%;
       			 * mp:60%
				 */
				for (int k = 0; k < ary.length; k++) {
					addAttrStr+=ary[k].trim()+";";
					/** hp:60% */
					String[] temp = ary[k].split(":");
					/** hp */
					String attrName = temp[0].trim();
					/** 60% */
					String value = temp[1].trim();
					addAttrs = calAddAttrsValue(addAttrs,attrName, value, tier.needRank);
				}
				tier.addAttrStr = addAttrStr ;
				tier.addAttrs = addAttrs ;
				gong.getTiers().add(tier);
			}
			Element skills = eleList.get(i).element("skills");
			List<Element> skillsList = null ;
			if(skills!=null){
				skillsList = skills.elements();
			}
			if(skillsList!=null){
				for (int j = 0; j < skillsList.size(); j++) {
					Skill skillTmp = new Skill() ;
					skillTmp.id = skillsList.get(j).attributeValue("id");
					skillTmp.name = skillsList.get(j).elementText("name");
					gong.getAllSkills().add(skillTmp);
					
				}
			}
			map.put(id, gong);
		}
		System.out.println("加载得到了:"+map.size()+"部功法!");
		return map;
	}
	
	
	public List<Skill> analyzeSkill(Element element){
		List<Element> eleList = element.elements();
		List<Skill> skillList = new ArrayList<>() ;
		Element temp ; 
		Skill skill = null ;
		/**  */
		for (int i = 0; i < eleList.size(); i++) {
			temp = eleList.get(i);
			skill = new Skill();
			skill.id = temp.attributeValue("id");
			skill.name = temp.elementText("name");
			skill.des = temp.elementText("des");
			skill.type = temp.elementText("type");
			skill.target = temp.element("target").attributeValue("type");
			skill.scope = temp.elementText("target");
			skill.result = temp.elementText("result");
			skill.studyCase = temp.elementText("studyCase");
			skill.cd = conStrtoInt(temp.elementText("cd"));
			skill.useCase = temp.elementText("useCase")==null?"":temp.elementText("useCase");
			skillList.add(skill);
		}
		return skillList; 
	}
	
	/**
	 * 计算属性加成
	 * @param name 属性名
	 * @param value 属性值
	 * @param rank 等级
	 * @return
	 */
	public static AddAttrs calAddAttrsValue(AddAttrs addAttrs,String name,String value,int rank){
		boolean flag = false ;
		int attrValue = 0 ;
		if(value.endsWith("%")){
			/** 表示为百分比加成 */
			flag = true ;
			attrValue = getBaseAttrByRank(name, rank); 
			value = value.substring(0,value.length()-1);
			attrValue = reDouPoint(attrValue*conStrtoInt(value)*0.01);
			System.out.println(name+":"+value);
		}else{
			attrValue = conStrtoInt(value);
		}
		switch (name) {
		case "hp":
			addAttrs.hp+=attrValue;
			break;
		case "mp":
			addAttrs.mp+=attrValue;
			break;
		case "atk":
			addAttrs.attack+=attrValue;
			break;
		case "def":
			addAttrs.defense+=attrValue;
			break;
		case "baoji":
			addAttrs.baoji+=attrValue;
			break;
		case "suck":
			break;
		case "reHp":/** 生命回复 */
			addAttrs.reHp+=attrValue;
			break;
		case "reMp":/** 法力回复 */
			addAttrs.reMp+=attrValue;
			break;
		case "atkUp":
			addAttrs.atkUp+=attrValue;
			break;
		case "atkDown":
			addAttrs.atkDown+=attrValue;
			break;
		case "defUp":
			addAttrs.defUp+=attrValue;
			break;
		case "defDown":
			addAttrs.defDown+=attrValue;
			break;
		case "speedUp":
			addAttrs.speedUp+=attrValue;
			break;
		case "speedDown":
			addAttrs.speedDown+=attrValue;
			break;
		case "hitUp":/** 命中上升 */
			addAttrs.hitUp+=attrValue;
			break;
		case "hitDown":/** 命中下降 */
			addAttrs.hitDown+=attrValue;
			break;
		case "dodgeUp":/** 闪避上升 */
			addAttrs.dodgeUp+=attrValue;
			break;
		case "dodgeDown":/** 闪避下降 */
			addAttrs.dodgeDown+=attrValue;
			break;
		case "wuRebound":/** 物理伤害反弹 */
			addAttrs.wuRebound+=attrValue;
			break;
		case "bufRe":/** 增益移除 */
			addAttrs.bufRe+=attrValue;
			break;
		case "debufRe":/** 减益移除 */
			addAttrs.debufRe+=attrValue;
			break;
		case "daze":/** 眩晕 */
			addAttrs.daze+=attrValue;
			break;
		default:
			break;
		}
		return addAttrs;
	}
	
	
	
	
	/** 加载任务信息 */
	public static Map<String,Tasks> loadTask() {
		Map<String,Tasks> map = new HashMap<>();
		Document document = load("src/game/xml/npc.xml");
		Element root = document.getRootElement();
		List<Element> eleList = root.elements("task");
		Tasks tasks = null;
		int curState = 0 ;
		String id , taskName = "", npcId = "", taskDes = "";
		String task1, task2, task3 ;
		String startCond, acceptCond,endCond ;
		for (int i = 0; i < eleList.size(); i++) {
			id = eleList.get(i).attributeValue("id");
			taskName = eleList.get(i).element("taskName").getText();
			curState = conStrtoInt(eleList.get(i).element("curState").getText());
			npcId = eleList.get(i).element("npcId").getText() ;
			taskDes = eleList.get(i).element("taskDes").getText();
			task1 = eleList.get(i).element("task1").getText() ;
			task2 = eleList.get(i).element("task2").getText() ;
			task3 = eleList.get(i).element("task3").getText() ;
			startCond = eleList.get(i).element("startCond").getText();
			acceptCond = eleList.get(i).element("acceptCond").getText();
			endCond = eleList.get(i).element("endCond").getText();
			tasks = new Tasks(); 
			tasks.setId(id);
			tasks.setTaskName(taskName);
			tasks.setCurState(curState);
			tasks.setNpcId(npcId);
			tasks.setTaskDes(taskDes);
			tasks.setTask1(task1);
			tasks.setTask2(task2);
			tasks.setTask3(task3);
			tasks.setStartCond(startCond);
			tasks.setAcceptCond(acceptCond);
			tasks.setEndCond(endCond);
			map.put(id, tasks);
		}
		return map;
	}
	
	/**
	 * 加载npc的基本信息
	 * id , name , des , msg , rank , type
	 * 而人物的属性
	 * 和与玩家的交互在之后解析
	 * @return
	 */
	public static Map<String, NPC> loadNpc() {
		Map<String, NPC> map = new HashMap<>();
		Document document = load("src/game/xml/npc.xml");
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		Element npcEle = null , action = null ;
		NPC tempNpc = null;
		int type, rank = 0 ;
		String id, name, des, msg, npcType, attrType = "";
		/** 开始每个npc的信息 */
		for (int i = 0; i < elementList.size(); i++) {
			/** 得到npc节点 */
			npcEle = elementList.get(i);
			id = npcEle.elementText("id");
			if (!isNullStr(id)) {// 不为空则加入
				name = npcEle.elementText("name");
				des = npcEle.elementText("des");
				msg = npcEle.elementText("msg");
				rank = conStrtoInt(npcEle.element("rank").getText());
				type = conStrtoInt(npcEle.element("type").getText());
				attrType = npcEle.attributeValue("attrType");
				tempNpc = new NPC();
				tempNpc.setId(id);
				tempNpc.setName(name);
				tempNpc.setDes(des);
				tempNpc.setMsg(msg);
				tempNpc.setRank(rank); 
				tempNpc.setType(type);
				tempNpc.setAttrType(attrType);
				map.put(id, tempNpc);
			}
		}
		return map;
	}
	
	
	
	/**
	 * 把xml中的npc解析成npc实体
	 * @param action
	 * @param npc
	 */
	private static void npcActionAnalyze(Element action,NPC npc) {
		if(action==null){
			return ;
		}
		List<Object> goods = null;
		List<Element> list = action.elements();
		Element tempE = null ;
		String type = "" ;
		String id,name,appear,itemType ;
		int appearLv = 0 , num = 0 ;
		/** 遍历人物所有可能动作 */
		for (int i = 0; i < list.size(); i++) {
			tempE = list.get(i);
			/** 动作的种类 */
			type = tempE.attribute("type").getText();
			List<Element> acList = tempE.elements();
			switch (type) {
			case "sell":/** 交易动作 */
				/**
				 * <ac>tempE
				 * 		<item></item>acList
				 * 		<item></item>acList
				 * </ac>
				 */
				for (int j = 0; j < acList.size(); j++) {
					id = acList.get(i).attributeValue("id") ;
					name = acList.get(i).attributeValue("name") ;
					num = SUtils.conStrtoInt(acList.get(i).attributeValue("num")) ;
					if(id.equals("bestTrader")||id.equals("bestTrader")||id.equals("bestTrader")){
						/*if(id.equals("bestTrader")){//极品商人|卖装备和图纸和材料
							goods = getBestTraderGoods(num);
						}else if(id.equals("petTrader")){//售卖宠物蛋
							goods = getPetTraderGoods(num);
						}else if(id.equals("skillTrader")){//售卖技能书
							goods = getSkillTraderGoods(num);
						}*/
						appear = acList.get(i).attributeValue("appear") ;
						npc.setAppearMode(appear);
						if(appear.equals("lvAppear")){//当商人为随机出现时才会有随即出现率
							appearLv = SUtils.conStrtoInt(acList.get(i).attributeValue("appearLv")) ;
							npc.setAppearLv(appearLv);
						}
					}else{
						//普通商人，储存货物信息 <item id="101" itemType="equip" name="长剑" num="1" type="2,3" ></item>
						itemType = acList.get(i).attributeValue("itemType");
						if(itemType.equals("equip")){//装备
							Equip equip = gameControl.getEquipMap().get(id);
							equip.setType(2);
							npc.getSellList().add(equip);
						}else if(itemType.equals("cailiao")){//材料
							Material mat = null ;
						}else if(itemType.equals("pet")){//宠物蛋
							
						}else if(itemType.equals("drawings")){//图纸
							
						}else if(itemType.equals("skillBook")){//技能书
							
						}
					}
				}
				break;
			case "detect":/** 监测动作 */
				break ;
			case "study":/** 学习动作 */
				break ;
			case "give":/** 玩家收取npc物品 */
				for (int j = 0; j < acList.size(); j++) {
					id = acList.get(i).attributeValue("id") ;
					itemType = acList.get(i).attributeValue("itemType");
					if(itemType.equals("equip")){
						/** 得到给与玩家的物品 */
						Equip equip = gameControl.getEquipMap().get(id);
					}else if(itemType.equals("cailiao")){
						
					}
				}
				break ;
			case "take":/** npc收取玩家物品 */
				
				break ;
			case "forge":/** 锻造动作 */
				break ;
			default:
				break;
			}
		}
		
	}
	
	/**
	 * 加载所有的防具信息 ,存放在Map中
	 * 
	 * @return
	 */
	/*public static Map<String, List<Equip>> loadArmor() {
		Document document = load("src/game/xml/armor.xml");
		*//** 迭代获得所有元素 *//*
		*//** 获取根目录 *//*
		Element root = document.getRootElement();
		*//** 获取根目录下所有元素 *//*
		// List<Element> list = root.elements();
		Element[] armorAry = { root.element("helmet"),
				root.element("necklace"), root.element("coat"),
				root.element("ring"), root.element("waistband"),
				root.element("trousers"), root.element("shoes") };

		List<Equip> armorList = null;
		*//**  *//*
		Map<String, List<Equip>> armorMap = new HashMap<>();
		Equip temp = null;
		for (int i = 0; i < armorAry.length; i++) {
			armorList = new ArrayList<Equip>();
			for (Element e : armorAry[i].elements()) {
				temp = new Equip();
				// 获取属性值
				// String name = e.attributeValue("name");
				String name = e.element("name").getText();
				String unit = e.element("unit").getText();
				String kind = e.element("kind").getText();
				String des = e.element("des").getText();
				String rank = e.element("rank").getText();
				String part = e.element("part").getText();
				temp.setName(name);
				temp.setUnit(unit);
				temp.setKind(kind);
				temp.setDes(des);
				temp.setRank(conStrtoInt(rank));
				temp.setPart(conStrtoInt(part));
				armorList.add(temp);
				// System.out.println(temp.toString());
			}
			armorMap.put(EquipControl.partAry[i + 1], armorList);
		}
		return armorMap;
		
		 * List<Node> list= document.selectNodes("root/weapon"); for (int i = 0;
		 * i < list.size(); i++) { System.out.println(list.get(i)); }
		 
	}*/

	

	/*
	 * public static String addSpaces(Object str) { int standard = 7;
	 * 
	 * int len = str.toString().trim().length(); StringBuffer bf = new
	 * StringBuffer(str.toString().trim()); if (len < standard) { for (int i =
	 * 0; i < standard - len; i++) { bf.append(" "); } }
	 * System.out.println("bf:" + bf.toString()); return new String(bf); }
	 */

	public static String autoNewline(String str) {
		int len = str.length();
		/** 如果字符串长度大于21 那么没21个字符就增加一个换行符 */
		if (len > count) {
			for (int i = count; i < len; i += count) {
				str = str.substring(0, i) + "\n" + str.substring(i);
			}
		}
		return str;
	}

	public static String lineSeparatorSplit(String str) {
		// 靠|号来分割
		String[] res = str.split("\\|");
		System.out.println("存在几个换行符:" + (res.length - 1));
		StringBuffer sb = null;
		/** 如果存在多个换行符 */
		if (res.length > 2) {
			sb = new StringBuffer(50);
			for (int i = 0; i < res.length; i++) {
				sb.append(autoNewline(res[i]) + "\n");
			}
		} else {
			sb = new StringBuffer(autoNewline(str));
		}
		return sb.toString();
	}

	public static void otherBagShow(JPanel bag, List equipList,
			BagActionListener bgac) {
		Item item = null;
		JButton tempBu;
		JLabel tempField;
		if (item == null) {
			return;
		}
		/** 先设置第一个，再设置其他 */
		/*
		 * for (int i = 0; i < equipList.size(); i++) { item = (Item)
		 * equipList.get(i); System.out.println(item.toString()); tempBu = new
		 * JButton(item.getName());
		 *//** 设置组件边距 */
		/*
		 * // tempBu.setBorder(BorderFactory.createEtchedBorder());
		 * tempBu.setMargin(new Insets(0, 2, 0, 0));
		 * tempBu.setHorizontalAlignment(SwingConstants.LEFT);
		 *//** 设置按钮透明 */
		/*
		 * tempBu.setContentAreaFilled(false);
		 * tempBu.setForeground(Color.white); tempBu.addActionListener(bgac);
		 * tempBu.setFocusable(false); tempBu.setBounds(4, i * 20 + 1, 98, 20);
		 * bag.add(tempBu); for (int j = 1; j < 4; j++) { String tempStr =
		 * getShowField(i, i); tempField = new JLabel(tempStr);
		 * tempField.setForeground(Color.white);
		 * tempField.setHorizontalTextPosition(SwingConstants.CENTER);
		 * tempField.setBackground(Color.red); tempField.setBounds(6 + j *
		 * 60+38, i * 20 + 1, 60, 20); bag.add(tempField); } }
		 */
	}

	/**
	 * 把背包内装备物品显示在面板上，并增加监听
	 * 
	 * @param bag
	 * @param equipList
	 * @param bals
	 */
	public static void equipBagShow(JPanel bag, List equipList,
			BagActionListener bals) {
		Font f = new Font("隶书", Font.PLAIN, 16);
		Equip equip = null;
		JButton tempBu;
		JLabel tempField;
		if (equipList == null) {
			return;
		}
		System.out.println("背包里物品的数量:" + equipList.size());
		/** 先设置第一个，再设置其他 */
		for (int i = 0; i < equipList.size(); i++) {
			equip = (Equip) equipList.get(i);
			tempBu = new JButton(equip.getName());
			/** ActionCommand为在当前list中的序号 */
			tempBu.setActionCommand(i + "");
			/** 设置组件边距 */
			tempBu.setBorder(new EmptyBorder(0, 4, 0, 0));
			// tempBu.setMargin(new Insets(0, 2, 0, 0));
			tempBu.setHorizontalAlignment(SwingConstants.LEFT);
			/** 设置按钮透明 */
			tempBu.setFont(f);
			tempBu.setContentAreaFilled(false);
			/** 设置字体颜色 */
			tempBu.setForeground(Constant.equipColor[equip.getType()]);
			tempBu.addActionListener(bals);
			tempBu.setFocusable(false);
			tempBu.setBounds(0, i * 20 + 1, 98, 20);
			bag.add(tempBu);
			for (int j = 1; j < 4; j++) {
				String tempStr = getEquipField(j, equip);
				tempField = new JLabel(tempStr);
				tempField.setFont(f);
				tempField.setBorder(new EmptyBorder(0, 4, 0, 0));
				tempField.setForeground(new Color(250, 255, 240));
				tempField.setHorizontalTextPosition(SwingConstants.CENTER);
				tempField.setBounds(2 + j * 60 + 38, i * 20 + 1, 60, 20);
				bag.add(tempField);
			}
		}
	}

	/** 得到装备字段信息 */
	public static String getEquipField(int i, Equip e) {
		String temp = null;
		if (i == 1)
			temp = EquipControl.typeAry[e.getType()];
		else if (i == 2)
			temp = EquipControl.partDes[e.getPart()];
		else
			temp = e.getNum() + "";
		return temp;
	}
	
	/** 得到功法对应的字段 */
	public static String getGongField(int i,Gong gong){
		String text = null ;
		if(i == 1)
			text = gong.getNum()+"" ;
		else if(i == 2)
			text = gong.getQuality()+"" ;
		else if(i == 3){
			int rank = gong.getNeedRank() ;
			text = Constant.STATE[rank/10];
		}
		return text;
	}
	

	/**
	 * 自动设置组件居中
	 * 
	 * @param jf
	 */
	public static void setFrameCenter(Component jf) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();// 接数据对象
		int x = (screen.width - jf.getWidth()) / 2;
		int y = (screen.height - jf.getHeight()) / 2 - 32;
		jf.setLocation(x, y);
	}

	/**
	 * 设置JFrame居中
	 * 
	 * @param com
	 * @param jf
	 */
	public static void setPanelCenter(Component com, JFrame jf) {
		int x = (jf.getWidth() - com.getWidth()) / 2;
		int y = (jf.getHeight() - com.getHeight()) / 2;
		com.setLocation(x, y);
	}

	/**
	 * 字符串 转换为 数字
	 * 
	 * @param num
	 * @return
	 */
	public static int conStrtoInt(String num) {
		num = strTrim(num);
		return "".equals(num) ? 0 : Integer.valueOf(num.trim());
	}
	
	public static int conobjtoInt(Object num) {
		return "".equals(conObjToStr(num)) ? 0 : Integer.valueOf(conObjToStr(num));
	}

	/**
	 * Object 转化为 数字
	 * 
	 * @param obj
	 * @return
	 */
	public static int conObjtoInt(Object obj) {
		return obj == null ? 0 : Integer.valueOf(obj.toString());
	}

	/**
	 * 字符串 转化为 浮点类型
	 * 
	 * @param num
	 * @return
	 */
	public static double conStrtoDou(String num) {
		return isNullStr(num) ? 0 : Double.valueOf(num);
	}

	/**
	 * 将数据保留两位小数
	 * 
	 * @param num
	 * @return
	 */
	public static double getTwoDecimal(double num) {
		DecimalFormat dFormat = new DecimalFormat("#.00");
		String yearString = dFormat.format(num);
		Double temp = Double.valueOf(yearString);
		return temp;
	}

	/**
	 * 为容器设置带 标题 和 指定 字体的边框
	 * 
	 * @param c
	 * @param str
	 * @param f
	 */
	public static void setBorder(JComponent c, String str, Font f) {
		/** 样式 标题 位置 字体 边框颜色 */
		c.setBorder(new TitledBorder(BorderFactory.createMatteBorder(2, 2, 2,
				2, new Color(128, 29, 174)), str, TitledBorder.LEFT,
				TitledBorder.TOP, new Font("楷体", 0, 12), Color.BLUE));
	}

	/**
	 * 为容器设置带 标题 和 指定字体 和 颜色 的边框
	 * 
	 * @param c
	 * @param str
	 * @param color
	 * @param f
	 */
	public static void setBorder(JComponent c, String str, Color color, Font f) {
		TitledBorder border = BorderFactory.createTitledBorder(str);
		border.setTitleFont(f);
		border.setTitleColor(color);
		c.setBorder(border);
	}

	/**
	 * 先读取 xml 文件 要保存第几个fb
	 * 
	 * @param str
	 * @param index
	 * @param fb
	 */
	public static void editXML(String str, int index, Ditu fb) {

		Document document = load("src/game/xml/fuben.xml");
		/** 获取根目录 */
		Element root = document.getRootElement();
		List<Element> temp = root.elements();
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i).attribute("name").getText().equals("副本")) {
				temp = temp.get(i).elements();
				break;
			}
		}
		Element ele = temp.get(index);

		ele.element("name").setText(fb.getName());
		ele.element("des").setText(fb.getDes());
		ele.element("rankL").setText(fb.getRankL() + "");
		ele.element("rankR").setText(fb.getRankR() + "");
		Element e = ele.element("map");
		Element map = ele.addElement("map");
		Element point, name, des, x, y, npcStr;
		ele.remove(e);
		Scene sc = null;
		List<Scene> sceneList = fb.getScene() ;
		for (int j = 0; j < sceneList.size(); j++) {
			sc = sceneList.get(j);
			point = map.addElement("point");

			name = point.addElement("name");
			des = point.addElement("des");
			x = point.addElement("x");
			y = point.addElement("y");
			npcStr = point.addElement("npcStr");

			name.setText(sc.name);
			des.setText(sc.des);
			x.setText(sc.x + "");
			y.setText(sc.y + "");
			npcStr.setText(sc.npcStr);
		}
		XMLWriter writer = null;
		OutputFormat outFormat = new OutputFormat();
		// 设置换行 为false时输出的xml不分行
		outFormat.setNewlines(true);
		// 生成缩进
		outFormat.setIndent(true);
		// 指定使用tab键缩进
		outFormat.setIndent("  ");
		// 不在文件头生成 XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)
		outFormat.setSuppressDeclaration(true);
		// 不在文件头生成 XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)中加入encoding 属性
		outFormat.setOmitEncoding(true);
		outFormat.setEncoding("UTF-8");
		try {
			writer = new XMLWriter(new FileOutputStream("E:/1.xml"), outFormat);
			writer.write(document);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Object 转化为 String
	 * 
	 * @param obj
	 * @return
	 */
	public static String conObjToStr(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/**
	 * 计算 一次攻击 发生的实际情况 0 友方 1敌方 1暴击 2普通攻击 3miss
	 * 
	 * @param player
	 * @param npc
	 * @param type
	 * @return
	 */
	public static Map<String, Object> fightHelper(Player player, NPC npc,int type) {
		Map<String, Object> map = new HashMap<>();
		/** 此次攻击的攻击加成 两位小数 */
		double atkAdd = getTwoDecimal((rd.nextInt(5) + 7) * 0.1);
		/** value造成的伤害显示 atkType伤害的类型1：普攻 2：暴击 3:miss */
		String value = "";
		int atkType = 0;
		int atk = 0, def = 0, baoji = 0, atkDemage = 0;
		/** atkAdd 这次攻击的攻击加成 atkDemage这次攻击造成伤害数值 lv命中率 rankLv等级比 */
		double lv = 0.0, rankLv = 0.0;
		if (type == 0) {
			atk = player.getAttack();
			baoji = player.getBaoji();
			if(player.getRank()<6){
				rankLv = player.getRank() * 3.0 / (player.getRank() + npc.getRank());
			}else{
				rankLv = player.getRank() * 2.0 / (player.getRank() + npc.getRank());
			}
			def = npc.getDefense();
		} else {
			atk = npc.getAttack();
			baoji = npc.getBaoji();
			if(player.getRank()<6){
				rankLv = npc.getRank() * 2.0 / (player.getRank() * 2 + npc.getRank());
			}else{
				rankLv = npc.getRank() * 2.0 / (player.getRank() + npc.getRank());
			}
			def = player.getDefense();
		}
		/**
		 * 先判断是否暴击 暴击双倍伤害
		 * 
		 */
		int rdValue = rd.nextInt(100) + 1;
		if (rdValue < baoji) {
			atkDemage = (int) ((atkAdd * 2) * atk);
			value = conObjToStr(atkDemage);
			atkType = 2;
		} else {
			/** 判断是否普攻击 */
			lv = getTwoDecimal(atk * 100 / (atk + def));
			lv = lv * rankLv;
			rdValue = rd.nextInt(100) + 1;
			// System.out.println("没有打出暴击,判断是否命中,随机数为:"+rdValue+",命中率为:"+lv);
			if (rdValue < lv) {
				atkAdd = (rd.nextInt(5) + 7) * 0.1;
				atkDemage = (int) (atkAdd * atk);
				value = conObjToStr(atkDemage);
				// System.out.println("耶,打中了,基数为"+atkAdd+",伤害值是:"+value);
				atkType = 1;
			} else {
				atkType = 3;
			}
		}
		/** 将战斗信息写入日志 */
		if(type==0){
			SUtils.writeFtLog("我方进行攻击,我攻击力为:"+atk+",我的暴击率为:"+baoji+",敌人防御力为:"+def+",此次攻击加成为:"+atkAdd+"\n");
		}else{
			SUtils.writeFtLog("敌方进行攻击,敌方攻击力为:"+atk+",敌方的暴击率为:"+baoji+",我的防御力为:"+def+",此次攻击加成为:"+atkAdd+"\n");
		}
		SUtils.writeFtLog("此次攻击命中率为:"+lv+",此次攻击判定数数值为:"+rdValue+"\n");
		
		map.put("atkType", atkType);
		map.put("value", value);
		map.put("atkDemage", atkDemage);
		return map;
	}

	/**
	 * 是当前线程睡眠 time 毫秒
	 * 
	 * @param time
	 */
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存档转化成玩家实体类
	 */
	public static Player conArcToPlayer(Archive archive) {
		if (archive == null) {
			return new Player();
		}
		Player player = new Player();
		player.setName(archive.getName());
		player.setRank(archive.getRank());
		player.setExp(archive.getExp());
		player.setCurExp(archive.getCurExp());

		player.setTili(archive.getTili());
		player.setJingli(archive.getJingli());
		player.setLi(archive.getLi());
		player.setMin(archive.getMin());
		player.setLucky(archive.getLucky());

		player.setHp(archive.getHp());
		player.setCurHp(player.getHp());
		player.setMp(archive.getMp());
		player.setCurMp(player.getMp());
		player.setAttack(archive.getAttack());
		player.setDefense(archive.getDefense());

		player.setBaoji(archive.getBaoji());
		player.setSuck(archive.getSuck());
		player.setExpAdd(archive.getExpAdd());
		player.setMoneyAdd(archive.getMoneyAdd());
		player.setPetAdd(archive.getPetAdd());
		player.setBaolv(archive.getBaolv());

		player.setEquipBag(archive.getEquipBag());
		player.setEquipAry(archive.getEquipAry());
		player.setGongBag(archive.getGongBag());
		
		return player;
	}

	/**
	 * 玩家实体类转化为存档
	 */
	public static Archive conPlayerToArc(Player player) {

		Archive archive = GameControl.getInstance().getArchive();
		if (archive == null) {
			return null;
		}
		archive.setName(player.getName());
		archive.setRank(player.getRank());
		archive.setExp(player.getExp());
		archive.setCurExp(player.getCurExp());

		archive.setTili(player.getTili());
		archive.setJingli(player.getJingli());
		archive.setLi(player.getLi());
		archive.setMin(player.getMin());
		archive.setLucky(player.getLucky());

		archive.setHp(player.getHp());
		archive.setMp(player.getMp());
		archive.setAttack(player.getAttack());
		archive.setDefense(player.getDefense());
		archive.setBaoji(player.getBaoji());
		archive.setMp(player.getMp());

		archive.setBaoji(player.getBaoji());
		archive.setSuck(player.getSuck());
		archive.setExpAdd(player.getExpAdd());
		archive.setMoneyAdd(player.getMoneyAdd());
		archive.setPetAdd(player.getPetAdd());
		archive.setBaolv(player.getBaolv());

		archive.setEquipAry(player.getEquipAry());
		archive.setEquipBag(player.getEquipBag());
		archive.setGongBag(player.getGongBag());
		System.out.println("背包功法书数量:"+player.getGongBag().size());
		return archive;
	}

	/**
	 * 把毫秒变为一定格式的时间
	 * 
	 * @param millSec
	 * @return
	 */
	public static String transferLongToDate(Long millSec) {
		String dateFormat = "MM月dd日  HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}

	/**
	 * 去掉浮点类型小数点，四舍五入
	 * 
	 * @param str
	 *            传入String
	 * @return 返回String
	 */
	public static String reDouPointStr(String str) {
		return formatDouble(conStrtoDou(str), 0);
	}

	/**
	 * 去掉浮点类型小数点，四舍五入
	 * 
	 * @param str
	 *            传入String
	 * @return 返回int
	 */
	public static int reDouPoint(double dou) {
		String num = formatDouble(dou, 0);
		return conStrtoInt(num);
	}

	/**
	 * 格式化浮点值为字符串型, 指定小数位数长度。
	 * 
	 * @param data
	 *            - 要转换的浮点数
	 * @param len
	 *            - 保留小数位数
	 * @return String - 返回转换后的数字字符串
	 */
	public static String formatDouble(double data, int len) {
		String ret = null;
		try {
			NumberFormat form = NumberFormat.getInstance();
			String mask = "###0";
			if (len > 0) {
				mask = "###0.";
				for (int i = 0; i < len; i++) {
					mask = mask + "0";
				}
			}
			((DecimalFormat) form).applyPattern(mask);
			ret = form.format(data);
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	public static void showThread() {
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int noThreads = currentGroup.activeCount();
		Thread[] lstThreads = new Thread[noThreads];
		currentGroup.enumerate(lstThreads);
		for (int i = 0; i < noThreads; i++)
			System.out.println("Thread No:" + i + " = "
					+ lstThreads[i].getName());
	}

	/**
	 * 将文件写入日志
	 * 
	 * @param str
	 */
	public static void writeFtLog(String str) {
		//暂时关闭
		/*try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件,如果为
			// true，则将字节写入文件末尾处，而不是写入文件开始处
			FileWriter writer = new FileWriter("src/game/log/ftlog.txt", true);
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	/** 测试 */
	public static void main(String[] args) {
		/*SUtils s = new SUtils();
		// SUtils.loadWeapon();
		Document document = SUtils.load("src/game/xml/npc.xml");
		Node node = document.selectSingleNode("/root/npc[id='1003']/action");
		Element e = node.getParent().element("action");
		System.out.println(e);
		System.out.println(node.asXML());*/
		
		/*Map<String, Gong> map = SUtils.loadGong();
		System.out.println(map.get("2001").getName());
		System.out.println(map.get("2001").getTiers().get(4).addAttrs);*/
		//s.writeFtLog("日志写入测试！");
		//s.writeFtLog("写入内容");
		
		new SUtils().getProjectPath() ;
	}
	
	public static void playMusic(){
		try {
			String gongFile ="/ Users / al / DevDaily / Project / MeditationApp / resources / gong.au";
		    InputStream in = new FileInputStream(gongFile);
		    AudioStream as = new AudioStream(in); 
//		    AudioClip ac = getAudioClip(getCodeBase(), soundFile);
			}
			catch (Exception e) {}
	}
	
	/**
	 * 得到不同等级之下人物的属性
	 * @param attrName 属性名
	 * @param rank 任务等级
	 * @return
	 */
	public static int getBaseAttrByRank(String attrName,int rank){
		int value = 0 ;
		Player player = new Player() ;
		player.setRank(rank);
		player.reloadBaseAttr();
		switch (attrName) {
		case "hp":
			value = player.getHp() ;
			break;
		case "mp":
			value = player.getMp() ;
			break;
		case "atk":
			value = player.getAttack() ;
			break;
		case "def":
			value = player.getDefense() ;
			break;
		case "baoji":
			value = player.getBaoji() ;
			break;
		case "speed":
			value = player.getSpeed() ;
			break;
		default:
			break;
		}
		return value ;
	}
	
	public static void saveGongInfo(List<Gong> gongList){
		Document document = load("src/game/xml/Gong.xml");
		/** 获取根目录 */
		Element root = document.getRootElement();
		List<Element> temp = root.elements();
		Element target = null ;
		Gong gongTmp = null ;
		for (int i = 0; i < temp.size(); i++) {
			target = temp.get(i) ;
			for (int j = 0; j < gongList.size(); j++) {
				System.out.println("id:"+gongList.get(j).getId());
				if(gongList.get(j).getId().equals(target.attributeValue("id"))){
					gongTmp = gongList.get(j) ;
					gongList.remove(j);
				}
			}
			target.element("name").setText(gongTmp.getName());
			target.element("quality").setText(gongTmp.getQuality()+"");
			target.element("des").setText(gongTmp.getDes());
			target.element("req").setText(gongTmp.getRequire()+"");
			target.element("needRank").setText(gongTmp.getNeedRank()+"");
			target.element("maxTier").setText(gongTmp.getMaxTier()+"");
			target.element("curTier").setText(gongTmp.getCurTier()+"");
			target.element("type").setText(gongTmp.getType()+"");
		}
		for (int i = 0; i < gongList.size(); i++) {
			Element newEle = root.addElement("gong");
			newEle.addAttribute("id", gongList.get(i).getId());
			newEle.addElement("name").setText(gongList.get(i).getName());
			newEle.addElement("quality").setText(gongList.get(i).getQuality()+"");
			newEle.addElement("des").setText(gongList.get(i).getDes());
			newEle.addElement("req").setText(gongTmp.getRequire()+"");
			newEle.addElement("needRank").setText(gongTmp.getNeedRank()+"");
			newEle.addElement("maxTier").setText(gongList.get(i).getMaxTier()+"");
			newEle.addElement("curTier").setText(gongList.get(i).getCurTier()+"");
			newEle.addElement("type").setText(gongList.get(i).getType()+"");
			newEle.addElement("effect");
		}
		XMLWriter writer = null;
		OutputFormat outFormat = OutputFormat.createPrettyPrint();
		// 设置换行 为false时输出的xml不分行
		outFormat.setNewlines(true);
		// 生成缩进
		outFormat.setIndent(false);
		// 指定使用tab键缩进
		outFormat.setIndent("  ");
		// 不在文件头生成 XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)
		outFormat.setSuppressDeclaration(true);
		// 不在文件头生成 XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)中加入encoding 属性
		outFormat.setOmitEncoding(true);
		outFormat.setEncoding("UTF-8");
		try {
			writer = new XMLWriter(new FileOutputStream("src/game/xml/Gong.xml"), outFormat);
			System.err.println(writer);
			writer.write(document);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 保存技能信息
	 * id 不为空
	 * name 不为空
	 * des 不为空
	 * 类型 不为空 1主动 2被动
	 * 消耗 默认为0
	 * 结果 不能为空 
	 * 目标 默认敌方
	 * 范围 默认单体
	 * 功法id 默认为2000
	 * 功法name 默认为暂无
	 * 使用条件 默认无
	 * 学习条件 默认无
	 * 需要等级 默认0
	 * cd 默认0
	 * curCd 默认0
	 * @param skillList
	 */
	public static void saveSkillInfo(List<Skill> skillList){
		Document document = load("src/game/xml/skill.xml");
		/** 获取根目录 */
		Element root = document.getRootElement();
		List<Element> temp = root.elements("skill");
		Element target = null ;
		Skill skillTmp = null ;
		for (int i = 0; i < temp.size(); i++) {
			target = temp.get(i) ;
			for (int j = 0; j < skillList.size(); j++) {
				System.out.println("id:"+skillList.get(j).id+","+target.attributeValue("id"));
				if(skillList.get(j).id.equals(target.attributeValue("id"))){
					skillTmp = skillList.get(j) ;
					skillList.remove(j);
				}
			}
			target.element("name").setText(strTrim(skillTmp.name));
			target.element("des").setText(strTrim(skillTmp.des));
			target.element("type").setText(strTrim(strTrim(skillTmp.type)));
			target.element("consume").setText(strTrim(skillTmp.consume));
			target.element("result").setText(strTrim(skillTmp.result));
			target.element("target").setText(strTrim(skillTmp.target));
			target.element("scope").setText(strTrim(skillTmp.scope));
			target.element("gongId").setText(strTrim(skillTmp.gongId));
			target.element("gongName").setText(strTrim(skillTmp.gongName));
			target.element("useCase").setText(strTrim(skillTmp.useCase));
			target.element("studyCase").setText(strTrim(skillTmp.studyCase));
			target.element("needTier").setText(strTrim(skillTmp.needTier+""));
			target.element("cd").setText(strTrim(skillTmp.cd+""));
			target.element("curCd").setText(strTrim("0"));
		}
		System.out.println("新增了"+skillList.size()+"条!");
		for (int i = 0; i < skillList.size(); i++) {
			Element newEle = root.addElement("skill");
			newEle.addAttribute("id", skillList.get(i).id);
			System.out.println(skillList.get(i).name);
			newEle.addElement("name").setText(skillList.get(i).name);
			newEle.addElement("des").setText(skillList.get(i).des);
			newEle.addElement("type").setText(skillList.get(i).type);
			newEle.addElement("consume").setText(skillList.get(i).consume);
			newEle.addElement("result").setText(skillList.get(i).result);
			newEle.addElement("newEle").setText(skillList.get(i).target);
			newEle.addElement("scope").setText(skillList.get(i).scope);
			newEle.addElement("target").setText(skillList.get(i).target);
			newEle.addElement("gongId").setText(skillList.get(i).gongId);
			newEle.addElement("gongName").setText(skillList.get(i).gongName);
			newEle.addElement("useCase").setText(skillList.get(i).useCase);
			newEle.addElement("studyCase").setText(skillList.get(i).studyCase);
			newEle.addElement("needTier").setText(skillList.get(i).needTier+"");
			newEle.addElement("cd").setText(skillList.get(i).cd+"");
			newEle.addElement("curCd").setText("0");
		}
		XMLWriter writer = null;
		OutputFormat outFormat = OutputFormat.createPrettyPrint();
		// 设置换行 为false时输出的xml不分行
		outFormat.setNewlines(true);
		// 生成缩进
		outFormat.setIndent(false);
		// 指定使用tab键缩进
		outFormat.setIndent("  ");
		// 不在文件头生成 XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)
		outFormat.setSuppressDeclaration(true);
		// 不在文件头生成 XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)中加入encoding 属性
		outFormat.setOmitEncoding(true);
		outFormat.setEncoding("UTF-8");
		try {
			writer = new XMLWriter(new FileOutputStream("src/game/xml/Skill.xml"), outFormat);
			System.err.println(writer);
			writer.write(document);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param id 功法id
	 * @param tierList 功法每层信息
	 */
	public static void saveGongTier(String id,List<Tier> tierList){
		System.out.println("开始保存id:"+id+"的功法信息!");
		Document document = load("src/game/xml/Gong.xml");
		/** 获取根目录 */
		Element root = document.getRootElement();
		List<Element> temp = root.elements();
		Element target = null ;
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i).attribute("id").getText().equals(id)) {
				target = temp.get(i) ;
				break;
			}
		}
		if(target==null){
			return ;
		}
		target = target.element("effect");
		/** 移除所有的 */
		List<Element> tierL = target.elements() ;
		for (int i = 0; i < tierL.size(); i++) {
			target.remove(tierL.get(i));
		}
		/** 根据提交内容新增 */
		for (int i = 0; i < tierList.size(); i++) {
			Element tier = target.addElement("tier");
			tier.addAttribute("value", tierList.get(i).curTier+"");
			Element needRank = tier.addElement("needRank");
			needRank.setText(tierList.get(i).needRank+"");
			Element needExp = tier.addElement("needExp");
			needExp.setText(tierList.get(i).needExp+"");
			Element addAttrs = tier.addElement("AddAttrs");
			addAttrs.setText(tierList.get(i).addAttrStr.trim());
		}
		XMLWriter writer = null;
		OutputFormat outFormat = OutputFormat.createPrettyPrint();
		// 设置换行 为false时输出的xml不分行
		outFormat.setNewlines(true);
		// 生成缩进
		outFormat.setIndent(false);
		// 指定使用tab键缩进
		outFormat.setIndent("  ");
		// 不在文件头生成 XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)
		outFormat.setSuppressDeclaration(true);
		// 不在文件头生成 XML 声明 (<?xml version="1.0" encoding="UTF-8"?>)中加入encoding 属性
		outFormat.setOmitEncoding(true);
		outFormat.setEncoding("UTF-8");
		try {
			writer = new XMLWriter(new FileOutputStream("src/game/xml/Gong.xml"), outFormat);
			System.err.println(writer);
			writer.write(document);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static int conGongQua(String qua){
		if(qua.equals("天品")){
			return 0 ;
		}else if(qua.equals("天品")){
			return 1 ;
		}else if(qua.equals("天品")){
			return 2 ;
		}else if(qua.equals("天品")){
			return 3 ;
		}
		return 0;
	}
	
	public String getProjectPath(){
		// 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);

        // 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录  D:\git\daotie\daotie\target\classes\my
        File f2 = new File(this.getClass().getResource("").getPath());
        System.out.println(f2);

        // 第二种：获取项目路径    D:\git\daotie\daotie
        File directory = new File("");// 参数为空
        String courseFile = null;
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(courseFile);


        // 第三种：  file:/D:/git/daotie/daotie/target/classes/
        URL xmlpath = this.getClass().getClassLoader().getResource("");
        System.out.println(xmlpath);


        // 第四种： D:\git\daotie\daotie
        System.out.println(System.getProperty("user.dir"));
        /*
         * 结果： C:\Documents and Settings\Administrator\workspace\projectName
         * 获取当前工程路径
         */

        // 第五种：  获取所有的类路径 包括jar包的路径
        System.out.println(System.getProperty("java.class.path"));
        return "" ;
	}
	
	/**
	 * 基数 1000
	 * @param needRank 10
	 * @param qua 3
	 * @return
	 */
	public static int getGongPrice(int needRank, int qua){
		int price = 1000 ;
		int state  = needRank%10 ;
		for (int i = 0; i < state; i++) {
			price *= 10 ; 
		}
		for (int i = 0; i < qua; i++) {
			price *= 3 ;
		}
		return price ;
	}
	
	public static void setUi(JTabbedPane jtp) {
		jtp.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI() {
			@Override
			protected void paintTopTabBorder(int tabIndex, Graphics g, int x,
					int y, int w, int h, int btm, int rght, boolean isSelected) {
				     g.drawLine(x, y, x+w-2, y);
				    // g.setColor(MetalLookAndFeel.getWhite());
				   //  g.drawLine(x, y + 2, x+w-2, y + 2);
			};
			@Override
			protected void paintTabBorder(Graphics g, int tabPlacement,
					int tabIndex, int x, int y, int w, int h, boolean isSelected) {
				int bottom = y + (h-1);
		        int right = x + (w-1);
		        g.setColor(Color.orange);
		        paintTopTabBorder(tabIndex, g, x, y, w, h, bottom, right, isSelected);
			}
			@Override
			protected void paintLeftTabBorder(int tabIndex, Graphics g, int x,
					int y, int w, int h, int btm, int rght, boolean isSelected) {
			};
			@Override
			protected void paintRightTabBorder(int tabIndex, Graphics g, int x,
					int y, int w, int h, int btm, int rght, boolean isSelected) {
			};
			@Override
			protected void paintContentBorderLeftEdge(Graphics g,
					int tabPlacement, int selectedIndex, int x, int y, int w,
					int h) {
			}
			@Override
			protected void paintContentBorderRightEdge(Graphics g,
					int tabPlacement, int selectedIndex, int x, int y, int w,
					int h) {
			}
			
		});
	}
	
	public static void setEmptyBorder(JComponent c){
		c.setBorder(new EmptyBorder(0, 0, 0, 0));
	}
	
}
