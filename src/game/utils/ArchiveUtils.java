package game.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import game.entity.Archive;


public class ArchiveUtils {
	/***
	 * 序列化
	 * 
	 * @param Archive
	 * @throws FileNotFoundException
	 */
	private static void serializable(Archive archive, String filePath) {
		/** 当前目录项目目录下 a.txt D:\java\workspace\w2\javaBase\a.txt */
		ObjectOutputStream outputStream = null;
		if ("".equals(filePath)) {
			filePath = "a.player";
		}
		filePath+=".player";
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
			outputStream.writeObject(archive);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 反序列化 */
	private static Object deSerializable(String fileName) {
		Archive archive = null;
		ObjectInputStream ois = null;
		if ("".equals(fileName)) {
			fileName = "autoArchive.player";
		}else{
			fileName = "D:/softworkspace/w1/yi/"+fileName+".player" ;
		}
		File file = new File(fileName);
		if(!file.exists()){
			System.out.println("存档不存在");
			return null ;
		}
		System.out.println("正在读取存档文件:"+fileName+","+file.exists());
		try {
			ois = new ObjectInputStream(new FileInputStream(fileName));
			archive = (Archive) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return archive;
	}

	/** 深克隆 */
	private static Object deepClone(Object obj) {
		// 将对象写入流中
		ByteArrayOutputStream bao = null ;
		ObjectOutputStream oos = null ;
		ByteArrayInputStream bis  = null ;
		ObjectInputStream ois = null ;
		Object objClone = null ;
		try {
			bao = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bao);
			oos.writeObject(obj);

			// 将对象从流中取出
			bis = new ByteArrayInputStream(bao.toByteArray());
			ois = new ObjectInputStream(bis);
			objClone = ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				ois.close();
				bis.close();
				oos.close();
				bao.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return objClone ;
	}
	
	/**
	 * 对象深度克隆
	 * @param srcObj
	 * @return
	 */
	public static Object depthClone(Object srcObj){
	    Object cloneObj = null;
	    try {
	    	/**内存字节访问流，不需要关闭*/
	    	ByteArrayOutputStream out = new ByteArrayOutputStream();	
		    ObjectOutputStream oo = new ObjectOutputStream(out);	
		    oo.writeObject(srcObj);
		    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());	
		    ObjectInputStream oi = new ObjectInputStream(in);	
		    cloneObj = oi.readObject();	
		}catch (Exception e) {	
			e.printStackTrace();
		} 
		return cloneObj;
	}
	
	/** 存档 */
	public static void saveArchiving(Archive archive, String filePath) {
		serializable(archive, filePath);
	}

	/** 读档 */
	public static Archive loadArchive( String filePath) {
		Archive theArchive = (Archive) deSerializable(filePath);
		return archiveClone(theArchive) ;
	}
	
	/** 档克隆 */
	public static Archive archiveClone(Archive archive) {
		Archive theArchive = (Archive) deepClone(archive);
		return theArchive ;
	}

}
