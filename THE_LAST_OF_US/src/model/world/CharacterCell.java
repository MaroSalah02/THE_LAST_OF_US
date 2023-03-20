package model.world;
import model.characters.Character;

public class CharacterCell extends Cell {
	private Character character;
	private boolean isSafe;
	
	public CharacterCell(Character character) {
		super();
		this.character=character;
	}

	public Character getCharacter() {
		return this.character;
	}
	public void setCharacter(Character character) {
		this.character = character;
	}
	public boolean isSafe() {
		return isSafe;
	}
	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}
}
