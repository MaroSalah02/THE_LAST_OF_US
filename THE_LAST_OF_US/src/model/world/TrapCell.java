package model.world;


public class TrapCell extends Cell {
	private int trapDamage;
	
	public TrapCell() {
		super();
		this.trapDamage=getrandom();
	}
	
	public int getTrapDamage() {
		return trapDamage;
	}
	
	public int getrandom() {
		return ((int)(Math.random() * 3 + 1)) * 10;
		}
	}

