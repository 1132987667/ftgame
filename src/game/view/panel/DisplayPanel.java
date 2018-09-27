package game.view.panel;

import javax.swing.JPanel;
/**
 * 在layered层展示信息
 * 一般移入组件上，就显示该组件所隐藏的信息
 *
 */
public class DisplayPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/** 当前要显示的信息的类型 */
	public int infoType = 0 ;
	
	public DisplayPanel() {
		
	}

	public void reLoad(int infoType){
		this.infoType = infoType ;
		/**
		 * 0:内功
		 * 1:外功
		 * 2:被动技
		 * 3:主动技能
		 * 4:
		 * 5:
		 * 6:
		 * 7:
		 */
		if(infoType==0){
			
		}else if(infoType==1){
			
		}else if(infoType==2){
			
		}
	}
	
}
