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
	public static final char Map = 'M' ;
	public static final char Bag = 'B' ;
	public static final char Task = 'Q' ;
	public static final char State = 'V' ;
	public static final char Juqing = 'J' ;
	public static final char Fuben = 'F' ;
	
	public FunListener funListener ;
	
	public KeyMana(FunListener funListener) {
		this.funListener = funListener ;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(";;;;;;;;;;;;;;;;;;;;;;");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("按键了");
		char a = e.getKeyChar();
		switch (a) {
		case Map:
			funListener.call("地图");
			break;
		case Bag:
			funListener.call("背包");
			break;
		case Task:
			funListener.call("任务");
			break;
		case State:
			funListener.call("状态");
			break;
		case Juqing:
			funListener.call("剧情");
			break;
		case Fuben:
			funListener.call("副本");
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	System.out.println(".................");	
	}

}
