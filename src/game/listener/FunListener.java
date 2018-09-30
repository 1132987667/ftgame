package game.listener;

import game.control.EquipControl;
import game.control.GameControl;
import game.control.SoundControl;
import game.entity.Archive;
import game.entity.Equip;
import game.entity.Player;
import game.utils.ArchiveUtils;
import game.utils.Constant;
import game.utils.SUtils;
import game.view.frame.MainFrame;
import game.view.frame.SpFrame;
import game.view.panel.BigMapP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;

/**
 * 对各个功能按钮进行监听
 * @author yilong22315
 *
 */
public class FunListener implements ActionListener{
	private MainFrame f  ;
	private GameControl gameControl ;
	private SpFrame fubenP, playerP, sp3, jiangHuP, bagP, mapP ;
	private Archive archive = null ;
	private Player player ;
	//private Clip bagOpen = SoundControl.jiemianMuc("openBag");
	//private Clip openMap = SoundControl.jiemianMuc("openMap");
	//private Clip bu = SoundControl.jiemianMuc("bu");
	
	public FunListener(MainFrame mainFrame,Archive archive,Player player) {
		this.f = mainFrame ;
		this.player = player ;
		this.archive = archive ;
		gameControl = GameControl.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand() ;
		call(command);
	}
	
	public void close(int type){
		switch (type) {
		case 0:
			break;
		case 1:
			fubenP.setVisible(false);
			break;
		case 2:
			playerP.setVisible(false);
			break;
		case 4:
			jiangHuP.setVisible(false);
			break;
		case 5:
			bagP.setVisible(false);
		default:
			break;
		}
	}
	
	public void call(String command){
		switch (command) {
		case Constant.SFuben:
			SoundControl.jiemianMuc("openMap"); 
			gameControl.funOpenInfo(Constant.Fuben);
			if(fubenP==null){
				fubenP = new SpFrame(f,Constant.Fuben);
			}
			fubenP.setVisible(true);
			fubenP.reload(Constant.Fuben);
			break;
		case Constant.SJiangHu:
			gameControl.funOpenInfo(Constant.JiangHu);
			SoundControl.jiemianMuc("openMap"); 
			if(jiangHuP==null){
				jiangHuP = new SpFrame(f,Constant.JiangHu);
			}
			jiangHuP.setVisible(true);
			jiangHuP.reload(Constant.JiangHu);
			break ;
		case Constant.SBag:
			gameControl.funOpenInfo(Constant.Bag);
			SoundControl.jiemianMuc("openBag"); 
			if(bagP==null){
				bagP = new SpFrame(f,Constant.Bag);
			}
			bagP.setVisible(true);
			bagP.reload(Constant.Bag);
			break;
		case "状态":
			gameControl.funOpenInfo(Constant.State);
			SoundControl.jiemianMuc("bu"); 
			//SUtils.sleep(bu.getMicrosecondLength()/1000);
			if(playerP==null){
				playerP = new SpFrame(f,Constant.State);
			}
			playerP.setVisible(true);
			playerP.reload(Constant.State);
			break;
		case "存档":
			gameControl.funOpenInfo(Constant.JiangHu);
			archive = SUtils.conPlayerToArc(player);
			ArchiveUtils.saveArchiving(archive, gameControl.getArchiveName());
			break;
		case "地图":
			gameControl.funOpenInfo(Constant.Map);
			
			
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
	
	

}
