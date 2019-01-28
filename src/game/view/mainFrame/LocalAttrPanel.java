package game.view.mainFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.control.GameControl;
import game.control.IoCtrl;
import game.control.UICtrl;
import game.entity.Player;
import game.entity.compontent.Point;
import game.utils.C;
import game.view.panel.BasePanel;
import game.view.ui.Field;
import game.view.ui.UniteModule;

public class LocalAttrPanel extends BasePanel{
	private static final long serialVersionUID = 1L;
	
	private Field name = new Field("hello, is me", C.LS_S) ;
	
	private Field nameValue,stateValue,rankValue,ExpValue,hpValue,mpValue,atkValue,defValue ;
	private Field[] attrAry = {nameValue,rankValue,stateValue,ExpValue,hpValue,mpValue,atkValue,defValue} ;
	
	private GameControl gc = GameControl.getInstance();
	
	public LocalAttrPanel(int attrWidth, int attrHeight) {
		initSet();
		setLocation(0 , 0);
		setSize(160, 225);
		
		name.setBounds(C.INSET, 0, 140, C.INSET+18);
		name.setAlignmentX(CENTER_ALIGNMENT);
		Field tempField = customField("");
		Point initPoint = new Point(C.INSET, name.getHeight());
		List<UniteModule> list = new ArrayList<>();
		/** 设置属性的标签和值 */
		for (int i = 0; i < attrAry.length; i++) {
			tempField = customField(C.attrAry[i]);
			attrAry[i] = customField("");
			attrAry[i].setSize(120, 20);
			attrAry[i].setPreferredSize(new Dimension(100, 20));
			list.add(new UniteModule(tempField, attrAry[i]));
			/*tempField.setBounds(x, y, fontSize*3, fontSize+inset);
			x = tempField.getX()+tempField.getWidth() ;
			attrAry[i].setBounds(x,y,attrWidth-x-inset, fontSize+inset);
			y = tempField.getY()+tempField.getHeight() ;*/
		}
		UICtrl.autoLayout((JPanel)this, initPoint, list, 0);
		add(name);
		setBacImg();
	}

	public Field customField(String text) {
		Field f = Field.defField(text);
		f.setOpaque(false);
		return f;
	}
	
	
	@Override
	public void reloadUI() {
		
	}

	@Override
	public void reloadData() {
		/*Player player = gc.getPlayer();
		attrAry[0].setText(player.getName()); 
		attrAry[1].setText(player.getRank()+"");
		attrAry[2].setText(player.getState()+"");
		attrAry[3].setText(player.getCurExp()+"/"+player.getExp());
		attrAry[4].setText(player.getHp()+"");
		attrAry[5].setText(player.getMp()+"");
		attrAry[6].setText(player.getAttack()+"");
		attrAry[7].setText(player.getDefense()+"");
		}*/
	}
	

	@Override
	public void setBacImg() {
		/** 设置人物属性的背景面板 */
		JLabel back = new JLabel();
		back.setOpaque(false);
		ImageIcon img = IoCtrl.loadImageIcon("/game/img/back/backC.png") ;
		back.setIcon(img);
		add(back);
		back.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		
	}
	

}
