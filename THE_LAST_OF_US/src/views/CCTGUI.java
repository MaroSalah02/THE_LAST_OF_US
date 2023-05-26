package views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CCTGUI extends StackPane{
	
	
	private Image spriteImage;
	private ImageView spriteImageView;
	private boolean isVisible;
	public void setVisibilty(boolean visibilty,int x, int y){
		if(!(Main.mapCells[x][y].getAny() instanceof TrapsGUI)){
			spriteImageView.setVisible(visibilty);
		}
	}
	public boolean isVisibile(){
		return isVisible;
	}
	public CCTGUI(String imageAddress){
		spriteImage = new Image(getClass().getResource(imageAddress).toExternalForm());
		spriteImageView = new ImageView(spriteImage);
		double desiredWidth = Main.blockSize-4;  // Set the desired width for the sprite
		double desiredHeight = Main.blockSize-4; // Set the desired height for the sprite
		spriteImageView.setFitWidth(desiredWidth);
		spriteImageView.setFitHeight(desiredHeight);
		isVisible =true;
		getChildren().add(spriteImageView);
		
		
	}
	
	

}