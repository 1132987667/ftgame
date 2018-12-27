package game.control;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import game.entity.Equip;
import game.entity.Gong;
import game.entity.Item;
import game.entity.Material;
import game.entity.NPC;
import game.entity.Scene;
import game.entity.Tasks;
import game.listener.ObjLner;
import game.utils.C;
import game.utils.SUtils;
import game.view.button.MButton;
 
public class NpcCtrl {
	
	private GameControl gameControl = GameControl.getInstance() ;
	
	/** npc.xml的根节点 */
	private Element root ;
	/** npc.xml文件的文档对象 */
	private Document document = SUtils.load("game/xml/npc.xml");
	
	private NpcCtrl() {
	}
	
	private static NpcCtrl npcCtrl = new NpcCtrl();
	
	public static NpcCtrl getInstance() {
		return npcCtrl ;
	}
	
	public NPC npc = null ;
	
	/** 
	 * 设置npc详细信息
	 * @param npc
	 */
	private void parsingSelf(NPC npc) {
		String ngStr = SUtils.strTrim(npc.getNgStr());
		String wgStr = SUtils.strTrim(npc.getWgStr());
		npc.setHasLeaNGs(psGongStr(ngStr, npc)); 
		npc.setHasLeaWGs(psGongStr(wgStr, npc));
	}
	
	/** 
	 * 批量设置npc详细信息
	 * @param npcs
	 */
	public  void batchPS(List<NPC> npcs) {
		for (NPC npc : npcs) {
			parsingSelf(npc);
		}
	}
	
	/**
	 * 功法信息转为功法实体
	 * @param gongStr
	 * @param npc
	 * @return
	 */
	public List<Gong> psGongStr(String gongStr, NPC npc) {
		List<Gong> gongs = new ArrayList<>();
		String[] gStr = gongStr.split(";");
		for (int i = 0; i < gStr.length; i++) {
			String[] info = gStr[i].split(":") ;
			if(info.length!=3) {
				throw new IllegalArgumentException(npc.getId()+"功法"+info[1]+"设定缺少参数");
			}
			String id = info[0] ;
			String status = info[2] ;
			int tier = 0 ;
			Gong gong = gameControl.getGongByID(id);
			switch (status) {
			case "S": tier = gong.getMaxTier(); break;
			case "A": tier = gong.getMaxTier()*3/5+1; break;
			case "B": tier = gong.getMaxTier()/3+1; break;
			case "C": tier = gong.getMaxTier()/5+1; break;
			default: break;
			}
			gong.setCurTier(tier);
			gongs.add(gong);
		}
		return gongs ;
	}
	
	
	/**
	 * 解析人物的所有交互动作
	 * 查看         交谈         击杀         给予         交易                  任务
	 * view, talk, kill, give, trading, tasks
	 * @param npc
	 */
	public void setNpcAc(NPC npc){
		String id = npc.getId();
		this.npc = npc ;
		Node node = document.selectSingleNode("/root/npc[id='"+id+"']/ac");
		if(node==null) return ;
		List<Element> acList = node.getParent().elements("e");
		System.out.println("正在加载"+npc.getName()+"的动作列表");
		for (int i = 0; i < acList.size(); i++) {
			Element e = acList.get(i);
			String type = e.attributeValue("type");
			/** 判断动作种类 */
			if(type.equals("kill")){//击杀
				String acValue = e.getText().trim();
				//判断能否击杀
				npc.setCankill(SUtils.conStrToBol(acValue));
			}else if(type.equals("trad")){
				psTradStr(id, e, i);
			}else if(type.equals("task")){/** 谈话|触发任务 */
			   /** <ac type="task"><!-- 交谈触发任务，苏醒  -->
				 *	task:102
				 * </ac> */
				List<Tasks> task = psTaskStr(e.getText().trim(), npc) ;
				npc.setTasks( task );
			}
		}
		/*** 得到所有动作按钮 */
		/*
		MButton[] actionBu = gameControl.getAction();
		System.out.println(npc.getName()+"能否击杀?"+npc.isCankill());
		if(npc.isCankill()){
			actionBu[2] = new MButton("战斗", 2);
			*//** 添加击杀动作 *//*
			actionBu[2].addActionListener(killListener);
		}
		
		
		
		actionBu[0] = new MButton("查看", 2) ;
		*//**** 交谈设置 ***//*
		actionBu[1] = new MButton("交谈", 2);
		actionBu[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] msg =  .getMsg().split("\\|");
				int num = new Random().nextInt(msg.length) ;
				gameControl.append(msg[num].trim()+"\n", 1);
			}
		});
		gameControl.setAction(actionBu);*/
	}
	
	
	/**
	 * 解析交易信息
	 * @param id
	 * @param ele
	 * @param i
	 */
	public void psTradStr(String id, Element ele, int i) {
		/** <item id="" type="" name="" num="" appear=""></item> */
		List<Element> itemList = ele.elements();
		String appear,itemType = null ;
		List<Object> goods = null;
		int appearLv = 0 ;
		
		for (int j = 0; j < itemList.size(); j++) {
			Element e = itemList.get(i) ;
			String itemId = e.attributeValue("id") ;
			String itemName = e.attributeValue("name") ;
			int itemNum = SUtils.conStrtoInt(itemList.get(i).attributeValue("num")) ;
			if(id.equals("bestTrader")||id.equals("bestTrader")||id.equals("bestTrader")){
				if(id.equals("bestTrader")){//极品商人|卖装备和图纸和材料
					goods = EquipControl.getBestTraderGoods(itemNum);
				}else if(id.equals("petTrader")){//售卖宠物蛋
					goods = EquipControl.getPetTraderGoods(itemNum);
				}else if(id.equals("skillTrader")){//售卖技能书
					goods = EquipControl.getSkillTraderGoods(itemNum);
				}
				appear = itemList.get(i).attributeValue("appear") ;
				//npc.setAppearMode(appear);
				if(appear.equals("lvAppear")){//当商人为随机出现时才会有随即出现率
					appearLv = SUtils.conStrtoInt(itemList.get(i).attributeValue("appearLv")) ;
					//npc.setAppearLv(appearLv);
				}
			}else{
				//普通商人，储存货物信息 <item id="101" itemType="equip" name="长剑" num="1" type="2,3" ></item>
				itemType = itemList.get(i).attributeValue("itemType");
				if(itemType.equals("equip")){//装备
					Equip equip = gameControl.getEquipMap().get(id);
					equip.setType(2);
					//npc.getSellList().add(equip);
				}else if(itemType.equals("cailiao")){//材料
					Material mat = null ;
				}else if(itemType.equals("pet")){//宠物蛋
					
				}else if(itemType.equals("drawings")){//图纸
					
				}else if(itemType.equals("skillBook")){//技能书
					
				}
			}
		}
	}
	
