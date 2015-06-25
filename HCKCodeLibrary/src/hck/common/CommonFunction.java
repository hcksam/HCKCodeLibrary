package hck.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommonFunction {
	private final static String MS = "@";
	private final static String SS = " ";
	private final static String US1 = ".";
	private final static String US2 = "/";
	private final static String US3 = "\\";
	private final static String US4 = ":";
	private final static String US5 = "http";
	private final static String Warning_DateFormat = "Date format wrong!";
	public final static char spaceSign = Character.toChars(1)[0];

	public static boolean isDate(String inString, String DateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
			Date date = sdf.parse(inString);
			return (date != null) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public static String getStringToday(String DateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
		Date today = new Date();
		return sdf.format(today);
	}

	public static String getStringDate(Date InDate, String DateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
		return sdf.format(InDate);
	}

	public static Date getDate(String InDate, String DateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
			return sdf.parse(InDate);
		} catch (Exception e) {
			System.out.println("InDate: " + InDate);
			System.out.println("DateFormat: " + DateFormat);
			System.out.println(Warning_DateFormat);
			return null;
		}
	}

	public static Date getLaterDate(String InDate1, String InDate2,
			String DateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
			Date d1 = sdf.parse(InDate1);
			Date d2 = sdf.parse(InDate2);
			return (d1.compareTo(d2) > 0) ? d1 : d2;
		} catch (Exception e) {
			System.out.println("Date1: " + InDate1);
			System.out.println("Date2: " + InDate2);
			System.out.println("DateFormat: " + DateFormat);
			System.out.println(Warning_DateFormat);
			return null;
		}
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

	public static String getLastDateFile(String Parent, String DateFormat,
			int DateBeginIndex, String ExtensionFileName) {
		try {
			File file = new File(Parent);
			String[] files = file.list();
			String lastFile = null;
			if (files != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
				String lastDateFile = null;
				for (int i = 0; i < files.length; i++) {
					boolean filter = (ExtensionFileName == null) ? true
							: (files[i].length() < ExtensionFileName.length()) ? false
									: isEqualExtensionFileName(files[i],
											ExtensionFileName);
					if (filter == true)
						lastFile = files[i];
					String fileDate = (files[i].length() >= DateFormat.length()) ? files[i]
							.substring(DateBeginIndex, DateFormat.length())
							: null;

					if (filter && isDate(fileDate, DateFormat)) {
						if (lastDateFile != null) {
							String InDate1 = lastDateFile.substring(
									DateBeginIndex, DateFormat.length());
							Date lastDate = getLaterDate(InDate1, fileDate,
									DateFormat);
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

	public static String insertHTMLTag(String Tag, String text) {
		return Tag + CommonData.Sign_CloseTag + text + getHTMLCloseTag(Tag);
	}

	public static String insertHTMLTag(String Tag, String text,
			String attribute) {
		return Tag + attribute + CommonData.Sign_CloseTag + text
				+ getHTMLCloseTag(Tag);
	}

	public static String getHTMLCloseTag(String Tag) {
		String tag = Tag.substring(1);
		return CommonData.TagClose + tag + CommonData.Sign_CloseTag;
	}

	public static ArrayList<Integer> getAllIndexOf(String Target, String keyword) {
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		int index = Target.indexOf(keyword);
		String s = Target;
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
			s += spaceSign;
		}
		return s;
	}

	public static ArrayList<Integer[]> getAllIndexOfURL(String Target) {
		ArrayList<Integer[]> indexs = new ArrayList<Integer[]>();
		ArrayList<Integer> wwwIndexs = getAllIndexOf(Target, US1);
		int doneIndex = 0;

		for (int i = 0; i < wwwIndexs.size(); i++) {
			int wwwIndex = wwwIndexs.get(i);
			if (wwwIndex < doneIndex) {
				continue;
			}

			if (wwwIndex != 0 && wwwIndex + 2 < Target.length()) {
				Integer[] index = new Integer[2];

				for (int j = wwwIndex - 1; j >= 0; j--) {
					if (!isEnglish(Target.charAt(j))) {
						index[0] = j + 1;
						break;
					}
					if (!Character.isLetter(Target.charAt(j))) {
						if (Target.charAt(j) == SS.charAt(0)
								|| Target.charAt(j) == US4.charAt(0)
								|| isSlantingSign(Target.charAt(j))
								&& Target.charAt(j + 1) != US1.charAt(0)) {
							if (isSlantingSign(Target.charAt(j)) && j >= 6) {
								if (isSlantingSign(Target.charAt(j - 1))) {
									if (Target.substring(j - 6, j - 1)
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

				for (int j = wwwIndex + 1; j < Target.length(); j++) {
					if (!isEnglish(Target.charAt(j))) {
						index[1] = j;
						break;
					}
					if (!Character.isLetter(Target.charAt(j))
							&& Target.charAt(j - 1) == US1.charAt(0)) {
						index[1] = j - 1;
						break;
					}
					if (Target.charAt(j) == SS.charAt(0)) {
						if (Character.isLetter(Target.charAt(j - 1))) {
							index[1] = j;
							break;
						} else {
							index[1] = j - 1;
							break;
						}
					}
					if (j == Target.length() - 1
							&& Target.charAt(j) == US1.charAt(0)) {
						index[1] = j;
						break;
					}
				}

				if (index[0] == null)
					index[0] = 0;
				if (index[1] == null)
					index[1] = Target.length();

				String url = Target.substring(index[0], index[1]);
				int usIndex = url.indexOf(US1);
				if (usIndex >= 1) {
					if (url.substring(usIndex).length() >= 3) {
						boolean isEmail = false;
						if (index[0] != 0) {
							isEmail = Target.charAt(index[0] - 1) == MS
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

	public static ArrayList<Integer[]> getAllIndexOfEmail(String Target) {
		ArrayList<Integer[]> indexs = new ArrayList<Integer[]>();
		ArrayList<Integer> mailIndexs = getAllIndexOf(Target, MS);
		for (int i = 0; i < mailIndexs.size(); i++) {
			int mailIndex = mailIndexs.get(i);
			if (mailIndex != 0 && mailIndex + 3 < Target.length()) {
				Integer[] index = new Integer[2];

				for (int j = mailIndex - 1; j >= 0; j--) {
					if (!Character.isLetter(Target.charAt(j))
							&& Target.charAt(j) != US1.charAt(0)) {
						index[0] = j + 1;
						break;
					}
				}

				for (int j = mailIndex + 1; j < Target.length(); j++) {
					if (!Character.isLetter(Target.charAt(j))
							&& Target.charAt(j) != US1.charAt(0)) {
						index[1] = j;
						break;
					}
				}

				if (index[0] == null)
					index[0] = 0;
				if (index[1] == null)
					index[1] = Target.length();

				indexs.add(index);
			}
		}
		return indexs;
	}

	public static ArrayList<Integer> getAllIndexOfWord(String Target,
			String Word) {
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		ArrayList<Integer> dIndexs = getAllIndexOf(Target, Word);
		for (int i = 0; i < dIndexs.size(); i++) {
			boolean left = false;
			boolean right = false;
			int dIndex = dIndexs.get(i);

			if (dIndex == 0) {
				left = true;
			} else {
				if (!Character.isLetter(Target.charAt(dIndex - 1))) {
					left = true;
				} else {
					if (!isEnglish(Target.charAt(dIndex - 1))) {
						left = true;
					}
				}
			}

			if (dIndex + Word.length() >= Target.length() - 1) {
				right = true;
			} else {
				if (!Character.isLetter(Target.charAt(dIndex + Word.length()))) {
					right = true;
				} else {
					if (!isEnglish(Target.charAt(dIndex + Word.length()))) {
						right = true;
					}
				}
			}

			if (left && right) {
				boolean ignore = false;
				ArrayList<Integer[]> ignoreIndexs = getAllIndexOfURL(Target);
				for (int j = 0; j < ignoreIndexs.size(); j++) {
					Integer[] ignoreIndex = ignoreIndexs.get(j);
					if (dIndex >= ignoreIndex[0] && dIndex < ignoreIndex[1]) {
						ignore = true;
						break;
					}
				}

				if (!ignore) {
					ignoreIndexs = getAllIndexOfEmail(Target);
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
		ArrayList<Integer> indexs = getAllIndexOf(inString, targetString);
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
			String ExtensionFileName) {
		return Name.substring(Name.length() - ExtensionFileName.length())
				.equalsIgnoreCase(ExtensionFileName);
	}

	public static ArrayList<String> getSplit(String inString, String Sign,
			boolean cutSign) {
		ArrayList<Integer> indexs = getAllIndexOf(inString, Sign);
		ArrayList<String> outString = new ArrayList<String>();
		String es = inString;
		int bIndex = 0;
		for (int i = 0; i < indexs.size(); i++) {
			int eIndex = (cutSign) ? indexs.get(i) : indexs.get(i)
					+ Sign.length();
			String bs = inString.substring(bIndex, eIndex);
			eIndex = (cutSign) ? eIndex + Sign.length() : eIndex;
			es = inString.substring(eIndex);
			if (bs.length() > 0)
				outString.add(bs);
			bIndex = eIndex;
		}
		if (es.length() > 0)
			outString.add(es);
		return outString;
	}

	private static boolean isSlantingSign(char inChar) {
		return (inChar == US2.charAt(0) || inChar == US3.charAt(0));
	}

	public static void main(String args[]) {
		System.out.println(getLastDateFile("C:/temp",
				CommonData.DATE_FORMAT_Simple, 0, ".doc"));
	}
}
