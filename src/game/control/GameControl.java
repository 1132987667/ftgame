package game.control;

import game.entity.AddAttrs;
import game.entity.Archive;
import game.entity.Ditu;
import game.entity.Equip;
import game.entity.Gong;
import game.entity.Material;
import game.entity.NPC;
import game.entity.Player;
import game.entity.Scene;
import game.entity.Tasks;
import game.listener.NpcListener;
import game.utils.ArchiveUtils;
import game.utils.Constant;
import game.utils.DataCal;
import game.utils.SUtils;
import game.view.TLabel;
import game.view.TTextPane;
import game.view.button.MButton;
import game.view.frame.EnterFrame;
import game.view.frame.MainFrame;
import game.view.frame.SpFrame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.swing.DefaultButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

/** 控制各个组件 */
public class GameControl {
	private EnterFrame enterFrame;
	private MainFrame mainFrame;
	private JLayeredPane layeredPanel ;
	/**
	 * 人物面板 房间描述 功能 游戏信息 小地图 npc与物品 与npc或物品交互
	 */
	private JPanel playerP = null;
	private TTextPane sceneP = null;
	private JPanel functionP = null;
	private TTextPane infoP = null;

	private JPanel mapP = null;
	private JPanel npcP = null;
	private JPanel controlP = null;

	private static GameControl gameControl = new GameControl();
	/** 没有选择 空存档 存在存档 */
	public static final int NotSelect = -1;
	public static final int NewArchive = 0;
	public static final int OldArchive = 1;

	public Map equipBag = new HashMap<>();
	
	private Random rd = new Random(System.currentTimeMillis());

	private String archiveName;

