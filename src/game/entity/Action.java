package game.entity;

import java.io.Serializable;
/**edddddd
 * 人或物能触发的所有动作
 * @author yilong22315
 */

import game.control.GameControl;
import game.utils.SUtils;
public class Action implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public GameControl gameControl = GameControl.getInstance();
	
	String value = "" ;
	
	public void doing() {
		
	}
	
	/**
	 * 索取
	 */
	public void blag() {
		value = "$have(40000,1);$true| ;$false|" ;
		if(value.startsWith("$have")) {
			String itemStr = value.substring(value.indexOf("(")+1,value.indexOf(")")); 
			boolean flag = gameControl.isHave(SUtils.strToAry(itemStr));
			if(flag) {
				
			}else {
				
			}
		}	
		
		
		
	}
	
	public static void main(String[] args) {
		System.out.println();
		
		/*String value = "$have(40000,1);$true| ;$false|" ;
		System.out.println(value.startsWith("$have"));
		if(value.startsWith("$have")) {
			String itemStr = value.substring(value.indexOf("(")+1,value.indexOf(")")); 
			System.out.println(itemStr);
		}*/
	}
	
}
