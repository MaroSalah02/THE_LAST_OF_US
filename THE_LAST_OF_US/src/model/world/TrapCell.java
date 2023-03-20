package model.world;
import java.util.*;

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
		Random r = new Random();
		int x=r.nextInt();
		while(x!=10||x!=20||x!=30) {
			x=r.nextInt();
		}
		return x;
	}
}
