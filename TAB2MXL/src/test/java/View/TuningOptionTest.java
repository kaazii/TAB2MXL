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
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.MenuItemMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class TuningOptionTest {

	@Start
	private void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Tuning.fxml"));
			primaryStage.setTitle("Tuning Options");
			primaryStage.setScene(new Scene(root,  360, 377));
			primaryStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	void has_confirm_button(FxRobot robot) {
		FxAssert.verifyThat("#confirmButton", NodeMatchers.isVisible());
	}
	
	@Test
	void has_cancel_button(FxRobot robot) {
		FxAssert.verifyThat("#cancelButton", NodeMatchers.isVisible());
	}
	
	

}
