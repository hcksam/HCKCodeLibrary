package pers.hck.readwrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import pers.hck.common.CommonData;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadInExcel {
	public final static int INDEX_READTYPE_NORMAL = 0;
	public final static int INDEX_READTYPE_NOTNULL = 1;
	private File file;

	public ReadInExcel() {
		file = CommonData.DEFAULT_FILE;
	}

	public ReadInExcel(File file) {
		this.file = file;
	}

	public ArrayList<ArrayList<String>> getSheetData(int page, int INDEX_READTYPE) {
		ArrayList<ArrayList<String>> outDatas = new ArrayList<ArrayList<String>>();
		try {
			InputStream is = new FileInputStream(file);
			Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(page);
			for (int i = 0; i < rs.getRows(); i++) {
				ArrayList<String> data = new ArrayList<String>();
				boolean haveData = false;
				for (int j = 0; j < rs.getColumns(); j++) {
					Cell cell = rs.getCell(j, i);
					String s = cell.getContents();
					if (!s.equals("")) {
						data.add(s);
						haveData = true;
					} else {
						if (!isColumnAllNull(rs.getColumn(j))) {
							data.add(null);
						}
					}
				}
				if (haveData) {
					outDatas.add(data);
				}
			}
			rwb.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return (outDatas.size() > 0) ? removeNullColumn(outDatas) : outDatas;
	}

	private ArrayList<ArrayList<String>> removeNullColumn(
			ArrayList<ArrayList<String>> datas) {
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

	private boolean isColumnAllNull(Cell[] columns) {
		for (int i = 0; i < columns.length; i++) {
			String s = columns[i].getContents();
			if (!s.equals("")) {
				return false;
			}
		}
		return true;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void showDatas(int page, int INDEX_READTYPE) {
		ArrayList<ArrayList<String>> datas = getSheetData(page, INDEX_READTYPE);
		for (int i = 0; i < datas.size(); i++) {
			for (int j = 0; j < datas.get(i).size(); j++) {
				System.out.print(datas.get(i).get(j) + " || ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		ReadInExcel rix = new ReadInExcel(new File("C:/temp",
				"Copy of QR code list_95 contact_fax_131011.xls"));
		rix.showDatas(0, ReadInExcel.INDEX_READTYPE_NOTNULL);
	}
}
