package model.characters;
import java.util.*;
import java.awt.Point;
import model.collectibles.*;
import exceptions.*;
import engine.Game;
import model.world.*;

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

	public void cure() throws InvalidTargetException ,NotEnoughActionsException,NoAvailableResourcesException {
			if(this.getTarget() == null) {
				throw new InvalidTargetException();
			}
			if(this.vaccineInventory.isEmpty()) {
				throw new NoAvailableResourcesException();
			}
			if(this.getTarget() instanceof Hero ) {
				throw new InvalidTargetException(); 
			}
			if(this.getActionsAvailable()==0) {
				throw new NotEnoughActionsException();
			}
			if(!this.adjacentTarget()) {
				throw new InvalidTargetException();
			}
			Vaccine v=new Vaccine();
			v.use(this);
			this.setActionsAvailable(this.getActionsAvailable()-1);
			Point p=this.getTarget().getLocation();
			Hero h=Game.availableHeroes.remove(Game.availableHeroes.size()-1);
			Game.heroes.add(h);
			Game.zombies.remove(this.getTarget());
			CharacterCell c=new CharacterCell(h);
			Game.map[p.x][p.y]=c;
			h.setLocation(p);
		
	}
	public void useSpecial() throws NoAvailableResourcesException , NotEnoughActionsException, InvalidTargetException{
			
			if(this.supplyInventory.isEmpty()) {
				throw new NoAvailableResourcesException();
			}
			if(this.actionsAvailable==0) {
				throw new NotEnoughActionsException();
			}
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
	
	public void move(Direction d) throws GameActionException{
			if (this.actionsAvailable == 0)
				throw new NotEnoughActionsException();
			
			Point posOld  = new Point(this.getLocation());
			Point posNew  = new Point(this.getLocation());
			
			switch(d) {
			case UP: posNew.translate(1, 0);break;
			case DOWN: posNew.translate(-1, 0);break;
			case RIGHT: posNew.translate(0, 1);break;
			case LEFT: posNew.translate(0, -1);break;
			default: throw new MovementException("invalid input.");
			}
			
			if(posNew.getX()<0||posNew.getY()<0||posNew.getX()>14||posNew.getY()>14)
				throw new MovementException();
			
			Cell newCell = Game.map[(int)posNew.getX()][(int)posNew.getY()];
				Collectible collectible;
			
			if(newCell instanceof CharacterCell)
				if (((CharacterCell)newCell).getCharacter()!=null)
					throw new MovementException();
			
			if(newCell instanceof CollectibleCell) {
				collectible = ((CollectibleCell)newCell).getCollectible();
				collectible.pickUp(this);
			}
			
			if(newCell instanceof TrapCell) {
				this.setCurrentHp(this.getCurrentHp()-((TrapCell)newCell).getTrapDamage());
				this.onCharacterDeath();
			}
			
			Game.map[(int)posOld.getX()][(int)posOld.getY()] = new CharacterCell(null);
			
			this.setLocation(posNew);
			
			Game.map[(int)posNew.getX()][(int)posNew.getY()] = new CharacterCell(this);
	
		Game.setAdjacentVisible(this.getLocation());
		
		this.actionsAvailable = this.actionsAvailable - 1;
	}

	public void attack() throws GameActionException {
		if(this.actionsAvailable == 0)
			throw new NotEnoughActionsException();
		super.attack();
		if(this instanceof Fighter) {
			if(!this.isSpecialAction())
				this.actionsAvailable = this.actionsAvailable -1;
		}
		else
			this.actionsAvailable = this.actionsAvailable -1;
		
		
		
	}
	
}
