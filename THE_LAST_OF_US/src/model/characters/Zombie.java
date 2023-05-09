package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;
import engine.Game;

public class Zombie extends Character {
	private static int ZOMBIES_COUNT = 0;
	
	public Zombie()
	{
		super("Zombie " + (++ZOMBIES_COUNT), 40, 10);	
	}

	public static int getZOMBIES_COUNT() {
		return ZOMBIES_COUNT;
	}

	public static void setZOMBIES_COUNT(int zOMBIES_COUNT) {
		ZOMBIES_COUNT = zOMBIES_COUNT;
	}
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		Point zombLoc = this.getLocation();
				
		int zx = (int)zombLoc.getX();
		int zy = (int)zombLoc.getY();
		ArrayList<Hero> HerosArroundMe = new ArrayList<Hero>();		
						
		Game.addHeroToHerosArroundMe(zx+1,zy+1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx+1,zy,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx+1,zy-1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx,zy+1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx,zy-1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx-1,zy+1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx-1,zy,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx-1,zy-1,HerosArroundMe);							
		if((int) (HerosArroundMe.size()) !=0) {
				int i = (int)(Math.random()*(HerosArroundMe.size()));
				this.setTarget(HerosArroundMe.remove(i));
				Character target = this.getTarget();
				this.getTarget().setCurrentHp(this.getTarget().getCurrentHp()-this.getAttackDmg());
				
				target.defend(this);
				
				target.onCharacterDeath();
				this.onCharacterDeath();
				
		}
		this.setTarget(null);		
	}
	
}
