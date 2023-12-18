package gelbooru.parser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class Commands {

	static FileReader reader;
	static FileWriter writer;

	static String data = "";
	static JSONObject jo;

	// ------- Help Commands -------

	public static void about() {
		System.out.println("Java Gelbooru.com Tags Parser v" + App.VERSION + "\n"
				+ "Allows to parse tags from post on Gelbooru.com");
		System.out.println("Known Issues:\n" + "> show updates only after restart\n"
				+ "If source.json is corrupted programm crashes");
	}

	public static void help() {
		System.out.println("Commands:");
		System.out.println("> help");
		System.out.println("> get [id] - get tags from post with [id] on Gelbooru.com");
		System.out.println("> show - show saved keys with tags in source.json");
		System.out.println("> l [key] [id] - [key] for tags, [id] on Gelbooru.com");
		System.out.println("> rm [key] - remove [key] from source.json");
		System.out.println("> exit");
	}

	// ------- Commands -------

	public static void show() throws IOException {
		init();
		System.out.println(jo.keySet());
		terminate();
	}

	public static void load(String key, int id) throws IOException {
		init();

		JSONObject post = new JSONObject();
		post.put("id", id);
		post.put("tags", Parser.getTags(id));
		jo.put(key, post);

		new FileWriter(App.src, false).close();
		jo.write(writer);

		terminate();
	}

	public static void remove(String key) throws IOException {
		init();

		jo.remove(key);
		new FileWriter(App.src, false).close();
		jo.write(writer);

		terminate();
	}

	// ------- Private -------

	private static void init() throws IOException {
		reader = new FileReader(App.src);
		writer = new FileWriter(App.src, true);

		int d = reader.read();
		while (d != -1) {
			data += (char) d;
			d = reader.read();
		}
		jo = new JSONObject(data);
	}

	private static void terminate() throws IOException {
		reader.close();
		writer.flush();
		writer.close();
	}

	// ------- Assets -------

	public static void createFile() throws IOException {
		FileWriter writer = new FileWriter(App.src);
		writer.write("{}");
		writer.flush();
		writer.close();
	}
}
