package views;
	
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
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


public class Main extends Application {
	static int blockSize;
	static int widthByBlocks= 15;
	static int heightByBlocks = 15;
	static ArrayList<HeroGUI> GUIs = new ArrayList<HeroGUI>();
	Screen screen = Screen.getPrimary();
    javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
    
    static Pixel[][] mapCells = new Pixel[widthByBlocks][heightByBlocks];
	Stage stage1;
    Button button;
    GridPane gamemap;
    BorderPane mainpane;
    static Group blocks = new Group();
    static Group toppings = new Group();
    static public CharacterGUI target;
    static public HeroGUI current;
    @Override
    public void start(Stage stage) throws IOException {
    	Scene scene = new Scene(createMap());
        stage.setResizable(false);
        scene.getStylesheets().add(Main.class.getResource("App.css").toExternalForm());
        scene.setOnKeyPressed(e->{
    		System.out.println(e.getCode());
    		int prevX = toMap(current.getPrevX());
    		int prevY = toMap(current.getPrevY());
    		int newX = prevX;
    		int newY = prevY;
    		Direction d;
    		switch(e.getCode()){
    			case W:
    				newX = prevX;
    	    		newY = prevY -1;
    	    		d = Direction.UP;
    	    		break;
    			case S:
    				newX = prevX;
    	    		newY = prevY +1;
    	    		d = Direction.DOWN;
    	    		break;
    			case A:
    				newX = prevX-1;
    	    		newY = prevY;
    	    		d = Direction.LEFT;
    	    		break;
    			case D:
    				newX = prevX+1;
    	    		newY = prevY;
    	    		d = Direction.RIGHT;
    	    		break;
    			default:
    	    		d = null;
    				break;
    		}
    		
    		 
    		if (d != null){
    		current.GUImove(newX, newY,d);
    		mapCells[prevX][prevY].setAny(null);
    		mapCells[newX][newY].setAny(current);
    		};
    		
    	});
        
        stage.setScene(scene);
        stage.show();
        
    }
    public Parent createMap() throws IOException{
    	Game.startGame(mainmenu.the_starting_hero);
    	Pane map = new Pane();
        mainpane = new BorderPane();
    	
        map.setPrefSize(widthByBlocks*blockSize, heightByBlocks*blockSize);
        map.getChildren().addAll(blocks,toppings);
    	for(int x =0;x<widthByBlocks; x++) {
            for(int y =0; y<heightByBlocks; y++) {
            	Pixel block = new Pixel(true,x,y);
            	mapCells[x][y] = block;
                block.getStyleClass().add("pixel");
                blocks.getChildren().add(block);
                if(Game.isHero(14-y,x)){
                	current = intializeHeroGUI(x, y,(Hero)((CharacterCell)(Game.map[14-y][x])).getCharacter());
                	//System.out.println("Hero");
            }
            if(Game.map[14-y][x] instanceof CharacterCell && ((CharacterCell)(Game.map[14-y][x])).getCharacter() instanceof Zombie){
            	intializeZombieGUI(x, y,(Zombie)((CharacterCell)(Game.map[14-y][x])).getCharacter());;
                //System.out.println("Zombie");
                
        

            }
            if(Game.map[14-y][x] instanceof CollectibleCell && ((CollectibleCell)(Game.map[14-y][x])).getCollectible() instanceof Vaccine){
            	createVaccine(x, y);
                //System.out.println("Vaccine");
        

            }
            if(Game.map[14-y][x] instanceof CollectibleCell && ((CollectibleCell)(Game.map[14-y][x])).getCollectible() instanceof Supply){
            	createSupply(x, y);
                //System.out.println("Supply");
        

            }
            if(Game.map[14-y][x] instanceof TrapCell){
            	createTrap(x, y);
                //System.out.println("Trap");
        

            }
            }
        }
    	checkVisibility();
    	SideBar sideBar = new SideBar(blockSize);
        mainpane.setRight(sideBar.SideBar);
        System.out.println("Done");
        mainpane.setCenter(map);
        CharacterBar characterBar = new CharacterBar(blockSize);
        mainpane.setLeft(characterBar.getCharacterBar());
       return mainpane;
        
    }
    public static ZombieGUI intializeZombieGUI(int x, int y,Zombie zomb){
    	ZombieGUI zombie = new ZombieGUI(x,y,zomb);
    	mapCells[x][y].setAny(zombie);
    	toppings.getChildren().add(zombie);
    	return zombie; 
    }
    public static HeroGUI intializeHeroGUI(int x, int y,Hero h){
    	HeroGUI character = new HeroGUI(x,y,h);
    	toppings.getChildren().add(character);
    	GUIs.add(character);

    	/*
    	character.setOnMouseReleased(e->{
    		int newX = toMap(character.getLayoutX());
    		int newY = toMap(character.getLayoutY());
    		
    		int prevX = toMap(character.getPrevX());
    		int prevY = toMap(character.getPrevY());
    		character.GUImove(newX, newY);
    		mapCells[prevX][prevY].setAny(null);
    		mapCells[newX][newY].setAny(character);
    		
    	});
    	*/
    	
    	return character; 
    }
    

    private int toMap(double loc) {
		return (int) (loc + blockSize /2)/blockSize;
	}
    public SupplyGUI createSupply(int x, int y){
    	SupplyGUI supply = new SupplyGUI(x,y);
    	toppings.getChildren().add(supply);
    	mapCells[x][y].setAny(supply);
    	return supply; 
    }
    public VaccineGUI createVaccine(int x,int y){
    	VaccineGUI vaccine = new VaccineGUI(x,y);
    	toppings.getChildren().add(vaccine);
    	mapCells[x][y].setAny(vaccine);
    	return vaccine; 
    }
    public TrapsGUI createTrap(int x,int y){
    	TrapsGUI trap = new TrapsGUI(x,y);
    	toppings.getChildren().add(trap);
        //trap.setVisibilty(false);

    	mapCells[x][y].setAny(trap);
    	mapCells[x][y].setVisibilty(false);

    	return trap; 
    }
	public static void main(String[] args) {
        //launch();
        
    }
	public static void cureZombie() {
		ZombieGUI zomb = ((ZombieGUI)(target));
		int x = (int) zomb.getCurrXBlocks();
		int y = (int) zomb.getCurrYBlocks();
		intializeHeroGUI(x, y,(Hero)((CharacterCell)(Game.map[14-y][x])).getCharacter());
		zomb.removeZombie();
		Main.checkVisibility();
		
		
		
	}
	public static void endTurnGUI(){
		SideBar.updateValues();
		for(int x =0;x<widthByBlocks; x++) {
            for(int y =0; y<heightByBlocks; y++) {
            	if(Game.map[14-y][x] instanceof CharacterCell && ((CharacterCell)(Game.map[14-y][x])).getCharacter() instanceof Zombie && mapCells[x][y].getAny() == null){
            		intializeZombieGUI(x, y,(Zombie)((CharacterCell)(Game.map[14-y][x])).getCharacter());
                	}
            }
        }
		SideBar.updateValues();
		Main.checkVisibility();
		

	}
	public static void checkVisibility(){
		for(int x =0;x<widthByBlocks; x++) {
            for(int y =0; y<heightByBlocks; y++) {
            		
            			mapCells[x][y].setVisibilty(false);
            		
            		if(Game.map[14-y][x].isVisible())
            			mapCells[x][y].setVisibilty(true);
            			
            		
            }
        }
		
	}
	
	
    
}