package game.view.panel;

import game.control.GameControl;
import game.entity.Scene;
import game.view.button.MButton;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BigMapP extends JPanel{
		
	private static final long serialVersionUID = 1L;

	//15
	private MButton tmpBu ;
	private final int INSET = 4 ;
	public JScrollPane jsc ;
	
	public BigMapP(List<Scene> list) {
		setLayout(null);
		jsc = new JScrollPane(this);
		setBackground(Color.white);
		if(list==null){
			list = GameControl.getInstance().loadJuqing("0").getScene(); 
		}
		System.out.println(list.size());
		int[] x = new int[list.size()];
		int[] y = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Scene scene = list.get(i) ;
			x[i] = scene.x ;
			y[i] = scene.y ;
			tmpBu = new MButton(scene.getName(), 15);
			tmpBu.setFlag(MButton.Base);
			add(tmpBu);
			tmpBu.setBounds(scene.x*80+INSET, scene.y*45+INSET, 80, 45);
		}
		
		Arrays.sort(x);
		Arrays.sort(y);
		int w = x[x.length-1]*(80+INSET)+INSET ;
		int h = y[y.length-1]*(45+INSET)+INSET ;
		
		w = w>600?w:600 ;
		h = h>300?h:300 ;
		jsc.setPreferredSize(new Dimension(w, h));
		jsc.setSize(600, 300);
		setSize(w, h);
		
	}
	
	public JScrollPane getScrollPanel(){
		return jsc ;
	}
	
	
	public static void main(String[] args) {
		JFrame j = new JFrame();
		BigMapP bigMap = new BigMapP(null);
		j.setContentPane(bigMap.getScrollPanel());
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setBounds(100, 100, 600, 360);
		j.setResizable(false);
		j.setUndecorated(true);
		j.setBackground(new Color(0, 0, 0, 0));
		j.setVisible(true);
	}
	
}
