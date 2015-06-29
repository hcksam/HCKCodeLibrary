package pers.hck.readwrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import pers.hck.common.CommonData;
import pers.hck.common.CommonFunction;
import pers.hck.utils.ReadWordUtils;

public class WordReader {
	public final static String[] FILTER_DATA = { "-  PAGE ", "-  PAGE ",
			"\r", "\n" };
	public final static String[][] FILTER_HUPERLINK = {
			{ "HYPERLINK \"http://", "" }, { "HYPERLINK \"", "" } };
	public final static String[] FILTER_SIGN = { "\r", "\n", "", "", "",
			"", "" };
	public final static Character SIGN_BR = Character.toChars(11)[0];
	public final static int INDEX_PARAGRAPHTYPE_NORMAL = 0;
	public final static int INDEX_PARAGRAPHTYPE_NOBLANKROW = 1;
	// public final static char[] sentenceSign = { '����, '���, '���, '.', '?', '!'
	// };
	private File file;
	private HWPFDocument document;
	private Range range;
	private int PARAGRAPHTYPE;
	private ArrayList<Integer[]> brIndex = new ArrayList<Integer[]>();
	private ArrayList<String> paragraphs = new ArrayList<String>();
	private Hashtable<Integer, Integer> paragraphIndexMap = new Hashtable<Integer, Integer>();

	public WordReader() {
		file = CommonData.DEFAULT_FILE;
		document = getData();
		range = document.getRange();
		PARAGRAPHTYPE = INDEX_PARAGRAPHTYPE_NORMAL;
		setVeriable();
	}

	public WordReader(File file, int INDEX_PARAGRAPHTYPE) {
		this.file = file;
		document = getData();
		range = document.getRange();
		this.PARAGRAPHTYPE = INDEX_PARAGRAPHTYPE;
		setVeriable();
	}

	private void setVeriable() {
		int pn = 0;
		for (int i = 0; i < range.numParagraphs(); i++) {
			String s = getParagraph(i, PARAGRAPHTYPE);
			Integer[] wbi = { -1, -1 };
			if (s != null) {
				ArrayList<Integer> wbs = CommonFunction.getAllIndexOf(s,
						SIGN_BR.toString());
				String es = s;
				int bi = 0;
				for (int j = 0; j < wbs.size(); j++) {
					String bs = s.substring(bi, wbs.get(j));
					es = s.substring(wbs.get(j) + 1);
					bi = wbs.get(j) + 1;
					paragraphs.add(bs);
					paragraphIndexMap.put(pn++, i);
					if (j == 0) {
						wbi[0] = i;
					}
					wbi[1] = i + j;
				}
				paragraphs.add(es);
				if (wbi[0] >= 0 && wbi[1] >= 0) {
					brIndex.add(wbi);
				}
				paragraphIndexMap.put(pn++, i);
			}
		}
	}

	public String getWholeInString() {
		return getData().getDocumentText();
	}

	// public String getParagraph(int paragraphIndex) {
	// String paragraph = range.getParagraph(paragraphIndex).text();
	//
	// for (int i = 0; i < replaceData.length; i++) {
	// int index = paragraph.indexOf(replaceData[i]);
	// if (index >= 0) {
	// String bs = paragraph.substring(0, index);
	// String es = paragraph
	// .substring(index + replaceData[i].length());
	// paragraph = bs + es;
	// }
	// }
	//
	// for (int i = 0; i < filterHyperLink.length; i++) {
	// paragraph = replaceHyperLink(paragraph, filterHyperLink[i]);
	// }
	//
	// return paragraph;
	// }

	public ArrayList<String> getParagraphs() {
		return paragraphs;
	}

	public int getTrueParagraphIndex(int paragraphIndex) {
		int pi = paragraphIndexMap.get(paragraphIndex);
		if (paragraphIndex < paragraphs.size()) {
			String s = removeSigns(range.getParagraph(pi).text());
			if (s.contains(
					paragraphs.get(paragraphIndex)))
				return pi;
			else
				return paragraphIndex;
		} else {
			return paragraphIndex;
		}
	}

