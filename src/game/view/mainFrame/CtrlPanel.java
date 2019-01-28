package game.view.mainFrame;

import javax.swing.JScrollPane;

import game.control.UICtrl;
import game.utils.C;
import game.view.TTextPane;
import game.view.button.MButton;
import game.view.panel.BasePanel;
import game.view.panel.GameView;

public class CtrlPanel extends BasePanel{
	private static final long serialVersionUID = 1L;
	private int x = 800 ;
	private int y = 460 ;
	private TTextPane infoP = new TTextPane(600, 180);
	private JScrollPane jsc = infoP.getInstance();
	private GameView gameView = new GameView();
	/** 移动时可能会用到的全部按钮 */
	private MButton[] mapButton = null ;
	
	public CtrlPanel() {
		initSet();
		setSize(x, y);
		
		//fightJpanel.setBorder(BorderFactory.createEtchedBorder());
		//取消文本域的边框
		//jsc.setUI(new ScrollPaneUI() {});
		
		jsc.setLocation(0,  0);
		
		gameView = new GameView();
		gameView.setLocation(0, jsc.getHeight());
		mapButton = gameView.mapButton ;
		
		UICtrl.setBorder(jsc, "游戏信息", C.YH_M);
		add(jsc);
		add(gameView);
	}

	@Override
	public void reloadUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadData() {
		// TODO Auto-generated method stub
		
	}
	
	public MButton[] getMapButton() {
		return mapButton ;
	}
	
	public TTextPane getInfoPanel() {
		return infoP;
	}
	
	public GameView getGameView() {
		return gameView ;
	}

	@Override
	public void setBacImg() {
		
	}
	
}
