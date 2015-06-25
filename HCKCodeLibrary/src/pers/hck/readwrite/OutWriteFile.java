package pers.hck.readwrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import pers.hck.common.CommonData;

public class OutWriteFile {
	private File file;
	private String encode;

	public OutWriteFile() {
		this.file = CommonData.DEFAULT_FILE;
		this.encode = CommonData.DEFAULT_ENCODE_CHINESE;
	}

	public OutWriteFile(File file, String encode) {
		this.file = file;
		this.encode = encode;
	}

	public void write(ArrayList<String> inDatas) {
		if (writeFile(inDatas)) {
			System.out.println("Write file: " + file.getPath() + " successful!");
		} else {
			System.out.println("Write file: " + file.getPath() + " fail!");
		}
	}

	public boolean writeFile(ArrayList<String> inDatas) {
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