	public Paragraph getParagraphProperties(int paragraphIndex) {
		int pi = getTrueParagraphIndex(paragraphIndex);
		return (pi >= 0) ? range.getParagraph(pi) : null;
	}

	public CharacterRun getCharacterProperties(int paragraphIndex, int characterIndex) {
		Paragraph p = getParagraphProperties(paragraphIndex);
		int ci = getCharacterRunsIndex(paragraphIndex, characterIndex);
		return (p != null && ci < p.numCharacterRuns()) ? p.getCharacterRun(ci)
				: null;
	}

	// public Character getCharacter(int index, int paragraphIndex) {
	// String s = null;
	// int pi = getTrueParagraphIndex(paragraphIndex);
	// if (pi >= 0) {
	// s = getParagraph(pi);
	// }
	// return (s != null && index < s.length()) ? s.charAt(index) : null;
	// }

	public Table getTable(int paragraphIndex) {
		Paragraph p = null;
		boolean inTable = false;
		int pi = getTrueParagraphIndex(paragraphIndex);
		for (int i = 0; i <= pi; i++) {
			Paragraph tp = range.getParagraph(i);
			if (tp.isInTable()) {
				if (!inTable) {
					p = tp;
					inTable = true;
				}
			} else {
				inTable = false;
			}
		}
		return (p != null) ? range.getTable(p) : null;
	}

	public int[] getTableIndex(int paragraphIndex) {
		boolean inTable = false;
		int[] index = { -1, -1 };
		int pi = getTrueParagraphIndex(paragraphIndex);
		for (int i = 0; i <= pi; i++) {
			Paragraph tp = range.getParagraph(i);
			if (tp.isInTable()) {
				if (!inTable) {
					index[0] = i;
					inTable = true;
				}
			} else {
				index[1] = i;
				inTable = false;
			}
		}
		return (index[0] >= 0 && index[1] >= 0) ? index : null;
	}

	// public int[] getTableCellParagraphIndex(int paragraphIndex) {
	// Paragraph p = getParagraphProperties(paragraphIndex);
	// try {
	// System.out.println("table: "+removeSigns(getTable(paragraphIndex).getRow(1).getCell(1).text()));
	// } catch (IllegalArgumentException iae) {
	//
	// } catch (NullPointerException npe){
	//
	// }
	// return null;
	// }
	//
	// public int[] getTablePosition(int paragraphIndex) {
	// Table table = getTable(paragraphIndex);
	// int pi = getTrueParagraphIndex(paragraphIndex);
	//
	// if (table == null) {
	// return null;
	// }
	//
	// String ps = table.getParagraph(pi - getTableIndex(pi)[0]).text();
	//
	// for (int i = 0; i < table.numRows(); i++) {
	// for (int j = 0; j < table.getRow(i).numCells(); j++) {
	// String ts = table.getRow(i).getCell(j).text();
	// if (ts.equalsIgnoreCase(ps))
	// return new int[] { i, j };
	// }
	// }
	// return null;
	// }

	public static String removeSigns(String inString) {
		for (int i = 0; i < FILTER_SIGN.length; i++) {
			int index = inString.indexOf(FILTER_SIGN[i]);
			if (index >= 0) {
				String bs = inString.substring(0, index);
				String es = inString.substring(index + FILTER_SIGN[i].length());
				inString = bs + es;
			}
		}

		for (int i = 0; i < FILTER_HUPERLINK.length; i++) {
			inString = replaceHyperLink(inString, FILTER_HUPERLINK[i]);
		}
		return inString;
	}

