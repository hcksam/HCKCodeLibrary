package pers.hck.readwrite;

import java.io.File;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pers.hck.common.CommonData;
import pers.hck.common.CommonFunction;

public class ExcelWriter {
	public final static int WordWidth = 21;
	public final static int MinWidth = 56;
	public final static int Err = 7;
	public final static int ASCIIWidth = 10;
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
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			workbook.createSheet("Report", page);
			WritableSheet sheet = workbook.getSheet(0);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			
			int[] maxWidth = new int[]{};
			if (datas != null && datas.size() > 0 && datas.get(0) != null && datas.get(0).size() > 0){
				maxWidth = new int[datas.get(0).size()];
			}
			
			for (int i=0;i<datas.size();i++){
				ArrayList<String> row = datas.get(i);
				for (int j=0;j<row.size();j++){
					String column = String.valueOf(row.get(j));
					Label cell = new Label(j, i, column, cellFormat);
					sheet.addCell(cell);
					int columnWidth = (!CommonFunction.isNull(column) && !CommonFunction.isDate(column) && !CommonFunction.isASCII(column))? 
							WordWidth * column.length():ASCIIWidth * column.length();
					columnWidth = (columnWidth > MinWidth)? columnWidth:MinWidth;
					maxWidth[j] = (maxWidth[j] == 0)? columnWidth:(maxWidth[j] > columnWidth)? maxWidth[j]:columnWidth;
				}
			}
			
			for(int i=0;i<maxWidth.length;i++){
			    sheet.setColumnView(i, maxWidth[i] / Err);
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
