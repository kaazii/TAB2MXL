package View;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import TAB2MXL.BassNoteUtility;
import TAB2MXL.NoteUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class TuningController {
	public static NoteUtility NU = new NoteUtility();
	public static BassNoteUtility BNU = new BassNoteUtility();

	@FXML
	MenuItem E1, F1, FS1, G1, GS1, A1, AS1, B1, C1, CS1, D1, DS1;
	@FXML
	MenuItem E2, F2, FS2, G2, GS2, A2, AS2, B2, C2, CS2, D2, DS2;
	@FXML
	MenuItem E3, F3, FS3, G3, GS3, A3, AS3, B3, C3, CS3, D3, DS3;
	@FXML
	MenuItem E4, F4, FS4, G4, GS4, A4, AS4, B4, C4, CS4, D4, DS4;
	@FXML
	MenuItem E5, F5, FS5, G5, GS5, A5, AS5, B5, C5, CS5, D5, DS5;
	@FXML
	MenuItem E6, F6, FS6, G6, GS6, A6, AS6, B6, C6, CS6, D6, DS6;

	@FXML
	MenuButton fret1, fret2, fret3, fret4, fret5, fret6;

	@FXML
	TextField octave1, octave2, octave3, octave4, octave5, octave6;
	
	@FXML
	Button cancelButton, confirmButton;

	public void initialize() {
		if (Controller.selected == Controller.Type.BASS) {
			fret5.setDisable(true);
			fret6.setDisable(true);
			octave5.setDisable(true);
			octave6.setDisable(true);
		}

//		MenuItem E1, F1, FS1, G1, GS1, A1, AS1, B1, C1, CS1, D1, DS1;
		E1.setOnAction(e -> {
			setCounter1(0);
			setFret1("E");
		});
		F1.setOnAction(e -> {
			setCounter1(1);
			setFret1("F");
		});
		FS1.setOnAction(e -> {
			setCounter1(2);
			setFret1("F#");
		});
		G1.setOnAction(e -> {
			setCounter1(3);
			setFret1("G");
		});
		GS1.setOnAction(e -> {
			setCounter1(4);
			setFret1("G#");
		});
		A1.setOnAction(e -> {
			setCounter1(5);
			setFret1("A");
		});
		AS1.setOnAction(e -> {
			setCounter1(6);
			setFret1("A#");
		});
		B1.setOnAction(e -> {
			setCounter1(7);
			setFret1("B");
		});
		C1.setOnAction(e -> {
			setCounter1(8);
			setFret1("C");
		});
		CS1.setOnAction(e -> {
			setCounter1(9);
			setFret1("C#");
		});
		D1.setOnAction(e -> {
			setCounter1(10);
			setFret1("D");
		});
		DS1.setOnAction(e -> {
			setCounter1(11);
			setFret1("D#");
		});

		// set fret for 2
		E2.setOnAction(e -> {
			setCounter2(0);
			setFret2("E");
		});
		F2.setOnAction(e -> {
			setCounter2(1);
			setFret2("F");
		});
		FS2.setOnAction(e -> {
			setCounter2(2);
			setFret2("F#");
		});
		G2.setOnAction(e -> {
			setCounter2(3);
			setFret2("G");
		});
		GS2.setOnAction(e -> {
			setCounter2(4);
			setFret2("G#");
		});
		A2.setOnAction(e -> {
			setCounter2(5);
			setFret2("A");
		});
		AS2.setOnAction(e -> {
			setCounter2(6);
			setFret2("A#");
		});
		B2.setOnAction(e -> {
			setCounter2(7);
			setFret2("B");
		});
		C2.setOnAction(e -> {
			setCounter2(8);
			setFret2("C");
		});
		CS2.setOnAction(e -> {
			setCounter2(9);
			setFret2("C#");
		});
		D2.setOnAction(e -> {
			setCounter2(10);
			setFret2("D");
		});
		DS2.setOnAction(e -> {
			setCounter2(11);
			setFret2("D#");
		});

		// set fret for 3
		E3.setOnAction(e -> {
			setCounter3(0);
			setFret3("E");
		});
		F3.setOnAction(e -> {
			setCounter3(1);
			setFret3("F");
		});
		FS3.setOnAction(e -> {
			setCounter3(2);
			setFret3("F#");
		});
		G3.setOnAction(e -> {
			setCounter3(3);
			setFret3("G");
		});
		GS3.setOnAction(e -> {
			setCounter3(4);
			setFret3("G#");
		});
		A3.setOnAction(e -> {
			setCounter3(5);
			setFret3("A");
		});
		AS3.setOnAction(e -> {
			setCounter3(6);
			setFret3("A#");
		});
		B3.setOnAction(e -> {
			setCounter3(7);
			setFret3("B");
		});
		C3.setOnAction(e -> {
			setCounter3(8);
			setFret3("C");
		});
		CS3.setOnAction(e -> {
			setCounter3(9);
			setFret3("C#");
		});
		D3.setOnAction(e -> {
			setCounter3(10);
			setFret3("D");
		});
		DS3.setOnAction(e -> {
			setCounter3(11);
			setFret3("D#");
		});

		// set fret for 4
		E4.setOnAction(e -> {
			setCounter4(0);
			setFret4("E");
		});
		F4.setOnAction(e -> {
			setCounter4(1);
			setFret4("F");
		});
		FS4.setOnAction(e -> {
			setCounter4(2);
			setFret4("F#");
		});
		G4.setOnAction(e -> {
			setCounter4(3);
			setFret4("G");
		});
		GS4.setOnAction(e -> {
			setCounter4(4);
			setFret4("G#");
		});
		A4.setOnAction(e -> {
			setCounter4(5);
			setFret4("A");
		});
		AS4.setOnAction(e -> {
			setCounter4(6);
			setFret4("A#");
		});
		B4.setOnAction(e -> {
			setCounter4(7);
			setFret4("B");
		});
		C4.setOnAction(e -> {
			setCounter4(8);
			setFret4("C");
		});
		CS4.setOnAction(e -> {
			setCounter4(9);
			setFret4("C#");
		});
		D4.setOnAction(e -> {
			setCounter4(10);
			setFret4("D");
		});
		DS4.setOnAction(e -> {
			setCounter4(11);
			setFret4("D#");
		});

		// set fret for 5
		E5.setOnAction(e -> {
			setCounter5(0);
			setFret5("E");
		});
		F5.setOnAction(e -> {
			setCounter5(1);
			setFret5("F");
		});
		FS5.setOnAction(e -> {
			setCounter5(2);
			setFret5("F#");
		});
		G5.setOnAction(e -> {
			setCounter5(3);
			setFret5("G");
		});
		GS5.setOnAction(e -> {
			setCounter5(4);
			setFret5("G#");
		});
		A5.setOnAction(e -> {
			setCounter5(5);
			setFret5("A");
		});
		AS5.setOnAction(e -> {
			setCounter5(6);
			setFret5("A#");
		});
		B5.setOnAction(e -> {
			setCounter5(7);
			setFret5("B");
		});
		C5.setOnAction(e -> {
			setCounter5(8);
			setFret5("C");
		});
		CS5.setOnAction(e -> {
			setCounter5(9);
			setFret5("C#");
		});
		D5.setOnAction(e -> {
			setCounter5(10);
			setFret5("D");
		});
		DS5.setOnAction(e -> {
			setCounter5(11);
			setFret5("D#");
		});

		// set fret for 6
		E6.setOnAction(e -> {
			setCounter6(0);
			setFret6("E");
		});
		F6.setOnAction(e -> {
			setCounter6(1);
			setFret6("F");
		});
		FS6.setOnAction(e -> {
			setCounter6(2);
			setFret6("F#");
		});
		G6.setOnAction(e -> {
			setCounter6(3);
			setFret6("G");
		});
		GS6.setOnAction(e -> {
			setCounter6(4);
			setFret6("G#");
		});
		A6.setOnAction(e -> {
			setCounter6(5);
			setFret6("A");
		});
		AS6.setOnAction(e -> {
			setCounter6(6);
			setFret6("A#");
		});
		B6.setOnAction(e -> {
			setCounter6(7);
			setFret6("B");
		});
		C6.setOnAction(e -> {
			setCounter6(8);
			setFret6("C");
		});
		CS6.setOnAction(e -> {
			setCounter6(9);
			setFret6("C#");
		});
		D6.setOnAction(e -> {
			setCounter6(10);
			setFret6("D");
		});
		DS6.setOnAction(e -> {
			setCounter6(11);
			setFret6("D#");
		});
		
		octave1.setTextFormatter(getFormatter());
		octave2.setTextFormatter(getFormatter());
		octave3.setTextFormatter(getFormatter());
		octave4.setTextFormatter(getFormatter());
		octave5.setTextFormatter(getFormatter());
		octave6.setTextFormatter(getFormatter());

	}
	
	//confirm button confirms the modification and confirm translation
	public void confirm() {
		if(Controller.selected == Controller.Type.BASS ) {
			if(!octave1.getText().isEmpty()) {
				BNU.setOctave1(Integer.parseInt(octave1.getText()));
			}
			if(!octave2.getText().isEmpty()) {
				BNU.setOctave2(Integer.parseInt(octave2.getText()));
			}
			if(!octave3.getText().isEmpty()) {
				BNU.setOctave3(Integer.parseInt(octave3.getText()));
			}
			if(!octave4.getText().isEmpty()) {
				BNU.setOctave4(Integer.parseInt(octave4.getText()));
			}
		}
		else {
			if(!octave1.getText().isEmpty()) {
				NU.setOctave1(Integer.parseInt(octave1.getText()));
			}
			if(!octave2.getText().isEmpty()) {
				NU.setOctave2(Integer.parseInt(octave2.getText()));
			}
			if(!octave3.getText().isEmpty()) {
				NU.setOctave3(Integer.parseInt(octave3.getText()));
			}
			if(!octave4.getText().isEmpty()) {
				NU.setOctave4(Integer.parseInt(octave4.getText()));
			}
			if(!octave5.getText().isEmpty()) {
				NU.setOctave5(Integer.parseInt(octave5.getText()));
			}
			if(!octave6.getText().isEmpty()) {
				NU.setOctave6(Integer.parseInt(octave6.getText()));
			}
		}
		Controller con = new Controller();
		Controller.state = 1;
		Controller.previousText = Controller.INPUT.getText();
		confirmButton.getScene().getWindow().hide();
		// TRANSLATE.setText("Save");
		try {
			Controller.INPUT.setText(con.XMLGenerate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			con.error(e);
			e.printStackTrace();
			return;
		}
		Controller.DELETEBUTTON.setDisable(false);
		Controller.TRANSLATE.setDisable(true);
	}
	
	public void cancel() {
		cancelButton.getScene().getWindow().hide();
	}

	private void setCounter1(int index) {
		if (Controller.selected == Controller.Type.BASS) {
			BNU.setCounter1(index);
		} else {
			NU.setCounter1(index);
		}
	}

	private void setCounter2(int index) {
		if (Controller.selected == Controller.Type.BASS) {
			BNU.setCounter2(index);
		} else {
			NU.setCounter2(index);
		}
	}

	private void setCounter3(int index) {
		if (Controller.selected == Controller.Type.BASS) {
			BNU.setCounter3(index);
		} else {
			NU.setCounter3(index);
		}
	}

	private void setCounter4(int index) {
		if (Controller.selected == Controller.Type.BASS) {
			BNU.setCounter4(index);
		} else {
			NU.setCounter4(index);
		}
	}

	private void setCounter5(int index) {
		NU.setCounter5(index);
	}

	private void setCounter6(int index) {
		NU.setCounter6(index);
	}

	private void setFret1(String fret) {
		fret1.setText(fret);
	}

	private void setFret2(String fret) {
		fret2.setText(fret);
	}

	private void setFret3(String fret) {
		fret3.setText(fret);
	}

	private void setFret4(String fret) {
		fret4.setText(fret);
	}

	private void setFret5(String fret) {
		fret5.setText(fret);
	}

	private void setFret6(String fret) {
		fret6.setText(fret);
	}
	
	private TextFormatter<String> getFormatter() {
		DecimalFormat format = new DecimalFormat( "0" );
		return new TextFormatter<String>(c ->{
		    if ( c.getControlNewText().isEmpty() ){
		        return c;
		    }
		   
		    ParsePosition parsePosition = new ParsePosition( 0 );
		    Object object = format.parse( c.getControlNewText(), parsePosition );

		    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() || c.getControlNewText().length()> 1 ){
		        return null;
		    }
		    else{
		        return c;
		    }
		});
	}
}
