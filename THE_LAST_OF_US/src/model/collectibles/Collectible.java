package model.collectibles;
import model.characters.Hero;
import java.util.ArrayList;

import exceptions.GameActionException;

public interface Collectible {
	 public void pickUp(Hero h) throws GameActionException;
	 public void use(Hero h) throws GameActionException;
}
