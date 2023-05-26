package views;

import model.characters.*;
import model.characters.Character;
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
import exceptions.*;

import java.awt.Point;
import java.io.*;

import engine.Game;

public class SideBar {

	//test hero
	public static Hero selectedCharacter = (Main.current).hero;
	public static HeroGUI SelectedCharacterGUI = Main.current;
	public static Character selectedTarget;
	public static CharacterGUI SelectedTargetGUI;
	public static ImageView BackgroundImage;
	
	
	public static ImageView CharImage;
	public static Label CharName;
	public static Label CharClass;
	public static Label HpLabel;
	public static Label DMGLabel;
	public static Label actionsLabel;
	public static Label availableVaccines;
	public static Label availableSupply;
	
	public static ImageView TargetImage;
	public static Label TargetName = new Label("");
	public static Label TargetClass = new Label("");
	public static Label TargetHp = new Label("");
	public static Label TargetDMG = new Label("");
	public static Label TargetActions = new Label("");
	public static Label TargetVaccines = new Label("");
	public static Label TargetSupply = new Label("");
	
	public static StackPane SideBar =  new StackPane();
	
	public static double ImageSize;
	

	private void setLabelstoWhile(int cellSize) {
		CharName.setTextFill(Color.WHITE);
		CharClass.setTextFill(Color.WHITE);
		HpLabel.setTextFill(Color.WHITE);
		DMGLabel.setTextFill(Color.WHITE);
		actionsLabel.setTextFill(Color.WHITE);
		availableVaccines.setTextFill(Color.WHITE);
		availableSupply.setTextFill(Color.WHITE);
		TargetName.setTextFill(Color.WHITE);
		TargetClass.setTextFill(Color.WHITE);
		TargetHp.setTextFill(Color.WHITE);
		TargetDMG.setTextFill(Color.WHITE);
		TargetActions.setTextFill(Color.WHITE);
		TargetVaccines.setTextFill(Color.WHITE);
		TargetSupply.setTextFill(Color.WHITE);
		
		CharName.setFont(new Font("Arial",cellSize*0.3));
		CharClass.setFont(new Font("Arial",cellSize*0.3));
		HpLabel.setFont(new Font("Arial",cellSize*0.3));
		DMGLabel.setFont(new Font("Arial",cellSize*0.3));
		actionsLabel.setFont(new Font("Arial",cellSize*0.3));
		availableVaccines.setFont(new Font("Arial",cellSize*0.3));
		availableSupply.setFont(new Font("Arial",cellSize*0.3));
		TargetName.setFont(new Font("Arial",cellSize*0.3));
		TargetClass.setFont(new Font("Arial",cellSize*0.3));
		TargetHp.setFont(new Font("Arial",cellSize*0.3));
		TargetDMG.setFont(new Font("Arial",cellSize*0.3));
		TargetActions.setFont(new Font("Arial",cellSize*0.3));
		TargetVaccines.setFont(new Font("Arial",cellSize*0.3));
		TargetSupply.setFont(new Font("Arial",cellSize*0.3));
		
	}
	
	public static String Type(Hero h) {
		 if(h instanceof Fighter)
			 return "Fighter";
		 if(h instanceof Explorer)
			 return "Explorer";
		 return "Medic";
	}

