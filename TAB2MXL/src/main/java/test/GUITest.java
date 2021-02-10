package test;

import static org.junit.jupiter.api.Assertions.*;
import View.Controller;
import javafx.scene.control.TextArea;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GUITest {
	
	private Controller con;

	@BeforeEach
	void setUp() throws Exception {
		con = new Controller();
		con.textInput = new TextArea();
	}
	

	@Test
	void test() {
		con.checkForEmpty();
		assertEquals(con.translateButton.isDisable(), true);
	}

}
