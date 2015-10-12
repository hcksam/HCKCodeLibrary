package pers.hck.readwrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

import pers.hck.common.CommonData;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelReader {
	public final static int INDEX_READTYPE_NORMAL = 0;
	public final static int INDEX_READTYPE_NOTNULL = 1;
	private File file;

	public ExcelReader() {
		file = CommonData.DEFAULT_FILE;
	}

	public ExcelReader(File file) {
		this.file = file;
	}

	public LinkedList<LinkedList<String>> getSheetData(int page, int INDEX_READTYPE) {
		return getSheetData(file, page, INDEX_READTYPE);
	}

	public void showDatas(int page, int INDEX_READTYPE) {
		showDatas(file, page, INDEX_READTYPE);
	}
	
	public static LinkedList<LinkedList<String>> getSheetData(File file, int page, int INDEX_READTYPE) {
		LinkedList<LinkedList<String>> outDatas = new LinkedList<LinkedList<String>>();
		try {
			InputStream is = new FileInputStream(file);
			Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(page);
//			System.out.println("Row: "+rs.getRows());
//			System.out.println("Column: "+rs.getColumns());
			for (int i = 0; i < rs.getRows(); i++) {
				LinkedList<String> data = new LinkedList<String>();
				boolean haveData = false;
				for (int j = 0; j < rs.getColumns(); j++) {
					Cell cell = rs.getCell(j, i);
					String s = cell.getContents();
					if (!s.equals("")) {
						data.add(s);
						haveData = true;
					} else {
						if (INDEX_READTYPE == 1 && !isColumnAllNull(rs.getColumn(j)) || INDEX_READTYPE == 0) {
							data.add(null);
						}
					}
				}
				if ((INDEX_READTYPE == 1 && haveData) || INDEX_READTYPE == 0) {
					outDatas.add(data);
				}
			}
			rwb.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return (INDEX_READTYPE == 1) ? removeNullColumn(outDatas) : outDatas;
	}

	private static LinkedList<LinkedList<String>> removeNullColumn(
			LinkedList<LinkedList<String>> datas) {
		if (datas == null) {
			return null;
		}
		if (datas.size() == 0) {
			return datas;
		}
		for (int i = datas.get(0).size() - 1; i >= 0; i--) {
			boolean allNull = true;
			for (int j = 0; j < datas.size(); j++) {
				if (datas.get(j).get(i) != null) {
					allNull = false;
				}
			}
			if (allNull) {
				for (int j = 0; j < datas.size(); j++) {
					datas.get(j).remove(i);
				}
			}
		}
		return datas;
	}

	private static boolean isColumnAllNull(Cell[] columns) {
		for (int i = 0; i < columns.length; i++) {
			String s = columns[i].getContents();
			if (!s.equals("")) {
				return false;
			}
		}
		return true;
	}

	public static void showDatas(File file, int page, int INDEX_READTYPE) {
		LinkedList<LinkedList<String>> datas = getSheetData(file, page, INDEX_READTYPE);
		for (int i = 0; i < datas.size(); i++) {
			for (int j = 0; j < datas.get(i).size(); j++) {
				System.out.print(datas.get(i).get(j) + " || ");
			}
			System.out.println();
		}
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public static void main(String[] args) {
		ExcelReader rix = new ExcelReader(new File("C:/temp",
				"Copy of QR code list_95 contact_fax_131011.xls"));
		rix.showDatas(0, ExcelReader.INDEX_READTYPE_NOTNULL);
	}
}
