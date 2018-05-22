package module.utility;

import java.io.File;
import java.util.regex.Pattern;
import module.video.VideoSpecs;

public class Validator {
	public static boolean validatePath(String path) {
		return new File(path).exists();
	}
	
	public static boolean validateColor(String hexColor) {
		String hexPattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
		Pattern pattern = Pattern.compile(hexPattern);
		return pattern.matcher(hexColor).matches();
	}
	
	
}
