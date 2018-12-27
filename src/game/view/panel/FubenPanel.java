package game.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.multi.MultiScrollBarUI;

import game.control.GameControl;
import game.entity.Ditu;
import game.utils.C;
import game.utils.SUtils;
import game.utils.XmlUtils;
import game.view.button.TButton;
import game.view.ui.TTextArea;
import game.view.ui.TextField;
/**
 * 进行副本和章节选择的面板
 * @author yilong22315
 */
public class FubenPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	/** 组件 */
	private JScrollPane jsc = null;
	private JPanel contentPane = null;
	/** 显示当前点击地图信息 */
	private JPanel selectInfo = new JPanel();
	private TButton dituBu = null;
	private List<TButton> buList = null;
	private GameControl gameControl = GameControl.getInstance() ;
	public  TextField name, rank, msg;
	private TextField[] info = { name, rank, msg };
	private String[] dituInfo = { "副本名:", "适合等级:", "简介:" };
	private TTextArea text = null;
	private int fontSize = 14;
	private Ditu ditu = null ;
	private List<Ditu> list  ;
	private JButton jb ;
	private int type = 0 ;
	private int inset = 8;
	/** type1 副本 type2 剧情 */
	public FubenPanel(final int type) {
		this.type = type ;
		this.setBounds(139, 76, 612, 370);
		setLayout(null);
		setOpaque(false);
		
		/** 显示副本信息 */
		add(selectInfo);
		selectInfo.setLayout(null);
		selectInfo.setBounds(-2, 160, 296, 210);
		selectInfo.setOpaque(false);
		int textType = 1 ;
		TextField temp = null;
		for (int i = 0; i < dituInfo.length; i++) {
			temp = new TextField(dituInfo[i], textType);
			temp.setBounds(inset, inset+i*(fontSize+inset),
					dituInfo[i].length()*fontSize+fontSize/2,fontSize+inset);
			info[i] = new TextField("",textType);
			info[i].setForeground(new Color(195,39,43));
			info[i].setBounds(inset+temp.getWidth(),inset+i*(fontSize+inset),fontSize*16,
						fontSize + inset);
			selectInfo.add(temp);
			selectInfo.add(info[i]);
		}
		text = new TTextArea(0);
		text.setBorder(new EmptyBorder(6, 6, 6, 6));
		text.setOpaque(true);
		text.setFont(new Font("幼圆", 0, 14));
		text.setBackground(new Color(66, 76, 80));
		text.setPreferredSize(new Dimension(276, 120));
		text.setBounds(inset, temp.getY() + temp.getHeight() + inset, 276, 120);
		selectInfo.add(text);
		/** 设置显示地图信息面板背景图 */
		JLabel label = new JLabel("");
		ImageIcon img = SUtils.loadImageIcon("/game/img/back/backB.png");
		label.setIcon(img);
		selectInfo.add(label, BorderLayout.CENTER);
		label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		
		initData();
		
		/** 确认按钮 */
		jb = new JButton("<html>确<br>认</html>");
		jb.setIcon(SUtils.loadImageIcon("/game/img/button/buD.png"));
		jb.setForeground(Color.white);
		jb.setFont(new Font("隶书", Font.PLAIN, 20));
		jb.setContentAreaFilled(false);// 设置图片填充所在区域
		jb.setMargin(new Insets(0, 0, 0, 0));
		jb.setBorderPainted(false);
		jb.setBorder(null);
		jb.setHorizontalTextPosition(SwingConstants.CENTER);
		jb.setVerticalTextPosition(SwingConstants.CENTER);
		add(jb);
		jb.setBounds(300, 160, 560, 210);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ditu==null){
					text.setText("请先选择一个要去往的地图!");
					text.setForeground(SUtils.Red);
				}else{
					System.out.println("剧情选择完毕！");
					System.out.println(ditu.getId()+":"+ditu.getDes());
					if(type==C.Fuben){
						gameControl.close(C.Fuben);
					}else if(type==C.JiangHu){
						gameControl.close(C.JiangHu);
						ditu = new XmlUtils().loadScene(ditu.getId());
					}
					gameControl.setInScene(true);
					gameControl.restore();
					gameControl.setSelect(ditu);
					gameControl.setNpcSpecInfo(ditu);
					gameControl.decorateMap();
				}
			}
		});
		
		JLabel jl = new JLabel();
		//612,370
		jl.setIcon(SUtils.loadImageIcon("/game/img/back/pingtai.png"));
		add(jl);
		jl.setBounds(0, 0, 612, 370);
		System.out.println("over.............................");
	}

	/** 重新布置 */
	public void initData() {
		if(type==C.Fuben)
			list = new XmlUtils().loadFuben();/** 加载副本列表 */
		else if(type==C.JiangHu)
			list = new XmlUtils().loadJuqing();/** 加载副本列表 */
		int length = list.size();
		System.err.println("加载得到了"+length+"剧情");
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setOpaque(false);
		SUtils.setEmptyBorder(contentPane);
		contentPane.setPreferredSize(new Dimension(length * (146 + 6), 127));
		jsc = new JScrollPane(contentPane);// 设置边框
		jsc.setOpaque(false);
		SUtils.setEmptyBorder(jsc);
		jsc.getViewport().setOpaque(false);
		jsc.setBounds(0, 0, 612, 160);
		//SUtils.setScrollUI(jsc);
		add(jsc);
		
		buList = new ArrayList<>();
		/** 显示副本 */
		for (int i = 0; i < length; i++) {
			dituBu = new TButton(list.get(i).getName(),29);
			dituBu.setSize();
			dituBu.setLocation(6+i*dituBu.getWidth(), 6);
			dituBu.addMouseListener(dituBu);
			dituBu.addActionListener(click);
			dituBu.setActionCommand(i+"");
			contentPane.add(dituBu);
			buList.add(dituBu);
		}
	 
		info[0].setText("");
		info[1].setText("");
		info[2].setText("");
		text.setText("");
		text.setText("丹崖怪石，削壁奇峰。丹崖上，彩凤双鸣；削壁前，麒麟独卧。峰头时听锦鸡鸣，石窟每观龙出入。林中有寿鹿仙狐，树上有灵禽玄鹤。瑶草奇花不谢，青松翠柏长春。仙桃常结果，修竹每留云。一条涧壑藤萝密，四面原堤草色新。");

	}
	
	ActionListener click = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < buList.size(); i++) {
				buList.get(i).exit();
			}
			((TButton)e.getSource()).click();
			int index = SUtils.conStrtoInt(e.getActionCommand());
			ditu = list.get(index);
			/** 更改显示信息 */
			info[0].setText(ditu.getName());
			info[1].setText(ditu.getRankL() + "-" + ditu.getRankR());
			text.setForeground(Color.white);
			text.setText(ditu.getDes());
		}
	};
	
	/*class Listenr extends MouseAdapter {
		private Ditu fuben = null;
		public JButton jb = null;
		public boolean flag = false;
		ImageIcon image1 = null ;
		ImageIcon image2 = null ;
		ImageIcon image3 = null ;
		
		public Listenr(JButton jb, Ditu fuben) {
			this.jb = jb;
			this.fuben = fuben;
			if(type==1){
				image1 = SUtils.loadImageIcon("/game/img/button/Fuben1.png"); 
				image2 = SUtils.loadImageIcon("/game/img/button/Fuben2.png");
				image3 = SUtils.loadImageIcon("/game/img/button/Fuben3.png");
			}else if(type==2){
				image1 = SUtils.loadImageIcon("/game/img/button/Juqing1.png"); 
				image2 = SUtils.loadImageIcon("/game/img/button/Juqing2.png");
				image3 = SUtils.loadImageIcon("/game/img/button/Juqing3.png");
			}
			
			jb.setIcon(image1);
			// 设置字体颜色
			jb.setForeground(Color.white);
			// 设置字体大小 和 格式
			jb.setFont(new Font("隶书", Font.PLAIN, 18));
			// 设置透明
			//jb.setOpaque(false);// 是否透明 false透明
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
			for (int i = 0; i < buList.size(); i++) {
				buList.get(i).setIcon(image1);
			}
			ditu = fuben ;
			flag = true;
			jb.setIcon(image2);
			*//** 更改显示信息 *//*
			info[0].setText(fuben.getName());
			info[1].setText(fuben.getRankL() + "-" + fuben.getRankR());
			text.setForeground(Color.white);
			text.setText(fuben.getDes());
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
	}*/
}
