package game.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectUtils {
	
	
	 public Object getField(String classPath,String fieldName){
		 	Object o = null ;
			try {
				Class<?> theClass = Class.forName(classPath);
				/** 得到类的无参数构造 */
				Constructor<?> con = theClass.getConstructor();
				Object obj = con.newInstance();
				/** 得到字段 */
				Field field = theClass.getField(fieldName);
				/** 判断是否可见 */
				if(!field.isAccessible()){
					field.setAccessible(true);
				}
				o = field.get(obj);
			} catch (ClassNotFoundException e) {
				System.out.println(classPath+"没有找到该类!");
			} catch (NoSuchFieldException e) {
				System.out.println(fieldName+"没有找到该字段!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		 	return o ;
		 }
	 
	 public static Object[] getArray(Class theClass,String fieldName){
		 	Object[] o = null ;
			try {
				/** 得到类的无参数构造 */
				Constructor<?> con = theClass.getConstructor();
				Object obj = con.newInstance();
				/** 得到字段 */
				Field field = theClass.getField(fieldName);
				/** 判断是否可见 */
				if(!field.isAccessible()){
					field.setAccessible(true);
				}
				Object temp = field.get(obj);
				if(temp.getClass().isArray()){
					o = (Object[]) temp ;
				}
			} catch (NoSuchFieldException e) {
				System.out.println(fieldName+"没有找到该字段!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		 	return o ;
		 }
	 
	 public static Object getInstance(String npcName){
		 Object obj = null ;
		 try {
			 String pageckName = "game.entity.npc." ;
			 String className = pageckName+npcName;
			 Class c = Class.forName(className);
			 Constructor<?> con = c.getConstructor();
			 obj = con.newInstance();
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
		 
	 }
	 
}
