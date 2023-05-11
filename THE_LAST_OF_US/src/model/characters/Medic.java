package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;

public class Medic extends Hero{

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}

	public void useSpecial() throws InvalidTargetException, NoAvailableResourcesException {
		if(this.getTarget() == null)
			throw new InvalidTargetException();
			
		if(this.getTarget() instanceof Zombie) {
			throw new InvalidTargetException();
		}
			
		if(!this.adjacentTarget()) {
			throw new InvalidTargetException();
		}
			
		super.useSpecial();
			
		Supply supply =this.getSupplyInventory().get(0);
		supply.use(this);
			
		this.getTarget().setCurrentHp(this.getTarget().getMaxHp());
		this.setSpecialAction(true);
	}
}
	

