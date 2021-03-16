package View;

import java.util.List;

import TAB2MXL.Measure;

public class Main {

	public static void main(String[] args) {
		//new MyFrame();
		String tab = "BD|x---------------|--------x-------|\n"
				+ "HH|--x-x-x-x-x-x-x-|----------------|\n"
				+ "SD|----o-------o---|oooo------------|\n"
				+ "HH|----------------|----oo----------|\n"
				+ "SD|----------------|------oo--------|\n"
				+ "BD|o-------o-------|o-------o-------|\n";
		List<Measure> measures = StringParserUtilityDrum.stringParse(tab);
	}
}