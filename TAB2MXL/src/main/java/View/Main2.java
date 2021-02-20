
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
		
		
		Parent root = FXMLLoader.load(getClass().getResource("PrimaryStage.fxml"));
		primaryStage.setTitle("TAB2XML");
		primaryStage.setScene(new Scene(root, 700,600));
		primaryStage.show();
	}

}
