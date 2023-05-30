package views;
import javafx.scene.Group;
import model.characters.Zombie;
import views.Main.*;
import views.SideBar.*;
public class ZombieGUI extends CharacterGUI{
	
	private double currX;
	private double currY;
	Zombie zomb;
	
	public double getCurrXBlocks(){
		return currX/Main.blockSize;
	}
	public double getCurrYBlocks(){
		return currY/Main.blockSize;
	}
	public ZombieGUI(int x, int y,Zombie zomb) {
		super("Zombie.png");
		this.zomb= zomb;
		relocateZombie(x,y);
		setOnMouseClicked(e->{
			
			if(Main.mapCells[x][y].isVisible()){
				Main.target = this;
				Main.current.hero.setTarget(zomb);
				Main.sideBar.addTargetSideBar();
			}
		});
		if(zomb.getCurrentHp() == 0 ){
			removeZombie();
		}
	}
	public void relocateZombie(double x,double y){
		currX =	x*Main.blockSize;
		currY = y*Main.blockSize;
    	relocate(currX,currY);

	}
	public void removeZombie(){
		Group parentOfHeroGUI = (Group) this.getParent();
		parentOfHeroGUI.getChildren().remove(this);
	}
	
	
}
