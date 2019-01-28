package game.control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.entity.NPC;
import game.entity.Player;
import game.entity.Tasks;
import game.utils.SUtils;
import game.view.button.PicBu;
import game.view.frame.SpFrame;
import game.view.ui.TTextArea;
import game.view.ui.Field;

public class TaskCtrl {
	/** 任务还未开启 */
	public static final int NoStart = 0 ;
	/** 满足任务条件 */
	public static final int FullCase = 1 ;
	/** 任务开始前对话 */
	public static final int StartTalk_Sta = 2 ;
	/** 任务开始前对话结束 */
	public static final int StartTalk_End = 3 ;
	/** 还未选择 */
	public static final int UnSelect = 4 ;
	/** 选择接受任务 */
	public static final int Accept_Task = 5 ;
	/** 选择拒绝任务 */
	public static final int Refused_Task = 6 ;
	/** 接受任务后对话开始 */
	public static final int AccpetTaskTalk_Sta = 7 ;
	/** 接受任务后对话结束 */		
	public static final int AcceptTaskTalk_End = 8	;	
	/** 任务完成 */
	public static final int Task_Complete = 9 ;
	/** 任务完成后对话开始 */
	public static final int TaskEndTalk_Sta = 10	;	
	/** 任务完成后对话结束 */
	public static final int TaskEndTalk_End = 11	;	
	/** 任务结束 */
	public static final int Task_Over = 11	;	
	
	/** 需要选择按钮出现 */
	public final int Task_NeedBu = 100 ;
	/** 出现了选择按钮 */
	public final int Task_ShowBu = 101 ;

	/** 任务类型 */
	/** 一次性任务 */
	public static final String once_task = "一次型" ;
	/** 可重置任务 */
	public static final String reset_task = "可重复型" ;
	
	/** 信息实体类 */
	Tasks task ;
	NPC npc ;
	GameControl gameControl = GameControl.getInstance();
	Player player = gameControl.getPlayer();
	
	/** 设置组件位置参数 */
	int INSET = 4 ;
	int height = 0 ;
	
	/** UI组件 */
	JPanel listP ; 
	JPanel courseP ;
	Field title ;
	JPanel jscPanel ;
	
	private PicBu accept = new PicBu("接受", 4) ;
	private PicBu refused = new PicBu("拒绝", 4) ;
	
	/** 设置对话内容 */
	int curMsgNum = 0 ;
	int msgWidth = 0 ;
	private String[] startMsgs ;
	private String[] acceptMsgs ;
	private int taskStatus = 0 ;
	String myName ;
	String npcName ;
	
	private TaskListener taskListener ;
	
	public TaskCtrl() {
		refused.addActionListener(select);
		accept.addActionListener(select);
	}
	
	public void setTaskInfo(NPC npc) {
		this.npc = npc ;
	}
	
	/**
	 * 任务开始流程
	 * @param task
	 * @param npc
	 */
	public void taskStart(Tasks task, NPC npc, int msgWidth) {
		this.task = task ;
		this.npc = npc ;
		this.msgWidth = msgWidth ;
		jscPanel.removeAll();
		jscPanel.updateUI();
		courseP.setVisible(true);
		listP.setVisible(false);
		height = 0 ;
		
		title.setText(task.getTaskName());
		myName = player.getName() ;
		npcName = npc.getName() ;
		
		String msg = task.getStartMsg() ;
		startMsgs = msg.split(";");
		if(startMsgs.length==1) {
			addMsg(task.getStartMsg());
			/** 任务开始前对话结束,下一步显示选择按钮 */
			taskStatus = Task_NeedBu ;
		}else {
			/** 任务开始前对话还未结束 */
			taskStatus = FullCase ;
			addMsg(startMsgs[0]);
			curMsgNum++;
		}
	}
	
