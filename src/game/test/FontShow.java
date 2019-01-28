package game.test;

import java.awt.GraphicsEnvironment;

public class FontShow {
	 public static void main(String[] args)
	  {
	    String fonts[] = 
	      GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

	    for ( int i = 0; i < fonts.length; i++ )
	    {
	      System.out.println(i+":"+fonts[i]);
	    }
	  }
}
