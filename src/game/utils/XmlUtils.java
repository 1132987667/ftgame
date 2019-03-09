package game.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import game.control.EquipControl;
import game.control.GameControl;
import game.control.GongCtrl;
import game.control.TaskCtrl;
import game.entity.AddAttrs;
import game.entity.CitiaoSD;
import game.entity.Ditu;
import game.entity.Equip;
import game.entity.Gong;
import game.entity.NPC;
import game.entity.Skill;
import game.entity.Tasks;
import game.entity.Tier;

public class XmlUtils {
	
	private DataCal dataCal ;
	{
		dataCal = new DataCal();
	}
	
	/** 功，技能，装备，人物，人物，副本，装备设定，场景 */
	
	
	
	
	
	
	
	
	
	
	
	
	/***
	 * 返回xml的路径
	 * @param type
	 * @return 路径
	 */
	public String getXmlPath(int type) {
		String path = "game/xml/";
		switch (type) {
		case C.xml_Gong:
			path += "Gong";
			break;
		case C.xml_Skill:
			path += "skill";
			break;
		case C.xml_Equip:
			path += "equip";
			break;
		case C.xml_Npc:
			path += "npc";
			break;
		case C.xml_Task:
			path += "task";
			break;
		case C.xml_Fuben:
			path += "fuben";
			break;
		case C.xml_EquipSet:
			path += "equipSetting";
			break;
		case C.xml_World:
			path += "world" ;
			break;
		default:
			break;
		}
		path += ".xml";
		return path;
	}
	
	/**
	 * 加载xml文件
	 * @param filename
	 * @return
	 */
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");
			// 读取XML文件,获得document对象
			document = saxReader.read(SUtils.class.getResourceAsStream("/" + filename)); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	
	/***
	 * 通过类型读取不同xml文档节点
	 * @param type
	 * @return
	 */
	public Document getXmlDoc(int type) {
		Document doc = null;
		doc = load(getXmlPath(type));
		return doc;
	}
	
