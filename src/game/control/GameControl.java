package game.control;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.dom4j.Element;

import game.entity.AddAttrs;
import game.entity.Archive;
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
import game.listener.FunListener;
import game.listener.KeyMana;
import game.listener.actionLner.AcLner;
import game.listener.actionLner.SpSceneLner;
import game.utils.ArchiveUtils;
import game.utils.C;
import game.utils.DataCal;
import game.utils.SUtils;
import game.utils.XmlUtils;
import game.view.TLabel;
import game.view.TTextPane;
import game.view.button.MButton;
import game.view.button.TButton;
import game.view.frame.EnterFrame;
import game.view.frame.MainFrame;
import game.view.frame.MyDialog;
import game.view.frame.SpFrame;
import game.view.panel.GameView;

/** 控制各个组件 */
public class GameControl {
	
	private EnterFrame enterFrame;
	private MainFrame mainFrame;
	private JLayeredPane layeredPanel ;
	/**
	 * 人物面板 房间描述 功能 游戏信息 小地图 npc与物品 与npc或物品交互
	 */
	/** 玩家信息面板 */
	private JPanel playerP = null;
	/** 
	 * 玩家所处场景信息 
	 * 文本框
	 */
	private TTextPane sceneP = null;
	/***交互按钮组*/
	private JPanel functionP = null;
	/** 游戏过程信息 */
	private TTextPane infoP = null;
	/** 游戏小地图面板 */
	private JPanel mapP = null;
	/** 场景中显示人物面板 */
	private JPanel npcP = null;
	/** 交互面板 */
	private JPanel controlP = null;
	
	private GameView gameView ;
	
	
	private static GameControl gameControl = new GameControl();
	/** 没有选择 空存档 存在存档 */
	public static final int NotSelect = -1;
	public static final int NewArchive = 0;
	public static final int OldArchive = 1;

	public Map equipBag = new HashMap<>();
	private Random rd = new Random(System.currentTimeMillis());
	private String archiveName;
	
	
	
	/** 游戏过程信息实体类 */
	/** 当前选择地图 */
	private Ditu curDitu = null;
	/** 当前选择场景 */
	private Scene scene;
	
	/** 加载的xml文件信息  功，技能，装备，人物，人物，副本，装备设定，场景 */
	private Map<String, Gong>   gongMap = null ;
	private Map<String, Skill>  skillMap = null ;
	private Map<String, Equip>  equipMap = null ;
	private Map<String, NPC>    npcMap = null ;
	private List<Ditu>   fbMap = null ;
	private Map<String, Tasks>  taskMap =null ;
	private Map<String, Ditu>   scMap = null ;
	
	private SpFrame fubenFrame, stateFrame, fightFrame, jiangHuFrame, bagFrame, mapFrame,  taskFrame;
	
	/** 判断是否进入了场景的标志 */
	private boolean isInScene = false;
	
	/** 其他控制器 **/
	private NpcCtrl npcCtrl = NpcCtrl.getInstance() ;
	
	private MyDialog tan = null ;
	private XmlUtils xmlUtils = null ;
	
	/**
	 * 加载游戏控制器
	 * 应该在其中完成相应资料的加载
	 * 	加载顺序
	 * 	武器信息
	 * 	npc信息
	 * 	副本信息
	 *  剧情信息
	 * 
	 */
	private GameControl() {
		loadXml();
	}
	
	
	
	/** 重新加载xml文件 */
	public void loadXml() {
		/** 功，技能，装备，人物，人物，副本，装备设定，场景 */
		if(xmlUtils==null) 
			xmlUtils = new XmlUtils();
		gongMap  = xmlUtils.loadGong();
		skillMap = xmlUtils.loadSkill();
		equipMap = xmlUtils.loadEquip();
		npcMap   = xmlUtils.loadNpc();
		fbMap    = xmlUtils.loadFuben();
		taskMap  = xmlUtils.loadTask();
		//scMap    = xmlUtils.loadScene(ID);
	}
	
	/** 当前存档 */
	private Archive archive = null ;
	/** 当前玩家实体类 */
	private Player player = null ;
	/** 当前与之战斗的npc */
	private NPC fightNpc = null ;
	
	
	
	public static GameControl getInstance() {
		return gameControl;
	}

	public void setPlayerInfo() {
		// mainFrame
	}
	
	/** 装备控制器 */
	public EquipControl equipControl = new EquipControl();

	/** 战斗面板 */
	private SpFrame ftFrame = null ;
	
	
	private KeyMana keyMana ;
	
	/** 在信息面板上显示字符串 */
	public void append(String str, int type) {
		infoP.append(str, type);
	}
	
	/** 在信息面板上增加选择按钮 */
	public void addSelectBu(TButton bu) {
		infoP.insertComponent(bu);
	}
	
	/** 退出游戏 */
	public void exitGame() {
		if (enterFrame != null) {
			enterFrame.dispose();
		}
		if (mainFrame != null) {
			mainFrame.dispose();
		}
	}

	/** 关闭弹窗窗口 */
	public void close(int type) {
		/*if (type == Constant.Fuben) {
			infoP.append("你犹豫了会，还是决定先不去【副本】了！\n", 0);
		}else if(type == Constant.State){
			infoP.append("你不再查看自己的【状态】!\n", 0);
		}*/
		mainFrame.close(type);
	}

	/** 隐藏登陆面板 */
	public void hideEnterFrame() {
		enterFrame.setVisible(false);
	}

	/**  
	 * 确认选择存档
	 * @param archiveName 当前选择存档的存档名
	 * @return 返回选择标志
	 */
	public int getSelectArchive(String archiveName) {
		if (SUtils.isNullStr(archiveName)) {
			JOptionPane.showMessageDialog(enterFrame, "请先选择存档!", "提示",JOptionPane.WARNING_MESSAGE);
			return NotSelect;
		} else {
			Archive archive = ArchiveUtils.loadArchive(archiveName);
			if (archive == null) {
				return NewArchive;
			} else {
				return OldArchive;
			}
		}
	}
	
