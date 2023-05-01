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
	
	
	public void onCharacterDeath() {
		if (this.getCurrentHp()==0) {
			for(int i=0;i<15;i++) {
				for(int j=0;j<15;j++) {
					if(this instanceof Hero) {
						
					}
				}
			}
		}
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
	
	
	
}
