package pers.hck.common;

import java.io.File;

public class CommonData {
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
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

	public final static String TagDoctype = "<!DOCTYPE";
	public final static String TagHtml = "<HTML";
	public final static String TagHead = "<HEAD";
	public final static String TagBody = "<BODY";
	public final static String TagTable = "<TABLE";
	public final static String TagTr = "<TR";
	public final static String TagTd = "<TD";
	public final static String TagB = "<B";
	public final static String TagP = "<P";
	public final static String TagA = "<A";
	public final static String TagH1 = "<H1";
	public final static String TagH2 = "<H2";
	public final static String TagBr = "<BR";
	public final static String TagSup = "<sup";
	public final static String TagSpan = "<span";
	public final static String TagUl = "<ul";
	public final static String TagLi = "<li";
	public final static String TagClose = "</";
	
	public final static String SingleTagBr = "<BR>";
	
	public final static String Sign_CloseTag = ">";
}