	public SideBar(int cellSize) throws IOException{
		
		 ImageSize = ((cellSize*15*225)/845)*0.65;
		 Image Background = new Image(getClass().getResourceAsStream("SideBar.png"));
		 BackgroundImage = new ImageView(Background);
		 BackgroundImage.setPreserveRatio(true);
		 BackgroundImage.setFitHeight(cellSize*15);
		 //getting background and setting background size
		 
		 Image Unknown = new Image(getClass().getResourceAsStream("Q.jpg"));
		 CharImage = new ImageView(Unknown);
		 CharImage.setPreserveRatio(true);
		 CharImage.setFitWidth(ImageSize);
		 
		 CharName = new Label(selectedCharacter.getName());
		 
		 CharClass = new Label("Class: " + Type(selectedCharacter));
		 
		 HpLabel = new Label("HP: " + selectedCharacter.getCurrentHp() + "/" + selectedCharacter.getMaxHp());
		 
		 DMGLabel = new Label("Damage: " + selectedCharacter.getAttackDmg());
		 
		 actionsLabel = new Label("Action Points: " + selectedCharacter.getActionsAvailable() + "/" + selectedCharacter.getMaxActions());
		 
		 availableVaccines = new Label("Vaccines: " + selectedCharacter.getVaccineInventory().size());
		 
		 availableSupply = new Label("Supplies  " + selectedCharacter.getSupplyInventory().size());
		 
		 //setting labels for selected character
		 
		 spacer underImage = new spacer(cellSize);
		 
		 HBox HP_DMG = new HBox(HpLabel, DMGLabel);
		 HP_DMG.setAlignment(Pos.CENTER);
		 HP_DMG.setSpacing(10);
		 HBox inventories = new HBox( availableVaccines, availableSupply);
		 inventories.setAlignment(Pos.CENTER);
		 inventories.setSpacing(10);
		 
		 VBox selected = new VBox(CharImage,underImage.spacer, CharName, CharClass, HP_DMG,actionsLabel, inventories);
		 selected.setAlignment(Pos.TOP_CENTER);
		//VBOX for selected character info
		 
		 TargetImage = new ImageView(Unknown);
		 TargetImage.setPreserveRatio(true);
		 TargetImage.setFitWidth(ImageSize);
		 
		 HBox HP_DMG2 = new HBox(TargetHp, TargetDMG);
		 HP_DMG2.setAlignment(Pos.CENTER);
		 HP_DMG2.setSpacing(10);
		 HBox inventories2 = new HBox(TargetVaccines, TargetSupply);
		 inventories2.setAlignment(Pos.CENTER);
		 inventories2.setSpacing(10);
		 
		//setting labels for target character
		 
		 spacer beforeImage = new spacer(cellSize*1.5);
		 spacer afterImage = new spacer(cellSize);
		 
		 VBox target = new VBox(beforeImage.spacer , TargetImage, afterImage.spacer,TargetName, TargetClass, HP_DMG2, inventories2);
		 target.setAlignment(Pos.TOP_CENTER);
		//VBOX for target character info
		 
		 spacer top = new spacer(cellSize);
		 
		 Button attack = new Button("Attack");
		 attack.setOnAction(event -> {
			 	try{
			 		selectedCharacter.attack(); 
			 		updateValues();
			 	}
			 	catch(GameActionException e) {
			 		Exceptionspopup x = new Exceptionspopup(e.getMessage());
			 	}
		 });
		 Button Cure = new Button("Cure");
		 Cure.setOnAction(event -> {
			 try{
		 		selectedCharacter.cure();
		 		Main.cureZombie();
		 		updateValues();
		 	}
		 	catch(GameActionException e) {
		 		Exceptionspopup x = new Exceptionspopup(e.getMessage());
		 	} 
			 });
		 Button useSpecial = new Button("Use \n Supply");
		 useSpecial.setOnAction(event -> {
			try{
		 		selectedCharacter.useSpecial();
		 		updateValues();
		 		Main.checkVisibility();
		 	}
		 	catch(GameActionException e) {
		 		Exceptionspopup x = new Exceptionspopup(e.getMessage());
		 	} 
			 });
		 useSpecial.setTextAlignment(TextAlignment.CENTER);
		 
		 HBox actions = new HBox(attack, Cure, useSpecial);
		 actions.setAlignment(Pos.CENTER);
		 
		 Button endTurn = new Button("End Turn");
		 endTurn.setOnAction(event -> {
			try{
				Game.endTurn();
				Main.endTurnGUI();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		});
		 Button switchHero = new Button("Swich Hero");
		 switchHero.setOnAction(event->{
					if(selectedTarget !=null && SelectedTargetGUI instanceof HeroGUI){
						Main.current = (HeroGUI) SelectedTargetGUI;
						Main.target = null;
						selectedCharacter = (Main.current).hero;
						updateValues();
					}else{
						Exceptionswitchpopup x = new Exceptionswitchpopup();
					}
					
		 });
		 
		 HBox actions2 = new HBox(endTurn, switchHero);
		 actions2.setAlignment(Pos.CENTER);
		 
		 spacer spacerBottom = new spacer(cellSize);
		 
		 VBox all = new VBox(top.spacer, selected, target,spacerBottom.spacer ,actions, actions2);
		 all.setAlignment(Pos.TOP_CENTER);
		 
		 SideBar.getChildren().addAll(BackgroundImage, all);
	

		 setLabelstoWhile(cellSize);
		 
		 attack.setMinHeight(40);
		 Cure.setMinHeight(40);
}
	public static void addTargetSideBar(){
		SelectedTargetGUI = Main.target;
		if(SelectedTargetGUI instanceof HeroGUI)
			selectedTarget = ((HeroGUI)(Main.target)).hero;
		if(SelectedTargetGUI instanceof ZombieGUI)
			selectedTarget = ((ZombieGUI)(Main.target)).zomb;
		
		updateValues();
	}


	public static void updateValues() {
		
		if (selectedCharacter != null && selectedCharacter.getCurrentHp()==0) {
			CharName.setText("");
			
			CharClass.setText("");
	 	
			HpLabel.setText("");
	 
			DMGLabel.setText("");
	 
			actionsLabel.setText("");
	 
			availableVaccines.setText("");
	 
			availableSupply.setText("");
			
			SelectedCharacterGUI.removeHero();
			/*
			try {
				Main.current = (HeroGUI) Main.GUIs.get(0);
				Main.target = null;
				selectedCharacter = (Main.current).hero;
				updateValues();
			}
			catch(Exception e)
			{
				
			}*/
		}
		else {
			CharName.setText(selectedCharacter.getName());

            CharClass.setText("Class: " + Type(selectedCharacter));

            HpLabel.setText("HP: " + selectedCharacter.getCurrentHp() + "/" + selectedCharacter.getMaxHp());

            DMGLabel.setText("Damage: " + selectedCharacter.getAttackDmg());

            actionsLabel.setText("Action Points: " + selectedCharacter.getActionsAvailable() + "/" + selectedCharacter.getMaxActions());

            availableVaccines.setText("Vaccines: " + selectedCharacter.getVaccineInventory().size());

            availableSupply.setText("Supplies  " + selectedCharacter.getSupplyInventory().size());
		}
		
		//System.out.println("Here 1" + selectedTarget);
		
		if (selectedTarget != null && selectedTarget.getCurrentHp()==0 && selectedTarget instanceof Zombie) {
			TargetName.setText("");
			
			TargetClass.setText("");
	 	
			TargetHp.setText("");
			
			((ZombieGUI) SelectedTargetGUI).removeZombie();
			
			selectedTarget = null;
		}
		else {
			if(selectedTarget != null && selectedTarget instanceof Zombie){
				//System.out.println("Here 2");
				TargetName.setText(selectedTarget.getName());
				TargetClass.setText("");
				TargetHp.setText("HP: " + selectedTarget.getCurrentHp() + "/" + selectedTarget.getMaxHp());
				TargetDMG.setText("DMG: " + selectedTarget.getAttackDmg());
				TargetActions.setText("");
				TargetVaccines.setText("");
				TargetSupply.setText("");
			}
			if(selectedTarget != null && selectedTarget instanceof Hero){
				//System.out.println("Here 3");
				TargetName.setText(selectedTarget.getName());
				TargetClass.setText("Class: " + Type((Hero)selectedTarget));
				TargetHp.setText("HP: " + selectedTarget.getCurrentHp() + "/" + selectedTarget.getMaxHp());
				TargetDMG.setText("DMG: " + selectedTarget.getAttackDmg());
				TargetActions.setText("Action Points: " + ((Hero)selectedTarget).getActionsAvailable() + "/" + ((Hero)selectedTarget).getMaxActions());
				TargetVaccines.setText("Vaccines: " + ((Hero)selectedTarget).getVaccineInventory().size());
				TargetSupply.setText("Supplies: " + ((Hero)selectedTarget).getSupplyInventory().size());
			}
		}
		
		
		if(Game.checkGameOver()){
			if(Game.checkWin()){
				Exceptionspopup x = new Exceptionspopup("You Won");
			}else{
				Exceptionspopup x = new Exceptionspopup("Game Over");
			}
		}
	}


class spacer{

	
	
	ImageView spacer;
	public spacer(double x) {
		Image SpacerImage = new Image(getClass().getResourceAsStream("Spacer (YES THIS IS NEEDED).png"));
		spacer = new ImageView(SpacerImage);
		
		spacer.setFitHeight(x);
		}
	}

}

