package pers.hck.readwrite;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

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
	public final static int TitleWordWidth = WordWidth * 3;
	public final static int TitleASCIIWidth = ASCIIWidth * 3;
	public final static int TitleMinWidth = MinWidth * 3;
	
	private File file;

	public ExcelWriter() {
		file = CommonData.DEFAULT_FILE;
	}

	public ExcelWriter(File file) {
		this.file = file;
	}
	
	public boolean writeToExcel(LinkedList<LinkedList<String>> datas) {
		return writeToExcel(datas, 0, null, true, null);
	}
	
	public boolean writeToExcel(LinkedList<LinkedList<String>> datas, String title) {
		return writeToExcel(datas, 0, title, true, null);
	}
	
	public boolean writeToExcel(LinkedList<LinkedList<String>> datas, String title, boolean contentBorder) {
		return writeToExcel(datas, 0, title, contentBorder, null);
	}
	
	public boolean writeToExcel(LinkedList<LinkedList<String>> datas, String title, boolean contentBorder, Integer columnTitleLine) {
		return writeToExcel(datas, 0, title, contentBorder, columnTitleLine);
	}
	
	public boolean writeToExcel(LinkedList<LinkedList<String>> datas, int page, String title, boolean contentBorder, Integer columnTitleLine) {
		return writeToExcel(file, datas, page, title, contentBorder, columnTitleLine);
	}
	
	public static boolean writeToExcel(File file, LinkedList<LinkedList<String>> datas) {
		return writeToExcel(file, datas, 0, null, true, null);
	}
	
	public static boolean writeToExcel(File file, LinkedList<LinkedList<String>> datas, String title) {
		return writeToExcel(file, datas, 0, title, true, null);
	}
	
	public static boolean writeToExcel(File file, LinkedList<LinkedList<String>> datas, String title, boolean contentBorder) {
		return writeToExcel(file, datas, 0, title, contentBorder, null);
	}
	
	public static boolean writeToExcel(File file, LinkedList<LinkedList<String>> datas, int page, String title, boolean contentBorder, Integer columnTitleLine) {
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
			int titleWidth = 0;
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
				
				titleWidth = (!CommonFunction.isASCII(title))? 
						TitleWordWidth * title.length():TitleASCIIWidth * title.length();
				titleWidth = (titleWidth > TitleMinWidth)? titleWidth:TitleMinWidth;
			}
			
			LinkedList<String> columnTitles = new LinkedList<>();
			int rowIndex = (title == null)? 0:1;
			int columnTitleLineErr = 0;
			for (int i=0;i<datas.size();i++){
				List<String> row = datas.get(i);
				boolean border = i == 0;
				
				if (columnTitleLine != null){
					if (i == 0){
						columnTitles = new LinkedList<>(row);
					}
					if ((rowIndex+1-columnTitleLineErr) % columnTitleLine == 0){
						for (int j=0;j<columnTitles.size();j++){
							String column = columnTitles.get(j);
							WritableCellFormat format = borderCellFormat;
							Label cell = new Label(j, rowIndex, column, format);
							sheet.addCell(cell);
							int columnWidth = (!CommonFunction.isNull(column) && !CommonFunction.isDate(column) && !CommonFunction.isASCII(column))? 
									WordWidth * column.length():ASCIIWidth * column.length();
							columnWidth = (columnWidth > MinWidth)? columnWidth:MinWidth;
							maxWidth[j] = (maxWidth[j] == 0)? columnWidth:(maxWidth[j] > columnWidth)? maxWidth[j]:columnWidth;
						}
						rowIndex++;
						columnTitleLineErr++;
					}
				}
				
				for (int j=0;j<row.size();j++){
					String column = row.get(j);
					if (column == null){
						column = "";
					}
					WritableCellFormat format = (contentBorder || border)? borderCellFormat:cellFormat;
					Label cell = new Label(j, rowIndex, column, format);
					sheet.addCell(cell);
					int columnWidth = (!CommonFunction.isNull(column) && !CommonFunction.isDate(column) && !CommonFunction.isASCII(column))? 
							WordWidth * column.length():ASCIIWidth * column.length();
					columnWidth = (columnWidth > MinWidth)? columnWidth:MinWidth;
					maxWidth[j] = (maxWidth[j] == 0)? columnWidth:(maxWidth[j] > columnWidth)? maxWidth[j]:columnWidth;
				}
				rowIndex++;
			}
			
			int columnWidthSum = 0;
			for(int i=0;i<maxWidth.length;i++){
			    sheet.setColumnView(i, maxWidth[i] / Err);
			    columnWidthSum += maxWidth[i];
			}
			
			if (title != null && columnWidthSum < titleWidth){
				int err = (titleWidth - columnWidthSum) / maxWidth.length;
				for(int i=0;i<maxWidth.length;i++){
				    sheet.setColumnView(i, (maxWidth[i] + err) / Err);
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
