package game.thread;

import game.control.EquipControl;
import game.control.FightControl;
import game.control.GameControl;
import game.control.SoundControl;
import game.entity.Equip;
import game.entity.NPC;
import game.entity.Player;
import game.utils.Constant;
import game.utils.SUtils;
import game.view.TLabel;
import game.view.TTextPane;
import game.view.panel.FtPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** 唤醒线程 */
public class AwakenThread extends Thread{
	private GameControl gameControl = GameControl.getInstance();
	
	/** 战斗控制器 */
	private FightControl fightControl = FightControl.getInstance();
	
	private Object obj = null ;
	/** 每个战斗人物的出手时间 */
	private List<Integer> speed = new ArrayList<>();
	/** 每个战斗人物已等待时间 */
	private List<Integer> waitTime = new ArrayList<>();
	/** 每个战斗人物代表的战斗线程 */
	public List<FightThread> ftList = new ArrayList<>();
	
	private Player player = null ;
	private NPC npc = null ;
	private int myCurHp  , foeCurHp ;
	//两把剑
	/** 储存信息的map */
	private Map<String, Object> map = null;
	
	/** 显示文字战斗信息的面板 */
	private TTextPane showInfo;
	
	/** 每次攻击都会花费一些时间 */
	private final int fightTime = 400 ;
	
	private TLabel mySu, foeSu;
	
	/**
	 * 
	 * @param myShow
	 * @param map 我的血条，法力条，敌方的血条，法力条，敌方实体类，我方实体类
	 * @param showInfo 文字显示战斗信息的面板
	 * @param ftPanel 战斗面板
	 */
	public AwakenThread(JPanel myShow, Map<String, Object> map,TTextPane showInfo,FtPanel ftPanel){
		this.player = gameControl.getPlayer();
		this.npc = fightControl.getNpc();
		this.map = map;
		this.obj = map.get("obj");
		this.showInfo = showInfo;
		this.mySu = (TLabel)map.get("mySu");
		this.foeSu = (TLabel)map.get("foeSu");
		speed.add(gameControl.getAtkSpeed(player.getLi(), player.getMin()));
		speed.add(gameControl.getAtkSpeed(npc.getLi(), npc.getMin()));
		waitTime.add(0);
		waitTime.add(0);
	}
	
	
	/** 20,白 21:红;22:深蓝;23:浅蓝;24:绿色;25:粉色 ; */
	@Override
	public void run() {
		showInfo.append(" 战斗开始!\n你和", 20);
		showInfo.append("【"+Constant.npcTypeDes[npc.getType()]+"】"+npc.getName(), 21);
		showInfo.append("只能活下一人!\n", 20);
		SoundControl.ftMuc(0);//拔刀动作 
		SUtils.writeFtLog(npc.npcInfo());
		while(isFightOver()){
			for (int i = 0; i < speed.size(); i++) {
				//System.out.println("我方血量:" + myHp + ",敌方血量:" + foeHp);
				waitTime.set(i,waitTime.get(i)+50 ) ;
				if(i==0){
					mySu.setSize((int) (101*(waitTime.get(i)*1.0/(speed.get(i)-fightTime))), 7);
				}else{
					foeSu.setSize((int) (101*(waitTime.get(i)*1.0/(speed.get(i)-fightTime))), 7);
				}
				/** 减去人物攻击所花费的时间 */
				if(waitTime.get(i)>(speed.get(i)-fightTime)){
					/** 如果出手时间满足条件，那么出手，出手时间重新计算 */
					if(!isFightOver()){
						break ;
					}
					synchronized (obj) {
						if(i==0){
							new Thread(new FightThread(obj, 0, map,showInfo)).start();;
							//singleThreadPool.execute(new FightThread(obj, 0, player,null));
						}else{
							new Thread(new FightThread(obj, 1, map,showInfo)).start();
							//singleThreadPool.execute(new FightThread(obj, 1, null,npc));
						}
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						waitTime.set(i, waitTime.get(i)-speed.get(i)+fightTime);
					}
				}
			}
			//SUtils.showThread();
			/** 时间流逝 */
			SUtils.sleep(50);
		}
		if(player.getCurHp()>0){
			showInfo.append("你取得了战斗的胜利!\n", 24);
			gameControl.append("你取得了战斗的胜利!\n", 3);
			fightControl.setVictorySign(true);
		}else{
			showInfo.append("你惨败在【"+Constant.npcTypeDes[npc.getType()]+"】"+npc.getName()+"手上!\n", 25);
			gameControl.append("你惨败在【"+Constant.npcTypeDes[npc.getType()]+"】"+npc.getName()+"手上!\n", 2);
			fightControl.setVictorySign(false);
		}
		fightControl.battleOver();
	}
	
	/**
	 * 判断此次战斗是否结束
	 * @return
	 */
	public boolean isFightOver(){
		myCurHp = player.getCurHp();
		foeCurHp = npc.getCurHp();
		if(myCurHp<=0){
			return false ;
		}
		if(foeCurHp<=0){
			return false ;
		}
		return true ;
	}
	
}
