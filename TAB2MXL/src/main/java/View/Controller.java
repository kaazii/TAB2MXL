package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import TAB2MXL.Measure;
import TAB2MXL.XmlGenerator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

public class Controller {
	/*
	 * ----------------------------------------------- variable for two screen
	 * communication
	 */
	private static TextArea INPUT;
	private static Button TRANSLATE;
	private static Button DELETEBUTTON;
	// private static int CONFIRM;
	// ------------------------------------------------
	@FXML
	Button saveButton;
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
	public static Type selected = Type.GUITAR;
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
	// --------------- Save variable ---------------
	@FXML
	Button musicXML;
	@FXML
	Button textFile;
	@FXML
	Button confirmSave;
	@FXML
	Button cancelSave;
	Save saveType;
	// ------------Magic Variables-------------//
	boolean selectAll;
	public static int state = 0;// 0 untraslated, 1 translated
	// ----------------------------------------//

	// ---------------Time Signature List --------//
	@FXML
	VBox timeList;
	public static Map<Integer, Pair<Integer, Integer>> beatList = new HashMap<>();
	@FXML
	Button plus;
	List<TimeHBOX> hboxList = new ArrayList<>();
	@FXML
	TextField numeText;
	@FXML
	TextField denoText;
	// ------------------------------------------//

	public static int nume = 4;
	public static int deno = 4;
	public static String previousText;

	// ---------------invalid model--------------//
	@FXML
	Label warning;
	@FXML
	Button modalConfirm;

	// ---------------composer and title---------//
	public static String COMPOSER = "";
	public static String TITLE = "";
	@FXML
	TextField composerField;

	@FXML
	TextField titleField;

	// -----------------validation Display--------
	@FXML
	TextFlow displayText;

