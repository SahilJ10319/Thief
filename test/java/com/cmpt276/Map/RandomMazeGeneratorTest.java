package com.cmpt276.Map;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.cmpt276.Map.*;
import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class RandomMazeGeneratorTest {
    private RandomMazeGenerator.Options genOptions = new RandomMazeGenerator.Options(18, 12);
    private MapGenerator gen = new RandomMazeGenerator(genOptions);

    /**
     * tests that the dimensions of the map are loaded correctly
     */
    @Test
    public void testDimensions() {
        gen.generateMap(0);
        assertTrue("Incorrect map dimensions for random maze", gen.getSize().getX() == 18 && gen.getSize().getY() == 12);
    }

    /**
     * tests that the amount of tiles is correct
     */
    @Test
    public void testMap() {
        List<Tile> map = gen.generateMap(0);
        assertTrue("Incorrect amount of tile in map", gen.getSize().getX() * gen.getSize().getY() == map.size());
    }

    /**
     * tests that the gaurds are loaded correctly
     */
    @Test
    public void loadedGuards() {
        gen.generateMap(0);
        List<Guard> guards = gen.getGuards(null, null);
        assertTrue("Incorrect amount of guards created", guards.size() == genOptions.numMovingGuards);
    }

    /**
     * tests that all of the tiles created are not null
     */
    @Test
    public void loadedTiles() {
        List<Tile> map = gen.generateMap(0);
        for (int i = 0; i < map.size(); i++) {
            assertTrue("Incorrect tile created", map.get(i) != null);
        }
    }
}
