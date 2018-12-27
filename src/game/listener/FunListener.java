package game.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.control.GameControl;
import game.control.SoundControl;
import game.entity.Archive;
import game.entity.Player;
import game.utils.ArchiveUtils;
import game.utils.C;
import game.utils.SUtils;
import game.view.frame.MainFrame;
import game.view.frame.SpFrame;

/**
 * 对各个功能按钮进行监听
 * @author yilong22315
 *
 */
public class FunListener implements ActionListener{
	private MainFrame mainFrme  ;
	private GameControl gameControl = GameControl.getInstance();
	private SpFrame fubenFrame, stateFrame, fightFrame, jiangHuFrame, bagFrame, mapFrame, taskFrame ;
	private SpFrame curFrame ;
	private Archive archive = null ;
	private Player player ;
	//private Clip bagOpen = SoundControl.jiemianMuc("openBag");
	//private Clip openMap = SoundControl.jiemianMuc("openMap");
	//private Clip bu = SoundControl.jiemianMuc("bu");
	
	public FunListener(MainFrame mainFrame,Archive archive,Player player) {
		this.mainFrme = mainFrame ;
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
			SpFrame mapFrame, SpFrame taskFrame) {
		this.fubenFrame = fubenFrame;
		this.stateFrame = stateFrame;
		this.fightFrame = fightFrame;
		this.jiangHuFrame = jiangHuFrame;
		this.bagFrame = bagFrame;
		this.mapFrame = mapFrame;
		this.taskFrame = taskFrame;
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
		case C.State:
			stateFrame.setVisible(false);
			break;
		case C.Skill:
			break;
		case C.Bag:
			bagFrame.setVisible(false);
			break;
		case C.Task:
			taskFrame.setVisible(false);
			break;
		case C.JiangHu:
			jiangHuFrame.setVisible(false);
		case C.Fuben:
			fubenFrame.setVisible(false);
		case C.Archive:
			//.setVisible(false);
		case C.Map:
			//mapFrame.setVisible(false);
		case C.Fight:
			//fightFrame.setVisible(false);
		default:
			break;
		}
	}
	
	/**
	 * 判断是否应该打开窗口
	 * 如果当前已进入场景中
	 * 那么判断当前是否有窗口打开
	 * 窗口打开?
	 * true 
	 * 		打开的窗口和调用的窗口为同一窗口？
	 * 		true
	 * 			关闭当前打开的窗口
	 * 		false
	 * 			不理会
	 * false
	 * 		打开调用的窗口
	 * 
	 * 
	 * @param command
	 */
	public void call(String command){
		//if(gameControl.isInScene()) {
		SoundControl.jiemianMuc("openMap"); 
		if(command.equals(C.SArchive)||command.equals(C.SConsole)) {
			if(command.equals(C.SArchive)) {
				archive = SUtils.conPlayerToArc(player);
				ArchiveUtils.saveArchiving(archive, gameControl.getArchiveName());
			}else if(command.equals("控制台")){
				System.out.println("打开控制台");
				gameControl.getTan().setVisible(true);
			}
		}else {
			SpFrame openFrame = getCurOpenFrame();
			if(openFrame!=null) {
				if(openFrame==getCurFrame(command)) {
					openFrame.setVisible(false);
					gameControl.funCloseInfo(openFrame.type);
					gameControl.restore();
				}
			}else {
				curFrame = getCurFrame(command) ;
				gameControl.funOpenInfo(curFrame.type);
				curFrame.reload(curFrame.type);
				curFrame.setVisible(true);
				curFrame.removeKeyListener(gameControl.getKeyMana());
				curFrame.addKeyListener(gameControl.getKeyMana());
				curFrame.setFocusable(true);
				curFrame.requestFocus();
				curFrame.reload(curFrame.type);
			}
		}
		
		
		
		/*if(curFrame.isVisible()&&gameControl.windowFlag.equals(command)){
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
		}*/
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
	private SpFrame getCurFrame(String command) {
		SpFrame sp = null ;
		switch (command) {
		case C.SState:
			sp = stateFrame ;
			break;
		case C.SBag:
			sp = bagFrame ;
			break;
		case C.STask:
			//curFrame = taskFrame ;
			break;
		case C.SJiangHu:
			sp = jiangHuFrame ;
			break;
		case C.SFuben:
			sp = fubenFrame ;
			break;
		case C.SMap:
			sp = mapFrame ;
			break;
		default:
			break;
		}
		return sp ;
	}
	
	public SpFrame getCurOpenFrame() {
		if(fubenFrame.isVisible())
			return fubenFrame ;
		if(stateFrame.isVisible())
			return stateFrame ;
		/*if(fightFrame.isVisible())
			return fightFrame ;*/
		if(jiangHuFrame.isVisible())
			return jiangHuFrame ;
		if(bagFrame.isVisible())
			return bagFrame ;
		if(mapFrame.isVisible())
			return mapFrame ;
		if(taskFrame.isVisible())
			return taskFrame ;
		return null;
	}

}
