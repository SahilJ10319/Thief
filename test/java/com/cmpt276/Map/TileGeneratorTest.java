package com.cmpt276.Map;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cmpt276.Map.*;
import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class TileGeneratorTest {
    private static Direction[] dirs = new Direction[] { Direction.West, Direction.North, Direction.East, Direction.South };
    private static int[] wallConfigurations = new int[] { 1, 2, 4, 8 };
    private TileGenerator gen = new TileGenerator();

    /**
     * tests that the walls are placed correcly by the factory
     */
    @Test
    public void correctWallPlacement() {
        int errorCount = 0;

        for (int i = 0; i < 4; i++) {
            Tile room = gen.CreateTile(wallConfigurations[i], new Position(0, 0));
            for (int j = 0; j < 4; j++) {
                errorCount += (room.canExit(dirs[j]) == (i != j)) ? 0 : 1;
            }
        }

        assertTrue("Some walls placed incorrectly: " + errorCount, errorCount == 0);
    }

    /**
     * tests that the room types are generated correctly
     */
    @Test
    public void correctRoomTypes() {
        assertTrue("Does't make room correctly", gen.CreateTile(0 << 4, new Position(0, 0)) instanceof Room);
        assertTrue("Does't make coin correctly", gen.CreateTile(1 << 4, new Position(0, 0)) instanceof Coin);
        assertTrue("Does't make guard station correctly", gen.CreateTile(2 << 4, new Position(0, 0)) instanceof GuardStation);
        assertTrue("Does't make mugging correctly", gen.CreateTile(3 << 4, new Position(0, 0)) instanceof Mugging);
    }
}
