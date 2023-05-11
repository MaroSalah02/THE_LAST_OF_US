package engine;


import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
import model.characters.*;
import model.characters.Character;
import model.collectibles.*;
import model.world.*;
import exceptions.*;

public class Game {
	public static ArrayList<Hero>availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell [][] map = new Cell[15][15];
	

	public static void loadHeroes(String filePath) throws IOException {

		
        BufferedReader br = new BufferedReader(new FileReader(filePath));
		
        String currentLine;
        
        while((currentLine=br.readLine()) != null)
        {
            String[] hero = currentLine.split(",");

            switch (hero[1]) {
            case "FIGH":availableHeroes.add(new Fighter(hero[0],Integer.parseInt(hero[2]),Integer.parseInt(hero[4]),Integer.parseInt(hero[3])));break;
            case "EXP":availableHeroes.add(new Explorer(hero[0],Integer.parseInt(hero[2]),Integer.parseInt(hero[4]),Integer.parseInt(hero[3])));break;
            case "MED":availableHeroes.add(new Medic(hero[0],Integer.parseInt(hero[2]),Integer.parseInt(hero[4]),Integer.parseInt(hero[3])));break;
            }
        }
    }
	
	public static void setAdjacentVisible(Point pos) {
		int X = (int) pos.getX();
		int Y = (int) pos.getY();
		
		map[X][Y].setVisible(true);
		
		if(X>0) {
			(map[X-1][Y]).setVisible(true);
			if(Y>0)
				(map[X-1][Y-1]).setVisible(true);
			if(Y<14)
			(map[X-1][Y+1]).setVisible(true);
		}
			
		if(X<14) {
			(map[X+1][Y]).setVisible(true);
			if(Y>0)
				(map[X+1][Y-1]).setVisible(true);
			if(Y<14)
				(map[X+1][Y+1]).setVisible(true);
		}
		
		if(Y>0)
			(map[X][Y-1]).setVisible(true);
		if(Y<14)
			(map[X][Y+1]).setVisible(true);
		
	}
	
	public static void startGame(Hero h) {
		// set the cells of the map to their default values
		for(int x =0;x<=14; x++) {
			for(int y =0; y<=14; y++) {
				map[x][y] = new CharacterCell(null);
			}
		}
		
		//add the hero to the controllable heroes pool and removing from the availableHeroes
		availableHeroes.remove(h);
		heroes.add(h);
		//finally allocating the hero to the bottom left corner of the map.
		map[0][0] = new CharacterCell(h);
		h.setLocation(new Point(0,0));
		setAdjacentVisible(new Point(0,0));
		/*spawn 10 zombies randomly around the map (old)
		cc = 0;
		do {
			x = ((int)(Math.random()*15));
			y = ((int)(Math.random()*15));
			if (map[x][y] instanceof CharacterCell) {
				if(((CharacterCell) (map[x][y])).getCharacter() == null && x!=0 && y!=0) {
					cc+=1;
					Zombie z =new Zombie();
					map[x][y] = new CharacterCell(z);
					z.setLocation(new Point(x,y));
					zombies.add(z);
				}
			}
		}while(cc !=10);*/
		
		//Spawning 10 Zombies (new)
		for(int i = 0; i < 10; i++)
			spawnZombie();
		
		// spawn 5 traps randomly around the map
		int x;
		int y;
		int count = 0;
		do {
			x = ((int)(Math.random() * 15));
			y = ((int)(Math.random() * 15));
			if (map[x][y] instanceof CharacterCell) {
				if(((CharacterCell) (map[x][y])).getCharacter() == null) {
					count+=1;
					map[x][y] = new TrapCell();
				}
			}
				}while(count < 5);
		
		// spawn the necessary Collectibles
		//Supply
		count = 0;
		do {
			x = ((int)(Math.random() * 15));
			y = ((int)(Math.random() * 15));
			if (map[x][y] instanceof CharacterCell) {
				if(((CharacterCell) (map[x][y])).getCharacter() == null) {
					count += 1;
					map[x][y] = new CollectibleCell(new Supply());
				}
			}
		}while(count < 5);
		//Vaccine
		count = 0;
		do {
			x = ((int)(Math.random()*15));
			y = ((int)(Math.random()*15));
			if (map[x][y] instanceof CharacterCell) {
				if(((CharacterCell) (map[x][y])).getCharacter() == null) {
					count += 1;
					map[x][y] = new CollectibleCell(new Vaccine());

				}
			}
		}while(count < 5);
		
		//updating visibility of bottom left corner after all cells are set to prevent clashes 
		setAdjacentVisible(new Point(0,0));
	}

