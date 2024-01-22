package gelbooru.parser;

import java.io.File;
import java.io.FileWriter;

public class Assets {

	public static void createFile() {
		createFile(App.data);
	}
	
	public static void createFile(File src) {
		try {
			FileWriter writer = new FileWriter(src, false);
			// Important because JSONObject throws exceptions
			writer.write("{}");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return boolean, is String an int value
	 * @param str - String value that will be checked
	 * 
	 * New version @since v1.3
	 */
	public static boolean isInt(String str) {
		return str.matches("-?\\d+");
	}
}
