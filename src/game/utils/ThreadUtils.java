package game.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
	
	private static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();  
	
	/***
	 * 把线程放入单线程池顺序执行
	 * @param runnable
	 */
	public static void execute(Runnable runnable){
		singleExecutor.execute(runnable);
		//int threadCount = ((ThreadPoolExecutor)singleExecutor).getActiveCount();
		//System.out.println(threadCount);
	}
	
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
