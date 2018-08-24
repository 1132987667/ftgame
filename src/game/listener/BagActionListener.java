package game.listener;

import game.entity.Equip;
import game.entity.Player;
import game.utils.SUtils;
import game.view.button.TButton;
import game.view.panel.BagPanel;
import game.view.panel.EquipInfoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 对每件物品进行监听
 * @author yilong22315
 *
 */
public class BagActionListener implements ActionListener {
	/** 当前点击的装备 */
	EquipInfoPanel selectEp;
	/** 身上穿着的装备 */
	EquipInfoPanel wearEp;
	private boolean flag = false;
	/** 当前面板背包内物品 */
	private List<Equip> list ;
	/** 当前点击的物品的下标 */
	private int index ;
	private TButton putOn , resolve ;
	private BagPanel bagPanel ;
	private Player player ;
	
	private int pageNum = 0 ;
	/**
	 * 
	 * @param bagPanel
	 * @param list 得到当前点击的物品信息
	 * @param player
	 */
	public BagActionListener(BagPanel bagPanel,int pageNum,Player player) {
		this.bagPanel = bagPanel ;
		this.player = player ;
		this.pageNum = pageNum ;
		selectEp = bagPanel.getSelectEp();
		wearEp = bagPanel.getWearEp();
		putOn = bagPanel.getPutOn();
		resolve = bagPanel.getResolve();
	}
	
	/** 点击物品时触动的监听 */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<Equip> eqList = player.getEquipBag(pageNum);
		index = SUtils.conStrtoInt(e.getActionCommand());
		Equip selectEquip = eqList.get(index);
		
		bagPanel.setClickEq(selectEquip);
		bagPanel.setIndex(pageNum);
		System.out.println("当前点击物品信息:"+selectEquip.toString());
		
		/** 当前装备的类型 */
		int part = selectEquip.getPart();
		Equip wearEquip = player.getEquip(part);
		System.out.println("当前装备:"+wearEquip.toString());
		
		selectEp.setEpInfo(selectEquip,1);
		selectEp.setVisible(true);
		wearEp.setVisible(true);
		wearEp.setEpInfo(wearEquip,2);
		
		putOn.setVisible(true);
		resolve.setVisible(true);
		//selectEp.repaint();

	}

}