	/**
	 * 开始任务开始前的对话
	 */
	public void taskStartTalk() {
		if(startMsgs==null) {
			String msg = task.getStartMsg() ;
			startMsgs = msg.split(";");
		}
		System.out.println("当前第"+curMsgNum+"句台词，一共"+startMsgs.length+"句台词！");
		if(curMsgNum<startMsgs.length) {/**任务开始前对话还未结束，继续对话*/
			appendMsg(startMsgs[curMsgNum]);
			curMsgNum++;
			if(curMsgNum==startMsgs.length) {
				task.setCurState(StartTalk_End);
			}
		}else {
			throw new IllegalArgumentException("显示任务对话出现问题！ TaskCtl-taskStartTalk");
		}
	}
	
	/**
	 * 接受任务后对话
	 * 
	 */
	public void acceptTaskTalk() {
		if(acceptMsgs==null) {
			String msg = task.getAcceptMsg() ;
			acceptMsgs = msg.split(";");
		}
		if(curMsgNum==acceptMsgs.length) {
			taskStatus = AcceptTaskTalk_End ;
		}else if(curMsgNum<acceptMsgs.length) {/**任务开始前对话还未结束，继续对话*/
			appendMsg(acceptMsgs[curMsgNum]);
			curMsgNum++;
		}else {
			throw new IllegalArgumentException("显示任务对话出现问题！ TaskCtl-taskStartTalk");
		}
	}
	
	/**
	 * 完成任务流程
	 * @param task
	 * @param npc
	 */
	public void taskComplete(Tasks task, NPC npc, int msgWidth) {
		this.task = task ;
		this.npc = npc ;
		this.msgWidth = msgWidth ;
	}
	
	public void appendMsg(String msg) {
		msg = msg.trim();
		if(msg.startsWith("aside:")) {
			msg = msg.substring(6);
			gameControl.append(msg+"\n", 1);
			return;
		}
		if(msg.startsWith("me:")) 
			msg = myName+msg.substring(2);
		else if(msg.startsWith("npc:"))
			msg = npc.getName()+msg.substring(3);
		gameControl.append(msg+"\n", 0);
		
		
	}
	
	public void addMsg(String msg) {
		msg = msg.trim();
		if(msg.startsWith("me:")) 
			msg = myName+msg.substring(2);
		else if(msg.startsWith("npc:"))
			msg = npc.getName()+msg.substring(3);

		
		int lineNum = msg.length()%19>0?msg.length()/19+1:msg.length()/19 ;
		TTextArea tarea = new TTextArea(1);
		tarea.setText(msg);
		tarea.setSize(msgWidth, 20*lineNum+4);
		tarea.setPreferredSize(new Dimension(msgWidth, 20*lineNum+4));
		tarea.setLocation(INSET, height);
		jscPanel.add(tarea);
		jscPanel.updateUI();
		height+=tarea.getHeight();
	}
	
