package View;

import java.util.List;

import TAB2MXL.Measure;

public class Main {

	public static void main(String[] args) {
		//new MyFrame();
		String tab = "CC|x---------------|--------x-------|\n"
				+ "HH|--x-x-x-x-x-x-x-|----------------|\n"
				+ "SD|----o-------o---|oooo------------|\n"
				+ "HT|----------------|----oo----------|\n"
				+ "MT|----------------|------oo--------|\n"
				+ "BD|o-------o-------|o-------o-------|";
		String tab1 = "|-------1-------1-|-1---------------|-1---------------|\r\n"
				+ "|-----2-----------|-2---------------|-1---------------|\r\n"
				+ "|---2-------------|-2---------------|-1---------------|\r\n"
				+ "|-0---------------|-0---------------|-1---------------|";
		//List<Measure> measures = StringParserUtilityDrum.stringParse(tab);
		List<Measure> measures1 = StringParserUtilityBass.stringParse(tab1);
	}
}