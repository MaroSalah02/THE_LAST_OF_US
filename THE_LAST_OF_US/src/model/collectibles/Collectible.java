package model.collectibles;
import model.characters.Hero;
import java.util.ArrayList;

import exceptions.GameActionException;

public interface Collectible {
	 public void pickUp(Hero h);
	 public void use(Hero h);
}
