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
	public  void use(Hero h){
		try {
		ArrayList<Vaccine> vaccinoArray = h.getVaccineInventory();
		if(vaccinoArray.size() == 0) {
			throw new NoAvailableResourcesException();
		}
		vaccinoArray.remove(vaccinoArray.size() - 1);
	}
	catch(NoAvailableResourcesException err){
		System.out.println("Sir, You don't have any Vaccine left");
	}
	}
}
