package game.control;

import game.utils.SUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundControl {
	private static SoundControl soundControl = new SoundControl();

	private SoundControl() {
	}

	public static SoundControl getInstance() {
		return soundControl;
	}

	private Clip getFtback(int type) {
		Clip clip = null;
		String str = null;
		switch (type) {
		case 0:
			str = "game/sounds/taskFail.wav";
			break;

		default:
			break;
		}
		try {
			URL url = this.getClass().getClassLoader().getResource(str);
			System.out.println(url);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			return clip;
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return clip;
	}

	/**
	 * 界面上的音效
	 * 
	 * @param str
	 * @return
	 */
	public static Clip jiemianMuc(String str) {
		String path = null;
		switch (str) {
		case "bu":
			path = "bu";// y
			break;
		case "openBag":
			path = "openBag";// y
			break;
		case "closeBag":
			path = "closeBag";
			break;
		case "openMap":
			path = "openMap";// y
			break;
		case "closeMap":
			path = "closeMap";// y
			break;
		case "lvUp":// 升级特效
			path = "lvUp";// y
			break;
		case "keyDown":
			path = "keyDown";// y
			break;
		case "bacA":
			path = "bacMucA";// 副本背景音乐 y
			break;
		case "bacB":
			path = "bacMucA";// 副本背景音乐 y
			break;
		default:
			break;
		}
		return loadSounds(path);
	}

	/**
	 * 战斗的音效
	 * 
	 * @param type
	 * @return
	 */
	public static Clip ftMuc(int type) {
		String path = "";
		switch (type) {
		case 0:
			path = "badao";// y
			break;
		case 1:
			path = "daoY";// y
			break;
		case 2:
			path = "jianY";// y
			break;
		case 3:
			path = "gunY";// y
			break;
		case 4:
			path = "quanY";// y
			break;
		case 5:
			path = "quanN";// y
			break;
		case 11:
			path = "gain";// 获得物品 y
			break;
		case 12:
			path = "victory";// 胜利 y
			break;
		case 13:
			path = "renDead";// 人物死亡 y
			break;
		case 14:
			path = "guaiDead";// 怪死亡 y
			break;
		case 21:
			path = "bacMucA";// 副本背景音乐 y
			break;
		case 22:
			path = "bacMucA";// 副本背景音乐 y
			break;
		case 25:
			path = "caocong";// 草丛
			break;
		default:
			break;
		}
		return loadSounds(path);
	}

	public static Clip loadSounds(String str) {
		File file;
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;
		Clip clip = null;
		try {
			file = new File("src/game/sounds/" + str + ".wav");
			stream = AudioSystem.getAudioInputStream(file);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clip;
	}

	public static void main(String[] args) {
		SoundControl s = new SoundControl();
	}
}
