package engine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.world.Cell;

public class Game {
	public static ArrayList<Hero>availableHeroes;
	public static ArrayList<Hero> heroes;
	public static ArrayList<Zombie> zombies;
	public static Cell [][] map;
	
	public static void loadHeroes(String filePath) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader(filePath));

        while(br.readLine() != null)
        {
            String[] hero = (br.readLine()).split(",");

            switch (hero[1]) {
            case "FIGH":availableHeroes.add(new Fighter(hero[0],Integer.parseInt(hero[2]),Integer.parseInt(hero[4]),Integer.parseInt(hero[3])));break;
            case "EXP":availableHeroes.add(new Explorer(hero[0],Integer.parseInt(hero[2]),Integer.parseInt(hero[4]),Integer.parseInt(hero[3])));break;
            case "MED":availableHeroes.add(new Medic(hero[0],Integer.parseInt(hero[2]),Integer.parseInt(hero[4]),Integer.parseInt(hero[3])));break;

            }

        }

    }
}

