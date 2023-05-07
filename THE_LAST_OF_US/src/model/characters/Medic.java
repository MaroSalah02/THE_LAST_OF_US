package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;

public class Medic extends Hero{

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	public void useSpecial() {
		super.useSpecial();
		try {
			Medic m=(Medic)this;
			if(m.getTarget() instanceof Zombie) {
				throw new InvalidTargetException();
			}
			if(!m.getTarget().adjacentTarget()) {
				throw new InvalidTargetException();
			}
			m.getSupplyInventory().remove(m.getSupplyInventory().size()-1);
			m.setSpecialAction(true);
		}
		catch(InvalidTargetException i) {
			System.out.println(" Error Invalid Target.");
		}
	
	}
	
}
