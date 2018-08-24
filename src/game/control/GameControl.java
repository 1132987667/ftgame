package game.control;

import game.control.EquipControl.AddAttrs;
import game.entity.Archive;
import game.entity.Ditu;
import game.entity.Equip;
import game.entity.NPC;
import game.entity.Player;
import game.entity.Scene;
import game.listener.NpcListener;
import game.utils.ArchiveUtils;
import game.utils.Constant;
import game.utils.SUtils;
import game.view.FightTextArea;
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.dom4j.Document;
import org.dom4j.Element;

/** 控制各个组件 */
public class GameControl {
	private EnterFrame enterFrame;
	private MainFrame mainFrame;
	/**
	 * 人物面板 房间描述 功能 游戏信息 小地图 npc与物品 与npc或物品交互
	 */
	private JPanel panelA = null;
	private TTextPane panelB = null;
	private JPanel panelC = null;
	private TTextPane panelD = null;

	private JPanel panelE = null;
	private JPanel panelF = null;
	private JPanel panelG = null;

	private static GameControl gameControl = new GameControl();
	/** 没有选择 空存档 存在存档 */
	public static final int NotSelect = -1;
	public static final int NewArchive = 0;
	public static final int OldaArchive = 1;

	public Map equipBag = new HashMap<>();
	

	private String archiveName;

