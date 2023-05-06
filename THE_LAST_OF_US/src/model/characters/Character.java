package model.characters;
import java.awt.Point;
import java.util.ArrayList;
import engine.Game;
abstract public class Character {

	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	
	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}	
		
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if (currentHp>=this.maxHp)
			this.currentHp=this.maxHp;
		else {
			if(currentHp<=0)
				this.currentHp=0;
			else
				this.currentHp = currentHp;
		}
			
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
	
	public void onCharacterDeath() {
		if (this.getCurrentHp()==0) {
			if(this instanceof Hero) {
				for(int i=0;i<Game.heroes.size();i++) {
					if((Game.heroes.get(i)).getName()==getName()) {
						Game.heroes.set(i,null);
					}
				}
			}
			else if(this instanceof Zombie) {
				for(int j=0;j<Game.zombies.size();j++) {
						if((Game.zombies.get(j)).getName()==getName()) {
							Game.zombies.set(j,null);
						}
					}
				}
			}
		Point L =this.getLocation();
		Game.map[15-L.y][L.x]=null;
		
		}
	
	public boolean adjacentTarget() {
		Point pos1 = this.getLocation();
		int X1 = (int) pos1.getX();
		int Y1 = (int) pos1.getY();
		
		Point pos2 = (this.getTarget()).getLocation();
		int X2 = (int) pos2.getX();
		int Y2 = (int) pos2.getY();
		
		if ((X1-X2<=1&&X1-X2>=-1)&&(Y1-Y2<=1&&Y1-Y2>=-1))
			return true;
		else
			return false;
		
	}
	
}
