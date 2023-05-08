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
	public static boolean isHero(int x, int y) {
		return map[x][y] instanceof CharacterCell && ((CharacterCell)(map[x][y])).getCharacter() instanceof Zombie;
	}
	public static void addHeroToHerosArroundMe(int x, int y,ArrayList<Hero> ham) {
		if(x <15 && x >= 0 && y <15 && y >= 0 && isHero(x,y)) {
			ham.add((Hero)((CharacterCell)(map[x][y])).getCharacter());
		}
	}
	public static void endTurn() {
		for(int x =0;x<=14; x++) {
			for(int y =0; y<=14; y++) {
				if(map[x][y] instanceof CharacterCell && ((CharacterCell)(map[x][y])).getCharacter() instanceof Zombie) {
					Zombie zomb = (Zombie)((CharacterCell)(map[x][y])).getCharacter();
					Point zombLoc = zomb.getLocation();

					int zx = (int)zombLoc.getX();
					int zy = (int)zombLoc.getY();
					ArrayList<Hero> HerosArroundMe = null;
					addHeroToHerosArroundMe(zx+1,zy+1,HerosArroundMe);
					addHeroToHerosArroundMe(zx+1,zy,HerosArroundMe);
					addHeroToHerosArroundMe(zx+1,zy-1,HerosArroundMe);
					addHeroToHerosArroundMe(zx,zy+1,HerosArroundMe);
					addHeroToHerosArroundMe(zx,zy-1,HerosArroundMe);
					addHeroToHerosArroundMe(zx-1,zy+1,HerosArroundMe);
					addHeroToHerosArroundMe(zx-1,zy,HerosArroundMe);
					addHeroToHerosArroundMe(zx-1,zy-1,HerosArroundMe);
					for(int i = HerosArroundMe.size()-1; i>=0;i--) {
						Hero H = HerosArroundMe.remove(i);
						zomb.setTarget(H);
						try {
							zomb.attack();
						} catch (GameActionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				for(x =0;x<=14; x++) {
					for(y =0; y<=14; y++) {
						if(!isHero(x,y)) {
							map[x][y].setVisible(false);
						}
						
					}
				}
				for(x =0;x<=14; x++) {
					for(y =0; y<=14; y++) {
						if(isHero(x,y)) {
							map[x][y].setVisible(false);
							if(x+1 <15) {
								if(y+1 <15) {
									map[x+1][y+1].setVisible(true);
								}
								map[x+1][y].setVisible(true);
								if(y-1 >=0) {
									map[x+1][y-1].setVisible(true);
								}
							}
								map[x][y+1].setVisible(true);
								map[x][y-1].setVisible(true);
								map[x-1][y+1].setVisible(true);
								map[x-1][y].setVisible(true);
								map[x-1][y-1].setVisible(true);
							}
						}
						
					}
				}
				
				for(int i = heroes.size()-1; i >=0; i--) {
					Hero H2 = heroes.get(i);
					H2.setSpecialAction(false);
					H2.setTarget(null);
					H2.setActionsAvailable(H2.getMaxActions());
				}
			}
		}
		
	
	
// Main Method added for debugging, remove later
	public static void main(String[]args) throws GameActionException{

		Character z  = new Zombie();
		
		System.out.println(z instanceof Zombie);
		
	}
}