	public static boolean checkWin() {
		//if the game is over and there are vaccines on the board, the player lost
		for(int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++)
			{
				if ((map[x][y] instanceof CollectibleCell) && ((CollectibleCell)map[x][y]).getCollectible() instanceof Vaccine)
					return false;
			}
		}
		
		//Checking if the player has unused vaccines, since they cannot win if they have any
		int cnt = 0;
		for(int i = 0; i < heroes.size(); i++)
			cnt += (heroes.get(i).getVaccineInventory()).size();
		if (cnt>0)
			return false;
		
		//if there are 5 or more heroes on the board when there are no remaining vaccines, the heroes win
		return (heroes.size() >= 5);
	}
	
	public static boolean checkGameOver() {
		//Check if all heroes are dead
		if(heroes.isEmpty())
			return true;
		//Checks if there are any uncollected vaccines
		for(int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++)
			{
				if ((map[x][y] instanceof CollectibleCell) && ((CollectibleCell)map[x][y]).getCollectible() instanceof Vaccine)
					return false;
			}
		}
		//Check if the Heroes have any vaccines
		int count = 0;
		for(int i = 0; i < heroes.size(); i++)
			count += (heroes.get(i).getVaccineInventory()).size();
		if (count>0)
			return false;
		//if all the tests fail, the game has ended
		return true;
	}
	
	//Check if this cell contains a hero
	public static boolean isHero(int x, int y) {
		return ((map[x][y] instanceof CharacterCell) && ((CharacterCell)(map[x][y])).getCharacter() instanceof Hero);
	}
	
	//if the given coordinates are valid and contain a hero, adds that hero to a given arrayList
	public static void addHeroToHerosArroundMe(int x, int y, ArrayList<Hero> HeroesAroundMe) {
		if(x < 15 && x >= 0 && y < 15 && y >= 0 && isHero(x,y)) {
			HeroesAroundMe.add((Hero)((CharacterCell)(map[x][y])).getCharacter());
		}
	}
	
	
	public static void endTurn() throws InvalidTargetException, NotEnoughActionsException {

		//The time for zombies to give some payback, calls attack method for every zombie alive in the game
		for(int i = 0; i < zombies.size(); i++) {
				Zombie zomb = zombies.get(i);
				zomb.attack();

		}
		
		//resets actionPoints, specialAction and target for all heroes that are alive
		for(int i = 0; i < heroes.size(); i++) {
			Hero H2 = heroes.get(i);
			H2.setActionsAvailable(H2.getMaxActions());
			H2.setSpecialAction(false);
			H2.setTarget(null);
		}
	
		//Spawns a zombie on the board at the end of the turn
		spawnZombie();
			
		//turning the entire board invisible then making only the cells adjacent to heroes visible
		for (int x = 0; x <= 14; x++)
			for(int y = 0; y <= 14; y++)
				map[x][y].setVisible(false);
				
		for (int i = 0; i < heroes.size(); i++)
			setAdjacentVisible(heroes.get(i).getLocation());
		}
		
	//A method that spawns a zombie in a random empty cell
	public static void spawnZombie()
	{
		Zombie z =new Zombie();
		while(true) {
			int x = ((int)(Math.random()*15));
			int y = ((int)(Math.random()*15));
			if ((map[x][y] instanceof CharacterCell) && ((CharacterCell) (map[x][y])).getCharacter() == null) {
					map[x][y] = new CharacterCell(z);
					z.setLocation(new Point(x,y));
					zombies.add(z);
					return;
			}
		}
	}
}

