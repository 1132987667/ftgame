package game.view.panel;

import game.control.GameControl;
import game.entity.Ditu;
import game.utils.SUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FubenPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JScrollPane jsc = null;
	private JPanel contentPane = null;
	private int inset = 8;
	private JButton tempBu = null;
	private List<JButton> tempBuList = null;
	private JPanel showPanel = null;
	private GameControl gameControl = null ;
	public JLabel name, rank, msg;
	private JLabel[] info = { name, rank, msg };
	private String[] ary = { "副本名:", "适合等级:", "简介:" };
	private JTextArea text = null;
	private int fontSize = 13;
	private Font f1 = new Font("楷体", Font.BOLD, fontSize);
	private Font f2 = new Font("幼圆", Font.PLAIN, fontSize);
	private Ditu fb = null ;
	private List<Ditu> list  ;
	private JButton jb ;
	
	public FubenPanel() {
		this.setBounds(139, 76, 612, 370);
		gameControl = GameControl.getInstance() ;
		setLayout(null);
		// setOpaque(false);
		setBackground(Color.white);
		/** 加载副本列表 */
		list = gameControl.loadFuben();
		int length = list.size();
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setPreferredSize(new Dimension(length * (146 + 6), 127));
		// 设置边框
		contentPane.setBorder(null);
		jsc = new JScrollPane(contentPane);
		jsc.setOpaque(false);
		jsc.getViewport().setOpaque(false);
		add(jsc);
		jsc.setBounds(0, 0, 612, 160);
		tempBuList = new ArrayList<>();
		/** 显示副本 */
		for (int i = 0; i < length; i++) {
			tempBu = new JButton(list.get(i).name);
			tempBu.setOpaque(false);
			contentPane.add(tempBu);
			tempBu.setBounds(0, 0, 146, 127);
			tempBu.addMouseListener(new Listenr(tempBu, list.get(i)));
			tempBuList.add(tempBu);
		}

		/** 显示副本信息 */
		showPanel = new JPanel();
		add(showPanel);
		showPanel.setLayout(null);
		showPanel.setBounds(0, 155, 296, 210);
		showPanel.setOpaque(false);
		JLabel temp = null;
		for (int i = 0; i < ary.length; i++) {
			temp = new JLabel(ary[i]);
			temp.setForeground(Color.white);
			temp.setFont(f1);
			temp.setBounds(inset, inset + i * (fontSize + inset),
					ary[i].length() * fontSize + fontSize / 2, fontSize + inset);
			showPanel.add(temp);
			if (i != 2) {
				info[i] = new JLabel();
				info[i].setForeground(new Color(195,39,43));
				info[i].setFont(new Font("隶书",0,18));
				info[i].setBounds(inset + temp.getWidth(), inset + i
						* (fontSize + inset), fontSize * 10, fontSize + inset);
				showPanel.add(info[i]);
			}
		}
		text = new JTextArea();
		text.setEditable(false);
		text.setOpaque(false);
		text.setLineWrap(true); // 激活自动换行功能
		text.setBorder(new EmptyBorder(6, 6, 6, 6));
		text.setBackground(new Color(66, 76, 80));
		text.setForeground(Color.white);
		text.setFont(f1);
		text.setPreferredSize(new Dimension(276, 120));
		text.setBounds(inset, temp.getY() + temp.getHeight() + inset, 276, 120);
		text.setText("丹崖怪石，削壁奇峰。丹崖上，彩凤双鸣；削壁前，麒麟独卧。峰头时听锦鸡鸣，石窟每观龙出入。林中有寿鹿仙狐，树上有灵禽玄鹤。瑶草奇花不谢，青松翠柏长春。仙桃常结果，修竹每留云。一条涧壑藤萝密，四面原堤草色新。");
		showPanel.add(text);

		JLabel label = new JLabel("");
		ImageIcon img = new ImageIcon("src/game/img/back/backB.png");
		label.setIcon(img);
		showPanel.add(label, BorderLayout.CENTER);
		label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		
		/** 确认按钮 */
		jb = new JButton("<html>确<br>认</html>");
		img = new ImageIcon("src/game/img/button/buD.png");
		jb.setIcon(img);
		// 设置字体颜色
		jb.setForeground(Color.white);
		// 设置字体大小 和 格式
		jb.setFont(new Font("隶书", Font.PLAIN, 20));
		// 设置透明
		jb.setOpaque(false);// 是否透明 false透明
		// 图片填充所在区域
		jb.setContentAreaFilled(false);// 设置图片填充所在区域
		// 设置与四周的间距
		jb.setMargin(new Insets(0, 0, 0, 0));
		// 设置是否绘制边框
		jb.setBorderPainted(false);
		// 设置边框
		jb.setBorder(null);
		// 居中显示
		jb.setHorizontalTextPosition(SwingConstants.CENTER);
		jb.setVerticalTextPosition(SwingConstants.CENTER);
		add(jb);
		jb.setBounds(300, 160, 560, 210);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fb==null){
					text.setText("请先选择一个要去往的副本!");
					text.setForeground(Color.red);
				}else{
					System.out.println("副本选择完毕！");
					gameControl.close(1);
					gameControl.restore();
					gameControl.setSelect(fb);
					gameControl.setNpcSpecInfo(fb);
					gameControl.createMap();
				}
			}
		});
	}

	/** 副本信息 */
	public void initData() {
		for (int i = 0; i < tempBuList.size(); i++) {
			contentPane.remove(tempBuList.get(i));
		}
		list = gameControl.loadFuben();
		int length = list.size();
		contentPane.setPreferredSize(new Dimension(length * (146 + 6), 127));
		tempBuList = new ArrayList<>();
		for (int i = 0; i < length; i++) {
		  tempBu = new JButton(list.get(i).name);
		  tempBu.setOpaque(false);
		  contentPane.add(tempBu);
		  tempBu.setBounds(0, 0, 146, 127);
		  tempBu.addMouseListener(new Listenr(tempBu, list.get(i)));
		  tempBuList.add(tempBu);
		} 
		info[0].setText("");
		info[1].setText("");
		text.setText("");
	}
	
	class Listenr extends MouseAdapter {
		private Ditu fuben = null;
		public JButton jb = null;
		public boolean flag = false;
		ImageIcon image1 = new ImageIcon("src/game/img/button/Fuben1.png");
		ImageIcon image2 = new ImageIcon("src/game/img/button/Fuben2.png");
		ImageIcon image3 = new ImageIcon("src/game/img/button/Fuben3.png");

		public Listenr(JButton jb, Ditu fuben) {
			this.jb = jb;
			this.fuben = fuben;
			jb.setIcon(image1);
			// 设置字体颜色
			jb.setForeground(Color.white);
			// 设置字体大小 和 格式
			jb.setFont(new Font("隶书", Font.PLAIN, 18));
			// 设置透明
			jb.setOpaque(false);// 是否透明 false透明
			// 图片填充所在区域
			jb.setContentAreaFilled(false);// 设置图片填充所在区域
			// 设置与四周的间距
			jb.setMargin(new Insets(0, 0, 0, 0));
			// 设置是否绘制边框
			jb.setBorderPainted(false);
			// 设置边框
			jb.setBorder(null);
			// 居中显示
			jb.setHorizontalTextPosition(SwingConstants.CENTER);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			for (int i = 0; i < tempBuList.size(); i++) {
				tempBuList.get(i).setIcon(image1);
			}
			fb = fuben ;
			flag = true;
			jb.setIcon(image2);
			/** 更改显示信息 */
			info[0].setText(fuben.name);
			info[1].setText(fuben.rankL + "-" + fuben.rankR);
			text.setForeground(Color.white);
			text.setText(fuben.des);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (!flag) {
				jb.setIcon(image3);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (!flag) {
				jb.setIcon(image1);
			}
		}
	}
}
