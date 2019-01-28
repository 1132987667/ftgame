package game.entity;

import java.io.Serializable;
import java.util.Date;

public class ArchiveInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id = 0 ;
	private String name = null ;
	private Date exitDate = null ;
	
	public ArchiveInfo(int id, String name, Date exitDate) {
		this.id = id ;
		this.name = name ;
		this.exitDate = exitDate ;
	}
	
}
