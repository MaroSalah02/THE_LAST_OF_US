package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import model.collectibles.Supply;

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
			Supply s=new Supply();
			s.use(m);
			m.setSpecialAction(true);
		}
		catch(InvalidTargetException i) {
			System.out.println(" Error Invalid Target.");
		}
	
	}
	
}
