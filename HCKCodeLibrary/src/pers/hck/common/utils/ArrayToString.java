package pers.hck.common.utils;

import java.util.ArrayList;

public class ArrayToString {
	public final static int INDEX_FORMAT_NORMAL = 0;
	public final static int INDEX_FORMAT_STRING = 1;
	public final static int INDEX_FORMAT_INTGER = 2;
	
	public static String toString(Integer[] array){
		return toString(array, false);
	}
	
	public static String toString(String[] array){
		return toString(array, false);
	}
	
	public static String toString(ArrayList<Object> array){
		return toString(array, INDEX_FORMAT_NORMAL);
	}
	
	public static String toJSArray(Integer[] array){
		return toString(array,"[]",true);
	}
	
	public static String toJSArray(String[] array){
		return toString(array,"[]",false);
	}
	
	public static String toJSArray(ArrayList<Object> array){
		return toString(array,"[]",INDEX_FORMAT_STRING);
	}
	
	public static String toString(Integer[] array, String brackets, boolean stringFormat){
		if (brackets == null){
			return toString(array, stringFormat);
		}
		if (brackets.length() !=2){
			System.out.println("Brackets format wrong!");
			return null;
		}
		return brackets.charAt(0)+toString(array, stringFormat)+brackets.charAt(1);
	}
	
	public static String toString(String[] array, String brackets, boolean integerFormat){
		if (brackets == null){
			return toString(array, integerFormat);
		}
		if (brackets == null || brackets.length() !=2){
			System.out.println("Brackets format wrong!");
			return null;
		}
		return brackets.charAt(0)+toString(array, integerFormat)+brackets.charAt(1);
	}
	
	public static String toString(ArrayList<Object> array, String brackets, int INDEX_FORMAT){
		if (brackets == null){
			return toString(array, INDEX_FORMAT);
		}
		if (brackets.length() !=2){
			System.out.println("Brackets format wrong!");
			return null;
		}
		return brackets.charAt(0)+toString(array,INDEX_FORMAT)+brackets.charAt(1);
	}
	
	public static String toString(Integer[] array, boolean stringFormat){
		String s = "";
		if (!stringFormat){
			for (int i=0;i<array.length;i++){
				s += (i+1 < array.length)? array[i]+",":array[i];
			}
		}else{
			for (int i=0;i<array.length;i++){
				s += (i+1 < array.length)? "\""+array[i]+"\",":"\""+array[i]+"\"";
			}
		}
		return s;
	}
	
	public static String toString(String[] array, boolean integerFormat){
		String s = "";
		if (!integerFormat){
			for (int i=0;i<array.length;i++){
				s += (i+1 < array.length)? "\""+array[i]+"\",":"\""+array[i]+"\"";
			}
		}else{
			for (int i=0;i<array.length;i++){
				s += (i+1 < array.length)? array[i]+",":array[i];
			}
		}
		return s;
	}
	
	public static String toString(ArrayList<Object> array, int INDEX_FORMAT){
		String s = "";
		for (int i=0;i<array.size();i++){
			Object object = array.get(i);
			switch (INDEX_FORMAT){
			case INDEX_FORMAT_NORMAL:
				if (object instanceof String){
					s += (i+1 < array.size())? "\""+object+"\",":"\""+object+"\"";
				}else if (object instanceof Integer){
					s += (i+1 < array.size())? object+",":object;
				}else {
					return null;
				}
				break;
			case INDEX_FORMAT_STRING:
				s += (i+1 < array.size())? "\""+object+"\",":"\""+object+"\"";
				break;
			case INDEX_FORMAT_INTGER:
				s += (i+1 < array.size())? object+",":object;
				break;
			}
			
		}
		return s;
	}
}
