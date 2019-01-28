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
import game.control.IoCtrl;
import game.control.UICtrl;
import game.entity.Ditu;
import game.utils.C;
import game.utils.SUtils;
import game.utils.XmlUtils;
import game.view.button.PicBu;
import game.view.ui.TTextArea;
import game.view.ui.Field;
/**
 * 进行副本和章节选择的面板
 * @author yilong22315
 */
public class FubenPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	/** 组件 */
	private JScrollPane jsc = null;
	private JPanel contentPane = null;
	/** 显示当前点击地图信息 */
	private JPanel selectInfo = new JPanel();
	private PicBu dituBu = null;
	private List<PicBu> buList = null;
	private GameControl gameControl = GameControl.getInstance() ;
	public  Field name, rank, msg;
	private Field[] info = { name, rank, msg };
	private String[] dituInfo = { "副本名:", "适合等级:", "简介:" };
	private TTextArea text = null;
	private int fontSize = 14;
	private Ditu ditu = null ;
	private List<Ditu> list  ;
	private JButton jb ;
	private int type = 0 ;
	/** type1 副本 type2 剧情 */
	public FubenPanel(final int type) {
		this.type = type ;
		this.setBounds(139, 76, 612, 370);
		initSet();
		
		/** 显示副本信息 */
		add(selectInfo);
		UICtrl.panelBaseSet(selectInfo);
		selectInfo.setBounds(-2, 160, 296, 210);
		selectInfo.setOpaque(true);
		Field temp = null;
		for (int i = 0; i < dituInfo.length; i++) {
			temp = customField(dituInfo[i]);
			temp.setBounds(C.INSET, C.INSET+i*(fontSize+C.INSET),
					dituInfo[i].length()*fontSize+fontSize/2,fontSize+C.INSET);
			info[i] = customField("");
			info[i].setForeground(new Color(195,39,43));
			info[i].setBounds(C.INSET+temp.getWidth(),C.INSET+i*(fontSize+C.INSET),fontSize*16,
						fontSize + C.INSET);
			selectInfo.add(temp);
			selectInfo.add(info[i]);
		}
		
		
		text = new TTextArea(0);
		text.setBorder(new EmptyBorder(6, 6, 6, 6));
		text.setFont(C.Y_M);
		text.setBackground(new Color(66, 76, 80));
		text.setPreferredSize(new Dimension(276, 120));
		text.setBounds(C.INSET, temp.getY() + temp.getHeight() + C.INSET, 276, 120);
		selectInfo.add(text);
		JLabel label = new JLabel("");
		ImageIcon img = IoCtrl.loadImageIcon("/game/img/back/backB.png");
		label.setIcon(img);
		selectInfo.add(label, BorderLayout.CENTER);
		label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		/** 确认按钮 */
		PicBu jb = new PicBu("确认", 10);
		jb.setForeground(Color.white);
		jb.setFont(C.LS_XL);
		add(jb);
		jb.setLocation(400, 260);
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
			dituBu = new PicBu(list.get(i).getName(),29);
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
	
	public Field customField(String text) {
		Field field = new Field(text,1);
		return field ;
	}
	
	ActionListener click = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < buList.size(); i++) {
				buList.get(i).exit();
			}
			((PicBu)e.getSource()).click();
			int index = SUtils.conStrtoInt(e.getActionCommand());
			ditu = list.get(index);
			/** 更改显示信息 */
			info[0].setText(ditu.getName());
			info[1].setText(ditu.getRankL() + "-" + ditu.getRankR());
			text.setForeground(Color.white);
			text.setText(ditu.getDes());
		}
	};
	@Override
	public void reloadUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadData() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setBacImg() {
		JLabel jl = new JLabel();
		//612,370
		jl.setIcon(IoCtrl.loadImageIcon("/game/img/back/pingtai.png"));
		add(jl);
		jl.setBounds(0, 0, 612, 370);
	}
	
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
			jb.setMargin(new C.INSETs(0, 0, 0, 0));
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
