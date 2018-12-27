package game.utils.xmltool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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

import game.control.GameControl;
import game.entity.Citiao;
import game.entity.Ditu;
import game.entity.Equip;
import game.entity.Gong;
import game.entity.NPC;
import game.entity.Scene;
import game.entity.Skill;
import game.entity.Tier;
import game.utils.C;
import game.utils.SUtils;
import game.utils.XmlUtils;

public class XMLTool extends JFrame{
	private static final long serialVersionUID = 1L;
	/***  主面板,jp1[查询菜单]         */
	private JPanel rootpane,jp1 ,jp2, gongEffectPanel ;
	private int inset = 9 ;
	/** 查询条件 */
	private JTextField name,des,rank ;
	JLabel temp ;
	private static JTable table ;
	private static JTable gongEffect;
	private SUtils sUtils = new SUtils(); 
	
	private DefaultTableModel tableModel ;
	private static DefaultTableModel gongModel = null;
	
	private JComboBox<String> cb1 = null , cb2 = null ;
	private String[] defFun = {"副本","装备","npc","功法","技能"} ;
	private int curInx = 0 ;
	private JScrollPane jsc ;
	private static JLayeredPane layeredPanel = null ;
	private static XMLTool frame ;
	private static SpecInfoFrame gongInfoFrame ;
	private static SpecInfoFrame skillInfoFrame ;

	/** 功法储存 */
	private static Map<String, Gong> gongMap ;
	/** 技能储存 */
	private Map<String, Skill> skillmap = null ;
	
	/** 加载完时的大小 */
	private int initSize = 0 ;
	
	int w = 960 ;
	int h = 720 ;
	