	/**
	 * 解析任务信息
	 * @param taskStr
	 * @param npc
	 * @return
	 */
	public List<Tasks> psTaskStr(String taskStr, NPC npc){
		List<Tasks> taskList = new ArrayList<>();
		String[] tasks = taskStr.split(";");
		System.out.println(npc.getName()+"身上任务信息:"+taskStr+",一共"+tasks.length+"个任务");
		for (int i = 0; i < tasks.length; i++) {
			/** 102;103 */
			String id = tasks[i];
			Tasks task = gameControl.getTaskByNaId(id);/** 可重置任务从gameControl中获得 */
			System.out.println("加载任务:"+task.getTaskName());
			if(task.getType().equals(TaskCtrl.once_task)) {
				task =  gameControl.getPlayer().getCurTasksList().get(id);
			}
			if(task!=null){
				taskList.add(task);
			}
		}
		return taskList;
	}
	
	/**
	 * 通过等级得到某基础属性的值
	 * @param attrName
	 * @param rank
	 */
	public static void getBaseAttr(String attrName, int rank) {
		switch (attrName) {
		case "hp":
			
			break;

		default:
			break;
		}
	}
	
	/***
	 * 显示和布置场景内的物品和人物
	 * @param scene
	 */
	public List<MButton> setFubenNpc(Scene scene, List<MButton> buList) {
		ObjLner npcListener = new ObjLner(scene);
		/** 布置场景内的npc */
		System.err.println(scene);
		List<NPC> npcList = scene.npcList;
		if (npcList==null || npcList.size() < 1)  return buList ;
		MButton bu = null;
		/**
		 * 为场景内的每一个人物或物品 创建一个按钮
		 */
		for (int i = 0; i < npcList.size(); i++) {
			NPC npc = npcList.get(i);
			bu = new MButton(npc.getName(), 3);
			if(npc.getName().length()==5) {
				bu.setFont(C.Kai_M);
			}else if(npc.getName().length()>5) {
				bu.setFont(C.Kai_S);
			}
			bu.setForeground(C.equipColor[npc.getType()]);
			bu.setNpc(npc);
			bu.addActionListener(npcListener);
			/** 显示npc信息 */
			//panelD.append(tempNpc.getName(), tempNpc.getType());
			//panelD.append(tempNpc.getDes(), 0);
			buList.add(bu);
		}
		
		/** 布置场景内的物品 */
		List<Item> itemList = scene.getItemList();
		if(itemList.size()<1) return buList;
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			bu = new MButton(item.getName(), 3);
			if(item.getName().length()==5) {
				bu.setFont(C.Kai_M);
			}else if(item.getName().length()>5) {
				bu.setFont(C.Kai_S);
			}
			bu.setForeground(C.equipColor[item.getQua()]);
			bu.addActionListener(npcListener);
			buList.add(bu);
		}
		
		npcListener.setBuList(buList);
		return buList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
