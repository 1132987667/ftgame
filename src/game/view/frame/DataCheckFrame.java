package game.view.frame;

import java.awt.Font;

import game.control.GameControl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/***
 * 创建用来检测人物数据的面板
 * @author yilong22315
 *
 */
public class DataCheckFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel rootPanel = new JPanel() ;
	private GameControl gameControl = GameControl.getInstance();
	JLabel hp , mp , atk , def ;
	JLabel[] ary = {hp , mp , atk , def} ;
	private Font font = new Font("幼圆",Font.PLAIN,14);
	JButton reload = new JButton("重置") ;
	
	
	public DataCheckFrame() {
		setContentPane(rootPanel);
		rootPanel.setLayout(null);
		rootPanel.add(reload);
		reload.setBounds(10, 10, 80, 24 );
		for (int i = 0; i < ary.length; i++) {
			ary[i] = new JLabel() ;
			ary[i].setBounds(10, 40+i*24, 300, 24);
			ary[i].setFont(font);
			rootPanel.add(ary[i]);
		}
		setBounds(200, 200, 500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	public void setLabelText(String str,int i){
		ary[i].setText(str);
	}
	
}
