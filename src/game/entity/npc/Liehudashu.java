package game.entity.npc;

import game.entity.NPC;

import javax.swing.JTextArea;
/**
 * 猎户大叔
 * @author yilong22315
 *
 */
public class Liehudashu extends NPC{
	

	public String[] action = { "交谈","给予","任务" } ;
	
	public String name = "猎户大叔" ;
	
	public void talk(JTextArea jt){
		jt.setText("最近外面越来越乱了，听说还有一伙马贼烧杀抢掠，无恶不作，真是可恶！");
	}
	
	public void give(JTextArea jt){
		jt.setText("你这么穷，能给我什么东西！");
	}
	
	public void task(JTextArea jt){
		jt.setText("你要是真的有本事，就去吧那伙马贼给灭了！");
	}
	
	public static void main(String[] args) {
		NPC n = new Liehudashu();
		System.out.println( n.getName() );
	}

	public String[] getAction() {
		return action;
	}

	public void setAction(String[] action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
