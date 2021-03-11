package View;

import java.util.List;

import TAB2MXL.Measure;

public class Main {

	public static void main(String[] args) {
		new MyFrame();
		String tab = "CC|x---------------|--------x-------|\n"
				+ "HH|--x-x-x-x-x-x-x-|----------------|\n"
				+ "SD|----o-------o---|oooo------------|\n"
				+ "HT|----------------|----oo----------|\n"
				+ "MT|----------------|------oo--------|\n"
				+ "BD|o-------o-------|o-------o-------|";
		List<Measure> measures = StringParserUtility.stringParse(tab);
	}
}