package game.test;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 展示如何使用DefaultTableModel类创建表格
 * 
 * @author burns
 * 
 */
public class Test8 implements ActionListener {

    JTable table = null;
    DefaultTableModel defaultModel = null;

    public Test8() {
        JFrame f = new JFrame();
        Object[][] p = {
                { "王鹏", new Integer(91), new Integer(1949), new Integer(1910) },
                { "朱学莲", new Integer(82), new Integer(1969), new Integer(1510) },
                { "梅婷", new Integer(47), new Integer(1957), new Integer(1040) },
                { "赵龙", new Integer(61), new Integer(1957), new Integer(1180) },
                { "李兵", new Integer(90), new Integer(1987), new Integer(1770) }, };
        String[] n = { "姓名", "工号", "出生年月", "薪水" };
        // 创建一个默认的表格模型
        defaultModel = new DefaultTableModel(p, n);
        table = new JTable(defaultModel);
        table.setPreferredScrollableViewportSize(new Dimension(400, 80));
        JScrollPane s = new JScrollPane(table);
        JPanel panel = new JPanel();
        JButton b = new JButton("增加行");
        panel.add(b);
        panel.add(b);
        b.addActionListener(this);
        b = new JButton("删除行");
        panel.add(b);
        panel.add(b);
        b.addActionListener(this);

        Container contentPane = f.getContentPane();
        contentPane.add(panel, BorderLayout.NORTH);
        contentPane.add(s, BorderLayout.CENTER);
        f.setTitle("AddRemoveCells");
        f.pack();
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("增加行")) {
            // 如果点击"增加行"按钮后，会在表中增加一行
            defaultModel.addRow(new Vector());
        }
        if (e.getActionCommand().equals("删除行")) {
            // 如果点击"删除行"按钮后，会在表中删除所选中的一行，并且设置下一行为当前行
            int rowcount = defaultModel.getRowCount() - 1;
            // getRowCount返回行数，rowcount<0代表已经没有行了
            if (rowcount >= 0) {
                defaultModel.removeRow(rowcount);
                defaultModel.setRowCount(rowcount);
                /**
                 * 删除行比较简单，只要用DefaultTableModel的removeRow方法即可
                 * 删除行完毕后必须重新设置列数，也就是使用DefaultTableModel的setRowCount()方法来设置当前行
                 */
            }
        }
        table.revalidate();
    }

    public static void main(String[] args) {
        new Test8();
    }

}