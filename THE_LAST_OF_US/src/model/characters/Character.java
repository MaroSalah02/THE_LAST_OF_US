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
	
	public void onCharacterDeath() {
		Point L =this.getLocation();
		if (this.getCurrentHp()==0) {
			if(this instanceof Hero) {
				Hero h = (Hero) this;
				int i = Game.heroes.indexOf(h);
				Game.heroes.remove(i);
			}
			if(this instanceof Zombie) {
				Zombie z = (Zombie) this;
				int j=Game.zombies.indexOf(z);
				Game.zombies.remove(j);
			}
			Point L =this.getLocation();
			Game.map[(int)L.getX()][(int)L.getY()]= new CharacterCell(null);
		}		
		
		}
		
	
	
	
	public boolean adjacentTarget() {
		Point pos1 = this.getLocation();
		int X1 = (int) pos1.getX();
		int Y1 = (int) pos1.getY();
		
		Point pos2 = (this.getTarget()).getLocation();
		int X2 = (int) pos2.getX();
		int Y2 = (int) pos2.getY();
		
		if ((X1-X2<=1&&X1-X2>=-1)&&(Y1-Y2<=1&&Y1-Y2>=-1)&&(this!=this.getTarget()))
			return true;
		else
			return false;
		
	}
	
	
	public void attack() throws GameActionException {
		
		if(this.getTarget() == null)
			throw new InvalidTargetException();
		
		if(!this.adjacentTarget()) {
			throw new InvalidTargetException();
		}
		
		if((this instanceof Hero)&&(this.getTarget() instanceof Hero)) {
			throw new InvalidTargetException();
		}
		
		if((this instanceof Zombie)&&(this.getTarget() instanceof Zombie)) {
			throw new InvalidTargetException();
		}
		
		this.getTarget().setCurrentHp(this.getTarget().getCurrentHp()-this.getAttackDmg());
		
		target.defend(this);

		target.onCharacterDeath();
		this.onCharacterDeath();
	}
	
	public void defend(Character attacker) {
		attacker.setCurrentHp(attacker.getCurrentHp()-(this.getAttackDmg())/2);
	}
	
}