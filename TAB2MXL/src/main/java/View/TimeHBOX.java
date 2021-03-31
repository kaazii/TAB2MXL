package View;

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
import javafx.util.Duration;
import javafx.util.Pair;

public class TimeHBOX{
	
	HBox hbox;
	public int timeSig = 1;
	public TextField range1;
	public TextField range2;
	public VBox parent;
	public  List<TimeHBOX> list;
	
	public TimeHBOX(VBox parent, List<TimeHBOX> list) {
		this.list = list;
		this.parent = parent;
		list.add(this);
		hbox = new HBox();
		hbox.setPrefSize(200, 31);
		hbox.setPadding(new Insets(0,15,0,15));
		TextField range1 = new TextField();
		TextField range2 = new TextField();
		RadioMenuItem item1 = new RadioMenuItem("1/4");
		
		RadioMenuItem item2 = new RadioMenuItem("2/4");
		RadioMenuItem item3 = new RadioMenuItem("3/4");
		RadioMenuItem item4 = new RadioMenuItem("4/4");
		MenuButton time = new MenuButton("4/4");
		//default
		item4.setSelected(true);
		//when item 1 is selected all other unselected
		item1.setOnAction(e -> {
			item2.setSelected(false);
			item3.setSelected(false);
			item4.setSelected(false);
			timeSig = 1;
			time.setText(timeSig + "/4");
		});
		//item 2 selected -> all other unselected
		item2.setOnAction(e -> {
			item1.setSelected(false);
			item3.setSelected(false);
			item4.setSelected(false);
			timeSig = 2;
			time.setText(timeSig + "/4");
		});
		//item 3 selected -> all other unselected
		item3.setOnAction(e -> {
			item1.setSelected(false);
			item2.setSelected(false);
			item4.setSelected(false);
			timeSig = 3;
			time.setText(timeSig + "/4");
		});
		
		//item 4 selected -> all other unselected
		item4.setOnAction(e -> {
			item1.setSelected(false);
			item2.setSelected(false);
			item3.setSelected(false);
			timeSig = 4;
			time.setText(timeSig + "/4");
		});
		time.getItems().add(item1);
		time.getItems().add(item2);
		time.getItems().add(item3);
		time.getItems().add(item4);
		time.setStyle("-fx-background-color: transparent;\r\n"
				+ "-fx-border-radius: 10;\r\n"
				+ "-fx-border-color:  #F0F4F0; \r\n"
				+ "-fx-border-width: 2");
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
		
		hbox.getChildren().add(time);
		hbox.getChildren().add(from);
		
		hbox.getChildren().add(range1);
		hbox.getChildren().add(to);
		hbox.getChildren().add(range2);
		
		
		//for spacing
		Label space = new Label("      ");
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
	
	public int getTimeSignature() {
		return timeSig;
	}
	public Pair<Integer,Integer> getRange(){
		
		return new Pair<>(Integer.parseInt(range1.getText()), Integer.parseInt(range2.getText()));
	}

}
