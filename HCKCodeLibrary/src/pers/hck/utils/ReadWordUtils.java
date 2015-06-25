package pers.hck.utils;

import org.apache.poi.hwpf.converter.AbstractWordUtils;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;

public class ReadWordUtils extends AbstractWordUtils {
	public static String getParagraphAlign(Paragraph paragraphProperties) {
		if (paragraphProperties != null) {
			String paragraphAlign = getJustification(paragraphProperties
					.getJustification());
			if (isNotEmpty(paragraphAlign)) {
				return paragraphAlign;
			} else {
				System.out.println("No Paragraph Align!");
				return null;
			}
		}else{
			System.out.println("ReadWordUtils.getParagraphAlign");
			System.out.println("Null paragraphProperties input!");
			return null;
		}
	}

	public static int getFontSize(CharacterRun characterProperties){
		if (characterProperties != null) {
			return characterProperties.getFontSize() / 2;
		}else{
			System.out.println("ReadWordUtils.getFontSize");
			System.out.println("Null characterProperties input!");
			return -1;
		}
	}

	public static boolean isParagraphAlignCenter(Paragraph paragraphProperties) {
		return getParagraphAlign(paragraphProperties)
				.equalsIgnoreCase("center");
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
}
