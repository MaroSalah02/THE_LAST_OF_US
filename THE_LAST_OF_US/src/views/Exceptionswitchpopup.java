package views;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
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
import javafx.util.Duration;
public class Exceptionswitchpopup {
	
	public Exceptionswitchpopup() {
		Stage cure_popup_stage=new Stage();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			cure_popup_stage.close();
        }));
        timeline.play();
		
		Image imge=new Image(getClass().getResourceAsStream("download.jpeg"));
		
		cure_popup_stage.getIcons().add(imge);

		BorderPane InvalidTargetExceptioncurepopup=new BorderPane();
		
		HBox hor=new HBox();
		
		InvalidTargetExceptioncurepopup.setCenter(hor);
		
		Scene sce=new Scene(InvalidTargetExceptioncurepopup,500,300);	
		
		cure_popup_stage.setScene(sce);
		
		Label label1=new Label("Invalid Switch");
		
		label1.setFont(Font.font("Impact", FontWeight.SEMI_BOLD, FontPosture.ITALIC,20));
		
		hor.setAlignment(Pos.CENTER);
		
		hor.getChildren().addAll(label1);
		
		cure_popup_stage.initModality(Modality.APPLICATION_MODAL);	
		
		cure_popup_stage.setOnCloseRequest(c->{
			cure_popup_stage.close();
		});
		
		cure_popup_stage.setOnHiding(d->{
			cure_popup_stage.close();
		});
		
		cure_popup_stage.setResizable(false);

		cure_popup_stage.showAndWait();
	}
}
