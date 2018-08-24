package game.test;

import java.util.List;

import game.utils.SUtils;

import org.dom4j.Document;
import org.dom4j.Node;

public class T {
	public static void main(String[] args) {
		/*Document document = SUtils.load("src/game/xml/npc.xml");
		Node n = document.selectSingleNode("/root/npc[id='1003']/action/ac[@type='take']/take");
		System.out.println(n.getText());
		System.out.println(n.asXML());
		List<Node> node = document.selectNodes("/root/npc[id='1003']");
		System.out.println(node.get(0).asXML());*/
		
		int start = "need:[1001,,1];".indexOf("[");
		String temp = "need:[1001,,1];".substring(start+1,"need:[1001,,1]".length()-1);
		System.out.println(temp);
	}
	
	/**
	 * boolean f = true ;
		while(f){
			if(true){
				//f= false; 
				break;
			}
			System.out.println("?");
		}
	 * 
	 */
		/*UIs.setUI();
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
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
		
		JFrame j = new JFrame();
		final JPanel ft = new JPanel();
		ft.setBounds(0, 0, 580, 299);
		j.add(ft, BorderLayout.CENTER);
		ft.setLayout(null);
		
		JPanel info = new JPanel();
		info.setBounds(10, 6, 545, 119);
		j.add(info);
		
		final NPC npc = new NPC();
		npc.setHp(1000);
		npc.setAttack(100);
		npc.setDefense(50);
		npc.setBaoji(10);
		npc.setRank(10);

		final Player player = new Player();
		player.setHp(1000);
		player.setAttack(100);
		player.setDefense(50);
		player.setBaoji(10);
		player.setRank(10);

		*//** 玩家的血条 *//*
		myXue = new TLabel("", 4);
		TLabel myXueK = new TLabel("", 5);
		myXue.setBounds(inset, 45, 150, 11);
		myXueK.setBounds(inset, 44, 150, 13);
		ft.add(myXue);
		ft.add(myXueK);
		*//** 蓝条 *//*
		myFa = new TLabel("", 8);
		TLabel myFaK = new TLabel("", 5);
		myFa.setBounds(inset, myXue.getY() + 13, 100, 11);
		myFaK.setBounds(inset, myXueK.getY() + 13, 137, 13);
		ft.add(myFa);
		ft.add(myFaK);

		*//** 敌人血条 *//*
		foeXue = new TLabel("", 6);
		TLabel foeXueK = new TLabel("", 7);
		foeXue.setBounds(ft.getWidth() - inset - 150, 45, 150, 11);
		foeXueK.setBounds(ft.getWidth() - inset - 150, 44, 150, 13);
		ft.add(foeXue);
		ft.add(foeXueK);
		*//** 敌人法力条 *//*
		foeFa = new TLabel("", 8);
		TLabel foeFaK = new TLabel("", 5);
		foeFa.setBounds(ft.getWidth() - inset - 137, foeXue.getY() + 13, 100,
				11);
		foeFaK.setBounds(ft.getWidth() - inset - 137, foeXueK.getY() + 13, 137,
				13);
		ft.add(foeFa);
		ft.add(foeFaK);

		draw = new JPanel();
		draw.setSize(ft.getWidth(), ft.getHeight());
		draw.setOpaque(false);
		ft.add(draw);

		final Map<String, Object> map = new HashMap<>();
		map.put("myXue", myXue);
		map.put("foeXue", foeXue);
		map.put("info", ft);
		map.put("obj", obj);
		map.put("player", player);
		map.put("npc", npc);
		
		JButton jb = new JButton("啊哈");
		jb.setBounds(0, 0, 80, 24);
		ft.add(jb);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				g = draw.getGraphics();
				executor.scheduleAtFixedRate(new FtThread(1,  g,
						map), 100, 800, TimeUnit.MILLISECONDS);
				executor.scheduleAtFixedRate(new FtThread(2,  g,
						map), 500, 900, TimeUnit.MILLISECONDS);
			}
		});
		
		TTextPane text = new TTextPane(545, 600) ;
		JScrollPane jsc = text.getInstance();
		ft.add(jsc);
		jsc.setBounds(0, 99, 545, 200);
		
		
		

		*//** 设置背景图片 *//*
		JLabel back = new JLabel();
		ImageIcon image1 = new ImageIcon("src/game/img/back/1.png");
		back.setIcon(image1);
		ft.add(back);
		back.setBounds(0, 0, image1.getIconWidth(), image1.getIconHeight());

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setBounds(200, 100, 545, 299);
		j.setVisible(true);

	}

	static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
			8);

	static Object obj = new Object();
	static Graphics g = null;
	private JPanel fightJpanel;
	private JPanel ft = null;
	private static JPanel draw = null;
	private Player player = null;
	private static int inset = 8;

	private static TLabel myXue;

	private static TLabel myFa;

	private static TLabel foeXue;

	private static TLabel foeFa;

	public static void say(NPC npc) {
		Liehudashu lie = new Liehudashu();
		NPC n = new Liehudashu();
		say(lie);
		System.out.println(n.name);
		System.out.println(new Liehudashu().name);
		System.out.println(npc.name);
	}
*/
}
