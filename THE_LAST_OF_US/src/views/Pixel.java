package views;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pixel extends Rectangle{
	private CCTGUI any = null;

	public CCTGUI getAny(){
		return any;
	}
	
	public void setAny(CCTGUI any2){
		any = any2;
	}
	
	public boolean hasAny(){
		return any !=null;
	}
	private boolean isVisible;
	public Pixel(boolean light, int x, int y){
		setWidth(Main.blockSize);
		setHeight(Main.blockSize);
		relocate(x*(Main.blockSize),y*Main.blockSize);
		
		
	}
}
