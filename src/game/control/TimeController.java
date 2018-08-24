package game.control;

public class TimeController {
	
	public static TimeController timeController = null ;
	
	private TimeController() {}

	public static TimeController getInstance(){
		if(timeController==null){
			timeController = new TimeController();
		}
		return timeController;
	}
	
	public int cur = 0 ;
	
	public static final int start = 0 ;
	
	
}
