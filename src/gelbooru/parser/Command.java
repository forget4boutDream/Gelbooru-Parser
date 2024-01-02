package gelbooru.parser;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONObject;

public class Command {

	static FileReader reader;
	static FileWriter writer;

	static String data = "";
	static JSONObject jo;

	// ------- Help Commands -------

	public static void about() {
		System.out.println(
				"Java Gelbooru.com Parser " + App.VERSION + "\n" + "Allows to parse tags from post on Gelbooru.com");
	}

	public static void help() {
		System.out.println("help");
		System.out.println("get [id] - get tags from a post on Gelbooru.com");
		System.out.println("l [key] [id] - save id and tags to source.json");
		System.out.println("show - shows all saved keys in source.json");
		System.out.println("rm [key] - remove a key from source.json");
		System.out.println("link [id/key] - get link with id or key in source.json");
		System.out.println("visit [id/key] - visit link using a default browser");
		System.out.println("exit");
	}

	// ------- Commands -------

	public static void show() {
		init();
		System.out.println(jo.keySet());
		terminate();
	}

	public static void load(String key, int id) {
		try {
			init();

			JSONObject post = new JSONObject();
			post.put("id", id);
			post.put("tags", Parser.getTags(id));
			jo.put(key, post);

			new FileWriter(App.src, false).close();
			jo.write(writer);

			terminate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void remove(String key) {
		try {
			init();

			jo.remove(key);
			new FileWriter(App.src, false).close();
			jo.write(writer);

			terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getID(String key) {
		try {
			init();
			JSONObject content = jo.optJSONObject(key);
			int id = content.getInt("id");
			terminate();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	public static String download(int id, String name) {

		try (BufferedInputStream in = new BufferedInputStream(
				new URI(Parser.getImage(id)).toURL().openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(name + ".png")) {
			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ------- Private -------

	private static void init() {
		try {
			reader = new FileReader(App.src);
			writer = new FileWriter(App.src, true);

			int d = reader.read();
			while (d != -1) {
				data += (char) d;
				d = reader.read();
			}
			jo = new JSONObject(data);
		} catch (IOException e) {
			System.err.println("[ ERROR ] Can't init");
			e.printStackTrace();
		}

	}

	private static void terminate() {
		try {
			reader.close();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("[ ERROR ] Can't terminate");
			e.printStackTrace();
		}
	}
}
