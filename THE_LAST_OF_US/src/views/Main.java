package views;
	
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.awt.Point;
import java.io.*;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
    static String[] name;
    static Pixel[][] mapCells = new Pixel[widthByBlocks][heightByBlocks];
	Stage stage1;
    Button button;
    GridPane gamemap;
    BorderPane mainpane;
    static Group blocks = new Group();
    static Group toppings = new Group();
    public static SideBar sideBar;
    static public CharacterGUI target;
    static public HeroGUI current;
    @Override
    public void start(Stage stage) throws IOException {
    	seticon(stage);
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
    			case H:
    				d = null;
    				if(sideBar.selectedTarget !=null && sideBar.SelectedTargetGUI instanceof HeroGUI){
						Main.current = (HeroGUI) sideBar.SelectedTargetGUI;
						Main.target = null;
						sideBar.selectedCharacter = (Main.current).hero;
						sideBar.SelectedCharacterGUI = (Main.current);
						sideBar.updateValues();
						Main.checkVisibility();
					}else{
						Exceptionswitchpopup x = new Exceptionswitchpopup();
					}
					break;
    			case C:
    				d = null;
    				try{
    					sideBar.selectedCharacter.cure();
    			 		Main.cureZombie();
    			 		sideBar.updateValues();
    			 	}
    			 	catch(GameActionException v) {
    			 		Exceptionspopup x = new Exceptionspopup(v.getMessage());
    			 	}
    				break;
    			case N:
    				d = null;
    				try{
    					sideBar.selectedCharacter.attack();
    						File musicpath=new File("Attack_sound.wav");
    						if(musicpath.exists()) {
    							AudioInputStream audioinput=AudioSystem.getAudioInputStream(musicpath);
    							Clip clip=AudioSystem.getClip();
    							clip.open(audioinput);
    							clip.loop(0);
    							clip.start();
    						}
    						else {
    							System.out.println("cant find file");
    						}
 
    			 		double difX = ((HeroGUI)sideBar.SelectedCharacterGUI).getCurrXBlocks() - ((ZombieGUI)sideBar.SelectedTargetGUI).getCurrXBlocks();
    			 		double difY = ((HeroGUI)sideBar.SelectedCharacterGUI).getCurrYBlocks() - ((ZombieGUI)sideBar.SelectedTargetGUI).getCurrYBlocks();
    			 		sideBar.SelectedCharacterGUI.spriteGUI.attackSprite((int)difX,(int)difY);
    			 	}
    			 	catch(GameActionException v) {
    			 		Exceptionspopup x = new Exceptionspopup(v.getMessage());
    			 	} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					} catch (UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    				sideBar.updateValues();
    				break;
				case M:
					d = null;
					try{
						sideBar.selectedCharacter.useSpecial();
				 		Main.checkVisibility();
				 		sideBar.updateValues();
				 	}
				 	catch(GameActionException v) {
				 		Exceptionspopup x = new Exceptionspopup(v.getMessage());
				 	}
					break;
				case E:
					d = null;
					try{
						Game.endTurn();
						Main.endTurnGUI();
					}
					catch(Exception v){
						v.printStackTrace();
					}
	    			break;
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
        
        BackgroundImage bgI = new BackgroundImage(
        		
                new javafx.scene.image.Image(getClass().getResourceAsStream("mapWallPaper.jpg")),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        
        Background bg = new Background(bgI);
        
        map.setBackground(bg);
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
    	sideBar = new SideBar(blockSize);
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
    	name = h.getName().split(" ");
    	HerosIMGwithAni characterSprite = new HerosIMGwithAni(name[0],x,y,character);
    	character.spriteGUI = characterSprite;
    	toppings.getChildren().add(character);
    	toppings.getChildren().add(characterSprite);
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
		sideBar.updateValues();
		for(int x =0;x<widthByBlocks; x++) {
            for(int y =0; y<heightByBlocks; y++) {
            	if(Game.map[14-y][x] instanceof CharacterCell && ((CharacterCell)(Game.map[14-y][x])).getCharacter() instanceof Zombie && mapCells[x][y].getAny() == null){
            		intializeZombieGUI(x, y,(Zombie)((CharacterCell)(Game.map[14-y][x])).getCharacter());
                	}
            }
        }
		sideBar.updateValues();
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
public void seticon(Stage stage) {
		
		Image imge=new Image(getClass().getResourceAsStream("download.jpeg"));
		stage.getIcons().add(imge);
		
	}
	
	
    
}
