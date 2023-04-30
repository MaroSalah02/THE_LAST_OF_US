package model.collectibles;
import java.util.ArrayList;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;

public class Vaccine implements Collectible {
	public Vaccine(){
		
	}

	@Override
	public void pickUp(Hero h) {
		Vaccine vaccino = new Vaccine();
		ArrayList<Vaccine> vaccinoArray = h.getVaccineInventory();
		vaccinoArray.add(vaccino);
		
	}

	@Override
	public void use(Hero h) throws NoAvailableResourcesException{
		try {
		ArrayList<Vaccine> vaccinoArray = h.getVaccineInventory();
		vaccinoArray.remove(vaccinoArray.size() - 1);
	}catch(NullPointerException err){
		throw new NoAvailableResourcesException("Sir, You don't have any Vaccine left left");
		}
	}
}
