package com.cmpt276.Map;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cmpt276.Game.*;
import com.cmpt276.Character.*;

public class MuggingTest {
    @Test
    /**
     * Testing if Mugging's EnterEffect is increasing score by correct amount
     * and if it is changing its hasReward boolean to false afterwards
     */
    public void enterEffectTest()
    {
        MapGenerator gen = new LevelLoader("target/classes/maps/map1.txt");
        Game game = new Game(gen);
        Thief thief = game.getThief();
        Mugging mugging = new Mugging(0, new Position(0,0));
        mugging.setHasRewardTrue();
        mugging.enterEffect(thief);
        assertTrue("EnterEffect score increase failed", game.getScore() == 30);
        assertTrue("hasReward switch failed", mugging.stillHasReward() == false);
    }
}
