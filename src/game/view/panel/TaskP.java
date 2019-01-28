package game.view.panel;


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollPaneUI;

import game.control.TaskCtrl;
import game.entity.NPC;
import game.entity.Player;
import game.entity.Tasks;
import game.utils.C;
import game.utils.SUtils;
import game.view.button.PicBu;
import game.view.ui.DemoScrollBarUI;
import game.view.ui.Field;

public class TaskP extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JPanel jscPanel = new JPanel() ;
	JScrollPane jsc = new JScrollPane(jscPanel);
	private JPanel superPanel;
	private Player player ;
	private final int List = 1 ;//r任务列表
	private final int Course = 2 ;//任务过程
	private final int flag = 1 ;
	
	private final int msgWidth = 270 ;
	
	private PicBu nextBu = new PicBu("", 27);
	
	private Field title = new Field("什么情况", 1, Color.blue) ;
	
	private JPanel listP = new JPanel() ;
	private JPanel courseP = new JPanel() ;
	
	private int height = 0 ;
	private final int INSET = 4 ;
	
	private JButton accept, refused ;
	
	/*** 0-hide未出现 1-已出现,未选择 2-已点击就接受 3-一已点击拒绝  */
	private int BuResult = 0 ;
	
	private Tasks task ;
	private NPC npc ;
	
	private TaskCtrl taskCtrl ;
	
	public TaskP(JPanel superPanel,final Player player) {
		this.superPanel = superPanel;
		this.player = player ;
		
		setOpaque(false);
		setLayout(null);

		jsc.setBounds(0, 86, 294, 240);
		//jsc.setPreferredSize(new Dimension(294, 250));
		jsc.setAutoscrolls(false);
		
		jscPanel.setPreferredSize(new Dimension(274, 500));
		jscPanel.setLayout(null);
		jscPanel.setOpaque(false);
		
		jsc.setOpaque(false);
		jsc.getViewport().setOpaque(false);
		jsc.setUI(new BasicScrollPaneUI());
		jsc.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		SUtils.setEmptyBorder(jscPanel);
		jsc.setVerticalScrollBarPolicy(                
	                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		
		nextBu.setBounds(230, jsc.getHeight()+jsc.getY(), 46, 46);
		courseP.add(nextBu);
		
		title.setBounds(40, 66, 200, 20);
		title.setVerticalAlignment(SwingConstants.CENTER);
		
		courseP.add(title);
		courseP.add(jsc);
		
		listP.setOpaque(false);
		courseP.setOpaque(false);
		listP.setLayout(null);
		courseP.setLayout(null);
		listP.setBounds(0, 0, 294, 440);
		courseP.setBounds(0, 0, 294, 440);
		listP.setVisible(false);
		
		add(listP);
		add(courseP);
	
		//接受拒绝按钮
		accept = new JButton("接受");
		refused = new JButton("拒绝");
		accept.setBackground(SUtils.LightBlue);
		refused.setBackground(SUtils.LightBlue);
		accept.setFont(C.LiShu);
		refused.setFont(C.LiShu);
		accept.setForeground(Color.white);
		refused.setForeground(Color.white);
		accept.setBounds(INSET, 30, 80, 22);
		refused.setBounds(90+INSET, 30, 80, 22);
		//taskCtrl = new TaskCtrl(listP, courseP, title, jscPanel, accept, refused);
		//refused.addActionListener(taskCtrl.select);
		//accept.addActionListener(taskCtrl.select);
		//nextBu.addActionListener(taskCtrl.next);
		
		jscPanel.add(accept);
		jscPanel.add(refused);
	
	}
	
	public void taskComplete(Tasks task, NPC npc) {
		taskCtrl.taskComplete(task, npc, msgWidth);
	}
	
	/**
	 * 任务开始流程
	 * @param task
	 * @param npc
	 */
	public void taskStart(Tasks task, NPC npc) {
		taskCtrl.taskStart(task, npc, msgWidth);
	}
	
	public void acceptTask() {
		
	}
}
