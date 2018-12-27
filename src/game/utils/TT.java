package game.utils;

import java.util.List;

public class TT {
	public static void main(String[] args) {
		TT t = new TT() ;
		t.testLoadEquipSet();
	}
	
	public void testLoadEquipSet() {
		List list = new SUtils().loadEquipSetting(0);
		for (Object object : list) {
			System.out.println(object);
		}
	}
	
}
