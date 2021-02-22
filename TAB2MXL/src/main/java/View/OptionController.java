package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;

public class OptionController {
	@FXML
	Button optionCancel;
	@FXML
	public MenuButton beatsChoice;
	@FXML
	RadioMenuItem beat1;
	@FXML
	RadioMenuItem beat2;
	@FXML
	RadioMenuItem beat3;
	@FXML
	RadioMenuItem beat4;
	static int beatType = 4;
	public void chosen1() {
		beatType = 1;
		beat2.setSelected(false);
		beat3.setSelected(false);
		beat4.setSelected(false);
		setText();
	}
	public void chosen2() {
		beatType = 2;
		setText();
		beat1.setSelected(false);
		beat3.setSelected(false);
		beat4.setSelected(false);
		
	}
	public void chosen3() {
		beatType = 3;
		setText();
		beat1.setSelected(false);
		beat2.setSelected(false);
		beat4.setSelected(false);
		
	}
	public void chosen4() {
		beat2.setSelected(false);
		beat3.setSelected(false);
		beat1.setSelected(false);
		beatType = 4;
		setText();
		
	}
	private void setText() {
		beatsChoice.setText(beatType+"/4");
	}
	
	
	public void closePopup() {
		
		optionCancel.getScene().getWindow().hide();;
	}
	
	public void confirmTranslate() {
		
	}

}
