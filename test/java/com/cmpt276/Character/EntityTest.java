package com.cmpt276.Character;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import com.cmpt276.Map.*;
import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class EntityTest {
    private Entity entity;
    private Position position;
    private TileMap tileMap;
    private Direction direction;

    /**
     * Set Up for Entity test Class
     */
    @Before
    public void setUp() {
        direction = Direction.North;
        RandomMazeGenerator.Options options = new RandomMazeGenerator.Options(4, 4);
        MapGenerator mapGenerator = new RandomMazeGenerator(options);
        tileMap = new TileMap(mapGenerator);
    }

    /**
     * Tests to see if Entity moves in Direction East when called
     */
    @Test
    public void moveToEastTest() {
        position = new Position(2, 2);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.East);
        assertEquals(3, entity.getPos().getX());
        assertEquals(2, entity.getPos().getY());
    }

    /**
     * Tests to see if Entity moves in Direction West when called
     */
    @Test
    public void moveToWestTest() {
        position = new Position(2, 2);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.West);
        assertEquals(1, entity.getPos().getX());
        assertEquals(2, entity.getPos().getY());
    }

    /**
     * Tests to see if Entity moves in Direction North when called
     */
    @Test
    public void moveToNorthTest() {
        position = new Position(2, 2);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.North);
        assertEquals(2, entity.getPos().getX());
        if (tileMap.canMove(new Position(2, 2), Direction.North)) {
            assertEquals(1, entity.getPos().getY());
        } else {
            assertEquals(2, entity.getPos().getY());
        }
    }

    /**
     * Tests to see if Entity moves in Direction South when called
     */
    @Test
    public void moveToSouthTest() {
        position = new Position(2, 2);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.South);
        assertEquals(2, entity.getPos().getX());
        if (tileMap.canMove(new Position(2, 2), Direction.South)) {
            assertEquals(3, entity.getPos().getY());
        } else {
            assertEquals(2, entity.getPos().getY());
        }
    }

    /**
     * Tests to see if move to Neutral Direction is working when called
     */
    @Test
    public void DoNotMoveTest() {
        position = new Position(2, 2);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.Neutral);
        assertEquals(2, entity.getPos().getX());
        assertEquals(2, entity.getPos().getY());
    }

    /**
     * Tests to see if entity stays on the board when
     * at the right edge and invalid direction is passed to move
     */
    @Test
    public void moveAtRightEdgeToEastTest() {
        position = new Position(5, 2);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.East);
        assertEquals(5, entity.getPos().getX());
        assertEquals(2, entity.getPos().getY());
    }

    /**
     * Tests to see if entity stays on the board when
     * at the top edge and invalid direction is passed to move
     */
    @Test
    public void moveAtNorthEdgeToNorthTest() {
        position = new Position(2, 0);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.North);
        assertEquals(2, entity.getPos().getX());
        assertEquals(0, entity.getPos().getY());
    }

    /**
     * Tests to see if entity stays on the board when
     * at the bottom edge and invalid direction is passed to move
     */
    @Test
    public void moveAtSouthEdgeToSouthTest() {
        position = new Position(2, 5);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.North);
        assertEquals(2, entity.getPos().getX());
        assertEquals(5, entity.getPos().getY());
    }

    /**
     * Tests to see if entity stays on the board when
     * at the left edge and invalid direction is passed to move
     */
    @Test
    public void moveAtLeftEdgeToWestTest() {
        position = new Position(0, 2);
        entity = new Guard(position, direction, tileMap, null);
        entity.move(Direction.West);
        assertEquals(0, entity.getPos().getX());
        assertEquals(2, entity.getPos().getY());
    }

    /**
     * Tests to see if getPosition method accurately returns position
     */
    @Test
    public void getPositionTest() {
        position = new Position(2, 2);
        entity = new Guard(position, direction, tileMap, null);
        assertNotNull(entity.getPos());
        assertEquals(2, entity.getPos().getX());
        assertEquals(2, entity.getPos().getY());
    }

}
