package com.cmpt276.Map;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class CoinTest {
    @Test
    /**
     * Testing if Coin's EnterEffect is increasing score by correct amount
     * and if it is changing its hasReward boolean to false afterwards
     */
    public void enterEffectTest()
    {
        MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        Thief thief = game.getThief();
        Coin coin = new Coin(0, new Position(0,0));
        coin.enterEffect(thief);
        assertTrue("EnterEffect score increased failed", game.getScore() == 10);
        assertTrue("hasReward switch failed", coin.stillHasReward() == false);
    }
}
