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
				
		int zx = zombLoc.x;
		int zy = zombLoc.y;
		
		ArrayList<Hero> HerosArroundMe = new ArrayList<Hero>();		
						
		Game.addHeroToHerosArroundMe(zx+1,zy+1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx+1,zy,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx+1,zy-1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx,zy+1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx,zy-1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx-1,zy+1,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx-1,zy,HerosArroundMe);
		Game.addHeroToHerosArroundMe(zx-1,zy-1,HerosArroundMe);
		
		if(!HerosArroundMe.isEmpty()) {
				//chose an unfortunate hero as the target
				int i = (int)(Math.random() * (HerosArroundMe.size()));
				this.setTarget(HerosArroundMe.remove(i));
				//attack and defend cycle
				this.getTarget().setCurrentHp(this.getTarget().getCurrentHp()-this.getAttackDmg());
				this.getTarget().defend(this);
				//check casualties
				this.getTarget().onCharacterDeath();
				this.onCharacterDeath();
		}
		//reset target
		this.setTarget(null);		
	}
}
