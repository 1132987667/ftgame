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
	public static void main(String[] args) {
		String root = ArchiveUtils.class.getResource("/game/archive1.player").getPath() ;
		System.out.println(root);
		File file = new File(root);
		System.out.println(file.exists());
	}
	
	private static String load(String archiveName) {
		try {
			System.out.println("存档地址1："+ArchiveUtils.class.getResource("/"+archiveName+".player").getPath());
			String path = ArchiveUtils.class.getResource("/").getPath()+archiveName+".player" ;
			 String classLoader_str = ArchiveUtils.class.getClassLoader().getResource(archiveName+".player").getPath();
			System.out.println("存档地址2："+path);
			System.out.println("存档地址3："+classLoader_str);
			return path ;
		} catch (NullPointerException e) {
			System.out.println("加载存档路径错误！(ArchiveUtils.load)");
			return "" ;
		}
	}
	
	/**
	 *  * 序列化
	 * @param archive
	 * @param filePath
	 */
	private static boolean serializable(Archive archive, String filePath) {
		/** 当前目录项目目录下 a.txt D:\java\workspace\w2\javaBase\a.txt */
		ObjectOutputStream outputStream = null;
		if ("".equals(filePath)) {
			filePath = "a.player";
		}
		/*filePath=System.getProperty("user.dir")+"\\"+filePath+".player";*/
		File file = new File(load(filePath));
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false ; 
		}
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
		return true ;
	}
	
	/**
	 * 反序列化
	 * @param fileName
	 * @return
	 */
	private static Object deSerializable(String fileName) {
		Archive archive = null;
		ObjectInputStream ois = null;
		if ("".equals(fileName)) {
			fileName = "autoArchive";
		}
		File file = new File(load(fileName));
		if(!file.exists()){
			System.out.println("存档不存在");
			return null ;
		}
		System.out.println("正在读取存档文件:"+fileName+","+file.exists());
		try {
			ois = new ObjectInputStream(new FileInputStream(load(fileName)));
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
	public static boolean saveArchiving(Archive archive, String filePath) {
		return serializable(archive, filePath);
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
