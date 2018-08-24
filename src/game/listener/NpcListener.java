package game.listener;

import game.control.FightControl;
import game.control.GameControl;
import game.entity.Equip;
import game.entity.NPC;
import game.entity.Player;
import game.entity.Scene;
import game.utils.Constant;
import game.view.button.MButton;
import game.view.frame.SpFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class NpcListener implements ActionListener{
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
		curBu = (MButton) e.getSource();
		npc = curBu.getNpc();
		System.out.println(npc.getName()+"被点击！");
		fightControl.setNpc(npc);
		appendNpcInfo(npc);
		for (int i = 0; i < buList.size(); i++) {
			buList.get(i).setFlag();
			//buList.get(i).mouseExited();
		}
		curBu.mouseClicked();
		/** 用来包含npc的全部动作的集合 */
		actionList = new ArrayList<>();
		view = new MButton("查看", 2);
		actionList.add(view);
		if(npc.isCankill()){
			kill = new MButton("击杀", 2);
			kill.addActionListener(killListener);
			actionList.add(kill);
		}
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
	
	
}
