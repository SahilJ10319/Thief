package com.cmpt276.Game;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


import org.junit.Test;


import com.cmpt276.Character.*;
import com.cmpt276.Game.*;
import com.cmpt276.Map.*;

public class GameTest {

	@Test
	/**
	 * Test if game's addscore method works as intended
	 */
	public void addscoreTest(){
		MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        game.addscore(10);
        assertEquals(10, game.getScore());
	}
	
	@Test 

	/**
	 * Test of setThief moves the position of thief
	 */
	public void setThiefTest(){
		MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
		game.setThief(new Thief(new Position(0,1), Direction.West, new TileMap(gen), game)); 
		assertEquals("Tilemap getSize X failed", game.getThief().getPos().getX(), 0);
        assertEquals("Tilemap getSize Y failed", game.getThief().getPos().getY(), 1);
	}

	@Test
	/**
	 * Test if the game is open
	 */
	public void isOpenTest(){
		MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
		Game game = new Game(gen);
		assertTrue(game.isOpen());
	}


	@Test
	/**
	 * Test if the game closes as intended
	 */
	public void CloseTest(){
		MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
		Game game = new Game(gen);
		game.close();
		assertFalse(game.isOpen());
	}

	@Test
	/**
	 * Tests to see if wonGame activates when the requirements are met.
	 */
	public void wonGameTest(){
		MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        TileMap map = game.getMap();
        Thief coinThief = new Thief(new Position(3,1), Direction.West, map, game);
		Thief coinThiefexit = new Thief(new Position(6,0), Direction.West, map, game);
        game.setThief(coinThief);
        map.enterEffect(coinThief);
		game.setThief(coinThiefexit);
		assertTrue(game.wonGame());
		
	}

}
