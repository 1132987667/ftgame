package game.utils.xmltool;

import game.utils.SUtils;
import game.view.button.PicBu;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class MyRender extends AbstractCellEditor implements TableCellRenderer, TableCellEditor,ActionListener {

	private static final long serialVersionUID = 1L;
	private PicBu button = null;
	/** 1-功法效果  2-技能 */
	public int type = 1 ;
	public final int EFFECT = 1 ;
	public final int SKILL = 2 ;
	
	/**
	 * 决定生成的类型
	 * 1:功法效果
	 * @param type
	 */
	public MyRender(int type) {
		this.type  = type ;
		if(type==1){
			button = new PicBu("效果",-1);
			button.addActionListener(this);
		}else if(type==2){
			button = new PicBu("技能效果",-1);
			button.addActionListener(this);
		}
	}
	
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return button;
	}


	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		button.setActionCommand(row+"");
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row = SUtils.conStrtoInt(e.getActionCommand());
		if(type==EFFECT){
			XMLTool.setGongEffectInfo(row);
		}else if(type==SKILL){
			//XMLTool.setGongEffectInfo(row);
			XMLTool.setSkillEffectInfo(row);
		}
	}

}