	/**
	 * 加载所有的装备信息 武器，防具
	 * @return
	 */
	public Map<String, Equip> loadEquip() {
		System.err.println("--加载装备xml文件!1");
		Map<String, Equip> equipMap = new HashMap<>();
		Document document = getXmlDoc(C.xml_Equip);
		/** 迭代获得所有元素 */
		/** 获取根目录 */
		Element root = document.getRootElement();
		/** 获取根目录下所有元素 */
		List<Element> list = root.elements();
		int part = 0, rank = 0;
		String mode, name, kind, des, id;
		Equip temp = null;
		for (Element e : list) {
			// 获取属性值
			part = SUtils.conStrtoInt(e.attributeValue("part"));
			mode = e.attributeValue("mode");
			id = e.element("id").getText();
			;
			name = e.element("name").getText();
			kind = e.element("kind").getText();
			des = e.element("des").getText();
			rank = SUtils.conStrtoInt(e.element("rank").getText());
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
	
	/**
	 * 加载对应装备的属性生成设定
	 * @param part
	 * @return
	 */
	public List<CitiaoSD> loadEquipSetting(int part) {
		// System.err.println("--加载装备设定xml文件!");
		/** 对应部位的设定列表 */
		List<CitiaoSD> list = new ArrayList<>();
		Document doc = getXmlDoc(C.xml_EquipSet);
		Element targetEle = doc.selectSingleNode("/root/equip[@name='" + EquipControl.partAry[part] + "']/attr")
				.getParent();
		List<Element> temp = targetEle.elements();
		/** 5条设定 每条设定六个属性 */
		for (int i = 0; i < temp.size(); i++) {
			String type = temp.get(i).element("type").getText();
			String attrType = temp.get(i).element("attrType").getText();
			String attrName = temp.get(i).element("attrName").getText();
			String ratio = temp.get(i).element("value").getText();
			CitiaoSD theAttr = new CitiaoSD(part, i, SUtils.conStrtoInt(type), attrType, attrName, SUtils.conStrtoDou(ratio));
			list.add(theAttr);
		}
		return list;
	}
	
	/**
	 * 加载技能列表
	 * 
	 * @return 返回带有所有技能信息的map
	 */
	public Map<String, Skill> loadSkill() {
		System.err.println("--加载技能xml文件!");
		Document document = getXmlDoc(C.xml_Skill);
		Element root = document.getRootElement();
		List<Element> eleList = root.elements("Skill");
		System.err.println("加载了" + eleList.size() + "个技能");
		Skill skillTmp = null;
		Map<String, Skill> skillMap = new HashMap<>();
		for (int i = 0; i < eleList.size(); i++) {
			Element ele = eleList.get(i);
			skillTmp = new Skill();
			/** 必填 */
			skillTmp.id = SUtils.strTrim(ele.attributeValue("id"));
			skillTmp.name = SUtils.strTrim(ele.elementText("name"));
			skillTmp.qua = SUtils.strTrim(ele.elementText("qua"));
			skillTmp.des = SUtils.strTrim(ele.elementText("des"));
			skillTmp.type = SUtils.strTrim(ele.elementText("type"));
			skillTmp.consume = SUtils.strTrim(ele.elementText("consume"));
			skillTmp.scope = SUtils.strTrim(ele.elementText("scope"));
			skillTmp.skillMaxRank = SUtils.conStrtoInt(ele.elementText("maxRank"));
			skillTmp.baseDamageRatio = SUtils.conStrToDou(ele.elementText("damageRatio"));
			skillTmp.effectStr = SUtils.strTrim(ele.elementText("effectStr"));

			/** 选填 */
			if (ele.selectNodes("gongId").size() > 0) {
				skillTmp.gongId = SUtils.strTrim(ele.elementText("gongId"));
			}
			if (ele.selectNodes("gongName").size() > 0) {
				skillTmp.gongName = SUtils.strTrim(ele.elementText("gongName"));
			}
			if (ele.selectNodes("useCase").size() > 0) {
				skillTmp.useCase = SUtils.strTrim(ele.elementText("useCase"));
			}
			if (ele.selectNodes("studyCase").size() > 0) {
				skillTmp.studyCase = SUtils.strTrim(ele.elementText("studyCase"));
			}
			if (ele.selectNodes("needTier").size() > 0) {
				skillTmp.needTier = SUtils.conStrtoInt(ele.elementText("needTier"));
			}
			if (ele.selectNodes("cd").size() > 0) {
				skillTmp.cd = SUtils.conStrtoInt(ele.elementText("cd"));
			}
			if (ele.selectNodes("curCd").size() > 0) {
				skillTmp.curCd = SUtils.conStrtoInt(ele.elementText("curCd"));
			}
			skillMap.put(skillTmp.id, skillTmp);
		}
		return skillMap;
	}
	
	/**
	 * 加载任务信息
	 * 
	 * @return 返回所有任务信息的map
	 */
	public Map<String, Tasks> loadTask() {
		System.err.println("--加载任务xml文件!");
		Map<String, Tasks> map = new HashMap<>();
		Document document = getXmlDoc(C.xml_Task);
		Element root = document.getRootElement();
		List<Element> eleList = root.elements("task");
		System.err.println("task.xml任务数量" + eleList.size());
		Tasks task = null;
		for (int i = 0; i < eleList.size(); i++) {
			String id = eleList.get(i).attributeValue("id");
			Element e = eleList.get(i);
			String taskName = e.element("taskName").getText();
			int curState = SUtils.conStrtoInt(eleList.get(i).element("curState").getText());
			String npcId = e.element("npcId").getText();
			String taskDes = e.element("taskDes").getText();

			String startMsg = e.element("startMsg").getText();
			String undoMsg = "";
			if (e.selectNodes("undoMsg").size() > 0) {
				undoMsg = e.element("undoMsg").getText();
			}
			String acceptMsg = e.element("acceptMsg").getText();
			String endMsg = e.element("acceptMsg").getText();

			String startCond = e.element("startCond").getText();
			String acceptCond = e.element("acceptCond").getText();
			String endCond = e.element("endCond").getText();
			String type = TaskCtrl.reset_task;
			if (e.selectNodes("type").size() > 0) {
				type = e.element("type").getText();
			}
			task = new Tasks();
			task.setId(id);
			task.setTaskName(taskName);
			task.setNpcId(npcId);
			task.setTaskDes(taskDes);
			task.setType(type);

			task.setCurState(curState);

			task.setStartMsg(startMsg);
			task.setUndoMsg(undoMsg);
			task.setAcceptMsg(acceptMsg);
			task.setEndMsg(endMsg);

			task.setStartCond(startCond);
			task.setAcceptCond(acceptCond);
			task.setEndCond(endCond);
			map.put(id, task);
		}
		return map;
	}
	
	/**
	 * 加载npc的基本信息 id , name , des , msg , rank , type 而人物的属性 和与玩家的交互在之后解析
	 * 
	 * @return
	 */
	public Map<String, NPC> loadNpc() {
		System.err.println("--加载人物xml文件!");
		Map<String, NPC> map = new HashMap<>();
		Document document = getXmlDoc(C.xml_Npc);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		Element npcEle = null;
		NPC npc = null;
		/** 开始初始化每个npc的信息 */
		for (int i = 0; i < elementList.size(); i++) {
			/** 循环每个npc节点 */
			npcEle = elementList.get(i);
			String id = npcEle.elementText("id");
			if (!SUtils.isNullStr(id)) {// 不为空则加入
				String name = npcEle.elementText("name");
				String des = npcEle.elementText("des");
				String msg = npcEle.elementText("msg").trim();
				String[] msgs = msg.split("\\|");
				int rank = SUtils.conStrtoInt(npcEle.element("rank").getText());
				int type = SUtils.conStrtoInt(npcEle.element("type").getText());
				String attrType = npcEle.attributeValue("attrType");
				if (npcEle.selectNodes("NeiGong").size() > 0) {
					String NGStr = npcEle.elementText("NeiGong").trim();
					npc.setNgStr(NGStr);
				}
				if (npcEle.selectNodes("WaiGong").size() > 0) {
					String WGStr = npcEle.elementText("WaiGong").trim();
					npc.setWgStr(WGStr);
				}
				npc = new NPC();
				npc.setMsgs(msgs);
				npc.setId(id);
				npc.setName(name);
				npc.setDes(des);
				npc.setMsg(msg);
				npc.setRank(rank);
				npc.setType(type);
				npc.setAttrType(attrType);

				map.put(id, npc);
			}
		}
		return map;
	}

	/**
	 * 加载功法
	 * 
	 * @return
	 */
	public Map<String, Gong> loadGong() {
		System.err.println("--加载功法xml文件 开始!");
		Map<String, Gong> map = new HashMap<>();
		Document document = getXmlDoc(C.xml_Gong);
		Element root = document.getRootElement();
		List<Element> eleList = root.elements();
		Gong gong = null;
		/** 功的属性 */
		GongCtrl gongCtrl = new GongCtrl();
		String id, name, des, req, effectStr = "";
		int quality, maxTier, curTier, type, needRank;
		Tier tier;
		for (int i = 0; i < eleList.size(); i++) {
			Element ele = eleList.get(i);
			gong = new Gong();
			id = ele.attributeValue("id");
			name = ele.elementText("name");
			des = ele.elementText("des");
			gong.setId(id);
			gong.setName(name);
			gong.setDes(des);
			gong.setPrice(100);
			gong.setWeight(SUtils.conStrtoInt(ele.elementText("weight")));
			if (SUtils.isExistEle(ele, "type")) {
				type = SUtils.conStrtoInt(ele.elementText("type"));
				gong.setType(type);
			}
			if (SUtils.isExistEle(ele, "maxTier")) {
				maxTier = SUtils.conStrtoInt(ele.elementText("maxTier"));
				gong.setMaxTier(maxTier);
			}
			if (SUtils.isExistEle(ele, "curTier")) {
				curTier = SUtils.conStrtoInt(ele.elementText("curTier"));
				gong.setCurTier(curTier);
			}
			if (SUtils.isExistEle(ele, "quality")) {
				quality = SUtils.conStrtoInt(ele.elementText("quality"));
				gong.setQua(quality);
			}
			if (SUtils.isExistEle(ele, "needRank")) {
				needRank = SUtils.conStrtoInt(ele.elementText("needRank"));
				gong.setNeedRank(needRank);
			}
			if (ele.selectNodes("effectStr").size() > 0) {
				effectStr = ele.elementText("effectStr");
			}
			if (ele.selectNodes("req").size() > 0) {
				req = ele.elementText("req");
				gong.setRequire(req);
			}
			/** 存在技能设定 */
			if (ele.selectNodes("skills").size() > 0) {
				List<Element> skillEle = ele.element("skills").elements("skill");
				for (int j = 0; j < skillEle.size(); j++) {
					Element e = skillEle.get(j);
					Skill skill = new Skill();
					skill.id = e.attributeValue("id");
					skill.name = e.attributeValue("name");
					skill.needTier = SUtils.conStrtoInt(e.attributeValue("needTier"));
					gong.getAllSkills().add(skill);
				}
			}
			if (SUtils.isExistEle(ele, "baseEff") && !SUtils.isNullStr(ele.elementText("baseEff"))) {
				String baseEffStr = ele.elementText("baseEff").trim();
				//System.out.println(baseEffStr);
				String[] baseEffAry = baseEffStr.split(";");
				AddAttrs addAttrs = new AddAttrs();
				for (int j = 0; j < baseEffAry.length; j++) {
					String[] key = baseEffAry[j].split(":");
					//System.out.println(baseEffAry[j]+","+key[1]+","+addAttrs+","+gong+","+key[0]+","+dataCal);
					addAttrs = dataCal.accGongAddAttrs(addAttrs, key[0], gong, SUtils.conStrtoInt(key[1]));
				}
				//System.out.println("功法加成:" + addAttrs.info());
				gong.setAddAttrs(addAttrs);
			}
			gong.setEffectStr(effectStr);
			/*
			 * for (int j = 0; j < maxTier; j++) { tier = new Tier() ; tier.curTier = j ;
			 * tier.needRank = needRank + curTier*2 - 2 ; if(effectStr.length()>0) {
			 * tier.addAttrs = gongCtrl.getGongEffect(effectStr, j, needRank, type); }
			 * gong.getTiers().add(tier); }
			 */
			/** 加载功法每一层的信息 */
			/*
			 * Element effect = eleList.get(i).element("effect"); tierList =
			 * effect.elements();
			 */
			/**
			 * <tier value="4"> <needRank>7</needRank> <AddAttrs> hp:60%; mp:60% </AddAttrs>
			 * </tier>
			 */
			/*
			 * for (int j = 0; j < tierList.size(); j++) { tier = new Tier() ; tier.curTier
			 * = conStrtoInt(tierList.get(j).attributeValue("value")); tier.needRank =
			 * conStrtoInt(tierList.get(j).elementText("needRank")); tier.needExp =
			 * conObjToStr(tierList.get(j).elementText("needExp")); addAttrs = new
			 * AddAttrs(); String addAttrsValue = tierList.get(j).elementText("AddAttrs");
			 * String[] ary = addAttrsValue.trim().split(";"); String addAttrStr = "" ;
			 *//**
				 * hp:60%; mp:60%
				 */
			/*
			 * for (int k = 0; k < ary.length; k++) { addAttrStr+=ary[k].trim()+";";
			 *//** hp:60% */
			/*
			 * String[] temp = ary[k].split(":");
			 *//** hp */
			/*
			 * String attrName = temp[0].trim();
			 *//** 60% */
			/*
			 * String value = temp[1].trim(); int attrValue = 0 ; if(value.endsWith("%")){
			 *//** 表示为百分比加成 *//*
								 * attrValue = getBaseAttrByRank(name, tier.needRank); value =
								 * value.substring(0,value.length()-1); attrValue =
								 * reDouPoint(attrValue*conStrtoInt(value)*0.01); }else { attrValue =
								 * conStrtoInt(value); } addAttrs = calAddAttrsValue(addAttrs,attrName,
								 * attrValue); } tier.addAttrStr = addAttrStr ; tier.addAttrs = addAttrs ;
								 * gong.getTiers().add(tier); }
								 */
			map.put(id, gong);
		}
		System.err.println("--加载功法xml文件 结束! 一共"+ map.size() + "部功法!");
		return map;
	}
	
	
	/**
	 * 加载剧情xml
	 * @return
	 */
	public List<Ditu> loadJuqing() {
		Ditu ditu = null ;
		Document document = getXmlDoc(C.xml_World);
		Element root = document.getRootElement();
		List<Element> temp  = root.elements();
		List<Ditu> list = new ArrayList<>();
		String jqId, jqName, jqDes = null ;
		for (int i = 0; i < temp.size(); i++) {
			jqId = temp.get(i).attribute("id").getText() ;
			jqName = temp.get(i).elementText("name");
			jqDes = temp.get(i).elementText("des");
			ditu = new Ditu(jqId, jqName, jqDes, 0, 0);
			list.add(ditu);
		}
		return list;
	}
	
	/**
	 * 加载当前选择的剧情
	 * @param jqID
	 * @return 返回地图实体类
	 */
	public Ditu loadScene(String ID){
		Ditu ditu = null ;
		Document doc = getXmlDoc(C.xml_World);
		Node gongNode = doc.selectSingleNode("/root/place[@id='"+ID.trim()+"']/name");
		Element cur = gongNode.getParent() ;
		String id,name,des = null ;
		if(cur==null) return null ;
		id = ID ;
		name = cur.elementText("name");
		des = cur.elementText("des");
		ditu = new Ditu(id, name, des, 0, 0);
		ditu.initPoint = SUtils.psCoord(cur.elementText("initPoint"));
		ditu = GameControl.getInstance().psDitu(cur, ditu);
		return ditu ;
	}
	
	/**
	 * 加载副本信息,并得到和设置npc信息
	 * @return 返回资料库中所有的npc的信息
	 */
	public List<Ditu> loadFuben(){
		List<Ditu> ditu = new ArrayList<>();
		Document document = SUtils.load("game/xml/fuben.xml");
		/** 获取根目录 */
		Element root = document.getRootElement();
		List<Element> temp  = root.elements();
		String id,name,des,rankL,rankR  = null;
		for (int i = 0; i < temp.size(); i++) {
			Element dituEle = temp.get(i) ;
			id = dituEle.attribute("id").getText().trim();
			name = temp.get(i).element("name").getText().trim();
			des = temp.get(i).element("des").getText().trim();
			rankL = temp.get(i).element("rankL").getText();
			rankR = temp.get(i).element("rankR").getText();
			Ditu fuben = new Ditu(id, name, des, SUtils.conStrtoInt(rankL), SUtils.conStrtoInt(rankR));
			GameControl.getInstance().psDitu(dituEle, fuben);
			ditu.add(fuben);
		}
		return ditu ;
	}
	
}
