package views;

import model.characters.*;
import views.SideBar.spacer;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import exceptions.*;
import java.util.ArrayList;

import java.awt.Point;
import java.io.*;

import engine.Game;

public class CharacterBar{

	private VBox CharacterBar = new VBox();
	
	private Label CharName = new Label("");
		 
	private Label CharClass = new Label("");
		 
	private Label HpLabel = new Label("");
		 
	private Label DMGLabel = new Label("");
		 
	private Label actionsLabel = new Label("");
    
	private Label status = new Label("");

	
	
	public VBox getCharacterBar() {
		return CharacterBar;
	}

	public CharacterBar(int cellSize) throws IOException {
		set_font(CharName);
		set_font(CharClass);
		set_font(HpLabel);
		set_font(DMGLabel);
		set_font(actionsLabel);
		set_font(status);
		
		ArrayList<Hero> avaliableh = Game.allHeroes;
		
		Button b0=new Button();
		assign_heroes(b0,0,avaliableh);

		
		Button b1=new Button();
		assign_heroes(b1,1,avaliableh);

		
		Button b2=new Button();
		assign_heroes(b2,2,avaliableh);

		
		Button b3=new Button();
		assign_heroes(b3,3,avaliableh);

		
		Button b4=new Button();
		assign_heroes(b4,4,avaliableh);

		
		Button b5=new Button();
		assign_heroes(b5,5,avaliableh);

		
		Button b6=new Button();
		assign_heroes(b6,6,avaliableh);

		
		Button b7=new Button();
		assign_heroes(b7,7,avaliableh);
		
		set_button_font(b0);
		set_button_font(b1);
		set_button_font(b2);
		set_button_font(b3);
		set_button_font(b4);
		set_button_font(b5);
		set_button_font(b6);
		set_button_font(b7);
		
		
		VBox buttons = new VBox(b0,b1,b2,b3,b4,b5,b6,b7);
		buttons.setSpacing(Main.blockSize*0.1);

   		 //setting labels for selected character
   		 
   		 HBox HP_DMG = new HBox(HpLabel, DMGLabel);
   		 HP_DMG.setAlignment(Pos.CENTER);
   		 HP_DMG.setSpacing(10);
   		 
   		 VBox selected = new VBox(CharName, CharClass, HP_DMG,actionsLabel, status);
   		 selected.setAlignment(Pos.TOP_CENTER);
   		 selected.setSpacing(Main.blockSize*0.3);
		
		CharacterBar.getChildren().addAll(buttons, selected);
		CharacterBar.setAlignment(Pos.TOP_CENTER);
		
		CharacterBar.setBackground(new Background(new BackgroundFill(Color.GRAY,null , null)));
		CharacterBar.setSpacing(Main.blockSize);
		

	}

	public void assign_heroes(Button b,int i,ArrayList<Hero> a) {
		//assigning hero
		b.setText(a.get(i).getName());
		double buttonWidth = Main.blockSize*4;
	    double buttonHeight = Main.blockSize*0.8;
	    b.setPrefWidth(buttonWidth);
	    b.setPrefHeight(buttonHeight);
	    Background blackBackground = new Background(new BackgroundFill(Color.BROWN, null, null));
        b.setBackground(blackBackground);
        b.setTextFill(Color.WHITESMOKE);
        double size = Main.blockSize * 2;
        b.setStyle("-fx-background-color: black; -fx-background-size: cover; -fx-text-fill: white; -fx-font-size: 16pt; -fx-background-radius: 25;");
        //assigning action
        b.setOnMouseClicked(event -> {
        	   CharName.setText(a.get(i).getName());
         		 
               CharClass.setText("Class: " + SideBar.Type(a.get(i)));
          		 
               HpLabel.setText("HP: " + a.get(i).getMaxHp());
          		 
               DMGLabel.setText("Damage: " + a.get(i).getAttackDmg());
          		 
               actionsLabel.setText("Action Points: " + a.get(i).getMaxActions());
               
               status.setText("Status: " + getStatus(a.get(i)));
        });
        
	}
	public void set_charachter_icon(Button b,String s) {
		Image icon = new Image(getClass().getResourceAsStream(s));
		ImageView imageView = new ImageView(icon);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(Main.blockSize*0.8);
		b.setGraphic(imageView);
	}
	
	public String getStatus(Hero h) {
		if(Game.availableHeroes.contains(h))
			return "Zombified";
		if(Game.heroes.contains(h))
			return "Alive";
		return "Dead";
	}
	private void set_font(Label l) {
		l.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"),14));
		l.setStyle("-fx-background-color: black;-fx-text-fill: white;");
	}
	private void set_button_font(Button l) {
		l.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"),14));
		l.setStyle("-fx-background-color: black;-fx-text-fill: white;");
	}
}
