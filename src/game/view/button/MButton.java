package game.view.button;

import java.awt.event.MouseEvent;

import game.entity.NPC;
import game.entity.Scene;

/***
 * 场景和npc的承载按钮
 * 交互按钮
 * @author yilong22315
 * 
 */
public class MButton extends TButton {
	private static final long serialVersionUID = 1L;
	/**
	 * flag的作用 当flag为false时，在鼠标移入移出才能有特效 当flag为true时，按钮保持被点击的状态
	 */
	/**
	 * 需要控制场景
	 * 在点击不能到达的地点时  移入移出没有效果 被点击时也没有效果
	 * 
	 * 移入移出有效果，但是点击无效果
	 * 
	 */
	
	
	/** 移入移出点击都有效果 */
	public static final int Well    = 1  ;
	/** 移入移出有效果，点击无效果 */
	public static final int Base    = 2 ;
	/** 移入移出都无效果 */
	public static final int Enabled = 3  ;

	
	public boolean used = false ;
	
	private int flag = 1 ;;
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


	public void mouseClicked() {
		/*if(flag==false){
			this.setIcon(image2);
			flag = true;
		}else{
			this.setIcon(image2);
		}*/
		if(flag==Well){
			this.setIcon(image2);
		}
		
	}

	public void mouseExited() {
		if (type == -1) {
			setBackground(c[0]);
		} else if (type == 14) {

		} else {
			/*if (!flag) {
				this.setIcon(image1);
			}*/
			if(flag!=Enabled){
				this.setIcon(image1);
			}
		}
	}

	public void removeMouseListener() {
		this.removeMouseListener(this);
	}

	public void setFlag(int flag) {
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
		/*if (!flag) {
			flag = true;
			this.setIcon(image2);
		}*/
		if(flag==Well){
			this.setIcon(image2);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(flag!=Enabled){
			this.setIcon(image3);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(flag!=Enabled){
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
