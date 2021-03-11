
package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.XmlGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {
	/*
	 * ----------------------------------------------- variable for two screen
	 * communication
	 */
	private static TextArea INPUT;
	private static Button TRANSLATE;
	private static Button DELETEBUTTON;
	//private static int CONFIRM;
	// ------------------------------------------------

	@FXML
	Button deleteButton1;
	@FXML
	Button guitarButton;
	@FXML
	Button bassButton;
	@FXML
	Button drumButton;
	@FXML
	public Button translateButton;
	@FXML
	public TextArea textInput;
	Type selected;
	@FXML
	Button fileButton;
	@FXML
	Button deleteButton;
	File file;
	String content;
	@FXML
	CheckBox autoDetect;
	@FXML
	Button optionCancel;
	@FXML
	Button optionConfirm;
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
	/**
	 * ------------ Alert Variable ----------------
	 */
	@FXML
	Button clearConfirm;
	@FXML
	Button clearCancel;
	/*
	 * ------------ Reset Variable -----------------
	 * 
	 */
	@FXML
	Button resetConfirm;
	@FXML
	Button resetCancel;
	// ------------Magic Variables-------------//
	boolean selectAll;
	// ----------------------------------------//

	static int beatType = 4;
	public static String previousText;

	public void intialize() {
		translateButton.disableProperty().bind(textInput.textProperty().isEmpty());
		textInput.textProperty().addListener((val1, val2, newVal) -> {
			if (newVal.isEmpty()) {
				translateButton.setText("Translate");
			}
		});
	}

	public void guitarButtonClicked() {
		selected = Type.GUITAR;
		bassButton.setStyle(
				"-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: #828F9C;");
		drumButton.setStyle(
				"-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: #828F9C;");
		guitarButton
				.setStyle("-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: black");
	}

	public void drumButtonClicked() {
		selected = Type.DRUM;
		drumButton
				.setStyle("-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: black");
		bassButton.setStyle(
				"-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: #828F9C;");
		guitarButton.setStyle(
				"-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: #828F9C;");
	}

	public void bassButtonClicked() {
		selected = Type.BASS;
		bassButton
				.setStyle("-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: black");
		drumButton.setStyle(
				"-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: #828F9C;");
		guitarButton.setStyle(
				"-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: #828F9C;");
	}

	enum Type {
		GUITAR, BASS, DRUM
	}

	public void hoverButtonChange() {
		drumButton.getScene().setCursor(Cursor.HAND);
	}

	public void hoverButtonBack() {
		drumButton.getScene().setCursor(Cursor.DEFAULT);
	}

	public void fileTooltipHover() {
		hoverButtonChange();
		Tooltip fileTip = new Tooltip("Upload a File");
		fileButton.setTooltip(fileTip);
		fileTip.setShowDelay(new Duration(0));
	}

	public void deleteTooltipHover() {
		hoverButtonChange();
		Tooltip fileTip = new Tooltip("Restart");
		deleteButton.setTooltip(fileTip);
		fileTip.setShowDelay(new Duration(0));
	}

	public void uploadFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Tab File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(fileButton.getScene().getWindow());
		// System.out.println(file);
		putString();

	}

	private void putString() {
		try {
			if (file != null) {
				Scanner fileIn = new Scanner(file);
				if (!textInput.getText().isEmpty()) {
					textInput.clear();
				}
				while (fileIn.hasNextLine()) {
					textInput.appendText(fileIn.nextLine() + "\n");
				}
				fileIn.close();
				if (translateButton.getText().equals("Save")) {
					translateButton.setText("Translate");
				}
				if (translateButton.isDisable()) {
					translateButton.setDisable(false);
				}
				if (autoDetect.isSelected()) {
					detectInst();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clear() {
		//previousText = textInput.getText();
		INPUT = textInput;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Reset.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Confirm Restart");
			popup.setScene(new Scene(root, 322, 140));

			popup.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void dragDropFile() {
		textInput.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();
			if (db.hasFiles()
					&& db.getFiles().size() == 1 /* && Files.probeContentType(db.getFiles().get(0).equals(")) */) {

				try {
					Path path = FileSystems.getDefault().getPath(db.getFiles().get(0).getPath());
					if (!Files.probeContentType(path).isEmpty() && Files.probeContentType(path).equals("text/plain")) {
						e.acceptTransferModes(TransferMode.COPY);
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				e.consume();
			}
		});
		textInput.setOnDragDropped(e -> {
			Dragboard db = e.getDragboard();
			boolean success = false;
			if (db.hasFiles()) {
				success = true;

				for (File f : db.getFiles()) {
					file = f;
				}
			}
			putString();

			e.setDropCompleted(success);
			e.consume();
		});
	}

	public void detectInst() {
		/*
		 * gets called directly from the ui to detect the type of tablatures
		 */
		if (autoDetect.isSelected()) {
			detect(textInput.getText());
		}
	}

	public void checkForEmpty() {
		/*
		 * check if the text area is empty if the button has set to save, this option
		 * will change the button to translate disable the button for translate
		 */

		if (textInput.getText().isEmpty() && translateButton.getText().equals("Save")) {
			translateButton.setText("Translate");
		}
		if (textInput.getText().isEmpty() && translateButton.getText().equals("Translate")) {
			translateButton.setDisable(true);
		} else {
			translateButton.setDisable(false);
		}
		if (autoDetect.isSelected()) {
			detect(textInput.getText());
		}

	}

	public void translate() {

		// beatsChoice.setItems(beatOptions);
		// beatsChoice.setItems(beatOptions);
		// beatsChoice.setValue("Beats");

		if (!textInput.getText().isEmpty() && translateButton.getText().equals("Translate")) {
			// translated text goes here
			// textInput.setText(parser.checkTabType(textInput.getText())); //for Amer
			// textInput.setText(parser.stringParse(textInput.getText()));
			translateButton.setText("Save");
			// textInput.setText(stringParse(textInput.getText()));
			TRANSLATE = translateButton;
			INPUT = textInput;
			DELETEBUTTON = deleteButton;
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("OptionBox.fxml"));
				final Stage popup = new Stage();
				popup.initModality(Modality.APPLICATION_MODAL);
				popup.setTitle("Tranlation Options");
				popup.setScene(new Scene(root, 322, 414));

				popup.show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (translateButton.getText().equals("Save")) {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save");
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("musicXML files (*.musicxml)",
						"*.musicxml");
				fileChooser.getExtensionFilters().add(extFilter);
				File savefile = fileChooser.showSaveDialog(translateButton.getScene().getWindow());
				if (savefile != null) {
					FileWriter myWriter = new FileWriter(savefile);
					myWriter.write(textInput.getText());
					myWriter.close();
				}

				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}

	public void detect(String input) {
		String lines[] = input.split("\\r?\\n");

		// for error testing
//		for (int i = 0; i < lines.length; i++) {
//			System.out.println(lines[i]);
//		}
//		System.out.println(lines.length);

		// basic checks
		if (lines[0].toUpperCase().startsWith("E")) {
			guitarButtonClicked();
			/*
			 * Guitar
			 */

		} else if (lines[0].toUpperCase().startsWith("C")) {
			drumButtonClicked();
			// Drum
		} else if (lines[0].toUpperCase().startsWith("G")) {
			bassButtonClicked();
			// Bass
		}
	}

	private String XMLGenerate() { // Pass parsing to here
		// TODO pass in the MEASURE list to XmlGenerator
		ArrayList<Measure> myList = new ArrayList<Measure>();

		Measure.timeBeats = beatType; // Numerator
		Measure.timeBeatType = 4; // Denominator

		myList = StringParserUtility.stringParse(INPUT.getText());

		String xmlString = XmlGenerator.Generate(myList);
		// System.out.println(xmlString);
		return xmlString;
	}

	public void confirmTranslate() { // Beat type box?
		previousText = INPUT.getText();
		closePopup();
		INPUT.setText(XMLGenerate());
		TRANSLATE.setText("Save");
		DELETEBUTTON.setDisable(false);

	}

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
		beatsChoice.setText(beatType + "/4");
	}

	public void closePopup() {

		optionCancel.getScene().getWindow().hide();
		;
	}

	public void resetSelect() {
		selectAll = false;

	}

	public void seeInput() {
		checkForEmpty();
	}

	public void resetTranslation() {

		if (selectAll) {
			translateButton.setText("Translate");
			selectAll = false;
			return;

		}

		if (translateButton.getText().equals("Save")) {
			if (textInput.getSelectedText().equals(textInput.getText())) {
				selectAll = true;
			}
		} else {
			selectAll = false;
		}

	}

	public void delete() {
		//show an alert
		INPUT = textInput;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Alert.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Confirm Clear");
			popup.setScene(new Scene(root, 322, 140));

			popup.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Creating a dialog
//		textInput.clear();
//		if (file != null)
//			file = null;
//		translateButton.setText("Translate");

	}

	public void clearTooltipHover() {
		hoverButtonChange();
		Tooltip fileTip = new Tooltip("Clear");
		deleteButton1.setTooltip(fileTip);

		fileTip.setShowDelay(new Duration(0));
	}
	
	/*
	 * -------------methods control the alert--------------
	 */
	public void alertCancel() {
		clearCancel.getScene().getWindow().hide();
	}
	public void alertConfirm() {
		INPUT.clear();
		clearCancel.getScene().getWindow().hide();
	}
	/*
	 * -----------------methods control the reset------------
	 */
	public void resetCancel() {
		resetCancel.getScene().getWindow().hide();
	}
	public void resetConfirm() {
		INPUT.setText(previousText);
		resetCancel.getScene().getWindow().hide();
	}
}
