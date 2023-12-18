package gelbooru.parser;

/* Java Gelbooru.com Tags Parser
 * Allows to parse tags from post on Gelbooru.com
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {
	
	public final static String VERSION = "1.0";
	
	final static File src = new File("source.json");
	
	public static void main(String[] args) throws IOException {
		Commands.about();
		if(!src.exists()) Commands.createFile();
		CLI();
	}
	
	private static void CLI() throws IOException {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		
		while (!exit) {
			System.out.print("> ");
			String command = scanner.next();

			switch (command) {
			case "help" -> Commands.help();
			case "get" -> {
				int id = scanner.nextInt();
				Parser.getTags(id);
			}
			case "show" -> Commands.show();
			case "l" -> {
				String key = scanner.next();
				int id = scanner.nextInt();
				Commands.load(key, id);
			}
			case "rm" -> {
				String key = scanner.next();
				Commands.remove(key);
			}
			case "exit" -> exit = true;
			}
		}
		scanner.close();
	}
}
