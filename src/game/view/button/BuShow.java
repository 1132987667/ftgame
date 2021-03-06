package game.view.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import game.utils.C;
import game.view.ui.Field;

public class BuShow extends JPanel {
	private static final long serialVersionUID = 5655277908824666596L;

	private Field text;
	private PicBu bu;
	private int width = 20, height = 30;

	public BuShow() {
		setLayout(null);
		this.setBackground(C.colorAry[4]);
		this.setBorder(BorderFactory.createTitledBorder("全部按钮"));
		
		List<Font> fonts = getFontList();
		for (int i = 0; i < 33; i++) {
			if (height >= 540) {
				width += 200;
				height -= 540;
			}
			text = new Field("按钮" + i, 1, Color.BLACK);
			text.setSize(50, 20);
			text.setLocation(width, height);
			bu = new PicBu("修炼", i);
			bu.setSize();
			height += bu.getHeight() + 6;
			System.out.println(height);
			bu.setLocation(width + 50, text.getY());
			/*
			 * if(i<24) { bu.setFont(fonts.get(i)); }
			 */
			add(text);
			add(bu);
		}

		JLabel jl = new JLabel("xxxxxxxxxxxxxxxxxxxxxxxxxx");
		jl.setBackground(Color.red);
		jl.setSize(80, 20);
		jl.setPreferredSize(new Dimension(80, 20));
		jl.setOpaque(true);
		add(jl);
		jl.setLocation(700, 500);

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {

		}
		final JFrame f = new JFrame();
		// f.setUndecorated(true);
		f.setContentPane(new BuShow());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setVisible(true);
	}

	public List<Font> getFontList() {
		List<Font> list = new ArrayList<>();
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		for (int i = 214; i < fonts.length; i++) {
			list.add(new Font(fonts[i], Font.PLAIN, 14));
			//System.out.println(fonts[i]);
		}
		return list;
	}

}
