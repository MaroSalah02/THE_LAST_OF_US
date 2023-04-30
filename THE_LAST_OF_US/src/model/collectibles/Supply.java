package model.collectibles;
import model.characters.Hero;
import java.util.ArrayList;

import exceptions.GameActionException;
import exceptions.NoAvailableResourcesException;
public class Supply implements Collectible {
	public Supply(){
		
	}

	@Override
	public void pickUp(Hero h) {
		Supply Sup = new Supply();
		ArrayList<Supply> supArray = h.getSupplyInventory();
		supArray.add(Sup);
		
	}

	@Override
	public void use(Hero h) throws NoAvailableResourcesException{
		try {
			ArrayList<Supply> supArray = h.getSupplyInventory();
			if(supArray.size() == 0) {
				throw new NoAvailableResourcesException();
			}
			supArray.remove(supArray.size() - 1);
		}
		catch(NoAvailableResourcesException err){
			System.out.println("Sir, You don't have any Supply left");
		}
		
	}
}
