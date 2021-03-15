package View;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class ApplicationTest {

	@Start
	private void statr(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PrimaryStage.fxml"));
			primaryStage.setTitle("TAB2XML");
			primaryStage.setScene(new Scene(root, 700,600));
			primaryStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void should_contain_translate_button(FxRobot robot) {
		FxAssert.verifyThat("#translateButton", LabeledMatchers.hasText("Translate"));
	}

}
