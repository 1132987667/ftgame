package game.listener;

import game.control.GameControl;
import game.control.SoundControl;
import game.entity.Archive;
import game.entity.Player;
import game.utils.Constant;
import game.view.frame.MainFrame;
import game.view.frame.SpFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 对各个功能按钮进行监听
 * @author yilong22315
 *
 */
public class FunListener implements ActionListener{
	private MainFrame f  ;
	private GameControl gameControl = GameControl.getInstance();
	private SpFrame fubenFrame, stateFrame, fightFrame, jiangHuFrame, bagFrame, mapFrame ;
	private SpFrame curFrame ;
	private Archive archive = null ;
	private Player player ;
	//private Clip bagOpen = SoundControl.jiemianMuc("openBag");
	//private Clip openMap = SoundControl.jiemianMuc("openMap");
	//private Clip bu = SoundControl.jiemianMuc("bu");
	
	public FunListener(MainFrame mainFrame,Archive archive,Player player) {
		this.f = mainFrame ;
		this.player = player ;
		this.archive = archive ;
	}
	
	
	/**
	 * 初始化功能窗口
	 * @param fubenP
	 * @param playerP
	 * @param sp3
	 * @param jiangHuP
	 * @param bagP
	 * @param mapP
	 */
	public void initFunFrame(SpFrame fubenFrame, SpFrame stateFrame,
			SpFrame fightFrame, SpFrame jiangHuFrame, SpFrame bagFrame,
			SpFrame mapFrame) {
		this.fubenFrame = fubenFrame;
		this.stateFrame = stateFrame;
		this.fightFrame = fightFrame;
		this.jiangHuFrame = jiangHuFrame;
		this.bagFrame = bagFrame;
		this.mapFrame = mapFrame;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand() ;
		call(command);
	}
	
	/***
	 * 隐藏功能界面
	 * @param type
	 */
	public void close(int type){
		switch (type) {
		case Constant.State:
			stateFrame.setVisible(false);
			break;
		case Constant.Skill:
			break;
		case Constant.Bag:
			bagFrame.setVisible(false);
			break;
		case Constant.Task:
			//Tasks.setVisible(false);
			break;
		case Constant.JiangHu:
			jiangHuFrame.setVisible(false);
		case Constant.Fuben:
			fubenFrame.setVisible(false);
		case Constant.Archive:
			//.setVisible(false);
		case Constant.Map:
			mapFrame.setVisible(false);
		case Constant.Fight:
			fightFrame.setVisible(false);
		default:
			break;
		}
	}
	
	/**
	 * 逻辑
	 * 如果命令对应界面当前是打开的，而且打开标志也是当前窗口，那么关闭当前窗口
	 * 如果命令对应界面是关闭的
	 * 		如果打开标志为无，那么打开这个对应界面
	 * 		如果打开标志不为无，说明已经有窗口打开，不做操作
	 * @param command
	 */
	public void call(String command){
		SoundControl.jiemianMuc("openMap"); 
		getCurFrame(command);
		System.out.println(gameControl.windowFlag+"和"+command);
		if(curFrame.isVisible()&&gameControl.windowFlag.equals(command)){
			curFrame.setVisible(false);
			gameControl.funCloseInfo(curFrame.type);
			gameControl.restore();
			gameControl.windowFlag="无";
		}else if(gameControl.windowFlag.equals("无")){
			gameControl.funOpenInfo(curFrame.type);
			curFrame.setVisible(true);
			curFrame.removeKeyListener(gameControl.getKeyMana());
			curFrame.addKeyListener(gameControl.getKeyMana());
			curFrame.setFocusable(true);
			curFrame.requestFocus();
			curFrame.reload(curFrame.type);
			gameControl.windowFlag=command;
		}
		/*switch (command) {
		case Constant.SFuben:
			SoundControl.jiemianMuc("openMap"); 
			gameControl.funOpenInfo(Constant.Fuben);
				if(fubenFrame.isVisible()&&gameControl.windowFlag.equals(Constant.SFuben)){
					System.out.println("副本现在可见");
					fubenFrame.setVisible(false);
					gameControl.funCloseInfo(Constant.Fuben);
					gameControl.restore();
					gameControl.windowFlag="无";
				}else if(gameControl.windowFlag.equals("无")){
					fubenFrame.setVisible(true);
					fubenFrame.reload(Constant.Fuben);
					gameControl.windowFlag=Constant.SFuben;
				}
			break;
		case Constant.SJiangHu:
			gameControl.funOpenInfo(Constant.JiangHu);
			SoundControl.jiemianMuc("openMap"); 
			if(jiangHuP==null){
				if(gameControl.windowFlag.equals("无")){
				jiangHuP = new SpFrame(f,Constant.JiangHu);
				jiangHuP.setVisible(true);
				jiangHuP.reload(Constant.JiangHu);
				gameControl.windowFlag=Constant.SJiangHu;
				}
			}else{
				if(jiangHuP.isVisible()&&gameControl.windowFlag.equals(Constant.SJiangHu)){
					jiangHuP.setVisible(false);
					gameControl.funCloseInfo(Constant.JiangHu);
					gameControl.restore();
					gameControl.windowFlag="无";
				}else if(gameControl.windowFlag.equals("无")){
					jiangHuP.setVisible(true);
					jiangHuP.reload(Constant.JiangHu);
					gameControl.windowFlag=Constant.SJiangHu;
				}
			}
			break ;
		case Constant.SBag:
			gameControl.funOpenInfo(Constant.Bag);
			SoundControl.jiemianMuc("openBag"); 
			if(bagP==null){
				if(gameControl.windowFlag.equals("无")){
				bagP = new SpFrame(f,Constant.Bag);
				bagP.setVisible(true);
				bagP.reload(Constant.Bag);
				gameControl.windowFlag=Constant.SBag;
				}
			}else{
				if(bagP.isVisible()&&gameControl.windowFlag.equals(Constant.SBag)){
					bagP.setVisible(false);
					gameControl.funCloseInfo(Constant.Bag);
					gameControl.restore();
					gameControl.windowFlag="无";
				}else if(gameControl.windowFlag.equals("无")){
					bagP.setVisible(true);
					bagP.reload(Constant.Bag);
					gameControl.windowFlag=Constant.SBag;
				}
			}
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
		}*/
	}

	
	/**
	 * 得到当前打开的窗口
	 * @param command
	 * @return
	 */
	private void getCurFrame(String command) {
		switch (command) {
		case Constant.SState:
			curFrame = stateFrame ;
			break;
		case Constant.SBag:
			curFrame = bagFrame ;
			break;
		case Constant.STask:
			//curFrame = taskFrame ;
			break;
		case Constant.SJiangHu:
			curFrame = jiangHuFrame ;
			break;
		case Constant.SFuben:
			curFrame = fubenFrame ;
			break;
		case Constant.SMap:
			curFrame = mapFrame ;
			break;
		default:
			break;
		}
	}

}
