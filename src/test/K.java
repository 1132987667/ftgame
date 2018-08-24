package test;

import java.net.URL;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class K {
	private static K k = new K() ;
	static{
	    JFXPanel fxPanel = new JFXPanel();
	}	
	
	public static void main(String[] args) {
		K.playSound("");
	}
	
	private static void playSound(String sound){
	    // cl is the ClassLoader for the current class, ie. CurrentClass.class.getClassLoader();
	    //URL file = cl.getResource(sound);
		URL url = k.getClass().getResource("<span style ='color:＃000000;'>/</ span>senlin.wav");  
		System.out.println(url);
		final Media media = new Media(url.getPath());
	    final MediaPlayer mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.play();
	}
}
