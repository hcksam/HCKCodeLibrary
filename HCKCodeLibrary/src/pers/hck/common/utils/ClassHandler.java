package pers.hck.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassHandler {
	public static ArrayList<String> getBeanGetMethodNames(String beanPath) {
		try {
			return getBeanGetMethodNames(Class.forName(beanPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> getBeanGetMethodNames(Class bean) {
		ArrayList<String> methodNames = new ArrayList<String>();
		try {
			Method[] methods = bean.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.substring(0, 3).equalsIgnoreCase("get")
						&& !methodName.equalsIgnoreCase("getClass")) {
					methodNames.add(methodName);
				}
			}
			return methodNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> getBeanValueNames(String beanPath) {
		try {
			return getBeanValueNames(Class.forName(beanPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> getBeanValueNames2(Class bean) {
		ArrayList<String> methodNames = getBeanGetMethodNames(bean);
		ArrayList<String> valueNames = new ArrayList<String>();
		try {
			for (int i = 0; i < methodNames.size(); i++) {
				valueNames.add(methodNames.get(i).substring(3));
			}
			return valueNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> getBeanValueNames(Class bean) {
		ArrayList<String> valueNames = new ArrayList<String>();
		try {
			Field[] fields = bean.getDeclaredFields();
			for (Field field : fields) {
				String value = field.getName();
				valueNames.add(value);
			}
			return valueNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> getBaseBeanValueNames(Class bean) {
		ArrayList<String> valueNames = new ArrayList<String>();
		ArrayList<String> baseType = new ArrayList<String>();
		baseType.add("class java.lang.Integer");
		baseType.add("class java.lang.String");
		baseType.add("class java.util.Date");
		
		try {
			Field[] fields = bean.getDeclaredFields();
			for (Field field : fields) {
				String value = field.getName();
				String valueType = field.getGenericType().toString();
				if (baseType.contains(valueType)){
					valueNames.add(value);
				}
			}
			return valueNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<String> getInsideBeanNames(Class bean, String beanPath) {
		return getInsideBeanNames(bean, new String[]{beanPath});
	}
	
	public static ArrayList<String> getInsideBeanNames(Class bean, String[] beanPaths) {
		ArrayList<String> valueNames = new ArrayList<String>();
		
		try {
			Field[] fields = bean.getDeclaredFields();
			for (Field field : fields) {
				String value = field.getName();
				String valueType = field.getGenericType().toString();
				for (String beanPath : beanPaths){
					if (valueType.contains("class "+beanPath)){
						valueNames.add(value);
						break;
					}
				}
			}
			return valueNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
