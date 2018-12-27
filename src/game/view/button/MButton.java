package game.view.button;

import java.awt.event.MouseEvent;

import game.entity.Item;
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
	
	/** 移入移出点击都有效果 */
	public static final int Well    = 1  ;
	/** 移入移出有效果，点击无效果 */
	public static final int Base    = 2 ;
	/** 移入移出都无效果 */
	public static final int Enabled = 3  ;
	/** 鼠标效果启用标志 */
	private int flag = Well ;
	
	/** 判断当前按钮是否被启用，在动作交互按钮中使用 */
	public boolean used = false ;
	
	/** 场景编号 */
	private int num = -1 ;
	/******** 储存的实体类 **********/
	private NPC npc = null;
	private Item item = null;
	private Scene scene = null;
	
	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public MButton(String str, int type) {
		super(str, type);
		setSize();
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
		this.num = num;
	}

	@Override
	public String toString() {
		return "MButton [flag=" + flag + ", curScene=" + scene + ", num="
				+ num + ", npc=" + npc + "]";
	}

	/**
	 * 为当前所在位置按钮设置特殊效果
	 */
	public void setCurBu() {
		this.setIcon(image2);
		flag = 3 ; 
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
}
