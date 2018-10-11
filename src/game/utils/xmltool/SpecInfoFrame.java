package game.utils.xmltool;

import game.entity.Gong;
import game.entity.Skill;
import game.entity.Tier;
import game.utils.SUtils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
 * 用来显示特殊信息的界面
 * 如 每层功法具体信息
 * 	 功法带来的技能
 * @author yilong22315
 *
 */
public class SpecInfoFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel detailPanel = null ;
	public DefaultTableModel tableModel ;
	public JTable effect ;
	private JScrollPane jsc ;
	
	private String curID ;
	private Gong gong = null ;
	private Map<String, Gong> gongMap = null ;
	private Map<String, Skill> skillmap = null ;
	public JLabel jl ;
	public XMLTool xmlTool = null ;
	private int roleType = 0 ;
	
	public SpecInfoFrame(int width, int height) {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBackground(Color.white);
		setSize(width, height);
		//setUndecorated(false);
		setResizable(false);
		
		detailPanel = new JPanel();
		detailPanel.setLayout(null);
		detailPanel.setBackground(Color.white);
		setContentPane(detailPanel);
		
		JPanel jbPanel = new JPanel() ;
		jbPanel.setOpaque(false);
		jbPanel.setLayout(new FlowLayout());
		jbPanel.setBounds(0,-3,1000, 34);
		jl = new JLabel("功法:") ;
		jbPanel.add(jl);
		JButton close = new JButton("关闭");
		jbPanel.add(close);
		JButton save = new JButton("保存");
		save.addActionListener(saveAc);
		JButton addBu = new JButton("新增");
		addBu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new String[5]);
				ColorRender colorRender = new ColorRender();
				for (int i = 0; i < 5; i++) {
					effect.getColumnModel().getColumn(i).setCellRenderer(colorRender);
				}
			}
		});
		jbPanel.add(addBu);
		jbPanel.add(save);
		detailPanel.add(jbPanel);
		
		String[][] ary = new String[0][0] ;
		String[] strAry = {"层数","要求等级","需要经验","属性加成","解锁技能"} ;
		int[] headWidth = {40,80,80,380,380} ;//1000
		int w = 960 ;
		tableModel = new DefaultTableModel(ary,strAry);
		effect = new JTable(tableModel);
		effect.setSize(w, 300);
		effect.setPreferredSize(new Dimension(w,300));
		effect.setRowHeight(20);// 设置表格行宽
		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
		effect.setRowSorter(sorter);
		JTableHeader head = effect.getTableHeader(); // 创建表格标题对象
		head.setPreferredSize(new Dimension(w, 20));
		head.setFont(new Font("微软雅黑", Font.PLAIN, 14));// 设置表格字体
		TableColumn column = null ;
		for (int i = 0; i < headWidth.length; i++) {
			column = effect.getColumnModel().getColumn(i);
			column.setPreferredWidth(headWidth[i]);
		}
		effect.setCellSelectionEnabled(false);
		
		jsc = new JScrollPane(effect);
		jsc.setBounds(0,jbPanel.getHeight(),w, 340);
		add(jsc);
		//gongJsc.setOpaque(false);
		//gongJsc.getViewport().setOpaque(false);
		jsc.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	}

	public void setGongMap(Map<String, Gong> gongMap) {
		this.gongMap = gongMap;
	}

	public void setGong(Gong gong) {
		this.gong = gong;
		this.curID = gong.getId();
	}
	
	ActionListener saveAc = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			/** 停止未完成的编辑 */
			if (effect.isEditing()) {
				TableCellEditor cellEditor = effect.getCellEditor();
				if (cellEditor != null) 
					cellEditor.stopCellEditing();
			}
			int length = effect.getRowCount() ;
			List<Tier> list = new ArrayList<>();
			Tier tmp = null ;
			for (int i = 0; i < length; i++) {
				if(effect.getValueAt(i, 0).toString().trim().length()==0){
					continue;
				}
				tmp = new Tier() ;
				tmp.curTier =  SUtils.conobjtoInt( effect.getValueAt(i, 0) ) ;
				tmp.needRank = SUtils.conobjtoInt( effect.getValueAt(i, 1) ) ;
				tmp.needExp = SUtils.conObjToStr( effect.getValueAt(i, 2) );
				tmp.addAttrStr = SUtils.conObjToStr( effect.getValueAt(i, 3) );
				list.add(tmp);
				System.out.println(tmp.info());
			}
			new SUtils().saveGongTier(curID,list);
		}
	};

	public void setXmlTool(XMLTool xmlTool) {
		this.xmlTool = xmlTool;
	}
	
	class ColorRender extends DefaultTableCellRenderer{
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(
					JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if(row%2==0){
					setBackground(new Color(240, 252, 255));
				}else{
					setBackground(new Color(227, 249, 253));
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
						row, column);
			}
	}


	public Map<String, Skill> getSkillmap() {
		return skillmap;
	}

	public void setSkillmap(Map<String, Skill> skillmap) {
		this.skillmap = skillmap;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	
}
