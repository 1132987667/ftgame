package game.listener;

import game.utils.Constant;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 为各个功能添加快捷键
 * 地图 M
 * 背包 B
 * 状态 V
 * 任务 Q
 * 副本 F
 * 剧情 J
 * @author yilong22315
 *
 */
public class KeyMana implements KeyListener{
	public static final String Map = "M" ;
	public static final String Bag = "B" ;
	public static final String Task = "Q" ;
	public static final String State = "V" ;
	public static final String Juqing = "J" ;
	public static final String Fuben = "F" ;
	
	public FunListener funListener ;
	
	public KeyMana(FunListener funListener) {
		this.funListener = funListener ;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char a = e.getKeyChar();
		String keyChar = (a+"").toUpperCase() ;
		System.out.println("按键了"+a+"键");
		switch (keyChar) {
		case Map:
			funListener.call(Constant.SMap);
			break;
		case Bag:
			funListener.call(Constant.SBag);
			break;
		case Task:
			funListener.call(Constant.STask);
			break;
		case State:
			funListener.call(Constant.SState);
			break;
		case Juqing:
			funListener.call(Constant.SJiangHu);
			break;
		case Fuben:
			funListener.call(Constant.SFuben);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
