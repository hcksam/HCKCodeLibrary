package pers.hck.readwrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import pers.hck.common.CommonData;

public class FileReader {
	private File file;
	private String encode;
	
	public FileReader(){
		this.file = CommonData.DEFAULT_FILE;
		this.encode = CommonData.DEFAULT_ENCODE_CHINESE;
	}
	
	public FileReader(File file, String encode){
		this.file = file;
		this.encode = encode;
	}
	
	public LinkedList<String> getDatas(){
		return readInFile(false);
	}
	
	public LinkedList<String> getDatasWithSpace(){
		return readInFile(true);
	}
	
/*
	public LinkedList<String> getCoding(String[][] filter) {
		try {
			LinkedList<String> codes = readInFile(false);
			codes = (filter == null) ? codes : CodeFilter(codes,
					Integer.parseInt(filter[0][0]), filter[1]);
			return codes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<String> getCoding(LinkedList<String> inCodes, String[][] filter) {
		try {
			LinkedList<String> codes = inCodes;
			codes = (filter == null) ? codes : CodeFilter(codes,
					Integer.parseInt(filter[0][0]), filter[1]);
			return codes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
*/
	
	private LinkedList<String> readInFile(boolean needSpace) {
		LinkedList<String> codes = new LinkedList<String>();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, encode);
			BufferedReader br = new BufferedReader(isr);
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if (readLine.trim().length() > 0 && !needSpace) {
					codes.add(readLine);
				}else{
					codes.add(readLine);
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return codes;
	}

	/*
	private LinkedList<String> CodeFilter(LinkedList<String> inCode, int index,
			String[] tags) {
		LinkedList<String> outCode = new LinkedList<String>();
//		System.out.println(inCode+" "+index+" "+tags);
		boolean b = false;
		for (int i = 0; i < inCode.size(); i++) {
			String code = inCode.get(i);
			for (int j = 0; j < tags.length; j++) {
				boolean select = false;
				switch (index) {
				case 0:
					select = code.contains(tags[j]);
					break;
				case 1:
					select = !code.contains(tags[j]);
					break;
				case 2:
					if (j >= tags.length - 1 || j % 2 != 0)
						break;
					if (code.contains(tags[j])) {
						b = true;
					}
//					System.out.println(code+" "+b);
					if (tags[j + 1] == null)
						break;
					if (b && code.contains(tags[j + 1])) {
						outCode.add(code);
						b = false;
					}
				}
				if (select || b) {
					outCode.add(code);
					break;
				}
			}
		}
		return (outCode.size() <= 0) ? null : outCode;
	}
*/
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
}
