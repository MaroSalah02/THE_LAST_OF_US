package views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import views.CharacterGUI;
import views.Pixel;
import views.Pixel.*;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.awt.Point;
import java.io.*;
import java.util.*;

import model.characters.*;
import model.characters.Character;
import model.collectibles.*;
import model.world.*;
import engine.Game;
import exceptions.*;

public class HerosIMGwithAni extends ImageView{
	HeroGUI MyHeroGUI;
	Image image;
	List<Image> spriteImages = new ArrayList<>();
	int x;
	int y;
	//update
	public HerosIMGwithAni(String charachter,int x2, int y2,HeroGUI MyHeroGUI) {
		this.MyHeroGUI = MyHeroGUI;
        for (int i = 2; i <= 61; i++) {
            String imagePath = charachter+"/frame_" + i + ".png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            spriteImages.add(image);
        }
        setImage(spriteImages.get(0));
        double desiredWidth = Main.blockSize;  // Set the desired width for the sprite
		double desiredHeight = Main.blockSize; // Set the desired height for the sprite
		setFitWidth(desiredWidth);
		setFitHeight(desiredHeight);
        this.setTranslateX(x2*Main.blockSize);
        this.setTranslateY(y2*Main.blockSize);
        x=x2*Main.blockSize;
        y=y2*Main.blockSize;
        setOnMouseClicked(e->{
			if (Main.current != MyHeroGUI){
				Main.target = MyHeroGUI;
				Main.current.hero.setTarget(MyHeroGUI.hero);
				Main.sideBar.addTargetSideBar();
				
			}
			if (Main.current.hero instanceof Medic){
				Main.target = MyHeroGUI;
				Main.current.hero.setTarget(MyHeroGUI.hero);
				Main.sideBar.addTargetSideBar();
				
			}
		});
	}
	public void moveSprite(int toX,int toY,Direction d) {
		switch(d) {
		case UP:moveUp(toX,toY);break;
		case DOWN:moveDown(toX,toY);break;
		case LEFT:moveLeft(toX,toY);break;
		case RIGHT:moveRight(toX,toY);break;
		
		}
	}
	public void moveRight(int toX,int toY) {
		
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
        transition.setFromX(x); // Initial X position
        transition.setFromY(y); // Initial Y position
        transition.setToX(toX); // Target X position
        transition.setToY(toY); // Target Y position
        transition.setCycleCount(1); // Animation occurs once

        this.x = toX;
        this.y = toY;
        transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            double duration = transition.getTotalDuration().toSeconds();
            double progress = newTime.toSeconds() / duration;
            int frameIndex = (int) (progress * 8) + 27;
            this.setImage(spriteImages.get(frameIndex));
        });
        transition.play();

        
	}
public void moveDown(int toX,int toY) {
	
    TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
    transition.setFromX(x); // Initial X position
    transition.setFromY(y); // Initial Y position
    transition.setToX(toX); // Target X position
    transition.setToY(toY); // Target Y position
    transition.setCycleCount(1); // Animation occurs once

    this.x = toX;
    this.y = toY;
        transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            double duration = transition.getTotalDuration().toSeconds();
            double progress = newTime.toSeconds() / duration;
            int frameIndex = (int) (progress * 8) + 18;
            this.setImage(spriteImages.get(frameIndex));
        });
        transition.play();

        
	}
public void moveLeft(int toX,int toY) {
	
    TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
    transition.setFromX(x); // Initial X position
    transition.setFromY(y); // Initial Y position
    transition.setToX(toX); // Target X position
    transition.setToY(toY); // Target Y position
    transition.setCycleCount(1); // Animation occurs once

    this.x = toX;
    this.y = toY;
    transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
        double duration = transition.getTotalDuration().toSeconds();
        double progress = newTime.toSeconds() / duration;
        int frameIndex = (int) (progress * 8) + 9;
        this.setImage(spriteImages.get(frameIndex));
    });
    transition.play();

    
}
public void moveUp(int toX,int toY) {
	
    TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
    transition.setFromX(x); // Initial X position
    transition.setFromY(y); // Initial Y position
    transition.setToX(toX); // Target X position
    transition.setToY(toY); // Target Y position
    transition.setCycleCount(1); // Animation occurs once

    this.x = toX;
    this.y = toY;
    transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
        double duration = transition.getTotalDuration().toSeconds();
        double progress = newTime.toSeconds() / duration;
        int frameIndex = (int) (progress * 8);
        this.setImage(spriteImages.get(frameIndex));
    });
    transition.play();

    
}

public void attackSprite(int toX,int toY) {
	System.out.println("difX = "+toX+" ,difY = "+toY);
	if(toX ==-1 && toY == 0) {
		System.out.println("Right");
		attackRight();
	}
	if(toX == 0 && toY == 1) {
		System.out.println("Up");
		attackUp();
	}
	if(toX == 1 && toY == 0) {
		System.out.println("Left");
		attackLeft();
		
	}
	if(toX == 0 && toY ==-1) {
		System.out.println("Down");
		attackDown();
		
	}
	if(toX == 1 && toY ==-1) {
		attackDown();
		System.out.println("Down");

	}
	if(toX ==-1 && toY ==-1) {
		attackDown();
		System.out.println("Down");

	}
	if(toX ==-1 && toY == 1) {
		System.out.println("Up");

		attackUp();
	}
	if(toX == 1 && toY == 1) {
		System.out.println("Up");
		attackUp();
	}
}
public void attackRight() {
	
    TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), this);
    transition.setFromX(x); // Initial X position
    transition.setFromY(y); // Initial Y position
    transition.setToX(x); // Target X position
    transition.setToY(y); // Target Y position
    transition.setCycleCount(1); // Animation occurs once

    transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
        double duration = transition.getTotalDuration().toSeconds();
        double progress = newTime.toSeconds() / duration;
        int frameIndex = (int) (progress * 5) + 54;
        this.setImage(spriteImages.get(frameIndex));
    });
    transition.play();

    
}
public void attackDown() {

TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), this);
transition.setFromX(x); // Initial X position
transition.setFromY(y); // Initial Y position
transition.setToX(x); // Target X position
transition.setToY(y); // Target Y position

transition.setCycleCount(1); // Animation occurs once
    transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
        double duration = transition.getTotalDuration().toSeconds();
        double progress = newTime.toSeconds() / duration;
        int frameIndex = (int) (progress * 5) + 48;
        this.setImage(spriteImages.get(frameIndex));
    });
    transition.play();

    
}
public void attackLeft() {

TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), this);
transition.setFromX(x); // Initial X position
transition.setFromY(y); // Initial Y position
transition.setToX(x); // Target X position
transition.setToY(y); // Target Y position

transition.setCycleCount(1); // Animation occurs once

transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
    double duration = transition.getTotalDuration().toSeconds();
    double progress = newTime.toSeconds() / duration;
    int frameIndex = (int) (progress * 5) + 42;
    this.setImage(spriteImages.get(frameIndex));
});
transition.play();


}
public void attackUp() {

TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), this);
transition.setFromX(x); // Initial X position
transition.setFromY(y); // Initial Y position
transition.setToX(x); // Target X position
transition.setToY(y); // Target Y position
transition.setCycleCount(1); // Animation occurs once
transition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
    double duration = transition.getTotalDuration().toSeconds();
    double progress = newTime.toSeconds() / duration;
    int frameIndex = (int) (progress * 5) + 36;
    this.setImage(spriteImages.get(frameIndex));
});
transition.play();


}

}
