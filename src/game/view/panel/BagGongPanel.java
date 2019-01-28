package game.view.panel;

import game.control.GameControl;
import game.entity.Gong;
import game.utils.C;
import game.view.ui.TTextArea;
import game.view.ui.Field;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *	显示背包中功法和材料信息的载体面板
 *
 */
public class BagGongPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private String[] GongField = {} ;
	private String[] ClField = {} ;
	private int type ;
	private Field name, kind, tier, qua, state, learn, price, require ;
	private TTextArea tta ;
	private final int INSET = 6 ;
	private final int TxtH = 20 ;
	private JLabel back = null ; 
	
	public static final int SIMPLE_GONG = 1 ;
	public static final int DETAIL_GONG = 2 ;
	public static final int CAILIAO = 3 ;
	
	/**
	 * 
	 * @param type
	 */
	public BagGongPanel(int type) {
		setLayout(null);
		setBackground(new Color(0,0,0,0.6f));
		setSize(150, 240);
		this.type = type ; 
		if(type==CAILIAO)
			initClPanel();
		else
			initGongPanel(type);
	}
	
	/** 初始化材料面板 */
	private void initClPanel() {
		
	}

	/** 初始化功法信息面板 */
	private void initGongPanel(int type) {
		name = new Field("name", 1);
		name.setBounds(INSET, INSET, 120, TxtH);
		
		kind = new Field("kind", 0);
		kind.setBounds(name.getX(), name.getY()+TxtH, 60, TxtH);
		
		tier = new Field("tier", 0);
		tier.setBounds(kind.getX()+kind.getWidth()+INSET, kind.getY(), 60, TxtH);
		
		qua = new Field("品阶", 0);
		qua.setBounds(INSET, tier.getY()+TxtH, 60, TxtH);
		
		state = new Field("tier", 0);
		state.setBounds(qua.getX()+qua.getWidth()+INSET, qua.getY(), 60, TxtH);
		
		tta = new TTextArea(0);
		tta.setBounds(INSET, qua.getY()+qua.getHeight(), 146, 72);
		tta.setPreferredSize(new Dimension( 146, 72));
		tta.setText("七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变"
				+ "七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变七十二变");
		
		require = new Field("学习条件:", 0);
		require.setBounds(INSET, tta.getY()+tta.getHeight(), 140, TxtH);
		
		price = new Field("售价", 0);
		price.setBounds(INSET, require.getY()+TxtH, 60, TxtH);

		learn = new Field("已学习", 0);
		learn.setForeground(new Color(0x2196F3));
		learn.setBounds(INSET, price.getY()+TxtH, 60, TxtH);
		
		back = new JLabel();
		/*String imgPath = "src/game/img/back/dazuo.png";
		ImageIcon img = new ImageIcon(imgPath);
		back.setIcon(img);
		back.setBounds(0, 60, img.getIconWidth(), img.getIconHeight());*/
		
		/** 展示详细功法信息，根据修炼层数显示当前已学会技能 */
		if(type==DETAIL_GONG){
			
		}
		
		add(name);
		add(kind);
		add(tier);
		add(qua);
		add(state);
		add(tta);
		add(learn);
		add(price);
		add(require);
		add(back);
	}
	
	
	/** 初始化材料面板 */
	public void reloadClPanel() {
		
	}

	/** 初始化功法信息面板 */
	public void reloadGongPanel(Gong gong) {
		name.setText(gong.getName());
		kind.setText(gong.getType()+"");
		tier.setText("共"+gong.getMaxTier()+"重");
		qua.setText(C.gongQu[gong.getQua()]);
		state.setText(C.STATE[gong.getNeedRank()%10]);
		tta.setText(gong.getDes());
		boolean hasLearn =  GameControl.getInstance().hasLean(gong.getId());
		String learnStr = hasLearn?"已学习":"未学习";
		learn.setText("学习条件:"+learnStr);
		price.setText(gong.getPrice()+"");
		require.setText(gong.getRequire());
	}
	
	public static void main(String[] args) {
		JFrame j = new JFrame();
		BagGongPanel bg = new BagGongPanel(1);
		j.add(bg);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setBounds(100, 100, 160, 240);
		j.setResizable(false);
		j.setUndecorated(true);
		j.setBackground(new Color(0, 0, 0, 0));
		j.setVisible(true);
	}
	
}
