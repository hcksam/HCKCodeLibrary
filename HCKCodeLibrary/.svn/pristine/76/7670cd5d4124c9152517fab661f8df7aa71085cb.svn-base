package hck.readWrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class HandleFile {
	public static boolean copyFile(File InFile, File OutFile) {
		try {
			OutFile.getParentFile().mkdirs();
			FileChannel inChannel = new FileInputStream(InFile).getChannel();
			FileChannel outChannel = new FileOutputStream(OutFile).getChannel();
			outChannel.transferFrom(inChannel, 0, inChannel.size());
			inChannel.close();
			outChannel.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
