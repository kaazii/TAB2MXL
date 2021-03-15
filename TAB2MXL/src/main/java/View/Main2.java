/*
package View;

import java.util.ArrayList;

import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.XmlGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main2 extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO pass in the MEASURE list to XmlGenerator
		ArrayList<Measure> myList = new ArrayList<Measure>();
		
		// Create Measure
		Measure myMeasure = new Measure(5, 4, 4, 1);
		
		// Create some notes to add to the measure
		// testing 2 different ways to make a new note
		Note n = new Note();
		n.setStep("E");
		n.setOctave(2);
		n.setDuration(1);
		n.setVoice(1);
		n.setType("eighth");
		n.setString(6);
		n.setFret(0);
		
		Note n2 = new Note() {{
			step = "B";
			octave = 2;
			duration = 1;
			voice = 1;
			type = "eighth";
			string = 5;
			fret = 2;
		}};
		
		myMeasure.noteList.add(n);
		myMeasure.noteList.add(n2);
		
		myList.add(myMeasure);
		String xmlString = XmlGenerator.Generate(myList);
		System.out.println(xmlString);
		
		Parent root = FXMLLoader.load(getClass().getResource("PrimaryStage.fxml"));
		primaryStage.setTitle("TAB2XML");
		primaryStage.setScene(new Scene(root, 700,600));
		primaryStage.show();
	}

}
*/

package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main2 extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);
	}

	
	public void start(Stage primaryStage) throws Exception {
		
		//System.out.println(getClass().getResource("PrimaryStage.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("PrimaryStage.fxml"));
		
		primaryStage.setTitle("TAB2XML");
		primaryStage.setScene(new Scene(root, 700,600));
		primaryStage.show();
	}

}
