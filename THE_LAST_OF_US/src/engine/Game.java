package engine;


import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
import model.characters.*;
import model.collectibles.*;
import model.world.*;

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
		
		if(X!=0) {
			(map[X-1][Y]).setVisible(true);
			if(Y!=0)
				(map[X-1][Y-1]).setVisible(true);
			if(Y!=14)
				(map[X-1][Y+1]).setVisible(true);
		}
			
		if(X!=14) {
			(map[X+1][Y]).setVisible(true);
			if(Y!=0)
				(map[X+1][Y-1]).setVisible(true);
			if(Y!=14)
				(map[X+1][Y+1]).setVisible(true);
		}
		
		if(Y!=0)
			(map[X][Y-1]).setVisible(true);
		if(Y!=14)
			(map[X][Y+1]).setVisible(true);
		
	}
	public static void startGame(Hero h) {
		// spawn the necessary Collectibles
		ArrayList<Supply> SupArray = h.getSupplyInventory();
		ArrayList<Vaccine> vaccinoArray =  h.getVaccineInventory();
		for(int i =0; i<5; i++) {
			SupArray.add(new Supply());
			vaccinoArray.add(new Vaccine());
		}
		for(int x =0;x<14; x++) {
			for(int y =0; y<14; y++) {
				map[x][y] = new CharacterCell(null);
			}
		}
		// spawn 5 traps randomly around the map
		int x;
		int y;
		int cc = 0;
			do {
				x = ((int)(Math.random()*15));
				y = ((int)(Math.random()*15));
				if (map[x][y] instanceof CharacterCell && x!=0 && y!=0) {
					if(((CharacterCell) (map[x][y])).getCharacter() != null) {
						cc+=1;
						map[x][y] = new TrapCell();
					}
				}
			}while(cc !=5);
		//spawn 10 zombies randomly around the map
		cc = 0;
		do {
			x = ((int)(Math.random()*15));
			y = ((int)(Math.random()*15));
			if (map[x][y] instanceof CharacterCell) {
				if(((CharacterCell) (map[x][y])).getCharacter() != null && x!=0 && y!=0) {
					cc+=1;
					map[x][y] = new CharacterCell(new Zombie());
					zombies.add(new Zombie());
				}
			}
		}while(cc !=10);
		//add the hero to the controllable heroes pool and removing from the availableHeroes
		int mcindex = (int)(Math.random()*(availableHeroes.size()));
		heroes.add(availableHeroes.get(mcindex));
		availableHeroes.remove(mcindex);
		
		//finally allocating the hero to the bottom left corner of the map.
		map[0][0] = new CharacterCell(heroes.get(0));
		
	}

	public static boolean checkWin() {
		return (heroes.size()>=5);
	}
	public static boolean checkGameOver() {
		// To do : This method checks the conditions for the game to end 
		if(heroes.isEmpty())
			return true;
		//Checks if there are any uncollected vaccines
		for(int x = 0; x<15; x++) {
			for (int y = 0; y<15; y++)
			{
				if ((map[x][y] instanceof CollectibleCell)&&((CollectibleCell)map[x][y]).getCollectible() instanceof Vaccine)
					return false;
			}
		}
		//Check if the Heroes have any vaccines
		int cnt = 0;
		for(int i = 0; i < heroes.size(); i++)
			cnt += (heroes.get(i).getVaccineInventory()).size();
		if (cnt>0)
			return false;
		//if all the tests fail, the game has ended
		return true;
	}
	
	public static void endTurn() {
		
	}
}

