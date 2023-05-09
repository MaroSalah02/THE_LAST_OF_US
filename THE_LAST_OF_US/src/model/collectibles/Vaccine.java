package model.collectibles;
import java.util.ArrayList;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;

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
	}
	
}
