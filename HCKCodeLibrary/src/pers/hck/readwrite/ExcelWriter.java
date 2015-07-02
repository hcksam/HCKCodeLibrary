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
	
	public boolean writeToCSV(ArrayList<ArrayList<String>> datas) {
		try {
			String name = file.getName().replaceAll(".xls", ".csv").replaceAll(".xlsx", ".csv");
			File csvFile = new File(file.getParent(),name);
			WritableWorkbook workbook = Workbook.createWorkbook(csvFile);
			workbook.createSheet("Report", 0);
			WritableSheet sheet = workbook.getSheet(0);
			
			for (int i=0;i<datas.size();i++){
				ArrayList<String> row = datas.get(i);
				for (int j=0;j<row.size();j++){
					String column = row.get(j);
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
