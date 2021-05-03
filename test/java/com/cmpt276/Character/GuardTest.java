package com.cmpt276.Character;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;

import com.cmpt276.Map.*;
import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class GuardTest {

    private Guard guard;
    private Position position;
    private TileMap tileMap;
    private Direction direction;
    private MapGenerator mapGenerator;

    /**
     * Set Up for Guard Test Classes
     */
    @Before
    public void setUp() {
        direction = Direction.North;
        RandomMazeGenerator.Options options = new RandomMazeGenerator.Options(4, 4);
        mapGenerator = new RandomMazeGenerator(options);
        tileMap = new TileMap(mapGenerator);
    }

    /**
     * Test that checks if update method for guard is working
     */
    @Test
    public void updateGuardTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        Thief thief = new Thief(new Position(2, 3), Direction.North, tileMap, game);
        guard = new Guard(position, direction, tileMap, thief );
        guard.update();
        assertEquals(2, guard.getPos().getX());
        assertEquals(2, guard.getPos().getY());
    }

    /**
     * Checks if Guard updates, and checks if Guard kills thief when in same spot
     */
    @Test
    public void updateGuardAndKillThiefTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        Thief thief = new Thief(new Position(2, 2), Direction.North, tileMap, game);
        thief.addScore(20);
        guard = new Guard(position, direction, tileMap, thief );
        guard.update();
        assertEquals(2, guard.getPos().getX());
        assertEquals(2, guard.getPos().getY());
        assertEquals(-10, game.getScore());
    }
}
