package gelbooru.parser;

/* Java Gelbooru.com Tags Parser
 * Allows to parse tags from post on Gelbooru.com
 */

import java.io.File;
import java.net.URI;
import java.util.Scanner;

public class App {

	public static final String VERSION = "v1.3dev";

	static final File src = new File("source.json");
	static final String ApiLink = "https://gelbooru.com/index.php?page=dapi&s=post&q=index&json=1";
	static final String ApiLinkID = ApiLink + "&id=";
	static final String PostLinkID = "https://gelbooru.com/index.php?page=post&s=view&id=";

	public static void main(String[] args) {

		Command.about();
		if (!src.exists()) {
			Assets.createFile();
		}

		CLI();
	}

	private static void CLI() {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.print("> ");
			String[] command = scanner.nextLine().split(" ");

			switch (command[0]) {
			case "help" -> Command.help();
			case "show" -> Command.show();
			case "rm" -> Command.remove(command[1]);
			case "get" -> {
				try {
					Parser.getTags(Integer.parseInt(command[1]));
				} catch (NumberFormatException e) {
					System.out.println("[ ERROR ] Incorrect post ID");
				}
			}
			// Load
			case "l" -> {
				try {
					Command.load(command[1], Integer.parseInt(command[2]));
				} catch (NumberFormatException e) {
					System.out.println("[ ERROR ] Invalid ID");
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("[ ERROR ] No enough arguments");
				}
			}

			// Get Link
			case "link" -> {
				if (Assets.isInt(command[1])) {
					System.out.println(ApiLinkID + command[1]);
				} else {
					System.out.println(ApiLinkID + Command.getID(command[1]));
				}
			}

			// Visit Link
			case "visit" -> {
				try {
					if (Assets.isInt(command[1])) {
						Browser.openWebpage(new URI(PostLinkID + command[1]));
					} else {
						Browser.openWebpage(new URI(PostLinkID + Command.getID(command[1])));
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("[ ERROR ] No enough arguments");
				} catch (Exception e) {
					System.err.println("[ ERROR ] An error occured " + e);
				}
			}
			case "dwn" -> {
				try {
					Command.download(Integer.parseInt(command[1]), command[2]);
					System.out.println("downloaded");
					
					Command.load(command[1], Integer.parseInt(command[2]));
				} catch (NumberFormatException e) {
					System.err.println("[ ERROR ] Invalid ID");
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("[ ERROR ] No enough arguments");
				} catch (Exception e) {
					System.err.println("[ ERROR ] An error occured " + e);
				}
			}

			case "exit" -> exit = true;
			}
		}
		scanner.close();
	}
}