	/** 游戏信息 */
	private Ditu fuben = null;
	/** 当前场景 */
	private Scene scene;
	
	
	private Map<String, Equip> equipMap = null ;
	private  Map<String,NPC> npcMap = null ;
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
		//equipMap = SUtils.loadEquip();
		//npcMap = SUtils.loadNpc();
	}
	
	/** 当前存档 */
	private Archive archive = null ;
	/** 当前玩家实体类 */
	private Player player =null ;
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
	
	
	/** 在信息面板上显显示字符串 */
	public void append(String str, int type) {
		panelD.append(str, type);
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
			panelD.append("你犹豫了会，还是决定先不去【副本】了！\n", 0);
		}else if(type == 2){
			panelD.append("你不再查看自己的【状态】!\n", 0);
		}
		mainFrame.close(type);
	}

	/** 隐藏登陆面板 */
	public void hideEnterFrame() {
		System.out.println(enterFrame);
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
				return OldaArchive;
			}
		}
	}
	
	/** 
	 * 通过npc名字得到资料库中npc实体类的克隆体 
	 * @param name 要查询的npc的name
	 * @return 返回资料库中Npc实体类的克隆体
	 */
	public NPC getNpcByName(String name){
		System.out.println("npc总数:"+npcMap.size());
		NPC npc = npcMap.get(name);
		return  (NPC) ArchiveUtils.depthClone(npc);
	}
	
	/**
	 * 加载要去的剧情
	 * @param name
	 * @return
	 */
	public List<Ditu> loadJuqing(String jqID){
		int id = 0 ;
		List<Ditu> list = new ArrayList<>();
		Document document = SUtils.load("src/game/xml/juqing.xml");
		Element root = document.getRootElement();
		List<Element> temp  = root.elements();
		for (int i = 0; i < temp.size(); i++) {
			if(temp.get(i).attribute("id").getText().equals(jqID)){
				temp = temp.get(i).elements() ;
				id = SUtils.conStrtoInt(jqID);
				break;
			}
		}
		Element map = null ;
		String name,des,pos  = null;
		Scene scene = null ;
		List<Element> mapTemp = null ;
		/*** 加载每个场景的内容 */
		for (int i = 0; i < temp.size(); i++) {
			name = temp.get(i).element("name").getText();
			des = temp.get(i).element("des").getText();
			Ditu fuben = new Ditu(id,name, des,0,0);
			/** 不是每个副本都加了地图信息 */
			map = temp.get(i).element("map") ;
			if(map!=null){
				mapTemp = map.elements() ;
				System.out.println("加载得到"+mapTemp.size()+"个场景");
				for (int j=0;j < mapTemp.size(); j++) {
					name =  mapTemp.get(j).element("name").getText();
					des = mapTemp.get(j).element("des").getText();
					pos = mapTemp.get(j).element("pos").getText();
					String[] tempAry = pos.split(",");
					int x =  SUtils.conStrtoInt(tempAry[0]);
					int y = SUtils.conStrtoInt(tempAry[1]);
					scene = new Scene(name,des,x,y) ;
					String str = mapTemp.get(j).element("npcList").getText();
					//得到副本中对应的怪物
					scene.npcList = getNPCList(str);
					scene.npcStr = str ;
					System.out.println(x+":"+y +", 该场景怪物数量:"+fuben.list.size());
					fuben.scene.add(scene);
					
				}
			}
			list.add(fuben);
		}
		return null ;
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
		int id = 0 ;
		String name,des,rankL,rankR  = null;
		Scene scene = null ;
		List<Element> mapTemp = null ;
		for (int i = 0; i < temp.size(); i++) {
			id = SUtils.conStrtoInt(temp.get(i).attribute("id").getText());
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
					System.out.println(x+":"+y +", 该场景怪物数量:"+fuben.list.size());
					fuben.scene.add(scene);
					
				}
			}
			list.add(fuben);
		}
		return list ;
	}
	
	/**
	 * 传入存有npc名字和数量的特殊格式字符串
	 * 创建对应对象添加到list中
	 * @param str
	 * @return 返回包含场景内所悟npc信息的集合
	 */
	private List<NPC> getNPCList(String str){
		List<NPC> list = new ArrayList<>();
		String[] temp = null ;
		String npcName = null ;
		String[] ary = str.split(",");
		int num = 0 ;
		for (int i = 0; i < ary.length; i++) {
			temp = ary[i].split(":");
			npcName = temp[0];
			num = SUtils.conStrtoInt(temp[1]);
			NPC npc = getNpcByName(npcName);
			if(npc!=null){
				for (int j = 0; j < num; j++) {
					/** 和当前玩家的幸运值相关 */
					npc = (NPC) ArchiveUtils.depthClone(npc) ;
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
		List<Scene> sceneList = fuben.scene ;
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
	public NPC npcMaker(NPC npc){
		/** 设置npc的品质 */
		npc.setType(rdNpcType(player.getLucky()));
		/** 1 */
		getNpcBaseAttr(npc);
		/** 2 */
		equipControl.getWholeBodyEq(npc, npc.getRank(), npc.getType());
		/** 3 */
		int expValue = getNpcExp(player.getRank(),npc.getRank(), npc.getType()) ;
		npc.setCombatExp(expValue);//设置击杀经验
		npc.setCombatMoney(getNpcMoney(player.getRank(),npc.getRank(), npc.getType()));//设置击杀金钱
		/** 4:计算装备带来的属性加成 */
		AddAttrs addAttrs = equipControl.newADdAttrs();
		Equip[] equipAry = npc.getEquipAry();
		for (int i = 0; i < equipAry.length; i++) {
			if(equipAry[i]!=null&&!SUtils.isNullStr(equipAry[i].getName())){
				equipControl.countAttr(addAttrs,equipAry[i].getAttrList());
			}
		}
		/** 5 */
		equipControl.calNpcReallyAttr(npc, addAttrs);//5
		return npc ;
	}

	
	/** 把属性设为基本属性 */
	public void getNpcBaseAttr(NPC npc){
		int rank = npc.getRank() ;
		int type = npc.getType() ;
		npc.setTili(SUtils.reDouPoint(((15+rank*5)*Constant.npcAttrLv[type])));
		npc.setJingli(SUtils.reDouPoint(((15+rank*5)*Constant.npcAttrLv[type])));
		npc.setLi(SUtils.reDouPoint(((15+rank*5)*Constant.npcAttrLv[type])));
		npc.setMin(SUtils.reDouPoint(((15+rank*5)*Constant.npcAttrLv[type])));
		npc.setLucky(SUtils.reDouPoint(((10+rank)*Constant.npcAttrLv[type])));
		
		npc.setHp(SUtils.reDouPoint(((10*npc.getTili())*Constant.npcAttrLv[type])));
		npc.setCurHp(npc.getHp());
		npc.setMp(SUtils.reDouPoint(((2.5*npc.getJingli())*Constant.npcAttrLv[type])));
		npc.setMp(npc.getMp());
		npc.setAttack(SUtils.reDouPoint(((4*npc.getLi()-35)*Constant.npcAttrLv[type])));
		npc.setDefense(SUtils.reDouPoint(((npc.getMin())*Constant.npcAttrLv[type])));
		
		npc.setBaoji(SUtils.reDouPoint(((npc.getLucky()*0.4)*Constant.npcAttrLv[type])));
	}
	
	/**
	 * 得到一定等级下升级所需要的经验
	 * @param rank
	 */
	public static int getUpgradeExp(int rank){
		int expValue = (int) Math.floor(((rank-1)*(rank-1)*20)/5*(rank+50));
		return expValue; 
	}
	
	/**
	 * 得到npc提供的经验值
	 * @param rank
	 * @param type
	 */
	public int getNpcExp(int myRank,int foeRank,int type){
		int expValue = getUpgradeExp(foeRank);
		if(foeRank>1){
			expValue -= getUpgradeExp(foeRank-1);
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
	
	/**
	 * 得到不同品质npc掉落金钱
	 * @param rank
	 * @param type
	 * @return
	 */
	public int getNpcMoney(int myRank,int foeRank,int type){
		int money = foeRank*100 ;
		money*=type+1 ;
		money/=5;
		if(myRank-foeRank>10){
			money*=0.1;
		}else{
			money = SUtils.reDouPoint(money*(1-(myRank-foeRank)*0.1));
		}
		return money ;
	}
	
	/**
	 * 根据玩家幸运值得到遇见怪物品质
	 * @param lucky
	 * @return
	 */
	public int rdNpcType(int lucky){
		int npcType = 0 ;
		int type0 = 40 ;
		int type1 = 32 ;
		int type2 = 16 ;
		int type3 = 8 ;
		int type4 = 4 ;
		int lv = lucky/25 ;
		if(lv<=4){
			type4 = type4 + 2*lv ;
			type3 = type3 + 2*lv ;
			type2 = type2 + 2*lv ;
			type1 = type1 + 2*lv ;
			type0 = 100 - type1 - type2 - type3 - type4 ;
		}else{
			type0 = 8 ;
			type4 = type4 + 2*lv ;
			type3 = type3 + 2*lv ;
			type2 = type2 + 2*lv ;
			type1 = 100 - type0 - type2 - type3 - type4 ;
		}
		int rdNum = new Random().nextInt(100)+1;
		if (rdNum<type4) {
			npcType = 4;
		} else if (type4<rdNum&&rdNum<type3+type4) {
			npcType = 3;
		} else if (type4+type3<rdNum&&rdNum<type3+type4+type2) {
			npcType = 2;
		} else if (type4+type3+type2<rdNum&&rdNum<type3+type4+type2+type1) {
			npcType = 1;
		}else{
			npcType = 0;
		}
		return npcType;
	}
	
	private JTextArea jta = FightTextArea.getInstance(5, 30);
	private TimeController t = TimeController.getInstance();

	/** 将主界面的界面传输过来 */
	public void sendPanel(JPanel panelA, TTextPane panelB, JPanel panelC,
			TTextPane panelD, JPanel panelE, JPanel panelF, JPanel panelG) {
		this.panelA = panelA;
		this.panelB = panelB;
		this.panelC = panelC;
		this.panelD = panelD;
		this.panelE = panelE;
		this.panelF = panelF;
		this.panelG = panelG;
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
		this.fuben = fuben;
		List<Scene> list = fuben.scene;
		for (int i = 0; i < fuben.scene.size(); i++) {
			System.out.println(fuben.scene.get(i).toString());
		}
		panelD.append("你选择了前往【", 0);
		panelD.append(fuben.name, 5);
		panelD.append("】\n", 0);

	}
	
	/**
	 * 通过场景的坐标来获得场景实体
	 * @param x 
	 * @param y
	 * @return 返回目标场景，不存在返回null
	 */
	private Scene getScene(int x, int y) {
		List<Scene> list = fuben.scene;
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
		panelF.removeAll();
		panelF.repaint();
		panelF.revalidate();
		Scene temp = null;
		temp = getScene(0, 1);
		panelD.apendFubenInfo(temp.name, 0);
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
				panelB.setText(list.get(i).des);
				buTemp.setVisible(true);
			}
			ary[i].addActionListener(maoBuAc);
		}
		/** 0,1坐标的位置设置为起点 */
		ary[4].mouseClicked();
		scene = ary[4].getCurScene();		
		panelE.repaint();
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
				curBu.setFlag();
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
					panelB.setText(list.get(i).des);
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
		panelF.removeAll();
		panelF.repaint();
		panelF.revalidate();
		/** 得到当前场景内的人物 */
		List<NPC> npcList = scene.npcList;
		System.out.println("npcList的大小:" + npcList.size());
		if (npcList.size() < 1) {
			return;
		}
		List<MButton> buList = new ArrayList<>();
		NpcListener npcListener = new NpcListener(panelF,scene);
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
		panelF.removeAll();
		//ActionListener ac
		int inset = 9;
		for (int i = 0; i < buList.size(); i++) {
			int x = i % 3;
			int y = i / 3;
			buList.get(i).setBounds(inset + x * (72 + inset),
					inset + y * (24 + inset), 72, 24);
			panelF.add(buList.get(i));
		}
		panelF.repaint();
		panelF.revalidate();
	}
	
	/**
	 * 设置副本人物互动按钮位置
	 * @param buList
	 */
	public void setFbNpcActionPos(List<MButton> buList) {
		panelG.removeAll();
		int inset = 9;
		for (int i = 0; i < buList.size(); i++) {
			int x = i % 3;
			int y = i / 3;
			buList.get(i).setLocation(inset + x * (56 + inset),inset + y * (22 + inset));
			buList.get(i).setSize(56, 22);
			panelG.add(buList.get(i));
		}
		panelG.repaint();
		panelG.revalidate();
	}

	
	public void reloadpanelG(){
		panelG.removeAll();
		panelG.repaint();
		panelG.revalidate();
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
		/** 新建存档和人物 , 初始赠送一些装备 */
		Player newPlayer = new Player() ;
		Equip equip = null ;
		/** 生成默认基本装备 */
		for (int i = 0; i < 8; i++) {
			equip = equipControl.equipGenerate(1, i);
			if(equip!=null){
				System.out.println("生成的背包装备:"+i+","+equip.toString());
				theArchive.obtainEquip(equip);
				newPlayer.obtainEquip(equip);
			}
		}
		for (int i = 0; i < 8; i++) {
			equip = equipControl.equipGenerate(1, i);
			if(equip!=null){
				System.out.println("已穿上装备:"+equip.toString());
				theArchive.wearEquip(equip, i);
				newPlayer.wearEquip(equip, i);
			}
		}
		for (int i = 0; i < newPlayer.getEquipBag().size(); i++) {
			System.out.println("正在检查人物背包:"+theArchive.getEquipBag().get(i).toString());
		}
		
		gameControl.setArchive(theArchive);
		gameControl.setPlayer(newPlayer);
		
		/** 存档 */
		ArchiveUtils.saveArchiving(theArchive, archiveName);
		return theArchive ;
	}
	
	
	/** 主界面的初始化 */
	public void mainFrameInit(){
		/** 主界面人物属性面板的初始化 */
		mainFrame.reloadPlayerAttr();
	}
	
	
	/** 隐藏战斗页面，显示及背包页面  */
	public void openBag(){
		mainFrame.openBag();
	}
	
	/** 隐藏背包页面  */
	public void closeBag(){
		mainFrame.closeBag();
	}
	
	/** 开始探索 */
	public void beganExplore(){
		mainFrame.beganExplore();
	}
	
	/***/
	public AddAttrs reloadAttr(){
		/** 根据当前人物装备重新计算  */
		Equip[] ary = player.getEquipAry();
		
		player.setBaseAttr();
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
	 * 得到人物的出手速度
	 * @param li
	 * @param min
	 * @return 返回出手所需要等待的时间 单位:ms
	 */
	public int getAtkSpeed(int li,int min){
		int speed = SUtils.reDouPoint(2500*(1/(1+(0.5*li+min)*0.0067))) ;
		if(speed<500){
			speed = 500 ;
		}
		return speed ;
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
	
}
