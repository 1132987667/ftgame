package game.utils;

import game.control.GameControl;
import game.entity.Archive;
import game.entity.Gong;
import game.entity.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
/**
 * 金手指
 * @author yilong22315
 *
 */
public class Cmf extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private String[] cases = {"装备","功法","材料",""};
	private final int INSET = 6 ;
	private final Font font1 = new Font("幼圆", Font.PLAIN, 14) ;
	private int goodType = -1 ;
	
	public JComboBox<String> selectCase ; 
	public FieldLabel temp, id, name, des ;
	private JTextField idInput, numInput;
	private JButton qry, add, save ;
	private GameControl gameControl = GameControl.getInstance();
	private Player player = gameControl.getPlayer();
	private FieldLabel tipMsg ; 
	
	/** 查询结果 */
	private Gong gong ;
	private Map<String, Gong> gongMap;
	
	public Cmf() {
		uiInit();
	}
	
	public void uiInit(){
		setLayout(null);
		setBackground(Color.white);
		Archive archive = ArchiveUtils.loadArchive("archive1");
		gameControl.setArchive(archive);
		gameControl.setArchiveName("archive1");
		Player player = SUtils.conArcToPlayer(archive);
		this.player = player ;
		
		selectCase = new JComboBox<>(cases);
		add(selectCase);
		selectCase.setBounds(10, 10, 80, 24);
		
		temp = new FieldLabel("id");
		idInput = new JTextField();
		temp.setBounds(selectCase.getWidth()+selectCase.getX()+INSET, selectCase.getY(), 20, 24);
		idInput.setBounds(temp.getWidth()+temp.getX(), temp.getY(), 80, 24);
		add(temp);
		add(idInput);
		
		qry = new JButton("查询");
		qry.addActionListener(ac);
		qry.setFont(font1);
		add(qry);
		qry.setBounds(idInput.getX()+idInput.getWidth()+INSET, idInput.getY(), 80, 24);
		
		temp = new FieldLabel("数量");
		numInput = new JTextField();
		temp.setBounds(qry.getWidth()+qry.getX()+INSET, qry.getY(), 40, 24);
		numInput.setBounds(temp.getWidth()+temp.getX(), temp.getY(), 80, 24);
		add(temp);
		add(numInput);

		add = new JButton("增加");
		add.addActionListener(ac);
		add.setFont(font1);
		add(add);
		add.setBounds(numInput.getX()+numInput.getWidth()+INSET, numInput.getY(), 80, 24);

		save = new JButton("保存");
		save.addActionListener(ac);
		save.setFont(font1);
		add(save);
		save.setBounds(add.getX()+add.getWidth()+INSET, add.getY(), 80, 24);
		
		temp = new FieldLabel("查询结果");
		add(temp);
		temp.setBounds(selectCase.getX(), selectCase.getY()+selectCase.getHeight()+INSET, 80, 20);
		tipMsg = new FieldLabel("");
		tipMsg.setForeground(Color.red);
		add(tipMsg);
		tipMsg.setBounds(temp.getX()+temp.getWidth(), temp.getY(), 200, 20);
	
		
		id = new FieldLabel("结果");
		add(id);
		id.setBounds(temp.getX()+36, temp.getY()+temp.getHeight(), 200, 20);
		temp = new FieldLabel("ID:");
		add(temp);
		temp.setBounds(id.getX()-36, id.getY(), 36, 20);
		
		name = new FieldLabel("结果");
		add(name);
		name.setBounds(id.getX(), id.getY()+id.getHeight(), 200, 20);
		temp = new FieldLabel("名字:");
		add(temp);
		temp.setBounds(name.getX()-36, name.getY(), 36, 20);

		des = new FieldLabel("结果");
		add(des);
		des.setBounds(name.getX(), name.getY()+name.getHeight(), 200, 20);
		temp = new FieldLabel("名字:");
		add(temp);
		temp.setBounds(des.getX()-36, des.getY(), 36, 20);
		
	}
	
	
	public static void main(String[] args) {
		if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
			final String platform = UIManager.getSystemLookAndFeelClassName();
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
							
				}
			}
		}
		
		JFrame frame = new JFrame();
		frame.setContentPane(new Cmf());
		frame.setBounds(20, 20, 600, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class FieldLabel extends JLabel{
		private static final long serialVersionUID = 1L;
		public FieldLabel(String text) {
			super(text);
			setFont(font1);
		}
	}
	
	ActionListener ac = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==add) {
				if(numInput.getText().trim().length()<=0||idInput.getText().trim().length()<=0){
					tipMsg.setText("请输入物品ID和数量！");
				}else{
					if(goodType==1){//功法
						player.getGongBag().add(gong);
						System.out.println("功法书数量:"+player.getGongBag().size());
					}
				}
				
			}else if(e.getSource()==qry){
				if(idInput.getText().trim().length()<=0){
					tipMsg.setText("请输入要查询物品的ID!");
				}
				goodType = selectCase.getSelectedIndex();
				System.out.println(player.getName());
				//System.out.println(player.getEquipBag().get(0));
				if(goodType==0){
					
				}else if(goodType==1){//功法
					gongMap = new SUtils().loadGong() ;
					System.out.println("背包内物品数量:"+player.getGongBag().size());
					gong = gongMap.get(idInput.getText());
					if(gong==null){
						tipMsg.setText("该ID不存在!");
					}else{
						id.setText(gong.getId());
						name.setText(gong.getName());
						des.setText(gong.getDes());
					}
				}
			}else if(e.getSource()==save){
				System.out.println("功法书数量:"+player.getGongBag().size()+","+gameControl.getArchiveName());
				Archive archive = SUtils.conPlayerToArc(player);
				boolean flag = ArchiveUtils.saveArchiving(archive, "archive1");
				System.out.println("保存存档："+flag);
			}
		}
	};
	
}
