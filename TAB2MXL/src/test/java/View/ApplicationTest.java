package View;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.StyleableAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.StyleableMatchers;
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
	
	/*
	 * Test if translate button is present
	 */
	@Test
	void should_contain_translate_button(FxRobot robot) {
		FxAssert.verifyThat("#translateButton", LabeledMatchers.hasText("Translate"));
	}
	/*
	 * Test if save button is present
	 */
	@Test
	void should_contain_save_button(FxRobot robot) {
		FxAssert.verifyThat("#saveButton", LabeledMatchers.hasText("Save"));
	}
	/*
	 * Test if both buttons are disabled by default
	 */
	@Test
	void bothButtonDisabled(FxRobot robot) {
		FxAssert.verifyThat("#saveButton",NodeMatchers.isDisabled());
		FxAssert.verifyThat("#translateButton",NodeMatchers.isDisabled());
	}
	/*
	 * Test if both Button are enabled after typing
	 */
	@Test
	void bothButtonEnabled(FxRobot robot) {
		robot.clickOn("#textInput");
		robot.write("hi");
		FxAssert.verifyThat("#saveButton",NodeMatchers.isEnabled());
		FxAssert.verifyThat("#translateButton",NodeMatchers.isEnabled());
	}
	@Test
	void restartButtonDisabled(FxRobot robot) {
		FxAssert.verifyThat("#deleteButton",NodeMatchers.isDisabled());
	}
//	@Test
//	void deleteButton(FxRobot robot) {
//		robot.clickOn("#textInput");
//		robot.write("hi");
//		robot.clickOn("#deleteButton");
//		FxAssert.verifyThat("#saveButton",NodeMatchers.isDisabled());
//		FxAssert.verifyThat("#translateButton",NodeMatchers.isDisabled());
//	}
	
	@Test
	void fileButtonEnabled(FxRobot robot) {
		FxAssert.verifyThat("#fileButton", NodeMatchers.isEnabled());
	}
//	
//	@Test
//	void testInstrumentButton(FxRobot robot) {
//		robot.clickOn("#bassButton");
//		StyleableAssert.assertThat("#bassButton", StyleableMatchers.hasStyle("-fx-font-size: 20;\r\n" + "-fx-background-color: transparent;\r\n" + "-fx-text-fill: #828F9C;"));
//	}


}
