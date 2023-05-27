package views;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import engine.Game;
import views.Main.*;
import model.characters.*;
import model.world.*;
import exceptions.*;
public class HeroGUI extends CharacterGUI{
	public Hero hero;
	private double prevX;
	private double prevY;
	
	public double getPrevX(){
		return prevX;
	}
	public double getPrevY(){
		return prevY;
	}
	public HeroGUI(int x, int y,Hero hero) {
		super("sprite.png");
		this.hero = hero;
		prevX = x*Main.blockSize;
    	prevY = y*Main.blockSize;
		relocate(prevX,prevY);		
		
		setOnMouseClicked(e->{
			if (Main.current != this){
				Main.target = this;
				Main.current.hero.setTarget(hero);
				SideBar.addTargetSideBar();
				
			}
			if (Main.current.hero instanceof Medic){
				Main.target = this;
				Main.current.hero.setTarget(hero);
				SideBar.addTargetSideBar();
				
			}
		});
		
	}
	
	public void GUImove(int x,int y,Direction d){
		
		try{
			hero.move(d);
			prevX = x*Main.blockSize;
	    	prevY = y*Main.blockSize;
	    	Main.mapCells[x][y].checkForSupplyAndVaccineAndTrapThenRemove();
			Main.checkVisibility();
	    	SideBar.updateValues();
	    	relocate(prevX,prevY);
		}
		catch(MovementException e){
            Exceptionspopup a = new Exceptionspopup(e.getMessage());
        }catch(NotEnoughActionsException e){
            Exceptionspopup a = new Exceptionspopup(e.getMessage());
        }
    	
    	
    }/*
	public void removeHero(){
		System.out.println(this.getParent());
		//parentOfHeroGUI.getChildren().remove(this);

		Main.GUIs.remove(this);
		Main.checkVisibility();
	}
	*/
	

}
