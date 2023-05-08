package model.characters;

import engine.Game;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

import model.collectibles.Supply;

public class Explorer extends Hero{

	public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	public void useSpecial() throws NoAvailableResourcesException, NotEnoughActionsException, InvalidTargetException {
		super.useSpecial();
		Explorer e=(Explorer)this;
		Supply s=new Supply();
		s.use(e);
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				Game.map[i][j].setVisible(true);
			}
		}
		e.setSpecialAction(true);
	}
	
}
