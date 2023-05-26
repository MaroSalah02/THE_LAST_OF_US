package views;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Character extends StackPane{
	
	public double mouseX;
	public double mouseY;
	public double prevX;
	public double prevY;
	public Character(int x,int y){
		relocate(x*Main.blockSize,y*Main.blockSize);
		GUImove(x,y);
		Image spriteImage = new Image(getClass().getResource("sprite.png").toExternalForm());
		ImageView spriteImageView = new ImageView(spriteImage);
		double desiredWidth = 60;  // Set the desired width for the sprite
		double desiredHeight = 60; // Set the desired height for the sprite
		spriteImageView.setFitWidth(desiredWidth);
		spriteImageView.setFitHeight(desiredHeight);
		getChildren().add(spriteImageView);
		setOnMousePressed(e->{
			mouseX= e.getSceneX();
			mouseY= e.getSceneY();
		});
		setOnMouseDragged(e->{
			relocate(e.getSceneX()-mouseX+prevX,e.getSceneY()-mouseY+prevY);
		});
		
	}
	public void GUImove(int x,int y){
    	prevX = x*Main.blockSize;
    	prevY = y*Main.blockSize;
    	relocate(x,y);
    }

}
