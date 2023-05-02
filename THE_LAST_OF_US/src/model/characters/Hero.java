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
	
	public void move(Direction d) {
		try {
			if (this.actionsAvailable == 0)
				throw new NotEnoughActionsException();
			
			Point pos  = new Point(this.getLocation());
			
			switch(d) {
			case UP: pos.translate(0, 1);break;
			case DOWN: pos.translate(0, -1);break;
			case RIGHT: pos.translate(1, 0);break;
			case LEFT: pos.translate(-1, 0);break;
			default: throw new MovementException("invalid input.");
			}
			
			if(pos.getX()<0||pos.getY()<0||pos.getX()>14||pos.getY()>14)
				throw new MovementException("you are trying to move out of bounds.");
			
			if(Game.map[(int)pos.getX()][(int)pos.getY()] instanceof CharacterCell)
				throw new MovementException("you are trying to move into an already occupied cell.");
			
			this.setLocation(pos);
		}
		catch(MovementException e){
			System.out.println("Invalid movement option," + e.getMessage());
		} catch (NotEnoughActionsException e) {
			System.out.println("You don't have more actoins this turn.");
		}
		
		Game.setAdjacentVisible(this.getLocation());
	}
	
	
}
