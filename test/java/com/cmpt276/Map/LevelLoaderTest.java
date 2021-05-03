package com.cmpt276.Map;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.cmpt276.Map.*;
import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class LevelLoaderTest {
    MapGenerator level3 = new LevelLoader("target/classes/maps/map3.txt");
    MapGenerator level4 = new LevelLoader("target/classes/maps/map4.txt");

    /**
     * tests that the coin tiles are loaded correctly
     */
    @Test
    public void loadedCoin() {
        List<Tile> tiles = level4.generateMap(0);
        assertTrue("Coin was not loaded", tiles.get(1) instanceof Coin);
    }
    /**
     * tests that the guard station tiles are loaded correctly
     */
    @Test
    public void loadedGuardStation() {
        List<Tile> tiles = level3.generateMap(0);
        assertTrue("GuardStation was not loaded", tiles.get(11) instanceof GuardStation);
    }
    /**
     * tests that the mugging is loaded correctly
     */
    @Test
    public void loadedMugging() {
        List<Tile> tiles = level3.generateMap(0);
        assertTrue("Mugging was not loaded", tiles.get(7) instanceof Mugging);
    }
    /**
     * tests that the guards are loaded correctly and that their positions are correct
     */
    @Test
    public void loadedGuards() {
        level4.generateMap(0);
        List<Guard> guards = level4.getGuards(null, null);
        assertTrue("Incorrect amount of guards created", guards.size() == 1);
        assertTrue("Incorrect guard position", guards.get(0).getPos().getX() == 3 &&  guards.get(0).getPos().getY() == 2);
    }
    /**
     * tests that the walls of a room are correct
     *
     *
     * @param tile the tile to test
     * @param west wether or not there is a wall in the west
     * @param north wether or not there is a wall in the north
     * @param east wether or not there is a wall in the east
     * @param south wether or not there is a wall in the south
     */
    private void testWalls(Tile tile, boolean west, boolean north, boolean east, boolean south) {
        assertTrue("Incorrect West wall", tile.canExit(Direction.West) == west);
        assertTrue("Incorrect North wall", tile.canExit(Direction.North) == north);
        assertTrue("Incorrect East wall", tile.canExit(Direction.East) == east);
        assertTrue("Incorrect South wall", tile.canExit(Direction.South) == south);
    }
    /**
     * tests that the walls are loaded correctly
     */
    @Test
    public void loadWalls() {
        List<Tile> tiles = level3.generateMap(0);

        testWalls(tiles.get(0), false, false, true, false);
        testWalls(tiles.get(13), true, false, false, true);
        testWalls(tiles.get(16), true, true, false, false);
    }
    /**
     * tests that the dimension of the maps are correct
     */
    @Test
    public void testDimensions() {
        assertTrue("Incorrect map dimensions for map 3", level3.getSize().getX() == 7 && level3.getSize().getY() == 3);
        assertTrue("Incorrect map dimensions for map 4", level4.getSize().getX() == 7 && level4.getSize().getY() == 3);
    }
}
