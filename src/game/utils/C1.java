package game.utils;

import game.control.GameControl;
import game.entity.Ditu;
import game.entity.Scene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class C1 extends JFrame{
		
	String[] nameAry = { "1","2","3","4" } ;
	JComboBox cb1 = null , cb2 = null ;
	List list = null ;
	String[] columnNames = {"name","des","pos","npcList"} ;
	GameControl gameControl = GameControl.getInstance();
	
	public C1() {
		JPanel jp = new JPanel();
		jp.setOpaque(false);
		this.getContentPane().setBackground(Color.white);
		
		JPanel jp1 = new JPanel();
		BoxLayout box = new BoxLayout(jp1, BoxLayout.X_AXIS);
		jp.setLayout(box);
		
		cb1 = new JComboBox(nameAry);
		cb1.setPreferredSize(new Dimension(80,24));
		cb1.setMaximumSize(new Dimension(80,24));
		cb1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String str = (String)e.getItem();
			}
		});
		jp1.add(cb1);
		
		JButton jb = new JButton("qry") ;
		jb.setPreferredSize(new Dimension(80,24));
		jp1.add(jb);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = (String)cb1.getSelectedItem();
				if(str.equals("fuben")){
					list = gameControl.loadFuben();
					JPanel jp1 = new JPanel() ;
				}
			}
		});
		
		JPanel jp2 = new JPanel();
		jp2.setBorder(new EmptyBorder(new Insets(8, 8, 8, 8)));
		jp.setLayout(new FlowLayout());
		jp2.add(new JLabel("name:"));
		JLabel nameV = new JLabel();
		jp2.add(nameV);
		jp2.add(new JLabel("   "));
		
		jp2.add(new JLabel("des:"));
		JLabel desV = new JLabel();
		jp2.add(nameV);
		jp2.add(new JLabel("   "));
		
		jp2.add(new JLabel("rank:"));
		JLabel rankV = new JLabel();
		jp2.add(nameV);
		jp2.add(new JLabel("   "));
		
		
		List<Ditu> fbList =  gameControl.loadFuben();
		Ditu f = fbList.get(0);
		List<Scene> l = f.scene ;
		
		String[][] ary = new String[columnNames.length][f.scene.size()] ;
		for (int i = 0; i < ary.length; i++) {
			Scene sc = l.get(i); 
			ary[i][0] = sc.name ;
			ary[i][1] = sc.des  ;
			ary[i][2] = sc.x+","+sc.y ;
			ary[i][3] = sc.npcStr ;
		}
		
		
		JPanel jp3 = new JPanel();
		DefaultTableModel model = new DefaultTableModel(columnNames, 10);  
		JTable table = new JTable(model); 
		JScrollPane jsc = new JScrollPane(table);
		jsc.setPreferredSize(new Dimension(400,200));
	        JTableHeader head = table.getTableHeader(); // 创建表格标题对象
	        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
	        head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
	        table.setRowHeight(18);// 设置表格行宽
		
		/*table.setModel(model);
		TableColumnModel columnModel = table.getColumnModel();// 获取列模型  
        int count = columnModel.getColumnCount();// 获取列数量  
        for (int i = 0; i < count; i++) {// 遍历列  
            TableColumn column = columnModel.getColumn(i);// 获取列对象  
            column.setPreferredWidth(40);// 以数组元素设置列的宽度  
            column.setHeaderValue(ary[i]);
        }  */
		
		jp3.add(jsc);
		
		jp.add(jp1);
		jp.add(jp2);
		jp.add(jp3);
		add(jp);
		
	}
	
	
	
	public static void main(String[] args) {
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
							
				}
			}
		}
		C1 frame = new C1();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200, 100, 1028, 512);
		//frame.setResizable(false);
		frame.setVisible(true);
	}
	
}