package pers.hck.readwrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import pers.hck.common.CommonData;

public class FileWriter {
	private File file;
	private static String encode = CommonData.DEFAULT_ENCODE_CHINESE;

	public FileWriter() {
		this.file = CommonData.DEFAULT_FILE;
	}

	public FileWriter(File file, String encode) {
		this.file = file;
		FileWriter.encode = encode;
	}

	public void write(List<String> inDatas) {
		write(file, inDatas);
	}

	public boolean writeFile(List<String> inDatas) {
		return writeFile(file, inDatas);
	}
	
	public static void write(File file, List<String> inDatas) {
		if (writeFile(file, inDatas)) {
			System.out.println("Write file: " + file.getPath() + " successful!");
		} else {
			System.out.println("Write file: " + file.getPath() + " fail!");
		}
	}

	public static boolean writeFile(File file, List<String> inDatas) {
		if (file.getParentFile() != null) {
			file.getParentFile().mkdirs();
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, encode);
			BufferedWriter bw = new BufferedWriter(osw);
			for (int i = 0; i < inDatas.size(); i++) {
				if (inDatas.get(i) != null) {
					bw.write(inDatas.get(i));
					bw.newLine();
				}
			}
			bw.close();
			osw.close();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

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
