package game.test.typeT;

import java.util.ArrayList;
import java.util.List;

public class B extends A{
	private String name = "B" ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static void main(String[] args) {
		List<A> a = new ArrayList<>() ;
		A a1 = new B() ;
		System.out.println( a1 instanceof B );
	}
	
}
