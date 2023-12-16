package gelbooru.parser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {

	private static String getData(URL url) throws IOException {
		String inline = "";
		Scanner sc = new Scanner(url.openStream());
		while (sc.hasNext()) {
			inline += sc.nextLine();
		}
		sc.close();
		return inline;
	}

	public static String getTags(int id) throws IOException {
		@SuppressWarnings("deprecation")
		URL url = new URL("https://gelbooru.com/index.php?page=dapi&s=post&q=index&json=1&id=" + id);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();

		System.out.println("Response code: " + con.getResponseCode());

		System.out.println("\nJSON data in string format");
		String data = getData(url);
		System.out.println(data);

		JSONObject jo = new JSONObject(data);
		JSONArray ja = new JSONArray(jo.getJSONArray("post"));
		JSONObject post = ja.getJSONObject(0);
		String tags = post.getString("tags");

		System.out.println("\nTags:");
		System.out.println(tags);

		return tags;
	}
}
