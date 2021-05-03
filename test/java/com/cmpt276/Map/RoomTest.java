package com.cmpt276.Map;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cmpt276.Map.*;
import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class RoomTest {
    private static Direction[] dirs = new Direction[] { Direction.West, Direction.North, Direction.East, Direction.South };
    private static int[] wallConfigurations = new int[] { 1, 2, 4, 8 };

    /**
     * tests that the walls are placed correctly
     */
    @Test
    public void correctWallPlacement() {
        int errorCount = 0;

        for (int i = 0; i < 4; i++) {
            Tile room = new Room(wallConfigurations[i], new Position(0, 0));
            for (int j = 0; j < 4; j++) {
                errorCount += (room.canExit(dirs[j]) == (i != j)) ? 0 : 1;
            }
        }

        assertTrue("Some walls placed incorrectly: " + errorCount, errorCount == 0);
    }
}
