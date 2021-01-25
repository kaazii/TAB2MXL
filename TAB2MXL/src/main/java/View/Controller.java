package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
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
		file = fileChooser.showOpenDialog(fileButton.getScene().getWindow());
		System.out.println(file);
		putString();
		
		
	}
	private void putString() {
		try {
			Scanner fileIn = new Scanner(file);
			while(fileIn.hasNextLine()) {
				textInput.appendText(fileIn.nextLine()+"\n");
			}
			fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void clear() {
		textInput.clear();
		if(file != null) file = null;
	}
	
	public void dragDropFile() {
		textInput.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();
			if(db.hasFiles()) {
				e.acceptTransferModes(TransferMode.COPY);
				
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
}