	public ActionListener next = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(taskStatus==Task_NeedBu) {
				showSelect();
			}else {
				//还处于任务开始前对话
				if(taskStatus==FullCase) {
					if(curMsgNum==startMsgs.length) {/**任务对话结束，显示选择按钮,标志改为已显示按钮*/
						showSelect();
						taskStatus = Task_ShowBu ;
					}else if(curMsgNum<startMsgs.length) {/**任务开始前对话还未结束，继续对话*/
						addMsg(startMsgs[curMsgNum]);
						curMsgNum++;
					}else {
						throw new IllegalArgumentException("显示任务对话出现问题！ TaskCtl-next");
					}
				}else if(taskStatus==Task_Complete) {
					if(curMsgNum==acceptMsgs.length) {
						/** 任务结束,关闭窗口 */
					}else if(curMsgNum<acceptMsgs.length) {
						addMsg(startMsgs[curMsgNum]);
						curMsgNum++;
					}
				}
			}
		}
	};
	
	public ActionListener select = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			accept.setVisible(false);
			refused.setVisible(false);
			if (e.getSource().equals(accept)) {
				taskStatus = Accept_Task ;
				String msg = task.getAcceptMsg() ;
				acceptMsgs = msg.split(";");
				appendMsg(acceptMsgs[0]);
				curMsgNum = 1 ;
			}else
				taskStatus = Refused_Task ;
		}
	};
	
	/** 显示选择按钮 */
	public void showSelect() {
		taskStatus = Task_ShowBu ;
		
		accept.setVisible(true);
		refused.setVisible(true);
		
		refused.setLocation(INSET, height);
		accept.setLocation(90,height);
		
		height += refused.getHeight();
		jscPanel.updateUI();
	}
	
	/** 交谈按钮的监听 */
	class TaskListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean inEnterTask = true ;
			List<Tasks> tasks = npc.getTasks(); 
			for (int i = 0; i < tasks.size(); i++) {
				task = tasks.get(i) ;
				boolean taskCase = gameControl.isFullCase(task.getStartCond());
				if(taskCase&&task.getCurState()!=Task_Over) {
					inEnterTask = false ;
				}
				int state = task.getCurState();
				System.out.println("第"+i+"个任务状态"+tasks.get(i).getCurState());
				/**
				 * 如果为未开启，那么判断是否满足条件，满足则flag=false，任务状态设置为满足状态，进入任务流程
				 * 如果未满足条件，那么意味着在进入任务系统后未选择就退出了|是否不允许
				 * 如果为拒绝状态，那么再次判断是否满足条件，满足则进行下一步
				 * 如果为已接受状态，也就是未完成状态，那么就应该输出未完成对话 undo
				 */
				switch (state) {
				/** 任务还未开始/拒绝了任务/未进行选择 需要重新开始任务流程 */
				case NoStart:
				case Refused_Task:
				case UnSelect:
					/** 正在任务前谈话 */
					task.setCurState(StartTalk_Sta);
					curMsgNum = 0 ;
					taskStartTalk();
					break;
				/** 接受了任务，要进行之后的谈话 */
				case Accept_Task:
					task.setCurState(AccpetTaskTalk_Sta);
					acceptTaskTalk();
					break;
				/** 任务开始之后交谈 */
				case StartTalk_Sta:
					taskStartTalk();
					break;
				case StartTalk_End:
					System.out.println("任务开始前对话结束");
					addSelectBu();
					task.setCurState(UnSelect);
					curMsgNum = 0 ;
					break;
				case AccpetTaskTalk_Sta:
				
				case AcceptTaskTalk_End:
					curMsgNum = 0;
					task.setCurState(Accept_Task);
					break;
				default:
					break;
				}
				/*
				if(task.getCurState()==TaskCtrl.NoStart||task.getCurState()==TaskCtrl.Task_Refused) {
					boolean startFlag = gameControl.isFullCase(task.getStartCond());
					System.out.println("判断是否满足条件？");
					if(startFlag) {//满足开启条件
						System.out.println("任务条件满足，开启任务！");
						inEnterTask = false ;
						task.setCurState(TaskCtrl.FullCase);
						new TaskCtrl().taskStart(task, npc);
						SpFrame taskFrame = gameControl.getTaskFrame();
						taskFrame.setVisible(true);
						taskFrame.taskStart(task, npc);
					}
				}else if(task.getCurState()==TaskCtrl.Accept_Task) {
					inEnterTask = false ;
					boolean completeFlag = gameControl.isFullCase(task.getEndCond());
					if(completeFlag) {
						SpFrame taskFrame = gameControl.getTaskFrame();
						taskFrame.taskComplete(task, npc);
					}else {
						gameControl.append(task.getUndoMsg(), 1);
					}
				}*/
			}
			
			if(inEnterTask) {
				String[] msgs = npc.getMsgs() ;
				int num =SUtils.rd(msgs.length);		
				gameControl.append(npc.getName()+msgs[num].trim()+"\n", 1);
			}
		}
	};
	
	private void addSelectBu() {
		accept.setVisible(true);
		refused.setVisible(true);
		gameControl.addSelectBu(refused);
		gameControl.addSelectBu(accept);
		gameControl.append("\n", 0);
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}
	
	
	public TaskListener newTaskListener() {
		taskListener = new TaskListener();
		return taskListener ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
