package game.utils;

import game.control.EquipControl;
import game.control.GameControl;
import game.entity.Archive;
import game.entity.CitiaoSD;
import game.entity.Equip;
import game.entity.Ditu;
import game.entity.Item;
import game.entity.Material;
import game.entity.NPC;
import game.entity.Player;
import game.entity.Scene;
import game.entity.Tasks;
import game.listener.BagActionListener;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import sun.audio.AudioStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class SUtils {
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
	 * 加载xml文件中所有的npc的资料
	 * 
	 * @return
	 */
	public static Map<String, NPC> loadNpc() {
		Map<String, NPC> map = new HashMap<>();
		Document document = load("src/game/xml/npc.xml");
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		Element temp = null , action = null ;
		NPC tempNpc = null;
		int id, type = 0 ;
		String name = "", des = "", msg = "";
		int rank = 0;
		for (int i = 0; i < elementList.size(); i++) {
			/** 得到npc节点 */
			temp = elementList.get(i);
			name = temp.element("name").getText();
			if (!isNullStr(name)) {// 不为空则加入
				id = SUtils.conStrtoInt(temp.element("id").getText());
				des = temp.element("des").getText();
				msg = temp.element("msg").getText();
				rank = conStrtoInt(temp.element("rank").getText());
				type = conStrtoInt(temp.element("type").getText());
				action = temp.element("action");
				tempNpc = new NPC();
				npcActionAnalyze(action,tempNpc);
				tempNpc.setId(id);
				tempNpc.setName(name);
				tempNpc.setDes(des);
				tempNpc.setMsg(msg);
				tempNpc.setRank(rank); 
				tempNpc.setType(type);
				map.put(name, tempNpc);
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
						if(id.equals("bestTrader")){//极品商人|卖装备和图纸和材料
							goods = getBestTraderGoods(num);
						}else if(id.equals("petTrader")){//售卖宠物蛋
							goods = getPetTraderGoods(num);
						}else if(id.equals("skillTrader")){//售卖技能书
							goods = getSkillTraderGoods(num);
						}
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
	
	/** 得到技能书商人的货物信息 */
	private static List<Object> getSkillTraderGoods(int num) {
		return null;
	}
	/** 得到宠物商人的货物信息 */
	private static List<Object> getPetTraderGoods(int num) {
		return null;
	}
	/** 得到极品商人的货物信息 
	 * @return */
	private static List<Object> getBestTraderGoods(int num) {
		return null;
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
				String tempStr = getShowField(j, equip);
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
	public static String getShowField(int i, Equip e) {
		String temp = null;
		if (i == 1)
			temp = EquipControl.typeAry[e.getType()];
		else if (i == 2)
			temp = EquipControl.partDes[e.getPart()];
		else
			temp = e.getNum() + "";
		return temp;
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
		return "".equals(num) ? 0 : Integer.valueOf(num);
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
		// Document doc= load(str);

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

		ele.element("name").setText(fb.name);
		ele.element("des").setText(fb.des);
		ele.element("rankL").setText(fb.rankL + "");
		ele.element("rankR").setText(fb.rankR + "");
		Element e = ele.element("map");
		Element map = ele.addElement("map");
		Element point, name, des, x, y, npcStr;
		ele.remove(e);
		Scene sc = null;
		for (int j = 0; j < fb.scene.size(); j++) {
			sc = fb.scene.get(j);
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
		SUtils s = new SUtils();
		// SUtils.loadWeapon();
		Document document = SUtils.load("src/game/xml/npc.xml");
		Node node = document.selectSingleNode("/root/npc[@id='1003']");
		System.out.println(node.asXML());
		
		//s.writeFtLog("日志写入测试！");
		//s.writeFtLog("写入内容");
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

}
