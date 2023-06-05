package views;
import javafx.scene.media.*;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import engine.Game;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;

public class mainmenu extends Application {
	public static Hero the_starting_hero;
	public static double height;
	public void start(Stage stage) throws Exception {
		//play_music();
	
		Stage Mainstage = stage; 	// the first window to be opened
		
		Mainstage.initStyle(StageStyle.UNDECORATED);
		
		seticon(Mainstage); // to set an icon for mainstage window
		
		Mainstage.setTitle("THE LAST OF US"); // sets the title of first Mainstage
		
		BorderPane layout1 = new BorderPane();		// the layout for the Mainstage
		
		VBox layout2=new VBox(); // second layout which we will put our buttons in it 
		
		Screen screen = Screen.getPrimary();
		javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();  // this to be able to fit any screen 
		
		Scene scene1 = new Scene(layout1,bounds.getWidth(),bounds.getHeight());	//the scene where layout1 will appear at
		
		layout1.setCenter(layout2);
		
		set_background(layout1); // adding background

		Button start_game_button=new Button("Start Game"); 	//creating a button to start the game
		
		
		
		start_game_button.setOnAction(e->{
			try {
				play_music_for_main_buttons();
				create_welcome_scene(Mainstage,scene1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		set_main_buttons(start_game_button);
		start_game_button.setScaleX(1.5);
		start_game_button.setScaleY(1.5);
	
		
		Button Exit_game_button=new Button("Exit"); 		//button to exit game and close Mainstage
		set_main_buttons(Exit_game_button);
		Exit_game_button.setScaleX(1.5);
		Exit_game_button.setScaleY(1.5);
		
		Button Tutorial_button=new Button("Tutorial");	
		set_main_buttons(Tutorial_button);
		Tutorial_button.setScaleX(1.5);
		Tutorial_button.setScaleY(1.5);
		
		
		Exit_game_button.setOnAction(e->{// when we press exit the player exits Main stage
			play_music_for_main_buttons();
			Stage tempstage = new Stage();
			
			Image imge=new Image(getClass().getResourceAsStream("download.jpeg"));
			tempstage.getIcons().add(imge);
			
			BorderPane layout_for_yes_no=new BorderPane();
			HBox hor=new HBox();
			layout_for_yes_no.setTop(hor);
			Scene sce=new Scene(layout_for_yes_no,500,300);		
			tempstage.setScene(sce);
			Label label1=new Label("Are you sure you want to exit?");
			label1.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"), 18));
			label1.setStyle("-fx-background-color: black;-fx-text-fill: white;");
			hor.setAlignment(Pos.CENTER);
			Button yes_button=new Button("Yes");
			Button no_button=new Button("NO");
			no_button.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"), 18));
			no_button.setStyle("-fx-background-color: black;-fx-text-fill: white;");
			yes_button.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"), 18));
			yes_button.setStyle("-fx-background-color: black;-fx-text-fill: white;");
			hor.getChildren().addAll(label1);
			HBox hor2=new HBox();
			layout_for_yes_no.setCenter(hor2);
			hor2.getChildren().addAll(no_button,yes_button);
			hor2.setAlignment(Pos.CENTER);
			hor2.setSpacing(50);
			tempstage.initModality(Modality.APPLICATION_MODAL);	
			yes_button.setOnAction(a->{
				tempstage.close();
				Mainstage.close();				
			});
			no_button.setOnAction(b->{
				tempstage.close();
			});
			tempstage.setOnCloseRequest(c->{
				tempstage.close();
			});
			tempstage.setOnHiding(d->{
				tempstage.close();
			});
			tempstage.setResizable(false);
			
			tempstage.showAndWait();
		});
		
		Tutorial_button.setOnAction(e->{
			play_music_for_main_buttons();
			create_new_scene_for_tutorial(Mainstage,scene1);
		});
		
		layout2.getChildren().addAll(start_game_button,Tutorial_button,Exit_game_button); // adding 2 buttons to layout2
		
		layout2.setAlignment(javafx.geometry.Pos.CENTER);  // to make buttons on center 
		
		layout2.setSpacing(100); // to set a space between buttons 
		
		Mainstage.setScene(scene1);  // set the scene to our stage 
		
		Mainstage.setMaximized(true);
		
		Mainstage.setResizable(false);
		
		Mainstage.show();	// show first window
		
	}
	// Method to play music in background
	public void play_music() {
		try {
			File musicpath=new File("Mus.wav");
			if(musicpath.exists()) {
				AudioInputStream audioinput=AudioSystem.getAudioInputStream(musicpath);
				Clip clip=AudioSystem.getClip();
				clip.open(audioinput);
				clip.loop(1000000000);
				clip.start();
			}
			else {
				System.out.println("cant find file");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void play_music_for_main_buttons() {
		try {
			File musicpath=new File("Button2.wav");
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
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	// method to set an icon to the game
	public void seticon(Stage stage) {
		
		Image imge=new Image(getClass().getResourceAsStream("download.jpeg"));
		stage.getIcons().add(imge);
		
	}
	// Method to add image to background
	public void set_background(BorderPane layout) {
		
		Image image= new Image(getClass().getResourceAsStream("pxfuel.jpg"));
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
	    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	    Background background = new Background(backgroundImage);
	    layout.setBackground(background);
	    
	}
	// method to create new scene in tutorial
	public void create_new_scene_for_tutorial(Stage Mainstage,Scene scene1) {
		BorderPane layout3 = new BorderPane();
		
		Scene scene2 = new Scene(layout3,scene1.getWidth(),scene1.getHeight());
		
		Button back_to_scene1=new Button();
		
		HBox layout4=new HBox();
		
		layout4.getChildren().add(back_to_scene1);
		
		layout3.setTop(layout4);
		
		layout4.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		HBox layout5=new HBox();
		
		layout3.setCenter(layout5);
		
		Label understand=new Label("1-First you have to choose a hero from 8 heros avaliable"+"\n"+"\n"+
		"2-The game is 15 * 15 grid that you can control a hero in the grid"+"\n"+"\n"+"3-The player only wins if he has successfully collected and used all vaccines and has 5 or more heroes alive."+"\n"+"\n"+
				"4-The game ends when the player\r\n"
				+ "has collected and used all vaccines or when all heroes have been overwhelmed and defeated by\r\n"
				+ "the zombies");
		
		understand.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"), 20));

		layout5.getChildren().add(understand);
		understand.setTextFill(Color.WHITE);
		Mainstage.setScene(scene2);
		
		back_to_scene1.setOnAction(e->{
			Mainstage.setScene(scene1);
		});
		
		Image icon = new Image(getClass().getResourceAsStream("93634.png"));
		ImageView imageView = new ImageView(icon);
		imageView.setFitWidth(20);
		imageView.setFitHeight(20);
		back_to_scene1.setGraphic(imageView);
		
		
		Image image= new Image(getClass().getResourceAsStream("backg22.jpg"));
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
	    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	    Background background = new Background(backgroundImage);
	    layout3.setBackground(background);	
	    
	}
	
	public void create_welcome_scene(Stage Mainstage,Scene Mainscene) throws IOException {
		BorderPane layout = new BorderPane();
	
		Image image= new Image(getClass().getResourceAsStream("backg22.jpg"));
		
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
	    
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	   
		Background background = new Background(backgroundImage);
	    
	    layout.setBackground(background);
	    
		HBox layout_for_back_button=new HBox();
		
		VBox layout_for_heores=new VBox();
		
		Scene scene = new Scene(layout,Mainscene.getWidth(),Mainscene.getHeight());

		Button back_to_scene1=new Button();
		
		back_to_scene1.setOnAction(e->{
			Mainstage.setScene(Mainscene);
		});
		
		layout_for_back_button.getChildren().add(back_to_scene1);
		
		Image icon = new Image(getClass().getResourceAsStream("93634.png"));
		ImageView imageView = new ImageView(icon);
		imageView.setFitWidth(20);
		imageView.setFitHeight(20);
		back_to_scene1.setGraphic(imageView);
		
		layout_for_back_button.setAlignment((javafx.geometry.Pos.CENTER_LEFT));
		
		
		layout.setTop(layout_for_back_button);
		
		layout.setLeft(layout_for_heores);
		
		layout_for_heores.setAlignment((javafx.geometry.Pos.CENTER));
		
		Label label_to_pick_a_hero=new Label("Pick a hero");
		
		label_to_pick_a_hero.setTextFill(Color.WHITE);
		
		label_to_pick_a_hero.setFont(new Font(30));
		
		//layout_for_heores.getChildren().add(label_to_pick_a_hero);
		
		Game.loadHeroes("Heros.csv");
		
		ArrayList<Hero>avaliableh= Game.availableHeroes;
		
		Button b0=new Button();
		assign_heroes(b0,0,avaliableh);

		set_button_action(b0,"Fighter",0,avaliableh,layout,Mainstage);
		
		
		Button b1=new Button();
		assign_heroes(b1,1,avaliableh);

		set_button_action(b1,"Medic",1,avaliableh,layout,Mainstage);
		
		Button b2=new Button();
		assign_heroes(b2,2,avaliableh);

		set_button_action(b2,"Explorer",2,avaliableh,layout,Mainstage);
		
		Button b3=new Button();
		assign_heroes(b3,3,avaliableh);

		set_button_action(b3,"Explorer",3,avaliableh,layout,Mainstage);
		
		
		Button b4=new Button();
		assign_heroes(b4,4,avaliableh);

		set_button_action(b4,"Explorer",4,avaliableh,layout,Mainstage);
		
		Button b5=new Button();
		assign_heroes(b5,5,avaliableh);

		set_button_action(b5,"Medic",5,avaliableh,layout,Mainstage);
		
		Button b6=new Button();
		assign_heroes(b6,6,avaliableh);

		set_button_action(b6,"Fighter",6,avaliableh,layout,Mainstage);
		
		Button b7=new Button();
		assign_heroes(b7,7,avaliableh);

		set_button_action(b7,"Medic",7,avaliableh,layout,Mainstage);
		
		
		layout_for_heores.getChildren().addAll(b0,b1,b2,b3,b4,b5,b6,b7);
		
		layout_for_heores.setSpacing(30);
		
		Mainstage.setScene(scene);
		
		double height = Mainstage.getHeight();
		Main.blockSize = (int) (height/16);
	}
	public String Type(Hero h) {
        if(h instanceof Fighter)
            return "Fighter";
        if(h instanceof Explorer)
            return "Explorer";
        return "Medic";
   }
	public void assign_heroes(Button b,int i,ArrayList<Hero> a) {
		b.setText(a.get(i).getName());
		double buttonWidth = 250;
	    double buttonHeight = 70;
	    b.setPrefWidth(buttonWidth);
	    b.setPrefHeight(buttonHeight);
        b.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"), 20));  // increase the font size
		b.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		
	}
	public void set_main_buttons(Button b) {
		double buttonWidth = 200;
	    double buttonHeight = 30; 
		b.setTextFill(Color.WHITE);		
		b.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"), 20));  // increase the font size
		b.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		b.setPrefWidth(buttonWidth);  // set the preferred width of the button
        b.setPrefHeight(buttonHeight);
	}

	public void set_button_action(Button b,String t,int i,ArrayList<Hero> a,BorderPane layout,Stage mainstage) {
		b.setOnAction(e->{
			VBox stats=new VBox();
			
			stats.setStyle("-fx-padding: 20px;");
		
			layout.setRight(stats);
			stats.setAlignment(javafx.geometry.Pos.CENTER);	
						
			Label stats_label = new Label("Name: "+a.get(i).getName()+"\n"+"\n"+"The Hero Class is "+t+"\n"+"\n"+
		"HP: "+a.get(i).getMaxHp()+
		"\n"+"\n"+"Starting Action Points: "+a.get(i).getActionsAvailable()+"\n"+"\n"+
		"Attack Damage: "+a.get(i).getAttackDmg());
			stats_label.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"), 32));
			stats_label.setTextAlignment(TextAlignment.CENTER);
			stats_label.setStyle("-fx-text-fill: white;");
			Button Coutinue=new Button("Coutinue");
			the_starting_hero=a.get(i);
			
			Coutinue.setFont(Font.loadFont(getClass().getResourceAsStream("ARCADEPI.TTF"), 26));
			Coutinue.setStyle("-fx-background-color: red;-fx-text-fill: white;");
			
			stats.getChildren().addAll(stats_label,Coutinue);
			
			stats.setSpacing(50);
			
			double buttonWidth = 200;
		    double buttonHeight = 50; 
		    Coutinue.setPrefWidth(buttonWidth);
		    Coutinue.setPrefHeight(buttonHeight);
		    
		    Coutinue.setOnAction(c->{
		    	// here we will link the grid with the mainmenu
		    	Main v = new Main();
		    	Stage s = new Stage();
		    	try {
					v.start(s);
					mainstage.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    });
		    
		});
	}
	public static void main(String[]args) {
		launch(args);
	}
	

}
