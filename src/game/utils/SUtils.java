package game.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import game.control.EquipControl;
import game.control.GameControl;
import game.control.GongCtrl;
import game.control.TaskCtrl;
import game.entity.AddAttrs;
import game.entity.Archive;
import game.entity.CitiaoSD;
import game.entity.Ditu;
import game.entity.Equip;
import game.entity.Gong;
import game.entity.NPC;
import game.entity.Player;
import game.entity.Scene;
import game.entity.Skill;
import game.entity.Tasks;
import game.entity.Tier;
import game.view.ui.DemoScrollBarUI;

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
	
	private DataCal dataCal ;
	{
		dataCal = new DataCal();
	}

	private static int count = 21;

	/**
	 * 随机数生成
	 */
	private static Random rd = new Random(System.currentTimeMillis());

	private static GameControl gameControl = GameControl.getInstance();

	
	/*****************************************************************
	 * 基本方法
	 ******************************************************************/
	/**
	 * 判断字符串是否为空 不为空则去空格
	 * 
	 * @param s
	 *            字符串
	 * @return true为空 false不为空
	 */
	public static boolean isNullStr(String s) {
		if (s == null || s.trim().length() <= 0)
			return true;
		else
			return false;
	}

	public static String strTrim(String s) {
		return s == null ? "" : s.trim();
	}

	public static String strTrim(Object obj) {
		String s = conObjToStr(obj);
		return s == null ? "" : s.trim();
	}

	/**
	 * Object 转化成 int
	 * @param obj
	 * @return
	 */
	public static int conObjToInt(Object obj) {
		return obj == null || "".equals(obj.toString()) ? 0 : Integer.parseInt(obj.toString().trim());
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
	 * @param str
	 * @return
	 */
	public static boolean conStrToBol(String str) {
		return isNullStr(str) ? false : Boolean.parseBoolean(str);
	}

	/**********************************************************************
	 * 解析  
	 **********************************************************************/

	

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
	public static String getGongField(int i, Gong gong) {
		String text = null;
		if (i == 1)
			text = gong.getNum() + "";
		else if (i == 2)
			text = gong.getQua() + "";
		else if (i == 3) {
			int rank = gong.getNeedRank();
			text = C.STATE[rank / 10];
		}
		return text;
	}

	/**
	 * 自动设置组件居中
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
	 * @param num
	 * @return
	 */
	public static int conStrtoInt(String num) {
		num = strTrim(num);
		return "".equals(num) ? 0 : Integer.valueOf(num.trim());
	}

	/**
	 * Object 转化为 数字
	 * @param obj
	 * @return 数字
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
	 * Object 转化为 String
	 * 
	 * @param obj
	 * @return
	 */
	public static String conObjToStr(Object obj) {
		return obj == null ? "" : obj.toString().trim();
	}

	/**
	 * 去掉浮点类型小数点，四舍五入
	 * @param str 传入String
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
		// BigDecimal b = new BigDecimal(dou);
		int num = new Long(Math.round(dou)).intValue();
		// double num = b.setScale(0, RoundingMode.HALF_UP).doubleValue();
		return num;
	}


	/**
	 * 使当前线程睡眠 time 毫秒
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
		System.out.println("背包功法书数量:" + player.getGongBag().size());
		return archive;
	}

	/**
	 * 把毫秒变为一定格式的时间
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

	public static double formatDou(double data, int len) {
		String str = formatDouble(data, len);
		return conStrtoDou(str);
	}

	public static void showThread() {
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int noThreads = currentGroup.activeCount();
		Thread[] lstThreads = new Thread[noThreads];
		currentGroup.enumerate(lstThreads);
		for (int i = 0; i < noThreads; i++)
			System.out.println("Thread No:" + i + " = " + lstThreads[i].getName());
	}

	/**
	 * 将文件写入日志
	 * 
	 * @param str
	 */
	public static void writeFtLog(String str) {
		// 暂时关闭
		/*
		 * try { // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件,如果为 //
		 * true，则将字节写入文件末尾处，而不是写入文件开始处 FileWriter writer = new
		 * FileWriter("game/log/ftlog.txt", true); writer.write(str); writer.close(); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	

	/** 把传入的属性名转为对应数字 */
	public int conNameToCode(String attrName) {
		int value = 0;
		switch (attrName) {
		case C.TiLi:
			value = C.tiLi;
			break;
		case C.JingLi:
			value = C.jingLi;
			break;
		case C.Li:
			value = C.li;
			break;
		case C.Min:
			value = C.min;
			break;
		case C.Lucky:
			value = C.lucky;
			break;
		case C.Hp:
			value = C.hp;
			break;
		case C.Mp:
			value = C.mp;
			break;
		case C.Atk:
			value = C.atk;
			break;
		case C.Def:
			value = C.def;
			break;
		case C.BaoJi:
			value = C.baoJi;
			break;
		case C.MonAdd:
			value = C.monAdd;
			break;
		case C.EqDrop:
			value = C.eqDrop;
			break;
		case C.ExpAdd:
			value = C.expAdd;
			break;
		case C.WMs:
			value = C.wms;
			break;
		case C.FMs:
			value = C.fms;
			break;
		case C.Lj2:
			value = C.lj2;
			break;
		case C.Lj3:
			value = C.lj3;
			break;
		case C.Suck:
			value = C.suck;
			break;
		default:
			break;
		}
		return value;
	}

	/**
	 * 把传入的属性数字转为对应属性名
	 * 
	 * @param attrName
	 * @return
	 */
	public String conCodeToName(int attrCode) {
		String value = "";
		switch (attrCode) {
		case C.tiLi:
			value = C.TiLi;
			break;
		case C.jingLi:
			value = C.JingLi;
			break;
		case C.li:
			value = C.Li;
			break;
		case C.min:
			value = C.Min;
			break;
		case C.lucky:
			value = C.Lucky;
			break;
		case C.hp:
			value = C.Hp;
			break;
		case C.mp:
			value = C.Mp;
			break;
		case C.atk:
			value = C.Atk;
			break;
		case C.def:
			value = C.Def;
			break;
		case C.baoJi:
			value = C.BaoJi;
			break;
		case C.monAdd:
			value = C.MonAdd;
			break;
		case C.eqDrop:
			value = C.EqDrop;
			break;
		case C.expAdd:
			value = C.ExpAdd;
			break;
		case C.wms:
			value = C.WMs;
			break;
		case C.fms:
			value = C.FMs;
			break;
		case C.lj2:
			value = C.Lj2;
			break;
		case C.lj3:
			value = C.Lj3;
			break;
		case C.suck:
			value = C.Suck;
			break;

		case C.reHp:
			value = C.ReHp;
			break;
		case C.reMp:
			value = C.ReMp;
			break;
		case C.atkUp:
			value = C.AtkUp;
			break;
		case C.atkDown:
			value = C.AtkDown;
			break;
		case C.defUp:
			value = C.DefUp;
			break;
		case C.defDown:
			value = C.DefDown;
			break;
		case C.speedUp:
			value = C.SpeedUp;
			break;
		case C.speedDown:
			value = C.SpeedDown;
			break;
		case C.hitUp:
			value = C.HitUp;
			break;
		case C.hitDown:
			value = C.HitDown;
			break;
		case C.dodgeUp:
			value = C.DodgeUp;
			break;
		case C.dodgeDown:
			value = C.DodgeDown;
			break;
		case C.wuRebound:
			value = C.WuRebound;
			break;
		case C.bufRe:
			value = C.BufRe;
			break;
		case C.debufRe:
			value = C.DebufRe;
			break;
		case C.daze:
			value = C.Daze;
			break;

		default:
			break;
		}
		return value;
	}

	
	/***************************************************
	 * 开始xml操作
	 ***************************************************/
	
	/**
	 * 保存功法信息
	 * 
	 * @param gongList
	 */
	public void saveGongInfo(List<Gong> gongList) {
		Document document = getXmlDoc(C.xml_Gong);
		/** 获取根目录 */
		Element root = document.getRootElement();

		List<Element> temp = root.elements();
		Element target = null;
		Gong gongTmp = null;
		for (int i = 0; i < temp.size(); i++) {
			target = temp.get(i);
			root.remove(target);
			/*
			 * for (int j = 0; j < gongList.size(); j++) {
			 * if(gongList.get(j).getId().equals(target.attributeValue("id"))){ gongTmp =
			 * gongList.get(j) ; gongList.remove(j); } }
			 * target.element("name").setText(gongTmp.getName());
			 * target.element("quality").setText(gongTmp.getQuality()+"");
			 * target.element("des").setText(gongTmp.getDes());
			 * target.element("req").setText(gongTmp.getRequire()+"");
			 * target.element("needRank").setText(gongTmp.getNeedRank()+"");
			 * target.element("maxTier").setText(gongTmp.getMaxTier()+"");
			 * target.element("curTier").setText(gongTmp.getCurTier()+"");
			 * target.element("type").setText(gongTmp.getType()+"");
			 */
		}
		for (int i = 0; i < gongList.size(); i++) {
			Element newEle = root.addElement("gong");
			Gong gong = gongList.get(i);
			newEle.addAttribute("id", gong.getId());
			newEle.addElement("name").setText(gong.getName());
			newEle.addElement("des").setText(gong.getDes());
			newEle.addElement("qua").setText(gong.getQua() + "");
			newEle.addElement("maxTier").setText(gong.getMaxTier() + "");
			newEle.addElement("type").setText(gong.getType() + "");
			newEle.addElement("needRank").setText(gong.getNeedRank() + "");
			newEle.addElement("effectStr").setText(gong.getEffectStr() + "");

			newEle.addElement("req").setText(gong.getRequire() + "");
			newEle.addElement("price").setText(gong.getPrice() + "");
			newEle.addElement("curTier").setText(gong.getCurTier() + "");
			newEle.addElement("effect");
		}
		xmlSave(getXmlPath(C.xml_Gong), document);
	}

	/**
	 * 先读取 xml 文件 要保存第几个fb
	 * 
	 * @param str
	 * @param index
	 * @param fb
	 */
	public void saveDituInfo(String str, int index, Ditu fb) {
		Document document = getXmlDoc(C.xml_Fuben);
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
		List<Scene> sceneList = fb.getScene();
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
		xmlSave(getXmlPath(C.xml_Fuben), document);
	}

	/**
	 * 保存技能信息 id 不为空 name 不为空 des 不为空 类型 不为空 1主动 2被动 消耗 默认为0 结果 不能为空 目标 默认敌方 范围 默认单体
	 * 功法id 默认为2000 功法name 默认为暂无 使用条件 默认无 学习条件 默认无 需要等级 默认0 cd 默认0 curCd 默认0
	 * 
	 * @param skillList
	 */
	public void saveSkillInfo(List<Skill> skillList) {
		Document document = load("game/xml/skill.xml");
		/** 获取根目录 */
		Element root = document.getRootElement();
		List<Element> temp = root.elements("skill");
		Element target = null;
		Skill skillTmp = null;
		for (int i = 0; i < temp.size(); i++) {
			target = temp.get(i);
			for (int j = 0; j < skillList.size(); j++) {
				System.out.println("id:" + skillList.get(j).id + "," + target.attributeValue("id"));
				if (skillList.get(j).id.equals(target.attributeValue("id"))) {
					skillTmp = skillList.get(j);
					skillList.remove(j);
				}
			}
			target.element("name").setText(strTrim(skillTmp.name));
			target.element("des").setText(strTrim(skillTmp.des));
			target.element("type").setText(strTrim(strTrim(skillTmp.type)));
			target.element("consume").setText(strTrim(skillTmp.consume));
			target.element("scope").setText(strTrim(skillTmp.scope));
			target.element("gongId").setText(strTrim(skillTmp.gongId));
			target.element("gongName").setText(strTrim(skillTmp.gongName));
			target.element("useCase").setText(strTrim(skillTmp.useCase));
			target.element("studyCase").setText(strTrim(skillTmp.studyCase));
			target.element("needTier").setText(strTrim(skillTmp.needTier + ""));
			target.element("cd").setText(strTrim(skillTmp.cd + ""));
			target.element("curCd").setText(strTrim("0"));
		}
		System.out.println("新增了" + skillList.size() + "条!");
		for (int i = 0; i < skillList.size(); i++) {
			Element newEle = root.addElement("skill");
			newEle.addAttribute("id", skillList.get(i).id);
			System.out.println(skillList.get(i).name);
			newEle.addElement("name").setText(skillList.get(i).name);
			newEle.addElement("des").setText(skillList.get(i).des);
			newEle.addElement("type").setText(skillList.get(i).type);
			newEle.addElement("consume").setText(skillList.get(i).consume);
			newEle.addElement("scope").setText(skillList.get(i).scope);
			newEle.addElement("gongId").setText(skillList.get(i).gongId);
			newEle.addElement("gongName").setText(skillList.get(i).gongName);
			newEle.addElement("useCase").setText(skillList.get(i).useCase);
			newEle.addElement("studyCase").setText(skillList.get(i).studyCase);
			newEle.addElement("needTier").setText(skillList.get(i).needTier + "");
			newEle.addElement("cd").setText(skillList.get(i).cd + "");
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
			writer = new XMLWriter(new FileOutputStream("game/xml/Skill.xml"), outFormat);
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
			document = saxReader.read(SUtils.class.getResourceAsStream("/" + filename)); // 读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/***
	 * 返回xml的路径
	 * 
	 * @param type
	 * @return
	 */
	public String getXmlPath(int type) {
		String path = "game/xml/";
		switch (type) {
		case C.xml_Gong:
			path += "Gong.xml";
			break;
		case C.xml_Skill:
			path += "skill.xml";
			break;
		case C.xml_Equip:
			path += "equip.xml";
			break;
		case C.xml_Npc:
			path += "npc.xml";
			break;
		case C.xml_Task:
			path += "task.xml";
			break;
		case C.xml_Fuben:
			path += "fuben.xml";
			break;
		case C.xml_EquipSet:
			path += "equipSetting.xml";
			break;
		case C.xml_Scene:
			path += "scene.xml" ;
			break;
		default:
			break;
		}
		return path;
	}

	/***
	 * 通过类型读取不同xml文档节点
	 * 
	 * @param type
	 * @return
	 */
	public Document getXmlDoc(int type) {
		Document doc = null;
		doc = load(getXmlPath(type));
		return doc;
	}

	/**
	 * xml保存的通用方法
	 */
	public void xmlSave(String path, Document doc) {
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
			// this.getClass().getResource("/"+path).getPath()
			writer = new XMLWriter(new FileOutputStream("src/" + path), outFormat);
			writer.write(doc);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				writer.close();
				System.err.println("保存xml信息成功！");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 加载对应装备的属性生成设定
	 * 
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
			CitiaoSD theAttr = new CitiaoSD(part, i, conStrtoInt(type), attrType, attrName, conStrtoDou(ratio));
			list.add(theAttr);
		}
		return list;
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
			int curState = conStrtoInt(eleList.get(i).element("curState").getText());
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
			if (!isNullStr(id)) {// 不为空则加入
				String name = npcEle.elementText("name");
				String des = npcEle.elementText("des");
				String msg = npcEle.elementText("msg").trim();
				String[] msgs = msg.split("\\|");
				int rank = conStrtoInt(npcEle.element("rank").getText());
				int type = conStrtoInt(npcEle.element("type").getText());
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
		System.err.println("--加载功法xml文件!");
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
			gong.setWeight(conStrtoInt(ele.elementText("weight")));
			if (isExistEle(ele, "type")) {
				type = conStrtoInt(ele.elementText("type"));
				gong.setType(type);
			}
			if (isExistEle(ele, "maxTier")) {
				maxTier = conStrtoInt(ele.elementText("maxTier"));
				gong.setMaxTier(maxTier);
			}
			if (isExistEle(ele, "curTier")) {
				curTier = conStrtoInt(ele.elementText("curTier"));
				gong.setCurTier(curTier);
			}
			if (isExistEle(ele, "quality")) {
				quality = conStrtoInt(ele.elementText("quality"));
				gong.setQua(quality);
			}
			if (isExistEle(ele, "needRank")) {
				needRank = conStrtoInt(ele.elementText("needRank"));
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
					skill.needTier = conStrtoInt(e.attributeValue("needTier"));
					gong.getAllSkills().add(skill);
				}
			}
			if (isExistEle(ele, "baseEff") && !isNullStr(ele.elementText("baseEff"))) {
				String baseEffStr = ele.elementText("baseEff").trim();
				System.out.println(baseEffStr);
				String[] baseEffAry = baseEffStr.split(";");
				AddAttrs addAttrs = new AddAttrs();
				for (int j = 0; j < baseEffAry.length; j++) {
					String[] key = baseEffAry[j].split(":");
					System.out.println(baseEffAry[j]+","+key[1]+","+addAttrs+","+gong+","+key[0]+","+dataCal);
					addAttrs = dataCal.accGongAddAttrs(addAttrs, key[0], gong, SUtils.conStrtoInt(key[1]));
				}
				System.out.println("功法加成:" + addAttrs.info());
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
		System.err.println("加载得到了:" + map.size() + "部功法!");
		return map;
	}

	/**
	 * 保存功法 每一层带来属性加成和可能解锁的技能信息
	 * 
	 * @param type
	 * @param id
	 * @param tierList
	 * @param skillList
	 */
	public void saveGongSkillOrEffect(int type, String id, List<Tier> tierList, List<Skill> skillList) {
		Document doc = getXmlDoc(C.xml_Gong);
		Node gongNode = doc.selectSingleNode("/root/gong[@id='" + id.trim() + "']/name");
		Element gongEle = gongNode.getParent();
		if (gongEle == null) {
			return;
		}
		if (type == 1) {
			saveGongEffectInfo(gongEle, tierList);
		} else if (type == 2) {
			saveGongSkillInfo(gongEle, skillList);
		}
		xmlSave(getXmlPath(C.xml_Gong), doc);
	}

	/**
	 * 保存功法信息中每种功法可以解锁的技能的信息
	 * 
	 * @param gongEle
	 * @param skillList
	 */
	private void saveGongSkillInfo(Element gongEle, List<Skill> skillList) {
		if (skillList.size() == 0)
			return;
		if (gongEle.selectNodes("skills").size() > 0) {
			gongEle = gongEle.element("skills");
		} else {
			gongEle = gongEle.addElement("skills");
		}
		/** 移除之前的 */
		List<Element> eleList = gongEle.elements();
		for (int i = 0; i < eleList.size(); i++) {
			gongEle.remove(eleList.get(i));
		}
		/** 根据提交内容新增 */
		for (int i = 0; i < skillList.size(); i++) {
			Element ele = gongEle.addElement("skill");
			ele.addAttribute("id", skillList.get(i).id + "");
			ele.addAttribute("name", skillList.get(i).name + "");
			ele.addAttribute("needTier", skillList.get(i).needTier + "");
			ele.addAttribute("useCase", skillList.get(i).useCase + "");
			ele.addAttribute("studyCase", skillList.get(i).studyCase + "");
		}
	}

	/**
	 * 保存功法信息中每种每一层的效果
	 * 
	 * @param gongEle
	 * @param skillList
	 */
	private void saveGongEffectInfo(Element gongEle, List<Tier> tierList) {
		if (tierList.size() == 0)
			return;
		if (gongEle.selectNodes("effect").size() > 0) {
			gongEle = gongEle.element("effect");
		} else {
			gongEle = gongEle.addElement("effect");
		}
		/** 移除之前的 */
		List<Element> eleList = gongEle.elements();
		for (int i = 0; i < eleList.size(); i++) {
			gongEle.remove(eleList.get(i));
		}
		/** 根据提交内容新增 */
		for (int i = 0; i < tierList.size(); i++) {
			Element ele = gongEle.addElement("tier");
			ele.addAttribute("value", tierList.get(i).curTier + "");
			Element needRank = ele.addElement("needRank");
			needRank.setText(tierList.get(i).needRank + "");
			Element needExp = ele.addElement("needExp");
			needExp.setText(tierList.get(i).needExp + "");
			Element addAttrs = ele.addElement("AddAttrs");
			addAttrs.setText(tierList.get(i).addAttrStr.trim());
		}
	}

	public static int conGongQua(String qua) {
		if (qua.equals("天品")) {
			return 0;
		} else if (qua.equals("地品")) {
			return 1;
		} else if (qua.equals("玄品")) {
			return 2;
		} else if (qua.equals("黄品")) {
			return 3;
		}
		return 0;
	}

	public String getProjectPath() {
		// 第一种：获取类加载的根路径 D:\git\daotie\daotie\target\classes
		File f = new File(this.getClass().getResource("/").getPath());
		System.out.println(f);

		// 获取当前类的所在工程路径; 如果不加“/” 获取当前类的加载目录 D:\git\daotie\daotie\target\classes\my
		File f2 = new File(this.getClass().getResource("").getPath());
		System.out.println(f2);

		// 第二种：获取项目路径 D:\git\daotie\daotie
		File directory = new File("");// 参数为空
		String courseFile = null;
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(courseFile);

		// 第三种： file:/D:/git/daotie/daotie/target/classes/
		URL xmlpath = this.getClass().getClassLoader().getResource("");
		System.out.println(xmlpath);

		// 第四种： D:\git\daotie\daotie
		System.out.println(System.getProperty("user.dir"));
		/*
		 * 结果： C:\Documents and Settings\Administrator\workspace\projectName 获取当前工程路径
		 */

		// 第五种： 获取所有的类路径 包括jar包的路径
		System.out.println(System.getProperty("java.class.path"));
		return "";
	}

	/**
	 * 基数 1000
	 * 
	 * @param needRank
	 *            10
	 * @param qua
	 *            3
	 * @return
	 */
	public static int getGongPrice(int needRank, int qua) {
		int price = 1000;
		int state = needRank % 10;
		for (int i = 0; i < state; i++) {
			price *= 10;
		}
		for (int i = 0; i < qua; i++) {
			price *= 3;
		}
		return price;
	}


	/** 样式设置 */

	public static void setEmptyBorder(JComponent c) {
		c.setBorder(new EmptyBorder(0, 0, 0, 0));
	}

	public static ImageIcon loadImageIcon(String fileName) {
		ImageIcon imageIcon = null;
		try {
			imageIcon = new ImageIcon(ImageIO.read(SUtils.class.getResourceAsStream(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageIcon;
	}

	public static int rd(int num) {
		return rd.nextInt(num);
	}

	/** 设置自定义滑轮样式 */
	public static void setScrollUI(JScrollPane jsc) {
		jsc.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		jsc.getHorizontalScrollBar().setUI(new DemoScrollBarUI());
	}

	public static void setBackImg(JPanel c, String bacPath) {
		if (isNullStr(bacPath))
			return;
		JLabel back = new JLabel();
		ImageIcon img = SUtils.loadImageIcon("/game/img/back/GameBack.png");
		back.setIcon(img);
		back.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		c.add(back);
	}

	/*******************************
	 ***** 技能相关
	 *******************************/
	public double calSkDamageRatio(Skill skill) {
		int skillCurRank = skill.skillCurRank;
		String skillQua = skill.qua;
		int qua = getQuaNum(skillQua);
		/** 基础系数 + 每级*2%/3%/4%/5% */
		double ratio = skillCurRank * C.skillQuaAdd[qua] * 0.01 + skill.baseDamageRatio;
		return ratio;
	}

	public int getQuaNum(String qua) {
		int quaNum = 0;
		switch (qua) {
		case "天":
			quaNum = 3;
			break;
		case "地":
			quaNum = 2;
			break;
		case "玄":
			quaNum = 1;
			break;
		case "黄":
			quaNum = 0;
			break;
		default:
			break;
		}
		return quaNum;
	}

	public static void npcMsg(NPC npc, String[] msgs) {
		int num = new Random().nextInt(msgs.length);
		gameControl.append(npc.getName() + " : ", 2);
		gameControl.append(msgs[num].trim() + "\n", 0);
	}

	public static void removeActionLner(JButton jb) {
		ActionListener[] acAry = jb.getActionListeners();
		for (int i = 0; i < acAry.length; i++) {
			jb.removeActionListener(acAry[i]);
		}
	}

	/**
	 * 某个节点下是否存在名为xx的节点且节点的内容不为空
	 * 
	 * @param ele
	 * @param name
	 * @return
	 */
	public static boolean isExistEle(Element ele, String name) {
		boolean flag = ele.selectNodes(name).size() > 0;
		flag = flag && !isNullStr(ele.elementText(name));
		return flag;
	}

	/**
	 * 解析坐标
	 * 
	 * @return
	 */
	public static Point psCoord(String coordStr) {
		String[] tempAry = coordStr.split(",");
		Point p = new Point();
		/** x,y坐标 */
		p.x = SUtils.conStrtoInt(tempAry[0]);
		p.y = SUtils.conStrtoInt(tempAry[1]);
		return p;
	}

	/**
	 * 把特定格式的字符串分割成数组
	 * 
	 * @param str
	 * @return
	 */
	public static String[] strToAry(String str) {
		String[] ary;
		ary = str.split(",");
		if (ary.length < 2)
			ary = str.split(":");
		return ary;
	}

	/**  */
	public static boolean canArrived(Scene sc, Scene sc2) {
		List<Point> points = sc.points;
		int x = sc2.x;
		int y = sc2.y;
		for (Point point : points) {
			if (point.x == x && point.y == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断某场景四周的连接线情况 每行第一个元素判断左上右 其他欧安段上右
	 * 
	 * @param scene
	 *            当前场景
	 * @param list
	 * @return
	 */
	public static boolean[] CanConnects(Scene scene, List<Scene> list) {
		boolean[] flags = { false, false };
		flags[0] = isExistScene(list, scene, scene.x, scene.y - 1);
		flags[1] = isExistScene(list, scene, scene.x + 1, scene.y);
		return flags;
	}

	/***
	 * 列表中是否存在 坐标为x,y的场景 找到的场景是否能到达原场景
	 * 
	 * @param list
	 * @param theSc
	 *            当前场景
	 */
	public static boolean isExistScene(List<Scene> list, Scene theSc, int x, int y) {
		Scene sc = null;
		for (Scene scene : list) {
			if (scene.x == x && scene.y == y) {
				sc = scene;
			}
		}
		if (sc != null) {
			List<Point> points = theSc.points;
			for (Point point : points) {
				/** 场景不能到达 */
				if (point.x == x && point.y == y) {
					return false;
				}
			}
			/** 存在场景切 */
			return true;
		}
		return false;
	}

	/****************************************************
	 * 控制台
	 ****************************************************/
	/**
	 * 分析控制台的代码 如 get:20000:1 如 clear:gongBag
	 */
	public static void psConsoleStr(String str) {
		System.out.println(str);
		if (str.startsWith("get:")) {
			String[] ary = str.split(":");
			int id = SUtils.conStrtoInt(ary[1]);
			int num = SUtils.conStrtoInt(ary[2].trim());
			if (20000 <= id && id < 29999) {
				Gong gong = gameControl.getGongByID(id + "");
				System.out.println(num);
				gong.setNum(num);
				gameControl.getPlayer().getGongBag().add(gong);
				System.out.println(gameControl.getPlayer().getGongBag().size());
			} else if (30000 < id && id < 39999) {

			}
		} else if (str.startsWith("clear:")) {
			String[] ary = str.split(":");
			String cmd = ary[1];
			switch (cmd) {
			case "gongBag":
				System.err.println("清除背包中功法书成功!");
				List<Gong> list = new ArrayList<>();
				gameControl.getPlayer().setGongBag(list);
				break;
			case "equipBag":

				break;
			default:
				break;
			}
		} else if (str.startsWith("reload:")) {
			String[] ary = str.split(":");
			String cmd = ary[1];
			if (cmd.equals("xml")) {
				gameControl.loadXml();
			}
		}
	}
	
	public List<Skill> analyzeSkill(Element element) {
		List<Element> eleList = element.elements();
		List<Skill> skillList = new ArrayList<>();
		Element temp;
		Skill skill = null;
		/**  */
		for (int i = 0; i < eleList.size(); i++) {
			temp = eleList.get(i);
			skill = new Skill();
			skill.id = temp.attributeValue("id");
			skill.name = temp.elementText("name");
			skill.des = temp.elementText("des");
			skill.type = temp.elementText("type");
			skill.scope = temp.elementText("target");
			skill.studyCase = temp.elementText("studyCase");
			skill.cd = conStrtoInt(temp.elementText("cd"));
			skill.useCase = temp.elementText("useCase") == null ? "" : temp.elementText("useCase");
			skillList.add(skill);
		}
		return skillList;
	}

}