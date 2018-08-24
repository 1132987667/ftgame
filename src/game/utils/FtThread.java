package game.utils;

import game.entity.NPC;
import game.entity.Player;
import game.view.TLabel;
import game.view.TTextPane;
import game.view.panel.FtPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FtThread implements Runnable {
	/** 用于同步的对象 */
	private Object obj = null;
	private Graphics g = null;
	/** 判断我方攻击还是对方攻击 */
	private int type = 0;
	private Player player = null;// 玩家
	private NPC npc = null;// 小怪
	private Font f = new Font("隶书", Font.PLAIN, 16);
	private JPanel info , myShow ;
	/** 战斗窗口 */
	private FtPanel ftPanel ;
	private Map<String, Object> map = null;
	private TLabel myXue, foeXue;
	private int myHp , myHpOld , foeHp , foeHpOld;
	private TTextPane showInfo;
	private String name;

	// 用来显示伤害数组的JPanel
	public FtThread(int type, JPanel myShow, Map<String, Object> map,
			TTextPane showInfo,FtPanel ftPanel) {
		this.type = type;
		this.player = (Player) map.get("player");
		this.npc = (NPC) map.get("npc");
		this.g = myShow.getGraphics();
		this.map = map;
		this.info = (JPanel) map.get("info");
		this.obj = map.get("obj");
		this.myXue = (TLabel) map.get("myXue");
		this.foeXue = (TLabel) map.get("foeXue");
		this.showInfo = showInfo;
		this.myShow = myShow ;
		this.ftPanel = ftPanel ;
		myHpOld = player.getHp();
		foeHpOld = npc.getHp();
		name = type == 1 ? "您" : npc.getName();
	}

	@Override
	public void run() {
		myHp = player.getHp();
		foeHp = npc.getHp();
		if (myHp > 0 && foeHp > 0) {
			System.out.println("我方血量:" + myHp + ",敌方血量:" + foeHp);
			info.repaint();
			map = SUtils.fightHelper(player, npc, type);
			String value = map.get("value").toString();
			System.out.println(map.get("atkType")+":"+SUtils.conObjtoInt(map.get("atkType")));
			int atkType = SUtils.conObjtoInt(map.get("atkType"));
			System.out.println(map.get("atkDemage")+":"+SUtils.conObjtoInt(map.get("atkDemage")));
			int atkDemage = SUtils.conObjtoInt(map.get("atkDemage"));
			System.out.println("伤害数值是:" + atkDemage + ",类型是:"+ atkType);
			System.out.println("---------------------------------");
			Color color = atkType == 2 ? Color.red
					: Color.white;
			if (atkType!=3) {
				value = "-" + value;
			} else {
				value = "MISS";
			}
			g.setColor(color);
			if (atkType == 1) {
				g.setFont(new Font("楷体", Font.BOLD, 20));
			} else {
				g.setFont(new Font("楷体", Font.BOLD, 18));
			}
			SwingUtilities.invokeLater(new DrawThread(g, value, type));
			synchronized (obj) {
				if (type == 1) {
					SwingUtilities.invokeLater(new infoInput("【我方】:", 14));
					// 为3时表示攻击无效,Miss
					if (atkType == 3) {
						SwingUtilities.invokeLater(new infoInput(npc.getName()+"躲开了来自您的一次攻击\n", 16));
					} else {
						foeHp = foeHp - atkDemage ;
						//敌人现在的血量和之前血量比较，分析现在血条的显示，然后重新设置敌人的血量
						foeXue.setSize(150 * foeHp / foeHpOld, 11);
						foeXue.repaint();
						foeHp = foeHp > 0 ? foeHp : 0;
						npc.setHp(foeHp);
						
						if (atkType==2) {// 暴击
							SwingUtilities.invokeLater(new infoInput(name
									+ "打出了一个暴击,给" + npc.getName() + "造成了", 11));
							SwingUtilities.invokeLater(new infoInput("【"
									+ value + "】", 12));
						} else {// 普攻
							SwingUtilities.invokeLater(new infoInput(name + "给"
									+ npc.getName() + "造成了", 11));
							SwingUtilities.invokeLater(new infoInput("【"
									+ value + "】", 14));
						}
						SwingUtilities.invokeLater(new infoInput("点伤害\n", 11));
					}
				} else if(type == 2){
					SwingUtilities.invokeLater(new infoInput("【敌方】:", 15));
					if (atkType == 3) {
						SwingUtilities.invokeLater(new infoInput("您躲开了来自"+npc.getName()+"的一次攻击\n", 16));
					} else {
						myHp = myHp - atkDemage;
						myXue.setSize(150 * myHp / myHpOld, 11);
						myXue.repaint();
						myHp = myHp > 0 ? myHp : 0;
						player.setHp(myHp);
						if (atkType == 2) {// 暴击
							SwingUtilities.invokeLater(new infoInput(npc.getName()
									+ "打出了一个暴击,给你造成了", 11));
							SwingUtilities.invokeLater(new infoInput("【"
									+ value + "】", 12));
						} else {// 普攻
							SwingUtilities.invokeLater(new infoInput(npc.getName()
									+ "给你造成了", 11));
							SwingUtilities.invokeLater(new infoInput("【"
									+ value + "】", 14));
						}
						SwingUtilities.invokeLater(new infoInput("点伤害\n", 11));
					}
				}
			}
		}else{
			if(myHp>0){
				SwingUtilities.invokeLater(new infoInput("你取得了战斗的胜利!", 13));
			}else{
				SwingUtilities.invokeLater(new infoInput("你惨败在"+npc.getName()+"的手上!", 12));
			}
			myShow.repaint();
		}
		/*
		 * if(npc.getHp()<0){ SwingUtilities.invokeLater(new DrawThread(g, 250,
		 * 100, "你取得了胜利!",type)); }else{ SwingUtilities.invokeLater(new
		 * DrawThread(g, 250, 100, "你输了!",type)); }
		 */
	}

	class infoInput implements Runnable {
		private String str;
		private int color;

		public infoInput(String str, int color) {
			this.str = str;
			this.color = color;
		}

		@Override
		public void run() {
			showInfo.append(str, color);
		}

	}

}
