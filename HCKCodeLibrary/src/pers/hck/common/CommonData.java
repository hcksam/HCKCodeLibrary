package pers.hck.common;

import java.io.File;

public class CommonData {
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public final static String DEFAULT_DATE_FORMAT_YEARMONTH = "yyyy-MM";
	public final static String DEFAULT_DATE_FORMAT_SIMPLE = "yyyyMMdd";
	public final static String SIGN_VERSION = "_V";
	
	public final static String DEFAULT_ENCODE = "UTF-8";
	public final static String DEFAULT_ENCODE_CHINESE = "Big5-HKSCS";
	
	public final static String DEFAULT_PATH = "C://temp";
	public final static String DEFAULT_PATH_TEST = DEFAULT_PATH
			+ "/test";
	public final static String DEFAULT_FILENAME = "t";
	public final static File DEFAULT_FILE = new File(DEFAULT_PATH,
			DEFAULT_FILENAME);
	
	public final static int INDEX_LANGUAGE_ENGLISH = 0;
	public final static int INDEX_LANGUAGE_CHINESE = 1;
	public final static int DEFAULT_LANGUAGE_NOSIGN = INDEX_LANGUAGE_ENGLISH;
	public final static String[] SIGN_LANGUAGE_ENGLISH = { "_e" };
	public final static String[] SIGN_LANGUAGE_CHINESE = { "_c" };
	public final static String[][] SIGN_LANGUAGELIST = { SIGN_LANGUAGE_ENGLISH,
		SIGN_LANGUAGE_CHINESE };

	public final static String TAG_DOCTYPE = "<!DOCTYPE";
	public final static String TAG_HTML = "<HTML";
	public final static String TAG_HEAD = "<HEAD";
	public final static String TAG_BODY = "<BODY";
	public final static String TAG_TABLE = "<TABLE";
	public final static String TAG_TR = "<TR";
	public final static String TAG_TD = "<TD";
	public final static String TAG_B = "<B";
	public final static String TAG_P = "<P";
	public final static String TAG_A = "<A";
	public final static String TAG_H1 = "<H1";
	public final static String TAG_H2 = "<H2";
	public final static String TAG_BR = "<BR";
	public final static String TAG_SUP = "<sup";
	public final static String TAG_SPAN = "<span";
	public final static String TAG_UL = "<ul";
	public final static String TAG_LI = "<li";
	public final static String TAG_CLOSE = "</";
	
	public final static String TAG_SINGLEBR = "<BR>";
	
	public final static String SIGN_CLOSETAG = ">";
}
