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
				map[x][y] = null;
			}
		}
		// spawn 5 traps randomly around the map
		int x;
		int y;
		for(int i = 0; i<5; i++) {
			do {
				x = ((int)(Math.random()*15));
				y = ((int)(Math.random()*15));
			}while(map[x][y] != null);
			map[x][y] = new TrapCell();
		}
		// To do : spawn 10 zombies randomly around the map
		// To do : add the hero to the controllable heroes pool and removing from the availableHeroes
		// To do : finally allocating the hero to the bottom left corner of the map.
		
		
	}
	
//	public static boolean checkWin() {
//		// To do : This method checks the win conditions for the game.
//	}
//	public static boolean checkGameOver() {
//		// To do : This method checks the conditions for the game to end 
//	}
	public static void endTurn() {
		
	}
}

