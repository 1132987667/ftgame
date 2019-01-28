package game.view.panel;

import game.control.GameControl;
import game.control.UICtrl;
import game.entity.Player;
import game.entity.Scene;
import game.utils.ArchiveUtils;
import game.utils.C;
import game.utils.SUtils;
import game.utils.XmlUtils;
import game.view.button.MButton;
import game.view.ui.TTextArea;
import game.view.ui.Field;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BigMapP extends JPanel{
		
	private static final long serialVersionUID = 1L;
	private final int INSET = 8 ;
	
	/** 组件 */
	public JScrollPane jsc ;
	private JPanel mapP ;
	private TTextArea txt = new TTextArea(1);
	private Field title = new Field("大地图", 1);
	
	/** 大小 */
	private static int w = 800 ;
	private static int h = 500 ;
	
	/** 横竖连接线 */
	Point p = new Point(6, 4) ;
	private Field heng = new Field(4, p.x, p.y);
	private Field shu = new Field(4, p.y, p.x);
	
	/** 当前位置 */
	private Point curPoint ;
	
	public BigMapP() {
		/** 初始化组件 */
		setLayout(null);
		setSize(w, h);
		setBackground(new Color(243, 249, 241));
		mapP = new JPanel() ;
		mapP.setLayout(null);
		mapP.setBackground(new Color(255, 251, 240));//new Color(255, 251, 240)
		txt.setSize(w, 94);
		txt.setMargin(new Insets(0, 0, 0, 0));
		txt.setOpaque(true);
		txt.setLocation(0, 30);
		txt.setText("");
		txt.setBackground(SUtils.White);
		txt.setForeground(SUtils.Black);
		txt.setFont(C.Kai_M);
		title.setSize(300,20);
		title.setLocation(10, 2);
		title.setFont(C.Kai_XL);
		title.setForeground(SUtils.Blue);
		jsc = new JScrollPane(mapP);
		jsc.setLocation(0, 100);
		jsc.setSize(w, h-txt.getHeight()-title.getHeight());
		/** 添加组件 */
		add(title);
		add(jsc);
		add(txt);
		initMap(null, "");
	}
	
	public void initMap(Point curPoint ,String dituId) {
		if(mapP!=null) {
			mapP.removeAll();
			mapP.validate();
			mapP.repaint();
		}
		/** txt不需要动 */
		
		List<Scene> list = null ;
		if(dituId.length()<1){
			list = new XmlUtils().loadScene("4").getScene(); 	
		}else {
			list = new XmlUtils().loadScene(dituId).getScene(); 	
		}
		int[] x = new int[list.size()];
		int[] y = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Scene scene = list.get(i) ;
			x[i] = scene.x ;
			y[i] = scene.y ;
		}
		/** 地图中场景x,y坐标排序 */
		Arrays.sort(x);
		Arrays.sort(y);
		int minX = x[0] ;
		int minY = y[0] ;
		
		jsc.setDoubleBuffered(true);
		int yPos = (jsc.getHeight()-2*INSET-y[y.length-1]*(45+INSET))/2 ;
		
		for (int i = 0; i < list.size(); i++) {
			MButton tmpBu ;
			Scene scene = list.get(i) ;
			System.out.println(scene.getName()+":"+scene.x+","+scene.y);
			tmpBu = new MButton(scene.getName(), 15);
			tmpBu.setScene(scene);
			tmpBu.addActionListener(ac);
			if(curPoint!=null&&scene.x==curPoint.x&&scene.y==curPoint.y) {
				System.err.println("当前所在位置点击:"+scene.x+","+scene.y);
				tmpBu.setCurBu();
			}else {
				tmpBu.setFlag(MButton.Base);
			}
			mapP.add(tmpBu);
			tmpBu.setBounds((scene.x-minX)*(80+INSET)+INSET, (scene.y)*(45+INSET)+INSET+yPos, 80, 45);
		}
		
		
		
		Field cable = null ;
		boolean[] flags = new boolean[2] ;
		/** 连接线的设置 */
		for (Scene scene: list) {
			flags = SUtils.CanConnects(scene, list);
			for (int i = 0; i < flags.length; i++) {
				if(flags[i]) {
					if(i==0) {
						cable = (Field)ArchiveUtils.depthClone(shu);
						cable.setLocation((scene.x-minX)*(80+INSET)+INSET+(80-p.x)/2, scene.y*(45+INSET)+INSET-(p.y+2)+yPos);
					}else {
						cable = (Field)ArchiveUtils.depthClone(heng);
						cable.setLocation((scene.x-minX)*(80+INSET)+INSET+80+1, (scene.y)*(45+INSET)+INSET+(45-p.y)/2+yPos);
					}
					cable.setOpaque(true);
					mapP.add(cable);
				}
			}
		}
		
		for (int i = 0; i < x.length; i++) {
		}
		int w = (x[x.length-1]+1-minX)*(80+INSET)+INSET ;
		int h = y[y.length-1]*(45+INSET)+INSET ;
		System.out.println( "面板大小:"+w+","+h );
		mapP.setSize(w, h);
		mapP.setPreferredSize(new Dimension(w, h));
		jsc.setPreferredSize(new Dimension(w, h));
	}
	
	private ActionListener ac = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			MButton bu = (MButton) e.getSource();
			Scene sc = bu.getScene();
			txt.setText(sc.getDes());
			UICtrl.setBorder(txt, sc.getName()+"("+sc.getX()+","+sc.getY()+")", Color.BLACK, C.Kai_M);
		}
	};
	
	
	public static void main(String[] args) {
		JFrame j = new JFrame();
		BigMapP bigMap = new BigMapP();
		j.setContentPane(bigMap);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setBounds(100, 100, w, h+20);
		j.setResizable(false);
		j.setUndecorated(true);
		//j.setBackground(new Color(0, 0, 0, 0));
		j.setVisible(true);
	}

	public Point getCurPoint() {
		return curPoint;
	}

	public void setCurPoint(Point curPoint) {
		this.curPoint = curPoint;
	}
	
	public void setTitle(String txt) {
		title.setText(txt);
	}
	
	
}
