package game.listener;

import game.control.EquipControl;
import game.control.FightControl;
import game.control.GameControl;
import game.control.SoundControl;
import game.entity.Equip;
import game.entity.Material;
import game.entity.NPC;
import game.entity.Player;
import game.entity.Scene;
import game.entity.Tasks;
import game.utils.Constant;
import game.utils.SUtils;
import game.view.button.MButton;
import game.view.frame.SpFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class NpcListener implements ActionListener{
	private Random rd = new Random() ;
	private GameControl gameControl = GameControl.getInstance();
	/** 战斗控制器 */
	private FightControl fightControl = FightControl.getInstance();
	private MButton view , kill  ;
	private List<MButton> buList = null  ;
	private List<MButton> actionList = null ;
	private SpFrame sp3 = null ;
	/** 显示人物的面板 */
	private JPanel panel = null ;
	
	private MButton curBu = null ;
	
	private Scene scene ;
	
	private NPC npc ;
	
	public NpcListener(JPanel panel,Scene scene) {
		this.panel = panel ;
		this.scene = scene ;
		fightControl.setNpcListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//npcActionAnalyze
		curBu = (MButton) e.getSource();
		npc = curBu.getNpc();
		/** 此时的npc实体类中只有基本数据 */
		showNpcAc(npc);
		//npcActionAnalyze(npc);
		
		System.out.println(npc.getName()+"被点击！");
		fightControl.setNpc(npc);
		appendNpcInfo(npc);
		for (int i = 0; i < buList.size(); i++) {
			//buList.get(i).setFlag();
			//buList.get(i).mouseExited();
		}
		curBu.mouseClicked();
		/** 用来包含npc的全部动作的集合 */
		actionList = new ArrayList<>();
		MButton[] actionBu = gameControl.getAction();
		for (int i = 0; i < actionBu.length; i++) {
			if(actionBu[i]!=null){
				actionList.add(actionBu[i]);
			}
		}
		
		/*view = new MButton("查看", 2);
		actionList.add(view);
		if(npc.isCankill()){
			kill = new MButton("击杀", 2);
			kill.addActionListener(killListener);
			actionList.add(kill);
		}*/
		gameControl.setFbNpcActionPos(actionList);
	}
	
	
	/**
	 * 移除不需要的按钮/移除被消灭的敌人
	 */
	public void remove(){
		if(curBu!=null){
			panel.remove(curBu);
			boolean flag = buList.remove(curBu);
			gameControl.setFubenNpcPos(buList);
			flag = scene.npcList.remove(npc);
			System.out.println("移出死亡npc"+flag);
			gameControl.reloadpanelG();
		}
	}
	
	/**
	 * 界面上设置各个交互按钮
	 * @param npc
	 */
	public void showNpcAc(NPC npc) {
		/* view, talk, kill, give, trading, tasks */
		MButton[] actionBu = gameControl.getAction();
		gameControl.initAcBustatus();
		
		/** 交流 */
		actionBu[1].used = true ;
		if(npc.getTasks().size()==0) {
			final String[] msgs = npc.getMsgs() ;
			if(actionBu[1].getActionListeners().length==0) {
				actionBu[1].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int num = rd.nextInt(msgs.length);		
						gameControl.append(msgs[num].trim()+"\n", 1);
					}
				});
			}
		}else {
			
		}
		
		if(npc.Cankill()) {
			actionBu[2].used = true ;
			/** 无监听则增加监听 */
			if(actionBu[2].getActionListeners().length==0) 
				actionBu[2].addActionListener(killListener);
		}
		
		if(npc.CanGive()) {
			actionBu[3].used = true ;
		}
		
		if(npc.CanSell()) {
			actionBu[4].used = true ;
			actionBu[4].addActionListener(tradListener);
		}
		gameControl.setAction(actionBu);
	}
	
	
	/***
	 * 在点击了副本场景中的npc之后，在信息面板中显示npc的信息
	 * @param npc
	 */
	public void appendNpcInfo(NPC npc){
		int type = npc.getType();
		if(type==0){
			type=5;
		}
		gameControl.append("\n"+npc.getName(), type+10);//品质颜色
		gameControl.append("【"+Constant.npcTypeDes[type]+"】", type+10);//品质颜色 npcTypeDes
		gameControl.append("lv"+npc.getRank()+"\n", 15);//黑色
		gameControl.append("hp:"+npc.getHp()+"  mp:"+npc.getMp()+"  atk:"+npc.getAttack()+"  def:"+npc.getDefense()+"\n",15);
		Equip[] equipAry = npc.getEquipAry() ;
		for (int i = 0; i < equipAry.length; i++) {
			if(equipAry[i]!=null&&equipAry[i].getName().length()>0){
				gameControl.append(Constant.partDes[i]+": ", 15);
				type = equipAry[i].getType()==0?5:equipAry[i].getType();
				gameControl.append(equipAry[i].getName()+"	", type+10);
			}
			if((i+1)%2==0){
				gameControl.append("\n", 1);
			}
			
		}
	}
	
	public void setBuList(List<MButton> buList) {
		this.buList = buList;
	}
	
	
	/**
	 * 解析人物的所有交互动作
	 * 查看         交谈         击杀         给予         交易                  任务
	 * view, talk, kill, give, trading, tasks
	 * @param npc
	 */
	public void npcActionAnalyze(final NPC npc){
		String id = npc.getId();
		SUtils SUtils = new SUtils() ;
		Document document = SUtils.load("game/xml/npc.xml");
		Node node = document.selectSingleNode("/root/npc[id='"+id+"']/action");
		Element action = null ;
		if(node==null){
			return ;
		}
		action = node.getParent().element("action");
		List<Element> actionList = action.elements("ac");
		/** <ac	type="sell" > type= actionName */
		String actionName = null ;
		String actionvalue = null ;
		System.out.println("正在加载"+npc.getName()+"的动作列表");
		for (int i = 0; i < actionList.size(); i++) {
			actionName = actionList.get(i).attributeValue("type");
			System.out.println(i+":"+actionName);
			/** 判断动作种类 */
			if(actionName.equals("kill")){//击杀
				actionvalue = actionList.get(i).getText().trim();
				//System.out.println("killValue:"+actionvalue);
				if(SUtils.conStrToBol(actionvalue)){//判断能否击杀
					npc.setCankill(true);
				}else{
					npc.setCankill(false);
				}
			}else if(actionName.equals("sell")){
				/** <item id="" type="" name="" num="" appear=""></item> */
				List<Element> acList = actionList.get(i).elements();
				String itemId,itemName,appear,itemType = null ;
				List<Object> goods = null;
				int itemNum = 0 , appearLv = 0 ;
				for (int j = 0; j < acList.size(); j++) {
					itemId = acList.get(i).attributeValue("id") ;
					itemName = acList.get(i).attributeValue("name") ;
					itemNum = SUtils.conStrtoInt(acList.get(i).attributeValue("num")) ;
					if(id.equals("bestTrader")||id.equals("bestTrader")||id.equals("bestTrader")){
						if(id.equals("bestTrader")){//极品商人|卖装备和图纸和材料
							goods = EquipControl.getBestTraderGoods(itemNum);
						}else if(id.equals("petTrader")){//售卖宠物蛋
							goods = EquipControl.getPetTraderGoods(itemNum);
						}else if(id.equals("skillTrader")){//售卖技能书
							goods = EquipControl.getSkillTraderGoods(itemNum);
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
			}else if(actionName.equals("talk")){/** 谈话|触发任务 */
				/**
				 * <ac type="talk">
				 *	<!-- 交谈触发任务，苏醒  -->
				 *	task:101
				 *	</ac>
				 */
				actionvalue = actionList.get(i).getText().trim();
				String[] info = actionvalue.split(":");
				if(info[0].equals("task")){
					gameControl.getTaskByNaId(info[1]);
					Tasks task =  gameControl.getPlayer().getCurTasksList().get(info[1]);
					if(task!=null){
						
					}
				}
			}
		}
		/*** 得到所有动作按钮 */
		MButton[] actionBu = gameControl.getAction();
		System.out.println(npc.getName()+"能否击杀?"+npc.Cankill());
		if(npc.Cankill()){
			actionBu[2] = new MButton("战斗", 2);
			/** 添加击杀动作 */
			actionBu[2].addActionListener(killListener);
		}
		
		
		
		actionBu[0] = new MButton("查看", 2) ;
		/**** 交谈设置 ***/
		actionBu[1] = new MButton("交谈", 2);
		actionBu[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] msg = npc.getMsg().split("\\|");
				int num = new Random().nextInt(msg.length) ;
				gameControl.append(msg[num].trim()+"\n", 1);
			}
		});
		gameControl.setAction(actionBu);
		
	}
	
	
	ActionListener killListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int type = npc.getType()==0?5:npc.getType();
			gameControl.append("你决定向【", 0);
			gameControl.append(Constant.npcTypeDes[npc.getType()]+"】"+npc.getName(), 10+type);
			gameControl.append("发起挑战!\n", 0);
			gameControl.append("战斗开始!\n", 0);
			/** 在点击时，已经将npc信息和战斗面板放入战斗控制器 */
			if(sp3==null){
				sp3 = new SpFrame(gameControl.getMainFrame(), 3);
				fightControl.setFtFrame(sp3);
			}else{
				System.out.println("再次打开战斗面板");
				Player player = gameControl.getPlayer();
				player.setCurHp(player.getHp());
				fightControl.getFtFrame().setVisible(true);
				sp3.reload(3);
			}
		}
	};
	
	ActionListener tradListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameControl.append("进行【", 0);
			gameControl.append("交易", 5);
			gameControl.append("】!\n", 0);
			List<Object> list = npc.getSellList() ;
			/** 显示商人交易的物品 */
		}
	};
			
	
}
