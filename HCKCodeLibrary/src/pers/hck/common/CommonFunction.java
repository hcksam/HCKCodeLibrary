package pers.hck.common;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Date;
import java.util.List;

public class CommonFunction {
	private final static String MS = "@";
	private final static String SS = " ";
	private final static String US1 = ".";
	private final static String US2 = "/";
	private final static String US3 = "\\";
	private final static String US4 = ":";
	private final static String US5 = "http";
	private final static String Warning_DateFormat = "Date format wrong!";
	public final static char SIGN_SPACE = Character.toChars(1)[0];

	public static boolean isASCII(String inString) {
		byte bytearray []  = inString.getBytes();
	    CharsetDecoder d = Charset.forName("US-ASCII").newDecoder();
	    try {
	      CharBuffer r = d.decode(ByteBuffer.wrap(bytearray));
	      r.toString();
	    }
	    catch(CharacterCodingException e) {
	      return false;
	    }
	    return true;
	}
	
	public static boolean isNull(String inString) {
		boolean isNull = (inString == null || inString.equals("") || inString.equalsIgnoreCase("null"));
		return isNull;
	}
	
	public static boolean isDate(String inString, String dateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date date = sdf.parse(inString);
			return (date != null) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isDate(String inString) {
		return isDate(inString, CommonData.DEFAULT_DATE_FORMAT);
	}

	public static String getStringToday(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date today = new Date();
		return sdf.format(today);
	}
	
	public static String getStringToday() {
		return getStringToday(CommonData.DEFAULT_DATE_FORMAT);
	}

	public static String getStringDate(Date inDate, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(inDate);
	}

	public static Date getDate(String inDate, String dateFormat,
			boolean nullable) {
		try {
			if (inDate.equals("")) {
				inDate = null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.parse(inDate);
		} catch (NullPointerException npe) {
			if (!nullable) {
				System.out.println("InDate: " + inDate);
			}
			return null;
		} catch (Exception e) {
			System.out.println("InDate: " + inDate);
			System.out.println("DateFormat: " + dateFormat);
			System.out.println(Warning_DateFormat);
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date getDate(String inDate) {
		return getDate(inDate, CommonData.DEFAULT_DATE_FORMAT);
	}

	public static Date getDate(String inDate, String dateFormat) {
		return getDate(inDate, dateFormat, true);
	}

	public static Date getLaterDate(String inDate1, String inDate2,
			String dateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date d1 = sdf.parse(inDate1);
			Date d2 = sdf.parse(inDate2);
			return (d1.compareTo(d2) > 0) ? d1 : d2;
		} catch (Exception e) {
			System.out.println("Date1: " + inDate1);
			System.out.println("Date2: " + inDate2);
			System.out.println("DateFormat: " + dateFormat);
			System.out.println(Warning_DateFormat);
			return null;
		}
	}
	
	public static Date getLaterDate(String inDate1, String inDate2) {
		return getLaterDate(inDate1, inDate2);
	}

	// public static String getLastDateFolder(String Parent, String DateFormat)
	// {
	// try {
	// File file = new File(Parent);
	// String[] Folders = file.list();
	// if (Folders != null) {
	// SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
	// String lastDateFolder = null;
	// for (int i = 0; i < Folders.length; i++) {
	// if (!Folders[i].contains(".")
	// && Folders[i].length() >= DateFormat.length()) {
	// if (lastDateFolder != null) {
	// lastDateFolder = sdf.format(getLaterDate(
	// lastDateFolder, Folders[i], DateFormat));
	// } else {
	// lastDateFolder = Folders[i];
	// }
	// }
	// }
	// return (lastDateFolder != null) ? lastDateFolder
	// : getStringToday(DateFormat);
	// } else {
	// return getStringToday(DateFormat);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

	public static String getLastDateFile(String parent, String dateFormat,
			int dateBeginIndex, String extensionFileName) {
		try {
			File file = new File(parent);
			String[] files = file.list();
			String lastFile = null;
			if (files != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				String lastDateFile = null;
				for (int i = 0; i < files.length; i++) {
					boolean filter = (extensionFileName == null) ? true
							: (files[i].length() < extensionFileName.length()) ? false
									: isEqualExtensionFileName(files[i],
											extensionFileName);
					if (filter == true)
						lastFile = files[i];
					String fileDate = (files[i].length() >= dateFormat.length()) ? files[i]
							.substring(dateBeginIndex, dateFormat.length())
							: null;

					if (filter && isDate(fileDate, dateFormat)) {
						if (lastDateFile != null) {
							String InDate1 = lastDateFile.substring(
									dateBeginIndex, dateFormat.length());
							Date lastDate = getLaterDate(InDate1, fileDate,
									dateFormat);
							if (lastDate == null)
								return null;
							if (sdf.format(lastDate).equals(fileDate)) {
								lastDateFile = files[i];
							}
						} else {
							lastDateFile = files[i];
						}
					}
				}
				return (lastDateFile != null) ? lastDateFile
						: (lastFile != null) ? lastFile : null;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getLastDateFile(String parent,int dateBeginIndex, String extensionFileName) {
		return getLastDateFile(parent,CommonData.DEFAULT_DATE_FORMAT_SIMPLE,dateBeginIndex,extensionFileName);
	}

	public static String insertHTMLTag(String TAG, String text) {
		return TAG + CommonData.SIGN_CLOSETAG + text + getHTMLCloseTag(TAG);
	}

	public static String insertHTMLTag(String tag, String text, String attribute) {
		return tag + attribute + CommonData.SIGN_CLOSETAG + text
				+ getHTMLCloseTag(tag);
	}

	public static String getHTMLCloseTag(String TAG) {
		String tag = TAG.substring(1);
		return CommonData.TAG_CLOSE + tag + CommonData.SIGN_CLOSETAG;
	}

	public static LinkedList<Integer> getAllIndexOf(String target, String keyword) {
		LinkedList<Integer> indexs = new LinkedList<Integer>();
		int index = target.indexOf(keyword);
		String s = target;
		while (index >= 0) {
			indexs.add(index);
			String bs = s.substring(0, index);
			String es = s.substring(index + keyword.length());
			s = bs + getSpace(keyword.length()) + es;
			index = s.indexOf(keyword);
		}
		return indexs;
	}

	public static String getSpace(int length) {
		String s = "";
		for (int i = 0; i < length; i++) {
			s += SIGN_SPACE;
		}
		return s;
	}

	public static LinkedList<Integer[]> getAllIndexOfURL(String target) {
		LinkedList<Integer[]> indexs = new LinkedList<Integer[]>();
		LinkedList<Integer> wwwIndexs = getAllIndexOf(target, US1);
		int doneIndex = 0;

		for (int i = 0; i < wwwIndexs.size(); i++) {
			int wwwIndex = wwwIndexs.get(i);
			if (wwwIndex < doneIndex) {
				continue;
			}

			if (wwwIndex != 0 && wwwIndex + 2 < target.length()) {
				Integer[] index = new Integer[2];

				for (int j = wwwIndex - 1; j >= 0; j--) {
					if (!isEnglish(target.charAt(j))) {
						index[0] = j + 1;
						break;
					}
					if (!Character.isLetter(target.charAt(j))) {
						if (target.charAt(j) == SS.charAt(0)
								|| target.charAt(j) == US4.charAt(0)
								|| isSlantingSign(target.charAt(j))
								&& target.charAt(j + 1) != US1.charAt(0)) {
							if (isSlantingSign(target.charAt(j)) && j >= 6) {
								if (isSlantingSign(target.charAt(j - 1))) {
									if (target.substring(j - 6, j - 1)
											.equalsIgnoreCase(US5 + US4)) {
										index[0] = j - 6;
										break;
									}
								}
							}
						}
						index[0] = j + 1;
						break;
					}
				}

				for (int j = wwwIndex + 1; j < target.length(); j++) {
					if (!isEnglish(target.charAt(j))) {
						index[1] = j;
						break;
					}
					if (!Character.isLetter(target.charAt(j))
							&& target.charAt(j - 1) == US1.charAt(0)) {
						index[1] = j - 1;
						break;
					}
					if (target.charAt(j) == SS.charAt(0)) {
						if (Character.isLetter(target.charAt(j - 1))) {
							index[1] = j;
							break;
						} else {
							index[1] = j - 1;
							break;
						}
					}
					if (j == target.length() - 1
							&& target.charAt(j) == US1.charAt(0)) {
						index[1] = j;
						break;
					}
				}

				if (index[0] == null)
					index[0] = 0;
				if (index[1] == null)
					index[1] = target.length();

				String url = target.substring(index[0], index[1]);
				int usIndex = url.indexOf(US1);
				if (usIndex >= 1) {
					if (url.substring(usIndex).length() >= 3) {
						boolean isEmail = false;
						if (index[0] != 0) {
							isEmail = target.charAt(index[0] - 1) == MS
									.charAt(0);
						}
						if (!url.contains(MS) && !isEmail) {
							indexs.add(index);
						}
						doneIndex = index[1];
					}
				}
			}
		}
		return indexs;
	}

	public static LinkedList<Integer[]> getAllIndexOfEmail(String target) {
		LinkedList<Integer[]> indexs = new LinkedList<Integer[]>();
		LinkedList<Integer> mailIndexs = getAllIndexOf(target, MS);
		for (int i = 0; i < mailIndexs.size(); i++) {
			int mailIndex = mailIndexs.get(i);
			if (mailIndex != 0 && mailIndex + 3 < target.length()) {
				Integer[] index = new Integer[2];

				for (int j = mailIndex - 1; j >= 0; j--) {
					if (!Character.isLetter(target.charAt(j))
							&& target.charAt(j) != US1.charAt(0)) {
						index[0] = j + 1;
						break;
					}
				}

				for (int j = mailIndex + 1; j < target.length(); j++) {
					if (!Character.isLetter(target.charAt(j))
							&& target.charAt(j) != US1.charAt(0)) {
						index[1] = j;
						break;
					}
				}

				if (index[0] == null)
					index[0] = 0;
				if (index[1] == null)
					index[1] = target.length();

				indexs.add(index);
			}
		}
		return indexs;
	}

	public static LinkedList<Integer> getAllIndexOfWord(String target,
			String word) {
		LinkedList<Integer> indexs = new LinkedList<Integer>();
		LinkedList<Integer> dIndexs = getAllIndexOf(target, word);
		for (int i = 0; i < dIndexs.size(); i++) {
			boolean left = false;
			boolean right = false;
			int dIndex = dIndexs.get(i);

			if (dIndex == 0) {
				left = true;
			} else {
				if (!Character.isLetter(target.charAt(dIndex - 1))) {
					left = true;
				} else {
					if (!isEnglish(target.charAt(dIndex - 1))) {
						left = true;
					}
				}
			}

			if (dIndex + word.length() >= target.length() - 1) {
				right = true;
			} else {
				if (!Character.isLetter(target.charAt(dIndex + word.length()))) {
					right = true;
				} else {
					if (!isEnglish(target.charAt(dIndex + word.length()))) {
						right = true;
					}
				}
			}

			if (left && right) {
				boolean ignore = false;
				LinkedList<Integer[]> ignoreIndexs = getAllIndexOfURL(target);
				for (int j = 0; j < ignoreIndexs.size(); j++) {
					Integer[] ignoreIndex = ignoreIndexs.get(j);
					if (dIndex >= ignoreIndex[0] && dIndex < ignoreIndex[1]) {
						ignore = true;
						break;
					}
				}

				if (!ignore) {
					ignoreIndexs = getAllIndexOfEmail(target);
					for (int j = 0; j < ignoreIndexs.size(); j++) {
						Integer[] ignoreIndex = ignoreIndexs.get(j);
						if (dIndex >= ignoreIndex[0] && dIndex < ignoreIndex[1]) {
							ignore = true;
							break;
						}
					}
				}

				if (!ignore) {
					indexs.add(dIndex);
				}
			}

		}
		return indexs;
	}

	public static boolean isEnglish(char inChar) {
		return Character.UnicodeBlock.of(inChar).equals(
				Character.UnicodeBlock.BASIC_LATIN);
	}

	public static String replaceCharInString(String inString, int index,
			char newChar) {
		String bs = inString.substring(0, index);
		String es = inString.substring(index + 1, inString.length());
		return bs + newChar + es;
	}

	public static String getReplaceAll(String inString, String targetString,
			String newString) {
		LinkedList<Integer> indexs = getAllIndexOf(inString, targetString);
		String outString = "";
		String es = inString;
		int bi = 0;
		for (int i = 0; i < indexs.size(); i++) {
			int index = indexs.get(i);
			String bs = inString.substring(bi, index);
			es = inString.substring(index + targetString.length(),
					inString.length());
			bi = index + targetString.length();
			outString += bs + newString;
		}
		outString += es;

		return outString;
	}

	public static boolean isEqualExtensionFileName(String Name,
			String extensionFileName) {
		return Name.substring(Name.length() - extensionFileName.length())
				.equalsIgnoreCase(extensionFileName);
	}

	public static LinkedList<String> getSplit(String inString, String sign,
			boolean cutSign) {
		LinkedList<Integer> indexs = getAllIndexOf(inString, sign);
		LinkedList<String> outString = new LinkedList<String>();
		String es = inString;
		int bIndex = 0;
		for (int i = 0; i < indexs.size(); i++) {
			int eIndex = (cutSign) ? indexs.get(i) : indexs.get(i)
					+ sign.length();
			String bs = inString.substring(bIndex, eIndex);
			eIndex = (cutSign) ? eIndex + sign.length() : eIndex;
			es = inString.substring(eIndex);
			if (bs.length() > 0)
				outString.add(bs);
			bIndex = eIndex;
		}
		if (es.length() > 0)
			outString.add(es);
		return outString;
	}

	public static LinkedList<String> replaceNullStringToNothing(
			LinkedList<String> datas) {
		for (int i = 0; i < datas.size(); i++) {
			String data = datas.get(i);
			if (data == null) {
				datas.set(i, "");
			} else {
				if (data.equalsIgnoreCase("null")) {
					datas.set(i, "");
				}
			}
		}
		return datas;
	}
	
	public static LinkedList<String> replaceNothingStringToNull(
			LinkedList<String> datas) {
		for (int i = 0; i < datas.size(); i++) {
			String data = datas.get(i);
			if (data != null) {
				if (data.equalsIgnoreCase("null") || data.equals("")) {
					datas.set(i, null);
				}
			}
		}
		return datas;
	}

	public static LinkedList<String> replaceNullStringToNull(
			LinkedList<String> datas) {
		for (int i = 0; i < datas.size(); i++) {
			String data = datas.get(i);
			if (data != null) {
				if (data.equalsIgnoreCase("null")) {
					datas.set(i, null);
				}
			}
		}
		return datas;
	}

	public static int convertObjectToInt(Object object) {
		Integer n = convertObjectToInteger(object, false);
		return (n == null)? 0 : n;
	}
	
	public static Integer convertObjectToInteger(Object object) {
		return convertObjectToInteger(object, true);
	}

	public static Integer convertObjectToInteger(Object object, boolean nullable) {
		try {
			return Integer.parseInt(String.valueOf(object));
		} catch (Exception e) {
			if (!nullable) {
				System.out.println("Convert Object to Integer Fail!");
				e.printStackTrace();
			}
			return null;
		}
	}

	private static boolean isSlantingSign(char inChar) {
		return (inChar == US2.charAt(0) || inChar == US3.charAt(0));
	}
	
	public static List<String> toUpperCase(List<String> array){
		for (int i=0;i<array.size();i++){
			array.set(i, array.get(i).toUpperCase());
		}
		return array;
	}
	
	public static List<String> toLowerCase(List<String> array){
		for (int i=0;i<array.size();i++){
			array.set(i, array.get(i).toLowerCase());
		}
		return array;
	}
	
	public static String toUpperCaseFirst(String string){
		String fs = string.substring(0,1);
		if (string.length() > 1){
			String ls = string.substring(1);
			return fs.toUpperCase()+ls;
		}else{
			return fs.toUpperCase();
		}
	}
	
	public static String toLowerCaseFirst(String string){
		String fs = string.substring(0,1);
		if (string.length() > 1){
			String ls = string.substring(1);
			return fs.toLowerCase()+ls;
		}else{
			return fs.toLowerCase();
		}
	}
	
	public static int lastIndexOfUCL(String string) {        
	    for(int i=string.length()-1; i>=0; i--) {
	        if(Character.isUpperCase(string.charAt(i))) {
	            return i;
	        }
	    }
	    return -1;
	}

	public static void main(String args[]) {
		System.out.println(getLastDateFile("C:/temp",
				CommonData.DEFAULT_DATE_FORMAT_SIMPLE, 0, ".doc"));
	}
}
