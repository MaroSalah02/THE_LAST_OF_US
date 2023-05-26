package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Checklose {
	public Checklose() {
Stage win_stage=new Stage();
		
		Image imge=new Image(getClass().getResourceAsStream("download.jpeg"));
		
		win_stage.getIcons().add(imge);

		BorderPane win_boarder=new BorderPane();
		
		VBox hor=new VBox();
		
		win_boarder.setCenter(hor);
		
		Scene sce=new Scene(win_boarder,500,300);	
		
		win_stage.setScene(sce);
		
		Label label1=new Label("Congratulations !");
		
		Label label2=new Label("You Won");
		
		Button exit_button=new Button("Exit");
		
		exit_button.setStyle("-fx-background-color: black; -fx-background-size: cover; -fx-text-fill: white; -fx-font-size: 16pt; -fx-background-radius: 25;");
		
		label1.setFont(Font.font("Impact", FontWeight.SEMI_BOLD, FontPosture.ITALIC,40));
		
		label2.setFont(Font.font("Impact", FontWeight.SEMI_BOLD, FontPosture.ITALIC,40));
		
		hor.setAlignment(Pos.CENTER);
		
		hor.getChildren().addAll(label1,label2,exit_button);
		
		win_stage.initModality(Modality.APPLICATION_MODAL);	
		
		exit_button.setOnAction(e->{
			win_stage.close();
			// here we will put the stage where the game is played
		});
		
		win_stage.setOnCloseRequest(c->{
			win_stage.close();
			// here we will put the stage where the game is played
		});
		
		win_stage.setOnHiding(d->{
			win_stage.close();
			// here we will put the stage where the game is played
		});
		
		win_stage.setResizable(false);

		win_stage.showAndWait();
	}
		
	
}
