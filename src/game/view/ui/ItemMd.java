package game.view.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

/***
 * 显示组件
 * 左右各是一个标签,作为一个小组件
 * @author admin
 *
 */
public class ItemMd{
	public Field leftMd ;
	public Field rightMd ;
	
	public ItemMd(String labelText, String text) {
		leftMd = new Field(labelText);
		rightMd = new Field(text);
	}
	
	public static ItemMd asSingleItem(String text, Color c) {
		ItemMd itemMd = new ItemMd(text, "");
		itemMd.rightMd = null ;
		itemMd.leftMd.setForeground(c);
		return itemMd;
	}
	
	
	public ItemMd(Field leftModule, Field rightModule) {
		if(leftModule == null) {
			leftModule = new Field("");
		}
		this.leftMd = leftModule ;
		if(rightModule==null) {
			rightModule = new Field("");
		}
		this.rightMd = rightModule ;
	}
	
	
	public void setLabelText(String text) {
		if(leftMd!=null) {
			leftMd.setText(text);
		}
	}
	
	public void setText(String text) {
		if(rightMd!=null) {
			rightMd.setText(text);
		}
	}
	
	public ItemMd size(int w1, int w2) {
		leftMd.setPreferredSize(new Dimension(w1, 22));
		rightMd.setPreferredSize(new Dimension(w2, 22));
		return this;
	}
	
}
