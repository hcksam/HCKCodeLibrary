package hck.common;

import java.io.File;
import java.io.FileFilter;

public class JSPFileFilter implements FileFilter {
	protected String[] keywords = { ".jsp", ".jspf" };

	@Override
	public boolean accept(File file) {
		String path = file.getPath().replace("\\", "/");
		if (file.isDirectory())
			return true;
		for (int i = 0; i < keywords.length; i++) {
			if (path.length() >= keywords[i].length()) {
				if (path.substring(path.length() - keywords[i].length())
						.toUpperCase().equals(keywords[i].toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}
}
