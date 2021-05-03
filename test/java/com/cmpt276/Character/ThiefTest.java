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

public class ThiefTest {
    private Thief thief;
    private Position position;
    private TileMap tileMap;
    private Direction direction;
    private MapGenerator mapGenerator;

    /**
     * Set up for Thief Test Class
     */
    @Before
    public void setUp() {
        direction = Direction.North;
        RandomMazeGenerator.Options options = new RandomMazeGenerator.Options(4, 4);
        mapGenerator = new RandomMazeGenerator(options);
        tileMap = new TileMap(mapGenerator);
    }

    /**
     * Tests to see if key event for moving west is working, and if thief updates
     */
    @Test
    public void updateAfterMoveToWestKeyPressedTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.A, false, false, false, false);
        game.handle(event);
        thief = new Thief(position, direction, tileMap, game);
        thief.update();
        assertEquals(1, thief.getPos().getX());
        assertEquals(2, thief.getPos().getY());
    }

    /**
     * Tests to see if key event for moving east is working, and if thief updates
     */
    @Test
    public void updateAfterMoveToEastKeyPressedTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.D, false, false, false, false);
        game.handle(event);
        thief = new Thief(position, direction, tileMap, game);
        thief.update();
        assertEquals(3, thief.getPos().getX());
        assertEquals(2, thief.getPos().getY());
    }

    /**
     * Tests to see if key event for moving North is working, and if thief updates
     */
    @Test
    public void updateAfterMoveToNorthKeyPressedTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.W, false, false, false, false);
        game.handle(event);
        thief = new Thief(position, direction, tileMap, game);
        thief.update();
        assertEquals(2, thief.getPos().getX());
        if (tileMap.canMove(new Position(2, 2), Direction.North)) {
            assertEquals(1, thief.getPos().getY());
        } else {
            assertEquals(2, thief.getPos().getY());
        }
    }

    /**
     * Tests to see if key event for moving south is working, and if thief updates
     */
    @Test
    public void updateAfterMoveToSouthKeyPressedTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.S, false, false, false, false);
        game.handle(event);
        thief = new Thief(position, direction, tileMap, game);
        thief.update();
        assertEquals(2, thief.getPos().getX());
        if (tileMap.canMove(new Position(2, 2), Direction.South)) {
            assertEquals(3, thief.getPos().getY());
        } else {
            assertEquals(2, thief.getPos().getY());
        }
    }

    /**
     * Test to see if thief's position os returned correctly to reduce distance
     */
    @Test
    public void getNextDirTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        thief = new Thief(position, direction, tileMap, game);
        assertEquals(Direction.Neutral, thief.getNextDir(new Position(2, 2)));
    }

    /**
     * Test to see if thief's add score method works
     */
    @Test
    public void addScoreTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        thief = new Thief(position, direction, tileMap, game);
        thief.addScore(10);
        assertEquals(10, game.getScore());
    }

    /**
     * Test to see if score changes after being killed while having no score
     */
    @Test
    public void killWithNoScoreTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        thief = new Thief(position, direction, tileMap, game);
        thief.kill();
        assertEquals(-10, game.getScore());
    }

    /**
     * Test to see if score changes if killed while having a score beforehand
     */
    @Test
    public void killWithPreScoreTest() {
        position = new Position(2, 2);
        Game game = new Game(mapGenerator, new Pane());
        thief = new Thief(position, direction, tileMap, game);
        thief.addScore(50);
        thief.kill();
        assertEquals(-10, game.getScore());
    }
}

