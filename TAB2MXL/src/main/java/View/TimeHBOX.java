package View;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.NumberFormatter;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

public class TimeHBOX{
	
	HBox hbox;
	public int nume = 4;
	public int deno = 4;
	public TextField range1;
	public TextField range2;
	public TextField numeText;
	public TextField denoText;
	public VBox parent;
	public  List<TimeHBOX> list;
	
	public TimeHBOX(VBox parent, List<TimeHBOX> list) {
		this.list = list;
		this.parent = parent;
		list.add(this);
		hbox = new HBox();
		hbox.setPrefSize(320, 32);
		hbox.setPadding(new Insets(0,15,0,15));
		range1 = new TextField();
		range2 = new TextField();
//		RadioMenuItem item1 = new RadioMenuItem("1/4");
//		
//		RadioMenuItem item2 = new RadioMenuItem("2/4");
//		RadioMenuItem item3 = new RadioMenuItem("3/4");
//		RadioMenuItem item4 = new RadioMenuItem("4/4");
//		MenuButton time = new MenuButton("4/4");
		numeText = new TextField();
		numeText.setPromptText("4");
		
		denoText = new TextField();
		denoText.setPromptText("4");
		
		//default
		numeText.setStyle("-fx-background-color: transparent;\r\n"
				+ "-fx-border-radius: 10;\r\n"
				+ "-fx-border-color:  #F0F4F0; \r\n"
				+ "-fx-border-width: 2");
		numeText.setPrefWidth(40);
		Label dash = new Label("/");
		dash.setPadding(new Insets(5,0,0,0));
		dash.setStyle("-fx-text-fill: #828f9c");
		denoText.setStyle("-fx-background-color: transparent;\r\n"
				+ "-fx-border-radius: 10;\r\n"
				+ "-fx-border-color:  #F0F4F0; \r\n"
				+ "-fx-border-width: 2");
		denoText.setPrefWidth(40);
		
		range1.setPrefWidth(40);
		DecimalFormat format = new DecimalFormat( "0" );
		range1.setTextFormatter( new TextFormatter<>(c ->{
		    if ( c.getControlNewText().isEmpty() ){
		        return c;
		    }
		   
		    ParsePosition parsePosition = new ParsePosition( 0 );
		    Object object = format.parse( c.getControlNewText(), parsePosition );

		    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ){
		        return null;
		    }
		    else{
		        return c;
		    }
		}));
		numeText.setTextFormatter( new TextFormatter<>(c ->{
		    if ( c.getControlNewText().isEmpty() ){
		        return c;
		    }
		   
		    ParsePosition parsePosition = new ParsePosition( 0 );
		    Object object = format.parse( c.getControlNewText(), parsePosition );

		    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ){
		        return null;
		    }
		    else{
		        return c;
		    }
		}));
		denoText.setTextFormatter( new TextFormatter<>(c ->{
		    if ( c.getControlNewText().isEmpty() ){
		        return c;
		    }
		   
		    ParsePosition parsePosition = new ParsePosition( 0 );
		    Object object = format.parse( c.getControlNewText(), parsePosition );

		    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ){
		        return null;
		    }
		    else{
		        return c;
		    }
		}));
		
		range2.setPrefWidth(40);
		range2.setTextFormatter( new TextFormatter<>(c ->{
		    if ( c.getControlNewText().isEmpty() ){
		        return c;
		    }
		   
		    ParsePosition parsePosition = new ParsePosition( 0 );
		    Object object = format.parse( c.getControlNewText(), parsePosition );

		    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ){
		        return null;
		    }
		    else{
		        return c;
		    }
		}));
		Label from = new Label("From");
		Label to = new Label("To");
		from.setPadding(new Insets(5,10,0,10));
		to.setPadding(new Insets(5,10,0,10));
		
		hbox.getChildren().add(numeText);
		hbox.getChildren().add(dash);
		hbox.getChildren().add(denoText);
		hbox.getChildren().add(from);
		
		hbox.getChildren().add(range1);
		hbox.getChildren().add(to);
		hbox.getChildren().add(range2);
		
		
		//for spacing
		Label space = new Label(" ");
		hbox.getChildren().add(space);
		
		//- button
		Button minus = new Button();
		minus.setPrefWidth(40);
		//minus padding
		minus.setPadding(new Insets(0,0,0,10));
		
	
		minus.setPrefHeight(30);
		//minus sty;e
		minus.setStyle("-fx-background-color: transparent;\r\n"
				+ "-fx-background-image:  url(\"min-24.png\");\r\n"
				+ "-fx-background-repeat:  no-repeat;\r\n"
				+ "-fx-background-insets: 2px;");
		hbox.getChildren().add(minus);
		minus.setOnMouseEntered(e ->{
			minus.getScene().setCursor(Cursor.HAND);
			Tooltip tooltip = new Tooltip("Delete");
			tooltip.setShowDelay(new Duration(0));
			minus.setTooltip(tooltip);
		});
		minus.setOnMouseExited(e -> {
			minus.getScene().setCursor(Cursor.DEFAULT);
		});
		minus.setOnAction(e->{
			parent.getChildren().remove(hbox);
			list.remove(this);
			//hbox.getParent().getScene().getWindow().setHeight(hbox.getParent().getScene().getWindow().getHeight() - 32);
			parent.getScene().getWindow().setHeight(parent.getScene().getWindow().getHeight() - 32);
			
		});
		
		//when minus this node gets removed
	}
	
	public HBox get() {
		return hbox;
	}
	
	public Pair<Integer,Integer> getTimeSignature() {
		int nume;
		int deno;
		
		if(numeText.getText().isEmpty()) {
			nume = 4;
			
		}
		else nume = Integer.parseInt(numeText.getText());
		if(denoText.getText().isEmpty()) {
			deno = 4;
		}
		else deno = Integer.parseInt(denoText.getText());
		
		return new Pair<>(nume, deno);
	}
	public Pair<Integer,Integer> getRange(){
		int r1;
		int r2;
		
		if(range1.getText().isEmpty()) {
			r1 = 1;
			
		}
		else r1 = Integer.parseInt(range1.getText());
		if(range2.getText().isEmpty()) {
			r2 = r1;
		}
		else r2 = Integer.parseInt(range2.getText());
		
		return new Pair<>(r1,r2);
	}

}