	private HWPFDocument getData() {
		try {
			POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream(file));
			return new HWPFDocument(fis);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getParagraph(int paragraphIndex, int INDEX_PARAGRAPHTYPE) {
		String paragraph = range.getParagraph(paragraphIndex).text();

		paragraph = removeSigns(paragraph);

		switch (INDEX_PARAGRAPHTYPE) {
		case 0:
			return paragraph;
		case 1:
			if (Filter(paragraph)) {
				return paragraph;
			}
		}
		return null;
	}

	private boolean Filter(String inString) {
		if (inString.trim().length() == 0)
			return false;
		if (inString.equals("��"))
			return false;
		for (int i = 0; i < FILTER_DATA.length; i++) {
			if (inString.trim().toLowerCase()
					.contains(FILTER_DATA[i].toLowerCase()))
				return false;
		}
		return true;
	}

	private int getCharacterRunsIndex(int paragraphIndex, int characterIndex) {
		int pi = getTrueParagraphIndex(paragraphIndex);
		Paragraph p = getParagraphProperties(paragraphIndex);
		if (characterIndex < p.numCharacterRuns()) {
			for (int i = 0; i < brIndex.size(); i++) {
				Integer[] wbi = brIndex.get(i);
				if (pi >= wbi[0] && pi <= wbi[1]) {
					for (int j = 0; j < p.numCharacterRuns(); j++) {
						if (p.getCharacterRun(j).text()
								.contains(paragraphs.get(paragraphIndex))) {
							return j;
						}
					}
				}
			}
			return characterIndex;
		} else {
			return p.numCharacterRuns()-1;
		}
	}

	// private int getTrueParagraphIndex(int paragraphIndex) {
	// int newIndex = paragraphIndex;
	// switch (PARAGRAPHTYPE) {
	// case 0:
	// newIndex = paragraphIndex;
	// break;
	// case 1:
	// newIndex = getParagraphLastIndex(paragraphIndex);
	// break;
	// }
	//
	// newIndex = getWBParagraphIndex(newIndex);
	//
	// return newIndex;
	// }

	// private int getParagraphLastIndex(int paragraphIndex) {
	// int c = -1;
	// for (int i = 0; i < range.numParagraphs(); i++) {
	// String s = getParagraph(i, INDEX_PARAGRAPHTYPE_NoBlankRow);
	// if (s != null) {
	// c++;
	// }
	//
	// if (c == paragraphIndex) {
	// return i;
	// }
	// }
	// return -1;
	// }
	//
	// private int getWBParagraphIndex(int paragraphIndex) {
	// for (int i = 0; i < brIndex.size(); i++) {
	// Integer[] wbi = brIndex.get(i);
	// if (paragraphIndex >= wbi[0] && paragraphIndex <= wbi[1]) {
	// return wbi[0];
	// }
	// }
	// return -1;
	// }

	private static String replaceHyperLink(String paragraph, String[] FILTER) {
		ArrayList<Integer> hlIndexs = CommonFunction.getAllIndexOf(paragraph,
				FILTER[0]);
		String ts = paragraph;
		int bIndex = 0;
		String les = paragraph;
		String s = "";

		for (int i = 0; i < hlIndexs.size(); i++) {
			int hlIndex = hlIndexs.get(i);
			String rs = paragraph.substring(hlIndex, ts.indexOf(FILTER[1])
					+ FILTER[1].length());
			String bs = paragraph.substring(bIndex, hlIndex);
			String es = paragraph.substring(hlIndex + rs.length());

			ts = bs + CommonFunction.getSpace(rs.length()) + es;
			bIndex = hlIndex + rs.length();
			les = es;
			s += bs;
		}
		s += les;
		return s;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public HWPFDocumentCore getDocument() {
		return document;
	}

	public void resetDocument() {
		this.document = getData();
	}
	
	public void showParagraphs(){
		for (int i=0;i<paragraphs.size();i++){
			System.out.println(i+": "+paragraphs.get(i));
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		int PARAGRAPHTYPE = WordReader.INDEX_PARAGRAPHTYPE_NOBLANKROW;
		WordReader riw = new WordReader(new File(CommonData.DEFAULT_PATH+"/press BUG/VT",
				"20131106c Free SIM.doc"), PARAGRAPHTYPE);
		
		riw.showParagraphs();
		
		System.out.println("-----------");
		System.out.println(ReadWordUtils.isParagraphAlignCenter(riw.getParagraphProperties(0)));
		System.out.println(ReadWordUtils.isParagraphAlignCenter(riw.getParagraphProperties(2)));
	}
	
	private static void TestTableSpan(){
		int PARAGRAPHTYPE = WordReader.INDEX_PARAGRAPHTYPE_NOBLANKROW;
		WordReader riw = new WordReader(new File(CommonData.DEFAULT_PATH,
				"20130621c Discover HK SIM.doc"), PARAGRAPHTYPE);
		ArrayList<String> s = riw.getParagraphs();
		for (int i = 0; i < s.size(); i++) {
			System.out.println(i + ": " + s.get(i));
			// riw.getTableCellParagraphIndex(i);
			// System.out.println(riw.getCharacterProperties(10, i).text());
			// int[] ti = riw.getTablePosition(i);
			// if (ti != null) {
			// System.out.println(riw.getTable(i).getParagraph(riw.getTrueParagraphIndex(i)-riw.getTableStartIndex(i)).text());
			// System.out.println("(" + ti[0] + "|" + ti[1] + ")");
			// }
		}
		System.out.println();

		Table table = riw.getTable(4);
		System.out.println(CommonData.TAG_TABLE
				+ CommonData.SIGN_CLOSETAG);
		int maxCol = 0;
		int maxRow = table.numRows();
		boolean spanCol = false;
		boolean spanRow = false;
		for (int i=0;i<table.numRows();i++){
			TableRow row = table.getRow(i);
			System.out.println(row.cantSplit());
			System.out.println(CommonData.TAG_TR
					+ CommonData.SIGN_CLOSETAG);
			for (int j=0;j<row.numCells();j++){
				TableCell column = row.getCell(j);
				String text = WordReader.removeSigns(column.text()).replaceAll(WordReader.SIGN_BR.toString(), CommonData.TAG_SINGLEBR);
				if (text.equals("")){
					System.out.println(CommonFunction.insertHTMLTag(CommonData.TAG_TD, text, " colspan=2"));
				}else{
					System.out.println(CommonFunction.insertHTMLTag(CommonData.TAG_TD, text));
				}
			}
			System.out.println(CommonFunction.getHTMLCloseTag(CommonData.TAG_TR));
		}
		System.out.println(CommonFunction.getHTMLCloseTag(CommonData.TAG_TABLE));
	}
	
	private static void Test1(){
		int PARAGRAPHTYPE = WordReader.INDEX_PARAGRAPHTYPE_NOBLANKROW;
		WordReader riw = new WordReader(new File(CommonData.DEFAULT_PATH,
				"20130621c Discover HK SIM.doc"), PARAGRAPHTYPE);
		// int PARAGRAPHTYPE = ReadInWord.INDEX_PARAGRAPHTYPE_Normal;
		
		// for (int i = 0; i < s.size(); i++) {
		// ArrayList<Integer> indexs = CommonFunction.getAllIndexOfWord(
		// s.get(i), "now");
		// for (int j = 0; j < indexs.size(); j++) {
		// Integer index = indexs.get(j);
		// System.out.println(i + ": "
		// + s.get(i).substring(index, index + "now".length()));
		// }
		// }

		// for (int i = 0; i < s.size(); i++) {
		// ArrayList<Integer[]> indexs = CommonFunction.getAllIndexOfURL(s
		// .get(i));
		// for (int j = 0; j < indexs.size(); j++) {
		// Integer[] index = indexs.get(j);
		// System.out.println(i + ": "
		// + s.get(i).substring(index[0], index[1]));
		// }
		// }
		// ArrayList<Integer[]> indexs =
		// CommonFunction.getAllIndexOfURL(s.get(22));
		// System.out.println(indexs.size());

		// int[] n = riw.getTableIndex(18);
		// System.out.println((n != null)? n[0] + " " + n[1]:null);

		// System.out.println("Char: " + riw.getCharacter(0, 6));
		// System.out.println("Char2: "
		// + riw.getCharacterProperties(0, 14)
		// .getFontSize());
		// System.out.println("Char3: "
		// + riw.getCharacterProperties(0, 15).isBold());
		// System.out.println("Char4: "
		// + riw.getCharacterProperties(0, 18));
		// for (int i = 0; i < 76; i++) {
		// riw.getCharacterProperties(0, i);
		// System.out.println(i + ": ");
		// }
	}
}
