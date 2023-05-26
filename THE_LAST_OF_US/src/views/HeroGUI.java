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
	    	if (Main.mapCells[x][y].getAny() instanceof VaccineGUI || Main.mapCells[x][y].getAny() instanceof SupplyGUI){
	    		Group parentOfCollectiblesGUI = (Group) Main.mapCells[x][y].getAny().getParent();
	    		parentOfCollectiblesGUI.getChildren().remove(Main.mapCells[x][y].getAny());

	    	}
	    	if (Main.mapCells[x][y].getAny() instanceof TrapsGUI){
	    		Group parentOfTraps = (Group) Main.mapCells[x][y].getAny().getParent();
	    		parentOfTraps.getChildren().remove(Main.mapCells[x][y].getAny());
	    		Exceptionspopup idk = new Exceptionspopup("You have fallen into a trap");
	    	}
	    	
	    	SideBar.updateValues();
	    	relocate(prevX,prevY);
		}
		catch(MovementException e){
            Exceptionspopup a = new Exceptionspopup(e.getMessage());
        }catch(NotEnoughActionsException e){
            Exceptionspopup a = new Exceptionspopup(e.getMessage());
        }
		Main.checkVisibility();
    	
    	
    }
	public void removeHero(){
		//Group parentOfHeroGUI = (Group) this.getParent();
		//parentOfHeroGUI.getChildren().remove(this);

		Main.GUIs.remove(this);
		Main.checkVisibility();
	}
	

}