	/***
	 * 通过人物id得到人物编号
	 * @param id
	 * @return
	 */
	public Tasks getTaskByNaId(String id){
		return (Tasks) ArchiveUtils.depthClone(taskMap.get(id));
	}
	
	/** 
	 * 通过npc ID得到资料库中npc实体类的克隆体 
	 * @param name 要查询的npc的name
	 * @return 返回资料库中Npc实体类的克隆体
	 */
	public NPC getNpcById(String id){
		NPC npc = npcMap.get(id);
		return  (NPC) ArchiveUtils.depthClone(npc);
	}
	
	/** 
	 * 通过物品的ID得到资料库中物品实体类的克隆体 
	 * 装备
	 * 材料
	 * 功法
	 * 宠物
	 * 设计图
	 * ...
	 * @param id 要查询的物品的id
	 * @return 返回资料库中物品实体类的克隆体
	 */
	public Item getItemById(String id){
		Item item = null ;
		item = equipMap.get(id);
		if(item!=null)
			return (Item) ArchiveUtils.depthClone(item);
		item = gongMap.get(id);
		if(item!=null)
			return (Item) ArchiveUtils.depthClone(item);
		item = equipMap.get(id);
		if(item!=null)
			return (Item) ArchiveUtils.depthClone(item);
		return null ;
	}
	
	/**
	 * 通过id得到装备对象
	 * @param id
	 * @return
	 */
	public Equip getEquipById(String id){
		Equip equip = equipMap.get(id);
		return (Equip) ArchiveUtils.depthClone(equip);
	}
	
	
	/**  
	 * 对地图的解析
	 * 1:解析每个场景中的人物，物品，场景的特殊功能
	 * 2:解析地图中的特殊地图，场景
	 * @param dituEle
	 * @param ditu
	 * @return
	 */
	public Ditu psDitu(Element dituEle, Ditu ditu) {
		String id, name, des, pos ;
		Scene scene = null ;
		if(dituEle.element("scene")==null) return ditu ;
		/** 1-解析普通场景 */
		List<Element> scenes = dituEle.element("scene").elements();
		List<Scene> sceneList = new ArrayList<>();
		for (int j = 0; j < scenes.size(); j++) {
			Element ele = scenes.get(j) ;
			name = ele.attributeValue("name").trim();
			des  = ele.element("des").getText().trim();
			pos  = ele.attributeValue("pos").trim();
			Point p = SUtils.psCoord(pos);
			scene = new Scene(name, des, p.x, p.y);
			
			/** 解析场景中的人物 */
			if(SUtils.isExistEle(ele, "npcList")) {
				String npcStr = ele.elementText("npcList").trim();
				if(npcStr.length()>0) 
					scene.setNpcList(getNPCList(npcStr));
					scene.setNpcStr(npcStr);
			}
			scene = psSceneEffect(scene, ele);
			
			/** 解析场景内的物品 */
			if(SUtils.isExistEle(ele, "items")) {
				String itemStr = ele.elementText("items");
				scene.itemList = getItemList(itemStr);
			};
			
			/** 解析该场景不能直接到达的位置 */
			if(SUtils.isExistEle(ele, "unArrive")) {
				String unArrive = ele.elementText("unArrive");
				if(!SUtils.isNullStr(unArrive)) {
					System.err.println("err:"+unArrive);
					String[] ps = unArrive.split(";");
					List<Point> points = new ArrayList<>();
					for (int i = 0; i < ps.length; i++) {
						Point point = SUtils.psCoord(ps[i]);
						points.add(point);
					}
					scene.points = points ;
				}
			}
			
			/** 解析特殊入口 */
			if(SUtils.isExistEle(ele, "enters")) {
				List<Element> enters = ele.element("enters").elements();
				for (int i = 0; i < enters.size(); i++) {
					Element entrance = enters.get(i);
					scene.enters.add(entrance.attributeValue("name").trim());
				}
			}
			
			sceneList.add(scene);
		}
		
		/** 2-该地点的特殊场景 */
		List<Ditu> spDituList = new ArrayList<>();
		if(SUtils.isExistEle(dituEle, "spScene")) {
			List<Element> spPlace = dituEle.element("spScene").elements();
			for (int i = 0; i < spPlace.size(); i++) {
				Element spDituEle = spPlace.get(i) ;
				id   =  spDituEle.attributeValue("id") ;
				Ditu spDitu = new Ditu(id, "", "", 0, 0);
				List<Element> spScene = spDituEle.elements();
				for (int j = 0; j < spScene.size(); j++) {
					Element ele = spScene.get(j);
					name = ele.attributeValue("name");
					des  = ele.elementText("des").trim();
					Point p = SUtils.psCoord(ele.attributeValue("pos"));
					scene = new Scene(name, des, p.x, p.y);
					scene.isSpScene = true ;
					if(SUtils.isExistEle(ele, "exit")) {//如果是离开点
						String exitPos = ele.element("exit").attributeValue("pos");
						scene.isExit = true ;
						scene.exitPoint = SUtils.psCoord(exitPos);
					}
					spDitu.getScene().add(scene);
				}
				spDituList.add(spDitu);
			}
		}
		ditu.setScene(sceneList);
		ditu.setSpList(spDituList);
		curDitu = ditu ;
		return ditu ;
	}
	
