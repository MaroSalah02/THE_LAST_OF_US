package model.characters;
import java.util.*;
import java.awt.Point;
import model.collectibles.*;
import exceptions.*;
import engine.Game;
import model.world.*;
//HERO IS ABSTRACT
abstract public class Hero extends Character{

	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;
	
	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		vaccineInventory = new ArrayList<Vaccine>();
		supplyInventory = new ArrayList<Supply>();
	}

	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}
	
	
	public void move(Direction d) throws NotEnoughActionsException, MovementException {
			if (this.actionsAvailable == 0)
				throw new NotEnoughActionsException("Not enough Action point to move");
			
			Point posOld  = new Point(this.getLocation());
			Point posNew  = new Point(this.getLocation());
			
			switch(d) {
			case UP: posNew.translate(1, 0);break;
			case DOWN: posNew.translate(-1, 0);break;
			case RIGHT: posNew.translate(0, 1);break;
			case LEFT: posNew.translate(0, -1);break;
			}
			
			if(posNew.x < 0 || posNew.y<0 || posNew.x > 14 || posNew.y > 14)
				throw new MovementException("Cannot move out of the map");
			
			Cell newCell = Game.map[posNew.x][posNew.y];
			Collectible collectible;
			
			if(newCell instanceof CharacterCell)
				if (((CharacterCell)newCell).getCharacter()!=null)
					throw new MovementException("Cannot move to an occupied cell");
			
			if(newCell instanceof CollectibleCell) {
				collectible = ((CollectibleCell)newCell).getCollectible();
				collectible.pickUp(this);
			}
			
			if(newCell instanceof TrapCell) {
				this.setCurrentHp(this.getCurrentHp() - ((TrapCell)newCell).getTrapDamage());
				this.onCharacterDeath();
			}
			
			Game.map[posOld.x][posOld.y] = new CharacterCell(null);
			
			this.setLocation(posNew);
			
			if (this.getCurrentHp() != 0) {
				Game.map[posNew.x][posNew.y] = new CharacterCell(this);
				Game.setAdjacentVisible(this.getLocation());
			}
			else{
				Game.map[posNew.x][posNew.y] = new CharacterCell(null);
				}
	
		
		this.actionsAvailable = this.actionsAvailable - 1;
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		super.attack();
		
		if(this instanceof Fighter) {
			if(!this.isSpecialAction()) {
				if(this.actionsAvailable == 0)
					throw new NotEnoughActionsException("Not have Enough Action points");
				this.actionsAvailable = this.actionsAvailable -1;
			}
		}
		else {
			if(this.actionsAvailable == 0)
				throw new NotEnoughActionsException("Not have Enough Action points");
			this.actionsAvailable = this.actionsAvailable -1;
		}

	}
	
	public void cure() throws InvalidTargetException ,NotEnoughActionsException, NoAvailableResourcesException {
		if(this.getTarget() == null) {
			throw new InvalidTargetException("Select a target");
		}
		
		if(this.getTarget() instanceof Hero ) {
			throw new InvalidTargetException("Can only cure zombies"); 
		}
		
		if(!this.adjacentTarget()) {
			throw new InvalidTargetException("Can only cure adjacent zombies");
		}
		
		if(this.vaccineInventory.isEmpty()) {
			throw new NoAvailableResourcesException("Not enough vaccines");
		}
		
		if(this.getVaccineInventory().size() == 0) {
			throw new NoAvailableResourcesException("Not enough vaccines");
		}
		
		if(this.getActionsAvailable() == 0) {
			throw new NotEnoughActionsException("Not enough action Points");
		}
		
		Vaccine vaccine = this.getVaccineInventory().get(0);
		
		vaccine.use(this);	
}

public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException{
		
		if(this.supplyInventory.isEmpty()) {
			throw new NoAvailableResourcesException("Not enough supplies");
		}
	}
}
