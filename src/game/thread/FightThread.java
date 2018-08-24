package game.thread;

import game.control.FightControl;
import game.control.GameControl;
import game.control.SoundControl;
import game.entity.NPC;
import game.entity.Player;
import game.utils.SUtils;
import game.view.TLabel;
import game.view.TTextPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import test.SutilTest;

/**
 * 执行战斗任务的战斗线程
 * 
 * @author yilong22315
 * 
 */
public class FightThread implements Runnable {
	private GameControl gameControl = GameControl.getInstance();
	
	/** 战斗控制器 */
	private FightControl fightControl = FightControl.getInstance();
	private Object obj = null;
	private boolean flag = true;

	private boolean gameOverFlag = true;
	/** 敌我标志 */
	private int type = -1;
	private Graphics2D g = null;
	private Player player;
	private NPC npc;

	private Map map = null;
	/** 显示文字战斗信息的面板 */
	private TTextPane showInfo;
	// 敌我血条
	private TLabel myXue, foeXue;
	private int myCurHp, myHpOld, foeCurHp, foeHpOld;
	private String name , myName , foeName;

	private JLabel myTextHp, foeTextHp;

	// 两把剑
	private JLabel myJian, foeJian;
	private JLabel myDamage, foeDamage;

	private Font font1 = new Font("华文新魏", Font.PLAIN, 18);
	private Font font2 = new Font("华文新魏", Font.PLAIN, 20);
	private Font temp = null;

	public FightThread(Object obj, int type, Map map, TTextPane showInfo) {
		this.player = gameControl.getPlayer();
		this.npc = fightControl.getNpc();
		this.obj = map.get("obj");
		this.type = type;
		this.map = map;
		this.myXue = (TLabel) map.get("myXue");
		this.foeXue = (TLabel) map.get("foeXue");
		this.myTextHp = (JLabel) map.get("mtTextHp");
		this.foeTextHp = (JLabel) map.get("foeTextHp");
		this.showInfo = showInfo;
		this.myJian = (JLabel) map.get("myJian");
		;
		this.foeJian = (JLabel) map.get("foeJian");
		;
		this.myDamage = (JLabel) map.get("myDamage");
		this.foeDamage = (JLabel) map.get("foeDamage");
		myHpOld = player.getHp();
		foeHpOld = npc.getHp();
		name = type == 0 ? "您" : npc.getName();
		myName = player.getName();
		foeName = npc.getName();
	}

