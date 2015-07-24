package pers.hck.readwrite;

import java.io.File;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pers.hck.common.CommonData;

public class ExcelWriter {
	private File file;

	public ExcelWriter() {
		file = CommonData.DEFAULT_FILE;
	}

	public ExcelWriter(File file) {
		this.file = file;
	}
	
	public boolean writeToExcel(ArrayList<ArrayList<String>> datas) {
		return writeToExcel(datas,0);
	}
	
	public boolean writeToExcel(ArrayList<ArrayList<String>> datas, int page) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			workbook.createSheet("Report", page);
			WritableSheet sheet = workbook.getSheet(0);
			
			for (int i=0;i<datas.size();i++){
				ArrayList<String> row = datas.get(i);
				for (int j=0;j<row.size();j++){
					String column = String.valueOf(row.get(j));
					Label cell = new Label(j,i,column);
					sheet.addCell(cell);
				}
			}

			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
