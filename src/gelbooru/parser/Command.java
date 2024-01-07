package gelbooru.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.json.JSONObject;

// Commands used in App.java
public class Command {

	private static FileReader reader;
	private static FileWriter writer;

	private static String data = "";
	private static JSONObject jo;

	// ------- Help Commands -------

	public static void about() {
		System.out.println("Java Gelbooru.com Parser " + App.VERSION);
	}

	public static void help() {
		System.out.println("help");
		System.out.println("get [id] - get tags from a post on Gelbooru.com");
		System.out.println("l [key] [id] [flag]- save id and tags to source.json");
		System.out.println("avaible flags: -w - overwrite if there a conflict");
		System.out.println("show - shows all saved keys in source.json");
		System.out.println("rm [key] - remove a key from source.json");
		System.out.println("link [id/key] - get link with id or key in source.json");
		System.out.println("visit [id/key] - visit link using a default browser");
		System.out.println("dwn [id] [file name] - download image and save tags");
		System.out.println("exit");
	}

	// ------- Commands -------

	public static void show() {
		init();
		System.out.println(jo.keySet());
		terminate();
	}

	public static void path() {
		System.out.println(App.src.getAbsolutePath());
	}

	public static void load(String key, int id, boolean rw) {
		try {
			init();

			if (jo.keySet().contains(key) && !rw) {
				System.out.println("conflict");
				return;
			}

			JSONObject post = new JSONObject();
			post.put("id", id);
			post.put("tags", Parser.getTags(id));
			jo.put(key, post);

			new FileWriter(App.src, false).close();
			jo.write(writer);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			terminate();
		}
	}

	public static void remove(String key) {
		try {
			init();

			jo.remove(key);
			new FileWriter(App.src, false).close();
			jo.write(writer);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			terminate();
		}
	}

	public static int getID(String key) {
		try {
			init();
			JSONObject content = jo.optJSONObject(key);
			int id = content.getInt("id");
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			// Default return if there no saved
			return 0;
		} finally {
			terminate();
		}
	}

	/**
	 * Downloads image from "api_link" field in API
	 * 
	 * @param id   - Post ID
	 * @param name - File name for image to be saved
	 * 
	 *             Image will be saved with ".png" extension
	 */
	public static void download(int id, String name) {

		try (BufferedInputStream in = new BufferedInputStream(new URI(Parser.getImage(id)).toURL().openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(name + ".png")) {
			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Merge 2 source.json
	 * 
	 * @param o1    - path to first file
	 * @param o2    - path to second file
	 * @param name  - name of merged file
	 * 
	 * @param flag: "-ignore" - ignore conflicts and overwrite to the first file
	 *              "-m"      - manually choose what to write
	 * 
	 * @since v1.4
	 */
	public static void merge(String o1, String o2, String name, String flag) {
		try {
			if (o1.equals(o2)) {
				System.out.println("same file");
				return;
			}

			File f1 = new File(o1);
			String f1data = read(f1);
			JSONObject jo1 = new JSONObject(f1data);
			File f2 = new File(o2);
			JSONObject jo2 = new JSONObject(read(f2));
			System.out.println(jo1 + "\n" + jo2);

			if (jo1.keySet().equals(jo2.keySet())) {
				System.out.println("same");
				return;
			}

			File merged = new File(name + ".json");
			merged.createNewFile();
			init(merged);
			
			if (flag.equals("-ignore")) {
				for (String key : jo1.keySet()) {
					jo.put(key, jo1.opt(key));
				}

				for (String key : jo2.keySet()) {
					jo.put(key, jo2.opt(key));
				}
				jo.write(writer);
			}
			else if (flag.equals("-m")) {
				
				
				
			} else {
				System.out.println("flag null");
			}

		} catch (NullPointerException e) {
		} catch (IndexOutOfBoundsException e) {
			PrintErr.noEnoughArgs();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			terminate();
		}
	}

	// ------- Private -------

	private static void init() {
		init(App.src);
	}

	private static void init(File file) {
		try {
			reader = new FileReader(file);
			writer = new FileWriter(file, true);

			int d = reader.read();
			while (d != -1) {
				data += (char) d;
				d = reader.read();
			}
			jo = new JSONObject(data);
		} catch (FileNotFoundException e) {
			PrintErr.fileNotFound();
		} catch (IOException e) {
			System.err.println("[ ERROR ] Can't init");
			e.printStackTrace();
		}
	}

	private static String read(File file) {
		try (FileReader reader = new FileReader(file)) {
			String _data = "";

			int d = reader.read();
			while (d != -1) {
				_data += (char) d;
				d = reader.read();
			}

			return _data;
		} catch (FileNotFoundException e) {
			PrintErr.fileNotFound();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void terminate() {
		try {
			reader.close();
			writer.flush();
			writer.close();
			data = "";
		} catch (NullPointerException e) {

		} catch (IOException e) {
			System.err.println("[ ERROR ] Can't terminate");
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unused")
	private static boolean merge_load(String key) {
		
		return false;
	}
}
