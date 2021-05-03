package com.cmpt276.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class TileMapTest {
    @Test
    /**
     * Integration test for TileMap and Coin's EnterEffect interaction. 
     * Tests if game's score is correct number for coin's EnterEffect
     */
    public void coinEnterEffect()
    {
        MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        TileMap map = new TileMap(gen);
        Thief coinThief = new Thief(new Position(3,1), Direction.West, map, game);
        game.setThief(coinThief);
        map.enterEffect(coinThief);
        assertTrue("Coin EnterEffect score increased failed", game.getScore() == 10);
    }
    @Test
    /**
     * Integration test for TileMap and guardStation's EnterEffect interaction. 
     * Tests if game's score is correct number for guardStation's EnterEffect
     */
    public void guardStationEnterEffect()
    {
        MapGenerator gen = new LevelLoader("target/classes/maps/map2.txt");
        Game game = new Game(gen);
        TileMap map = new TileMap(gen);
        Thief guardStationThief = new Thief(new Position(4,1), Direction.West, map, game);
        game.setThief(guardStationThief);
        map.enterEffect(guardStationThief);
        assertTrue("GuardStation EnterEffect score decrease failed", game.getScore() == -20);
    }

    @Test
    /**
     * Testing canMove(Entity, Direction) for correctly stopping and allowing Entity's movement
     */
    public void canMoveEntityTest(){
        MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        TileMap map = new TileMap(gen);
        Thief thief = game.getThief();
        assertTrue("canMoveEntity failed movement prevention", map.canMove(thief, Direction.West) == false);
        thief = new Thief(new Position(0,2), Direction.East, map, game);
        assertTrue("canMoveEntity failed movement", map.canMove(thief, Direction.East) == true);
    }

    @Test
    /**
     * Testing canMove(Position, Direction) for correctly stopping and allowing Entity's movement
     */
    public void canMovePosTest(){
        MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        TileMap map = new TileMap(gen);
        Thief thief = new Thief(new Position(2,2), Direction.East, map, game);
        assertTrue("canMovePos failed movement prevention", map.canMove(thief.getPos(), Direction.South) == false);
        assertTrue("canMovePos failed movement", map.canMove(thief.getPos(), Direction.North) == true);
    }

    @Test
    /**
     * Testing getSize to return correct getPos.
     * <p>
     * Have to test Position's getX and getY as Positions
     * cannot be compared.
     */
    public void getSizeTest(){
        MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        TileMap map = new TileMap(gen);
        Position expPos = new Position(7, 3);
        assertEquals("Tilemap getSize X failed", map.getSize().getX(), expPos.getX());
        assertEquals("Tilemap getSize Y failed", map.getSize().getY(), expPos.getY());
    }

    @Test
    /**
     * Testing if hasRemainingCoins is returning expected results.
     */
    public void hasRemainingCoinsTest(){
        MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        TileMap map = new TileMap(gen);
        Thief coinThief = new Thief(new Position(3,1), Direction.West, map, game);
        assertTrue("TileMap hasRemainingCoins did not find coin", map.hasRemainingCoins() == true);
        game.setThief(coinThief);
        map.enterEffect(coinThief);
        assertTrue("TileMap hasRemainingCoins should have not found a coin", map.hasRemainingCoins() == false);
    }

    
}
