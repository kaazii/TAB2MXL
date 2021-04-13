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
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class TranslateOptionsTest {

	@Start
	private void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("OptionBox.fxml"));
			primaryStage.setTitle("Translation Options");
			primaryStage.setScene(new Scene(root,  360, 356));
			primaryStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Test

	void should_contain_confirm(FxRobot robot) {
		FxAssert.verifyThat("#optionConfirm", LabeledMatchers.hasText("Confirm"));
	}
	
	// Confirm option box has cancel button
	@Test

	void should_contain_cancel(FxRobot robot) {
		FxAssert.verifyThat("#optionCancel", LabeledMatchers.hasText("Cancel"));
	}
	
	
	//Test illegal input are rejected
	

}