	public XMLTool() {
		setSize(new Dimension(w, h));
		rootpane = new JPanel() ;
		setContentPane(rootpane);
		rootpane.setBackground(Color.white);
		rootpane.setLayout(null);
		rootpane.setSize(w, h);
		
		jp1 = new JPanel() ;
		jp1.setOpaque(false);
		jp1.setBounds(inset, inset, w, 24 );
		jp1.setLayout(null);
		
		cb1 = new JComboBox<String>(defFun);
		cb1.setMaximumSize(new Dimension(92,24));
		/** removeAllItems[移除所有下拉框选项],新增选项 addItem*/	
		
		cb2 = new JComboBox<String>();
		
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
		
		tableModel = new DefaultTableModel();
		
		jp2 = new JPanel() ;
		jp2.setOpaque(false);
		jp2.setLocation(-240, 34);
		jp2.setSize(w-2*inset, 34);
		temp = new JLabel("name:");
		temp.setLocation(0, 0);
		name = new JTextField();
		name.setPreferredSize(new Dimension(50, 30));
		name.setLocation(temp.getX()+temp.getWidth(), 0);
		jp2.add(temp);
		jp2.add(name);
		temp = new JLabel("des");
		temp.setLocation(name.getX()+name.getWidth(), 0);
		des = new JTextField();
		des.setPreferredSize(new Dimension(290, 30));
		des.setLocation(temp.getX()+temp.getWidth(), 0);
		jp2.add(temp);
		jp2.add(des);
		temp = new JLabel("des");
		temp.setLocation(des.getX()+des.getWidth(), 0);
		rank = new JTextField();
		rank.setPreferredSize(new Dimension(40, 30));
		rank.setLocation(temp.getX()+temp.getWidth(), 0);
		jp2.add(temp);
		jp2.add(des);
		
		jsc = new JScrollPane(null);
		jsc.setPreferredSize(new Dimension(w*2,( h-30)*2));
		jsc.setBounds(inset, jp2.getY()+jp2.getHeight()+inset, w-30, h-(jp2.getY()+jp2.getHeight()+inset)-80 );
		//jsc.setOpaque(false);
		//jsc.getViewport().setOpaque(false);
		jsc.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		rootpane.add(jp1);
		rootpane.add(jp2);
		rootpane.add(jsc);
		
		layeredPanel = this.getLayeredPane() ;
		layeredPanel.setLayout(null);
		layeredPanel.setBackground(Color.red);
		
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
		frame = new XMLTool();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
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
				if (table.isEditing()) {
					TableCellEditor cellEditor = table.getCellEditor();
					if (cellEditor != null) 
						cellEditor.stopCellEditing();
				}
				//table.getCellEditor().stopCellEditing();
				if(curInx==1){
					int length = table.getRowCount() ;
					List<Scene> list = new ArrayList<>();
					Scene temp = null ;
					for (int i = 0; i < length; i++) {
						temp = new Scene();
						temp.name = (String) table.getValueAt(i, 0);
						temp.des = (String) table.getValueAt(i, 1);
						String str = (String) table.getValueAt(i, 2);
						String[] ary = str.split(",");
						temp.x = SUtils.conStrtoInt(ary[0]);
						temp.y = SUtils.conStrtoInt(ary[1]);
						temp.npcStr = (String) table.getValueAt(i, 3);
						list.add(temp);
					}
					String[] ary = rank.getText().split(",");
					int rankL = SUtils.conStrtoInt(ary[0]);
					int rankR = SUtils.conStrtoInt(ary[1]);
					Ditu fb = new Ditu(0+"",name.getText(), des.getText(), rankL, rankR);
					fb.setScene(list); 
					sUtils.saveDituInfo("", curInx, fb);
				}else if(curInx==3){
					int length = table.getRowCount() ;
					System.out.println("保存功法信息,共:"+length+"条！");
					List<Gong>  gongList= new ArrayList<>() ; 
					Gong tmpGong = null; 
					for (int i = 0; i < length; i++) {
						tmpGong = new Gong();
						tmpGong.setId(SUtils.conObjToStr(table.getValueAt(i, 0)));
						tmpGong.setName(SUtils.conObjToStr(table.getValueAt(i, 1)));
						tmpGong.setDes(SUtils.conObjToStr(table.getValueAt(i, 2)));
						tmpGong.setQua(SUtils.conGongQua(table.getValueAt(i, 3).toString()));
						tmpGong.setMaxTier(SUtils.conStrtoInt(table.getValueAt(i, 4).toString()));
						tmpGong.setType(SUtils.conStrtoInt(table.getValueAt(i, 5).toString()));
						tmpGong.setNeedRank(SUtils.conStrtoInt(table.getValueAt(i, 6).toString()));
						tmpGong.setEffectStr(SUtils.strTrim(table.getValueAt(i, 7).toString()));
						tmpGong.setRequire(table.getValueAt(i, 8).toString());
						tmpGong.setPrice(SUtils.conStrtoInt(table.getValueAt(i, 9).toString()));
						tmpGong.setCurTier(1);
						tmpGong.setNum(1);
						gongList.add(tmpGong);
					}
					Collections.sort(gongList);
					for (int j = 0; j < gongList.size(); j++) {
						System.out.println("排序后:"+gongList.get(j).getId());
					}
					sUtils.saveGongInfo(gongList);
				}else if(curInx==4){
					int length = table.getRowCount() ;
					System.out.println("保存技能信息,共:"+length+"条！");
					List<Skill>  gongList= new ArrayList<>() ; 
					Skill tmpGong = null; 
					for (int i = 0; i < length; i++) {
						tmpGong = new Skill();
						tmpGong.id = SUtils.strTrim(table.getValueAt(i, 0));
						tmpGong.name = SUtils.strTrim(table.getValueAt(i, 1));
						tmpGong.des = SUtils.strTrim(table.getValueAt(i, 2));
						String type = SUtils.strTrim(table.getValueAt(i, 3)) ;
						tmpGong.type = type.equals("")?"主动":type;
						tmpGong.consume = SUtils.strTrim(table.getValueAt(i, 4));
						String target = SUtils.strTrim(table.getValueAt(i, 6));
						String scope = SUtils.strTrim(table.getValueAt(i, 7)); 
						tmpGong.scope = scope.equals("")?"单体":scope ;
						String gongId = SUtils.strTrim(table.getValueAt(i, 8)); ;
						tmpGong.gongId = gongId.equals("")?"2000":gongId ;
						String gongName = SUtils.strTrim(table.getValueAt(i, 9)); 
						tmpGong.gongName = gongName.equals("")?"未定":gongName ;
						String useCase = SUtils.strTrim(table.getValueAt(i, 10)) ;
						tmpGong.useCase = useCase.equals("")?"无":useCase ;
						String studyCase = SUtils.strTrim(table.getValueAt(i, 11));
						tmpGong.studyCase = studyCase.equals("")?"无":studyCase ;
						String needTier = SUtils.strTrim(table.getValueAt(i, 12));
						tmpGong.needTier = needTier.equals("")?0:SUtils.conStrtoInt(needTier);
						String cd = SUtils.strTrim(table.getValueAt(i,13)) ;
						tmpGong.cd = cd.equals("")?0:SUtils.conStrtoInt(cd);
						tmpGong.curCd = 0 ;
						gongList.add(tmpGong);
					}
					sUtils.saveSkillInfo(gongList);
				}
				
			}
			if(e.getActionCommand().equals("sure")){
				curInx = cb1.getSelectedIndex() ;
				if(curInx==1){
					setEquipTable();
				}else if(curInx==2){
					setNpcTable();
				}else if(curInx==3){//功法
					setGongTable();
				}else if(curInx==4){
					setSkillTable();
				}
				
			}
			//table.revalidate();
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
		String[] tableHead = {"id","name","介绍","品质","rank","词条1","词条2","词条3","词条4","词条5"} ; 
		int[] headWidth = {50,100,440,50,76,130,130,130,130,130} ;
		setTable(tableHead, headWidth);
		/** 重新设置 数据 */
		String key = null;
		Equip value = null;
		GameControl ganecControl = GameControl.getInstance();
		Map<String, Equip> equipMap = ganecControl.getEquipMap();
		//System.out.println("equipMap:"+equipMap);
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
		jsc.setViewportView(table);
	}
	
	public void setFubenTable(){
		String[] tableHead = {"name","des","pos","npc"} ; 
		int[] headWidth = {50,360,50,100} ;
		setTable(tableHead, headWidth);		
		List<Ditu> fbList =  new XmlUtils().loadFuben();
		String[] fbAry = new String[fbList.size()];
		for (int i = 0; i < fbAry.length; i++) {
			fbAry[i] = fbList.get(i).getName();
		}
		Ditu f = fbList.get(cb2.getSelectedIndex());
		List<Scene> l = f.getScene() ;
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
		jsc.setViewportView(table);
	}
	
	/** 1360 */
	public void setNpcTable(){
		String[] tableHead = {"id","name","attrType","des","msg","rank","type","action"} ; 
		int[] headWidth = {50,100,80,300,300,80,80,300} ;
		setTable(tableHead, headWidth);		
		/** 重新设置 数据 */
		String key = null;
		NPC value = null;
		Map<String, NPC> equipMap = sUtils.loadNpc();
		Iterator<String> iter = equipMap.keySet().iterator();
		String[] textAry = null ;
		/** 填充表格 */
		/** id","name","attrType","des","msg","rank","type","action */
		while (iter.hasNext()) {
			key = iter.next();
			value = equipMap.get(key);
			textAry = new String[8];
			textAry[0] = value.getId();
			textAry[1] = value.getName();
			textAry[2] = value.getAttrType();
			textAry[3] = value.getDes()+"";
			textAry[4] = value.getMsg()+"";
			textAry[5] = value.getRank()+"";
			textAry[6] = value.getType()+"";
			textAry[7] = "";
			tableModel.addRow(textAry);
		}
		jsc.setViewportView(table);
	}
	
	
	/** 设置功法表 1360 */
	public void setGongTable(){
		String[] tableHead = {"id","功法名","描述","品质","max层数","type","need等级","加成",  "require","价格","当前层数","数量","技能","属性"} ; 
		int[] headWidth    = {90,  160,   300,   90,  120,     90,    120,     200,     120,     90,   90,     90,   100, 100  } ;
		setTable(tableHead, headWidth);		
		/** 重新设置 数据 */
		String key = null;
		Gong value = null;
		gongMap = sUtils.loadGong();
		List<Gong> gongList = new ArrayList<>(gongMap.values());
		initSize = gongMap.size() ;
		Collections.sort(gongList);
		String[] textAry = null ;
		/** 填充表格 */
		/** id","name","attrType","des","msg","rank","type","action */
		for (int i = 0; i < gongList.size(); i++) {
			value = gongList.get(i);
			textAry = new String[20];
			textAry[0] = value.getId();
			textAry[1] = value.getName();
			textAry[2] = value.getDes();
			textAry[3] = C.gongQu[value.getQua()];
			textAry[4] = value.getMaxTier()+"";
			textAry[5] = value.getType()+"";
			textAry[6] = value.getNeedRank()+"";
			textAry[7] = value.getEffectStr()+"";
			textAry[8] = value.getRequire() ;
			textAry[9] = value.getPrice()+"" ;
			textAry[10] = value.getCurTier()+"" ;
			textAry[11] = value.getNum()+"" ;
			tableModel.addRow(textAry);
		}
		table.getColumnModel().getColumn(12).setCellEditor(new MyRender(2));
		table.getColumnModel().getColumn(12).setCellRenderer(new MyRender(2));
		table.getColumnModel().getColumn(13).setCellEditor(new MyRender(1));
		table.getColumnModel().getColumn(13).setCellRenderer(new MyRender(1));
		jsc.setViewportView(table);
		setGongEffectView();
		setSkillEffectView();
	}
	
	/** 设置功法表 1360 */
	public void setSkillTable(){
		String[] tableHead = {"id","name","des","品质","类型","范围","消耗","效果","max等级","伤害系数","cur等级","功ID","Gong名","使用条件","学习条件","CD","curCD"} ; 
		int[] headWidth =    {80,  100,   300,  100,   100,   100,   100, 200,  120,     150,     150,      100,   120,    200,     200,    80,     100} ;
		setTable(tableHead, headWidth);		
		/** 重新设置 数据 */
		String key = null;
		Skill value = null;
		//skillmap = sUtils.loadSkill();
		initSize = skillmap.size() ;
		Iterator<String> iter = skillmap.keySet().iterator();
		String[] textAry = null ;
		/** 填充表格 */
		/** id","name","attrType","des","msg","rank","type","action */
		while (iter.hasNext()) {
			key = iter.next();
			value = skillmap.get(key);
			textAry = new String[18];
			textAry[0] = value.id;
			textAry[1] = value.name ;
			textAry[2] = value.des ;
			textAry[3] = value.qua+"" ;
			textAry[4] = value.type ;
			textAry[5] = value.scope ;
			textAry[6] = value.consume ;
			textAry[7] = value.effectStr ;
			textAry[8] = value.skillMaxRank+"";
			textAry[9] = value.baseDamageRatio+"" ;
			textAry[10] = value.skillCurRank +"";
			textAry[11] = value.gongId ;
			textAry[12] = value.gongName+"" ;
			textAry[13] = value.useCase+"" ;
			textAry[14] = value.studyCase+"" ;
			textAry[15] = value.needTier+"" ;
			textAry[16] = value.cd+"" ;
			textAry[17] = value.curCd+"" ;
			tableModel.addRow(textAry);
		}
		setGongEffectView();
	}
	
	/***
	 * 重新创建表
	 * @param tableHead 表头信息
	 * @param headWidth 每列的宽度
	 */
	private void setTable(String[] tableHead,int[] headWidth){
		String[][] ary = new String[0][0] ;
		tableModel = new DefaultTableModel(ary, tableHead){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				 if(column != 0){  
				       return true;  
				    }else{
				    	if(row+1 > initSize){
				    		return true ;
				    	}
				       return false;  
				    } 
			}
		};
		table = new JTable(tableModel);// 创建表格组件
		table.setAutoResizeMode(0);
		table.setSize(1200, 600);
		table.setPreferredSize(new Dimension(1200, 1200));
		jsc.setViewportView(table);
		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
		table.setRowSorter(sorter);
		int headW = 0 ;
		for (int i = 0; i < headWidth.length; i++) {
			headW+=headWidth[i];
		}
		JTableHeader head = table.getTableHeader(); // 创建表格标题对象
		head.setPreferredSize(new Dimension(headW, 24));
		head.setFont(new Font("微软雅黑", Font.PLAIN, 14));// 设置表格字体
		table.setRowHeight(20);// 设置表格行宽
		TableColumn column = null ;
		for (int i = 0; i < headWidth.length; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(headWidth[i]);
		}
		table.setCellSelectionEnabled(false);
		//tableModel.fireTableDataChanged();
		
		/*table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
					Point mousepoint;
					mousepoint =e.getPoint(); //获取鼠标位置
					int k = table.rowAtPoint(mousepoint);  //此方法返回行号
					table.setRowSelectionInterval(k,k); //设置为选中此行 
					System.out.println("当前选中行为:"+k);
			}
		});*/

	}
	
	/** 重新设置功法的信息 */
	public static void setGongEffectInfo(int tier){
		gongInfoFrame.setVisible(true);
		/** 通过id得到功法 */
		gongModel.setRowCount( 0 );
		Gong gong = gongMap.get(table.getValueAt(tier, 0)); 
		gongInfoFrame.setGong(gong);
		gongInfoFrame.jl.setText("功法:"+gong.getName());
		List<Tier> tierList = gong.getTiers();
		System.out.println("该功法一共"+tierList.size()+"层");
		String tempAry[] = null ;
		for (int i = 0; i < tierList.size(); i++) {
			tempAry = new String[8];
			tempAry[0] = tierList.get(i).curTier+"";
			tempAry[1] = tierList.get(i).needRank+"";
			tempAry[2] = tierList.get(i).needExp+"";
			tempAry[3] = tierList.get(i).addAttrStr ;
			gongModel.addRow(tempAry);
		}
	}
	
	/** 设置功法详细界面信息 */
	public void setGongEffectView(){
		/** 功法详细信息 */
		gongInfoFrame = new SpecInfoFrame(1000, 400, 1);
		gongInfoFrame.setXmlTool(this);
		gongInfoFrame.setGongMap(gongMap);
		gongModel = gongInfoFrame.tableModel ;
		gongEffect = gongInfoFrame.effect ;
	}
	
	public void setSkillEffectView(){
		skillInfoFrame = new SpecInfoFrame(750, 360, 2);
		skillInfoFrame.setXmlTool(this);
		skillInfoFrame.setSkillmap(skillmap);
		gongModel = skillInfoFrame.tableModel ;
		gongEffect = skillInfoFrame.effect ;
	}
	
	/** 重新设置功法的信息 */
	public static void setSkillEffectInfo(int row){
		skillInfoFrame.setVisible(true);
		/** 清空表格 */
		gongModel.setRowCount(0);
		String id = SUtils.conObjToStr(table.getValueAt(row, 0));
		System.out.println("得到当前点击技能ID:"+id);
		/** 通过id得到功法 */
		Gong gong = gongMap.get(id); 
		skillInfoFrame.setGong(gong);
		skillInfoFrame.jl.setText("功法:"+gong.getName());
		gongModel = skillInfoFrame.tableModel ;
		List<Skill> skillList = gong.getAllSkills();
		System.out.println("该功法一共"+skillList.size()+"层");
		String tempAry[] = null ;
		for (int i = 0; i < skillList.size(); i++) {
			tempAry = new String[5];
			tempAry[0] = skillList.get(i).needTier+"";
			tempAry[1] = skillList.get(i).id+"";
			tempAry[2] = skillList.get(i).name ;
			tempAry[3] = skillList.get(i).studyCase ;
			tempAry[4] = skillList.get(i).useCase ;
			gongModel.addRow(tempAry);
		}
	}
	
	public void updateGong(){
		gongMap = sUtils.loadGong() ;
	}
	
}