	// -------------------instrument popup---------
	@FXML
	Button instrumentConfirm;
	public static CheckBox AUTO;

//	public void intialize() {
//		translateButton.disableProperty().bind(textInput.textProperty().isEmpty());
//		saveButton.disableProperty().bind(textInput.textProperty().isEmpty());
//	}

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
		state = 0;

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
//				if (translateButton.getText().equals("Save")) {
//					translateButton.setText("Translate");
//				}
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
		// previousText = textInput.getText();
		INPUT = textInput;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Reset.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Confirm Restart");
			popup.setScene(new Scene(root, 322, 140));
			popup.setOnHidden(e -> {
				popup.close();
			});
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
			detect(cleanup(textInput.getText()));
		}
	}

	public void checkForEmpty() {
		/*
		 * check if the text area is empty if the button has set to save, this option
		 * will change the button to translate disable the button for translate
		 */

		if (textInput.getText().isEmpty()) {
			translateButton.setDisable(true);
			saveButton.setDisable(true);
		} else if (state == 1) {
			translateButton.setDisable(true);
		} else {
			translateButton.setDisable(false);
			saveButton.setDisable(false);
		}

//		if (autoDetect.isSelected()) {
//			detect(textInput.getText());
//		}

	}

	public void translate() {

		// beatsChoice.setItems(beatOptions);
		// beatsChoice.setItems(beatOptions);
		// beatsChoice.setValue("Beats");

		// translated text goes here
		// textInput.setText(parser.checkTabType(textInput.getText())); //for Amer
		// textInput.setText(parser.stringParse(textInput.getText()));
		// translateButton.setText("Save");
		// textInput.setText(stringParse(textInput.getText()));

		TRANSLATE = translateButton;
		INPUT = textInput;
		DELETEBUTTON = deleteButton;
		AUTO = autoDetect;
		clearMeasureList();
		if (!cleanup(INPUT.getText()).replace("\n", "").replace("\r", "")
				.equals(INPUT.getText().replace("\n", "").replace("\r", ""))) {
			checker();
			System.out.println(cleanup(INPUT.getText()));
		} else if (isInvalid()) {
			showInvalid();
		} else if (AUTO.isSelected() && !detect(cleanup(INPUT.getText())))
			return;
		else {

			openOption();

		}

	}

	private void openOption() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("OptionBox.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Tranlation Options");
			popup.setScene(new Scene(root, 360, 356));
			popup.setOnHidden(e -> {
				popup.close();
			});
			popup.setResizable(false);
			popup.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean detect(String input) {
		// return true if detected

		String[] splitInput = input.split("\\r?\\n\\r?\\n");

		String lines[] = splitInput[0].split("\\r?\\n");

		// for error testing
//		for (int i = 0; i < lines.length; i++) {
//			System.out.println(lines[i]);
//		}
//		System.out.println(lines.length);

		// basic checks
		if ((lines[0].toUpperCase().startsWith("E") || (lines.length == 6)) && !lines[0].contains("o")
				&& !lines[0].contains("x")) {
			// System.out.println("This is a guitar"); // testing
			guitarButtonClicked();
			/*
			 * Guitar
			 */

		} else if (lines[0].toUpperCase().startsWith("G") && (lines.length == 5 || lines.length == 4)
				&& !lines[0].contains("o") && !lines[0].contains("x")) {
			// System.out.println("This is a bass"); // testing
			bassButtonClicked();
			// Bass
		} else if (lines[0].contains("x") || lines[0].contains("o")) {
			// System.out.println("This is a drum"); // testing
			drumButtonClicked();
			// Drum
		} else {
			showNotDetected();
			return false;
		}
		return true;
	}

	private String XMLGenerate() throws Exception { // Pass parsing to here
		// TODO pass in the MEASURE list to XmlGenerator
		ArrayList<Measure> myList = new ArrayList<Measure>();
		String xmlString = "";
		String instrumentName = "DRUMS";
//		Measure.timeBeats = beatType; // Numerator
//		Measure.timeBeatType = 4; // Denominator
//		Measure.beatList = beatList;
		if (selected == Type.GUITAR) {
			System.out.println("Guitar");
			instrumentName = "GUITAR";
			try {

				myList = StringParserUtility.stringParse(cleanup(INPUT.getText()));
			} catch (Exception e) {
				// TODO error handle here
				error(e);
			}
		} else if (selected == Type.BASS) {
			System.out.println("Bass");
			instrumentName = "BASS";
			try {
				myList = StringParserUtilityBass.stringParse(cleanup(INPUT.getText()));
			} catch (Exception e) {
				// TODO error handle here
				error(e);
			}
		} else if (selected == Type.DRUM) {
			System.out.println("Drums");
			myList = StringParserUtilityDrum.stringParse(cleanup(INPUT.getText()));
		} else {
			showNotDetected();
			TRANSLATE.setDisable(false);
			return previousText;
		}

		try {
			xmlString = XmlGenerator.Generate(myList, instrumentName);
		} catch (Exception e) {
			error(e);
		}

		// System.out.println(xmlString);
		return xmlString;
	}

	private void clearMeasureList() {
		// TODO Auto-generated method stub
		StringParserUtility.clearMeasureList();

		StringParserUtilityBass.clearMeasureList();

		StringParserUtilityDrum.clearMeasureList();

	}

	public void confirmTranslate() throws Exception { // Beat type box?
		if (isInvalid()) {

			System.out.println("reached\n");
			showInvalid();

			optionConfirm.getScene().getWindow().hide();

			return;
		}

		for (TimeHBOX hbox : hboxList) {
			Pair<Integer, Integer> range = hbox.getRange();
			for (int i = range.getKey(); i <= range.getValue(); i++) {
				beatList.put(i, hbox.getTimeSignature());
			}
		}
		// if the text fields are not empty, set nume and deno
		// else use the default 4/4
		if (!numeText.getText().isEmpty())
			nume = Integer.parseInt(numeText.getText());
		else
			nume = 4;
		if (!denoText.getText().isEmpty())
			deno = Integer.parseInt(denoText.getText());
		else
			deno = 4;

		state = 1;
		previousText = INPUT.getText();
		closePopup();
		// TRANSLATE.setText("Save");
		DELETEBUTTON.setDisable(false);
		TRANSLATE.setDisable(true);
		// set the list
		COMPOSER = composerField.getText();
		TITLE = titleField.getText();
		INPUT.setText(XMLGenerate());

	}

	public void chosen1() {
		nume = 1;
		beat2.setSelected(false);
		beat3.setSelected(false);
		beat4.setSelected(false);
		setText();
	}

	public void chosen2() {
		nume = 2;
		setText();
		beat1.setSelected(false);
		beat3.setSelected(false);
		beat4.setSelected(false);

	}

	public void chosen3() {
		nume = 3;
		setText();
		beat1.setSelected(false);
		beat2.setSelected(false);
		beat4.setSelected(false);

	}

	public void chosen4() {
		beat2.setSelected(false);
		beat3.setSelected(false);
		beat1.setSelected(false);
		nume = 4;
		setText();

	}

	private void setText() {
		beatsChoice.setText(nume + "/4");
	}

	public void closePopup() {

		optionCancel.getScene().getWindow().hide();

	}

	public void resetSelect() {
		selectAll = false;

	}

	public void seeInput() {
		checkForEmpty();
	}

	public void resetTranslation() {

		if (selectAll) {
			state = 0;
			deleteButton1.setDisable(true);
			selectAll = false;
			return;

		}

		if (state == 1) {
			if (textInput.getSelectedText().equals(textInput.getText())) {
				selectAll = true;
			}
		} else {
			selectAll = false;
		}

	}

	public void delete() {
		// show an alert
		INPUT = textInput;
		TRANSLATE = translateButton;
		DELETEBUTTON = deleteButton;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Alert.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Confirm Clear");
			popup.setScene(new Scene(root, 322, 140));
			popup.setOnHidden(e -> {
				popup.close();
			});
			popup.setResizable(false);
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

	public void save() {
		INPUT = textInput;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("SaveOption.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.setTitle("Save Options");
			popup.setScene(new Scene(root, 322, 280));
			popup.setOnHidden(e -> {
				popup.close();
			});
			popup.setResizable(false);
			popup.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * -------------methods control the alert--------------
	 */
	public void alertCancel() {
		clearCancel.getScene().getWindow().hide();
	}

	public void alertConfirm() {
		INPUT.clear();

		TRANSLATE.setDisable(false);
		state = 0;
		DELETEBUTTON.setDisable(true);
		clearCancel.getScene().getWindow().hide();
	}

	/*
	 * -----------------methods control the reset------------
	 */
	public void resetCancel() {
		resetCancel.getScene().getWindow().hide();
	}

	public void resetConfirm() {
		TRANSLATE.setDisable(false);
		INPUT.setText(previousText);
		state = 0;
		DELETEBUTTON.setDisable(true);
		resetCancel.getScene().getWindow().hide();

	}

	/**
	 * ----------------Methods control the save options ------------------
	 */

	private void saveFile() {
		if (saveType == Save.FILE) {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save");
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
				fileChooser.getExtensionFilters().add(extFilter);
				File savefile = fileChooser.showSaveDialog(confirmSave.getScene().getWindow());
				if (savefile != null) {
					if (savefile.length() != 0) {
						PrintWriter pw = new PrintWriter(savefile);
						pw.close();
					}
					FileWriter myWriter = new FileWriter(savefile);
					myWriter.write(INPUT.getText());
					myWriter.close();
				}

				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		} else {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save");
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("musicXML files (*.musicxml)",
						"*.musicxml");
				fileChooser.getExtensionFilters().add(extFilter);
				File savefile = fileChooser.showSaveDialog(confirmSave.getScene().getWindow());
				if (savefile != null) {
					FileWriter myWriter = new FileWriter(savefile);
					myWriter.write(INPUT.getText());
					myWriter.close();
				}

				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}

	public void saveCancel() {
		cancelSave.getScene().getWindow().hide();
	}

	public void saveConfirm() {
		saveFile();
		confirmSave.getScene().getWindow().hide();
	}

	public void textSelected() {
		saveType = Save.FILE;
		textFile.setStyle("-fx-background-color: #bbbbbb;\r\n" + "-fx-text-fill:  white;\r\n"
				+ "-fx-border-radius: 10;\r\n" + "-fx-border-color:  #bbbbbb;");
		musicXML.setStyle("-fx-background-color: transparent;\r\n" + "-fx-font-fill: black;\r\n"
				+ "-fx-border-radius: 10;\r\n" + "-fx-border-color:  #F0F4F0");
	}

	public void musicSelected() {
		saveType = Save.MUSICXML;
		musicXML.setStyle("-fx-background-color: #bbbbbb;\r\n" + "-fx-text-fill:  white;\r\n"
				+ "-fx-border-radius: 10;\r\n" + "-fx-border-color:  #bbbbbb;");
		textFile.setStyle("-fx-background-color: transparent;\r\n" + "-fx-font-fill: black;\r\n"
				+ "-fx-border-radius: 10;\r\n" + "-fx-border-color:  #F0F4F0;");
	}

	/*
	 * ----------------enum-------------------
	 */
	enum Save {
		FILE, MUSICXML;
	}

	// -------------time list-------------------
	public void add() {

		TimeHBOX hbox = new TimeHBOX(timeList, hboxList);
		timeList.getChildren().add(hbox.get());
		timeList.getScene().getWindow().setHeight(timeList.getScene().getWindow().getHeight() + 32);
	}

	public void hoverChange() {

		plus.getScene().setCursor(Cursor.HAND);
		Tooltip timeTip = new Tooltip("Add a signature");
		plus.setTooltip(timeTip);

		timeTip.setShowDelay(new Duration(0));
	}

	public void hoverBack() {
		plus.getScene().setCursor(Cursor.DEFAULT);
	}

	// -----------invalid-------------------------
	public void showInvalid() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("invalid.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.WINDOW_MODAL);
			popup.setTitle("Invalid Input");
			popup.setScene(new Scene(root, 322, 120));
			popup.setOnHidden(e -> {
				popup.close();
			});
			popup.setResizable(false);
			popup.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void hoverModalChange() {

		cursorToHand(modalConfirm, null);

	}

	public void hoverModalBack() {
		cursorToDefault(modalConfirm);
	}

	public void closeWarning() {
		warning.getScene().getWindow().hide();

	}

	// Checks for illegal characters in the input

	public boolean isInvalid() {
//		// final String NEW_LINE = System.getProperty("line.separator");
//		boolean illegalChar = false;
//		// store the text tab
//
//		String tempInput = cleanup(INPUT.getText());
//		// string containing all possible characters for the text tab for all 3
//		// instruments
//
//		String validChars = "0123456789-|EADGBECHSTMxgmaoshp[]*XREPEAT";
//
//		// must be same length
//		String[] splitInput = tempInput.split("\\r?\\n\\r?\\n");
//
//		if (splitInput.length == 0)
//			return true;
//		for (int i = 0; i < splitInput.length; i++) {
//			String[] splitLines = splitInput[i].split("\\r?\\n");
//			if (splitLines.length == 0)
//				return true;
//			int length = splitLines[0].length();
//			for (int j = 1; j < splitLines.length; j++) {
//				if (splitLines[j].length() != length)
//					return true;
//			}
//
//		}
//		// remove new line from the string(the contains method wasn't working properly
//		// otherwise)
//
//		tempInput = tempInput.replace("\n", "").replace("\r", "");
//		// compare each character in the tempInput string with validChars
//		for (int i = 0; i < tempInput.length(); i++) {
//			if (!(validChars.contains(Character.toString(tempInput.charAt(i))))) {
//				System.out.println(tempInput.charAt(i) + " did not match");
//				// set to true if illegal character found
//				illegalChar = true;
//			}
//		}
//
//		return illegalChar;
//		// if(INPUT.getText().startsWith("s")) return true;
//		// return false;
		return false;
	}

	// ------------hover helper---------------
	private void cursorToHand(Button node, String tooltip) {
		node.getScene().setCursor(Cursor.HAND);
		if (tooltip != null) {
			Tooltip tip = new Tooltip(tooltip);
			node.setTooltip(tip);
			tip.setShowDelay(new Duration(0));
		}

	}

	private void cursorToDefault(Button node) {
		node.getScene().setCursor(Cursor.DEFAULT);

	}

	// --------------Clear measureList------------

	// --------------error catch---------------
	private void error(Exception e) {
		e.printStackTrace();
		showInvalid();
	}

	// -----input clean up-----------------
	public static String cleanup(String input) {
		StringBuilder sb = new StringBuilder();

		String[] lines = input.split("\\r?\\n");
		boolean consecutive = false; // if there is consecutive lines to be ignored
		for (int i = 0; i < lines.length; i++) {
			// System.out.println(lines[i]);
			if (!lines[i].contains("-") || !lines[i].contains("|")) {
				if (consecutive)
					continue;
				else {
					consecutive = true;
					if (!sb.isEmpty())
						sb.append("\n");
				}

			} else {
				sb.append(lines[i]);
				sb.append("\n");
				consecutive = false;
			}
		}

		return sb.toString();
	}

	// ----------------------ignore text--------------

	public void checker() {
		Parent root;
		try {
			INPUT = textInput;
			root = FXMLLoader.load(getClass().getResource("display.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.WINDOW_MODAL);
			popup.setTitle("Input Ignored");
			popup.setScene(new Scene(root, 600, 500));
			popup.setOnHidden(e -> {
				popup.close();
			});
			popup.setResizable(false);
			popup.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void displayValid() {
		displayText.getChildren().clear();
		displayText.setVisible(true);
		String[] lines = INPUT.getText().split("\\r?\\n");
		for (int i = 0; i < lines.length; i++) {
			Text t = new Text();
			t.setText(lines[i] + "\n");
			t.setFont(Font.font("Monospaced", FontWeight.NORMAL, 12));
			// System.out.println(lines[i]);
			if ((!lines[i].contains("-") || !lines[i].contains("|")) && !lines[i].equals("\\r?\\n")) {

				t.setStrikethrough(true);
				t.setFill(Color.RED);

			}
			displayText.getChildren().add(t);
		}
	}

	public void cancelIgnore() {
		displayText.setVisible(false);
		displayText.getScene().getWindow().hide();
	}

	public void confirmIgnore() {
		cancelIgnore();
		openOption();
	}

	// ----------------Text Field validation-----------------
	public void numberValidation() {
		DecimalFormat format = new DecimalFormat("0");
		numeText.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}

			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(c.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
				return null;
			} else {
				return c;
			}
		}));
		denoText.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}

			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(c.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
				return null;
			} else {
				return c;
			}
		}));
	}

	// Instrument not detected

	private void showNotDetected() {
		// TODO Auto-generated method stub
		// no longer activated
		AUTO.setSelected(false);
		Parent root;
		try {
			INPUT = textInput;
			root = FXMLLoader.load(getClass().getResource("instrument.fxml"));
			final Stage popup = new Stage();
			popup.initModality(Modality.WINDOW_MODAL);
			popup.setTitle("No Instrument Detected");
			popup.setScene(new Scene(root, 322, 120));
			popup.setOnHidden(e -> {
				popup.close();
			});
			popup.setResizable(false);
			popup.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeInstrument() {
		instrumentConfirm.getScene().getWindow().hide();
	}
}