package game.entity;

import java.io.Serializable;

/**
 * 任务实体类
 * @author yilong22315
 *
 */
public class Tasks implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 基本信息 */
	private String taskName = "" ;
	private String id = "" ;
	private String npcId = "" ;
	private String npcName = "" ;
	private String taskDes = "" ;
	private String type = "" ;
	
	/** 任务状态 */
	private int curState = 0 ;
	
	/** 谈话内容 */
	private String startMsg = "" ;
	private String undoMsg = "" ;
	private String acceptMsg = "" ;
	private String endMsg = "" ;
	
	/** 条件 */
	private String startCond = "" ;
	private String acceptCond = "" ;
	private String endCond = "" ;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNpcId() {
		return npcId;
	}
	public void setNpcId(String npcId) {
		this.npcId = npcId;
	}
	public String getNpcName() {
		return npcName;
	}
	public void setNpcName(String npcName) {
		this.npcName = npcName;
	}
	public int getCurState() {
		return curState;
	}
	public void setCurState(int curState) {
		this.curState = curState;
	}
	public String getStartMsg() {
		return startMsg;
	}
	public void setStartMsg(String startMsg) {
		this.startMsg = startMsg;
	}
	public String getUndoMsg() {
		return undoMsg;
	}
	public void setUndoMsg(String undoMsg) {
		this.undoMsg = undoMsg;
	}
	public String getAcceptMsg() {
		return acceptMsg;
	}
	public void setAcceptMsg(String acceptMsg) {
		this.acceptMsg = acceptMsg;
	}
	public String getEndMsg() {
		return endMsg;
	}
	public void setEndMsg(String endMsg) {
		this.endMsg = endMsg;
	}
	public String getStartCond() {
		return startCond;
	}
	public void setStartCond(String startCond) {
		this.startCond = startCond;
	}
	public String getAcceptCond() {
		return acceptCond;
	}
	public void setAcceptCond(String acceptCond) {
		this.acceptCond = acceptCond;
	}
	public String getEndCond() {
		return endCond;
	}
	public void setEndCond(String endCond) {
		this.endCond = endCond;
	}
	public String getTaskDes() {
		return taskDes;
	}
	public void setTaskDes(String taskDes) {
		this.taskDes = taskDes;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
