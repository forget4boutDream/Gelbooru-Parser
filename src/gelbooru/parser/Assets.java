package gelbooru.parser;

import java.io.FileWriter;

public class Assets {

	public static void createFile() {
		try {
			FileWriter writer = new FileWriter(App.src);
			// Important because JSONObject throws exceptions
			writer.write("{}");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