	/** 游戏信息 */
	private Ditu curDitu = null;
	/** 当前场景 */
	private Scene scene;
	
	
	private Map<String, Equip> equipMap = null ;
	private  Map<String,NPC> npcMap = null ;
	private Map<String,Tasks> taskMap =null ;
	private Map<String,Gong> gongMap =null ;
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
		equipMap = SUtils.loadEquip();
		npcMap = SUtils.loadNpc();
		taskMap = SUtils.loadTask();
		gongMap = SUtils.loadGong();
	}
	
	/** 当前存档 */
	private Archive archive = null ;
	/** 当前玩家实体类 */
	private Player player = null ;
	/** 当前与之战斗的npc */
	private NPC fightNpc = null ;
	
	
	private MButton view, talk, kill, give, trading, tasks ;
	private MButton[] actionBu = {view, talk, kill, give, trading, tasks} ;
	
	
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
	
	
	/** 在信息面板上显显示字符串 */
	public void append(String str, int type) {
		infoP.append(str, type);
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
		if (type == 1) {
			infoP.append("你犹豫了会，还是决定先不去【副本】了！\n", 0);
		}else if(type == 2){
			infoP.append("你不再查看自己的【状态】!\n", 0);
		}
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
		return taskMap.get(id);
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
	 * 通过id得到装备对象
	 * @param id
	 * @return
	 */
	public Equip getEquipById(String id){
		Equip equip = equipMap.get(id);
		return (Equip) ArchiveUtils.depthClone(equip);
	}
	
	/**
	 * 加载剧情xml
	 * @return
	 */
	public List<Ditu> loadJuqing() {
		Ditu ditu = null ;
		Document document = SUtils.load("src/game/xml/juqing.xml");
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
	public Ditu loadJuqing(String jqID){
		Ditu ditu = null ;
		Document document = SUtils.load("src/game/xml/juqing.xml");
		Element root = document.getRootElement();
		Element curJuqing = null ;
		List<Element> temp  = root.elements();
		for (int i = 0; i < temp.size(); i++) {
			if(temp.get(i).attribute("id").getText().equals(jqID)){
				curJuqing = temp.get(i);
				break;
			}
		}
		String jqId,jqName,jqDes = null ;
		if(curJuqing==null){
			return null ;
		}
		jqId = jqID ;
		jqName = curJuqing.elementText("name");
		jqDes = curJuqing.elementText("des");
		ditu = new Ditu(jqId, jqName, jqDes, 0, 0);
		
		String name,des,pos  = null;
		Scene scene = null ;
		List<Element> mapTemp = curJuqing.element("map").elements();
		System.out.println("剧情"+jqId+"共有"+mapTemp.size()+"个场景");
		
		/** 场景集合 */
		List<Scene> sceneList = new ArrayList<>();
		for (int j = 0; j < mapTemp.size(); j++) {
			name = mapTemp.get(j).element("name").getText();
			des = mapTemp.get(j).element("des").getText();
			pos = mapTemp.get(j).element("pos").getText();
			String[] tempAry = pos.split(",");
			/** x,y坐标 */
			int x = SUtils.conStrtoInt(tempAry[0]);
			int y = SUtils.conStrtoInt(tempAry[1]);
			scene = new Scene(name, des, x, y);
			String npcStr = mapTemp.get(j).element("npcList").getText();
			// 得到副本中对应的怪物
			if(npcStr.trim().length()>0){
				scene.setNpcList(getNPCList(npcStr));
			}
			scene.setNpcStr(npcStr);
			System.out.println(x + ":" + y + ", 该场景怪物数量:" + scene.getNpcList().size());
			sceneList.add(scene);
		}
		ditu.setScene(sceneList);
		curDitu = ditu ;
		return ditu ;
	}
	
	
	/**
	 * 加载副本信息,并得到和设置npc信息
	 * @return 返回资料库中所有的npc的信息
	 */
	public List<Ditu> loadFuben(){
		List<Ditu> list = new ArrayList<>();
		Document document = SUtils.load("src/game/xml/fuben.xml");
		/** 获取根目录 */
		Element root = document.getRootElement();
		List<Element> temp  = root.elements();
		for (int i = 0; i < temp.size(); i++) {
			if(temp.get(i).attribute("name").getText().equals("副本")){
				temp = temp.get(i).elements() ;
				break;
			}
		}
		Element map = null ;
		String id,name,des,rankL,rankR  = null;
		Scene scene = null ;
		List<Element> mapTemp = null ;
		for (int i = 0; i < temp.size(); i++) {
			id = temp.get(i).attribute("id").getText();
			name = temp.get(i).element("name").getText();
			des = temp.get(i).element("des").getText();
			rankL = temp.get(i).element("rankL").getText();
			rankR = temp.get(i).element("rankR").getText();
			Ditu fuben = new Ditu(id, name, des, SUtils.conStrtoInt(rankL), SUtils.conStrtoInt(rankR));
			/** 不是每个副本都加了地图信息 */
			map = temp.get(i).element("map") ;
			if(map!=null){
				mapTemp = map.elements() ;
				System.out.println("加载得到"+mapTemp.size()+"个场景");
				for (int j=0;j < mapTemp.size(); j++) {
					name =  mapTemp.get(j).element("name").getText();
					des = mapTemp.get(j).element("des").getText();
					int x =  SUtils.conStrtoInt(mapTemp.get(j).element("x").getText());
					int y = SUtils.conStrtoInt(mapTemp.get(j).element("y").getText());
					scene = new Scene(name,des,x,y) ;
					String str = mapTemp.get(j).element("npcList").getText();
					//得到副本中对应的怪物
					scene.npcList = getNPCList(str);
					scene.npcStr = str ;
					System.out.println(x+":"+y +", 该场景怪物数量:"+fuben.getList().size());
					fuben.getScene().add(scene);
				}
			}
			list.add(fuben);
		}
		return list ;
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
	 * 设置当前副本所有npc的具体信息
	 * 此时npc已有信息 name rank 
	 * 每次进入都会重新设置
	 * @param fuben 副本
	 */
	public void setNpcSpecInfo(Ditu fuben){
		System.out.println("设置副本npc信息!"+fuben);
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
		int exp = SUtils.reDouPoint((expValue/num)*Constant.npcExpLv[type]) ;
		
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
			TTextPane panelD, JPanel panelE, JPanel panelF, JPanel panelG) {
		this.playerP = panelA;
		this.sceneP = panelB;
		this.functionP = panelC;
		this.infoP = panelD;
		this.mapP = panelE;
		this.npcP = panelF;
		this.controlP = panelG;
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
	private Scene getScene(int x, int y) {
		List<Scene> list = curDitu.getScene();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).x == x && list.get(i).y == y) {
				return list.get(i);
			}
		}
		return null;
	}

	/**
	 * 作用:生成副本的地图 
	 * 0,1 为中心
	 */
	public void createMap() {
		MButton buTemp = null;
		/** 得到当前副本所有场景 */
		List<Scene> list = new ArrayList<>();
		/** 重置场景 */
		mainFrame.setMapBuHide();
		/** 整套移出并重绘的流程 */
		npcP.removeAll();
		npcP.repaint();
		npcP.revalidate();
		Scene temp = null;
		temp = getScene(0, 1);
		infoP.apendFubenInfo(temp.name, 0);
		int x = 0, y = 1;
		scene = temp;
		list.add(getScene(x + 1, y - 1));
		list.add(getScene(x + 1, y));
		list.add(getScene(x + 1, y + 1));
		list.add(getScene(x, y - 1));
		list.add(getScene(x, y));
		list.add(getScene(x, y + 1));
		list.add(getScene(x - 1, y - 1));
		list.add(getScene(x - 1, y));
		list.add(getScene(x - 1, y + 1));
		MButton[] ary = mainFrame.getMapBuAry();
		/** 按照副本的地图进行布置 */
		for (int i = 0; i < ary.length; i++) {
			ary[i].removeActionListener(maoBuAc);
			if (list.get(i) != null) {
				buTemp = ary[i];
				/** 设置每个自定义按钮类中所在地点信息对象 */
				buTemp.setCurScene(list.get(i));
				buTemp.setText(list.get(i).name);
				buTemp.setNum(i + 1);
				sceneP.setText(list.get(i).des);
				buTemp.setVisible(true);
			}
			ary[i].addActionListener(maoBuAc);
		}
		/** 0,1坐标的位置设置为起点 */
		ary[4].mouseClicked();
		scene = ary[4].getCurScene();		
		mapP.repaint();
		setFubenNpc();
		
	}

	/**
	 * 玩家选择场景的监听
	 * 	1:控制玩家能选择的场景路径
	 * 	2:被点击时按钮切换形态，其他按钮回复形态
	 *  3:被点击时进入新场景
	 */
	ActionListener maoBuAc = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SoundControl.ftMuc(26);
			MButton curBu = (MButton) e.getSource();
			/** 得到当前点击按钮的场景 */
			Scene sc = curBu.getCurScene();
			System.out.println("怪物数量:" + sc.npcList.size());
			System.out.println("场景信息:" + sc.toString());
			System.out.println("要去的地点:" + sc.x + ":" + sc.y + " , 当前地点:"
					+ scene.x + " , " + scene.y);
			/** 过远的地点不可前往 */
			boolean b = (sc.x == scene.x - 1 && sc.y == scene.y)
					|| (sc.x == scene.x + 1 && sc.y == scene.y)
					|| (sc.x == scene.x && sc.y == scene.y - 1)
					|| (sc.x == scene.x && sc.y == scene.y + 1);
			if (!b) {
				// type15 true
				//curBu.setFlag();
				curBu.mouseExited();
				return;
			}
			System.out.println("按钮生效！");
			/** 更新当前场景 */
			scene = sc;
			/** 点击了正确的按钮时 */
			mainFrame.setMapBuHide();
			List<Scene> list = new ArrayList<>();
			int x = sc.x;
			int y = sc.y;
			// 得到x,y坐标
			MButton[] ary = mainFrame.getMapBuAry();
			list.add(getScene(x + 1, y - 1));
			list.add(getScene(x + 1, y));
			list.add(getScene(x + 1, y + 1));
			list.add(getScene(x, y - 1));
			list.add(getScene(x, y));
			list.add(getScene(x, y + 1));
			list.add(getScene(x - 1, y - 1));
			list.add(getScene(x - 1, y));
			list.add(getScene(x - 1, y + 1));
			MButton temp = null;
			for (int i = 0; i < ary.length; i++) {
				if (list.get(i) != null) {
					temp = ary[i];
					temp.setCurScene(list.get(i));
					temp.setText(list.get(i).name);
					temp.setNum(i + 1);
					sceneP.setText(list.get(i).des);
					temp.setVisible(true);
				}
			}
			for (int j = 0; j < ary.length; j++) {
				if (ary[j].getNum() == 5) {
					ary[j].mouseClicked();
				}
			}
			setFubenNpc();
		}
	};

	/***
	 * 显示当前副本中当前场景存在的npc
	 * 并为每一个npc设置一个按钮 
	 * 大小243, 99
	 */
	public void setFubenNpc() {
		/** 移除人物 */
		npcP.removeAll();
		npcP.repaint();
		npcP.revalidate();
		/** 得到当前场景内的人物 */
		List<NPC> npcList = scene.npcList;
		System.out.println("npcList的大小:" + npcList.size());
		if (npcList.size() < 1) {
			return;
		}
		List<MButton> buList = new ArrayList<>();
		NpcListener npcListener = new NpcListener(npcP,scene);
		MButton temp = null;
		/**
		 * 为场景内的每一个人物创建一个按钮
		 * 
		 */
		for (int i = 0; i < npcList.size(); i++) {
			NPC tempNpc = npcList.get(i);
			temp = new MButton(tempNpc.getName(), 3);
			temp.setForeground(Constant.equipColor[tempNpc.getType()]);
			temp.setNpc(tempNpc);
			temp.addActionListener(npcListener);
			/** 显示npc信息 */
			//panelD.append(tempNpc.getName(), tempNpc.getType());
			//panelD.append(tempNpc.getDes(), 0);
			buList.add(temp);
		}
		npcListener.setBuList(buList);
		setFubenNpcPos(buList);
	}

	/**
	 * 传入 一个储存了人物信息的按钮集合
	 * 设置它们的位置
	 * @param buList
	 *  大小243, 99 间隔为9
	 */
	public void setFubenNpcPos(List<MButton> buList) {
		System.out.println("正在重新设置场景内人物的位置!");
		npcP.removeAll();
		//ActionListener ac
		int inset = 9;
		for (int i = 0; i < buList.size(); i++) {
			int x = i % 3;
			int y = i / 3;
			buList.get(i).setBounds(inset + x * (72 + inset),
					inset + y * (24 + inset), 72, 24);
			npcP.add(buList.get(i));
		}
		npcP.repaint();
		npcP.revalidate();
	}
	
	/**
	 * 设置副本人物互动按钮位置
	 * @param buList
	 */
	public void setFbNpcActionPos(List<MButton> buList) {
		controlP.removeAll();
		int inset = 9;
		for (int i = 0; i < buList.size(); i++) {
			int x = i % 3;
			int y = i / 3;
			buList.get(i).setLocation(inset + x * (56 + inset),inset + y * (22 + inset));
			buList.get(i).setSize(56, 22);
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
		ImageIcon image1 = new ImageIcon("src/game/img/back/1.png");
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
	
	/***/
	public AddAttrs reloadAttr(){
		/** 根据当前人物装备重新计算  */
		Equip[] ary = player.getEquipAry();
		
		player.reloadBaseAttr();
		EquipControl equipControl = gameControl.equipControl;
		
		AddAttrs addAttrs = null ;
		/** 累加玩家装备带来的属性加成 */
		if(ary[0].getName().trim().length()>0){
			addAttrs = equipControl.countAttr(ary[0].getAttrList());
		}else{
			addAttrs = equipControl.newADdAttrs();
		}
		
		for (int i = 1; i < ary.length; i++) {
			if(ary[i].getName().trim().length()>0){
				addAttrs = equipControl.countAttr(addAttrs, ary[i].getAttrList());
			}
		}
		
		
		/** 玩家属性和装备加成属性相加 */
		equipControl.calReallyAttr(player, addAttrs);
		return addAttrs ;
	}

	public void reloadPlayerAttr(){
		mainFrame.reloadPlayerAttr();
	}
	
	public void dealFubenNpc(){
		
	}
	
	
	/**
	 * 解析人物的所有交互动作
	 * 查看         交谈         击杀         给予         交易                  任务
	 * view, talk, kill, give, trading, tasks
	 * @param npc
	 */
	public void npcActionAnalyze(final NPC npc){
		String id = npc.getId();
		Document document = SUtils.load("src/game/xml/npc.xml");
		Node node = document.selectSingleNode("/root/npc[id='"+id+"']/action");
		Element action ;
		if(node!=null){
			action = node.getParent().element("action");
		}
		actionBu[0] = new MButton("查看", 2) ;
		/**** 交谈设置 ***/
		actionBu[1] = new MButton("交谈", 2);
		actionBu[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] msg = npc.getMsg().split("\\|");
				int num = rd.nextInt(msg.length) ;
				append(msg[num], 1);
			}
		});
		
		
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
		return tasks;
	}

	public void setTask(MButton tasks) {
		this.tasks = tasks;
	}

	public MButton[] getAction() {
		return actionBu;
	}

	public void setAction(MButton[] action) {
		this.actionBu = action;
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
	 * 判断是否满足人物开启的条件
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
		return gongMap.get(id);
	}
	
	/**
	 * 当功能关闭时，输出对应信息
	 * @param type
	 */
	public void funCloseInfo(int type){
		if(type == Constant.State){
			append("你不再查看自己的【",0);
		}else if(type == Constant.Bag){
			append("你关上了【",0);
		}else{
			SoundControl.jiemianMuc("closeMap"); 
			append("你犹豫了会，还是决定先不去【",0);
		}
		append(Constant.funAry[type], 1);
		append("】！\n", 0);
		new DefaultButtonModel();
	}
	
	/**
	 * 当功能开启时，输出对应信息
	 * @param type
	 */
	public void funOpenInfo(int type){
		if(type==Constant.State){
			append("你闭上双目，精心查看自己的【",0);
		}else if(type==Constant.Map){
			append("你稍作停顿，准备好好的看看【",0);
		}else if(type==Constant.Bag){
			append("你心神一动，便打开了【",0);
		}else if(type==Constant.Task){
			append("你正准备查看【",0);
		}else{
			append("你正准备进入【",0);
		}
		append(Constant.funAry[type], 1);
		append("】！\n", 0);
	}
	
}
