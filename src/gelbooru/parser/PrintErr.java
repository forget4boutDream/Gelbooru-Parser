package gelbooru.parser;

/**
 * Print statements with error
 * 
 * @since v1.4
 */
public class PrintErr {

	// Changing will be available in future
	private static String generalErrFormat = "[ ERROR ]";
	private static String parserErrFormat = "[ PARSER ]";
	
	public static void incorrectPostID() {
		System.err.println(generalErrFormat + " Incorrect post ID");
	}

	public static void noEnoughArgs() {
		System.err.println(generalErrFormat + " No enough arguments");
	}
	
	public static void fileNotFound() {
		System.err.println(generalErrFormat + " File not found");
	}

	public static void defaultErr(Exception e) {
		System.err.println(generalErrFormat + " An error occured: " + e);
	}
	
	public static void parserDefaultErr(Exception e) {
		System.err.println(parserErrFormat + " An error occured: " + e);
	}
}
