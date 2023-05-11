package model.characters;
import java.awt.Point;
import java.util.ArrayList;
import engine.Game;
import exceptions.*;
import model.world.*;
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
	
	//checks if a character is dead, and executes the required tasks if it is
	public void onCharacterDeath() {
		if (this.getCurrentHp() == 0) {
			if(this instanceof Hero) {
				Game.heroes.remove(this);
			}
			
			if(this instanceof Zombie) {
				Game.zombies.remove(this);
				Game.spawnZombie();
			}	
			
		Point L =this.getLocation();
		Game.map[L.x][L.y]= new CharacterCell(null);
		}
	}
	
	//Checks if the character and it's target are adjacent
	public boolean adjacentTarget() {
		Point pos1 = this.getLocation();
		int X1 = pos1.x;
		int Y1 = pos1.y;
		
		Point pos2 = (this.getTarget()).getLocation();
		int X2 = pos2.x;
		int Y2 = pos2.y;
		
		if (((X1 - X2 <= 1) && (X1 - X2 >= -1)) && ((Y1-Y2 <= 1) && (Y1 - Y2 >= -1)))
			return true;
		else
			return false;
		
	}
	
	//General Attack method for characters
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		if(this.getTarget() == null)
			throw new InvalidTargetException();
		
		if((this instanceof Hero) && (this.getTarget() instanceof Hero)) {
			throw new InvalidTargetException();
		}
		
		if((this instanceof Zombie) && (this.getTarget() instanceof Zombie)) {
			throw new InvalidTargetException();
		}
		
		if(!this.adjacentTarget()) {
			throw new InvalidTargetException();
		}
		
		this.getTarget().setCurrentHp(this.getTarget().getCurrentHp()-this.getAttackDmg());
		
		target.defend(this);
		
		target.onCharacterDeath();
		this.onCharacterDeath();
		
		if(this.getTarget().getCurrentHp()==0)
			this.setTarget(null);
	}
	
	//General Defend method for character
	public void defend(Character attacker) {
		attacker.setCurrentHp(attacker.getCurrentHp()-(this.getAttackDmg())/2);
	}
	
}