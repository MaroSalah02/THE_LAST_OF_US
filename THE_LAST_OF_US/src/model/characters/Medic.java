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

	public void useSpecial() throws NoAvailableResourcesException, NotEnoughActionsException, InvalidTargetException {
			super.useSpecial();
			Medic m=(Medic)this;
			if(m.getTarget() instanceof Zombie) {
				throw new InvalidTargetException();
			}
			Supply s=new Supply();
			s.use(m);
			m.setSpecialAction(true);

		}

	

	}
	

