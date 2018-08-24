package game.listener;

import game.control.EquipControl;
import game.control.GameControl;
import game.control.SoundControl;
import game.entity.Archive;
import game.entity.Equip;
import game.entity.Player;
import game.utils.ArchiveUtils;
import game.utils.SUtils;
import game.view.frame.MainFrame;
import game.view.frame.SpFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;


public class GreatListener implements ActionListener{
	private MainFrame f  ;
	private GameControl gameControl ;
	private SpFrame sp1,sp2,sp3,sp4 = null ;
	private Archive archive = null ;
	private Player player ;
	//private Clip bagOpen = SoundControl.jiemianMuc("openBag");
	//private Clip openMap = SoundControl.jiemianMuc("openMap");
	//private Clip bu = SoundControl.jiemianMuc("bu");
	
	/** 背包打开标志 */
	private boolean bagFlag = false ;
	
	public GreatListener(MainFrame mainFrame,Archive archive,Player player) {
		this.f = mainFrame ;
		this.player = player ;
		this.archive = archive ;
		gameControl = GameControl.getInstance();
	}
	/**
	 * SpFrame类型
	 * 副本 sp1 1
	 * 状态 sp2 2
	 * 背包 sp3 3
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand() ;
		System.out.println(str);
		archive = SUtils.conPlayerToArc(player);
		//System.out.println("存档ing:"+archive.toString());
		ArchiveUtils.saveArchiving(archive, gameControl.getArchiveName());
		int type = 0 ;
		switch (str) {
		case "副本":
			SoundControl.jiemianMuc("openMap"); 
			gameControl.append("你正准备进入【",0);
			gameControl.append("副本", 1);
			gameControl.append("】！\n", 0);
			type = 1 ;
			if(sp1==null){
				sp1 = new SpFrame(f,1);
			}
			/** 关闭背包 */
			gameControl.closeBag();
			bagFlag = false ;
			sp1.setVisible(true);
			sp1.reload(1);
			break;
		case "背包":
			SoundControl.jiemianMuc("openBag"); 
			/** true为打开，按下则关闭 */
			if(bagFlag){
				gameControl.append("你关上了【背包】！\n", 0);
				gameControl.beganExplore();
				bagFlag = false ;
			}else{
				gameControl.append("你打开了【背包】！\n", 0);
				gameControl.openBag();
				bagFlag = true ;
			}
			type = 1 ;
			break;
		case "状态":
			SoundControl.jiemianMuc("bu"); 
			//SUtils.sleep(bu.getMicrosecondLength()/1000);
			gameControl.append("你想查看自己的【状态】！\n", 0);
			type = 2 ;
			if(sp2==null){
				sp2 = new SpFrame(f,2);
			}
			sp2.setVisible(true);
			sp2.reload(2);
			break;
		case "存档":
			type = 1 ;
			break;
		case "地图":
			List<Equip> temp = new ArrayList<>();
			EquipControl equipControl = gameControl.equipControl;
			for (int i = 0; i < 8; i++) {
				Equip equip = equipControl.equipGenerate(1, i);
				if(equip!=null){
					temp.add(equip);
				}
			}
			player.setEquipBag(temp);
			for (int i = 0; i < 8; i++) {
				Equip equip = equipControl.equipGenerate(1, i);
				if(equip!=null){
					player.wearEquip(equip, i);
				}
			}
			gameControl.append("-------------------------",0);
			for (int i = 0; i < player.getEquipBag().size(); i++) {
				gameControl.append("背包:"+player.getEquipBag().get(i).toString(), 0);
			}
			gameControl.append("-------------------------",0);
			for (int i = 0; i < player.getEquipAry().length; i++) {
				if(player.getEquipAry()[i]!=null){
					gameControl.append("已装备:"+player.getEquipAry()[i].toString(), 0);
				}
			}
			break ;
		default:
			break;
		}
	}
	
	public void close(int type){
		switch (type) {
		case 0:
			break;
		case 1:
			sp1.setVisible(false);
			break;
		case 2:
			sp2.setVisible(false);
			break;
		default:
			break;
		}
	}

}