	/** 0友方 1敌方 20,白 21:红;22:深蓝;23:浅蓝;24:绿色;25:粉色 */
	@Override
	public void run() {
		isFightOver();
		map = SUtils.fightHelper(player, npc, type);
		String value = map.get("value").toString();
		// 伤害类型
		int atkType = SUtils.conObjtoInt(map.get("atkType"));
		// 伤害数值
		int atkDemage = SUtils.conObjtoInt(map.get("atkDemage"));
		/** 颜色 */
		Color color = atkType == 2 ? Color.red : Color.white;
		/** 字体 */
		temp = atkType == 2 ? font2 : font1;
		if (atkType != 3) {
			SoundControl.ftMuc(4);//挥剑动作 
			value = "-" + value;
		} else {
			SoundControl.ftMuc(5);//挥剑动作 
			value = "MISS";
		}
		String tempStr = "" ;
		int colorType =0 ;
		synchronized (obj) {
			tempStr = ifFun(type, "【我方】:", "【敌方】:");
			colorType = type==0?24:25;
			SwingUtilities.invokeLater(new InfoInput(tempStr, colorType));
			if (atkType == 3){
				tempStr = ifFun(type, foeName+"躲开了来自您的一次攻击\n","您躲开了来自"+foeName+"的一次攻击\n");
				SwingUtilities.invokeLater(new InfoInput(tempStr, 20));
			}else{
				if(type==0){
					System.out.println("我造成了"+atkDemage+"点伤害");
					foeCurHp = foeCurHp - atkDemage;
					// 敌人现在的血量和之前血量比较，分析现在血条的显示，然后重新设置敌人的血量
					foeXue.setSize(150 * foeCurHp / foeHpOld, 11);
					// foeXue.repaint();
					foeCurHp = foeCurHp > 0 ? foeCurHp : 0;
					npc.setCurHp(foeCurHp);
					foeTextHp.setText(foeCurHp + "/" + foeHpOld);
				}else{
					System.out.println("敌方造成了"+atkDemage+"点伤害");
					myCurHp = myCurHp - atkDemage;
					myXue.setSize(150 * myCurHp / myHpOld, 11);
					//myXue.repaint();
					//myXue.revalidate();
					myCurHp = myCurHp > 0 ? myCurHp : 0;
					myTextHp.setText(myCurHp + "/" + myHpOld);
					player.setCurHp(myCurHp);
				}
				if(type==0){
					if (atkType == 2){
						SwingUtilities.invokeLater(new InfoInput(name+"打出了一个暴击,给"+foeName+"造成了",25));
						SwingUtilities.invokeLater(new InfoInput("【"+value+"】",21));
					}else{
						SwingUtilities.invokeLater(new InfoInput(name +"给"+foeName+"造成了",23));
						SwingUtilities.invokeLater(new InfoInput("【"+value+"】",22));
					}
				}else{
					if (atkType == 2) {// 暴击
						SwingUtilities.invokeLater(new InfoInput(npc.getName()+"打出了一个暴击,给你造成了",25));
						SwingUtilities.invokeLater(new InfoInput("【"+value+"】",21));
					} else {// 普攻
						SwingUtilities.invokeLater(new InfoInput(npc.getName()+"给你造成了",23));
						SwingUtilities.invokeLater(new InfoInput("【"+value+"】",22));
					}
				}
				SwingUtilities.invokeLater(new InfoInput("点伤害\n", 23));
			}
			/** 打击效果展示  耗时 450 */
			if (type == 0) {
				myJian.setVisible(true);
				myJian.setLocation(270, 30);
				for (int i = 0; i < 5; i++) {
					myJian.setLocation(myJian.getX() + 6, myJian.getY() + 5);
					SUtils.sleep(40);
				}// 300 55
				/** 显示伤害数值 */
				myDamage.setForeground(color);
				myDamage.setFont(temp);
				myDamage.setText(value + "");
				myJian.setLocation(310, 55);
				myJian.setForeground(Color.red);
				myJian.setText("1");
				SUtils.sleep(250);
				myDamage.setText("");
				myJian.setVisible(false);
				// myJian.setLocation(212, 30);
			} else {
				foeJian.setVisible(true);
				foeJian.setLocation(174, 30);
				for (int i = 0; i < 5; i++) {
					foeJian.setLocation(foeJian.getX() - 6, foeJian.getY() + 5);
					SUtils.sleep(40);
				}
				/** 显示伤害数值 */
				foeDamage.setForeground(color);
				foeDamage.setFont(temp);
				foeDamage.setText(value + "");
				foeJian.setLocation(134, 55);
				foeJian.setForeground(Color.red);
				foeJian.setText("1");
				SUtils.sleep(250);
				foeDamage.setText("");
				foeJian.setVisible(false);
			}
			obj.notifyAll();
		}
		System.out.println("攻击结束！");
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setGameOverFlag(boolean gameOverFlag) {
		this.gameOverFlag = gameOverFlag;
	}

	class InfoInput implements Runnable {
		private String str;
		private int color;

		public InfoInput(String str, int color) {
			this.str = str;
			this.color = color;
		}

		@Override
		public void run() {
			showInfo.append(str, color);
		}

	}

	/**
	 * 判断此次战斗是否结束
	 * 
	 * @return
	 */
	public boolean isFightOver() {
		myCurHp = player.getCurHp();
		foeCurHp = npc.getCurHp();
		if (myCurHp <= 0) {
			return false;
		}
		if (foeCurHp <= 0) {
			return false;
		}
		return true;
	}
	
	public String ifFun(int type,String str1,String str2){
		if(type==0){
			return str1 ;
		}else{
			return str2 ;
		}
	}

}
