package game.view.mainFrame;

import game.listener.FunListener;
import game.utils.C;
import game.view.button.PicBu;
import game.view.panel.BasePanel;

public class FunPanel extends BasePanel{
	private static final long serialVersionUID = 1L;
	private int x = 160 ;
	private int y = 200 ;
	PicBu status, skillsBu, bagBu, task, map, fuben, save, juqing, console;
	/** 状态,技能,背包,任务,剧情,副本,保存,大地图,宠物,境界 */
	PicBu[] funAry = { status, skillsBu, bagBu, task, juqing, fuben, save, map, console };
	FunListener funListener = null ;
	
	public FunPanel() {
		initSet();
		setOpaque(true);
		setBackground(C.colorAry[4]);
		setSize(x,  y);
		
		
		for (int i = 0; i < funAry.length; i++) {
			funAry[i] = new PicBu(C.funAry[i],1);
			funAry[i].addActionListener(funListener);
			add(funAry[i]);
			if(i%2==0){
				funAry[i].setLocation(6, 26*(i/2)+6);
			}else{
				funAry[i].setLocation(82, 26*(i/2)+6);
			}
			funAry[i].setSize(72, 24);
		}
	}
	
	public void setFunListener(FunListener funListener) {
		this.funListener = funListener ;
	}

	@Override
	public void reloadUI() {
		
	}

	@Override
	public void reloadData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBacImg() {
		// TODO Auto-generated method stub
		
	}

}
