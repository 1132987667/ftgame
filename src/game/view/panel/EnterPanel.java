package game.view.panel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.utils.Constant;


public class EnterPanel extends JPanel{
	
	public EnterPanel(final JPanel mainPanel) {
		setLayout(null);
		setBackground(new Color(204,204,255));
		String name = Constant.getUserInfo().get("name").toString();
		if(name==null){
			
		}else{
			JLabel nameLabel = new JLabel("欢迎你:"+name);
			add(nameLabel);
			nameLabel.setBounds(100, 100, 200, 40);
		}
		
	}
	
}
