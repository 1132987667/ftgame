package game.view.button;

import java.awt.event.MouseEvent;

import game.entity.NPC;
import game.entity.Scene;

/***
 * 场景和npc的承载按钮
 * 
 * @author yilong22315
 * 
 */
public class MButton extends TButton {
	private static final long serialVersionUID = 1L;
	/**
	 * flag的作用 当flag为false时，在鼠标移入移出才能有特效 当flag为true时，按钮保持被点击的状态
	 */
	private boolean flag = false;
	private Scene curScene = null;
	private int num = -1 ;
	private NPC npc = null;
	
	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public MButton(String str, int type) {
		super(str, type);
	}

	public void setFlag() {
		flag = false;
	}

	public void mouseClicked() {
		if(flag==false){
			this.setIcon(image2);
			flag = true;
		}else{
			this.setIcon(image2);
		}
		
		
	}

	public void mouseExited() {
		if (type == -1) {
			setBackground(c[0]);
		} else if (type == 14) {

		} else {
			if (!flag) {
				this.setIcon(image1);
			}
		}
	}

	public void removeMouseListener() {
		this.removeMouseListener(this);
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Scene getCurScene() {
		return curScene;
	}

	public void setCurScene(Scene curScene) {
		this.curScene = curScene;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!flag) {
			flag = true;
			this.setIcon(image2);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(!flag){
			this.setIcon(image3);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(!flag){
			this.setIcon(image1);
		}
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		System.out.println(curScene.name+"的num设置为:"+num);
		this.num = num;
	}

	@Override
	public String toString() {
		return "MButton [flag=" + flag + ", curScene=" + curScene + ", num="
				+ num + ", npc=" + npc + "]";
	}

	
}
