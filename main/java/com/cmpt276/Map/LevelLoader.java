package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/** 
 * MapGenerator to load a map from a file
 */
public class LevelLoader implements MapGenerator {
    private Position size = null;
    private int[] tiles = null;
    private List<Position> guardPositions;

    /**
     * Converts a hex digit to its binary representation
     * 
     * @param hex the hex digit to be converted
     */
    private int hexToInt(char hex) {
        if (hex >= '0' && hex <= '9')
            return hex - '0';
        else if (hex >= 'a' && hex <= 'f')
            return hex - 'a' + 10;
        else if (hex >= 'A' && hex <= 'F')
            return hex - 'A' + 10;
        else
            return 0;
    }

    /**
     * Converts two characters to their integer tile representations
     * 
     * @param walls the hex character representing the wall configuration of the tile
     * @param type the hex character representing the type of Tile to be created
     */
    private int charsToTile(char walls, char type) {
        return hexToInt(walls) + (hexToInt(type) << 4);
    }

    /**
     * Constructor
     * loads the map data from the file at [filepath]
     * 
     * @param filepath the path to the map to be loaded
     */
    public LevelLoader(String filepath) {
        this.guardPositions = new ArrayList<Position>();
        try {
            File file = new File(filepath);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String str = br.readLine();

            String[] dims = str.split(" ");
            this.size = new Position(Integer.parseInt(dims[0]), Integer.parseInt(dims[1]));

            this.tiles = new int[this.size.getX() * this.size.getY()];

            for (int y = 0; y < this.size.getY(); y++) {
                str = br.readLine();
                for (int x = 0; x < this.size.getX(); x++) {
                    this.tiles[x + y * this.size.getX()] = charsToTile(str.charAt(x * 2), str.charAt(x * 2 + 1));
                }
            }
        } catch(Exception e) {
            System.out.println("Failed to load: " + filepath);
        }
    }
    /**
     * returns the width and height of the map as a position
     * 
     * @return the width and height as a position
     */
    public Position getSize() {
        return this.size;
    }

    /**
     * returns an array of tiles representing the map
     * 
     * @param seed unused in this implementation of the function
     * @return a list of tiles representing the map
     */
    public List<Tile> generateMap(int seed) {
        List<Tile> map = new ArrayList<Tile>();

        TileGenerator gen = new TileGenerator();

        for (int y = 0; y < this.size.getY(); y++) {
            for (int x = 0; x < this.size.getX(); x++) {
                int tile = tiles[x + y * this.size.getX()];
                int tileType = (tile >> 4);
                map.add(gen.CreateTile(tile, new Position(x, y)));
                if (tileType == 4) {
                    this.guardPositions.add(new Position(x, y));
                }
            }
        }

        return map;
    }
    /**
     * Returns an array of moving guards that corresponds to the map
     * 
     * @return array of moving guards that corresponds to the map
     */
    public List<Guard> getGuards(TileMap map, Thief thief) {
        List<Guard> guards = new ArrayList<Guard>();

        for (Position pos : this.guardPositions) {
            guards.add(new Guard(pos, Direction.West, map, thief));
        }

        return guards;
    }
}