package game.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private String imageName = null;
	private ImageIcon image1 = null;
	public int type;

	public TLabel(String str, int type) {
		super(str);
		this.type = type;
		// 设置字体颜色
		setForeground(Color.white);
		// 设置字体大小 和 格式
		setFont(new Font("楷体", Font.BOLD, 16));
		// 设置透明
		setOpaque(false);// 是否透明 false透明
		// 设置边框
		setBorder(null);
		iconControl(type);
		this.setIcon(image1);
		int x = image1.getIconHeight();
		int y = image1.getIconWidth();
		this.setSize(x, y);
		// 居中显示
		setHorizontalTextPosition(SwingConstants.CENTER);
	}

	private void iconControl(int type) {
		switch (type) {
		case 0:
			setFont(new Font("隶书", Font.PLAIN, 16));
			setForeground(Color.white);
			imageName = "" ;
			break;
		case 1:
			imageName = "精致";
			break;
		case 2:
			imageName = "LabelA" ;
			break ;
		case 3:
			imageName = "LabelB" ;
			setFont(new Font("隶书", Font.PLAIN, 18));
			setForeground(Color.white);
			break ;
		case 4:
			imageName = "血条" ;
			break ;
		case 5:
			imageName = "血条框" ;
			break ;
		case 6:
			imageName = "血条反" ;
			break ;
		case 7:
			imageName = "血条框反" ;
			break ;
		case 8:
			imageName = "法力" ;
			break ;
		case 9:
			imageName = "法力反" ;
			break ;
		case 10:
			imageName = "LabelC" ;
			break ;
		case 11:
			imageName = "minSwordL" ;
			break ;
		case 12:
			imageName = "minSwordR" ;
			break ;
		case 13:
			imageName = "showA" ;
			break ;
		case 14:
			imageName = "能量" ;
			break ;
		case 15:
			imageName = "能量反" ;
			break ;
		default:
			break;
		}
		image1 = new ImageIcon("src/game/img/one/" + imageName+ ".png");
	}
}
