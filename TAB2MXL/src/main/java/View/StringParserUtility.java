
package View;

import TAB2MXL.Note;

public final class StringParserUtility {
	public static String stringParse(String input) {
		String lines[] = input.split("\\r?\\n");
		
		//for error testing
		for (int i = 0; i < lines.length; i++) {
			System.out.println(lines[i]);
		}
		System.out.println(lines.length);
		
		//basic checks
		if (lines[0].toUpperCase().startsWith("E")) {
			return "This is a Guitar tab.";
		}
		else if (lines[0].toUpperCase().startsWith("C")) {
			return "This is a Drum tab.";
		}
		else if (lines[0].toUpperCase().startsWith("G")) {
			return "This is a Bass tab.";
		}
		return "Boom! Translated.";
	}
}