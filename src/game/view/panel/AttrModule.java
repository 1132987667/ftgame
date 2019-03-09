package game.view.panel;

import java.awt.Dimension;

import game.view.frame.QuickFrame;
import game.view.ui.ItemMd;

public class AttrModule extends BasePanel{

	private static final long serialVersionUID = 1L;

	public AttrModule() {
		initSet();
		Dimension d =backImg.asImgLabel("equipAndGong.png");
		setSize(d);
		add(backImg);
	}
	
	public ItemMd personItemMd(String labelText, String text) {
		ItemMd itemMd = new ItemMd(labelText, text);
		itemMd.leftMd.setPreferredSize(new Dimension(45, 24));
		itemMd.leftMd.setPreferredSize(new Dimension(100, 24));
		return itemMd ;
	}
	
	public static void main(String[] args) {
		QuickFrame qf = new QuickFrame();
		AttrModule attrModule =  new AttrModule();
		System.out.println(attrModule.getWidth()+":"+attrModule.getHeight());
		qf.getMainPanel().add(attrModule);
		qf.start();
	}
	
	
	
}
