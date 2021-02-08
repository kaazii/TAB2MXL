
package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Controller {
	@FXML
	Button guitarButton;
	@FXML
	Button bassButton;
	@FXML
	Button drumButton;
	@FXML
	Button translateButton;
	@FXML
	TextArea textInput;
	Type selected;
	@FXML
	Button fileButton;
	@FXML
	Button deleteButton;
	File file;
	String content;
	@FXML
	CheckBox autoDetect;

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
	}

	public void deleteTooltipHover() {
		hoverButtonChange();
		Tooltip fileTip = new Tooltip("Clear");
		deleteButton.setTooltip(fileTip);
	}

	public void uploadFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Tab File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		file = fileChooser.showOpenDialog(fileButton.getScene().getWindow());
		//System.out.println(file);
		putString();
		
		
	}
	private void putString() {
		try {
			if(file != null) {
				Scanner fileIn = new Scanner(file);
				if(!textInput.getText().isEmpty()) {
					textInput.clear();
				}
				while(fileIn.hasNextLine()) {
					textInput.appendText(fileIn.nextLine()+"\n");
				}
				fileIn.close();
				if(translateButton.isDisable()) {
					translateButton.setDisable(false);
				}
				if(autoDetect.isSelected()) {
					detectInst();
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void clear() {
		textInput.clear();
		if(file != null) file = null;
		translateButton.setText("Translate");
	}
	
	public void dragDropFile() {
		textInput.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();
			if(db.hasFiles() && db.getFiles().size() == 1 /*&& Files.probeContentType(db.getFiles().get(0).equals("))*/) {
				
				
				try {
					Path path = FileSystems.getDefault().getPath(db.getFiles().get(0).getPath());
					if(Files.probeContentType(path).equals("text/plain")) {
						e.acceptTransferModes(TransferMode.COPY);
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				e.consume();
			}
		});
		textInput.setOnDragDropped(e->{
			Dragboard db = e.getDragboard();
			boolean success = false;
			if(db.hasFiles()) {
				success = true;
				
				for(File f : db.getFiles()) {
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
		 * gets called directly from the ui to detect the type
		 * of tablatures
		 */
		if(autoDetect.isSelected()) {
			detect(textInput.getText());
		}
	}
	
	public void checkForEmpty() {
		/*
		 * check if the text area is empty
		 * if the button has set to save, this option will 
		 * change the button to translate
		 * disable the button for translate
		 */
		if(textInput.getText().isEmpty() && translateButton.getText().equals("Save")) {
			translateButton.setText("Translate");
		}
		if(textInput.getText().isEmpty() && translateButton.getText().equals("Translate")) {
			translateButton.setDisable(true);
		}
		else {
			translateButton.setDisable(false);
		}
		if(autoDetect.isSelected()) {
			detect(textInput.getText());
		}
		
		
	}
	

	public void translate() {
		if (!textInput.getText().isEmpty() && translateButton.getText().equals("Translate")) {
			//textInput.setText(stringParse(textInput.getText()));
			textInput.setText("Translation");
			translateButton.setText("Save");
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
		
		//for error testing
//		for (int i = 0; i < lines.length; i++) {
//			System.out.println(lines[i]);
//		}
//		System.out.println(lines.length);
		
		//basic checks
		if (lines[0].toUpperCase().startsWith("E")) {
			guitarButtonClicked();
			/*
			 * Guitar
			 */
			
		}
		else if (lines[0].toUpperCase().startsWith("C")) {
			drumButtonClicked();
			//Drum
		}
		else if (lines[0].toUpperCase().startsWith("G")) {
			bassButtonClicked();
			// Bass
		}
	}
}
