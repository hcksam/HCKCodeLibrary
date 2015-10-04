package pers.hck.readwrite;

import java.io.File;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
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
		return writeToExcel(datas, 0, null, true);
	}
	
	public boolean writeToExcel(ArrayList<ArrayList<String>> datas, String title) {
		return writeToExcel(datas, 0, title, true);
	}
	
	public boolean writeToExcel(ArrayList<ArrayList<String>> datas, String title, boolean contentBorder) {
		return writeToExcel(datas, 0, title, contentBorder);
	}
	
	public boolean writeToExcel(ArrayList<ArrayList<String>> datas, int page, String title, boolean contentBorder) {
		try {
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			workbook.createSheet("Report", page);
			WritableSheet sheet = workbook.getSheet(0);
			
			WritableCellFormat cellFormat = new WritableCellFormat();
			
			WritableCellFormat borderCellFormat = new WritableCellFormat();
			borderCellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
			borderCellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			borderCellFormat.setBorder(Border.TOP, BorderLineStyle.THIN);
			borderCellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			
			int[] maxWidth = new int[]{};
			int maxColumns = 0;
			if (datas != null && datas.size() > 0 && datas.get(0) != null && datas.get(0).size() > 0){
				maxColumns = datas.get(0).size();
				maxWidth = new int[datas.get(0).size()];
			}
			
			if (title != null){
				WritableFont titleWriteFont = new WritableFont(WritableFont.TIMES, 36, WritableFont.BOLD);
				WritableCellFormat titleCellFormat = new WritableCellFormat(titleWriteFont);
				titleCellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
				titleCellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
				titleCellFormat.setBorder(Border.TOP, BorderLineStyle.THIN);
				titleCellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
				titleCellFormat.setAlignment(Alignment.CENTRE);
				
				Label cell = new Label(0, 0, title, titleCellFormat);
				sheet.addCell(cell);
				sheet.mergeCells(0, 0, maxColumns-1, 0);
			}
			
			for (int i=0;i<datas.size();i++){
				ArrayList<String> row = datas.get(i);
				int rowIndex = (title == null)? i:i+1;
				for (int j=0;j<row.size();j++){
					String column = String.valueOf(row.get(j));
					WritableCellFormat format = (contentBorder || i == 0)? borderCellFormat:cellFormat;
					Label cell = new Label(j, rowIndex, column, format);
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
