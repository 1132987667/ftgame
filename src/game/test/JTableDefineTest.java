package game.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class JTableDefineTest extends JFrame {

	public JTableDefineTest() {
		super();
		setTitle("表格");
		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane scrollPane = new JScrollPane(); // 支持滚动
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		String[] columnNames = { "A", "B", "C", "D", "E", "F", "G" };
		Vector columnNameV = new Vector(); // 获得表头
		for (int column = 0; column < columnNames.length; column++) {
			columnNameV.add(columnNames[column]);
		}
		Vector tableValueV = new Vector();
		for (int row = 1; row < 21; row++) // 获得数据
		{
			Vector rowV = new Vector();
			for (int column = 0; column < columnNames.length; column++) {
				rowV.add(columnNames[column] + row); // 数据
			}
			tableValueV.add(rowV);
		}
		JTable table = new MyTable(tableValueV, columnNameV); // 自定义的表格
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 关闭表格列的自动调整功能。
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionForeground(Color.RED);
		table.setRowHeight(30);
		scrollPane.setViewportView(table); // 支持滚动

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JTableDefineTest jTableDefineTest = new JTableDefineTest();
		jTableDefineTest.setVisible(true);
	}

}

class MyTable extends JTable // 实现自定义类
{

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public MyTable() {

	}

	public MyTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
	}

	/**
	 * @Override
	 */
	public JTableHeader getTableHeader() {
		JTableHeader tableHeader = super.getTableHeader();
		tableHeader.setReorderingAllowed(false); // 设置表格列不可重排
		DefaultTableCellRenderer hr = (DefaultTableCellRenderer) tableHeader
				.getDefaultRenderer(); // 获得表格头的单元格对象
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER); // 列名居中
		return tableHeader;

	}

	/**
	 * @Override
	 */
	public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
		DefaultTableCellRenderer cr = (DefaultTableCellRenderer) super
				.getDefaultRenderer(columnClass);
		cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER); // 单元格内容居中
		return cr;
	}

	/**
	 * @Override
	 */
	public boolean isCellEditable(int row, int column) {
		return false; // 单元格不可修改
	}
}
