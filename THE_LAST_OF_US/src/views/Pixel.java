package views;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pixel extends Rectangle{
	private CCTGUI any = null;

	public void setVisibilty(boolean visibilty){
		if((any != null)){
			any.setVisible(visibilty);
		}
		isVisiblePixel = visibilty;
		if(any instanceof TrapsGUI){
			any.setVisible(false);
			isVisiblePixel = false;
		}
		if(visibilty){
			this.setFill(Color.BLACK);
			this.setOpacity(0.1);
		}else{
			this.setFill(Color.BLACK);
			this.setOpacity(0.8);


		}

	}
	public CCTGUI getAny(){
		return any;
	}
	
	public void setAny(CCTGUI any2){
		any = any2;
	}
	
	public boolean hasAny(){
		return any !=null;
	}
	private boolean isVisiblePixel;
	public Pixel(boolean light, int x, int y){
		setWidth(Main.blockSize);
		setHeight(Main.blockSize);
		setVisibilty(true);
		if(any instanceof TrapsGUI){
			any.setVisible(false);
			isVisiblePixel = false;
		}
		relocate(x*(Main.blockSize),y*Main.blockSize);
		
		
	}
	public void checkForSupplyAndVaccineAndTrapThenRemove(){
		if (any instanceof SupplyGUI || any instanceof VaccineGUI){
			any.setVisible(false);
			any = null;
		}else{
			if(any instanceof TrapsGUI){
				any = null;
	    		Exceptionspopup idk = new Exceptionspopup("You have fallen into a trap");
			}
		}
		
	}
}
