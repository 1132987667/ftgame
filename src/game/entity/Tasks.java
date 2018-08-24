package game.entity;

import java.io.Serializable;

public class Tasks implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id = "" ;
	private String taskName = "" ;
	private int curState = 0 ;
	private String npcId = "" ;
	private String taskDes = "" ;
	private String task1 = "" ;
	private String task2 = "" ;
	private String task3 = "" ;
	private String startCond = "" ;
	private String acceptCond = "" ;
	private String endCond = "" ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getCurState() {
		return curState;
	}
	public void setCurState(int curState) {
		this.curState = curState;
	}
	public String getNpcId() {
		return npcId;
	}
	public void setNpcId(String npcId) {
		this.npcId = npcId;
	}
	public String getTaskDes() {
		return taskDes;
	}
	public void setTaskDes(String taskDes) {
		this.taskDes = taskDes;
	}
	public String getTask1() {
		return task1;
	}
	public void setTask1(String task1) {
		this.task1 = task1;
	}
	public String getTask2() {
		return task2;
	}
	public void setTask2(String task2) {
		this.task2 = task2;
	}
	public String getTask3() {
		return task3;
	}
	public void setTask3(String task3) {
		this.task3 = task3;
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
	
	
	
}
