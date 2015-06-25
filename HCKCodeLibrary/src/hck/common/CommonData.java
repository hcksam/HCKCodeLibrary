package hck.common;

import java.io.File;

public class CommonData {
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATE_FORMAT_Simple = "yyyyMMdd";
	public final static String FileType_Excel = ".xls";
	public final static String Sign_Version = "_V";
	
	public final static String DefaultEncode = "UTF-8";
	public final static String DefaultChineseEncode = "Big5-HKSCS";
	
	public final static String DefaultPathParent = "C://temp";
	public final static String DefaultTestPathParent = DefaultPathParent
			+ "/test";
	public final static String DefaultFileName = "t";
	public final static File DefaultFile = new File(DefaultPathParent,
			DefaultFileName);

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

	public final static int Index_LanguageEnglish = 0;
	public final static int Index_LanguageChinese = 1;
	public final static int DefaultNoSignLanguage = Index_LanguageEnglish;
	public final static String[] Sign_LanguageEnglish = { "_e" };
	public final static String[] Sign_LanguageChinese = { "_c" };
	public final static String[][] Sign_LanguageList = { Sign_LanguageEnglish,
			Sign_LanguageChinese };
	public final static String[] Folder_LanguageEnglish = { "/English" };
	public final static String[] Folder_LanguageChinese = { "/Chinese" };
	public final static String[][] Folder_LanguageList = {
			Folder_LanguageEnglish, Folder_LanguageChinese };
	
	public final static String ExtensionFileName_Doc = ".doc";
	public final static String ExtensionFileName_Xls = ".xls";
	public final static String ExtensionFileName_Html = ".html";
	public final static String ExtensionFileName_Xml = ".xml";
}