	public Scene psSceneEffect(Scene sc, Element ele) {
		if(SUtils.isExistEle(ele, "canFishing"))
			sc.canFishing = SUtils.conStrToBol(ele.elementText("npcList").trim());
		if(SUtils.isExistEle(ele, "canWushu"))
			sc.canWushu   = SUtils.conStrToBol(ele.elementText("npcList").trim());
		if(SUtils.isExistEle(ele, "canXiu"))
			sc.canXiu     = SUtils.conStrToBol(ele.elementText("npcList").trim());
		if(SUtils.isExistEle(ele, "canDig"))
			sc.canDig     = SUtils.conStrToBol(ele.elementText("npcList").trim());
		if(SUtils.isExistEle(ele, "canCutdown"))
			sc.canCutdown = SUtils.conStrToBol(ele.elementText("npcList").trim());
		if(SUtils.isExistEle(ele, "canHunting"))
			sc.canHunting = SUtils.conStrToBol(ele.elementText("npcList").trim());
		if(SUtils.isExistEle(ele, "canRest"))
			sc.canRest    = SUtils.conStrToBol(ele.elementText("npcList").trim());
		return sc ;
	}
	
	
	/**
	 * 传入存有npc名字和数量的特殊格式字符串
	 * 创建对应对象添加到list中
	 * 例如  1005:猎户大叔:1,1004:小花:1
	 * @param str
	 * @return 返回包含场景内所悟npc信息的集合
	 */
	private List<NPC> getNPCList(String str){
		List<NPC> list = new ArrayList<>();
		if(str.length()<1)
			return list ;
		String[] temp = null ;
		String id = "" ;
		String[] ary = str.split(",");
		int num = 0 ;
		for (int i = 0; i < ary.length; i++) {
			/**  1005:猎户大叔:1 */
			temp = ary[i].split(":");
			id = temp[0];
			num = SUtils.conStrtoInt(temp[2]);
			NPC npc = getNpcById(id);
			if(npc!=null){
				for (int j = 0; j < num; j++) {
					/** 和当前玩家的幸运值相关 */
					list.add(npc);
				}
			}
		}
		return list ;
	}
	
	/**
	 * 传入存有物品名字和数量的特殊格式字符串
	 * 创建对应对象添加到list中
	 * 例如  2001:牡丹:1,2002:风筝:1
	 * @param str
	 * @return 返回包含场景内所悟npc信息的集合
	 */
	public List<Item> getItemList(String str){
		List<Item> list = new ArrayList<>();
		if(str.length()<0)  return list ;
		String[] temp = null ;
		String id = "" ;
		String[] ary = str.split(",");
		int num = 0 ;
		for (int i = 0; i < ary.length; i++) {
			/**  2001:牡丹:1 */
			temp = ary[i].split(":");
			id = temp[0];
			num = SUtils.conStrtoInt(temp[2]);
			Item item = getItemById(id);
			if(item!=null){
				for (int j = 0; j < num; j++) {
					/** 和当前玩家的幸运值相关 */
					list.add(item);
				}
			}
		}
		return list ;
	}
	
	/******************************************************
	 *			                                  初始化地图 
	 *******************************************************/
	
	/**
	 * 设置当前副本所有npc的具体信息
	 * 此时npc已有信息 name rank 
	 * 每次进入都会重新设置
	 * @param fuben 副本
	 */
	public void setNpcSpecInfo(Ditu fuben){
		List<Scene> sceneList = fuben.getScene() ;
		List<NPC> npcList = null ;
		NPC tempNpc = null ;
		for (int i = 0; i < sceneList.size(); i++) {
			System.out.println(sceneList.get(i));
			npcList = sceneList.get(i).npcList ;
			if(npcList.size()==0){ return ; }
			for (int j = 0; j < npcList.size(); j++) {
				tempNpc = npcList.get(j) ;
				if(tempNpc!=null&&!SUtils.isNullStr(tempNpc.getName())){
					npcMaker(tempNpc);
				}
			}
		}
	}
	
	/***
	 * 布置地图
	 * 根据所选择的地点布置地图
	 * 0,1 为中心，也就是起点
	 */
	public void decorateMap() {
		MButton buTemp = null;
		/** 重置场景 */
		mainFrame.hideMapBu();
		/** 整套移出并重绘的流程 */
		npcP.removeAll();
		npcP.repaint();
		npcP.revalidate();
		controlP.removeAll();
		npcP.repaint();
		npcP.revalidate();
		Scene temp = null;
		Point initPoint = curDitu.initPoint;
		int x = initPoint.x, y = initPoint.y;
		temp = getScene(x, y);
		System.out.println("当前所在:"+temp.getName());
		infoP.apendFubenInfo(temp.name, 0);
		
		scene = temp;
		/** 得到当前副本所有场景 */
		List<Scene> list = getRoundScene(x, y);
		MButton[] ary = mainFrame.getMapBuAry();
		/** 按照副本的地图进行布置 */
		for (int i = 0; i < ary.length; i++) {
			if (list.get(i) != null) {
				buTemp = ary[i];
				/** 设置每个自定义按钮类中所在地点信息对象 */
				buTemp.setScene(list.get(i));
				buTemp.setText(list.get(i).name);
				buTemp.setNum(i + 1);
				buTemp.setVisible(true);
			}
			
		}
		gameView.setCablesLoc();
		/** 0,1坐标的位置设置为起点 */
		ary[4].setCurBu();
		scene = ary[4].getScene();		
		mapP.repaint();
		List<MButton> buList = new ArrayList<>();
		setSceneNpc(buList);
	}

	
	/**
	 * 战略五步走
	 * 0: 先设置npc的品质
	 * 1: 根据npc的品质生成npc的属性	
	 * 2: 根据npc的品质生成npc的装备
	 * 3: 根据npc的等级与品质设定npc击杀经验与金钱
	 * 4: 计算npc装备带来的属性加成
	 * 5: 计算npc最后属性值
	 * @param npc
	 * @return
	 */
	public NPC npcMaker(NPC npc) {
		int rank = npc.getRank();
		int type = npc.getType();
		/** 设置npc的品阶 */
		npc.setType(DataCal.rdNpcType(player.getLucky()));
		/** 1: 根据npc的品质生成npc的属性 */
		setNpcBaseAttr(npc);
		/** 2: 根据npc的品质生成npc的装备 */
		equipControl.getWholeBodyEq(npc, rank, type);
		/** 3: 根据npc的等级与品质设定npc击杀经验与金钱 */
		int expValue = getNpcExp(player.getRank(), rank, type);
		npc.setCombatExp(expValue);// 设置击杀经验
		npc.setCombatMoney(DataCal.getNpcMoney(player.getRank(), rank, type));// 设置击杀金钱
		/** 4:计算装备带来的属性加成 */
		AddAttrs addAttrs = equipControl.newADdAttrs();
		Equip[] equipAry = npc.getEquipAry();
		for (int i = 0; i < equipAry.length; i++) {
			if (equipAry[i] != null && !SUtils.isNullStr(equipAry[i].getName())) {
				equipControl.countAttr(addAttrs, equipAry[i].getAttrList());
			}
		}
		/** 计算npc最后属性值 */
		equipControl.calNpcReallyAttr(npc, addAttrs);// 5
		return npc;
	}

	
	/**
	 * 重新设置npc的初始属性值
	 * @param npc
	 */
	public void setNpcBaseAttr(NPC npc){
		DataCal.setNpcBaseAttr(npc);
	}
	
