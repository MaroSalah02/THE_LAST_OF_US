package engine;


import java.awt.Point;
import java.io.*;
import java.util.ArrayList;

import model.characters.*;
import model.world.Cell;

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
	
}

