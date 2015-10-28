package pers.hck.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;

public class ClassHandler {
	public final static int TYPE_ALL = 0;
	public final static int TYPE_BASE = 1;
	public final static int TYPE_INSIDE = 2;

	public static LinkedList<String> getBeanGetMethodNames(Class bean, int TYPE, String beanType) {
		LinkedList<String> methodNames = new LinkedList<String>();
		try {
			Method[] methods = bean.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.substring(0, 3).equalsIgnoreCase("get")
						&& !methodName.equalsIgnoreCase("getClass")) {
					LinkedList<String> beanValueNames = null;
					switch (TYPE){
					case TYPE_ALL:
						methodNames.add(methodName);
						break;
					case TYPE_BASE:
						beanValueNames = getBaseBeanValueNames(bean);
						for (String beanValueName:beanValueNames){
							if (methodName.toLowerCase().contains(beanValueName.toLowerCase())){
								methodNames.add(methodName);
								break;
							}
						}
						break;
					case TYPE_INSIDE:
						beanValueNames = getInsideBeanNames(bean, beanType);
						for (String beanValueName:beanValueNames){
							if (methodName.toLowerCase().contains(beanValueName.toLowerCase())){
								methodNames.add(methodName);
								break;
							}
						}
						break;
					}
				}
			}
			return methodNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LinkedList<String> getBeanSetMethodNames(Class bean, int TYPE, String beanType) {
		LinkedList<String> methodNames = new LinkedList<String>();
		try {
			Method[] methods = bean.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.substring(0, 3).equalsIgnoreCase("set")){
					LinkedList<String> beanValueNames = null;
					switch (TYPE){
					case TYPE_ALL:
						methodNames.add(methodName);
						break;
					case TYPE_BASE:
						beanValueNames = getBaseBeanValueNames(bean);
						for (String beanValueName:beanValueNames){
							if (methodName.toLowerCase().contains(beanValueName.toLowerCase())){
								methodNames.add(methodName);
								break;
							}
						}
						break;
					case TYPE_INSIDE:
						beanValueNames = getInsideBeanNames(bean, beanType);
						for (String beanValueName:beanValueNames){
							if (methodName.toLowerCase().contains(beanValueName.toLowerCase())){
								methodNames.add(methodName);
								break;
							}
						}
						break;
					}
				}
			}
			return methodNames;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getBeanGetMethodNames(Class bean) {
		return getBeanGetMethodNames(bean, TYPE_ALL, null);
	}
	
	public static LinkedList<String> getBeanGetMethodNames(String beanPath) {
		try {
			return getBeanGetMethodNames(Class.forName(beanPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getBeanSetMethodNames(Class bean) {
		return getBeanSetMethodNames(bean, TYPE_ALL, null);
	}
	
	public static LinkedList<String> getBeanSetMethodNames(String beanPath) {
		try {
			return getBeanSetMethodNames(Class.forName(beanPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getBaseBeanGetMethodNames(Class bean) {
		return getBeanGetMethodNames(bean, TYPE_BASE, null);
	}
	
	public static LinkedList<String> getBaseBeanGetMethodNames(String beanPath) {
		try {
			return getBaseBeanGetMethodNames(Class.forName(beanPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getBaseBeanSetMethodNames(Class bean) {
		return getBeanSetMethodNames(bean, TYPE_BASE, null);
	}
	
	public static LinkedList<String> getBaseBeanSetMethodNames(String beanPath) {
		try {
			return getBaseBeanSetMethodNames(Class.forName(beanPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getInsideBeanGetMethodNames(Class bean, String beanType) {
		return getBeanGetMethodNames(bean, TYPE_INSIDE, beanType);
	}
	
	public static LinkedList<String> getInsideBeanGetMethodNames(String beanPath, String beanType) {
		try {
			return getInsideBeanGetMethodNames(Class.forName(beanPath), beanType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getInsideBeanSetMethodNames(Class bean, String beanType) {
		return getBeanSetMethodNames(bean, TYPE_INSIDE, beanType);
	}
	
	public static LinkedList<String> getInsideBeanSetMethodNames(String beanPath, String beanType) {
		try {
			return getInsideBeanSetMethodNames(Class.forName(beanPath), beanType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LinkedList<String> getBeanValueNames(String beanPath) {
		try {
			return getBeanValueNames(Class.forName(beanPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LinkedList<String> getBeanValueNames(Class bean) {
		LinkedList<String> valueNames = new LinkedList<String>();
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

	public static LinkedList<String> getBaseBeanValueNames(Class bean) {
		LinkedList<String> valueNames = new LinkedList<String>();
		LinkedList<String> baseType = new LinkedList<String>();
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
	
	public static LinkedList<String> getBaseBeanValueNames(String beanPath) {
		try {
			return getBaseBeanValueNames(Class.forName(beanPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getInsideBeanNames(String beanPath, String beanType) {
		try {
			return getInsideBeanNames(Class.forName(beanPath), beanType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<String> getInsideBeanNames(Class bean, String beanType) {
		return getInsideBeanNames(bean, new String[]{beanType});
	}
	
	public static LinkedList<String> getInsideBeanNames(Class bean, String[] beanTypes) {
		LinkedList<String> valueNames = new LinkedList<String>();
		
		try {
			Field[] fields = bean.getDeclaredFields();
			for (Field field : fields) {
				String value = field.getName();
				String valueType = field.getGenericType().toString();
				for (String beanType : beanTypes){
					if (valueType.contains("class "+beanType)){
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
	
	public static String getBeanValueTypeByValueName(Class bean, String beanValueName, boolean longType){
		try {
			Field[] fields = bean.getDeclaredFields();
			for (Field field : fields) {
				String value = field.getName();
				String valueType = field.getGenericType().toString();
				if (beanValueName.equals(value)){
					if (!longType){
						valueType = valueType.substring(valueType.lastIndexOf('.')+1);
					}else{
						valueType = valueType.substring(valueType.lastIndexOf(' ')+1);
					}
					return valueType;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getBeanValueTypeByValueName(String beanPath, String beanValueName){
		try {
			return getBeanValueTypeByValueName(Class.forName(beanPath), beanValueName, false);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getBeanValueLongTypeByValueName(String beanPath, String beanValueName){
		try {
			return getBeanValueTypeByValueName(Class.forName(beanPath), beanValueName, true);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