	/**
	 * 得到npc提供的经验值
	 * @param rank
	 * @param type
	 */
	public int getNpcExp(int myRank,int foeRank,int type){
		int expValue = DataCal.getUpgradeExp(foeRank);
		if(foeRank>1){
			expValue -= DataCal.getUpgradeExp(foeRank-1);
		}else{
			expValue = 150 ;
		}
		int num = 3 ;
		num += foeRank*2;
		int exp = SUtils.reDouPoint((expValue/num)*C.npcExpLv[type]) ;
		
		/** 根据双方等级差距来调整经验值 越级有经验加成*/
		if(myRank-foeRank>10){
			exp*=0.1;
		}else{
			exp = SUtils.reDouPoint(exp*(1-(myRank-foeRank)*0.1));
		}
		return exp;
	}
	
	
	
	
	//private TimeController t = TimeController.getInstance();

	/** 将主界面的界面传输过来 */
	public void sendPanel(JPanel panelA, TTextPane panelB, JPanel panelC,
			TTextPane panelD, GameView gameView) {
		this.playerP = panelA;
		this.sceneP = panelB;
		this.functionP = panelC;
		this.infoP = panelD;
		this.gameView = gameView ;
		this.mapP = gameView.mapView;
		this.npcP = gameView.npcView;
		this.controlP = gameView.ctrlView;
	}

	/** 恢复对mainFrame窗口的限制 */
	public void restore() {
		mainFrame.setEnabled(true);
		mainFrame.requestFocus();
	}

