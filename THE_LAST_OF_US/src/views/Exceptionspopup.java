package views;
import javafx.geometry.Pos;
import javafx.util.Duration;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class Exceptionspopup {
	
	public Exceptionspopup(String s) {
		try {
			File musicpath=new File("Gameover.wav");
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
		Stage Stage_for_exception=new Stage();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			Stage_for_exception.close();
        }));
        timeline.play();
        
		Image imge=new Image(getClass().getResourceAsStream("download.jpeg"));
		
		Stage_for_exception.getIcons().add(imge);

		BorderPane InvalidTargetExceptioncurepopup=new BorderPane();
		
		HBox hor=new HBox();
		
		InvalidTargetExceptioncurepopup.setCenter(hor);
		
		Scene sce=new Scene(InvalidTargetExceptioncurepopup,600,300);	
		
		Stage_for_exception.setScene(sce);
		
		Label label1=new Label(s);
		
		label1.setFont(Font.font("Impact", FontWeight.SEMI_BOLD, FontPosture.ITALIC,40));
		
		hor.setAlignment(Pos.CENTER);
		
		hor.getChildren().addAll(label1);
		
		Stage_for_exception.initModality(Modality.APPLICATION_MODAL);	
		
		Stage_for_exception.setOnCloseRequest(c->{
			Stage_for_exception.close();
		});
		
		Stage_for_exception.setOnHiding(d->{
			Stage_for_exception.close();
		});
		
		Stage_for_exception.setResizable(false);

		Stage_for_exception.showAndWait();
	}
}
