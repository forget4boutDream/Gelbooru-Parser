package gelbooru.parser;

/* Java Gelbooru.com Tags Parser
 * Allows to parse tags from post on Gelbooru.com
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class App {

	public final static String VERSION = "1.1dev";

	final static File src = new File("source.json");
	final static String link = "https://gelbooru.com/index.php?page=post&s=view&id=";

	public static void main(String[] args) throws IOException {
		Commands.about();
		if (!src.exists())
			Commands.createFile();
		CLI();
	}

	private static void CLI() throws IOException {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.print("> ");
			String[] command = scanner.nextLine().split(" ");

			long start = System.nanoTime();

			switch (command[0]) {
			case "help" -> Commands.help();
			case "get" -> Parser.getTags(Integer.parseInt(command[1]));

			case "show" -> Commands.show();
			case "l" -> {
				Commands.load(command[1], Integer.parseInt(command[2]));
			}
			case "id" -> {
				switch (command[1]) {
				case "-link" -> {
					if (Commands.isInt(command[2]))
						System.out.println(link + command[2]);
					else
						System.out.println(link + Commands.getID(command[2]));
				}
				case "-help" -> Commands.idHelp();
				default -> System.out.println(Commands.getID(command[1]));
				}
			}

			case "visit" -> {
				if (Commands.isInt(command[1])) {
					@SuppressWarnings("deprecation")
					URL url = new URL("https://gelbooru.com/index.php?page=post&s=view&id=" + command[1]);
					TotallyNotBorrowedCodeFromStackOverFlow.openWebpage(url);
				} else {
					@SuppressWarnings("deprecation")
					URL url = new URL(
							"https://gelbooru.com/index.php?page=post&s=view&id=" + Commands.getID(command[1]));
					TotallyNotBorrowedCodeFromStackOverFlow.openWebpage(url);
				}
			}

			case "rm" -> {
				String key = scanner.next();
				Commands.remove(key);
			}
			case "exit" -> exit = true;
			}
			System.out.println("[ TIME ]: " + (System.nanoTime() - start) / 1000 + " microsecs");
		}
		scanner.close();
	}
}