	/** 
	 * 得到并储存当前选择的副本信息
	 * @param fuben
	 */
	public void setSelect(Ditu fuben) {
		this.curDitu = fuben;
		List<Scene> list = fuben.getScene();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(fuben.getScene().get(i).toString());
		}
		infoP.append("你选择了前往【", 0);
		infoP.append(fuben.getName(), 5);
		infoP.append("】\n", 0);

	}
	
	/**
	 * 通过场景的坐标来获得场景实体
	 * @param x 
	 * @param y
	 * @return 返回目标场景，不存在返回null
	 */
	public Scene getScene(int x, int y) {
		List<Scene> list = curDitu.getScene();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).x == x && list.get(i).y == y) {
				return list.get(i);
			}
		}
		List<Ditu> dituList = curDitu.getSpList();
		for (int i = 0; i < dituList.size(); i++) {
			list = dituList.get(i).getScene();
			for (int j = 0; j < list.size(); j++) {
				System.out.println("特殊场景:"+list.get(j).getName()+","+x+","+y);
				if (list.get(j).x == x && list.get(j).y == y) {
					return list.get(j);
				}
			}
		}
		return null;
	}


	/**
	 *  玩家点击每个场景时触发
	 *  0:播放声音
	 *  1:判断这个场景能否到达
	 *  2:如果可以，被点击按钮被按下，其他按钮恢复
	 *  3:人物的交互动作隐藏,如果该场景存在特殊功能
	 *  4:进入新场景，并刷新其他场景按钮
	 *  	是否进入特殊场景
	 *  5:刷新场景人物
	 *  	如果场景存在物品和特殊场景，一起显示
	 *  6:显示当前场景信息
	 */
	public ActionListener mapBuAc = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*for (int i = 0; i < curDitu.getSpList().size(); i++) {
				for (int j = 0; j < curDitu.getSpList().get(i).getScene().size(); j++) {
					Scene sce = curDitu.getSpList().get(i).getScene().get(j) ;
				}
			}*/
			MButton curBu = (MButton) e.getSource();
			Scene sc = curBu.getScene();
			playerMoved(sc, curBu, false);
		}
	};
	
	/**
	 * *  玩家点击每个场景时触发
	 *  0:判断这个场景能否到达
	 *  1:如果可以，被点击按钮被按下，其他按钮恢复
	 *  2:播放声音
	 *  3:人物的交互动作隐藏,如果该场景存在特殊功能
	 *  4:进入新场景，并刷新其他场景按钮
	 *  	是否进入特殊场景
	 *  5:刷新场景人物
	 *  	如果场景存在物品和特殊场景，一起显示
	 *  6:显示当前场景信息
	 * @param sc
	 * @param curBu
	 * @param flag
	 */
	public void playerMoved(Scene sc, MButton curBu,boolean flag) {
		/** 0-判断该场景是否能到达,特殊场景不用检查  */
		if(!flag) {
			boolean b = (sc.x == scene.x - 1 && sc.y == scene.y) || (sc.x == scene.x + 1 && sc.y == scene.y)
					||  (sc.x == scene.x && sc.y == scene.y - 1) || (sc.x == scene.x && sc.y == scene.y + 1);
			List<Point> points = scene.points ;
			for (int i = 0; i < points.size(); i++) {
				if(sc.x == points.get(i).x && sc.y == points.get(i).y) {
					b = false ;
				}
			}
			if (!b) {
				curBu.mouseExited();
				return;
			}
		}
		/** 1-可以到达的场景,更新当前场景,设置每个按钮点击状态 */
		scene = sc;
		/** 如果是特殊出口，那么实际出去位置为出口制定位置 */
		if(scene.isExit) {
			scene = getScene(sc.exitPoint.x, sc.exitPoint.y) ;
		}
		mainFrame.hideMapBu();
		/** 2-说明可以到达，那么播放声音 */
		SoundControl.ftMuc(26);
		/** 3-隐藏交互按钮 */
		gameView.hideAcBu();
		/** 4-重新布置场景 **/
		// 得到x,y坐标
		int x = scene.x;
		int y = scene.y;
		System.out.println("当前点击的场景坐标:"+x+","+y);
		List<Scene> list = getRoundScene(x, y);
		MButton temp = null;
		/** 得到场景移动用到的按钮,显示在面板上 */
		MButton[] ary = mainFrame.getMapBuAry();
		for (int i = 0; i < ary.length; i++) {
			if (list.get(i) != null) {
				temp = ary[i];
				temp.setScene(list.get(i));
				temp.setText(list.get(i).name);
				temp.setNum(i + 1);
				temp.setVisible(true);
			}
		}
		gameView.setCablesLoc();
		/** 设置当前所在场景按钮状态 */
		for (int j = 0; j < ary.length; j++) {
			if (ary[j].getNum() == 5) {
				ary[j].setCurBu();
			}
		}
		/** 5-显示副本存在人物，物品，特殊入口 */
		List<MButton> buList = new ArrayList<>() ;
		buList = setSceneSpDestn(buList);//场景内特殊入口
		buList = setSceneItem(buList);
		buList = setSceneNpc(buList);//场景内存在的人物
		/** 设置按钮位置 */
		setSceneCellPos(buList);
		/** 6-显示场景信息 */
		setSceneTitle(sc.getName());
		sceneP.setText(sc.des);
	}
	
	private List<MButton> setSceneItem(List<MButton> buList) {
		
		
		
		
		return null;
	}

	/** 通过当前位置得到周围场景 */
	public List<Scene> getRoundScene(int x, int y){
		List<Scene> list = new ArrayList<>();
		System.out.println("当前点击的场景坐标:"+x+","+y);
		list.add(getScene(x - 1, y - 1));
		list.add(getScene(x, y - 1));
		list.add(getScene(x + 1, y - 1));
		list.add(getScene(x - 1, y));
		list.add(getScene(x, y));
		list.add(getScene(x + 1, y));
		list.add(getScene(x - 1, y + 1));
		list.add(getScene(x, y + 1));
		list.add(getScene(x + 1, y + 1));
		return list ;
	}
	
	/**
	 * 显示当前场景内存在的特殊入口
	 */
	public List<MButton> setSceneSpDestn(List<MButton> buList) {
		List<String> entrances = scene.enters ;
		if(entrances.size()<1)  return buList;
		MButton bu ;
		SpSceneLner spSceneLner ;
		for (int i = 0; i < entrances.size(); i++) {
			bu = new MButton(entrances.get(i).split(";")[0], 3);
			spSceneLner = new SpSceneLner(entrances.get(i));
			bu.addActionListener(spSceneLner);
			buList.add(bu);
		}
		return buList;
	}

	/***
	 * 显示当前副本中当前场景存在的npc
	 * 并为每一个npc设置一个按钮 
	 * 大小243, 99
	 */
	public List<MButton> setSceneNpc(List<MButton> buList) {
		gameView.removeNpc();
		buList = npcCtrl.setFubenNpc(scene, buList);
		return buList ;
	}

	/**
	 * 传入 一个储存了人物信息的按钮集合
	 * 设置它们的位置
	 * 为面板画线
	 * @param buList
	 *  大小243, 99 间隔为9
	 *  6,12  
	 */
	public void setSceneCellPos(List<MButton> buList) {
		npcP.removeAll();
		int inset = 6;
		for (int i = 0; i < buList.size(); i++) {
			int x = i % 4;
			int y = i / 4;
			buList.get(i).setBounds(inset+x*(72+inset), inset*2+y*(24+inset), 72, 24);
			npcP.add(buList.get(i));
		}
		npcP.repaint();
		npcP.revalidate();
	}
	
	/**
	 * 设置副本人物互动按钮位置|交互
	 * @param buList
	 */
	public void setFbNpcActionPos(List<MButton> buList) {
		controlP.removeAll();
		int inset = 9;
		for (int i = 0; i < buList.size(); i++) {
			int x = i % 3;
			int y = i / 3;
			buList.get(i).setVisible(true);
			buList.get(i).setLocation(inset+x*(buList.get(i).getWidth()+inset/2),inset+y*(buList.get(i).getHeight()+inset/2));
			buList.get(i).setSize();
			controlP.add(buList.get(i));
		}
		controlP.repaint();
		controlP.revalidate();
	}

	
	public void reloadpanelG(){
		controlP.removeAll();
		controlP.repaint();
		controlP.revalidate();
	}
	
	public EnterFrame getEnterFrame() {
		return enterFrame;
	}

	public void setEnterFrame(EnterFrame enterFrame) {
		this.enterFrame = enterFrame;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	
	
	
	public void setFightJpanel(JPanel fightJpanel){
		this.fightJpanel = fightJpanel ;
	}
	
	
	ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8);
	
	Object obj = new Object();
	
	private JPanel fightJpanel ; 
	private JPanel ft = null ;
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private int inset = 8 ;
	
	private TLabel myXue,myFa,foeXue,foeFa ;
	
	/**
	 * 战斗开始
	 * 未使用
	 */
	@Deprecated
	public void fightStart(NPC foe){
		if(ft==null){
			ft = new JPanel() ;
			ft.setBounds(0, 100, 545, 299);
			fightJpanel.add(ft);
			ft.setLayout(null);
		}
		/** 玩家的血条 */
		myXue = new TLabel("", 4);
		TLabel myXueK = new TLabel("", 5);
		myXue.setBounds(inset, 45, 100, 11);
		myXueK.setBounds(inset, 46, 150, 13);
		ft.add(myXue);
		ft.add(myXueK);
		/** 蓝条 */
		myFa = new TLabel("", 8);
		TLabel myFaK = new TLabel("", 5);
		myFa.setBounds(inset, myXue.getY()+14, 100, 11);
		myFaK.setBounds(inset, myXueK.getY()+13, 137, 13);
		ft.add(myFa);
		ft.add(myFaK);
		
		/** 敌人血条 */
		foeXue = new TLabel("", 6);
		TLabel foeXueK = new TLabel("", 7);
		foeXue.setBounds(ft.getWidth()-inset-150, 45, 100, 11);
		foeXueK.setBounds(ft.getWidth()-inset-150, 46, 150, 13);
		ft.add(foeXue);
		ft.add(foeXueK);
		/** 敌人法力条 */
		foeFa = new TLabel("", 8);
		TLabel foeFaK = new TLabel("", 5);
		foeFa.setBounds(ft.getWidth()-inset-137, foeXue.getY()+14, 100, 11);
		foeFaK.setBounds(ft.getWidth()-inset-137, foeXueK.getY()+13, 137, 13);
		ft.add(foeFa);
		ft.add(foeFaK);
		
		/** 设置背景图片 */
		JLabel back = new JLabel();
		ImageIcon image1 = SUtils.loadImageIcon("/game/img/back/1.png");
		back.setIcon(image1);
		ft.add(back);
		back.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());
		
		Graphics g = ft.getGraphics();
		g.setFont(new Font("隶书",Font.PLAIN,16));
		g.drawString("好人啊", 0, 10);
		
	}

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}
	
	/***
	 * 设置主游戏界面的人物属性
	 * @param player
	 */
	public void setAttrValue(Player player){
		JLabel[] attrAry = mainFrame.getAttrAry();
		for (int i = 0; i < attrAry.length; i++) {
			switch (i) {
			case 0:
				attrAry[0].setText(player.getName());
				break;
			case 1:
				attrAry[1].setText(player.getRank()+"");		
				break;
			case 2:
				attrAry[2].setText("凡人");
				break;
			case 3:
				attrAry[3].setText(player.getExp()+"");
				break;
			case 4:
				attrAry[4].setText(player.getHp()+"");
				break;
			case 5:
				attrAry[5].setText(player.getMp()+"");
				break;
			case 6:
				attrAry[6].setText(player.getAttack()+"");
				break;
			case 7:
				attrAry[7].setText(player.getDefense()+"");
				break;
			case 8:
				attrAry[8].setText(player.getName());
				break;
			default:
				break;
			}
		}
	}

	public String getArchiveName() {
		return archiveName;
	}

	public void setArchiveName(String archiveName) {
		System.out.println("存档名被设置为:"+archiveName);
		this.archiveName = archiveName;
	}
	
	/** 
	 * 创建新的玩家
	 * @param name
	 * @return
	 */
	public Archive createNewPlayer(String name){
		Archive theArchive = new Archive();
		theArchive.setName(name);
		/** 把时间设置为当前时间 */
		theArchive.setTime(System.currentTimeMillis());
		/** 初始化玩家所有任务 */
		theArchive.setTaskMap(taskMap);
		/** 新建存档和人物 , 初始赠送一些装备 */
		Player newPlayer = new Player() ;
		newPlayer.setName(name);
		Equip equip = null ;
		/** 生成默认基本装备 */
		for (int i = 0; i < 8; i++) {
			equip = equipControl.equipGenerate(1, i);
			if(equip!=null){
				theArchive.obtainEquip(equip);
				newPlayer.obtainEquip(equip);
			}
		}
		for (int i = 0; i < 8; i++) {
			equip = equipControl.equipGenerate(1, i);
			if(equip!=null){
				theArchive.wearEquip(equip, i);
				newPlayer.wearEquip(equip, i);
			}
		}
		setArchive(theArchive);
		setPlayer(newPlayer);
		
		/** 存档 */
		System.out.println("设置玩家名字是否成功:"+theArchive.getName());
		ArchiveUtils.saveArchiving(theArchive, archiveName);
		return theArchive ;
	}
	
	
	/** 主界面的初始化 */
	public void mainFrameInit(){
		/** 主界面人物属性面板的初始化 */
		mainFrame.reloadPlayerAttr();
	}
	

	public void reloadPlayerAttr(){
		mainFrame.reloadPlayerAttr();
	}
	
	public void dealFubenNpc(){
		
	}
	
	public SpFrame getFtFrame() {
		return ftFrame;
	}

	public void setFtFrame(SpFrame ftFrame) {
		this.ftFrame = ftFrame;
	}

	public NPC getFightNpc() {
		return fightNpc;
	}

	public void setFightNpc(NPC fightNpc) {
		this.fightNpc = fightNpc;
	}

	public Map<String, Equip> getEquipMap() {
		return equipMap;
	}

	public void setEquipMap(Map<String, Equip> equipMap) {
		this.equipMap = equipMap;
	}

	public MButton getTask() {
		return gameView.actionBu[1];
	}

	public void setTask(MButton tasks) {
		gameView.actionBu[1] = tasks;
	}

	public MButton[] getAction() {
		return gameView.actionBu ;
	}

	public void setAction(MButton[] action) {
		gameView.actionBu = action;
	}
	
	/**
	 * 对talk动作的处理
	 * @param str
	 */
	public void dealTalkReq(String str){
		str = str.trim();
		String[] actionList = str.split("\\|") ;
		int reNum = 0 ;
		/** 任务是否开启的标志 */
		boolean flag = true ;
		for (int i = 0; i < actionList.length; i++) {
			if(!actionList[i].startsWith("remove")){
				boolean temp = isFullCase(actionList[i]);
				flag = flag&&temp ;
			}
		}
		/** 不满足任务条件则退出 */
		if(!flag){
			return ;
		}
		/** 满足任务条件则移除必须的物品  remove:type:id:num */
		String type = "" ;
		String id = "" ;
		int num = 0 ;
		for (int i = 0; i < actionList.length; i++) {
			/** 判断移除装备的数量 */
			if(actionList[i].startsWith("remove")){
				/** 开始移除装备 */
				String[] tempAry = actionList[i].split(":");
				type = tempAry[1] ;
				id = tempAry[2] ;
				num = SUtils.conStrtoInt(tempAry[3]);
				switch (type) {
				case "equip":/** 移除num数量id的装备 */
					
					break;
				case "cailiao":/** 移除num个id材料 */
									
					break;
				case "pet":/** 移除num个id宠物 */
					
					break;

				default:
					break;
				}
			}
		}
		
		
		
		
	}
	
	/**
	 * 判断是否满足任务开启的条件
	 * 如果开启人物需要道具，那么就去掉
	 * 1:满足属性
	 * 2:满足物品
	 * 3:满足前置任务
	 * 4:直接true
	 * @param str
	 * @return
	 */
	public boolean isFullCase(String str){
		str = str.trim();
		String[] strAry = null ;
		if(str.startsWith("attr")){
			/** attr:
			 * 
			 */
			strAry = str.split(":");
			String attrName = strAry[1] ;
			int value = SUtils.conStrtoInt(strAry[2]);
			switch (attrName) {
			case "rank":
				if(player.getRank()>value){
					return true ;
				}
				break;
			case "atk":
				if(player.getAttack()>value){
					return true ;
				}
				break;
			case "def":
				if(player.getDefense()>value){
					return true ;
				}
				break;
			default:
				break;
			}
			
		}else if(str.startsWith("Item")){
			/** item:equip:id:num  装备
			 *  item:pet:id:num 宠物
			 *  item:cailiao 材料
			 *  item:skillBook 技能书
			 */
			strAry = str.split(":");
			String itemName = strAry[1] ;
			String id = strAry[2] ;
			int num = SUtils.conStrtoInt(strAry[3]) ;
			switch (itemName) {
			case "equip":
				return isExistEquip(id);
			case "pet":
							
				break;
			case "cailiao":
				return isExistCailiao(id, num);
			case "skillBook":
				
				break;
			default:
				break;
			}
		}else if(str.startsWith("task")){
			/** 需要满足前置任务 task:id:curState */
			strAry = str.split(":");
			String id = strAry[1] ;
			int curState = SUtils.conStrtoInt(strAry[2]) ;
			Tasks task = player.getCurTasksList().get(id);
			if(task.getCurState()==curState){
				return true ;
			}else{
				return false ;
			}
		}else if(str.equals("true")){
			return true ;
		}
		return false; 
	}
	
	
	
	
	
	
	/**
	 * 查看某件装备是否存在玩家背包
	 * @param id
	 * @return
	 */
	public boolean isExistEquip(String id){
		List<Equip> equipList = player.getEquipBag();
		for (int i = 0; i < equipList.size(); i++) {
			if(equipList.get(i).getId().equals(id)){
				return true ;
			}
		}
		return false ;
	}
	
	/**
	 * 查看玩家背包是否存在一定的数量的某材料
	 * @param id 材料id
	 * @param num 材料数量
	 * @return
	 */
	public boolean isExistCailiao(String id,int num){
		List<Material> cailiaoList = player.getMaterialBag();
		for (int i = 0; i < cailiaoList.size(); i++) {
			if(cailiaoList.get(i).getId().equals(id)){
				if(cailiaoList.get(i).getNum()>=num){
					return true ;
				}
			}
		}
		return false ;
	}

	public JPanel getPanelA() {
		return playerP;
	}

	public void setPanelA(JPanel panelA) {
		this.playerP = panelA;
	}

	public JPanel getPanelC() {
		return functionP;
	}

	public void setPanelC(JPanel panelC) {
		this.functionP = panelC;
	}
	
	/**
	 * 判断背包空间是否满了
	 * 装备空间，材料空间，技能书空间
	 * @return
	 */
	public boolean isBagFull(){
		int num = player.getEquipBag().size() ;
		num += player.getMaterialBag().size() ;
		num += player.getGongBag().size();
		if(num>player.getBagSize()){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 扩大背包的大小|极其珍贵
	 * 36 49 64 81 100 
	 * 	  2  4  8  10
	 */
	public boolean expandBagSize(){
		/** 判断条件 */
		
		return false ;
	}

	public JLayeredPane getLayeredPanel() {
		return layeredPanel;
	}

	public void setLayeredPanel(JLayeredPane layeredPanel) {
		this.layeredPanel = layeredPanel;
	}
	
	/**
	 * 输入功法ID
	 * 查看玩家是否已经学会这门功法
	 * @param id
	 * @return
	 */
	public boolean hasLean(String id){
		if(SUtils.isNullStr(id)){
			return false ; 
		}
		List<Gong> list = player.getHasLeaNeiGongs() ;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(id)){
				return true ;
			}
		}
		list = player.getHasLeaWaiGongs() ;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(id)){
				return true ;
			}
		}
		return false ;
	}
	
	
	public Gong getGongByID(String id){
		return (Gong)ArchiveUtils.depthClone(gongMap.get(id));
	}
	
	/*******************************
	 * 	动作解析
	 ******************************/
	public void setSceneAc(Scene scene) {
		
	}
	
	
	
	
	
	public MButton psCmdToBu(String type, String name, String value){
		switch (type) {
		case "eat":

			break;
		case "ask":

			break;
		case "c":

			break;
		case "get":

			break;
		case "stduy":

			break;
		case "buy":

			break;
		case "move":

			break;
		case "say":
			
			break;

		default:
			break;
		}
		
		
		return null ;
	}
	
	/**
	 * 把 [说]指令变为按钮
	 * @param name
	 * @param value
	 * @return
	 */
	public MButton psSayCmd(String name, String value) {
		MButton bu = new MButton(name, 33);
		String[] theMsgs = value.split(";");
		bu.addActionListener(new AcLner(theMsgs) {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(index == msgs.length) {
					index = 0 ;
				}
				append(forMatMsg(msgs[index]), 0);
				index++;
			}
		});
		return null ;
	}
	
	/**
	 * 把 [说]指令变为按钮
	 * @param name
	 * @param value
	 * @return
	 * <e type="eat" name="食用" value="$say:xxx;$eff:" />
	 */
	public MButton psEatCmd(String name, String value) {
		MButton bu = new MButton(name, 33);
		String[] ary = value.split(";");
		bu.addActionListener(new AcLner(ary) {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < msgs.length; i++) {
					if(msgs[i].startsWith("$say:")) {
						String msg = msgs[i].substring(6);
						msg = forMatMsg(msg);
						append(msg, 0);
					}
					if(msgs[i].startsWith("$eff:")) {
						String msg = msgs[i].substring(5);
					}
				}
			}
		});
		return bu ;
	}
	
	public String forMatMsg(String str) {
		str = str.replaceAll("$N", player.getName());
		str = str.replaceAll("$n", npcCtrl.npc.getName());
		return str ;
	}
	
	
	/****************************************************************
	 *                     输出游戏中信息                                                                                      *
	 ****************************************************************/
	/**
	 * 当功能关闭时，输出对应信息
	 * @param type
	 */
	public void funCloseInfo(int type){
		if(type == C.State){
			append("你不再查看自己的【",0);
		}else if(type == C.Bag){
			append("你关上了【",0);
		}else{
			SoundControl.jiemianMuc("closeMap"); 
			append("你犹豫了会，还是决定先不去【",0);
		}
		append(C.funAry[type], 1);
		append("】！\n", 0);
	}
	
	/**
	 * 当功能开启时，输出对应信息
	 * @param type
	 */
	public void funOpenInfo(int type){
		if(type==C.State){
			append("你闭上双目，精心查看自己的【",0);
		}else if(type==C.Map){
			append("你稍作停顿，准备好好的看看【",0);
		}else if(type==C.Bag){
			append("你心神一动，便打开了【",0);
		}else if(type==C.Task){
			append("你正准备查看【",0);
		}else{
			append("你正准备进入【",0);
		}
		append(C.funAry[type], 1);
		append("】！\n", 0);
	}
	
	
	
	
	/**
	 * 初始化特殊窗口
	 * @param funListener
	 */
	public void initFunFrame(FunListener funListener){
		fubenFrame = new SpFrame(mainFrame, C.Fuben);
		stateFrame = new SpFrame(mainFrame, C.State);
		//fightFrame = new SpFrame(mainFrame, Constant.Fight);
		jiangHuFrame = new SpFrame(mainFrame, C.JiangHu);
		mapFrame = new SpFrame(mainFrame, C.Map);
		bagFrame = new SpFrame(mainFrame, C.Bag);
		taskFrame = new SpFrame(mainFrame, C.Task);
		funListener.initFunFrame(fubenFrame, stateFrame, fightFrame, jiangHuFrame, bagFrame, mapFrame, taskFrame);
	}

	public KeyMana getKeyMana() {
		return keyMana;
	}

	public void setKeyMana(KeyMana keyMana) {
		this.keyMana = keyMana;
	}
	
	
	
	
	
	
	
	
	
	
	/*****************************************************************
	 ********************       玩家与npc的交互               ****************
	 ****************************************************************/
	/**
	 * 把所有交互按钮设置为不启用
	 */
	public void initAcBustatus() {
		gameView.initAcBustatus();
	}
	

	public SpFrame getTaskFrame() {
		return taskFrame;
	}
	
	
	/*******************************************************************
	 * 							检查人物信息
	 *******************************************************************/
	 public boolean isHave(String[] ary) {
		 String id = ary[0] ;
		 int num = SUtils.conStrtoInt(ary[1]);
		 int bagNum = player.getItemNum(id);
		 if(num>bagNum) {
			 return false ;
		 }
		 return true ;
	 }
	
	
	
	
	
	/***********************************************************************************************
	 **********************************  控制主界面视图 
	 ***********************************************************************************************/
	public void setSceneTitle(String title) {
		sceneP.setBorder(BorderFactory.createTitledBorder(title));
	}
	
	public void removeNPC(JButton npc) {
		gameView.removeNpc(npc);
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public boolean isInScene() {
		return isInScene;
	}

	public void setInScene(boolean isInScene) {
		this.isInScene = isInScene;
	}
	
	
	public String getCurDituID() {
		return curDitu.getId();
	}

	public MyDialog getTan() {
		return tan;
	}

	public void setTan(MyDialog tan) {
		this.tan = tan;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
