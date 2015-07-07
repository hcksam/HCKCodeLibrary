package pers.hck.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ClassHandler {
	public static ArrayList<String> getBeanGetMethodNames(String beanName){
		try{
			return getBeanGetMethodNames(Class.forName(beanName));
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<String> getBeanGetMethodNames(Class bean){
		ArrayList<String> methodNames = new ArrayList<String>();
		try{
			Method[] methods = bean.getMethods();
			for (int i=0;i<methods.length;i++){
				String method = methods[i].getName();
				if (method.substring(0,3).equalsIgnoreCase("get") && !method.equalsIgnoreCase("getClass")){
					methodNames.add(method);
				}
			}
			return methodNames;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<String> getBeanValueNames(String beanName){
		try{
			return getBeanValueNames(Class.forName(beanName));
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<String> getBeanValueNames(Class bean){
		ArrayList<String> methodNames = getBeanGetMethodNames(bean);
		ArrayList<String> valueNames = new ArrayList<String>();
		try{
			for (int i=0;i<methodNames.size();i++){
				valueNames.add(methodNames.get(i).substring(3));
			}
			return valueNames;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
