package hck.readWrite;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class SearchFile {
	private String rootPath;

	public SearchFile(String rootPath) {
		this.rootPath = rootPath;
	}

	public ArrayList<File> searchFile(FileFilter filter) {
		ArrayList<File> files = new ArrayList<File>();
		try {
			ArrayList<File> directorys = new ArrayList<File>();
			directorys.add(new File(rootPath));
			while (directorys.size() != 0) {
				File[] filesSrc = (filter == null) ? directorys.get(0)
						.listFiles() : directorys.get(0).listFiles(filter);
				for (int i = 0; i < filesSrc.length; i++) {
					if (filesSrc[i].isDirectory()) {
						directorys.add(filesSrc[i]);
					} else {
//						System.out.println(filesSrc[i]);
						files.add(filesSrc[i]);
					}
				}
				directorys.remove(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return files;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
}
