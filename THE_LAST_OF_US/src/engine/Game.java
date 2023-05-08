package engine;


import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
import model.characters.*;
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
		// set the map
		for(int x =0;x<=14; x++) {
			for(int y =0; y<=14; y++) {
				map[x][y] = new CharacterCell(null);
			}
		}
		// spawn the necessary Collectibles
		int x;
		int y;
		int cc = 0;
		do {
			x = ((int)(Math.random()*15));
			y = ((int)(Math.random()*15));
			if (map[x][y] instanceof CharacterCell) {
				if(((CharacterCell) (map[x][y])).getCharacter() == null && x!=0 && y!=0) {
					cc+=1;
					map[x][y] = new CollectibleCell(new Supply());

				}
			}
		}while(cc !=4);
		
		cc = 0;
		do {
			x = ((int)(Math.random()*15));
			y = ((int)(Math.random()*15));
			if (map[x][y] instanceof CharacterCell) {
				if(((CharacterCell) (map[x][y])).getCharacter() == null && x!=0 && y!=0) {
					cc+=1;
					map[x][y] = new CollectibleCell(new Vaccine());

				}
			}
		}while(cc !=4);
		
		
		// spawn 5 traps randomly around the map
		
		cc = 0;
		do {
			x = ((int)(Math.random()*15));
			y = ((int)(Math.random()*15));
			if (map[x][y] instanceof CharacterCell && x!=0 && y!=0) {
				if(((CharacterCell) (map[x][y])).getCharacter() == null) {
					cc+=1;
					map[x][y] = new TrapCell();
				}
			}
		}while(cc !=4);
		//spawn 10 zombies randomly around the map
		cc = 0;
		do {
			x = ((int)(Math.random()*15));
			y = ((int)(Math.random()*15));
			if (map[x][y] instanceof CharacterCell) {
				if(((CharacterCell) (map[x][y])).getCharacter() == null && x!=0 && y!=0) {
					cc+=1;
					map[x][y] = new CharacterCell(new Zombie());
					Zombie z =new Zombie();
					z.setLocation(new Point(x,y));
					zombies.add(z);
				}
			}
		}while(cc !=9);
		//add the hero to the controllable heroes pool and removing from the availableHeroes
		heroes.add(h);
		availableHeroes.remove(h);
		
		//finally allocating the hero to the bottom left corner of the map.
		map[0][0] = new CharacterCell(heroes.get(0));
		heroes.get(0).setLocation(new Point(0,0));
		setAdjacentVisible(new Point(0,0));
		
	}

	public static boolean checkWin() {
		for(int x = 0; x<15; x++) {
			for (int y = 0; y<15; y++)
			{
				if ((map[x][y] instanceof CollectibleCell)&&((CollectibleCell)map[x][y]).getCollectible() instanceof Vaccine)
					return false;
			}
		}
		int cnt = 0;
		for(int i = 0; i < heroes.size(); i++)
			cnt += (heroes.get(i).getVaccineInventory()).size();
		if (cnt>0)
			return false;
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
	
// Main Method added for debugging, remove later
/*	public static void main(String[]args) throws GameActionException{
		availableHeroes.add(new Fighter("Hamza",100,10,3));
		
		startGame(new Fighter("Hamza",100,10,3));		
		for (int y = 0; y <= 14; y++) {
			System.out.print("[");
			for (int x = 0; x<=14; x++) {
				System.out.print(map[x][y] + ", ");
			}
			System.out.println("]");
		}
		
		//System.out.print((((CharacterCell)map[0][0]).getCharacter()).getLocation());
		System.out.println(heroes.get(0).getLocation());
		heroes.get(0).move(Direction.UP);
		System.out.println(heroes.get(0).getLocation());
		heroes.get(0).move(Direction.DOWN);
		heroes.get(0).move(Direction.RIGHT);
		System.out.print((((CharacterCell)map[1][0]).getCharacter()));
	}*/
}

