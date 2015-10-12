package pers.hck.readwrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileUtil {
	public static boolean copyFile(File inFile, File outFile) {
		try {
			outFile.getParentFile().mkdirs();
			FileInputStream fileInputStream = new FileInputStream(inFile);
			FileChannel inChannel = fileInputStream.getChannel();
			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
			FileChannel outChannel = fileOutputStream.getChannel();
			outChannel.transferFrom(inChannel, 0, inChannel.size());
			
			fileInputStream.close();
			fileOutputStream.close();
			inChannel.close();
			outChannel.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean moveFile(File inFile, File outFile) {
		try {
			boolean done = copyFile(inFile, outFile);

			if (done){
				return inFile.delete();
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
