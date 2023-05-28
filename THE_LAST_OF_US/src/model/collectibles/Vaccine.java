package model.collectibles;
import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.NoAvailableResourcesException;
import model.characters.Hero;
import model.world.CharacterCell;

public class Vaccine implements Collectible {
	public Vaccine(){
		
	}

	@Override
	public void pickUp(Hero h) {
		ArrayList<Vaccine> vaccinoArray = h.getVaccineInventory();
		vaccinoArray.add(this);
		
	}

	@Override
	public  void use(Hero h) throws NoAvailableResourcesException{
		if(h.getActionsAvailable()==0) {
			throw new NoAvailableResourcesException();
		}
		ArrayList<Vaccine> vaccinoArray = h.getVaccineInventory();
		vaccinoArray.remove(this);
		
		h.setActionsAvailable(h.getActionsAvailable() - 1);
		
		Point location = h.getTarget().getLocation();
		
		Hero randomHero = Game.availableHeroes.remove((int)(Game.availableHeroes.size() * Math.random()));
		
		Game.heroes.add(randomHero);
		Game.map[location.x][location.y] = new CharacterCell(randomHero);
		randomHero.setLocation(location);
		
		Game.zombies.remove(h.getTarget());
		
		h.setTarget(null);
	}
	
}
