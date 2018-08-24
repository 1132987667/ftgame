package game.utils;

import game.control.GameControl;
import game.entity.Citiao;
import game.entity.Ditu;
import game.entity.Equip;
import game.entity.Scene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class XMLTool extends JFrame{
	private static final long serialVersionUID = 1L;
	/***  主面板,jp1[查询菜单]         */
	private JPanel rootpane,jp1 ,jp2,jp3 ;
	private int inset = 9 ;
	private JTextField name,des,rank ;
	private JTable fubenTable ;
	private JTable equipTable ;
	private int width , height ;
	
	private DefaultTableModel tableModel = null ;
	
	private JComboBox<String> cb1 = null , cb2 = null ;
	private String[] defFun = {"fuben","equip","3","4"} ;
	private String curSelect = null ; 
	private int curInx = 0 ;
	private JScrollPane jsc ;
	
	public XMLTool() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screensize.getWidth();
		height = (int) screensize.getHeight();
		System.out.println(width);
		rootpane = new JPanel() ;
		setContentPane(rootpane);
		this.getContentPane().setBackground(Color.white);
		rootpane.setLayout(null);
		rootpane.setSize(width, height);
		
		jp1 = new JPanel() ;
		jp1.setOpaque(false);
		jp1.setBounds(inset, inset, rootpane.getWidth()-inset*2, 24 );
		jp1.setLayout(null);
		
		cb1 = new JComboBox<String>(defFun);
		cb1.setMaximumSize(new Dimension(92,24));
		/** removeAllItems[移除所有下拉框选项],新增选项 addItem*/	
		
		List<Ditu> fbList =  GameControl.getInstance().loadFuben();
		String[] fbAry = new String[fbList.size()];
		for (int i = 0; i < fbAry.length; i++) {
			fbAry[i] = fbList.get(i).name;
		}
		cb2 = new JComboBox<String>(fbAry);
		
		cb2.setMaximumSize(new Dimension(192,24));
		JButton submit = new JButton("sure");
		JButton add = new JButton("add");
		JButton del = new JButton("delete");
		JButton save = new JButton("save");
		jp1.add(cb1);
		jp1.add(cb2);
		jp1.add(submit);
		jp1.add(add);
		jp1.add(del);
		jp1.add(save);
		cb1.setBounds(0,0,92,24);
		cb2.setBounds(100,0,92,24);
		submit.setBounds(cb2.getX()+cb2.getWidth()+inset,0,72,24);
		add.setBounds(submit.getX()+submit.getWidth()+inset,0,72,24);
		del.setBounds(add.getX()+add.getWidth()+inset,0,72,24);
		save.setBounds(del.getX()+del.getWidth()+inset,0,72,24);
		add.addActionListener(ac);
		del.addActionListener(ac);
		save.addActionListener(ac);
		submit.addActionListener(ac);
		
		jp2 = new JPanel() ;
		jp2.setOpaque(false);
		jp2.setBounds(inset, jp1.getY()+jp1.getHeight()+inset, rootpane.getWidth()-inset*2, 34 );
		Box box = Box.createHorizontalBox();
		box.setOpaque(false);
		box.add(Box.createHorizontalStrut(inset));	
		box.add(new Field("name:"));
		name = new JTextField(fbList.get(0).name);
		name.setPreferredSize(new Dimension(50, 30));
		box.add(name);
		box.add(Box.createHorizontalStrut(inset));	
		box.add(new Field("des:"));
		des = new JTextField(fbList.get(0).des);
		des.setPreferredSize(new Dimension(290, 30));
		box.add(des);
		box.add(Box.createHorizontalStrut(inset));	
		box.add(new Field("rank:"));
		rank = new JTextField(fbList.get(0).rankL+","+fbList.get(0).rankR);
		rank.setPreferredSize(new Dimension(40, 30));
		box.add(rank);
		jp2.add(box);
		
		
		String[][] ary = new String[0][0] ;
		String[] tableHead = {"name","des","pos","npc"} ; 
		tableModel = new DefaultTableModel(ary, tableHead);
		fubenTable = new JTable(tableModel);// 创建表格组件
		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
        fubenTable.setRowSorter(sorter);
		JTableHeader head = fubenTable.getTableHeader(); // 创建表格标题对象
		head.setPreferredSize(new Dimension(500, 24));
		head.setFont(new Font("隶书", Font.PLAIN, 18));// 设置表格字体
		fubenTable.setRowHeight(20);// 设置表格行宽
		TableColumn column = null ;
		column = fubenTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(50);
		column = fubenTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(360);
		column = fubenTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(50);
		column = fubenTable.getColumnModel().getColumn(3);
		column.setPreferredWidth(100);
		fubenTable.setCellSelectionEnabled(false);
		//tableModel.fireTableDataChanged();
		
		
		Ditu f = fbList.get(cb2.getSelectedIndex());
		List<Scene> l = f.scene ;
		String[] textAry = null ;
		/** 填充表格 */
		for (int i = 0; i < l.size(); i++) {
			textAry = new String[4];
			textAry[0] = new String(l.get(i).name);
			textAry[1] = new String(l.get(i).des);
			textAry[2] = new String(l.get(i).x+","+l.get(i).y);
			textAry[3] = new String(l.get(i).npcStr);
			tableModel.addRow(textAry);
		}
		
		jsc = new JScrollPane(fubenTable);
		jsc.setPreferredSize(new Dimension(width, height-30));
		jsc.setBounds(inset, jp2.getY()+jp2.getHeight()+inset, width-30, height-(jp2.getY()+jp2.getHeight()+inset)-80 );
		jsc.setOpaque(false);
		jsc.getViewport().setOpaque(false);
		jsc.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		
		rootpane.add(jp1);
		rootpane.add(jp2);
		rootpane.add(jsc);
		
	}
	
	public static void main(String[] args) {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
							
				}
			}
		}
		XMLTool frame = new XMLTool();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, (int)screensize.getWidth(), (int)screensize.getHeight());
		//frame.setResizable(false);
		frame.setVisible(true);
	}
	
	
	
	ActionListener ac = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){ 
			System.out.println("总行数:"+tableModel.getRowCount());
			if(e.getActionCommand().equals("add")){
				tableModel.addRow(new String[4]);
			}
			if(e.getActionCommand().equals("delete")){
				//删除选中的当前行，把下一行设置为当前行
				int rowcount = tableModel.getRowCount() - 1;
				//需要>0
				 if (rowcount >= 0) {
					 tableModel.removeRow(rowcount);
					 tableModel.setRowCount(rowcount);
		             /**
		              * 删除行比较简单，只要用DefaultTableModel的removeRow方法即可
		              * 删除行完毕后必须重新设置列数，也就是使用DefaultTableModel的setRowCount()方法来设置当前行
		             */
		         }
			}
			if(e.getActionCommand().equals("save")){
				//table.getCellEditor().stopCellEditing();
				if (fubenTable.isEditing()) {
					TableCellEditor cellEditor = fubenTable.getCellEditor();
					if (cellEditor != null) 
						cellEditor.stopCellEditing();
				}

				int length = fubenTable.getRowCount() ;
				List<Scene> list = new ArrayList<>();
				Scene temp = null ;
				for (int i = 0; i < length; i++) {
					temp = new Scene();
					temp.name = (String) fubenTable.getValueAt(i, 0);
					temp.des = (String) fubenTable.getValueAt(i, 1);
					String str = (String) fubenTable.getValueAt(i, 2);
					String[] ary = str.split(",");
					temp.x = SUtils.conStrtoInt(ary[0]);
					temp.y = SUtils.conStrtoInt(ary[1]);
					temp.npcStr = (String) fubenTable.getValueAt(i, 3);
					System.out.println(temp.toString());
					list.add(temp);
				}
				String[] ary = rank.getText().split(",");
				int rankL = SUtils.conStrtoInt(ary[0]);
				int rankR = SUtils.conStrtoInt(ary[1]);
				Ditu fb = new Ditu(0,name.getText(), des.getText(), rankL, rankR);
				fb.scene = list ;
				SUtils.editXML("", curInx, fb);
			}
			if(e.getActionCommand().equals("sure")){
				curInx = cb1.getSelectedIndex() ;
				if(curInx==1){
					setEquipTable();
				}
				
			}
			fubenTable.revalidate();
		}
	};
	
	class TableAc implements TableModelListener{

		@Override
		public void tableChanged(TableModelEvent e) {
		}
		
	}
	
	class Field extends Label{
		private static final long serialVersionUID = 1L;

		public Field(String str) {
			super(str);
			setBackground(Color.white);
			setFont(new Font("楷体",Font.PLAIN,14));
		}
	}
	
	/**
	 * 设置装备的表格
	 */
	private void setEquipTable(){
		String[][] ary = new String[0][0] ;
		String[] tableHead = {"id","name","介绍","品质","rank","词条1","词条2","词条3","词条4","词条5"} ; 
		tableModel = new DefaultTableModel(ary, tableHead);
		equipTable = new JTable(tableModel);// 创建表格组件
		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
        equipTable.setRowSorter(sorter);
        
        JTableHeader head = equipTable.getTableHeader(); // 创建表格标题对象
		head.setPreferredSize(new Dimension(width, 24));
		head.setFont(new Font("微软雅黑", Font.PLAIN, 14));// 设置表格字体
		equipTable.setRowHeight(20);// 设置表格行宽
		TableColumn column = null ;
		column = equipTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(50);
		column = equipTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(100);
		column = equipTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(440);
		column = equipTable.getColumnModel().getColumn(3);
		column.setPreferredWidth(50);
		column = equipTable.getColumnModel().getColumn(4);
		column.setPreferredWidth(76);
		column = equipTable.getColumnModel().getColumn(5);
		column.setPreferredWidth(130);
		column = equipTable.getColumnModel().getColumn(6);
		column.setPreferredWidth(130);
		column = equipTable.getColumnModel().getColumn(7);
		column.setPreferredWidth(130);
		column = equipTable.getColumnModel().getColumn(8);
		column.setPreferredWidth(130);
		column = equipTable.getColumnModel().getColumn(9);
		column.setPreferredWidth(130);
		equipTable.setCellSelectionEnabled(false);
		
		/** 重新设置 数据 */
		String key = null;
		Equip value = null;
		GameControl ganecControl = GameControl.getInstance();
		Map<String, Equip> equipMap = ganecControl.getEquipMap();
		Iterator<String> iter = equipMap.keySet().iterator();
		String[] textAry = null ;
		/** 填充表格 */
		while (iter.hasNext()) {
			key = iter.next();
			value = equipMap.get(key);
			textAry = new String[10];
			textAry[0] = value.getId();
			textAry[1] = value.getName();
			textAry[2] = value.getDes();
			textAry[3] = value.getType()+"";
			textAry[4] = value.getRank()+"";
			/**  */
			if(value.getFixedList()!=null){
				Citiao[] ctList = value.getFixedList() ;  
				for (int i = 0; i < ctList.length; i++) {
					textAry[i+5] = ctList[i].des ;
				}
			}
			tableModel.addRow(textAry);
		}
		jsc.setViewportView(equipTable);
	}
	
	
	
}
