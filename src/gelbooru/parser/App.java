package gelbooru.parser;

/**
 * Java Gelbooru.com Parser
 * 
 * Allows to download image from Gelbooru
 * with saving tags and id in source.json
 */

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
 Issues:
 [+] >link gives API link
 [+] >visit doesnt work on linux
 [+] data updates only after restart

 New:
 added merge command [BETA]
 added conflict warning and flag to overwrite
 added clear src file command
 added >path command
*/
public class App {

	public static final String VERSION = "v1.4";

	static final File data = new File("data.json");
	static final String ApiLink = "https://gelbooru.com/index.php?page=dapi&s=post&q=index&json=1";
	static final String ApiLinkID = ApiLink + "&id=";
	static final String PostLinkID = "https://gelbooru.com/index.php?page=post&s=view&id=";

	public static void main(String[] args) {

		// Set args from IDE

		//String cm = "help";
		//args = cm.split(" ");
		// Comment ^^ lines to run without args

		Command.about();
		if (!data.exists()) {
			Assets.createFile();
		}

		if (args.length > 0) {
			List<String> command = new ArrayList<>(Arrays.asList(args));
			proc(command);
		} else {
			CLI();
		}

	}

	private static void proc(List<String> command) {
		switch (command.get(0)) {
		case "help" -> Command.help();
		case "show" -> Command.show();
		case "rm" -> Command.remove(command.get(1));
		case "clear" -> Assets.createFile(); // It does the same thing lmao
		case "path" -> Command.path();
		case "get" -> {
			try {
				Parser.getTags(Integer.parseInt(command.get(1)));
			} catch (NumberFormatException e) {
				PrintErr.incorrectPostID();
			}
		}

		// Load
		case "l" -> {
			try {
				if (command.size() <= 2) {
					PrintErr.noEnoughArgs();
					break;
				}
				Command.load(command.get(1), Integer.parseInt(command.get(2)), command.contains("-w"));

			} catch (NumberFormatException e) {
				PrintErr.incorrectPostID();
			}
		}

		// Get Link
		case "link" -> {
			if (Assets.isInt(command.get(1))) {
				System.out.println(PostLinkID + command.get(1));
			} else {
				System.out.println(PostLinkID + Command.getID(command.get(1)));
			}
		}

		// Visit Link
		case "visit" -> {
			try {
				if (Assets.isInt(command.get(1))) {
					Browser.openWebpage(new URI(PostLinkID + command.get(1)));
				} else {
					Browser.openWebpage(new URI(PostLinkID + Command.getID(command.get(1))));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				PrintErr.noEnoughArgs();
			} catch (Exception e) {
				PrintErr.defaultErr(e);
			}
		}

		// Download image
		case "dwn" -> {
			try {
				if (command.size() <= 2) {
					PrintErr.noEnoughArgs();
				}
				Command.download(Integer.parseInt(command.get(1)), command.get(2));
				System.out.println("Downloaded.");

				Command.load(command.get(2), Integer.parseInt(command.get(1)), false);
			} catch (NumberFormatException e) {
				PrintErr.incorrectPostID();
			} catch (Exception e) {
				PrintErr.defaultErr(e);
			}
		}

		// Merge
		case "merge" -> {
			try {
				if (command.size() <= 3) {
					PrintErr.noEnoughArgs();
					break;
				}
				if (!command.contains("-ignore") && !command.contains("-m")) {
					command.add(null);
				}

				Command.merge(command.get(1), command.get(2), command.get(3), command.get(4));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// case "exit" -> exit = true;
		default -> {
			System.out.println("wrong command, type >help for commands");
		}
		}
	}

	private static void CLI() {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.print("> ");
			List<String> command = new ArrayList<>(Arrays.asList(scanner.nextLine().split(" ")));
			if (command.contains("exit")) {
				System.exit(0);
			}
			proc(command);
		}
		scanner.close();
	}
}
