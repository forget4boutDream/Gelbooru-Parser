package gelbooru.parser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {
	
	// ------- Get Data -------
	
	public static String getData(URI uri) {
		try {
			return getData(uri.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getData(URL url) {
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
	
	// ------- Parser -------
	
	public static String getImage(int id) {
		try {
			URL url = new URI(App.ApiLinkID + id).toURL();

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();

			String data = getData(url);

			JSONObject jo = new JSONObject(data);
			JSONArray ja = new JSONArray(jo.getJSONArray("post"));
			JSONObject post = ja.getJSONObject(0);

			String file_url = post.getString("file_url");
			return file_url;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getTags(int id) {
		try {
			URL url = new URI(App.ApiLinkID + id).toURL();

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
			e.printStackTrace();
			return null;
		}
	}
}
