package game.utils.xmltool;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

import game.entity.Gong;
import game.entity.Skill;
import game.entity.Tier;
import game.utils.SUtils;

/**
 * 用来显示特殊信息的界面
 * 如 每层功法具体信息
 * 	  功法带来的技能
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
	/** 1-功法效果  2-技能 */
	private int roleType = 0 ;
	String[] effectAry = {"层数","等级","经验","属性加成","无"} ;
	String[] skillAry  = {"层数","技能Id","技能名","解锁条件","使用条件"} ;
	int[] effectHeadWidth = {40,80,80,380,380} ;//1000
	int[] skillHeadWidth = {60,60,200,200,200} ;//1000
	
	
	public SpecInfoFrame(int width, int height, int roleType) {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBackground(Color.white);
		setSize(width, height);
		this.roleType = roleType ;
		setUndecorated(false);
		setResizable(false);
		
		detailPanel = new JPanel();
		detailPanel.setLayout(null);
		detailPanel.setBackground(Color.white);
		setContentPane(detailPanel);
		
		JPanel jbPanel = new JPanel() ;
		jbPanel.setOpaque(false);
		jbPanel.setLayout(null);
		jbPanel.setBounds(4,0,500, 20);
		jl = new JLabel("功法:");
		jl.setFont(new Font("隶书", 0, 14));
		jl.setBounds(0, 0, 140, 20);
		JButton close = new JButton("关闭");
		close.setBounds(144, 0, 80, 20);
		JButton save = new JButton("保存");
		save.setBounds(312, 0, 80, 20);
		save.addActionListener(saveAc);
		JButton addBu = new JButton("新增");
		addBu.setBounds(228, 0, 80, 20);
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
		JButton deleteBu = new JButton("删除");
		deleteBu.setBounds(396, 0, 80, 20);
		deleteBu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		});
		jbPanel.add(jl);
		jbPanel.add(close);
		jbPanel.add(addBu);
		jbPanel.add(save);
		jbPanel.add(deleteBu);
		detailPanel.add(jbPanel);
		
		String[][] ary = new String[0][0] ;
		int w = width - 20 ;
		if(roleType==1) {
			tableModel = new DefaultTableModel(ary,effectAry);
		}else {
			tableModel = new DefaultTableModel(ary,skillAry);
		}
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
		for (int i = 0; i < effectHeadWidth.length; i++) {
			column = effect.getColumnModel().getColumn(i);
			if(roleType==1)
				column.setPreferredWidth(effectHeadWidth[i]);
			else
				column.setPreferredWidth(skillHeadWidth[i]);
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
	
	/**
	 * 保存按钮的监听
	 */
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
			if(roleType==1) {
				List<Tier> list = new ArrayList<>();
				Tier tmp = null ;
				for (int i = 0; i < length; i++) {
					if(effect.getValueAt(i, 0).toString().trim().length()==0){
						continue;
					}
					tmp = new Tier() ;
					tmp.curTier =  SUtils.conObjtoInt( effect.getValueAt(i, 0) ) ;
					tmp.needRank = SUtils.conObjtoInt( effect.getValueAt(i, 1) ) ;
					tmp.needExp = SUtils.conObjToStr( effect.getValueAt(i, 2) );
					tmp.addAttrStr = SUtils.conObjToStr( effect.getValueAt(i, 3) );
					list.add(tmp);
					System.out.println(tmp.info());
				}
				new SUtils().saveGongSkillOrEffect(roleType, curID, list, null);
			}else if(roleType==2){
				List<Skill> list = new ArrayList<>();
				Skill tmp = null ;
				for (int i = 0; i < length; i++) {
					if(effect.getValueAt(i, 0).toString().trim().length()==0){
						continue;
					}
					tmp = new Skill() ;
					/** "层数","技能Id","技能名","解锁条件","使用条件" */
					tmp.needTier  = SUtils.conObjtoInt( effect.getValueAt(i, 0) ) ;
					tmp.id        = SUtils.conObjToStr( effect.getValueAt(i, 1) ) ;
					tmp.name      = SUtils.conObjToStr( effect.getValueAt(i, 2) );
					tmp.studyCase = SUtils.conObjToStr( effect.getValueAt(i, 3) );
					tmp.useCase   = SUtils.conObjToStr( effect.getValueAt(i, 4) );
					list.add(tmp);
				}
				new SUtils().saveGongSkillOrEffect(roleType, curID, null, list);
			}
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
