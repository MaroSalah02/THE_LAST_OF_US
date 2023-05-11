package model.characters;

import engine.Game;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

import model.collectibles.*;

public class Fighter extends Hero{

	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		super.useSpecial();
		
		Supply supply = this.getSupplyInventory().get(0);
		supply.use(this);
		this.setSpecialAction(true);
	}
}
