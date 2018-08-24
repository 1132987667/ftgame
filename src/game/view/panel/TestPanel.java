package game.view.panel;

import game.utils.Constant;
import game.utils.SUtils;
import game.view.TLabel;
import game.view.TTextPane;
import game.view.button.TButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class TestPanel extends JPanel {

	JTextField partField;

	JTextField rankField;

	JTextField typeOField;

	TButton jb;

	JLabel j;

	TButton jb2;

	Component panel = null;

	public JPanel oneP, twoP, SPP;

	private JLabel tili, jingli, li, min, lucky;
	private JLabel[] oneAttr = { tili, jingli, li, min, lucky };

	private JLabel hp, mp, atk, def;
	private JLabel[] twoAttr = { hp, mp, atk, def };

	private int fontSize = 14;
	private int attrName = fontSize * 5 / 2;
	private int attrValue = fontSize * 6 + 2;
	private int attrHeight = 16;
	public Font font = new Font("幼圆", Font.PLAIN, fontSize);

	public JPanel equipPanel = null;
	private JPanel equipPanelView ;
	private JLabel weapon, helmet, necklace, coat, ring, waistband, trousers,shoes;
	private JLabel[] equipAttr = { weapon, helmet, necklace, coat, ring, waistband, trousers,shoes } ;
	private JLabel weaponV, helmetV, necklaceV, coatV, ringV, waistbandV, trousersV,shoesV;
	private JLabel[] equipAttrValue = { weaponV, helmetV, necklaceV, coatV, ringV, waistbandV, trousersV,shoesV } ;
	
	
	
	
	/** 216+200 */
	public TestPanel() {
		setLayout(null);
		// setBounds(4,416,300,150);
		this.setBackground(Constant.colorAry[4]);
		this.setBorder(BorderFactory.createTitledBorder("测试面板"));

		oneP = new JPanel();
		oneP.setForeground(Color.black);
		oneP.setFont(font);
		oneP.setOpaque(false);
		oneP.setLayout(null);
		add(oneP);
		oneP.setBounds(100, 100, 20 + (attrName + attrValue) * 3,
				28 + 2 * fontSize);// 10 + (30+58)*3 + 10 = 284
		SUtils.setBorder(oneP, "一级属性", font);
		// oneP.setBorder(BorderFactory.createTitledBorder("一级属性"));
		int length = Constant.oneAttr.length;
		for (int i = 0; i < length; i++) {
			oneAttr[i] = new JLabel(Constant.oneAttr[i] + ":");
			oneP.add(oneAttr[i]);
			oneAttr[i].setAlignmentX(LEFT_ALIGNMENT);
			oneAttr[i].setFont(font);
			if (i < 3) {
				oneAttr[i].setBounds(10 + i * (attrName + attrValue), 14,
						attrName, attrHeight);
			} else {
				oneAttr[i].setBounds(10 + (attrName + attrValue) * (i - 3),
						14 + attrHeight, attrName, attrHeight);
			}
		}
		/** 二级属性 */
		twoP = new JPanel();
		twoP.setFont(font);
		twoP.setOpaque(false);
		twoP.setLayout(null);
		add(twoP);
		twoP.setBounds(100 + 20 + (attrName + attrValue) * 3, 100,
				20 + (attrName + attrValue) * 2, 28 + 2 * fontSize);// 10 +
																	// (30+58)*3
																	// + 10
		SUtils.setBorder(twoP, "二级属性", font);
		length = Constant.twoAttr.length;
		for (int i = 0; i < length; i++) {
			twoAttr[i] = new JLabel(Constant.twoAttr[i] + ":");
			twoP.add(twoAttr[i]);
			twoAttr[i].setAlignmentX(LEFT_ALIGNMENT);
			twoAttr[i].setFont(font);
			if (i < 2) {
				twoAttr[i].setBounds(10 + i * (attrName + attrValue), 14,
						attrName, attrHeight);
			} else {
				twoAttr[i].setBounds(10 + (attrName + attrValue) * (i - 2),
						14 + attrHeight, attrName, attrHeight);
			}
		}

		/*UIDefaults uiDefaults = UIManager.getLookAndFeelDefaults();
		Enumeration keys = uiDefaults.keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object val = uiDefaults.get(key);

			if (val instanceof Color) {
				System.out.println(key.toString());
			}
		}
*/
		partField = new JTextField(4);
		add(partField);
		partField.setBounds(19, 24, 40, 20);

		rankField = new JTextField(4);
		add(rankField);
		rankField.setBounds(59, 24, 40, 20);

		typeOField = new JTextField(4);
		add(typeOField);
		typeOField.setBounds(99, 24, 40, 20);

		jb = new TButton("create", -1);
		add(jb);
		jb.addMouseListener(jb);
		jb.addActionListener(ac);
		jb.setBorder(new EmptyBorder(0, 0, 0, 0));
		// jb.setBorder(new MetalBorders.ButtonBorder());
		// jb.setContentAreaFilled(false);
		jb.setFocusable(false);
		jb.setBounds(139, 24, 60, 20);

		/*
		 * ImageIcon img = new ImageIcon("src/game/img/line.jpg"); j = new
		 * JLabel(img) ; add(j); j.setBounds( 0,84,120,30 );
		 */
		for (int i = 1; i < 15; i++) {
			TButton t = new TButton("背包", i );
			t.addMouseListener(t);
			add(t);
			t.setBounds(20, 36 + i * 24, 72, 24);
		}
		
		TLabel tLabel = new TLabel("人物属性", 2);
		add(tLabel);
		tLabel.setLocation(20, 450);
		tLabel.setSize(122, 36);
		
		panel = this;

		JTextArea jt = new JTextArea(10, 10);
		add(jt);
		jt.setLocation(200, 60);
		jt.setAutoscrolls(true);
		jt.setBackground(Color.white);
		jt.setForeground(Color.black);
		jt.append("12345");
		jt.setForeground(Color.blue);
		jt.append("上山打老虎");
		jt.setForeground(Color.red);
		jt.append("老虎在哪里");

		String key = "TitledBorder.font";
		UIManager.put(key, new javax.swing.plaf.FontUIResource(font));
		String color = "TitledBorder.titleColor";
		UIManager.put(color, new javax.swing.plaf.ColorUIResource(Color.red));
		SwingUtilities.updateComponentTreeUI(oneP);

		equipPanel = new JPanel();
		add(equipPanel);
		equipPanel.setOpaque(false);
		equipPanel.setLayout(null);
		equipPanelView = new JPanel() ;
		equipPanel.add(equipPanelView);
		equipPanelView.setOpaque(false);
		equipPanelView.setLayout(null);
		/** 一列，8行 */
		equipPanelView.setBounds( 0, 0,
				20 + (attrName + attrValue) , 28 + 8*fontSize);
		SUtils.setBorder(equipPanelView, "角色装备", font);
		length = Constant.partDes.length;
		for (int i = 0; i < length; i++) {
			equipAttr[i] = new JLabel(Constant.partDes[i] + ":");
			equipPanelView.add(equipAttr[i]);
			equipAttr[i].setAlignmentX(LEFT_ALIGNMENT);
			equipAttr[i].setFont(font);
			if (i < 2) {
				equipAttr[i].setBounds(10 + i * (attrName + attrValue), 14,
						attrName, attrHeight);
			} else {
				equipAttr[i].setBounds(10 + (attrName + attrValue) * (i - 2),
						14 + attrHeight, attrName, attrHeight);
			}
		}
		
		TTextPane tt = new TTextPane(400,300);
		JScrollPane jsc = tt.getInstance();
		add(jsc);
		jsc.setSize(400, 300);
		jsc.setLocation(200, 200);
		jsc.setBackground(Color.red);
	}

	Point loc = null;

	Point tmp = null;

	boolean isDragged = false;

	private void setDragable(JFrame f) {

		this.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseReleased(java.awt.event.MouseEvent e) {

				isDragged = false;

				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			public void mousePressed(java.awt.event.MouseEvent e) {

				tmp = new Point(e.getX(), e.getY());

				isDragged = true;

				setCursor(new Cursor(Cursor.MOVE_CURSOR));

			}

		});

		this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			public void mouseDragged(java.awt.event.MouseEvent e) {

				if (isDragged) {

					loc = new Point(getLocation().x + e.getX() - tmp.x,

					getLocation().y + e.getY() - tmp.y);

					setLocation(loc);

				}

			}

		});

	}

	ActionListener ac = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ImageIcon image = new ImageIcon("src/game/img/button/type1.png");
			JOptionPane.showMessageDialog(panel, "", "测试", 0, image);
		}
	};

	public static void main(String[] args) {
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {

				}
			}
		}
		final JFrame f = new JFrame();
		f.setUndecorated(true);
		f.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				f.setExtendedState(f.ICONIFIED); // 最小化

				if (f.getExtendedState() != f.MAXIMIZED_BOTH)
					f.setExtendedState(f.MAXIMIZED_BOTH);
				else
					f.setExtendedState(f.NORMAL);
				System.exit(0);

				// 关闭，退出程序
			}
		});
		f.add(new TestPanel());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setVisible(true);
		new HashMap<>();
	}
}
