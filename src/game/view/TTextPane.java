package game.view;

import game.utils.Constant;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicTextPaneUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TTextPane extends JTextPane{
	private static final long serialVersionUID = 1L;
	/** 蓝，红，白，黑 */
	private Color[] cAry = { new Color(75,92,196),Color.red,Color.white,Color.black} ;
	private Color[] outputAry = {
			/** 白色，为MISS，红色，为暴击，深蓝色，为普攻，蓝色为描述字体颜色，我方，绿色，敌方，粉色 */
			Color.white,new Color(255,36,0),new Color(77, 77, 255),
			new Color(61,175,247),new Color(0,229,0),new Color(255,36,0)
	} ;
	private SimpleAttributeSet attrset ;
	private StyledDocument doc = null;
	private static JScrollPane scrollPane = null;
	private String newLine = "\n" ;
	
	/** 35 */
	public TTextPane(int width,int height) {
		setOpaque(false);
		setEditable(false);
		setFont(new Font("华文中宋", Font.PLAIN, 14));
		setSize(width,height);
		doc = getStyledDocument() ;
		scrollPane = new JScrollPane(this);
		scrollPane.setPreferredSize(new Dimension(512, 600));
		scrollPane.setSize(width,height);
	}
	
	public TTextPane(String str,int width,int height) {
		if(str.equals("我了个去")){
			setUI(new BasicTextPaneUI());
			setOpaque(false);
			setEditable(false);
			setFont(new Font("华文中宋", Font.PLAIN, 14));
			setSize(width,height);
			doc = getStyledDocument() ;
			scrollPane = new JScrollPane(this);
			scrollPane.setPreferredSize(new Dimension(512, 600));
			scrollPane.setSize(width,height);
			scrollPane.setAutoscrolls(true);
		}
	}
	
	public JScrollPane getInstance(){
		return scrollPane;
	}
	
	/**
	 * 在面板上显示副本的信息
	 * @param str 
	 * @param type 要显示副本的什么信息
	 * 0所在地名
	 */
	public void apendFubenInfo(String str,int type){
		if(type==0){
			append("你到达了"+str+"!\n", 0);
		}else{
			
		}
	}
	
	/** 
	 * 蓝红白黑
	 * 0-9  0:黑色[微软雅黑,14];1:蓝色[微软雅黑,14];2:红色;3:绿色;5:黑色[隶书,14]
	 * 10-19  装备颜色 10,白色 ;11,蓝色; 12,紫色 ; 13,橙色 ; 14,红色 ;15,黑色(在背景颜色为白色的情况下) 
	 * 20-29  战斗颜色  20,白 21:红;22:深蓝;23:浅蓝;24:绿色;25:粉色 ;
	 * 30-39
	 * @param str 要输出的字符串
	 * @param type 输出字符的格式与颜色
	 */
	public void append(String str,int type){
		Color color = null ;
		attrset = new SimpleAttributeSet();
		String fontStr = "幼圆" ;
		int fontSize = 14 ;
		switch (type) {
		case 0:
			fontSize = 14 ;
			fontStr = "微软雅黑";
			color = Color.black ;
			break;
		case 1:
			fontSize = 14 ;
			fontStr = "微软雅黑";
			color = Color.blue ;
			break;
		case 2:
			fontSize = 14 ;
			fontStr = "微软雅黑";
			color = Color.red ;
			break;
		case 3:
			fontSize = 14 ;
			fontStr = "微软雅黑";
			color = Color.green ;
			break;
		case 5:
			fontSize = 16 ;
			color = Color.BLUE ;
			fontStr = "隶书" ;
			StyleConstants.setBold(attrset, true);
			break ;
		case 6:
			fontSize = 16 ;
			color = Color.red ;
			fontStr = "隶书" ;
			StyleConstants.setBold(attrset, true);
			break ;
		case 7:
			fontSize = 16 ;
			color = cAry[type-1] ;
			fontStr = "隶书" ;
			StyleConstants.setBold(attrset, true);
			break ;
		/** 装备颜色:10,白色  11,蓝色  12,紫色  13,橙色  14,红色 15,黑色(在背景颜色为白色的情况下)  */	
		case 10: 
		case 11:
		case 12:
		case 13:
		case 14:
			color = Constant.equipColor[type-10] ;
			fontSize = 14 ;
			fontStr = "微软雅黑";
			break;
		case 15:/** 11-16 是输出信息的字体颜色 */
			color = Color.black ;
			fontSize = 14 ;
			fontStr = "微软雅黑";
			break;
		case 20://白色
		case 21://红色
		case 22://深蓝
		case 23://浅蓝/
		case 24://绿色
		case 25://红色
			color = outputAry[type-20] ;
			fontSize = 16 ;
			fontStr = "楷体" ;
			StyleConstants.setBold(attrset, true);//加粗
			break;
		case 26:
			color = Color.black ;
			fontSize = 16 ;
			fontStr = "楷体" ;
			StyleConstants.setBold(attrset, true);//加粗
			break;
		default:
			break;
		}
		StyleConstants.setForeground(attrset, color);
		StyleConstants.setFontSize(attrset, fontSize);
		StyleConstants.setFontFamily(attrset, fontStr);
		insert(str, attrset);
		/** 设置JTextPanel自动滚动 */
		setCaretPosition(getStyledDocument().getLength());  
	}
	
	public void insert(String str, AttributeSet attrset) {
		// 利用getDocument()方法取得JTextPane的Document instance.0
		Document docs = getDocument();
		try {
			docs.insertString(docs.getLength(), str, attrset);
		} catch (BadLocationException ble) {
			System.out.println("BadLocationException:" + ble);
		}
	}
	
}

