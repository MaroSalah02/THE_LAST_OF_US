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
		ArrayList<Vaccine> vaccinoArray = h.getVaccineInventory();
		vaccinoArray.remove(this);
		if(h.getActionsAvailable()==0) {
			throw new NoAvailableResourcesException();
		}
		h.setActionsAvailable(h.getActionsAvailable()-1);
		
		Point p = h.getTarget().getLocation();
		
		Hero hnew = Game.availableHeroes.remove(Game.availableHeroes.size()-1);
		Game.heroes.add(hnew);
		
		Game.zombies.remove(h.getTarget());
		
		h.setTarget(h);
		Game.map[p.x][p.y] = new CharacterCell(hnew);
		hnew.setLocation(p);
	}
	
}
