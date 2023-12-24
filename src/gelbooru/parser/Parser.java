package gelbooru.parser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {

	private static String getData(URL url) {
		try (Scanner sc = new Scanner(url.openStream())) {
			String inline = "";
			while (sc.hasNext()) {
				inline += sc.nextLine();
			}
			sc.close();
			return inline;
		} catch (IOException e) {
			System.err.println("[ PARSER ] An error occured: " + e);
		}
		return null;
	}

	public static String getTags(int id) {
		try {
			URI uri = new URI(App.ApiLink + "&id=" + id);
			URL url = uri.toURL(); // new URL(link) is deprecated

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();

			System.out.println("Response code: " + con.getResponseCode());

			String data = getData(url);

			JSONObject jo = new JSONObject(data);
			JSONArray ja = new JSONArray(jo.getJSONArray("post"));
			JSONObject post = ja.getJSONObject(0);
			String tags = post.getString("tags");

			System.out.println("\nTags:");
			System.out.println(tags);

			return tags;
		} catch (Exception e) {
			System.out.println(e);
			return "";
		}
	}
}
