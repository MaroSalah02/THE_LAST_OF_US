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
	public ZombieGUI(double x, double y,Zombie zomb) {
		super("Zombie.png");
		this.zomb= zomb;
		relocateZombie(x,y);
		setOnMouseClicked(e->{
			System.out.println(super.isVisibile());
			if(super.isVisibile()){
				Main.target = this;
				Main.current.hero.setTarget(zomb);
				SideBar.addTargetSideBar();
			}
		});
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
