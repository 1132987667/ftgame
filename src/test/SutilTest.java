package test;

import game.utils.SUtils;

public class SutilTest {
	public static void main(String[] args) {
		SutilTest s = new SutilTest(); 
		s.testTransferLongToDate();
		s.testReDouPoint();
		
	
	}
	
	/** 测试时间转换方法 */
	public void testTransferLongToDate(){
		System.out.println("testTransferLongToDate();");
		System.out.println(SUtils.transferLongToDate(System.currentTimeMillis()));
	}
	
	public void testReDouPoint(){
		System.out.println("testReDouPoint();");
		/*System.out.println(SUtils.reDouPoint("0.162"));
		System.out.println(SUtils.reDouPoint("4.162"));
		System.out.println(SUtils.reDouPoint("5.752"));*/
	}
}	
